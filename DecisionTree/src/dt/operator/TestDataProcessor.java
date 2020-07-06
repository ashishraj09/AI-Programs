package dt.operator;

import java.util.ArrayList;
import java.util.List;

import dt.bean.DataSet;
import dt.bean.DecisionTree;
import dt.bean.Instance;

public class TestDataProcessor extends Helper{

	
	public static List<Boolean> processTesData(DataSet data,DecisionTree tree)
	{
		
		List<Boolean> outcome = new ArrayList<>(); 
		for(int i = 0 ; i< data.getTestSet().size(); i++)
		{
			outcome.add(predictOutCome(data.getTestSet().get(i), tree, data.getAttributeNames()));
		}
		return outcome;
		
	}
	
	public static boolean predictOutCome(Instance instance,DecisionTree tree, List<String> attributeNames)
	{
		try {
			if (null == tree.getTrueBranch() && null == tree.getFalseBranch())
				return tree.isOutput();

			if (getBooleanValueAtIndex(instance.getAttributes(), attributeNames.indexOf(tree.getValue())))
					return predictOutCome(instance,tree.getTrueBranch(),attributeNames);
				else
					return predictOutCome(instance, tree.getFalseBranch(),attributeNames);
		}
		catch (Exception e) {
			System.out.println("Error processing Test Data"+e.getLocalizedMessage());
		}
		return false ;
	}
}
