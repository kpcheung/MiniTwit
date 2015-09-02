package miniTwit;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JList;

/**
 * The SubscriptionFeed class uses the Composite design pattern to display messages.
 * @author K
 *
 */
//-----------------------------------------------------------------------------------
//
// DESIGN PATTERN USED: Composite
//
//-----------------------------------------------------------------------------------
public class SubscriptionFeed {
	
	/**
	 * The user's SubscriptionFeed.
	 */
	private List<String> userSubscriptionFeed;	
	/**
	 * The list model that contains the user's feed information.
	 */
	private DefaultListModel<String> listModel;
	
	/**
	 * The default SubscriptionFeed constructor.
	 */
	public SubscriptionFeed() {
		userSubscriptionFeed = new ArrayList<String>();
		listModel = new DefaultListModel<String>();
		
	}
	
	/**
	 * This method creates a new list model that contains all of the
	 * mesasges in a user's subscription feed.
	 * @param user The user whose feed is being used to generate the list model.
	 * @return The generated list model using the user's ArrayList of their subscription feed.
	 */
	public DefaultListModel<String> getFeedModel(User user) {
		userSubscriptionFeed = user.getSubscriptionFeed();
		listModel.removeAllElements();
		for(int i = userSubscriptionFeed.size() - 1; i >= 0; --i) {
			//System.out.println(userNewsfeed.get(i));
			listModel.addElement(userSubscriptionFeed.get(i));
		}
		
		return listModel;
	}
}
