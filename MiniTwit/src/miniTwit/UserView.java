package miniTwit;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class UserView extends JFrame {
	private String userID;
	private JPanel userPanel;
	private JScrollPane followerList, newsFeed;
	private JTextArea followID, messageText;
	private JButton addFollow, messagePost;
	private ArrayList<String> following, followers;
	
	
	public UserView(String userID) {
		this.userID = userID;
		User user = new User(userID);
		
		userPanel = new JPanel();
		userPanel.setPreferredSize(new Dimension(400, 500));
		
		Border border = BorderFactory.createLineBorder(Color.BLACK);
		followID = new JTextArea(1, 15);
		followID.setBorder(border);
		addFollow = new JButton("Follow");
		addFollow.setPreferredSize(new Dimension(120, 20));
		
		
		
		userPanel.add(followID, BorderLayout.WEST);
		userPanel.add(addFollow, BorderLayout.EAST);
		
		this.setTitle(userID + "\'s View");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().add(userPanel);
		//this.setResizable(false);
		this.setLayout(new GridLayout(0, 1));
		this.setVisible(true);
		this.add(userPanel);
		this.pack();
	}
	
}
