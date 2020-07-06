package dt.bean;

public class Attribute {

	private double infoGain;
	private String columnName;
	private int columnIndex;
	public double getInfoGain() {
		return infoGain;
	}
	public void setInfoGain(double infoGain) {
		this.infoGain = infoGain;
	}
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}
	public int getColumnIndex() {
		return columnIndex;
	}
	public void setColumnIndex(int columnIndex) {
		this.columnIndex = columnIndex;
	}
	public Attribute(double infoGain, String columnName, int columnIndex) {
		super();
		this.infoGain = infoGain;
		this.columnName = columnName;
		this.columnIndex = columnIndex;
	}
	
	@Override
	public String toString() {
		return "Attribute [infoGain=" + infoGain + ", columnName=" + columnName + ", columnIndex=" + columnIndex + "]";
	}
}
