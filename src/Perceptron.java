import java.util.ArrayList;
public class Perceptron {
	private int num_inputs;
	private int num_outputs;
	private Digit[] training_examples;
	private double[][] matrix;

	public Perceptron(int num_inputs, int num_outputs, Digit[] training_examples, double learning_rate){
		this.num_inputs = num_inputs;
		this.num_outputs = num_outputs;
		this.training_examples = training_examples;
		generate_Matrix();
	}

	private void generate_Matrix(){
		matrix = new double[num_outputs][num_inputs];
		for(int x = 0; x < num_outputs; x++){
			for(int y = 0; y < num_inputs; y++){
				matrix[x][y] = 0; //*************NEED initializer code here
			}
		}
	}

	public void train(int epochs){
		for(int x = 0; x < epochs; x++){
			for(int y = 0; y < training_examples.length; y++){
				int result = test(training_examples[y]);
			}
		}
	}

	private int test(Digit example){
		for(int x = 0; x < example.length; x++){
			
		}
		double[][] output = multiplyByMatrix(example, matrix);
		output = activation_function(output);
		if(output.length == 1){
			return (int)(output[0]*10);
		}
		else{
			int index = 0;
			double max_index_value = output[0];
			for(int x = 0; x < num_outputs; x++){
				if(max_index_value < output[x]){
					index = x;
					max_index_value = output[x];
				}
			}
			return index;
		}
	}

	private double[] activation_function(double[] output){
		for(int x = 0; x < output.length(); x++){
			output[x] = 1/(1+Math.exp(output[x]+.5));
		}
		return output;
	}

	public double[][] multiplyByMatrix(double[][] m1, double[][] m2) {
        int m1ColLength = m1[0].length; // m1 columns length
        int m2RowLength = m2.length;    // m2 rows length
        if(m1ColLength != m2RowLength) return null; // matrix multiplication is not possible
        int mRRowLength = m1.length;    // m result rows length
        int mRColLength = m2[0].length; // m result columns length
        double[][] mResult = new double[mRRowLength][mRColLength];
        for(int i = 0; i < mRRowLength; i++) {         // rows from m1
            for(int j = 0; j < mRColLength; j++) {     // columns from m2
                for(int k = 0; k < m1ColLength; k++) { // columns from m1
                    mResult[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return mResult;

    }



}
