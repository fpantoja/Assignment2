import javax.swing.*;
import javax.swing.tree.DefaultTreeModel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando on 8/22/15.
 */
// Observer Pattern
public interface Observer {
    public void update(Twitter tweeter);
}

/**
 * Created by Fernando on 8/22/15.
 */
class UserObserver implements Observer {
    private JList<String> listFollowing;
    private JList<String> listNewsFeed;
    private User user;

    public UserObserver(JList<String> listFollowing, JList<String> listNewsFeed, User user) {
        this.listFollowing = listFollowing;
        this.listNewsFeed = listNewsFeed;
        this.user = user;
    }

    @Override
    public void update(Twitter tweeter) {
        this.listFollowing.setListData(user.getFollowing());
        List<String> feed = new ArrayList<String>();
        List<Tweet> tweets = user.getFeedList();
        for (Tweet tweet : tweets) {
            feed.add(tweet.toString());
        }
        String[] feedArray = feed.toArray(new String[feed.size()]);
        this.listNewsFeed.setListData(feedArray);
    }
}

/**
 * Created by Fernando on 8/22/15.
 */
class RootObserver implements Observer {
    private JTree tree;

    public RootObserver(JTree tree) {
        this.tree = tree;
    }

    @Override
    public void update(Twitter tweeter) {
        ((DefaultTreeModel) tree.getModel()).reload(tweeter);
    }
}
