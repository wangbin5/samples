package com.study.algorithm.binarysearch;

import org.junit.jupiter.api.Test;

import static org.springframework.test.util.AssertionErrors.assertEquals;


public class FindFirstTest {

    @Test
    public void search(){
        int[] values = new int[]{1,2,3,4,5};
        int result = new FindFirst().search(values,values.length,3);
        assertEquals("查找到的坐标",2,result);
    }
}