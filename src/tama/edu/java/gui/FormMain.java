package tama.edu.java.gui;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import tama.edu.java.util.Util;

public class FormMain extends JFrame {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				FormMain formMain = new FormMain();
				formMain.setVisible(true);
			}
		});
	}

	private static final long serialVersionUID = 1L;

	private JLabel lblTitle;
	private JButton btnFeatureOne;
	private JButton btnFeatureTwo;
	private JButton btnUpdate;
	private JButton btnAbout;
	private JButton btnExit;

	private Process updateProcess = null;

	public FormMain() {
		// get screen size
		Dimension screenSize = new Util().getScreenSize();

		// set up standard button size of this frame
		Dimension buttonSize = new Dimension(400, 50);

		// set up frame
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("EPAV");
		this.setSize(600, 480);
		this.setLocation(screenSize.width / 2 - this.getWidth() / 2,
				screenSize.height / 2 - this.getHeight() / 2 - 50);
		this.setResizable(false);
		this.setLayout(null);

		// set up main title
		lblTitle = new JLabel("EPAV");
		lblTitle.setSize(60, 100);
		lblTitle.setLocation(this.getWidth() / 2 - lblTitle.getWidth() / 2, 0);
		lblTitle.setFont(new Font("Tahoma", 1, 20));
		this.add(lblTitle);

		// set up button feature one
		btnFeatureOne = new JButton("Vulnerabilities Scanner");
		btnFeatureOne.setSize(buttonSize);
		btnFeatureOne.setLocation(
				this.getWidth() / 2 - btnFeatureOne.getWidth() / 2, 100);
		btnFeatureOne.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormFeatureOne formFeatureOne = new FormFeatureOne();
				formFeatureOne.setVisible(true);
				dispose();
			}
		});
		this.add(btnFeatureOne);

		// set up button feature two
		btnFeatureTwo = new JButton("Insert Exploit");
		btnFeatureTwo.setSize(buttonSize);
		btnFeatureTwo.setLocation(
				this.getWidth() / 2 - btnFeatureTwo.getWidth() / 2, 160);
		btnFeatureTwo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormFeatureTwo formFeatureTwo = new FormFeatureTwo();
				formFeatureTwo.setVisible(true);
				dispose();
			}
		});
		this.add(btnFeatureTwo);

		// set up button update
		btnUpdate = new JButton("Update Patches");
		btnUpdate.setSize(buttonSize);
		btnUpdate.setLocation(this.getWidth() / 2 - btnUpdate.getWidth() / 2,
				220);
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (new Util().isOnline()) {
					try {
						JOptionPane.showMessageDialog(null,
								"The update will run in background!",
								"Informing", JOptionPane.INFORMATION_MESSAGE);
						String execute = "bash " + Util.getHomeDirectory() + "/EPAV/update/update_patches.bash";
						updateProcess = Runtime.getRuntime().exec(execute);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Something was wrong! Please try again later!",
								"Error", JOptionPane.ERROR_MESSAGE);
					}
				} else {
					JOptionPane
							.showMessageDialog(
									null,
									"This feature need your Internet connection! Please make sure you are now online!",
									"Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		this.add(btnUpdate);

		// set up button about
		btnAbout = new JButton("About");
		btnAbout.setSize(buttonSize);
		btnAbout.setLocation(this.getWidth() / 2 - btnAbout.getWidth() / 2, 280);
		btnAbout.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				FormAbout formAbout = new FormAbout();
				formAbout.setVisible(true);
				dispose();
			}
		});
		this.add(btnAbout);

		// set up button exit
		btnExit = new JButton("Exit");
		btnExit.setSize(buttonSize);
		btnExit.setLocation(this.getWidth() / 2 - btnExit.getWidth() / 2, 340);
		btnExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (updateProcess != null) {
					updateProcess.destroy();
				}
				dispose();
			}
		});
		this.add(btnExit);
	}
}
