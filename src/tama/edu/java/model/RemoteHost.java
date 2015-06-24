package tama.edu.java.model;


public class RemoteHost {
	private String iP;
	private String mAC;
	private String oS;
	private PortList ports;
	private VulnerabilityList vulnerabilities;

	public String getIP() {
		return iP;
	}

	public void setIP(String iP) {
		this.iP = iP;
	}

	public String getMAC() {
		return mAC;
	}

	public void setMAC(String mAC) {
		this.mAC = mAC;
	}

	public String getOS() {
		return oS;
	}

	public void setOS(String oS) {
		this.oS = oS;
	}

	public PortList getPorts() {
		return ports;
	}

	public VulnerabilityList getVulnerabilities() {
		return vulnerabilities;
	}

	public RemoteHost(String iP, String mAC, String oS, PortList ports) {
		super();
		this.iP = iP;
		this.mAC = mAC;
		this.oS = oS;
		this.ports = ports;
		this.vulnerabilities = new VulnerabilityList();
	}

	@Override
	public String toString() {
		return "Host [IP=" + this.iP + ", MAC=" + this.mAC + ", OS=" + this.oS + ", Ports="
				+ this.ports.display() + ", Vulnerabilities =" + this.vulnerabilities.display() + "]";
	}
}
