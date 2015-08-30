import java.util.ArrayList;

/**
 * Created by Fernando on 8/21/15.
 */

public class UserGroup extends Twitter {

    private static UserGroup root = new UserGroup();

    private UserGroup() {
        this.parent = null;
        this.uID = "Root";
        this.children = new ArrayList<Twitter>();
    }

    public UserGroup(String uID, UserGroup parentGroup) throws Exception {
        InvalidNameVisitor visitor = new InvalidNameVisitor();
        Twitter.bfs(visitor, UserGroup.getRootGroup());
        if (visitor.checkInvalidGroup(uID)) {
            throw new Exception("Groupname taken please choose another ");
        }
        this.parent = parentGroup;
        this.uID = uID;
        this.children = new ArrayList<Twitter>();
        this.parent.children.add(this);
        UserGroup.getRootGroup().notifyObservers();
    }

    public static UserGroup getRootGroup() {
        return root;
    }

    public void accept(Visitor visitor) {
        visitor.visitUserGroup(this);
    }
}

