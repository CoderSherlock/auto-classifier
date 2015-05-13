package ui.desktop;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextPane;
import javax.swing.JTree;
import javax.swing.ListSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import storage.CategoryNodeStorage;
import storage.PatentNodeStorage;
import core.VectorFactory;

public class main {

	private JFrame frmClassifier;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					main window = new main();
					window.frmClassifier.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 * 
	 * @throws Exception
	 */
	public main() throws Exception {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 * 
	 * @throws Exception
	 */
	@SuppressWarnings("static-access")
	private void initialize() throws Exception {
		/*
		 * Logic Method
		 */
		final VectorFactory v = new VectorFactory();
		/*
		 * End of Logic Domain
		 */
		frmClassifier = new JFrame();
		frmClassifier.setTitle("Classifier");
		frmClassifier.setResizable(false);
		frmClassifier.setBounds(100, 100, 800, 480);
		Dimension winSize = Toolkit.getDefaultToolkit().getScreenSize(); // 屏幕分辨率
		frmClassifier.setLocation(
				(winSize.width - frmClassifier.getWidth()) / 2,
				(winSize.height - frmClassifier.getHeight()) / 2);
		frmClassifier.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		final JTree tree = new JTree();
		final JTable table = new JTable();
		final JTextPane text = new JTextPane();
		final JScrollPane tablesp = new JScrollPane();

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				int id = (Integer) table.getModel().getValueAt(row, 0);
				PatentNodeStorage tmp = v.patentStorage.Patents.get(id);
				text.setText("ID:\t" + String.valueOf(id) + "\nNO.:\t" + tmp.no
						+ "\nName:\t" + tmp.name + "\nCategory:\t"
						+ v.categoryStorage.StringAt(tmp.category)
						+ "\nAbstruct:\t\n" + tmp.abstruct);
			}
		});

		tree.addTreeSelectionListener(new TreeSelectionListener() {
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree
						.getLastSelectedPathComponent();
				String cateName = "-2:FuckStr";
				try {
					cateName = (String) node.getUserObject();
				} catch (Exception exception) {
					System.out.println("error1");
				}
				String cateString[] = cateName.split(":");
				int cate = -2;

				try {
					cate = Integer.valueOf(cateString[0]);
				} catch (Exception exception) {
					System.out.println("error2");
				}
				System.out.println(cate);

				DefaultTableModel table1 = new DefaultTableModel(null,
						new String[] { "ID", "SPNO", "NAME", "IsMatched" });
				if (cate > -1) {

					for (Integer patent : v.categoryStorage.index.get(cate).patents) {
						PatentNodeStorage tmpp = v.patentStorage.Patents
								.get(patent);
						table1.addRow(new Object[] { tmpp.id, tmpp.no,
								tmpp.name, tmpp.isMatched });
					}

				} else if (cate == -1) {
					for (int i = 0, j = v.patentStorage.Patents.size(); i < j; i++) {
						PatentNodeStorage tmpp = v.patentStorage.Patents.get(i);
						if (tmpp.category == -1) {
							table1.addRow(new Object[] { tmpp.id, tmpp.no,
									tmpp.name, tmpp.isMatched });
						}
					}
				}
				table.removeAll();
				table.setModel(table1);
				table.getColumnModel().getColumn(0).setPreferredWidth(35);
				table.getColumnModel().getColumn(1).setPreferredWidth(110);
				table.getColumnModel().getColumn(2)
						.setPreferredWidth(tablesp.getWidth() - 195);
				table.getColumnModel().getColumn(3).setPreferredWidth(50);
			}
		});
		tree.removeAll();
		tree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("NULL")));

		JMenuBar menuBar = new JMenuBar();
		menuBar.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		frmClassifier.setJMenuBar(menuBar);

		JMenu mnFile = new JMenu("File ");
		mnFile.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		menuBar.add(mnFile);

		JMenuItem mntmOpenProject = new JMenuItem("Open Project");
		mntmOpenProject.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		mntmOpenProject.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// System.out.println("Fcuk");
				try {
					v.readFiles();
				} catch (Exception ex) {
					// System.out.println("Fcuk");
				}
				DefaultMutableTreeNode tree1 = new DefaultMutableTreeNode(
						"Category");
				int all = v.categoryStorage.index.size();
				for (int i = 0; i < all; i++) {
					CategoryNodeStorage tmpn1 = v.categoryStorage.index.get(i);
					if (tmpn1.level == 1) {
						DefaultMutableTreeNode tmp1 = new DefaultMutableTreeNode(
								tmpn1.id + ":" + tmpn1.name);
						if (v.categoryStorage.hasChild(tmpn1.id)) {
							for (int j = 0; j < all; j++) {
								CategoryNodeStorage tmpn2 = v.categoryStorage.index
										.get(j);
								if (tmpn2.fatherid == tmpn1.id) {
									DefaultMutableTreeNode tmp2 = new DefaultMutableTreeNode(
											tmpn2.id + ":" + tmpn2.name);
									if (v.categoryStorage.hasChild(tmpn2.id)) {
										for (int k = 0; k < all; k++) {
											CategoryNodeStorage tmpn3 = v.categoryStorage.index
													.get(k);
											if (tmpn3.fatherid == tmpn2.id) {
												DefaultMutableTreeNode tmp3 = new DefaultMutableTreeNode(
														tmpn3.id + ":"
																+ tmpn3.name);
												tmp2.add(tmp3);
											}
										}
									}
									tmp1.add(tmp2);
								}
							}
						}
						tree1.add(tmp1);
					}
				}
				tree1.add(new DefaultMutableTreeNode("-1::未分类"));
				tree.removeAll();
				tree.setModel(new DefaultTreeModel(tree1));
				// System.out.println("finish");
			}
		});
		mntmOpenProject.setIcon(new ImageIcon(
				".\\src\\main\\resources\\icons\\open.png"));
		mnFile.add(mntmOpenProject);

		JMenuItem mntmSaveProject = new JMenuItem("Save Project");
		mntmSaveProject.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		mntmSaveProject.setIcon(new ImageIcon(
				".\\src\\main\\resources\\icons\\save.png"));
		mnFile.add(mntmSaveProject);

		JMenuItem mntmNewMenuItem = new JMenuItem("Close Project");
		mntmNewMenuItem.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		mntmNewMenuItem.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				System.exit(1);
			}
		});
		mntmNewMenuItem.setIcon(new ImageIcon(
				".\\src\\main\\resources\\icons\\close.png"));
		mnFile.add(mntmNewMenuItem);

		JMenu mnEdit = new JMenu("Edit ");
		mnEdit.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		menuBar.add(mnEdit);

		JMenuItem mntmAddPatent = new JMenuItem("Add Patent");
		mntmAddPatent.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		mntmAddPatent.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				// addFrame t = new addFrame();
				addFrame d = new addFrame(frmClassifier, true);
				d.comboBox.removeAll();
				DefaultComboBoxModel<String> model = new DefaultComboBoxModel<String>();
				for (int i = 0, j = v.categoryStorage.index.size(); i < j; i++) {
					if (!v.categoryStorage.hasChild(i)) {
						model.addElement(v.categoryStorage.index.get(i).id
								+ ":" + v.categoryStorage.StringAt(i));
					}
				}
				if (model.getSize() != 0)
					model.addElement(-1 + ":" + v.categoryStorage.StringAt(-1));
				d.comboBox.setModel(model);
				d.setLocation(frmClassifier.getX() + frmClassifier.getWidth()
						/ 2 - d.getWidth() / 2, frmClassifier.getY()
						+ frmClassifier.getHeight() / 2 - d.getHeight() / 2);
				d.setVisible(true);
				d.setModal(true);
				if (d.textField.getText() != "" && d.returnPatent() != null) {
					PatentNodeStorage tmp = d.returnPatent();
					int id = v.patentStorage.addPatent(tmp);
					tmp = v.patentStorage.Patents.get(id);
					v.categoryStorage.addPatent(tmp);
				}

			}
		});
		mntmAddPatent.setIcon(new ImageIcon(
				".\\src\\main\\resources\\icons\\add.png"));
		mnEdit.add(mntmAddPatent);

		JMenuItem mntmRemovePatent = new JMenuItem("Remove Patent");
		mntmRemovePatent.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		mntmRemovePatent.setIcon(new ImageIcon(
				".\\src\\main\\resources\\icons\\remove.png"));
		mnEdit.add(mntmRemovePatent);

		JMenu mnLearning = new JMenu("Learning ");
		mnLearning.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		menuBar.add(mnLearning);

		JMenuItem mntmLeaning = new JMenuItem("Leaning");
		mntmLeaning.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		mntmLeaning.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				try {
					v.IG();
					//v.TFIDF();
					JOptionPane.showMessageDialog(null,
							"Learning Procedure Finished", "Finished",
							JOptionPane.INFORMATION_MESSAGE);
				} catch (Exception e1) {
					e1.printStackTrace();
				}

			}
		});
		mntmLeaning.setIcon(new ImageIcon(
				".\\src\\main\\resources\\icons\\learning.png"));
		mnLearning.add(mntmLeaning);

		JMenuItem mntmSetTheshold = new JMenuItem("Set Theshold");
		mntmSetTheshold.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		mntmSetTheshold.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				settings s = new settings();
				s.setModal(true);
				s.setLocation(frmClassifier.getX() + frmClassifier.getWidth()
						/ 2 - s.getWidth() / 2, frmClassifier.getY()
						+ frmClassifier.getHeight() / 2 - s.getHeight() / 2);
				s.setVisible(true);
				int t = Integer.valueOf(s.returnValues());
				v.THRESHOLD = t;
			}
		});
		mntmSetTheshold.setIcon(new ImageIcon(
				".\\src\\main\\resources\\icons\\set.png"));
		mnLearning.add(mntmSetTheshold);
		
		JMenu mnClassify = new JMenu("Classify ");
		mnClassify.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		menuBar.add(mnClassify);

		JMenu mnHelp = new JMenu("Help ");
		mnHelp.setFont(new Font("微软雅黑", Font.PLAIN, 14));
		menuBar.add(mnHelp);

		JMenuItem mntmAboutClassifer = new JMenuItem("About Classifer");
		mntmAboutClassifer.setFont(new Font("微软雅黑", Font.PLAIN, 13));
		mntmAboutClassifer.setIcon(new ImageIcon(
				".\\src\\main\\resources\\icons\\about.png"));
		mnHelp.add(mntmAboutClassifer);
		frmClassifier.getContentPane().setLayout(new BorderLayout(0, 0));

		JScrollPane Panel1 = new JScrollPane();
		frmClassifier.getContentPane().add(Panel1, BorderLayout.WEST);
		Panel1.setPreferredSize(new Dimension(
				(int) (frmClassifier.getWidth() * 0.2), 480));

		Panel1.setViewportView(tree);
		tree.setBounds(0, 0, (int) (frmClassifier.getWidth() * 0.2), 480);

		JPanel panel = new JPanel();
		frmClassifier.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		panel.add(tablesp, BorderLayout.NORTH);

		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setColumnSelectionAllowed(true);
		table.setFillsViewportHeight(true);
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] {
				"ID", "SPNO", "NAME", "IsMatched" }));
		tablesp.setViewportView(table);
		tablesp.setPreferredSize(new Dimension(
				(int) (frmClassifier.getWidth() * 0.8), (int) (frmClassifier
						.getHeight() * 0.4)));
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(1).setPreferredWidth(110);
		table.getColumnModel().getColumn(2)
				.setPreferredWidth(tablesp.getWidth() - 195);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.setCellSelectionEnabled(false);

		JScrollPane textsp = new JScrollPane();
		panel.add(textsp, BorderLayout.CENTER);

		text.setBackground(SystemColor.inactiveCaption);
		text.setText("");
		//text.setLineWrap(true);
		textsp.setViewportView(text);
		textsp.setPreferredSize(new Dimension(
				(int) (frmClassifier.getWidth() * 0.8), (int) (frmClassifier
						.getHeight() * 0.6)));

	}
}
