package wang.study.neural.result;

/**
 * Created by Administrator on 2018/1/10.
 */
public class NeuralNetConfig {
    //最大训练次数
    private int maxEpochs;
    //目标误差
    private double targetError;

    /**
     *
     * 增长率
     */
    private double growthRate;

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
}
