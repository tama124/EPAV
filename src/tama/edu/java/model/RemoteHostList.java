package tama.edu.java.model;

import java.util.ArrayList;

public class RemoteHostList {
	private ArrayList<RemoteHost> listOfRemoteHost;

	public ArrayList<RemoteHost> getListOfRemoteHost() {
		return listOfRemoteHost;
	}

	public RemoteHostList() {
		listOfRemoteHost = new ArrayList<RemoteHost>();
	}

	public void add(RemoteHost remoteHost) {
		listOfRemoteHost.add(remoteHost);
	}
}
