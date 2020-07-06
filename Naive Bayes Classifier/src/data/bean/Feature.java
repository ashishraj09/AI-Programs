package data.bean;

public class Feature {

	private int[] inputs;
	private int classification;
	public int[] getInputs() {
		return inputs;
	}
	public void setInputs(int[] inputs) {
		this.inputs = inputs;
	}
	public int getClassification() {
		return classification;
	}
	public void setClassification(int classification) {
		this.classification = classification;
	}
	public Feature(int[] inputs, int classification) {
		super();
		this.inputs = inputs;
		this.classification = classification;
	}
	
	public Feature(int[] inputs) {
		super();
		this.inputs = inputs;
	}
	
}
