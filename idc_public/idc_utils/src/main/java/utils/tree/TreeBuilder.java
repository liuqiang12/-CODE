package utils.tree;

import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mylove on 2016/11/28.
 */

public class TreeBuilder {
    @SuppressWarnings("unchecked")
    public static List<TreeNode> buildListToTree(List<TreeNode> sNodes) {
        return newBuildListToTree(sNodes);
    }
    public static <T extends TreeNode> List<T> formatTree(List<T> list) {
        return newBuildListToTree(list);
    }
    public static <T extends TreeNode> List<T> newBuildListToTree(List<T> sNodes) {
        int i, l;
        List r = new ArrayList();
        if (sNodes != null && !sNodes.isEmpty()) {
            Map<String, T> tmpMap = new HashMap<String, T>();
            for (i = 0, l = sNodes.size(); i < l; i++) {
                tmpMap.put(sNodes.get(i).getId(), sNodes.get(i));
            }
            for (i = 0, l = sNodes.size(); i < l; i++) {
                TreeNode node = sNodes.get(i);
                if(i == 0 ){
                    System.out.println(node.getName());
                }
                if (tmpMap.get(node.getParentId()) != null && node.getId() != node.getParentId()) {
                    List childen = tmpMap.get(node.getParentId()).getChildren();
                    if (childen == null){
                        childen = new ArrayList<T>();
                    }
                    childen.add(sNodes.get(i));
                    tmpMap.get(sNodes.get(i).getParentId()).setChildren(childen);
                } else {
                    r.add(sNodes.get(i));
                }
            }
        }
        return r;
    }
//    public static List<TreeNode> newBuildListToTree(List<TreeNode> sNodes) {
//        int i, l;
//        List r = new ArrayList();
//        if (sNodes != null && !sNodes.isEmpty()) {
//            Map<String, TreeNode> tmpMap = new HashMap<String, TreeNode>();
//            for (i = 0, l = sNodes.size(); i < l; i++) {
//                tmpMap.put(sNodes.get(i).getId(), sNodes.get(i));
//            }
//            for (i = 0, l = sNodes.size(); i < l; i++) {
//                TreeNode node = sNodes.get(i);
//                if(i == 0 ){
//                	System.out.println(node.getName());
//                }
//                if (tmpMap.get(node.getParentId()) != null && node.getId() != node.getParentId()) {
//                    List<TreeNode> childen = tmpMap.get(node.getParentId()).getChildren();
//                    if (childen == null)
//                        childen = new ArrayList<TreeNode>();
//                    childen.add(sNodes.get(i));
//                    tmpMap.get(sNodes.get(i).getParentId()).setChildren(childen);
//                } else {
//                    r.add(sNodes.get(i));
//                }
//            }
//        }
//        return r;
//    }

    private static <T extends TreeNode> List<T> findRoots(List<T> allNodes) {
        List<T> results = new ArrayList<T>();
        for (T node : allNodes) {
            boolean isRoot = true;
            for (T comparedOne : allNodes) {
                if (node.getParentId() == comparedOne.getId()) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                node.setLevel(0);
                results.add(node);
                node.setRootId(node.getId());
            }
        }
        return results;
    }

//    public static <T extends TreeNode> T findTreeNodeById(List<T> allNodes, int id) {
//        for (T allNode : allNodes) {
//            T node = allNode.findTreeNodeById(id);
//            if (node != null) {
//                return node;
//            }
//        }
//        return null;
//    }

    @SuppressWarnings("unchecked")
    public static List<TreeNode> findChildren(TreeNode root, List<TreeNode> allNodes) {
        List<TreeNode> children = new ArrayList<TreeNode>();

        for (TreeNode comparedOne : allNodes) {
            if (comparedOne.getParentId() == root.getId()) {
                comparedOne.setParent(root);
                comparedOne.setLevel(root.getLevel() + 1);
                children.add(comparedOne);
            }
        }
        List<TreeNode> notChildren = (List<TreeNode>) CollectionUtils.subtract(allNodes, children);
        for (TreeNode child : children) {
            List<TreeNode> tmpChildren = findChildren(child, notChildren);
            if (tmpChildren == null || tmpChildren.size() < 1) {
                child.setLeaf(true);
            } else {
                child.setLeaf(false);
            }
            child.setChildren(tmpChildren);
        }
        return children;
    }

    public static void main(String[] args) {
        TreeBuilder tb = new TreeBuilder();
        List<TreeNode> allNodes = new ArrayList<TreeNode>();
//        allNodes.add(new TreeNode(0, -1, "节点-1"));
//        allNodes.add(new TreeNode(1, 0, "节点1"));
//        allNodes.add(new TreeNode(2, 0, "节点2"));
//        allNodes.add(new TreeNode(3, 0, "节点3"));
//        allNodes.add(new TreeNode(11, 7, "节点11"));
//        allNodes.add(new TreeNode(4, 1, "节点4"));
//        allNodes.add(new TreeNode(5, 1, "节点5"));
//        allNodes.add(new TreeNode(6, 1, "节点6"));
//        allNodes.add(new TreeNode(7, 4, "节点7"));
//        allNodes.add(new TreeNode(8, 4, "节点8"));
//        allNodes.add(new TreeNode(9, 5, "节点9"));
//        allNodes.add(new TreeNode(10, 100, "节点10"));
//        List<TreeNode> roots = tb.buildListToTree(allNodes);
//        for (TreeNode root : roots) {
//            TreeNode te = root.findTreeNodeById(1);
//            System.out.println(te.getJuniors());
//        }
//        System.out.println();
    }
}
