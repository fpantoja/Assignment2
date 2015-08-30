import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Fernando on 8/22/15.
 */
// Visitor Pattern
public interface Visitor {

    public void visitUser(User user);

    public void visitUserGroup(UserGroup group);

}

/**
 * Created by Fernando on 8/26/15.
 */
class ShowUserTotal implements Visitor {
    private int users = 0;

    public int getUsers() {
        return this.users;
    }

    @Override
    public void visitUser(User user) {
        this.users += 1;
    }

    @Override
    public void visitUserGroup(UserGroup group) {
    }

}

/**
 * Created by Fernando on 8/26/15.
 */
class ShowGroupTotal implements Visitor {
    private int groups = 0;

    public int getGroups() {
        return this.groups;
    }

    @Override
    public void visitUser(User user) {
    }

    @Override
    public void visitUserGroup(UserGroup group) {
        this.groups += 1;
    }
}

/**
 * Created by Fernando on 8/26/15.
 */
class ShowPositivePercentage implements Visitor {
    private int tweets = 0;
    private int positiveTweets = 0;

    public String getPercentPositiveTweets() {
        return Double.toString(Math.round((double) this.positiveTweets / (double) this.tweets * 100.0));
    }

    @Override
    public void visitUser(User user) {
        int users = 0;
        users += 1;
        List<Tweet> tweets = user.getTweets();
        for (Tweet tweet : tweets) {
            this.tweets += 1;
            if (tweet.hasPositiveMessage()) this.positiveTweets += 1;
        }
    }

    @Override
    public void visitUserGroup(UserGroup group) {
        int groups = 0;
        groups += 1;
    }
}

/**
 * Created by Fernando on 8/26/15.
 */
class ShowMessagesTotal implements Visitor {
    private int tweets = 0;

    public int getTweets() {
        return this.tweets;
    }

    @Override
    public void visitUser(User user) {
        this.tweets += user.getTweets().size();
    }

    @Override
    public void visitUserGroup(UserGroup group) {
    }

}

/**
 * Created by Fernando on 8/26/15.
 */
class InvalidNameVisitor implements Visitor {
    private Set<String> users = new HashSet<String>();
    private Set<String> groups = new HashSet<String>();
    private List<String> invalid = new ArrayList<String>();

    @Override
    public void visitUser(User user) {
        if (!users.add(user.getuID())) invalid.add(user.getuID());
    }

    @Override
    public void visitUserGroup(UserGroup group) {
        if (!groups.add(group.getuID())) invalid.add(group.getuID());
    }

    public List<String> getInvalid() {
        return invalid;
    }

    public boolean checkInvalidUser(String name) {
        return nameExists(name, this.users);
    }

    public boolean checkInvalidGroup(String name) {
        return nameExists(name, this.groups);
    }

    private static boolean nameExists(String name, Set<String> tweeters) {
        if (name.indexOf(' ') >= 0) return true;
        for (String twitter : tweeters) {
            if (twitter.compareTo(name) == 0) return true;
        }
        return false;
    }
}