package miniTwit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import testTree.TestTree;  

import java.awt.*;
import java.awt.event.*;
import java.awt.GridLayout;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.Border;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;

public class AdminCPanel extends JFrame {
	private DefaultTreeModel treeModel, model;
	private UserView userView;
	private JPanel buttonPanel;
	private JTextArea userID, groupID;
	private JButton addUser, addGroup, openUserView, showUserTotal, showGroupTotal, showMsgTotal, showPosMsgRatio;
	private JLabel showText;
	private JTree userTree;
	private ArrayList<String> users;
	private ArrayList<String> groups;
	private Map<String, String> groupList;
	private static JScrollPane treeView;
	private String newUserID;
	private String inheritGroup;
	
	
	private static volatile AdminCPanel instance = null;
	
	public AdminCPanel() {
		users = new ArrayList<String>();
		groups = new ArrayList<String>();
		groupList = new HashMap<String, String>();
		
		/**
		 * Defines the tree view of the users
		 */
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root", true);
		userTree = new JTree(root);
		treeModel = (DefaultTreeModel) userTree.getModel();
		userTree.addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode)
		                           userTree.getLastSelectedPathComponent();
		        if (selectedNode == null) return;

		        Object nodeInfo = selectedNode.getUserObject();
		        System.out.println(selectedNode + "'s parent is " + selectedNode.getParent());
		        
		    }
		});
		userTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		userTree.setPreferredSize(new Dimension(300, 300));
		
		/**
		 * Defines TextAreas and Buttons for the Admin Control Panel
		 */
		buttonPanel = new JPanel();
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		userID = new JTextArea(1, 15);
		userID.setBorder(border);
		userID.setMaximumSize(new Dimension(120, 20));
		groupID = new JTextArea(1, 15);
		groupID.setBorder(border);
		
		addUser = new JButton("Add User");
		addUser.setPreferredSize(new Dimension(120, 20));
		addUser.addActionListener(new ActionListener() {
	        private String newUserID;

			public void actionPerformed(ActionEvent arg0) {
	            newUserID = userID.getText();
	            if(!users.contains(newUserID) && !newUserID.isEmpty() ) {
	            	users.add(newUserID);
	            	
	            	//Implement adding to tree here
	            	root.add(new DefaultMutableTreeNode(newUserID, false));
	            	treeModel.reload(root);
	            }
	        }
	    });
		
		addGroup = new JButton("Add Group");
		addGroup.setPreferredSize(new Dimension(120, 20));
		addGroup.addActionListener(new ActionListener() {
	        private String newGroupID;

			public void actionPerformed(ActionEvent arg0) {
	            newGroupID = groupID.getText();
	            DefaultMutableTreeNode parent;
	            if(!groups.contains(newGroupID) && !newGroupID.isEmpty()) {
	            	groups.add(newGroupID);
	            }
	            root.add(new DefaultMutableTreeNode(newGroupID, true));
	            treeModel.reload(root);
	        }
	    });
		
		openUserView = new JButton("Open User View");
		openUserView.setPreferredSize(new Dimension(292, 20));
		openUserView.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				if(newUserID != root.toString()) {
					userView = new UserView(newUserID);
					userView.setVisible(true);
				}
	        }
	    });
		
		showUserTotal = new JButton("Show User Total");
		showUserTotal.setPreferredSize(new Dimension(140, 20));
		showUserTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	            showText.setText("User Total: " + countUsers(root));
	        }
	    });
		
		showGroupTotal = new JButton("Show Group Total");
		showGroupTotal.setPreferredSize(new Dimension(140, 20));
		showGroupTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	            showText.setText("Group Total: ");
	        }
	    });
		
		showMsgTotal = new JButton("<html><center>Show Message<br>Total</center></html>");
		showMsgTotal.setPreferredSize(new Dimension(140, 40));
		showMsgTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	            showText.setText("Message Total: ");
	        }
	    });
		
		showPosMsgRatio = new JButton("<html><center>Show Positive<br>Percentage</center></html>");
		showPosMsgRatio.setPreferredSize(new Dimension(140, 40));
		showPosMsgRatio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	            showText.setText("Positive Messages: 500%");
	        }
	    });
		
		showText = new JLabel();
		showText.setPreferredSize(new Dimension(180, 20));
		
		/**
		 * Sets up the Admin Control Panel 
		 */
		treeView = new JScrollPane(userTree);
		this.setTitle("Admin Control Panel");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setResizable(false);
		this.setLayout(new GridLayout(0, 2));
		this.add(treeView);
		this.pack();
		this.setVisible(true);
		initComponents(buttonPanel, userID, groupID, addUser, addGroup, openUserView, showUserTotal, showGroupTotal, showMsgTotal, showPosMsgRatio, showText);
		this.add(buttonPanel);
	}
	
	//-----------------------------------------------------------------------------------
	//
	// DESIGN PATTERN USED: Singleton
	//
	//-----------------------------------------------------------------------------------
	
	/**
	 * This is the lazy Singleton implementation. 
	 * @return the single instance of AdminCPanel
	 */
	public static AdminCPanel getInstance() {
		if (instance == null) {
			instance = new AdminCPanel();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param buttonPanel
	 * @param userID
	 * @param groupID
	 * @param addUser
	 * @param addGroup
	 * @param openUserView
	 * @param showUserTotal
	 * @param showGroupTotal
	 * @param showMsgTotal
	 * @param showPosMsgTotal
	 */
	public static void initComponents(JPanel buttonPanel, JTextArea userID, JTextArea groupID, JButton addUser, JButton addGroup, JButton openUserView, JButton showUserTotal, JButton showGroupTotal, JButton showMsgTotal, JButton showPosMsgTotal, JLabel showText) {
		buttonPanel.add(userID, BorderLayout.WEST);
		buttonPanel.add(addUser, BorderLayout.EAST);
		buttonPanel.add(groupID, BorderLayout.WEST);
		buttonPanel.add(addGroup, BorderLayout.EAST);
		buttonPanel.add(openUserView, BorderLayout.CENTER);
		buttonPanel.add(showUserTotal, BorderLayout.WEST);
		buttonPanel.add(showGroupTotal, BorderLayout.EAST);
		buttonPanel.add(showMsgTotal, BorderLayout.WEST);
		buttonPanel.add(showPosMsgTotal, BorderLayout.EAST);
		buttonPanel.add(showText, BorderLayout.CENTER);
	}
	 
	 private int countGroup() {
		 int numGroups = 0;
		 return numGroups;
	 }
	 
	 private int countUsers(DefaultMutableTreeNode root) {
		 int userCount;
		 userCount = 0;
		 Enumeration children = root.children();
		 if(children != null) {
			 while(children.hasMoreElements()) {
				 countUsers((DefaultMutableTreeNode) children.nextElement());
				 ++userCount;
			 }
		 }
		return userCount;
	 }
}
