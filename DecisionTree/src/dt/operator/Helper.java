package dt.operator;

import java.util.List;

import dt.bean.DataSet;
import dt.bean.DecisionTree;
import dt.bean.Instance;

public class Helper {
	
	Helper()
	{
		
	}

	public static boolean getBooleanValueAtIndex (boolean[] attributes, int index) {
		return attributes[index];
	}

	public static boolean getClassLabel (boolean[] attributes) {
		return attributes[attributes.length-1];
	}

	public static void setAttributeVisited (boolean[] visitedAttribute,int index) {
		visitedAttribute[index] = true;
	}

	public static boolean isAttributeVisited (boolean[] visitedAttribute, int index) {
		return visitedAttribute[index];
	}
	
	public static void getAccuracy(List<Boolean> testOutput,List<Instance> actualTestData)
	{
		int count = 0;
		if(testOutput.size() == actualTestData.size())
		{
			for(int i = 0; i< testOutput.size(); i++)
			{
				if(testOutput.get(i) == getClassLabel(actualTestData.get(i).getAttributes()))
					count++;
			}
		}
		double percentage =  (double) count/actualTestData.size();
		System.out.println("Decision Tree Accuracy: "+percentage * 100+"%");
		
	}
	
	public static void getBaseLineClassifier (DataSet data) {

		int count = 0;
		for (int i = 0; i < data.getTestSet().size(); i++)
			
			{
			if (getClassLabel(data.getTestSet().get(i).getAttributes()))
				count++;
			}
		double percentage =  (double) count/data.getTestSet().size();
		System.out.println("Baseline Accuracy: "+ percentage* 100+"%");

	}
	
	public static void displayFinalTree (String tab, DecisionTree tree) {
		if (null == tree.getTrueBranch() && null == tree.getFalseBranch()) {
			if (tree.isOutput())
				System.out.println(tab + "Class Live");
			else
				System.out.println(tab + "Class Die");

			return;
		}

		System.out.println(tab + tree.getValue() + " = true: ");
		displayFinalTree("\t" + tab,tree.getTrueBranch());

		System.out.println(tab + tree.getValue() + " = false: ");
		displayFinalTree("\t" + tab,tree.getFalseBranch());
	}
}
