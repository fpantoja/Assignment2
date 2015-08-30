import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Fernando on 8/21/15.
 */

public class Tweet implements Comparable<Tweet>, ActionListener {
    private static int count = 0;
    private int id = ++count;
    private String message;
    private UserView userGUI;

    public Tweet(String message) {
        this.message = message;
    }

    public boolean hasPositiveMessage() {
        String[] positives = {"Happy", "Good", "Great", "Excellent",
                "Excited", ":)"};
        for (String positive : positives) {
            if (message.contains(positive)) {
                return true;
            }
        }
        return false;
    }

    public int compareTo(Tweet comparePost) {
        return this.id - comparePost.id;
    }

    public String toString() {
        return this.message;
    }

    public Tweet(UserView userGUI) {
        this.userGUI = userGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        userGUI.getUser().tweet(userGUI.getFieldNewMessage().getText());
        userGUI.getFieldNewMessage().setText("");
    }
}
