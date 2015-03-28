package ui.desktop;

import javax.swing.JDialog;
import javax.swing.JScrollPane;

import java.awt.BorderLayout;

import javax.swing.JList;
import javax.swing.AbstractListModel;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JTextField;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class settings extends JDialog {
	/**
	 * @wbp.nonvisual location=57,307
	 */
	private final JPanel panel1 = new JPanel();
	private final JPanel panel = new JPanel();
	private final JButton btnNewButton = new JButton("Submit");
	private final JButton btnNewButton_1 = new JButton("Cancel");
	private final JLabel lblNewLabel = new JLabel("Theshold");
	private final JTextField textField = new JTextField();

	public settings() {
		textField.setColumns(10);
		this.setSize(200, 300);

		JTabbedPane tabbedPane = new JTabbedPane(JTabbedPane.TOP);
		getContentPane().add(tabbedPane, BorderLayout.CENTER);

		tabbedPane.addTab("Theshold", panel1);

		panel1.add(lblNewLabel);

		panel1.add(textField);

		getContentPane().add(panel, BorderLayout.SOUTH);
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					Integer.valueOf(textField.getText());
					dispose();
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "Not a right number",
							"Wrong INPUT", JOptionPane.ERROR_MESSAGE);
				}

			}
		});

		panel.add(btnNewButton);
		btnNewButton_1.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				textField.setText("-2");
				dispose();
			}
		});

		panel.add(btnNewButton_1);
	}

	public String returnValues() {
		return textField.getText();
	}
}
