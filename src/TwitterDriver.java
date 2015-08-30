import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Fernando on 8/27/15.
 */
public class TwitterDriver {
    // Driver using Lazy Instantiation Singleton
    public static TwitterDriver driver = null;

    private TwitterDriver() {
    }

    public static TwitterDriver getInstance() {
        if (driver == null) {
            driver = new TwitterDriver();
        }
        return driver;
    }

    public static void main(String[] args) {
        List<UserGroup> userGroups = new ArrayList<UserGroup>();
        List<User> users = new ArrayList<User>();
        userGroups.add(UserGroup.getRootGroup());
        try {
            userGroups.add(new UserGroup("UserGroup1", userGroups.get(0)));
            userGroups.add(new UserGroup("UserGroup2", userGroups.get(1)));
        } catch (Exception e) {
            e.printStackTrace();
        }
        randomUsers(10, users, userGroups);
        randomMessages(5, users);
        randomFollowers(5, users);
        TwitterDriver.getInstance().start();
    }

    // GUI launcher
    public void start() {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    AdminControlPanel.getInstance().setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private static void randomUsers(Integer num, List<User> users, List<UserGroup> groups) {
        for (Integer i = 0; i < num; i++) {
            try {
                User temp = new User("@User" + i, groups.get(new Random().nextInt(groups.size())));
                users.add(temp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void randomMessages(Integer num, List<User> users) {
        String[] words = {"Happy", "Sad", "Good", "Great", "Excellent", "Força Barça",
                "Excited", ":(", ":)", ">:(", "Hungry", "Bored", "Visca Barça"};
        Random rand = new Random();
        for (User user : users) {
            for (Integer i = 0; i < num; i++) {
                user.tweet(words[rand.nextInt(words.length)]);
            }
        }
    }

    private static void randomFollowers(int num, List<User> users) {
        for (User user : users) {
            List<User> followers = new ArrayList<User>();
            followers.add(user);
            for (Integer i = 0; i < num; i++) {
                User u;
                do {
                    u = users.get(new Random().nextInt(users.size()));
                } while (followers.contains(u));
                User r = u;
                followers.add(r);
                try {
                    user.follow(r, null);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
