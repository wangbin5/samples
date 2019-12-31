package com.study.algorithm.binarysearch;

public class FindFirst {



    public int search(int[] a, int n, int value) {
        int low = 0;
        int high = n - 1;
        while (low <= high) {
            int mid = low + ((high - low) >> 1);
            if (a[mid] >= value) {
                high = mid - 1;
                System.out.println("change high "+high);
            } else {
                low = mid + 1;
                System.out.println("change low "+low);
            }
        }
        System.out.println("result low "+low);
        if (low < n && a[low]==value){
            return low;
        }
        else return -1;
    }
}
