package wang.study.neural.result.activate;

/**
 * Created by Administrator on 2018/1/15.
 */
public class SigmoidFunction implements ActivateFunction{
    @Override
    public double activation(double value) {
        double result  = 1.0/(1+Math.exp(-1.0*value));
        if(Double.isNaN(result)){
            return 0.0;
        }
        return result;

    }

    @Override
    public double derivative(double v) {
        return (v * (1.0 - v));
    }
}
