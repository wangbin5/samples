package wang.study.neural.result;

import wang.study.neural.result.activate.ActivateFunction;
import wang.study.neural.result.activate.HardLimitingFunction;

/**
 * Created by Administrator on 2018/1/10.
 */
public class NeuronNetConfig {
    /**
     * 激活函数
     */
    private ActivateFunction activateFunction = new HardLimitingFunction();
    /**
     * 最大训练次数
     */
    private int maxEpochs =1000;
    /**
     *目标误差
     */
    private double targetError=0.001;
    /**
     *
     * 增长率
     */
    private double growthRate=0.1;

    public int getMaxEpochs() {
        return maxEpochs;
    }

    public void setMaxEpochs(int maxEpochs) {
        this.maxEpochs = maxEpochs;
    }

    public double getTargetError() {
        return targetError;
    }

    public void setTargetError(double targetError) {
        this.targetError = targetError;
    }

    public double getGrowthRate() {
        return growthRate;
    }

    public void setGrowthRate(double growthRate) {
        this.growthRate = growthRate;
    }

    public ActivateFunction getActivateFunction() {
        return activateFunction;
    }

    public void setActivateFunction(ActivateFunction activateFunction) {
        this.activateFunction = activateFunction;
    }
}
