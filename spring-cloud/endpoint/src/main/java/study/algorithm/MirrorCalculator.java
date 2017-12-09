package study.algorithm;

/**
 * Created by Administrator on 2017/10/13.
 */
public class MirrorCalculator {

    public TreeNode mirror(TreeNode node){
        TreeNode copy = new TreeNode();
        copy.setValue(node.getValue());
        TreeNode right = node.getRight();
        TreeNode left = node.getLeft();
        if(left!=null){
            copy.setLeft( mirror(left));
        }
        if(right!=null){
            copy.setRight(mirror(right));
        }
        return node;
    }

    public boolean equals(TreeNode node1,TreeNode node2){
        if(node1 == node2) return true;
        if(node1==null||node2 == null) return false;
        if(!node1.getValue().equals(node2.getValue())) return false;
        return equals(node1.getLeft(),node2.getLeft()) && equals(node1.getRight(),node2.getRight());
    }
}
