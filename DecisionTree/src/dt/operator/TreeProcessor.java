package dt.operator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import dt.bean.Attribute;
import dt.bean.DecisionTree;
import dt.bean.Instance;

public class TreeProcessor extends Helper {
	

	public static DecisionTree treeBuilder(List<Instance> trainingSet,List<String> attributeNames)
	{
		try {
				List <Attribute> dataQueue = new ArrayList <Attribute> ();
				if(0 == TreeCalculator.calcuateLiveEntropy(trainingSet)){
					return new DecisionTree(getClassLabel(trainingSet.get(0).getAttributes()));
				}
		
				for (int i = 0; i < attributeNames.size(); i++)
					dataQueue.add(new Attribute(TreeCalculator.calculateInfoGain(i,trainingSet),attributeNames.get(i),i));
		
				Collections.sort(dataQueue, new Comparator<Attribute>(){
					@Override
					public int compare(Attribute data1, Attribute data2) {
						if (data1.getInfoGain() < data2.getInfoGain())
						{
							return 1;
						}
						else 
						{
							return -1;
						}
					}});
					Attribute att = dataQueue.get(0);
					dataQueue.remove(0);
		
					for (int i = 0; i < trainingSet.size(); i++) 
						setAttributeVisited(trainingSet.get(i).getVisitedAttribute(),att.getColumnIndex());
					
					return new DecisionTree(att.getColumnName(),treeBuilder(splitTrainingSet(trainingSet,att.getColumnIndex(),true),attributeNames),treeBuilder(splitTrainingSet(trainingSet,att.getColumnIndex(),false),attributeNames));
					
		}catch(Exception e)
		{
			System.err.println("Error Creating Tree : " + e.getMessage());
		}
		return new DecisionTree();
	}
	
	private static List<Instance> splitTrainingSet (List<Instance> trainingSet, int columnNumber, boolean criteria) {

		List<Instance> subTrainingSet = new ArrayList <Instance> ();

		for (int i = 0; i< trainingSet.size();i++) {
			if (criteria == getBooleanValueAtIndex(trainingSet.get(i).getAttributes(), columnNumber))
				subTrainingSet.add(trainingSet.get(i));
		}
		return subTrainingSet;

	}
	
}
