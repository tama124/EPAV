package tama.edu.java.gui;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.table.JTableHeader;

import tama.edu.java.io.IOStream;
import tama.edu.java.model.RemoteHostList;

public class FormResult extends JFrame {

	private static final long serialVersionUID = 1L;

	private JTabbedPane tabbedPane;
	private Container[] containerHostDetailsList;

	private JLabel hostInfomation;
	private JLabel hostIP;
	private JLabel hostMAC;
	private JLabel hostOS;
	private JLabel hostPortsList;
	private JLabel hostVulsList;

	private JTable[] tablePortsList;
	private String[][] rowPortDatas;
	final private String columnNamesTablePortsList[] = { "Name", "State",
			"Service", "Warning", "Solution" };

	private JTable[] tableVulsList;
	private String[][] rowVulDatas;
	final private String columnNamesTableVulsList[] = { "Name", "State",
			"Patches" };

	private JButton btnClose;
	private JButton btnExportCsv;

	final private RemoteHostList remoteHostList;
	final private int numberOfHosts;

	public FormResult(final RemoteHostList remoteHostList,
			final String currentTime) {
		this.remoteHostList = remoteHostList;
		this.numberOfHosts = this.remoteHostList.getListOfRemoteHost().size();

		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setTitle("Your Result");
		this.setSize(900, 500);
		this.setLocation(260, 100);
		this.setResizable(false);
		this.setLayout(null);

		tabbedPane = new JTabbedPane(JTabbedPane.LEFT,
				JTabbedPane.SCROLL_TAB_LAYOUT);
		tabbedPane.setSize(900, 400);
		tabbedPane.setLocation(0, 0);
		this.add(tabbedPane);

		containerHostDetailsList = new JPanel[this.numberOfHosts];
		tablePortsList = new JTable[this.numberOfHosts];
		tableVulsList = new JTable[this.numberOfHosts];

		for (int i = 0; i < this.numberOfHosts; i++) {
			// get remote host infomation
			hostInfomation = new JLabel("Remote host infomation:");
			hostInfomation.setFont(new Font("Tahoma", 1, 14));
			hostIP = new JLabel("IP address: "
					+ this.remoteHostList.getListOfRemoteHost().get(i).getIP());
			hostMAC = new JLabel("MAC address: "
					+ this.remoteHostList.getListOfRemoteHost().get(i).getMAC());
			hostOS = new JLabel("Operating system details: "
					+ this.remoteHostList.getListOfRemoteHost().get(i).getOS());

			// get remote host ports list
			hostPortsList = new JLabel("Ports:");
			int numberOfPorts = this.remoteHostList.getListOfRemoteHost()
					.get(i).getPorts().getListOfPort().size();
			rowPortDatas = new String[numberOfPorts][5];
			for (int j = 0; j < numberOfPorts; j++) {
				rowPortDatas[j][0] = remoteHostList.getListOfRemoteHost()
						.get(i).getPorts().getListOfPort().get(j).getName();
				rowPortDatas[j][1] = remoteHostList.getListOfRemoteHost()
						.get(i).getPorts().getListOfPort().get(j).getState();
				rowPortDatas[j][2] = remoteHostList.getListOfRemoteHost()
						.get(i).getPorts().getListOfPort().get(j).getService();
				rowPortDatas[j][3] = remoteHostList.getListOfRemoteHost()
						.get(i).getPorts().getListOfPort().get(j).getWarning();
				rowPortDatas[j][4] = remoteHostList.getListOfRemoteHost()
						.get(i).getPorts().getListOfPort().get(j).getSolution();
			}
			tablePortsList[i] = new JTable(rowPortDatas,
					columnNamesTablePortsList);
			JTableHeader tableHeaderPortsList = tablePortsList[i]
					.getTableHeader();

			// get remote host vulnerabilities list
			hostVulsList = new JLabel("Scanned vulnerabilities:");
			int numberOfVuls = this.remoteHostList.getListOfRemoteHost().get(i)
					.getVulnerabilities().getListOfVulnerability().size();
			rowVulDatas = new String[numberOfVuls][3];
			for (int j = 0; j < numberOfVuls; j++) {
				rowVulDatas[j][0] = remoteHostList.getListOfRemoteHost().get(i)
						.getVulnerabilities().getListOfVulnerability().get(i)
						.getName();
				rowVulDatas[j][1] = remoteHostList.getListOfRemoteHost().get(i)
						.getVulnerabilities().getListOfVulnerability().get(i)
						.getState();
				rowVulDatas[j][2] = remoteHostList.getListOfRemoteHost().get(i)
						.getVulnerabilities().getListOfVulnerability().get(i)
						.getPatchList().display();
			}
			tableVulsList[i] = new JTable(rowVulDatas, columnNamesTableVulsList);
			JTableHeader tableHeaderVulsList = tableVulsList[i]
					.getTableHeader();

			// get component to container tab
			containerHostDetailsList[i] = new JPanel(new BorderLayout());
			containerHostDetailsList[i].setLayout(new GridLayout(10, 1));

			containerHostDetailsList[i].add(hostInfomation);
			containerHostDetailsList[i].add(new JLabel(""));

			containerHostDetailsList[i].add(hostIP);
			containerHostDetailsList[i].add(new JLabel(""));

			containerHostDetailsList[i].add(hostMAC);
			containerHostDetailsList[i].add(new JLabel(""));

			containerHostDetailsList[i].add(hostOS);
			containerHostDetailsList[i].add(new JLabel(""));

			containerHostDetailsList[i].add(hostPortsList);
			containerHostDetailsList[i].add(new JLabel(""));

			containerHostDetailsList[i].add(tableHeaderPortsList);
			containerHostDetailsList[i].add(new JLabel(""));

			containerHostDetailsList[i].add(tablePortsList[i]);
			containerHostDetailsList[i].add(new JLabel(""));

			containerHostDetailsList[i].add(hostVulsList);
			containerHostDetailsList[i].add(new JLabel(""));

			containerHostDetailsList[i].add(tableHeaderVulsList);
			containerHostDetailsList[i].add(new JLabel(""));

			containerHostDetailsList[i].add(tableVulsList[i]);

			tabbedPane.add(new JScrollPane(containerHostDetailsList[i],
					JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
					JScrollPane.HORIZONTAL_SCROLLBAR_NEVER),
					this.remoteHostList.getListOfRemoteHost().get(i).getIP());

			btnExportCsv = new JButton("Export to csv");
			btnExportCsv.setSize(150, 50);
			btnExportCsv.setLocation(500, 420);
			btnExportCsv.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					try {
						new IOStream().createCSVResultFile(remoteHostList,
								currentTime);
						JOptionPane.showMessageDialog(null,
								"Exporting successfully!", "Informing",
								JOptionPane.INFORMATION_MESSAGE);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Something was wrong! Please try again later!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				}
			});
			this.add(btnExportCsv);

			btnClose = new JButton("Close");
			btnClose.setSize(150, 50);
			btnClose.setLocation(300, 420);
			btnClose.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					dispose();
				}
			});
			this.add(btnClose);
		}
	}
}