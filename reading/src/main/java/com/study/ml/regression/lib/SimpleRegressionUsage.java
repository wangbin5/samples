package com.study.ml.regression.lib;

import org.apache.commons.math3.stat.regression.SimpleRegression;

/**
 * Created by Administrator on 2018/1/5.
 */
public class SimpleRegressionUsage {
    public static void main(String[] args){
        SimpleRegression regression = new SimpleRegression();
        regression.addData(1d, 2d);
        regression.addData(3d, 3d);
        regression.addData(3d, 3d);

        double[][] data = { { 1, 3 }, {2, 5 }, {3, 7 }, {4, 14 }, {5, 11 }};
        regression.addData(data);

        System.out.println(regression.getIntercept());
        System.out.println(regression.getSlope());
    }
}
