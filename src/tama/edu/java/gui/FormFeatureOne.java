package tama.edu.java.gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JProgressBar;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTree;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.TreePath;

import tama.edu.java.feature.Metasploit;
import tama.edu.java.feature.Nmap;
import tama.edu.java.io.IOStream;
import tama.edu.java.model.Patch;
import tama.edu.java.model.PatchList;
import tama.edu.java.model.RemoteHost;
import tama.edu.java.model.RemoteHostList;
import tama.edu.java.model.Vulnerability;
import tama.edu.java.util.Util;

public class FormFeatureOne extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// components of this frame
	private JLabel lblTitle;
	
	private JLabel lblLocalHost;
	private JTextField txtLocalHost;
	
	private JLabel lblRemoteHost;
	private JTextField txtRemoteHost;
	
	private JFileChooser fileChooser;
	private JButton btnBrowser;
	
	private JLabel lblRootPass;
	private JPasswordField txtRootPass;
	
	private JRadioButton radiobtnAll;
	private JRadioButton radiobtnOptional;
	private ButtonGroup groupRadioButton;
	
	// flag to check radio button selection
	private boolean radioButtonFlag = true;
	// listener of radio button
	private ActionListener radioButtonListener;
	
	private JPanel panelVulSelection;
	private JScrollPane sclVulSelection;
	private CheckTreeManager checkTreeManager;
	private JTree treeVulSelection;
	
	private JProgressBar progressBar;
	
	// the current percent value of progress bar
	private int currentPercentValue = 0;
	// the percent value of each process in progress bar 
	private int eachProcessPercentValue = 0;
	// the old state text on progress bar
	private String oldProgressBarState = "";
	// the max and in percent value of progress bar
	private final int MIN_PERCENT_VALUE = 0;
	private final int MAX_PERCENT_VALUE = 100;
	
	private JButton btnRun;
	private JButton btnBack;
	
	// list of all vulnerabilities name in EPAV
	private ArrayList<String> listOfAllVulName;
	// list of all patches name in EPAV
	private ArrayList<String> listOfAllPatchName;
	
	public FormFeatureOne() {
		// get screen size
		Dimension screenSize = new Util().getScreenSize();
		
		listOfAllVulName = new ArrayList<String>();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("EPAV");
		this.setSize(900, 520);
		this.setLocation(screenSize.width / 2 - this.getWidth() / 2, screenSize.height / 2 - this.getHeight() / 2 - 50);
		this.setResizable(false);
		this.setLayout(null);

		lblTitle = new JLabel("Vulnerabilities Scanner");
		lblTitle.setSize(300, 100);
		lblTitle.setLocation(this.getWidth() / 2 - lblTitle.getWidth() / 2, 0);
		lblTitle.setFont(new Font("Tahoma", 1, 20));
		this.add(lblTitle);
		
		lblLocalHost = new JLabel("Local Host");
		lblLocalHost.setSize(140, 20);
		lblLocalHost.setLocation(460, 100);
		lblLocalHost.setFont(new Font("Tahoma", 1, 14));
		this.add(lblLocalHost);

		txtLocalHost = new JTextField();
		txtLocalHost.setSize(200, 20);
		txtLocalHost.setLocation(604, 100);
		this.add(txtLocalHost);
		this.addWindowListener(new WindowAdapter() {
			public void windowOpened(WindowEvent e) {
				txtLocalHost.requestFocus();
			}
		});

		lblRemoteHost = new JLabel("Remote Hosts");
		lblRemoteHost.setSize(140, 20);
		lblRemoteHost.setLocation(460, 160);
		lblRemoteHost.setFont(new Font("Tahoma", 1, 14));
		this.add(lblRemoteHost);

		txtRemoteHost = new JTextField();
		txtRemoteHost.setSize(200, 20);
		txtRemoteHost.setLocation(604, 160);
		this.add(txtRemoteHost);
		
		fileChooser = new JFileChooser();
		fileChooser.setAcceptAllFileFilterUsed(false);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setFileFilter(new FileNameExtensionFilter("Normal Text File (.txt)", "txt"));
		
		btnBrowser = new JButton("...");
		btnBrowser.setSize(30, 20);
		btnBrowser.setLocation(824, 160);
		btnBrowser.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				int returnVal = fileChooser.showOpenDialog(FormFeatureOne.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					txtRemoteHost.setText(file.getPath());
				}
			}
		});
		this.add(btnBrowser);
		
		lblRootPass = new JLabel("Root password");
		lblRootPass.setSize(140, 20);
		lblRootPass.setLocation(460, 220);
		lblRootPass.setFont(new Font("Tahoma", 1, 14));
		this.add(lblRootPass);

		txtRootPass = new JPasswordField();
		txtRootPass.setSize(200, 20);
		txtRootPass.setLocation(604, 220);
		this.add(txtRootPass);
		
		radioButtonListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (e.getActionCommand() == "1") {
					lockTreeView(panelVulSelection, false);
					radioButtonFlag = true;
				} else if (e.getActionCommand() == "2") {
					lockTreeView(panelVulSelection, true);
					radioButtonFlag = false;
				}
			}
		};
		
		radiobtnAll = new JRadioButton("Select all supported vulnerabilities");
		radiobtnAll.setSize(400, 20);
		radiobtnAll.setLocation(42, 100);
		radiobtnAll.setFont(new Font("Tahoma", 1, 14));
		radiobtnAll.setActionCommand("1");
		radiobtnAll.setSelected(true);
		radiobtnAll.addActionListener(radioButtonListener);
		this.add(radiobtnAll);

	    radiobtnOptional = new JRadioButton("Select some vulnerabilities in list below");
	    radiobtnOptional.setSize(400, 20);
	    radiobtnOptional.setLocation(42, 125);
	    radiobtnOptional.setFont(new Font("Tahoma", 1, 14));
	    radiobtnOptional.setActionCommand("2");
	    radiobtnOptional.addActionListener(radioButtonListener);
	    this.add(radiobtnOptional);
	    
	    groupRadioButton = new ButtonGroup();
	    groupRadioButton.add(radiobtnAll);
	    groupRadioButton.add(radiobtnOptional);
		
		checkTreeManager = new CheckTreeManager(treeVulSelection = new JTree(this.getVulNameListtoTreeView()));
		treeVulSelection.setEditable(false);

		sclVulSelection = new JScrollPane(treeVulSelection);
		
		panelVulSelection = new JPanel(new BorderLayout());
		panelVulSelection.setSize(380, 320);
		panelVulSelection.setLocation(42, 150);
		panelVulSelection.add(sclVulSelection);
		this.add(panelVulSelection);
		lockTreeView(panelVulSelection, false);
		
		progressBar = new JProgressBar(MIN_PERCENT_VALUE, MAX_PERCENT_VALUE);
		progressBar.setSize(394, 20);
		progressBar.setLocation(460, 320);
		progressBar.setStringPainted(true);
		updateProgressBarSmoothly("", MIN_PERCENT_VALUE);
		this.add(progressBar);

		btnRun = new JButton("Run");
		btnRun.setSize(150, 50);
		btnRun.setLocation(670, 420);
		btnRun.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
		    	final String currentTime = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date());
		    	Util util = new Util();
		    	
				oldProgressBarState = "Scanning your remote hosts.";
				lockComponent(false);
				
				String lhost = txtLocalHost.getText();
				String rhost = txtRemoteHost.getText();
				String rootPass = util.getRootPass(txtRootPass.getPassword());
				
				if (util.checkValidIpAddress(lhost) && (util.checkValidIpAddress(rhost) || rhost.contains("/"))) {
					if (util.checkValidRootPass(rootPass)) {
						try {
							// get list checked vulname from treeview
							ArrayList<String> listOfCheckedVulName = new ArrayList<String>();
							if (radioButtonFlag == true) {
								listOfCheckedVulName.add("root");
							} else {
								TreePath checkedPaths[] = checkTreeManager.getSelectionModel().getSelectionPaths();
								for (int i = 0; i < checkedPaths.length; i++) {
									listOfCheckedVulName.add(checkedPaths[i].getLastPathComponent().toString());
								}
							}
							if (listOfCheckedVulName.size() == 0) {
								showMessageErrorDialog("You must check at least one vulnerability!");
							} else {
								updateProgressBarSmoothly("Scanning your remote hosts", MIN_PERCENT_VALUE);
								execute(lhost, rhost, rootPass, listOfCheckedVulName, currentTime);
							}
						} catch (Exception e) {
							showMessageErrorDialog("You must check at least one vulnerability!");
						}
					} else {
						showMessageErrorDialog("So sorry! The password is incorrect. Please try again!");
					}
				} else {
					showMessageErrorDialog("You must enter a valid IP address!");
				}
			}
		});
		this.add(btnRun);

		btnBack = new JButton("Back");
		btnBack.setSize(150, 50);
		btnBack.setLocation(480, 420);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormMain formMain = new FormMain();
				formMain.setVisible(true);
				dispose();
			}
		});
		this.add(btnBack);
	}
	
	private Vector<String> getVulNameListtoTreeView() {
		final Vector<String> rootNode = new Vector<String>();
		
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					File[] listOfVulScripts = new File("./src/main/edu/script").listFiles();
					
					for (File file : listOfVulScripts) {
						if (file.isFile() && file.getName().toLowerCase().endsWith(".sh")) {
							rootNode.add(file.getName());
							listOfAllVulName.add(file.getName());
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
		
		return rootNode;
	}
	
	private void execute(final String lhost, final String rhost, final String rootPass,
			final ArrayList<String> listOfCheckedVulName, final String currentTime) {
		Thread t = new Thread(new Runnable() {
			public void run() {
				try {
					IOStream ioStream = new IOStream();
					int tmpPercentValue = 0;
					final RemoteHostList remoteHostList = new Nmap().scanNmap(rootPass, rhost);
					if (remoteHostList == null) {
						showMessageErrorDialog("Something was wrong! Please try again!");
					} else {
						for (RemoteHost h : remoteHostList.getListOfRemoteHost()) {
							ArrayList<String> listFilter;
							if (listOfCheckedVulName.get(0) == "root") {
								listFilter = listOfAllVulName;
							} else {
								listFilter = listOfCheckedVulName;
							}
							listOfAllPatchName = ioStream.getPatchNameList();

							int totalProcess = (remoteHostList.getListOfRemoteHost().size() * (listFilter.size() + 1)); // +1 process nmap
							eachProcessPercentValue = MAX_PERCENT_VALUE / totalProcess;
							tmpPercentValue = MAX_PERCENT_VALUE - (eachProcessPercentValue * totalProcess);
							updateProgressBarSmoothly("Exploiting " + h.getIP() + ": " + listFilter.get(0).substring(0, 8).replace(".sh", ""), eachProcessPercentValue);
							
							for (int i = 0; i < listFilter.size(); i++) {
								h.getVulnerabilities().add(new Vulnerability(listFilter.get(i).substring(0, listFilter.get(i).length()).replace(".sh", "")));
								
								String metasploitResult = new Metasploit().runMetasploit(lhost, h.getIP(), rootPass, listFilter.get(i));
								
								if ((i + 1) == listFilter.size()) {
									updateProgressBarSmoothly("Exploiting " + h.getIP() + ": " + listFilter.get(i).substring(0, 8).replace(".sh", ""), eachProcessPercentValue);
								} else {
									updateProgressBarSmoothly("Exploiting " + h.getIP() + ": " + listFilter.get(i + 1).substring(0, 8).replace(".sh", ""), eachProcessPercentValue);
								}

								PatchList patchList = new PatchList();
								if(metasploitResult.contains("was detected")) {
									h.getVulnerabilities().getListOfVulnerability().get(i).setState("Detected");
									for (int j = 0; j < listOfAllPatchName.size(); j++) {
										if (h.getVulnerabilities().getListOfVulnerability().get(i).getName().toLowerCase().substring(0, 8).equals("ms09_050")) {
											patchList.add(new Patch("MS09_050_WindowsEnable.msi"));
											patchList.add(new Patch("MS09_050_WindowsDisable.msi"));
											break;
										}
										if (listOfAllPatchName.get(j).toLowerCase().contains(h.getVulnerabilities().getListOfVulnerability().get(i).getName().toLowerCase().substring(0, 8)) && h.getOS().toLowerCase().contains(listOfAllPatchName.get(j).toLowerCase().substring(16, listOfAllPatchName.get(j).length() - 4))) {
											patchList.add(new Patch(listOfAllPatchName.get(j)));
										}
									}
									h.getVulnerabilities().getListOfVulnerability().get(i).setPatchList(patchList);
									ioStream.copyPatchesToHosts(h.getIP(), patchList, currentTime);
								} else {
									h.getVulnerabilities().getListOfVulnerability().get(i).setState("Fixed");
									patchList.add(new Patch("none"));
									h.getVulnerabilities().getListOfVulnerability().get(i).setPatchList(patchList);
								}
							}
						}
						updateProgressBarSmoothly("Finishing", tmpPercentValue);
						lockComponent(true);
						radiobtnAll.setSelected(true);
						radioButtonFlag = true;
						javax.swing.SwingUtilities.invokeLater(new Runnable() {
							public void run() {
								FormResult formResult = new FormResult(remoteHostList, currentTime);
								formResult.setVisible(true);
							}
						});
					}
				} catch (Exception e) {
					showMessageErrorDialog("Something was wrong! Please try again!");
				}
			}
		});
		t.start();
	}
	
	private void updateProgressBarSmoothly(String currentProgressBarState, int newPercentValue) {
		if (newPercentValue == MIN_PERCENT_VALUE && !currentProgressBarState.equals("Finishing")) {
			if (currentProgressBarState == "") {
				progressBar.setValue(newPercentValue);
				progressBar.setString("");
				currentPercentValue = 0;
			} else {
				progressBar.setValue(newPercentValue);
				progressBar.setString("Running " + newPercentValue + " % - " + currentProgressBarState);
				currentPercentValue = 0;
			}
		} else {
			for (int i = currentPercentValue + 1; i <= currentPercentValue + newPercentValue; i++) {
				progressBar.setValue(i);
				progressBar.setString("Running " + i + " % - " + oldProgressBarState);
				try {
					Thread.sleep(30);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			oldProgressBarState = currentProgressBarState;
			currentPercentValue += newPercentValue;
			progressBar.setString("Running " + currentPercentValue + " % - " + currentProgressBarState);
			if (currentPercentValue >= MAX_PERCENT_VALUE) {
				progressBar.setString("Completed");
			}
		}
	}
	
	private void showMessageErrorDialog(String error) {
		lockComponent(true);
		lockTreeView(panelVulSelection, false);
		radiobtnAll.setSelected(true);
		updateProgressBarSmoothly("Error", currentPercentValue);
		JOptionPane.showMessageDialog(null, error, "Error",	JOptionPane.ERROR_MESSAGE);
	}
	
	private void lockComponent(boolean lock) {
		txtLocalHost.setEnabled(lock);
		txtRemoteHost.setEnabled(lock);
		txtRootPass.setEnabled(lock);
		treeVulSelection.setEnabled(lock);
		btnBrowser.setEnabled(lock);
		btnRun.setEnabled(lock);
	}
	
	private void lockTreeView(Container container, boolean lock) {
        Component[] components = container.getComponents();
        for (Component component : components) {
            component.setEnabled(lock);
            if (component instanceof Container) {
            	lockTreeView((Container)component, lock);
            }
        }
    }
}