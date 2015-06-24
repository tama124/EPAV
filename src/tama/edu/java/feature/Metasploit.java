package tama.edu.java.feature;

import java.util.ArrayList;

import tama.edu.java.io.IOStream;
import tama.edu.java.runtime.SystemCommandExecutor;

public class Metasploit {
	
	ArrayList<String> dataBase;
	
	public Metasploit() {
		dataBase = this.getDataBase();
	}
	
	private ArrayList<String> getDataBase() {
		IOStream ioStream = new IOStream();
		String fileContent = ioStream.readExistingFile("./src/main/edu/database/database.txt");
		String[] fileContentLines = fileContent.split("\n");
		ArrayList<String> dataBase = new ArrayList<String>();
		for (int i = 0; i < fileContentLines.length; i++) {
			dataBase.add(fileContentLines[i]);
		}
		return dataBase;
	}
	
	public String runMetasploit(String lhost, String rhost,
			String rootPass, String checkedVulName) throws Exception {
		
		String vulName = "";
		String vulScriptPath = "";
		String vulPath = "";
		String payload = "";
		
		// parse data from database
		for (String data : dataBase) {
			if (data.contains(checkedVulName.substring(0, checkedVulName.length()).replace(".sh", ""))) {
				String[] datas = data.split(";");
				vulName = checkedVulName.substring(0, checkedVulName.length()).replace(".sh", "");
				vulScriptPath = "./src/main/edu/script/" + checkedVulName;
				vulPath = datas[0];
				payload = datas[1];
				break;
			}
		}
		
		ArrayList<String> commands = new ArrayList<String>();
		commands.add("/bin/sh");
		commands.add("-c");
		commands.add("echo " + rootPass + " | sudo -S bash " + vulScriptPath + " "
				+ vulPath + " " + lhost + " " + rhost + " " + payload);
		
		// execute the command
		SystemCommandExecutor commandExecutor = new SystemCommandExecutor(commands);
		commandExecutor.executeCommand();

		// get the stdout from the command that was run
		String stdout = commandExecutor.getStandardOutputFromCommand() + "";

		return rhost + ": " + this.parseResults(vulName, stdout) + "\n" + stdout;
	}
	
	private String parseResults(String vulName, String stdout) {
		if(!stdout.contains("[-]")) {
			return vulName + " was detected!";
		} else {
			return vulName + " was fixed!";
		}
	}
}
