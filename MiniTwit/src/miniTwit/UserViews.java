package miniTwit;

import java.util.ArrayList;
import java.util.List;

/**
 * The UserViews class keeps track of all the UserView instances.
 * @author K
 *
 */
public class UserViews {
	private List<UserView> userViews;
	private static volatile UserViews instance = null;
	
	/**
	 * The default UserViews constructor.
	 */
	public UserViews() {
		setUserViews(new ArrayList<UserView>());
	}
	
	/**
	 * Returns the list of all current UserView objects.
	 * @return The list of UserView objects
	 */
	public List<UserView> getUserViews() {
		return userViews;
	}

	/**
	 * Sets the UserViews list.
	 * @param userViews The UserViews list
	 */
	public void setUserViews(List<UserView> userViews) {
		this.userViews = userViews;
	}
	
	/**
	 * The implementation of the Single design pattern to ensure that there is only one 
	 * instance of the UserViews object.
	 * @return
	 */
	public static UserViews getInstance() {
		if (instance == null) {
			instance = new UserViews();
		}
		return instance;
	}

	/**
	 * Adds the specified UserView into the list of all current UserViews.
	 * @param userView The UserView to be added to the list
	 */
	public void add(UserView userView) {
		userViews.add(userView);
	}
	
}
