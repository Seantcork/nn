import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/*
    This class creates an isntance of the perceptron problem. Meaning it reads in the training
    and testing files that are then used by the perceptron class to test and train the
    simple nueral nets on. This function contains an initializer to read the problem
    either from a 32x32 solution or an 8x8 solution.


 */

public class Problem{
    int num_output_nodes;
    //double initial_weight;
    double num_input_nodes;

    //Arrays containing the training and testing problem
    public static ArrayList<Digit> training_problems;
    public static ArrayList<Digit> testing_problems;

    //initialzier so that testing is relatively simple
    public Problem(int num_input_nodes, int num_output_nodes, int representation){
        this.num_input_nodes = num_input_nodes;
        this.num_output_nodes = num_output_nodes;
        //makes sure its easy to tell whether to divide or equal.
        if(representation == 32){
            training_problems = read32("optdigits-32x32.tra");
            testing_problems = read32("optdigits-32x32.tes");
        }
        if(representation == 8){
            training_problems = read8("optdigits-8x8-int.tra");
            testing_problems = read8("optdigits-8x8-int.tes");
        }

    }

    /*
        Reads the function from the 32x32 digit representation

     */
    public static ArrayList<Digit> read32(String fileName){
        //create a local isntance of the problems
        ArrayList<Digit> all_problems = new ArrayList<>();
        //start reading in the file
        BufferedReader reader;
        try {
            //set reader to read lines
            reader = new BufferedReader(new FileReader(fileName));
            //new line
            String line = reader.readLine();
            //store the contents of the image
            ArrayList<Integer> nums = new ArrayList<>();
            while(line != null) {
                if(line.isEmpty()){
                    line = reader.readLine();
                    continue;
                }

                //if we have reached a enw 32x32 file
                if (String.valueOf(line.charAt(0)).equals(" ")) {
                    //System.out.println(line);
                    //make sure the 32*32 isnt empty
                    if(!nums.isEmpty()) {
                        //add a bias node
                        nums.add(1);

                        //create a new instance of the digit
                        Digit digit = new Digit(Character.getNumericValue(line.charAt(1)), nums);
                        //add it to all the exampels
                        all_problems.add(digit);

                        //make sure that a new digit can be read in
                        nums.clear();
                    }
                }

                //if there is a digit representation
                 if (Character.isDigit(line.charAt(0))) {

                     //split the line into an array
                    String array[] = line.split("");
                    //add all values to the digit representation of 1 and 0.
                    for(int i = 0; i < array.length; i++) {
                        nums.add(Integer.parseInt(array[i]));
                    }
                }
                //advance to next line
                line = reader.readLine();
            }
        }
        catch(IOException e) {
            System.out.print("error " + e);
        }
        //return the digits read into the file
        return all_problems;

    }


//    public static void main(String[] args) {
//        training_problems = read32("optdigits-32x32.tra");
//        testing_problems = read32("optdigits-32x32.tes");
//        //System.out.println(all_problems.get(0).nodes.size());
//        Perceptron p = new Perceptron(1025, 1, .1);
//        p.train(10, training_problems);
//        p.test(testing_problems);
//    }



    /*
        Reads file for 8*8 squares. It takes each line and makes the nodes for each testing problem adds then to the
        all problems arraylist. Each problem is contained in the digit class. It contains the answer to the problem
        and all of the nodes in the problem.
         */
    public static ArrayList<Digit> read8(String fileName){
        ArrayList<Digit> all_problems = new ArrayList<>();
        BufferedReader reader;
        try {
            //set reader to read lines
            reader = new BufferedReader(new FileReader(fileName));
            //new line
            String line = reader.readLine();
            ArrayList<Integer> nums = new ArrayList<>();
            while(line != null) {
                String array[] = line.split(",");
                for(int i = 0; i < array.length -1; i++){
                    nums.add(Integer.parseInt(array[i]));
                }
                nums.add(1);
                //create digit and add answer to image
                Digit digit = new Digit(Integer.parseInt(array[array.length -1]),nums);
                all_problems.add(digit);
                nums.clear();
                line = reader.readLine();
            }
        }
        catch(IOException e) {
            System.out.print("error " + e);
        }
        //return the digits
        return all_problems;

    }
    public static ArrayList<Digit> getTraining_problems() {
        return training_problems;
    }


    public static ArrayList<Digit> getTesting_problems() {
        return testing_problems;
    }


}
