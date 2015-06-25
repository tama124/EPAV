package tama.edu.java.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

import tama.edu.java.model.PatchList;
import tama.edu.java.model.Port;
import tama.edu.java.model.RemoteHost;
import tama.edu.java.model.RemoteHostList;
import tama.edu.java.model.Vulnerability;
import tama.edu.java.util.Util;

import com.csvreader.CsvWriter;

public class IOStream {

	public String readExistingFile(String filepath) {
		String content = new String();
		try {
			Scanner sc = new Scanner(new FileInputStream(new File(filepath)));
			while (sc.hasNextLine()) {
				content += sc.nextLine();
				content += "\n";
			}
			sc.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return content;
	}

	public void writeNewFile(String filepath, String content) {
		PrintWriter pw;
		try {
			pw = new PrintWriter(new FileOutputStream(new File(filepath)));
			pw.write(content);
			pw.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void appendTextToExistingFile(String fileName, String content) {
		try {
			FileWriter fw = new FileWriter(fileName, true);
			fw.write(content);
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getPatchNameList() {
		final ArrayList<String> patchNameList = new ArrayList<String>();
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					File[] listOfPatches = new File(Util.getHomeDirectory() + "/EPAV/patch").listFiles();

					for (File file : listOfPatches) {
						if (file.isFile()) {
							patchNameList.add(file.getName());
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
		t.start();

		// wait thread finish then return
		try {
			t.join();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return patchNameList;
	}

	public void copyPatchesToHosts(String hostIp, PatchList patchList,
			String currentTime) {
		final String hostPath = "./host/" + currentTime + "_" + hostIp;
		File newDirectory = new File(hostPath);
		if (!newDirectory.exists()) {
			newDirectory.mkdirs();
		}
		for (int i = 0; i < patchList.getListOfPatch().size(); i++) {
			copyFile(Util.getHomeDirectory() + "/EPAV/patch/"
					+ patchList.getListOfPatch().get(i).getName(), hostPath
					+ "/" + patchList.getListOfPatch().get(i).getName());
		}
	}

	private void copyFile(String fromFile, String toFile) {
		if (!new File(toFile).exists()) {
			FileInputStream from = null;
			FileOutputStream to = null;
			try {
				from = new FileInputStream(fromFile);
				to = new FileOutputStream(toFile);
				byte[] buffer = new byte[1024];
				int bytesRead;

				while ((bytesRead = from.read(buffer)) != -1) {
					to.write(buffer, 0, bytesRead);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (from != null) {
					try {
						from.close();
					} catch (Exception e2) {
						e2.printStackTrace();
					}
				}
				if (to != null) {
					try {
						to.close();
					} catch (Exception e3) {
						e3.printStackTrace();
					}
				}
			}
		}
	}

	public void createCSVResultFile(RemoteHostList remoteHostList,
			String currentTime) {
		for (RemoteHost rh : remoteHostList.getListOfRemoteHost()) {
			String outpuCsvtFile = "./host/" + currentTime + "_" + rh.getIP()
					+ "/" + currentTime + "_" + rh.getIP() + "_EPAV_Result.csv";

			try {
				CsvWriter csvOutput = new CsvWriter(new FileWriter(
						outpuCsvtFile, true), ' ');

				csvOutput.write("Remote host infomation:");
				csvOutput.endRecord();
				csvOutput.endRecord();

				csvOutput.write("IP Address:");
				csvOutput.write(rh.getIP());
				csvOutput.endRecord();

				csvOutput.write("MAC Address:");
				csvOutput.write(rh.getMAC());
				csvOutput.endRecord();

				csvOutput.write("Operating system details:");
				csvOutput.write(rh.getOS());
				csvOutput.endRecord();
				csvOutput.endRecord();

				csvOutput.write("List of Ports:");
				csvOutput.endRecord();

				csvOutput.write("Name");
				csvOutput.write("State");
				csvOutput.write("Service");
				csvOutput.write("Warning");
				csvOutput.write("Solution");
				csvOutput.endRecord();

				for (Port p : rh.getPorts().getListOfPort()) {
					csvOutput.write(p.getName());
					csvOutput.write(p.getState());
					csvOutput.write(p.getService());
					csvOutput.write(p.getWarning());
					csvOutput.write(p.getSolution());
					csvOutput.endRecord();
				}
				csvOutput.endRecord();

				csvOutput.write("List of Vulnerabilities:");
				csvOutput.endRecord();

				csvOutput.write("Name");
				csvOutput.write("State");
				csvOutput.write("Patches");
				csvOutput.endRecord();

				for (Vulnerability v : rh.getVulnerabilities()
						.getListOfVulnerability()) {
					csvOutput.write(v.getName());
					csvOutput.write(v.getState());
					csvOutput.write(v.getPatchList().display());
					csvOutput.endRecord();
				}

				csvOutput.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
