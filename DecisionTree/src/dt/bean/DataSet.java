package dt.bean;

import java.util.ArrayList;
import java.util.List;

public class DataSet {

	private List<Instance> trainingSet = new ArrayList <Instance> ();
	private List <Instance> testSet = new ArrayList <Instance> ();
	private List<String> attributeNames = new ArrayList <String> ();
	public List<Instance> getTrainingSet() {
		return trainingSet;
	}
	public void setTrainingSet(List<Instance> trainingSet) {
		this.trainingSet = trainingSet;
	}
	public List<Instance> getTestSet() {
		return testSet;
	}
	public void setTestSet(List<Instance> testSet) {
		this.testSet = testSet;
	}
	public List<String> getAttributeNames() {
		return attributeNames;
	}
	public void setAttributeNames(List<String> attributeNames) {
		this.attributeNames = attributeNames;
	}
	
}
