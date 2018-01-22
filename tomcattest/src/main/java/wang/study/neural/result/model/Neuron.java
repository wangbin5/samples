package wang.study.neural.result.model;

import java.util.Arrays;

/**
 * 神经元.
 */
public class Neuron {
    private double[] weight;
    public Neuron(int length) {
        this.weight = new double[length+1];
        for(int i=0;i<this.weight.length;i++){
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
            try{
                value+= weight[i]*input[i];
            }
            catch(RuntimeException ex){
                throw ex;
            }
        }
        return value;
    }

    public void addWeight(int i, double wi) {
        this.weight[i]-=wi;
    }

    @Override
    public String toString() {
        return "Neuron{" +
                "weight=" + Arrays.toString(weight) +
                '}';
    }
}
