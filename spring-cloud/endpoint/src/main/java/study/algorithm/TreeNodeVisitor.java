package study.algorithm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/10/13.
 */
public class TreeNodeVisitor {
    private List<List<String>> values = new ArrayList<List<String>>();

    public void visit(TreeNode root){
        visitInternal(root,0);
    }

    public void visitInternal(TreeNode node ,int level){
        List<String> levelValues = this.values.get(level);
        if(levelValues == null){
            levelValues = new ArrayList<String>();
            values.add(level,levelValues);
        }

    }

}
