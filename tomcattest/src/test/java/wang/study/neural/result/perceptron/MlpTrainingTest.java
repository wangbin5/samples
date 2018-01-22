package wang.study.neural.result.perceptron;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import wang.study.neural.result.DataSet;
import wang.study.neural.result.NeuralNetConfig;
import wang.study.neural.result.activate.HardLimitingFunction;
import wang.study.neural.result.activate.ReluFunction;
import wang.study.neural.result.activate.SigmoidFunction;
import wang.study.neural.result.mlp.MlpTraining;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Created by Administrator on 2018/1/15.
 */
public class MlpTrainingTest {
    private MlpTraining training;
    private DataSet dataSet;
    @Before
    public void setUp() throws IOException {
        NeuralNetConfig config= new NeuralNetConfig();
        config.setActivateFunction(new SigmoidFunction());
        training = new MlpTraining();
        training.setNeuralNetConfig(config);

    }

    private DataSet loadDataSet(InputStream input) throws IOException {

        String content = IOUtils.toString(input,"utf-8");
        String[] lines = content.split("\\r\\n");
        double[][] inputData = null;
        double[][] output = null;
        for(int i=0;i<lines.length;i++){
            String line = lines[i];
            String[] value = line.split(" ");
            int inputLength = value.length-2;
            if(inputData == null){
                inputData = new double[lines.length][inputLength];
            }
            if(output == null ){
                output = new double[lines.length][2];
            }
            inputData[i]= new double[inputLength];
            for(int k=0;k<inputLength;k++){
                inputData[i][k] = toDouble(value[k]);
            }
            for(int k=inputLength;k<value.length;k++){
                output[i][k-inputLength]= toDouble(value[k]);
            }
        }
        DataSet dataSet = new DataSet(inputData,output);
        return dataSet;
    }

    private double toDouble(String s) {
        return Double.parseDouble(s);
    }

    @Test
    public void testMlp() throws IOException {
        dataSet = this.loadDataSet(new ClassPathResource("data/mlp.txt").getInputStream());
        training.train(dataSet);

        System.out.println(training);
    }
}