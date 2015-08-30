import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreeNode;

/**
 * Created by Fernando on 8/21/15.
 */

public abstract class Twitter implements MutableTreeNode {
    protected String uID;
    protected UserGroup parent;
    protected List<Twitter> children = null;
    private List<Observer> observers = new ArrayList<Observer>();

    public void attach(Observer observer) {
        this.observers.add(observer);
    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update(this);
        }
    }

    public static void bfs(Visitor visitor, UserGroup parent) {
        parent.accept(visitor);
        for (Twitter twitter : parent.children) {
            if (twitter.getAllowsChildren()) {
                bfs(visitor, (UserGroup) twitter);
            } else {
                twitter.accept(visitor);
            }
        }
    }

    // implementing / Overriding Abstract Methods of MutableTreeNode
    public abstract void accept(Visitor visitor);

    @Override
    public String toString() {
        return this.uID;
    }

    public String getuID() {
        return this.uID;
    }

    public int compareTo(Twitter b) {
        if ((b instanceof User && this instanceof User) || (b instanceof UserGroup && this instanceof UserGroup)) {
            return this.uID.compareTo(b.uID);
        } else {
            if (this instanceof UserGroup) {
                return 1;
            } else {
                return -1;
            }
        }
    }

    @Override
    public void setUserObject(Object object) {
    }

    @Override
    public Twitter getParent() {
        return parent;
    }

    @Override
    public int getIndex(TreeNode node) {
        return -1;
    }

    @Override
    public void setParent(MutableTreeNode newParent) {
        this.parent = (UserGroup) newParent;
    }

    @Override
    public void removeFromParent() {
        this.parent.children.remove(this);
    }

    @Override
    public boolean isLeaf() {
        return this.children == null;
    }

    @Override
    public Twitter getChildAt(int childIndex) {
        return this.isLeaf() ? null : this.children.get(childIndex);
    }

    @Override
    public int getChildCount() {
        return this.getAllowsChildren() ? this.children.size() : 0;
    }

    @Override
    public boolean getAllowsChildren() {
        return !this.isLeaf();
    }

    @Override
    public Enumeration<Twitter> children() {
        return this.isLeaf() ? null : java.util.Collections.enumeration(this.children);
    }

    @Override
    public void remove(int index) {
        if (this.getAllowsChildren()) this.children.remove(index);
    }

    @Override
    public void insert(MutableTreeNode child, int index) {
        if (this.getAllowsChildren()) this.children.add(index, (UserGroup) child);
    }

    @Override
    public void remove(MutableTreeNode node) {
        if (this.getAllowsChildren()) this.children.remove((UserGroup) node);
    }
}
