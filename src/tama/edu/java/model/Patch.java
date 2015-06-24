package tama.edu.java.model;

public class Patch {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Patch(String name) {
		super();
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
