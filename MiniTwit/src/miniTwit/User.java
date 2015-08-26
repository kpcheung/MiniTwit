package miniTwit;

import java.awt.List;
import java.util.ArrayList;

public class User extends Visitor implements Observer, Visitable {
	private String userID;
	private ArrayList<String> messages;
	private int posMsgTotal;
	
	public User() {
		messages = new ArrayList<String>();
		posMsgTotal = 0;
	}
	
	public User(String userID) {
		this.userID = userID;
	}
	
	public String getUserID() {
		return userID;
	}
	
	public void setUserID(String userID) {
		this.userID = userID;
	}
	
	public ArrayList getMessages() {
		return messages;
	}
	
	public void addtMessage(String message) {
		messages.add(message);
	}
	
}
