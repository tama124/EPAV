package tama.edu.java.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import tama.edu.java.feature.InsertExploit;
import tama.edu.java.util.Util;

public class FormFeatureTwo extends JFrame {
	private static final long serialVersionUID = 1L;
	
	private JLabel lblTitle;
	
	private JLabel lblPathExploit;
	private JTextField txtPathExploit;

	private JLabel lblLocalPort;
	private JTextField txtLocalPort;

	private JLabel lblRemotePort;
	private JTextField txtRemotePort;

	private JLabel lblTarget;
	private JTextField txtTarget;

	private JLabel lblPayload;
	private JComboBox<String> comboPayload;

	private JButton btnInsert;
	private JButton btnBack;
	
	private String pathExploit = "";
	private String payload = "";
	private String lport = "";
	private String rport = "";
	private String target = "";

	public FormFeatureTwo() {
		// get screen size
		Dimension screenSize = new Util().getScreenSize();
		
		// setup this form
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("EPAV");
		this.setSize(600, 480);
		this.setLocation(screenSize.width / 2 - this.getWidth() / 2, screenSize.height / 2 - this.getHeight() / 2 - 50);
		this.setResizable(false);
		this.setLayout(null);
		
		lblTitle = new JLabel("Insert Exploit");
		lblTitle.setSize(200, 100);
		lblTitle.setLocation(this.getWidth() / 2 - lblTitle.getWidth() / 2, 0);
		lblTitle.setFont(new Font("Tahoma", 1, 20));
		this.add(lblTitle);

		lblPathExploit = new JLabel("Enter Path Exploit:");
		lblPathExploit.setSize(270, 20);
		lblPathExploit.setLocation(60, 100);
		lblPathExploit.setFont(new Font("Tahoma", 1, 14));
		this.add(lblPathExploit);

		txtPathExploit = new JTextField();
		txtPathExploit.setSize(310, 20);
		txtPathExploit.setLocation(230, 100);
		this.add(txtPathExploit);

		lblPayload = new JLabel("Enter Target:");
		lblPayload.setSize(270, 20);
		lblPayload.setLocation(60, 300);
		lblPayload.setFont(new Font("Tahoma", 1, 14));
		this.add(lblPayload);

		comboPayload = new JComboBox<String>();
		comboPayload.setSize(310, 20);
		comboPayload.setLocation(230, 150);
		comboPayload.addItem("windows/meterpreter/reverse_tcp");
		comboPayload.addItem("windows/meterpreter/bind_tcp");
		comboPayload.addItem("windows/shell/reverse_tcp");
		comboPayload.addItem("windows/shell/bind_tcp");
		comboPayload.addItem("windows/vncinject/reverse_tcp");
		comboPayload.addItem("windows/vncinject/bind_tcp");
		this.add(comboPayload);

		lblLocalPort = new JLabel("Enter Payload:");
		lblLocalPort.setSize(270, 20);
		lblLocalPort.setLocation(60, 150);
		lblLocalPort.setFont(new Font("Tahoma", 1, 14));
		this.add(lblLocalPort);

		txtLocalPort = new JTextField();
		txtLocalPort.setSize(310, 20);
		txtLocalPort.setLocation(230, 200);
		this.add(txtLocalPort);

		lblRemotePort = new JLabel("Enter LPort:");
		lblRemotePort.setSize(270, 20);
		lblRemotePort.setLocation(60, 200);
		lblRemotePort.setFont(new Font("Tahoma", 1, 14));
		this.add(lblRemotePort);

		txtRemotePort = new JTextField();
		txtRemotePort.setSize(310, 20);
		txtRemotePort.setLocation(230, 250);
		this.add(txtRemotePort);

		lblTarget = new JLabel("Enter RPort:");
		lblTarget.setSize(270, 20);
		lblTarget.setLocation(60, 250);
		lblTarget.setFont(new Font("Tahoma", 1, 14));
		this.add(lblTarget);

		txtTarget = new JTextField();
		txtTarget.setSize(310, 20);
		txtTarget.setLocation(230, 300);
		this.add(txtTarget);

		btnBack = new JButton("Back");
		btnBack.setSize(150, 50);
		btnBack.setLocation(110, 380);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormMain formMain = new FormMain();
				formMain.setVisible(true);
				dispose();
			}
		});
		this.add(btnBack);

		btnInsert = new JButton("Insert");
		btnInsert.setSize(150, 50);
		btnInsert.setLocation(300, 380);
		btnInsert.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					pathExploit = txtPathExploit.getText();
					payload = comboPayload.getSelectedItem().toString();
					lport = txtLocalPort.getText();
					rport = txtRemotePort.getText();
					target = txtTarget.getText();
					new InsertExploit().insertExploit(pathExploit, payload, lport, rport, target);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} finally {
					JOptionPane.showMessageDialog(null, "Insert exploit successful!", "Informing", JOptionPane.INFORMATION_MESSAGE);
				}
			}
		});
		this.add(btnInsert);
	}
}
