#!/usr/bin/env python
# coding: utf-8

# In[1]:


import numpy as np
import pandas as pd
from sklearn.neighbors import KNeighborsClassifier
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


clf = KNeighborsClassifier(n_neighbors=9)


# In[9]:


clf.fit(x_Train,y_Train)


# In[10]:


pred = clf.predict(x_Test)


# In[11]:


print(classification_report(y_Test,pred))


# In[12]:


print("Training set score: %f" % clf.score(x_Train, y_Train))


# In[13]:


print("Test set score: %f" % clf.score(x_Test, y_Test))


# In[14]:


a=y_Test.values

count = 0
for i in range(len(pred)):
	if pred[i] == a[i]:
		count = count + 1
count


# In[15]:


len(pred)


# In[16]:


count/len(pred)


# In[17]:


print("Test Accuracy:",(count/len(pred)) * 100)


# In[18]:


print("Training set score: %f" % clf.score(x_Train, y_Train))

