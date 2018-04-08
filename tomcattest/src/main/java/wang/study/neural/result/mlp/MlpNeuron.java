package wang.study.neural.result.mlp;

/**
 * Created by Administrator on 2018/1/23.
 */
public class MlpNeuron {
    private double[] w;
    private double b;
    private double th;

    public MlpNeuron(int length){
        w = new double[length];
        for(int i=0;i<this.w.length;i++){
            w[i] = 0.5;
        }
        b = 0.5;
    }
    //return wx+b
    public double calculate(double[] input){
        double value = 0.0;
        for(int i=0;i<this.w.length;i++){
            value+= w[i]*input[i];
        }
        value = value +b;
        return value;
    }


    public double[] getW() {
        return w;
    }

    public void setW(double[] w) {
        this.w = w;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    public double getTh() {
        return th;
    }

    public void setTh(double th) {
        this.th = th;
    }

    public double getWeight(int i) {
        if(this.w.length == i){
            return b;
        }
        else{
            return this.w[i];
        }
    }

    public void addWeight(int j, double v) {
        if(this.w.length == j){
            b += v;
        }
        else{
            w[j]+=v;
        }
    }
}
