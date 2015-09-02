package miniTwit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.border.Border;

/**
 * The UserView class that creates a JFrame for every User that exists in the MiniTwit program.
 * @author K
 *
 */
public class UserView extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private User user;
	private String userID;
	@SuppressWarnings("unused")
	private JPanel userPanel, followingPanel;
	private JScrollPane followingList, newsfeed;
	private JTextArea followID, messageText;
	private JButton addFollow, postMessage;
	private DefaultListModel<String> followingListModel;
	private JList<String> followingListData, newsfeedData;
	private JLabel followingHeader, newsfeedHeader;
	private SubscriptionFeed feed;
	private Timer timer;
	private final int INTERVAL = 1000;
	
	public UserView() {
		
	}
	
	/**
	 * Populates a new UserView object with the designated User object
	 * @param userTarget The user that the UserView is for
	 */
	public UserView(User userTarget) {
		this.user = userTarget;
		this.userID = userTarget.getUserID();
		followingListModel = new DefaultListModel<String>();
		followingHeader = new JLabel("Following: ");
		newsfeedHeader = new JLabel("Newsfeed: ");
		feed = new SubscriptionFeed();
		
		userPanel = new JPanel();
		followingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		userPanel.setPreferredSize(new Dimension(350, 500));
		
		
		
		timer = new Timer(INTERVAL, new ActionListener() {
			public void actionPerformed(ActionEvent evt) {
			       newsfeedData.removeAll();
			       newsfeedData = new JList<String>(feed.getFeedModel(user));
			    }    
			});

		Border border = BorderFactory.createLineBorder(Color.BLACK);
		followID = new JTextArea(1, 18);
		followID.setBorder(border);
		addFollow = new JButton("Follow");
		addFollow.setPreferredSize(new Dimension(120, 20));
		addFollow.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(user.userAlreadyExists(followID.getText()) == true && user.visit(followID.getText()) == false) {
					User newSubscription = user.accept(followID.getText());
					user.attach(user, newSubscription);
					followingListModel.addElement(followID.getText());
				}
	        }
	    });
		
		messageText = new JTextArea(1, 18);
		messageText.setBorder(border);
		postMessage = new JButton("Post");
		postMessage.setPreferredSize(new Dimension(120, 20));
		postMessage.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				user.setMessage(messageText.getText());
				user.notifyAllObservers(user, messageText.getText());
	        }
	    });
		
		userPanel.add(followID, BorderLayout.WEST);
		userPanel.add(addFollow, BorderLayout.EAST);
		userPanel.add(followingHeader, BorderLayout.WEST);
		
		followingListData = new JList<String>(followingListModel);
		followingList = new JScrollPane(followingListData);
		followingList.setPreferredSize(new Dimension(350, 150));
		
		newsfeedData = new JList<String>(feed.getFeedModel(user));
		
		newsfeed = new JScrollPane(newsfeedData);
		newsfeed.setPreferredSize(new Dimension(350, 150));
		
		userPanel.add(followingList, BorderLayout.CENTER);
		userPanel.add(messageText, BorderLayout.WEST);
		userPanel.add(postMessage, BorderLayout.EAST);
		userPanel.add(newsfeedHeader, BorderLayout.WEST);
		userPanel.add(newsfeed, BorderLayout.CENTER);
		
		timer.start();
		this.setTitle(userID + "\'s View");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(userPanel);
		this.setResizable(false);
		this.setLayout(new GridLayout(0, 1));
		this.setVisible(true);
		timer.start();
		this.add(userPanel);
		this.pack();
	}
	
}
