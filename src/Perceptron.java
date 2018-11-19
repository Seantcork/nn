import java.util.ArrayList;
import java.util.Random;



public class Perceptron {
	private int num_inputs;
	private int num_outputs;
	private ArrayList<Digit> training_examples;
	private double[][] matrix;
	private double learning_rate;

	public Perceptron(int num_inputs, int num_outputs, ArrayList<Digit> training_examples, double learning_rate){
		this.num_inputs = num_inputs;
		this.num_outputs = num_outputs;
		this.training_examples = training_examples;
		this.learning_rate = learning_rate;
		generate_Matrix();
	}

	private void generate_Matrix(){
		Random r = new Random();
		matrix = new double[num_outputs][num_inputs];
		for(int x = 0; x < num_outputs; x++){
			for(int y = 0; y < num_inputs; y++){
				matrix[x][y] = 0;//2*r.nextDouble() - 1; 
			}
		}
	}

	public void train(int epochs){
		for(int x = 0; x < epochs; x++){
			int num_correct = 0;
			double[] per = new double[11];
			double[] real = new double[11];
			for(int y = 0; y < training_examples.size(); y++){
				//print_matrix();
				//System.out.println("Example "+ y);
				int result = test(training_examples.get(y));
				//System.out.println(result);
				if(training_examples.get(y).answer - result == 0){
					num_correct += 1;
				}
				per[result]+=1;
				real[training_examples.get(y).answer]+=1;

			}
			for(int z = 0; z < 11; z ++){
				System.out.println("Number "+z+" was predicted "+ (per[z]/(double)training_examples.size()));
			}
			for(int z = 0; z < 11; z ++){
				System.out.println("Number "+z+" was tested "+ (real[z]/(double)training_examples.size()));
			}
			System.out.println("Percentage Correct During Epoch" + x + ": " + (num_correct/(double)training_examples.size()));
		}
	}

	private void update_weights(int result, Digit example, double[] raw_output){
		int error = example.answer - result;
		for(int x = 0; x < num_outputs; x++){
			for(int y = 0; y < num_inputs; y++){
				matrix[x][y] = matrix[x][y] + (this.learning_rate * error * example.nodes.get(y) * activation_function_derivative(raw_output[x]));
			}
		}
	}

	private double activation_function_derivative(double input){
		return (1 - activation_function(input))*activation_function(input);

	}




	private int test(Digit example){
		//System.out.println("Example Size: " + example.nodes.size());
		double[][] input = new double[example.nodes.size()][1];
		for(int x = 0; x < example.nodes.size(); x++){
			input[x][0] = example.nodes.get(x); 
		}

		double[][] matrix_product = multiplyByMatrix(matrix, input);

		double[] raw_output = new double[num_outputs];
		for(int x = 0; x < matrix_product.length; x++){
			raw_output[x] = matrix_product[x][0]; 
		}

		double[] activated_output = activate_output(raw_output);

		int result = output_to_digit(activated_output);

		update_weights(result, example, raw_output);

		return result;
	}

	private int output_to_digit(double[] activated_output){
		if(activated_output.length == 1){

			return (int)(activated_output[0]*10);

		}
		else{
			int index = 0;
			double max_index_value = activated_output[0];
			for(int x = 0; x < num_outputs; x++){
				if(max_index_value < activated_output[x]){
					index = x;
					max_index_value = activated_output[x];
				}
			}
			return index;
		}
	}

	private double[] activate_output(double[] output){
		double[] actived_output = new double[output.length];
		for(int x = 0; x < num_outputs; x++){
			actived_output[x] = activation_function(output[x]);
		}
		return actived_output;
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

    private double activation_function(double input){
    	return 1/(1+Math.exp(-input+.5));
    }



	private void print_matrix(){
		for(int x = 0; x < num_outputs; x++){
			for(int y = 0; y < num_inputs; y++){
				System.out.print(matrix[x][y]+" "); 
			}
			System.out.print("\n");
		}
	}



}
