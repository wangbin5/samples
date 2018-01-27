package wang.study.neural.result.perceptron;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import wang.study.neural.result.DataSet;
import wang.study.neural.result.NeuralNetConfig;

import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * Created by Administrator on 2018/1/15.
 */
public class PerceptronTrainingTest {
    private PerceptronTraining training;
    private DataSet dataSet;
    @Before
    public void setUp() throws IOException {
        NeuralNetConfig config= new NeuralNetConfig();
        training = new PerceptronTraining();
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
            int inputLength = value.length-1;
            if(inputData == null){
                inputData = new double[lines.length][inputLength+1];
            }
            if(output == null ){
                output = new double[lines.length][1];
            }
            inputData[i]= new double[inputLength+1];
            for(int k=0;k<inputLength;k++){
                inputData[i][k] = toDouble(value[k]);
            }
            inputData[i][inputLength] = 1;
            output[i][0]= toDouble(value[value.length-1]);
        }
        DataSet dataSet = new DataSet(inputData,output);
        return dataSet;
    }

    private double toDouble(String s) {
        return Double.parseDouble(s);
    }

    @Test
    public void testOrTrain() throws IOException {
        dataSet = this.loadDataSet(new ClassPathResource("data/or_training_data.txt").getInputStream());
        training.train(dataSet);
        assertEquals(1.0,training.prediction(new double[]{1,1,1}),0.001);
        assertEquals(1.0,training.prediction(new double[]{1,0,1}),0.001);
        assertEquals(1.0,training.prediction(new double[]{0,1,1}),0.001);
        assertEquals(0,training.prediction(new double[]{0,0,1}),0.001);
    }
    @Test
    public void testAndTrain() throws IOException {
        dataSet = this.loadDataSet(new ClassPathResource("data/and_training_data.txt").getInputStream());
        training.train(dataSet);
        assertEquals(1.0,training.prediction(new double[]{1,1,1}),0.001);
        assertEquals(0.0,training.prediction(new double[]{1,0,1}),0.001);
        assertEquals(0.0,training.prediction(new double[]{0,1,1}),0.001);
        assertEquals(0,training.prediction(new double[]{0,0,1}),0.001);
    }

    @Test
    public void testXorTrain() throws IOException {
        dataSet = this.loadDataSet(new ClassPathResource("data/and_training_data.txt").getInputStream());
        training.train(dataSet);
        try{
            assertEquals(0.0,training.prediction(new double[]{1,1,1}),0.001);
            assertEquals(1.0,training.prediction(new double[]{1,0,1}),0.001);
            assertEquals(1.0,training.prediction(new double[]{0,1,1}),0.001);
            assertEquals(0,training.prediction(new double[]{0,0,1}),0.001);
            fail("xor 不能通过单个神经元网络训练出合适的模型。");

        }
        catch(AssertionError ex){

        }

    }
}