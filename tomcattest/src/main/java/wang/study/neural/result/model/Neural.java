package wang.study.neural.result.model;

/**
 * 神经元.
 */
public class Neural {
    private double[] weight;

    public Neural(int length) {
        this.weight = new double[length];
        for(int i=0;i<length;i++){
            this.weight[i] = 0.5;
        }
    }

    public double[] getWeight() {
        return weight;
    }

    public void setWeight(double[] weight) {
        this.weight = weight;
    }

    public double calculate(double[] input) {
        double value = 0.0;
        for(int i=0;i<this.weight.length;i++){
            value+= weight[i]*input[i];
        }
        return value;
    }

    public void addWeight(int i, double wi) {
        this.weight[i]+=wi;
    }
}
