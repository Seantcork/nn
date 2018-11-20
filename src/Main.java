public class Main{
    public static void main(String[] args) {
        //small demo
        Problem problem = new Problem(1025, 10, 32);
        Perceptron p = new Perceptron(1025, 10, .1);
        p.train(10, problem.training_problems);
        p.test(problem.testing_problems);
    }
}
