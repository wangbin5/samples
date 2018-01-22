package wang.study.neural.result;

/**
 *  数据集
 */
public class DataSet {
    private double[][] input;
    private double[][] output;

    public DataSet(double[][] input, double[][] output) {
        this.input = input;
        this.output = output;
    }

    public double[][] getInput() {
        return input;
    }

    public void setInput(double[][] input) {
        this.input = input;
    }

    public double[][] getOutput() {
        return output;
    }

    public void setOutput(double[][] output) {
        this.output = output;
    }

    public int getRows() {
        return input.length;
    }
    public int getCols() {
        return input[0].length;
    }
    public double[] getInputData(int i) {
        return this.input[i];
    }

    public double getSingleOutputData(int i) {
        return this.output[i][0];
    }

    public double[] getOutputData(int i) {
        return this.output[i];
    }
}
