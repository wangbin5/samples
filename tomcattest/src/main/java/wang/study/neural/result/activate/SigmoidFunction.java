package wang.study.neural.result.activate;

/**
 * Created by Administrator on 2018/1/15.
 */
public class SigmoidFunction implements ActivateFunction{
    @Override
    public double activation(double value) {
        return 1.0/(1+Math.exp(-1.0*value));
    }

    @Override
    public double derivative(double value) {
        return -1*(1+Math.exp(-1.0*value));
    }
}
