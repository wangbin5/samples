package com.study.algorithm.redblacktree;


import lombok.Getter;
import lombok.Setter;

public class Tree {

    private TreeNode root;

    public void visit(Visitor visitor){
        this.visit(root,visitor);

    }

    private void visit(TreeNode node, Visitor visitor) {
        if(node ==null){
            return;
        }
        visitor.visit(node);
        visit(node.getLeftChild(),visitor);
        visit(node.getRightChild(),visitor);
    }

    public TreeNode insert(int value) {
        if(this.root == null){
            this.root = new TreeNode();
            this.root.setValue(value);
            return this.root;
        }
        TreeNode parent = findParentForInsert(this.root,value);
        TreeNode current= new TreeNode();
        current.setValue(value);
        current.setBlack(false);
        if(parent.getValue() >= value && parent.getLeftChild() == null){
            parent.changeLeftChild(current);

        }
        else{
            parent.changeRightChild(current);
        }
        fixAfterInsert(current);
        this.root.setBlack(true);
        return current;

    }

    private void fixAfterInsert(TreeNode current) {
        if(isBlack(current.getParent())){
            return;
        }
        //condition 1
        TreeNode parentNeighbour = getNeighbour(current.getParent());
        if(!isBlack(parentNeighbour)){
            parentNeighbour.setBlack(true);
            current.getParent().setBlack(true);
            current.getParent().getParent().setBlack(false);
            fixAfterInsert(current.getParent().getParent());
            return;
        }

        if(isRightChild(current)){
            TreeNode parent = current.getParent();
            rotateLeft(current.getParent());
            fixAfterInsert(parent);
            return;
        }


        if(isLeftChild(current)){
            if(isLeftChild(current.getParent())){
                TreeNode parent = current.getParent();
                TreeNode ancestor = current.getParent().getParent();
                rotateRight(ancestor);
                ancestor.setBlack(false);
                parent.setBlack(true);
            }
            else{
                TreeNode parent = current.getParent();
                TreeNode ancestor = current.getParent().getParent();
                rotateLeft(ancestor);
                ancestor.setBlack(false);
                parent.setBlack(true);
                this.fixAfterInsert(current);
            }

        }



    }

    private void rotateRight(TreeNode node) {
        TreeNode left = node.getLeftChild();
        node.changeLeftChild(left.getRightChild());
        if(node.getParent()!=null){
            node.getParent().changeNode(node,left);
        }
        left.changeRightChild(node);
        if(node == this.root){
            this.root = left;
        }
    }


    private void rotateLeft(TreeNode node) {
        TreeNode other  = node.getRightChild();
        node.changeRightChild(other.getLeftChild());
        if(node.getParent()!=null){
            node.getParent().changeNode(node,other);
        }
        other.changeLeftChild(node);
        if(node == this.root){
            this.root = other;
        }
    }

    private boolean isRightChild(TreeNode current) {
        return current.getParent().getRightChild() == current;
    }

    private boolean isLeftChild(TreeNode current) {
        return !isRightChild(current);
    }

    public TreeNode getNeighbour(TreeNode node){
        if(node.getParent() == null){
            return null;
        }
        if(node.getParent().getLeftChild() == node){
            return node.getParent().getRightChild();
        }
        return node.getParent().getLeftChild();
    }


    public boolean isBlack(TreeNode node){
        return node == null || node.isBlack();
    }

    private TreeNode findParentForInsert(TreeNode parent,int value) {

        if(parent.getValue() >= value){
            if(parent.getLeftChild()!=null) {
                return findParentForInsert(parent.getLeftChild(),value);
            }
            else{
                return parent;
            }
        }
        else if(parent.getRightChild() == null){
            return parent;
        }
        return findParentForInsert(parent.getRightChild(),value);
    }

    public boolean isBlackEqual() {
        if(this.root == null){
            return true;
        }
        return getBlackHeight(this.root.getLeftChild()) == getBlackHeight(this.root.getRightChild());
    }

    private int getBlackHeight(TreeNode node) {
        if(node == null){
            return 1;
        }
        int leftHeight = getBlackHeight(node.getLeftChild());
        int rightHeight = getBlackHeight(node.getRightChild());
        assert(leftHeight == rightHeight);
        int count = isBlack(node) ?  1 : 0;
        return count+leftHeight;
    }

    @Getter
    @Setter
    static class TreeNode{
        private boolean black = true;
        private int value;

        private TreeNode parent;
        private TreeNode leftChild;
        private TreeNode rightChild;

        public void changeLeftChild(TreeNode current) {
            this.leftChild = current;
            if(current!=null){
                current.setParent(this);
            }
        }

        public void changeRightChild(TreeNode current) {
            this.rightChild = current;
            if(current!=null){
                current.setParent(this);
            }
        }

        public void changeNode(TreeNode source, TreeNode target) {
            if(this.getLeftChild() == source){
                this.changeLeftChild(target);
            }
            if(this.getRightChild() == source){
                this.changeRightChild(target);
            }
        }
    }

    static class Visitor{
        public void visit(TreeNode node){
            StringBuilder result = new StringBuilder();
            result.append(node.getValue()+" -- black "+ node.isBlack());
            if(node.getLeftChild()!=null){
                result.append(";left - "+node.getLeftChild().getValue()+",black "+node.getLeftChild().isBlack());
            }
            if(node.getRightChild()!=null){
                result.append(";right - "+node.getRightChild().getValue()+",black "+node.getRightChild().isBlack());
            }
            System.out.println(result.toString());
        }
    }
}
