package wang.study.neural.result.mlp;

import wang.study.neural.result.NeuralNetConfig;
import wang.study.neural.result.activate.ActivateFunction;
import wang.study.neural.result.model.Neuron;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//层
public class MlpLayer {

	private List<Neuron> neurons = new ArrayList<>();
	private ActivateFunction activateFunction;
	private NeuralNetConfig neuralNetConfig;
	private MlpLayer next;
	private MlpLayer last;
	private boolean stop;
	private double[] sums;
	private double[] input;
	public MlpLayer(int size,MlpLayer last,NeuralNetConfig neuralNetConfig){
		this.activateFunction = neuralNetConfig.getActivateFunction();
		if(last!=null){
			last.next = this;
		}
		this.last = last;
		this.neuralNetConfig = neuralNetConfig;
		for(int i=0;i<size;i++){
			neurons.add(new Neuron(size));
		}
	}




    public boolean train(double[] input, double[] result){
		this.input = input;
		sums = calculateSum(input);
		double[] calresults = this.calculateResults(sums);
		if(this.next!=null){
			return this.next.train(appendLastOne(calresults),result);
		}
		else{
			double error = calculateError(calresults,result);
			System.out.println("mse is "+error);
			if(Math.abs(error) < this.neuralNetConfig.getTargetError()){
				return true;
			}
			//修正权值
			double[] ths = new double[this.neurons.size()];
			for(int i=0;i<this.neurons.size();i++){
				double thi = (calresults[i]-result[i])*this.activateFunction.derivative(sums[i]);
				for(int j=0;j<input.length;j++){
					this.neurons.get(i).addWeight(j,input[j]*thi*this.neuralNetConfig.getGrowthRate());
				}
				ths[i] = thi;
			}
			if(this.last!=null){
				this.last.back(ths,this.neurons);
			}
			//计算
			return false;
		}
	}

	private double[] appendLastOne(double[] results) {
    	double[] append = new double[results.length+1];
    	for( int i=0;i<results.length;i++){
    		append[i] = results[i];
		}
		append[append.length-1] = 1;
		return append;
	}

	private void back(double[] lasts,List<Neuron> lastNeurons) {
		double[] ths = new double[this.neurons.size()];
		for(int i=0;i<this.neurons.size();i++){
			double thi = calculateTh(lasts, lastNeurons,i);
			for(int j=0;j<input.length;j++){
				this.neurons.get(i).addWeight(j,input[j]*thi*this.neuralNetConfig.getGrowthRate());
			}
			ths[i] = thi;
		}
		if(this.last!=null){
			this.last.back(ths,this.neurons);
		}
	}

	private double calculateTh(double[] lasts, List<Neuron> lastNeurons,int i) {
    	double sum = 0.0;
    	for(int l=0;l<lasts.length;l++){
    		sum+= lasts[l]*lastNeurons.get(l).getWeight()[i]*this.activateFunction.derivative(sums[l]);
    		if(Double.isNaN(sum)){
    			throw new RuntimeException("");
			}
		}
		return sum;
	}

	private double calculateError(double[] calresults, double[] result) {
    	double error= 0.0;
    	for(int i=0;i<calresults.length;i++){
    		error += (calresults[i]-result[i])*(calresults[i]-result[i]);;
		}
		return error/calresults.length;
	}

	private double[] calculateResults(double[] sums) {
		double[] results = new double[sums.length];
		for(int i=0;i<sums.length;i++){
			results[i] = this.activateFunction.activation(sums[i]);
		}
		return results;
	}

	private double[] calculateSum(double[] input) {
    	double[] sums = new double[this.neurons.size()];
    	for(int i=0;i<sums.length;i++){
    		sums[i] = this.neurons.get(i).calculate(input);
		}
		return sums;

	}

	;

	private boolean shouldStop(){
		return this.next== null ? this.stop : this.next.shouldStop();
	};

	public double[] preidct(double[] input){
		double[] sums = calculateSum(input);
		double[] calresults = this.calculateResults(sums);
		if(this.next!=null){
			return this.next.preidct(appendLastOne(calresults));
		}
		return calresults;
	};

	public MlpLayer getNext() {
		return next;
	}

	@Override
	public String toString() {
		return "MlpLayer{" +
				"neurons=" + neurons +
				", next=" + next +
				'}';
	}
}
