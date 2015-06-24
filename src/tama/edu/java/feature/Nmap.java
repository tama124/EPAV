package tama.edu.java.feature;

import java.util.ArrayList;

import tama.edu.java.model.Port;
import tama.edu.java.model.PortList;
import tama.edu.java.model.RemoteHost;
import tama.edu.java.model.RemoteHostList;
import tama.edu.java.runtime.SystemCommandExecutor;

public class Nmap {

	public RemoteHostList scanNmap(String rootPass, String rhost) throws Exception {
		// add command
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("/bin/sh");
		commands.add("-c");
		if (rhost.contains("/")) {
			commands.add("echo " + rootPass + " | sudo -S nmap -sS -O -iL " + rhost);
		} else {
			commands.add("echo " + rootPass + " | sudo -S nmap -sS -O " + rhost);
		}

		// execute the command
		SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
		commandExecutor.executeCommand();

		// get the stdout from the command that was run
		String stdout = commandExecutor.getStandardOutputFromCommand() + "";

		return this.parseResults(stdout);
	}

	private RemoteHostList parseResults(String stdout) {
		// create a host list
		RemoteHostList hostList = new RemoteHostList();
		
		// get nmap command results
		String[] stdoutEachHost = stdout.split("Nmap scan report for ");
		
		// get each host information
		for (int i = 1; i < stdoutEachHost.length; i++) {
			RemoteHost rhost = this.getRemoteHostInfo(stdoutEachHost[i]);
			if (rhost != null) {
				hostList.add(rhost);
			} else {
				return null;
			}
		}
		
		return hostList;
	}

	private RemoteHost getRemoteHostInfo(String stdoutEachHost) {
		try {
			// get each line of result
			String[] stdoutLines = stdoutEachHost.split("\n");
			
			// variables contain remote host information
			String IP = stdoutLines[0];
			String MAC = null;
			String OS = null;
			PortList Ports = new PortList();
	
			// get MAC
			for (int i = 0; i < stdoutLines.length; i++) {
				if (stdoutLines[i].contains("MAC Address:")) {
					MAC = stdoutLines[i];
					break;
				}
			}
			MAC = MAC.substring(13, MAC.length() - 9);
	
			// get OS
			for (int i = 0; i < stdoutLines.length; i++) {
				if (stdoutLines[i].contains("OS details:")) {
					OS = stdoutLines[i];
					break;
				}
			}
			OS = OS.substring(12, OS.length());
	
			// get port
			for (int i = 0; i < stdoutLines.length; i++) {
				if (stdoutLines[i].contains("PORT")) {
					int j = 1;
					while (true) {
						if (stdoutLines[i + j].contains("MAC Address:")) {
							break;
						} else {
							String[] portProperties = stdoutLines[i + j].trim().replaceAll(" +", " ").split(" ");
							Ports.add(new Port(portProperties[0], portProperties[1], portProperties[2]));
						}
						j++;
					}
					break;
				}
			}
			
			// create a host
			RemoteHost host = new RemoteHost(IP, MAC, OS, Ports);
			
			return host;
		} catch (Exception e) {
			return null;
		}
	}
}
