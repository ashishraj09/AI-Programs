package knn.bean;

public class KNNSortedData {

	private double[] testFeatures;	
	private double distance;
	private String resultLabel;
	
	public double[] getTestFeatures() {
		return testFeatures;
	}

	public void setTestFeatures(double[] testFeatures) {
		this.testFeatures = testFeatures;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public String getResultLabel() {
		return resultLabel;
	}

	public void setResultLabel(String resultLabel) {
		this.resultLabel = resultLabel;
	}

	public KNNSortedData(double[] testFeatures, double distance, String resultLabel) {
		super();
		this.testFeatures = testFeatures;
		this.distance = distance;
		this.resultLabel = resultLabel;
	}
}
