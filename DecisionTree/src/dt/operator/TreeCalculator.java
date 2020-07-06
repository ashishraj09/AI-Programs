package dt.operator;

import java.util.List;

import dt.bean.Instance;


public class TreeCalculator extends Helper {
	
	TreeCalculator()
	{
		
	}

	
	public static double calcuateLiveEntropy(List<Instance> trainingSet)
	{
		int liveClassCount = 0, dieClassCount = 0; 
		for(Instance train : trainingSet)
		{
			if(getClassLabel(train.getAttributes()))
				{
					liveClassCount++;
				}
			else
				{
					dieClassCount++;
				}
		}
		return entropyCalculator(liveClassCount,dieClassCount);
		
	}
	
	public static double entropyCalculator(int positive, int negative)
	{
		double positiveprobability, negativeprobability;
		double positiveLogValue = 0, negativeLogValue = 0;
		
		if(0 == positive && 0 == negative)
		{
			positiveprobability = negativeprobability =  0;
		}
		else
		{
			positiveprobability = (double)positive/(positive + negative);
			negativeprobability = (double)negative/(positive + negative);
		}
		
		if(0 == positive)
			positiveLogValue = 0;
		else
			positiveLogValue = logCalculator(positiveprobability);
		
		if(0 == negative)
			negativeLogValue = 0;
		else
			negativeLogValue = logCalculator(negativeprobability);

		return -positiveprobability * positiveLogValue - negativeprobability * negativeLogValue;
		
	}
	
	
	private static double logCalculator(double value)
	{
		return Math.log10(value)/Math.log10(2);
	}
	
	static double calculateInfoGain(int columnNumber, List<Instance> trainingSet) {
		
		int trueLiveCount = 0, trueDieCount = 0, falseLiveCount = 0, falseDieCount = 0;
		for (int i = 0; i< trainingSet.size(); i++) {

			boolean classLabel = getClassLabel(trainingSet.get(i).getAttributes());
			
			if (getBooleanValueAtIndex(trainingSet.get(i).getAttributes(),columnNumber)) {
				if (classLabel)
					{
					trueLiveCount++;
					}
				else
					{
					trueDieCount++;
					}
			}else{
				if (classLabel)
					{
					falseLiveCount++;
					}
				else
					{
					falseDieCount++;
					}
			}

		}
		
		double truePercentage = (double) (trueLiveCount+trueDieCount)/(trueLiveCount + trueDieCount + falseLiveCount + falseDieCount);
		double falsePercentage = (double) (falseLiveCount+falseDieCount)/(trueLiveCount + trueDieCount + falseLiveCount + falseDieCount);

		return calcuateLiveEntropy(trainingSet) - (truePercentage * entropyCalculator(trueLiveCount,trueDieCount) + falsePercentage* entropyCalculator(falseLiveCount,falseDieCount));

	}
	
}
