package ui.desktop;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;

import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JList;
import javax.swing.AbstractListModel;

import java.awt.Font;

import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

import org.eclipse.jdt.core.dom.ThisExpression;

import storage.PatentNodeStorage;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class addFrame extends JDialog {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_2;
	public JComboBox comboBox = new JComboBox();

	public addFrame(JFrame frmClassifier, boolean b) {
		super(frmClassifier, b);
		this.setResizable(false);
		setTitle("Add Patent");
		this.setSize(301, 340);
		JPanel panel = new JPanel();
		panel.setLayout(new BorderLayout(0, 0));

		JLabel lblNewLabel = new JLabel("US NO.");
		lblNewLabel.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));

		textField = new JTextField();
		textField.setColumns(10);

		JLabel lblNewLabel_1 = new JLabel("Patent\r\nName");
		lblNewLabel_1.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));

		textField_1 = new JTextField();
		textField_1.setColumns(10);

		JLabel lblNewLabel_2 = new JLabel("Abstract");
		lblNewLabel_2.setFont(new Font("Microsoft YaHei UI", Font.PLAIN, 12));

		textField_2 = new JTextField();
		textField_2.setColumns(10);

		JLabel lblNewLabel_3 = new JLabel("Category");
		lblNewLabel_3.setFont(new Font("微软雅黑", Font.PLAIN, 12));

		JButton btnNewButton = new JButton("Submit");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if (textField.getText().equals("")
						|| textField_1.getText().equals("")
						|| textField_2.getText().equals("")) {
					JOptionPane.showMessageDialog(null, "Not allowed Amount",
							"Wrong INPUT", JOptionPane.ERROR_MESSAGE);
				} else {
					dispose();
				}
			}
		});

		JButton btnCancel = new JButton("Cancel");
		btnCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				textField.setText("");
				dispose();
			}
		});
		GroupLayout groupLayout = new GroupLayout(getContentPane());
		groupLayout
				.setHorizontalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																panel,
																GroupLayout.PREFERRED_SIZE,
																334,
																GroupLayout.PREFERRED_SIZE)
														.addGroup(
																groupLayout
																		.createSequentialGroup()
																		.addContainerGap()
																		.addGroup(
																				groupLayout
																						.createParallelGroup(
																								Alignment.LEADING)
																						.addComponent(
																								lblNewLabel_3)
																						.addGroup(
																								groupLayout
																										.createParallelGroup(
																												Alignment.TRAILING)
																										.addGroup(
																												Alignment.LEADING,
																												groupLayout
																														.createSequentialGroup()
																														.addGroup(
																																groupLayout
																																		.createParallelGroup(
																																				Alignment.LEADING)
																																		.addComponent(
																																				lblNewLabel_1)
																																		.addComponent(
																																				lblNewLabel)
																																		.addComponent(
																																				lblNewLabel_2))
																														.addGap(18)
																														.addGroup(
																																groupLayout
																																		.createParallelGroup(
																																				Alignment.LEADING,
																																				false)
																																		.addGroup(
																																				groupLayout
																																						.createSequentialGroup()
																																						.addGap(10)
																																						.addComponent(
																																								btnNewButton)
																																						.addGap(18)
																																						.addComponent(
																																								btnCancel))
																																		.addComponent(
																																				textField,
																																				180,
																																				180,
																																				Short.MAX_VALUE)
																																		.addComponent(
																																				textField_2)
																																		.addComponent(
																																				textField_1)))
																										.addComponent(
																												comboBox,
																												Alignment.LEADING,
																												GroupLayout.PREFERRED_SIZE,
																												270,
																												GroupLayout.PREFERRED_SIZE)))))
										.addGap(43)));
		groupLayout
				.setVerticalGroup(groupLayout
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								groupLayout
										.createSequentialGroup()
										.addComponent(panel,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																lblNewLabel)
														.addComponent(
																textField,
																GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE))
										.addGap(18)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblNewLabel_1)
														.addComponent(
																textField_1,
																GroupLayout.PREFERRED_SIZE,
																51,
																GroupLayout.PREFERRED_SIZE))
										.addGap(15)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.LEADING)
														.addComponent(
																lblNewLabel_2)
														.addComponent(
																textField_2,
																GroupLayout.PREFERRED_SIZE,
																92,
																GroupLayout.PREFERRED_SIZE))
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(lblNewLabel_3)
										.addPreferredGap(
												ComponentPlacement.RELATED)
										.addComponent(comboBox,
												GroupLayout.PREFERRED_SIZE,
												GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addGap(18)
										.addGroup(
												groupLayout
														.createParallelGroup(
																Alignment.BASELINE)
														.addComponent(
																btnNewButton)
														.addComponent(btnCancel))
										.addGap(14)));
		getContentPane().setLayout(groupLayout);
	}

	public PatentNodeStorage returnPatent() {
		try {
			String tmpStr = comboBox.getSelectedItem().toString();
			String[] tmp2 = tmpStr.split(":");
			PatentNodeStorage tmp = new PatentNodeStorage(textField.getText(),
					textField_1.getText(), textField_2.getText(),
					Integer.valueOf(tmp2[0]));
			System.out.println(textField.getText() + textField_1.getText()
					+ textField_2.getText() + Integer.valueOf(tmp2[0]));
			if (textField.getText().equals(""))
				return null;
			return tmp;
		} catch (Exception exception) {
			return null;
		}

	}
}
