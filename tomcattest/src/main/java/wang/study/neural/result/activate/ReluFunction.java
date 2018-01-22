package wang.study.neural.result.activate;

/**
 * Created by Administrator on 2018/1/15.
 */
public class ReluFunction implements ActivateFunction{
    @Override
    public double activation(double value) {
        return value > 0 ? value : 0;
    }

    @Override
    public double derivative(double value) {

        return value>0 ? 1 : 0;
    }
}
