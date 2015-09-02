package miniTwit;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeSelectionModel;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;

import java.awt.*;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Enumeration;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.Border;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

/**
 * The AdminCPanel class is the main frame of the MiniTwit program.
 * @author K
 *
 */
public class AdminCPanel extends JFrame {
	private static final long serialVersionUID = 1L;
	private DefaultTreeModel treeModel;
	
	/**
	 * A dummy User object to introduce a new object before being instantiated.
	 */
	private User genericUser;
	
	/**
	 * One instance of the UserViews object.
	 */
	private UserViews userViews;
	
	/**
	 * The UserView object used to add a UserView to the UserViews object.
	 */
	private UserView userView;
	
	/**
	 * The 
	 */
	private JPanel buttonPanel;
	private JTextArea userID, groupID;
	private JButton addUser, addGroup, openUserView, showUserTotal, showGroupTotal, showMsgTotal, showPosMsgRatio;
	private JLabel showText;
	
	/**
	 * The JTree that houses all users and groups in a hierarchy.
	 */
	private JTree userTree;
	
	/**
	 * An ArrayList of all current users within the program.
	 */
	private ArrayList<User> users;
	
	/**
	 * An ArrayList of all curren groups within the program.
	 */
	private ArrayList<Group> groups;
	
	/**
	 * The JScrollPane that houses the JTree of users and groups.
	 */
	private static JScrollPane treeView;
	
	/**
	 * The most recent TreeNode that the user has selected from the JTree.
	 */
	private DefaultMutableTreeNode selectedNode;
	
	/**
	 * One instance of the AdminCPanel object.
	 */
	private static volatile AdminCPanel instance = null;
	
