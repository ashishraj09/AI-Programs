#!/usr/bin/env python
# coding: utf-8

# In[1]:


import numpy as np
import pandas as pd
from sklearn.preprocessing import StandardScaler
from sklearn.neural_network import MLPClassifier
from sklearn.metrics import classification_report


# In[2]:


wineTrain = pd.read_csv('wine_training', sep = ' ')


# In[3]:


wineTest = pd.read_csv('wine_test', sep = ' ')


# In[4]:


x_Train=wineTrain.drop('Class', axis = 1)


# In[5]:


y_Train= wineTrain['Class']


# In[6]:


x_Test=wineTest.drop('Class', axis = 1)


# In[7]:


y_Test=wineTest['Class']


# In[8]:


scaler = StandardScaler()


# In[9]:


scaler.fit(x_Train)


# In[10]:


x_Train = scaler.transform(x_Train)


# In[11]:


x_Test = scaler.transform(x_Test)


# In[12]:


clf = MLPClassifier(hidden_layer_sizes=(11), max_iter=1000,random_state = 40, verbose= True)


# In[13]:


clf.fit(x_Train, y_Train)


# In[14]:


pred=clf.predict(x_Test)


# In[15]:


print(classification_report(y_Test,pred))


# In[16]:


count = 0
a=y_Test.values
for i in range(len(pred)):
	if pred[i] == a[i]:
		count = count + 1
count


# In[17]:


len(pred)


# In[18]:


print("Test Accuracy:",(count/len(pred)) * 100)


# In[19]:


print("Training set score: %f" % clf.score(x_Train, y_Train))


# In[20]:


print("Test set score: %f" % clf.score(x_Test, y_Test))


# In[ ]:





# In[ ]:





# In[ ]:




