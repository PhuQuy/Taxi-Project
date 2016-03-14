package testXr;


import java.io.Serializable;

public class AppItem implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String label;
	private String name;
	private String packageName;
	private Boolean included;
	private Boolean important;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPackageName() {
		return packageName;
	}
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	public Boolean getIncluded() {
		return included;
	}
	public void setIncluded(Boolean included) {
		this.included = included;
	}
	public Boolean getImportant() {
		return important;
	}
	public void setImportant(Boolean important) {
		this.important = important;
	}
//	public User getOwner() {
//		return owner;
//	}
//	public void setOwner(User owner) {
//		this.owner = owner;
//	}
	
	
	
}
