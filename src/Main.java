public class Main {
    public static void main(String[] args) {
        //small demo
//        Problem problem = new Problem(1025, 10);
//        Perceptron p = new Perceptron(1025, 10, .1);
//        p.train(10, problem.training_problems);
//        p.test(problem.testing_problems);

        int[] waysToRepresentInput = {1025, 65};
        int[] waysToRepresentOutput = {10, 1};

        double[] learningRatesToTest = {.1, .5, 1.0};
        int[] numEpochsToChoose = {10, 25, 40, 50};

        for (int inputOption : waysToRepresentInput) {
            for (int outputOption : waysToRepresentOutput) {
                for (double learningRateOption : learningRatesToTest) {
                    for (int epochOption : numEpochsToChoose) {
                        test(inputOption, outputOption, learningRateOption, epochOption);
                    }
                }
            }
        }
    }

    public static void test(int numInputNodes, int numOutputNodes, double learningRate, int numEpochs) {
        System.out.println("Testing w/ # inputs: " + numInputNodes + ", # outputs: " + numOutputNodes
        + ", learning rate: " + learningRate + ", epochs: " + numEpochs);

        Problem problem = new Problem(numInputNodes, numOutputNodes);
        Perceptron p = new Perceptron(numInputNodes, numOutputNodes, learningRate);
        p.train(numEpochs, problem.training_problems);
        p.test(problem.testing_problems);

        System.out.println();

    }
}
