import java.util.ArrayList;
import java.util.List;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Fernando on 8/21/15.
 */

public class User extends Twitter {
    private Set<User> following = new HashSet<User>();
    private List<Tweet> tweets = new ArrayList<Tweet>();

    public User(String uID, UserGroup parentGroup) throws Exception {
        InvalidNameVisitor visitor = new InvalidNameVisitor();
        Twitter.bfs(visitor, UserGroup.getRootGroup());
        if (visitor.checkInvalidUser(uID)) {
            throw new Exception("Username taken please choose another ");
        }
        this.uID = uID;
        this.parent = parentGroup;
        this.parent.children.add(this);
        UserGroup.getRootGroup().notifyObservers();
    }

    public void accept(Visitor visitor) {
        visitor.visitUser(this);
    }

    public void follow(User user, Observer observer) throws Exception {
        if (this == user || !this.following.add(user)) throw new Exception("Already following.");
        if (observer != null) user.attach(observer);
        this.notifyObservers();
    }

    public void tweet(String message) {
        tweets.add(new Tweet(uID + ": " + message));
        this.notifyObservers();
    }

    public List<Tweet> getTweets() {
        return this.tweets;
    }

    public List<Tweet> getFeedList() {
        List<Tweet> feed = new ArrayList<Tweet>();
        feed.addAll(this.getTweets());
        for (User user : this.following) {
            feed.addAll(user.getTweets());
        }
        Collections.sort(feed);
        Collections.reverse(feed);
        return feed;
    }

    public String[] getFollowing() {
        String[] following = new String[this.following.size()];
        Integer i = 0;
        for (User user : this.following) {
            following[i++] = user.toString();
        }
        return following;
    }

    public void attachUser(Observer observer) {
        this.attach(observer);
        for (User user : this.following) {
            user.attach(observer);
        }
    }

    public static User finduID(String uID, UserGroup parent) {
        for (Twitter twitter : parent.children) {
            if (twitter.isLeaf()) {
                if (twitter.getuID().compareTo(uID) == 0) return (User) twitter;
            } else {
                User rec = finduID(uID, (UserGroup) twitter);
                if (rec != null) return rec;
            }
        }
        return null;
    }
}

