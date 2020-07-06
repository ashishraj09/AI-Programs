package dt.bean;

public class Instance {
	
	public Instance(boolean[] attributes, boolean[] visitedAttribute) {
		super();
		this.attributes = attributes;
		this.visitedAttribute = visitedAttribute;
	}
	private boolean[] attributes;
	private boolean[] visitedAttribute;
	
	public boolean[] getAttributes() {
		return attributes;
	}
	public void setAttributes(boolean[] attributes) {
		this.attributes = attributes;
	}
	public boolean[] getVisitedAttribute() {
		return visitedAttribute;
	}
	public void setVisitedAttribute(boolean[] visitedAttribute) {
		this.visitedAttribute = visitedAttribute;
	}
}