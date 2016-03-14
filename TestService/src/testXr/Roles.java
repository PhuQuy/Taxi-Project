package testXr;
import java.util.HashSet;
import java.util.Set;

public class Roles implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private String name;
	private Set<User> users = new HashSet<User>(0);

	public Roles() {
	}

	public Roles(String name) {
		this.name = name;
	}

	public Roles(String name, Set<User> users) {
		this.name = name;
		this.users = users;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<User> getUsers() {
		return this.users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}

}
