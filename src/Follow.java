import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Fernando on 8/27/15.
 */
public class Follow implements ActionListener {
    private UserView userGUI;

    public Follow(UserView userGUI) {
        this.userGUI = userGUI;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = userGUI.getFieldFollowUser().getText();
        User user = User.finduID(name, UserGroup.getRootGroup());
        if (user == null) JOptionPane.showMessageDialog(userGUI, "Couldn't find user: " + name);
        try {
            userGUI.getUser().follow(user, userGUI.getObserver());
        } catch (Exception e1) {
            JOptionPane.showMessageDialog(userGUI, e1.getMessage() + ": " + name);
        }
        userGUI.getFieldFollowUser().setText("");
    }
}

