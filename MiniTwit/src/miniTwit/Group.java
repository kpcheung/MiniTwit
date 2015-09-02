package miniTwit;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The Group class keeps track of all the users who belong to a Group.
 * @author K
 *
 */
public class Group {
	
	/**
	 * The name of the group.
	 */
	private String groupID;
	
	/**
	 * The list of users within the group.
	 */
	private List<User> usersInGroup = new ArrayList<User>();
	
	/**
	 * The default Group constructor.
	 */
	public Group() {
	}
	
	/**
	 * Instantiates the Group with the specified String group ID
	 * @param groupID The new group's ID
	 */
	public Group(String groupID) {
		setGroupID(groupID);
	}
	
	/**
	 * Returns the group ID.
	 * @return groupID
	 */
	public String getGroupID() {
		return groupID;
	}
	
	/**
	 * Sets the GroupID to the specified String groupID.
	 * @param groupID the new group ID
	 */
	public void setGroupID(String groupID) {
		this.groupID = groupID;
	}
	
	/**
	 * Adds a user to the group's list of users in that group.
	 * @param user The user ID to be added to the group's list
	 */
	public void addUserToGroup(User user) {
		usersInGroup.add(user);
	}
	
	/**
	 * Prints out all the users currently in the specified Group.
	 */
	public void getUsersInGroup() {
		System.out.println(groupID + " members: ");
		for(User user : usersInGroup) {
			 System.out.println(user.getUserID());
		}
	}
}
