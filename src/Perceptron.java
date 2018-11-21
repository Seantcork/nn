import java.util.ArrayList;
import java.util.Random;



public class Perceptron {
	private int num_inputs;
	private int num_outputs;
	private double[][] matrix;
	private double learning_rate;

	/***********
	Initialize Perceptron
	************/
	public Perceptron(int num_inputs, int num_outputs, double learning_rate){
		this.num_inputs = num_inputs;
		this.num_outputs = num_outputs;
		this.learning_rate = learning_rate;
		generate_Matrix();
	}

	/***********
	Initialize training weights which are held in the matrix array
	************/
	private void generate_Matrix(){
		Random r = new Random();
		matrix = new double[num_outputs][num_inputs];
		for(int x = 0; x < num_outputs; x++){
			for(int y = 0; y < num_inputs; y++){
				matrix[x][y] = .3*r.nextDouble() - .15; 
			}
		}
	}

	/***********
	Takes training examples and a number of epochs and trains the perceptron
	************/
	public void train(int epochs, ArrayList<Digit> training_examples){

		for(int x = 0; x < epochs; x++){
			int num_correct = 0;
			for(int y = 0; y < training_examples.size(); y++){
				int result = train_example(training_examples.get(y));
				if(training_examples.get(y).answer - result == 0){
					num_correct += 1;
				}
			}
			System.out.println(x + "," + (num_correct/(double)training_examples.size()));
		}
	}
	
	/***********
	Takes one training example and trains the perceptron on it
	************/
	private int train_example(Digit example){

		//gets it into a format to do matrix multiplcation with arrays
		double[][] input = new double[example.nodes.size()][1];
		for(int x = 0; x < example.nodes.size(); x++){
			input[x][0] = example.nodes.get(x); 
		}

		//multiplys the weights by the inputs
		double[][] matrix_product = multiplyByMatrix(matrix, input);

		//turns the output of the matrix product back into a 1D array
		double[] raw_output = new double[num_outputs];
		for(int x = 0; x < matrix_product.length; x++){
			raw_output[x] = matrix_product[x][0]; 
		}

		double[] activated_output = activate_output(raw_output);

		int result = output_to_digit(activated_output);

		double[] error = calculate_error(activated_output, example);

		update_weights(example, raw_output, error);

		return result;
	}

	/***********
	Takes testing data and returns the percentage of the perceptrons predictions which are correct
	************/
	public double test(ArrayList<Digit> testing_examples){
		int num_correct = 0;
		for(int y = 0; y < testing_examples.size(); y++){
			int result = test_example(testing_examples.get(y));
			if(testing_examples.get(y).answer - result == 0){
				num_correct += 1;
			}
		}
		System.out.println("-1," + (num_correct/(double)testing_examples.size()));
		return (num_correct/(double)testing_examples.size());
	}

	/***********
	Takes one testing example and returns the perceptrons prediction
	************/
	private int test_example(Digit example){

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

		return result;
	}

	/***********
	Takes the input, not activated output, and error, and adjusts the weights accordingly.
	************/
	private void update_weights(Digit example, double[] raw_output, double[] error){
		for(int x = 0; x < num_outputs; x++){
			for(int y = 0; y < num_inputs; y++){
				matrix[x][y] = matrix[x][y] + (this.learning_rate * error[x] * example.nodes.get(y) * activation_function_derivative(raw_output[x]));
			}
		}
	}

	/***********
	Takes the output and the training example and calculates the error for each of the output nodes
	************/
	private double[] calculate_error(double[] activated_output, Digit example){
		double[] error;
		if(activated_output.length == 1){
			error = new double[1];
			error[0] = (example.answer/((double)10) - activated_output[0]);
		}
		else{
			error = new double[10];
			for(int x = 0; x < 10; x ++){
				if(x == example.answer){
					error[x] = 1 - activated_output[x];
				}
				else{
					error[x] = 0 - activated_output[x];
				}
			}
		}
		return error;
	}


	/***********
	Takes a perceptrons output and returns the predicted digit
	************/
	private int output_to_digit(double[] activated_output){

		if(activated_output.length == 1){
			return (int)(activated_output[0]*10);
		}
		else{
			int index = 0;
			double max_index_value = activated_output[0];
			for(int x = 1; x < num_outputs; x++){
				if(max_index_value < activated_output[x]){
					index = x;
					max_index_value = activated_output[x];
				}
			}
			return index;
		}
	}

	/***********
	Multiplies two arrays together like matricies
	From: https://stackoverflow.com/questions/17623876/
	************/
	public double[][] multiplyByMatrix(double[][] m1, double[][] m2) {
        int m1ColLength = m1[0].length;
        int m2RowLength = m2.length;
        if(m1ColLength != m2RowLength) return null;
        int mRRowLength = m1.length;
        int mRColLength = m2[0].length;
        double[][] mResult = new double[mRRowLength][mRColLength];
        for(int i = 0; i < mRRowLength; i++) {
            for(int j = 0; j < mRColLength; j++) {
                for(int k = 0; k < m1ColLength; k++) {
                    mResult[i][j] += m1[i][k] * m2[k][j];
                }
            }
        }
        return mResult;

    }

    /***********
	Takes an output layer and computes the activation function on all the nodes
	************/
	private double[] activate_output(double[] output){

		double[] actived_output = new double[output.length];
		for(int x = 0; x < num_outputs; x++){
			actived_output[x] = activation_function(output[x]);
		}
		return actived_output;
	}

	/***********
	Computes the activation function given an input
	************/
    private double activation_function(double input){
    	return 1/(1+Math.exp(-input));
    }

    /***********
	Computes the derivative of the activation function given an input
	************/
	private double activation_function_derivative(double input){
		return (1 - activation_function(input))*activation_function(input);

	}

}
