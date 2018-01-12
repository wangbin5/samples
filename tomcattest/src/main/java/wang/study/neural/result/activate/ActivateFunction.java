package wang.study.neural.result.activate;

/**
 * 激活函数
 * @author wang
 */
public interface ActivateFunction {
    /**
     * 激活函数
     * @param value
     * @return
     */
    double activation(double value);
    /**
     * 激活函数的一阶导数
     * @param value
     * @return
     */
    double derivative(double value);
}
