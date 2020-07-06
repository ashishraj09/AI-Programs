package knn.bean;

public class KNNResultData {
	
	private double[] testFeatures;	
	private String resultLabel;
	public double[] getTestFeatures() {
		return testFeatures;
	}
	public void setTestFeatures(double[] testFeatures) {
		this.testFeatures = testFeatures;
	}
	public String getResultLabel() {
		return resultLabel;
	}
	public void setResultLabel(String resultLabel) {
		this.resultLabel = resultLabel;
	}
	public KNNResultData(double[] testFeatures, String resultLabel) {
		super();
		this.testFeatures = testFeatures;
		this.resultLabel = resultLabel;
	}
	
}
