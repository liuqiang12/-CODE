package utils.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2016/11/28.
 */

public class TreeNode implements java.io.Serializable {


    protected String id;

    protected String parentId;

    protected TreeNode parent;

    protected List<TreeNode> children = new ArrayList<TreeNode>();

    protected String name;

    protected int level;

    protected int sort;

    protected String rootId;

    protected String type;

    protected String text;

    public String getText() {
        return this.getName();
    }

    protected boolean isLeaf;
    protected boolean open = false;


    protected String description;
    /***
     * 自身真是对象
     */
    protected Object self;

//    public Object getSelf() {
//        return self;
//    }
//
//    public void setSelf(Object self) {
//        this.self = self;
//    }

    //    private Object self;
    public Object getSelf() {
        return self;
    }

    public void setSelf(Object self) {
        this.self = self;
    }

    public Map<String, Object> getAttrs() {
        return attrs;
    }

    public void setAttrs(Map<String, Object> attrs) {
        this.attrs = attrs;
    }

    public void addAttr(String attrName, Object o) {
        this.attrs.put(attrName, o);
    }

    protected Map<String, Object> attrs = new HashMap<String, Object>();

    public TreeNode() {
        super();
    }

    public TreeNode(String id, String parentId, String name) {
        super();
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TreeNode getParent() {
        return parent;
    }

    public void setParent(TreeNode parent) {
        this.parent = parent;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getType() {
        return type;
    }

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isLeaf() {
        return isLeaf;
    }

    public void setLeaf(boolean isLeaf) {
        this.isLeaf = isLeaf;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getRootId() {
        return rootId;
    }

    public void setRootId(String rootId) {
        this.rootId = rootId;
    }

//    @Override
//    public int hashCode() {
//        final int prime = 31;
//        int result = 1;
//        result = prime * result + id;
//        result = prime * result + parentId;
//        return result;
//    }

    public List<TreeNode> getElders() {
        List<TreeNode> elderList = new ArrayList<TreeNode>();
        TreeNode parentNode = this.getParent();
        if (parentNode == null) {
            return elderList;
        } else {
            elderList.add(parentNode);
            elderList.addAll(parentNode.getElders());
            return elderList;
        }
    }

    public List<TreeNode> getJuniors() {
        List<TreeNode> juniorList = new ArrayList<TreeNode>();
        List<TreeNode> childList = this.getChildren();
        if (childList == null) {
            return juniorList;
        } else {
            int childNumber = childList.size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode junior = childList.get(i);
                juniorList.add(junior);
                juniorList.addAll(junior.getJuniors());
            }
            return juniorList;
        }
    }

    public TreeNode findTreeNodeById(int id) {
        if (this.getId().equals(id)) {
            return this;
        }
        if (getChildren() == null || getChildren().isEmpty()) {
            return null;
        } else {
            int childNumber = getChildren().size();
            for (int i = 0; i < childNumber; i++) {
                TreeNode child = getChildren().get(i);
                TreeNode resultNode = child.findTreeNodeById(id);
                if (resultNode != null) {
                    return resultNode;
                }
            }
            return null;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TreeNode other = (TreeNode) obj;
        if (id != other.id)
            return false;
        if (parentId != other.parentId)
            return false;
        return true;
    }

    public boolean isOpen() {
        return open;
    }

    public void setOpen(boolean open) {
        this.open = open;
    }

    @Override
    public String toString() {
        return "Node {id=" + id + ", parentId=" + parentId + ", children=" + children + ", name=" + name + ", level ="
                + level + "}";
    }
}