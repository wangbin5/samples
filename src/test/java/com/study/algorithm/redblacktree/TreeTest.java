package com.study.algorithm.redblacktree;


import org.junit.Test;

import static org.junit.Assert.assertTrue;

public class TreeTest {

    @Test
    public void test(){
        Tree tree = new Tree();
        for(int i=0;i<100;i++){
            tree.insert(i);
            assertTrue("黑节点数量相等",tree.isBlackEqual());
        }

    }
}