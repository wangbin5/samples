package com.study.ml.regression.lib;

import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

/**
 * Created by Administrator on 2018/1/5.
 */
public class OLSMultipleLinearRegressionUsage {

    public static void main(String[] args){
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        double[] y = new double[]{11.0, 12.0, 13.0, 14.0, 15.0, 16.0};
        double[][] x = new double[6][];
        x[0] = new double[]{0, 0, 0, 0, 0};
        x[1] = new double[]{2.0, 0, 0, 0, 0};
        x[2] = new double[]{0, 3.0, 0, 0, 0};
        x[3] = new double[]{0, 0, 4.0, 0, 0};
        x[4] = new double[]{0, 0, 0, 5.0, 0};
        x[5] = new double[]{0, 0, 0, 0, 6.0};
        regression.newSampleData(y, x);
        double[] beta = regression.estimateRegressionParameters();
        for(double b : beta){
            System.out.println(b);
        }
    }
}
