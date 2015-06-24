package tama.edu.java.gui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

public class FormAbout extends JFrame {

	private static final long serialVersionUID = 1L;
	private JLabel lblAbout1;
	private JTextArea lblAbout2;
	private JButton btnOK;

	public FormAbout() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("About EPAV");
		this.setSize(400, 300);
		this.setLocation(500, 100);
		this.setResizable(false);
		this.setLayout(null);

		lblAbout1 = new JLabel("EPAV version 3.0");
		lblAbout1.setLocation(50, 50);
		lblAbout1.setSize(300, 50);
		lblAbout1.setFont(new Font("Tahoma", 1, 18));
		this.add(lblAbout1);

		lblAbout2 = new JTextArea("Written by Huỳnh Bửu Bửu.");
		lblAbout2.setLocation(50, 100);
		lblAbout2.setSize(300, 80);
		lblAbout2.setWrapStyleWord(true);
		lblAbout2.setLineWrap(true);
		lblAbout2.setEditable(false);
		lblAbout2.setFont(new Font("Tahoma", 0, 14));
		this.add(lblAbout2);

		btnOK = new JButton("OK");
		btnOK.setLocation(150, 200);
		btnOK.setSize(100, 50);
		btnOK.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormMain formMain = new FormMain();
				formMain.setVisible(true);
				dispose();
			}
		});
		this.add(btnOK);
	}
}