	/**
	 * The AdminCPanel constructor class.
	 */
	public AdminCPanel() {
		userViews = UserViews.getInstance();
		genericUser = new User();
		users = new ArrayList<User>();
		groups = new ArrayList<Group>();
		
		/**
		 * Defines the tree view of the users.
		 */
		final DefaultMutableTreeNode root = new DefaultMutableTreeNode("Root", true);
		selectedNode = root;
		userTree = new JTree(root);
		treeModel = (DefaultTreeModel) userTree.getModel();
		
		/**
		 * Defines the tree node selection mechanism that keeps track of what node, if any, that
		 * the user has clicked on most recently.
		 */
		userTree.addTreeSelectionListener(new TreeSelectionListener() {
		    public void valueChanged(TreeSelectionEvent e) {
		        DefaultMutableTreeNode selected = (DefaultMutableTreeNode)
		                           userTree.getLastSelectedPathComponent();
		        if (selected == null) return;

		        selectedNode = selected;
		        }
		        
		});
		
		/**
		 * Defines the components in the AdminCPanel and some decorative elements.     
		 */
		userTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		userTree.setPreferredSize(new Dimension(300, 300));
		
		buttonPanel = new JPanel();
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		
		/**
		 *  Defines the JTextAreas to receive a new userID or groupID from the user.
		 */
		userID = new JTextArea(1, 15);
		userID.setBorder(border);
		userID.setMaximumSize(new Dimension(120, 20));
		groupID = new JTextArea(1, 15);
		groupID.setBorder(border);
		
		/**
		 * Defines the button to add a new user to the JTree.
		 */
		addUser = new JButton("Add User");
		addUser.setPreferredSize(new Dimension(120, 20));
		addUser.addActionListener(new ActionListener() {
	        private String newUserID;
	        private User newUser;
	        private boolean userAlreadyExists;

			public void actionPerformed(ActionEvent arg0) {
				userAlreadyExists = false;
				
				if(!userID.getText().isEmpty()) {
		            newUserID = userID.getText();
		            newUser = new User();
		            newUser.setUserID(newUserID);
		          
		            for(User user : users) {
		            	if(user.getUserID().equals(newUser.getUserID())) {
		            		userAlreadyExists = true;
		            	}
		            }
		            
		            if(userAlreadyExists == false) {
		            	users.add(newUser);
		            	for(User u : users) {
	            			u.setUserList(users);
	            		}
		            	if(selectedNode.getAllowsChildren() == true) {
		            		selectedNode.add(new DefaultMutableTreeNode(newUserID, false));
		            		if(selectedNode != root) {
		            			for(Group g : groups) {
		            				if(g.getGroupID().equals(selectedNode.getUserObject())) {
		            					g.addUserToGroup(newUser);
		            				}
		            			}
		            		}
		            	}
		            	else {
		            		root.add(new DefaultMutableTreeNode(newUserID, false));
		            	}
		            	treeModel.reload(root);
		            }
		            selectedNode = root;
		        }
			}
	    });
		
		/**
		 * Defines the button to add a new group to the JTree.
		 */
		addGroup = new JButton("Add Group");
		addGroup.setPreferredSize(new Dimension(120, 20));
		addGroup.addActionListener(new ActionListener() {
			private String newGroupID;
	        private Group newGroup;
	        public void actionPerformed(ActionEvent arg0) {
				if(!groupID.getText().isEmpty()) { 
					newGroupID = groupID.getText();
		            newGroup = new Group(groupID.getText());
		            
		            if(!groups.contains(newGroup)) {
		            	groups.add(newGroup);
		            	if(selectedNode.getAllowsChildren() == true) {
		            		selectedNode.add(new DefaultMutableTreeNode(newGroupID, true));
		            	}
		            	else {
		            		root.add(new DefaultMutableTreeNode(newGroupID, true));
		            	}
		            	treeModel.reload(root);
		            }
		            selectedNode = root;
		        }
			}
	    });
		
		/**
		 * Defines the button to open a user's User View in a new JFrame.
		 */
		openUserView = new JButton("Open User View");
		openUserView.setPreferredSize(new Dimension(292, 20));
		openUserView.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				User targetUser = null;
				if(!selectedNode.getAllowsChildren()) {
					for(User user : users) {
						if(user.getUserID().equals(selectedNode.getUserObject())) {
							targetUser = user;
						}
					}
					userView = new UserView(targetUser);
					userViews.add(userView);
				}
	        }
	    });
		
		/**
		 * Defines the button and it's onclick behavior to show how many users there currently are in the program.
		 */
		showUserTotal = new JButton("Show User Total");
		showUserTotal.setPreferredSize(new Dimension(140, 20));
		showUserTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	            showText.setText("User Total: " + count(0));
	        }
	    });
		
		/**
		 * Defines the button and its onclick behavior to show how many groups are currently in the program.
		 */
		showGroupTotal = new JButton("Show Group Total");
		showGroupTotal.setPreferredSize(new Dimension(140, 20));
		showGroupTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	            showText.setText("Group Total: " + count(1));
	        }
	    });
		
		/**
		 * Defines the button and its onclick behavior to show how many messages there are in the program.
		 */
		showMsgTotal = new JButton("<html><center>Show Message<br>Total</center></html>");
		showMsgTotal.setPreferredSize(new Dimension(140, 40));
		showMsgTotal.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
	            showText.setText("Message Total: " + getMessageTotal());
	        }
	    });
		
		/**
		 * Defines the button and its onclick behavior to show many messages out of the total number of messages is positive.
		 */
		showPosMsgRatio = new JButton("<html><center>Show Positive<br>Percentage</center></html>");
		showPosMsgRatio.setPreferredSize(new Dimension(140, 40));
		showPosMsgRatio.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DecimalFormat df = new DecimalFormat("0.00");
	            showText.setText("Positive Messages: " + df.format(getPosMsgTotal()) + "%");
	        }
	    });
		
		/**
		 * Defines a JLabel to show the information when the previous four buttons are pressed.
		 */
		showText = new JLabel();
		showText.setPreferredSize(new Dimension(180, 20));
		
		/**
		 * Sets up the AdminCPanel components.
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
	 * Initializes the JPanel components by adding them to the JPanel.
	 * @param buttonPanel The JPanel that houses all of the AdminCPanel components.
	 * @param userID The new user ID JTextArea
	 * @param groupID The new group ID JTextArea
	 * @param addUser The new user ID JButton
	 * @param addGroup The new group ID JButton
	 * @param openUserView The open User View JButton
	 * @param showUserTotal The show user total JButton
	 * @param showGroupTotal The show group total JButton
	 * @param showMsgTotal The show total messages JButton
	 * @param showPosMsgTotal The show positive messages JButton
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
	
	/**
	 * This method displays either the user total or the group total depending on the choice.
	 * @param choice If the method receives the integer 0 then the method should return the user ID total 
	 * @return Returns the size of the ArrayList of users or the size of the ArrayList of groups
	 */
	 public int count(int choice) {
		 if(choice == 0) {
			 return users.size();
		 }
		 return groups.size();
	 }
	 
	 /**
	  * Returns the percentage of total positive messages across all users.
	  * @return 
	  */
	 private Double getPosMsgTotal() {
		 Double messageTotal = (double) getMessageTotal();
		 double posPercentage = 0;
		 int posMessageTotal = 0;
		 for(User user : users) {
			 posMessageTotal = posMessageTotal + user.getPosMsgTotal();
		 }
		 if(messageTotal.doubleValue() != 0) {
			 posPercentage = (double) posMessageTotal/messageTotal * 100;
			 return posPercentage;
		 }
		 return 0.00;
	 }
	 
	 /**
	  * Returns the number of total messages across all users.
	  * @return
	  */
	 private int getMessageTotal() {
		 int messageTotal = 0;
		 for(User user : users) {
			 messageTotal+=user.getMessages().size();
		 }
		return messageTotal;
	 }
	 
/*Debugging element: To see all User objects */	 
/*	 public void getAllNewsfeeds() {
		 System.out.println("All users: ");
		 for(User u : users) {
			 System.out.println(u.getUserID() + ", which refers to " + u);
		 }
		 
		 System.out.println("ALL SUBSCRIBER LISTS: ");
		 for (User u : users) {
				u.getSubscribers();
			 }
		 
		 System.out.println("---");
		 
		 
		 System.out.println("ALL NEWSFEEDS: ");
		 for (User u : users) {
			System.out.println(u.getUserID() + "'s Feed");
			SubscriptionFeed nf = new SubscriptionFeed();
			nf.getFeedModel(u);
		 }
	 }*/
	 
}
