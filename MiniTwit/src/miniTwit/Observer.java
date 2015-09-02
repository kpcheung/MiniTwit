package miniTwit;

import java.util.List;

/**
 * The Observer class defines Observer pattern-related methods.
 * @author K
 *
 */
public abstract class Observer {
	public abstract boolean visit(String newSubscriptionUserID);
	public abstract void setMessage(String message);
	public abstract List<String> getMessages();
	public abstract void addSubscriber(User subscriber);
	public abstract List<User> getSubscribers();
	public abstract void attach(User user, User newUserSubscription);
	public abstract void notifyAllObservers(User user, String message);
	public abstract void update(User userSubject);
}
