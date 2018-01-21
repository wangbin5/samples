package wang.study.neural.result.activate;

/**
 * Created by Administrator on 2018/1/15.
 */
public class HardLimitingFunction implements ActivateFunction{
    @Override
    public double activation(double value) {
        return value >= 0 ? 1 : 0;
    }

    @Override
    public double derivative(double value) {
        return 1;
    }
}
