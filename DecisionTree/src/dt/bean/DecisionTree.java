package dt.bean;

public class DecisionTree {

	public DecisionTree(String value, DecisionTree trueBranch, DecisionTree falseBranch) {
		super();
		this.value = value;
		this.trueBranch = trueBranch;
		this.falseBranch = falseBranch;
	}
	
	public DecisionTree(boolean output) {
		super();
		this.output = output;
	}
	
	public DecisionTree() {
	}
	
	private String value;
	private DecisionTree trueBranch;
	private DecisionTree falseBranch;
	private boolean output;
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public DecisionTree getTrueBranch() {
		return trueBranch;
	}
	public void setTrueBranch(DecisionTree trueBranch) {
		this.trueBranch = trueBranch;
	}
	public DecisionTree getFalseBranch() {
		return falseBranch;
	}
	public void setFalseBranch(DecisionTree falseBranch) {
		this.falseBranch = falseBranch;
	}
	public boolean isOutput() {
		return output;
	}

	public void setOutput(boolean output) {
		this.output = output;
	}
	
}
