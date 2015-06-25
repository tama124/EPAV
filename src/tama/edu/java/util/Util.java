package tama.edu.java.util;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import tama.edu.java.runtime.SystemCommandExecutor;

public class Util {
	// create a pattern to check valid ip address
	private static final String IPV4_PATTERN = "^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";
	
	public static String getHomeDirectory() {
		return System.getProperty("user.home");
	}

	public Dimension getScreenSize() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		Double screenWidthDouble = screenSize.getWidth();
		Double screenHeightDouble = screenSize.getHeight();
		final int screenWidth = screenWidthDouble.intValue();
		final int screenHeight = screenHeightDouble.intValue();
		return new Dimension(screenWidth, screenHeight);
	}

	public boolean isOnline() {
		Socket sock = new Socket();
		InetSocketAddress addr = new InetSocketAddress("www.microsoft.com", 80);
		try {
			sock.connect(addr, 3000);
			return true;
		} catch (IOException e) {
			return false;
		} finally {
			try {
				sock.close();
			} catch (IOException e) {
			}
		}
	}

	public boolean checkValidIpAddress(String ip) {
		Pattern pattern = Pattern.compile(IPV4_PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	public boolean checkValidRootPass(String pass) {
		String stderr = "";
		try {
			ArrayList<String> commands = new ArrayList<String>();
			commands.add("/bin/sh");
			commands.add("-c");
			commands.add("echo " + pass + " | sudo -S blabla");

			SystemCommandExecutor commandExecutor = new SystemCommandExecutor(
					commands);
			commandExecutor.executeCommand();

			stderr = commandExecutor.getStandardErrorFromCommand() + "";
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (stderr.contains("Sorry, try again")) {
			return false;
		} else {
			return true;
		}
	}

	public String getRootPass(char[] pass) {
		String rootPass = new String();
		for (int i = 0; i < pass.length; i++) {
			rootPass += pass[i];
		}
		return rootPass;
	}
}
