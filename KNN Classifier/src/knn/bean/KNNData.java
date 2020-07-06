package knn.bean;

import java.util.ArrayList;
import java.util.List;

public class KNNData {
	
	private List<double[]> trainFeatures = new ArrayList<>();
	private List<String> trainLabel = new ArrayList<>();

	private List<double[]> testFeatures = new ArrayList<>();
	private List<String> testLabel = new ArrayList<>();
	
	List<String> attributeName = new ArrayList<String>();
	
	public List<double[]> getTrainFeatures() {
		return trainFeatures;
	}

	public void setTrainFeatures(List<double[]> trainFeatures) {
		this.trainFeatures = trainFeatures;
	}

	public List<String> getTrainLabel() {
		return trainLabel;
	}

	public void setTrainLabel(List<String> trainLabel) {
		this.trainLabel = trainLabel;
	}

	public List<double[]> getTestFeatures() {
		return testFeatures;
	}

	public void setTestFeatures(List<double[]> testFeatures) {
		this.testFeatures = testFeatures;
	}

	public List<String> getTestLabel() {
		return testLabel;
	}

	public void setTestLabel(List<String> testLabel) {
		this.testLabel = testLabel;
	}
	public List<String> getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(List<String> attributeName) {
		this.attributeName = attributeName;
	}

	@Override
	public String toString() {
		
		StringBuilder sb=new StringBuilder();  
		sb.append("KNNData: \n");
		sb.append("Training Set:\n");
		
		for(int i=0;i<trainFeatures.size();i++)
		{
			
			double[] trainFeatureList = trainFeatures.get(i);
			for (double list : trainFeatureList) {
	            sb.append(list+",");
	        }
			sb.append(trainLabel.get(i)+"\n");
			
		}
		sb.append("Test Set:\n");
		
		for(int i=0;i<testFeatures.size();i++)
		{
			
			double[] testFeatureList = testFeatures.get(i);
			for (double list : testFeatureList) {
	            sb.append(list+",");
	        }
			sb.append(testLabel.get(i)+"\n");
			
		}
		
		return sb.toString();
	}

	
	
}
