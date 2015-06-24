package tama.edu.java.model;

import java.util.ArrayList;

public class PortList {
	private ArrayList<Port> listOfPort;

	public ArrayList<Port> getListOfPort() {
		return listOfPort;
	}

	public PortList() {
		listOfPort = new ArrayList<Port>();
	}

	public void add(Port port) {
		listOfPort.add(port);
	}

	public String display() {
		String tmp = "[";
		for (Port p : listOfPort) {
			tmp += p;
		}
		tmp += "]";
		return tmp;
	}
}
