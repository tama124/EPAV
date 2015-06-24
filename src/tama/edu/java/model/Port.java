package tama.edu.java.model;

public class Port {
	final private String BADWARNING = "This port must be closed.";
	final private String GOODWARNING = "This port is OK.";
	final private String SOLUTION_1 = "Open Command Prompt as administrator and execute command: netsh advfirewall firewall add rule name=\"Block ";
	final private String SOLUTION_2 = "\" protocol=";
	final private String SOLUTION_3 = " dir=in localport=";
	final private String SOLUTION_4 = " action=block";
	private String name;
	private String state;
	private String service;
	private String warning;
	private String solution;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getState() {
		return this.state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getWarning() {
		return warning;
	}

	private void setWarning() {
		if (this.state == "open" && this.service == "unknown") {
			this.warning = BADWARNING;
		} else {
			this.warning = GOODWARNING;
		}
	}

	public String getSolution() {
		return solution;
	}

	private void setSolution() {
		if (this.warning == BADWARNING) {
			String[] tmp = this.name.split("/");
			this.solution = SOLUTION_1 + tmp[0] + SOLUTION_2 + tmp[1] + SOLUTION_3 + tmp[0] + SOLUTION_4;
		} else {
			this.solution = "none";
		}
	}

	public Port(String name, String state, String service) {
		super();
		this.name = name;
		this.state = state;
		this.service = service;
		this.setWarning();
		this.setSolution();
	}

	@Override
	public String toString() {
		return "Port [Name=" + this.name + ", State=" + this.state + ", Service="
				+ this.service + "]";
	}
}
