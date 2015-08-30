import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultTreeModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Fernando on 8/27/15.
 */

public class AdminControlPanel extends JFrame implements TreeSelectionListener {
    // AdminControlPanel using Lazy Instantiation Singleton
    private static AdminControlPanel instance = null;
    public static AdminControlPanel getInstance() {
        if(instance == null){
            instance = new AdminControlPanel();
        }
        return instance;
    }

    private JButton jbAddUser;
    private JTextField jtAddUser;
    private JButton jbAddGroup;
    private JTextField jtAddGroup;
    private JButton jbUserView;
    private JLabel jlTweeterSelected;
    private JLabel jlStats;
    private JTree jTreeView;
    private Twitter selectedTweeter;

    private AdminControlPanel() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 500, 350);
        setTitle("Admin Control Panel");

        JPanel contentPanel = new JPanel();
        contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPanel);
        contentPanel.setLayout(null);
        // JTree
        jTreeView = new JTree(new DefaultTreeModel(UserGroup.getRootGroup()));
        jTreeView.addTreeSelectionListener(this);
        JScrollPane treeScroll = new JScrollPane();
        treeScroll.setBounds(20, 8, 155, 307);
        treeScroll.setViewportView(jTreeView);
        contentPanel.add(treeScroll);
        Observer obs = new RootObserver(jTreeView);
        UserGroup.getRootGroup().attach(obs);
        // JTextField to Add User
        jtAddUser = new JTextField("     User Id");
        jtAddUser.setEnabled(false);
        jtAddUser.setBounds(190, 5, 140, 30);
        contentPanel.add(jtAddUser);
        jtAddUser.setColumns(10);
        // JButton to Add User
        jbAddUser = new JButton("Add User");
        jbAddUser.setEnabled(false);
        jbAddUser.setBounds(340, 5, 140, 30);
        jbAddUser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = AdminControlPanel.getInstance().getFieldAddUser().getText();
                AdminControlPanel.getInstance().getFieldAddUser().setText("");
                try {
                    new User(name, (UserGroup) AdminControlPanel.getInstance().getSelectedEntity());
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(AdminControlPanel.getInstance(), e1.getMessage() + ": " + name);
                }
            }
        });
        contentPanel.add(jbAddUser);
        // JTextField to Add Group
        jtAddGroup = new JTextField("     Group Id");
        jtAddGroup.setEnabled(false);
        jtAddGroup.setBounds(190, 40, 140, 30);
        contentPanel.add(jtAddGroup);
        jtAddGroup.setColumns(10);
        // JButton to Add Group
        jbAddGroup = new JButton("Add Group");
        jbAddGroup.setEnabled(false);
        jbAddGroup.setBounds(340, 40, 140, 30);
        jbAddGroup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = AdminControlPanel.getInstance().getFieldAddGroup().getText();
                AdminControlPanel.getInstance().getFieldAddGroup().setText("");
                try {
                    new UserGroup(name, (UserGroup) AdminControlPanel.getInstance().getSelectedEntity());
                } catch (Exception e1) {
                    JOptionPane.showMessageDialog(AdminControlPanel.getInstance(), e1.getMessage() + ": " + name);
                }
            }
        });
        contentPanel.add(jbAddGroup);
        // JButton to Open User View
        jbUserView = new JButton("Open User View");
        jbUserView.setEnabled(false);
        jbUserView.setBounds(190, 80, 290, 30);
        jbUserView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UserView userFrame = new UserView((User) AdminControlPanel.getInstance().getSelectedEntity());
                    userFrame.setVisible(true);
                } catch (Exception err) {
                    err.printStackTrace();
                }
            }
        });
        contentPanel.add(jbUserView);
        // JButton to get User Total
        JButton btnTotalUsers = new JButton("User Total");
        btnTotalUsers.setBounds(190, 255, 140, 30);
        btnTotalUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowUserTotal visitor = new ShowUserTotal();
                Twitter.bfs(visitor, UserGroup.getRootGroup());
                AdminControlPanel.getInstance().getLblMessage().setText("Total users: "+visitor.getUsers());
            }
        });
        contentPanel.add(btnTotalUsers);
        // JButton to get Group Total
        JButton btnTotalGroups = new JButton("Group Total");
        btnTotalGroups.setBounds(340, 255, 140, 30);
        btnTotalGroups.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowGroupTotal visitor = new ShowGroupTotal();
                Twitter.bfs(visitor, UserGroup.getRootGroup());
                AdminControlPanel.getInstance().getLblMessage().setText("Total groups: "+visitor.getGroups());
            }
        });
        contentPanel.add(btnTotalGroups);
        // JButton to get Messages Total
        JButton btnTotalTweets = new JButton("Messages Total");
        btnTotalTweets.setBounds(190, 290, 140, 30);
        btnTotalTweets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowMessagesTotal visitor = new ShowMessagesTotal();
                Twitter.bfs(visitor, UserGroup.getRootGroup());
                AdminControlPanel.getInstance().getLblMessage().setText("Total tweets: "+visitor.getTweets());
            }
        });
        contentPanel.add(btnTotalTweets);
        // JButton to get Positive Percentage
        JButton btnPositiveTweets = new JButton("Positive Percentage");
        btnPositiveTweets.setBounds(340, 290, 140, 30);
        btnPositiveTweets.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShowPositivePercentage visitor = new ShowPositivePercentage();
                Twitter.bfs(visitor, UserGroup.getRootGroup());
                AdminControlPanel.getInstance().getLblMessage().setText("Percent of positive tweets: "+visitor.getPercentPositiveTweets()+"%");
            }
        });
        contentPanel.add(btnPositiveTweets);
        // JLabel for Selected Twitter User/Group
        jlTweeterSelected = new JLabel("");
        // JLabel to Display Stats
        jlStats = new JLabel("");
        jlStats.setBounds(200, 200, 256, 30);
        contentPanel.add(jlStats);
    }
    // Rules to prevent crashing/invalid actions
    @Override
    public void valueChanged(TreeSelectionEvent e) {
        selectedTweeter = (Twitter)jTreeView.getLastSelectedPathComponent();
        if (selectedTweeter != null) {
            jlTweeterSelected.setText(selectedTweeter.toString());
            if (selectedTweeter.isLeaf()) {
                jbUserView.setEnabled(true);
                jbAddUser.setEnabled(false);
                jbAddGroup.setEnabled(false);
                jtAddUser.setEnabled(false);
                jtAddGroup.setEnabled(false);
            } else {
                jbUserView.setEnabled(false);
                jbAddUser.setEnabled(true);
                jbAddGroup.setEnabled(true);
                jtAddUser.setEnabled(true);
                jtAddGroup.setEnabled(true);
            }
        }
        else {
            jlTweeterSelected.setText("");
            jbUserView.setEnabled(false);
            jbAddUser.setEnabled(false);
            jbAddGroup.setEnabled(false);
            jtAddUser.setEnabled(false);
            jtAddGroup.setEnabled(false);
        }
    }

    public JLabel getLblMessage() {
        return jlStats;
    }

    public JTextField getFieldAddUser() {
        return jtAddUser;
    }

    public JTextField getFieldAddGroup() {
        return jtAddGroup;
    }

    public Twitter getSelectedEntity() {
        return selectedTweeter;
    }
}
