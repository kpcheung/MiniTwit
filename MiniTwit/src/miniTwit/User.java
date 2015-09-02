package miniTwit;

import java.util.List;
import java.util.ArrayList;


/**
 * The User class extends the Observer class and creates User objects that are the functional part of the
 * MiniTwit program.
 * @author K
 *
 */
public class User extends Observer {
	
	/**
	 * The user's User ID as a String.
	 */
	private String userID;
	
	/**
	 * The user's most current message.
	 */
	private String message;
	
	/**
	 * The ArrayList of all the user's messages.
	 */
	private List<String> messages;
	
	/**
	 * The total number of positive messages posted by the user.
	 */
	private int posMsgTotal;
	
	/**
	 * The ArrayList of the user's subscriptions and their subscribers.
	 */
	private List<User> subscriptions, subscribers;
	
	/**
	 * A String list of their subscription feed.
	 */
	private List<String> subscriptionFeed;
	
	/**
	 * A list of all users in the program.
	 */
	private List<User> userList;
	
	/**
	 * One instance of the UserViews object.
	 */
	private UserViews userViews;
	
	/**
	 * The default User constructor that initiates all elements.
	 */
	public User() {
		userViews = UserViews.getInstance();
		messages = new ArrayList<String>();
		posMsgTotal = 0;
		setUserList(new ArrayList<User>());
		subscriptions = new ArrayList<User>();
		subscribers = new ArrayList<User>();
		subscriptionFeed = new ArrayList<String>();
	}
	
	/**
	 * Sets the user ID to the specified String.
	 * @param userID
	 */
	public void setUserID(String userID){
		this.userID = userID;
	}
	
	/**
	 * Gets the user ID.
	 * @return the String that contains the user ID
	 */
	public String getUserID() {
		return userID;
	}
	
	//-----------------------------------------------------------------------------------
	//
	// DESIGN PATTERN USED: Observer
	//
	//-----------------------------------------------------------------------------------
	
	/**
	 * Gets the most recent message that the user posted.
	 * @return the user's most recent message
	 */
	public String getMessage() {
		return message;
	}
	
	/**
	 * Sets the message to the specified String.
	 * @param message
	 */
	public void setMessage(String message) {
		this.message = message;
		messages.add(message);
		checkPositive(message);
	}
	
	/**
	 * Returns the list of the user's messages.
	 * @return messages 
	 */
	public List<String> getMessages() {
		return messages;
	}
	
	/**
	 * Adds a subscriber to the user's subscriber list.
	 * @param subscriber
	 */
	public void addSubscriber(User subscriber) {
		subscribers.add(subscriber);
	}
	
	/**
	 * Gets the user's subscriber list.
	 * @return the list of subscribers
	 */
	public List<User> getSubscribers() {
		return subscribers;
	}
	
	/**
	 * Adds a new subscription.
	 * @param user The user that wants to subscribe to another user
	 * @param newUserSubscription The user that is being subscribed to
	 */
	public void attach(User user, User newUserSubscription) {
		subscriptions.add(newUserSubscription);
		newUserSubscription.addSubscriber(user);
	}
	
	/**
	 * 
	 * @param user
	 * @param message
	 */
	public void notifyAllObservers(User user, String message) {
		for(User u : user.getSubscribers()) {
			u.update(user);
		}
	}
	
	/**
	 * Updates all of the user's subscribers' subscription feeds to include their newest message.
	 * @param userSubject
	 */
	public void update(User userSubject) {
		subscriptionFeed.add(userSubject.getUserID() + ": " + userSubject.getMessage());
		
	}
	
	/**
	 * This method checks to see if the current message is a positive message, i.e. if the message contains
	 * "excellent", "good", or "great" within the String and adds to the positive message counter if true.
	 * @param message The user's most current message.
	 * @return <code>true</code> if the message has either of the three words within it, <code>false</code> otherwise
	 */
	public boolean checkPositive(String message) {
		if(message.contains("excellent") || message.contains("good") || message.contains("great")) {
			++posMsgTotal;
			return true;
		}
		return false;
	}

	/**
	 * Gets the list of all users.
	 * @return The list of all users
	 */
	public List<User> getUserList() {
		return userList;
	}

	/**
	 * Sets the list of all users.
	 * @param userList
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}
	
	/**
	 * Evaluates whether the current user exists within a list of all current users.
	 * @param newUserID The user ID to be searched for in the userList
	 * @return <code>true</code> if the user is in the the userList, <code>false</code> otherwise
	 */
	public boolean userAlreadyExists(String newUserID) {
		for(User u : userList) {
			if(u.getUserID().equals(newUserID)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns an ArrayList of all the messages from the user's subscriptions.
	 * @return
	 */
	public List<String> getSubscriptionFeed() {
		return subscriptionFeed;
	}
	
	//-----------------------------------------------------------------------------------
	//
	// DESIGN PATTERN USED: Visitor
	//
	//-----------------------------------------------------------------------------------
	
	/**
	 * Visits the new subscription that the user wants to subscribe to.
	 * @param newSubscriptionUserID The new subscription's user ID
	 * @return <code>true</code> if the subscription already exists, <code>false</code> otherwise
	 */
	public boolean visit(String newSubscriptionUserID) {
		for(User subscription : subscriptions) {
			if(subscription.getUserID().equals(newSubscriptionUserID)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Accepts the new subscriber's request of the subscriber is currently not subscribed to the user.
	 * @param newSubscriptionUserID The user ID of the user who wants to subscribe to this user
	 * @return The subscription's user ID
	 */
	public User accept(String newSubscriptionUserID) {
		User subscriptionUserID;
		subscriptionUserID = new User();
		for(User u : userList) {
			if(u.getUserID().equals(newSubscriptionUserID)) {
				subscriptionUserID = u;
			} 
		}
		return subscriptionUserID;
	}
	
	/**
	 * Gets the current list of user subscriptions.
	 * @return an ArrayList of the user IDs of the feeds the user follows
	 */
	public List<User> getSubscriptions() {
		return subscriptions;
	}
	
	/**
	 * Returns the number of positive messages the user has.
	 */
	public int getPosMsgTotal() {
		return posMsgTotal;
	}
}
