import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Fernando on 8/27/15.
 */

public class UserView extends JFrame {

    private User user;
    private JTextField fieldFollowUser;
    private JTextField fieldNewMessage;
    private Observer observer;

    public UserView(User user) {
        this.user = user;
        setBounds(100, 100, 295, 350);
        setTitle(this.user.toString());
        JPanel userPanel = new JPanel();
        userPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        userPanel.setLayout(null);
        setContentPane(userPanel);

        fieldFollowUser = new JTextField();
        fieldFollowUser.setBounds(5, 5, 140, 30);
        userPanel.add(fieldFollowUser);
        fieldFollowUser.setColumns(10);

        JButton btnFollowUser = new JButton("Follow");
        btnFollowUser.setBounds(150, 5, 140, 30);
        btnFollowUser.addActionListener(new Follow(this));
        userPanel.add(btnFollowUser);

        JLabel lblFollowing = new JLabel("Following");
        lblFollowing.setBounds(8, 30, 256, 30);
        userPanel.add(lblFollowing);

        JList<String> listFollowing = new JList<String>(this.user.getFollowing());
        JScrollPane scrollFollowing = new JScrollPane();
        scrollFollowing.setBounds(8, 55, 275, 100);
        scrollFollowing.setViewportView(listFollowing);
        userPanel.add(scrollFollowing);

        fieldNewMessage = new JTextField();
        fieldNewMessage.setBounds(5, 160, 140, 30);
        userPanel.add(fieldNewMessage);
        fieldNewMessage.setColumns(10);

        JButton btnNewMessage = new JButton("Tweet");
        btnNewMessage.setBounds(150, 160, 140, 30);
        btnNewMessage.addActionListener(new Tweet(this));
        userPanel.add(btnNewMessage);

        JLabel lblNewsFeed = new JLabel("News Feed");
        lblNewsFeed.setBounds(8, 190, 256, 30);
        userPanel.add(lblNewsFeed);

        List<String> feed = new ArrayList<String>();
        List<Tweet> tweets = this.user.getFeedList();
        for (Tweet tweet : tweets) {
            feed.add(tweet.toString());
        }
        String[] feedArray = feed.toArray(new String[feed.size()]);

        JList<String> listNewsFeed = new JList<String>(feedArray);
        JScrollPane scrollNewsFeed = new JScrollPane();
        scrollNewsFeed.setBounds(8, 215, 275, 100);
        scrollNewsFeed.setViewportView(listNewsFeed);
        userPanel.add(scrollNewsFeed);
        observer = new UserObserver(listFollowing, listNewsFeed, user);
        this.user.attachUser(observer);
    }

    public User getUser() {
        return this.user;
    }

    public Observer getObserver() {
        return this.observer;
    }

    public JTextField getFieldFollowUser() {
        return this.fieldFollowUser;
    }

    public JTextField getFieldNewMessage() {
        return this.fieldNewMessage;
    }
}
