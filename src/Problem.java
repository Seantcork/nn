import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Problem{
    int num_output_nodes;
    //double initial_weight;
    double num_input_nodes;
    String problemType;
    static ArrayList<Digit> all_problems = new ArrayList<>();


    public Problem(int num_input_nodes, int num_output_nodes){
        this.num_input_nodes = num_input_nodes;
        this.num_output_nodes = num_output_nodes;
    }



    public static void read32(String fileName){
        BufferedReader reader;
        try {
            //set reader to read lines
            reader = new BufferedReader(new FileReader(fileName));
            //new line
            String line = reader.readLine();
            ArrayList<Node> nums = new ArrayList<>();
            while(line != null) {
                if(line.isEmpty()){
                    line = reader.readLine();
                    continue;
                }
                if (String.valueOf(line.charAt(0)).equals(" ")) {
                    //System.out.println(line);
                    if(!nums.isEmpty()) {
                        Digit digit = new Digit(Character.getNumericValue(line.charAt(1)), nums);
                        all_problems.add(digit);
                        nums.clear();
                    }
                }
                 if (Character.isDigit(line.charAt(0))) {
                     System.out.println(line);
                    String array[] = line.split("");
                    for(int i = 0; i < array.length; i++) {
                        Node node = new Node(Integer.parseInt(array[i]));
                        nums.add(node);
                    }
                }
                line = reader.readLine();
            }
        }
        catch(IOException e) {
            System.out.print("error " + e);
        }

    }

    public static void main(String[] args) {
        read8("optdigits-8x8-int.tra");
    }


    public static void read8(String fileName){
        BufferedReader reader;
        try {
            //set reader to read lines
            reader = new BufferedReader(new FileReader(fileName));
            //new line
            String line = reader.readLine();
            ArrayList<Node> nums = new ArrayList<>();
            while(line != null) {
                String array[] = line.split(",");
                for(int i = 0; i < array.length -1; i++){
                    Node node = new Node(Integer.parseInt(array[i]));
                    nums.add(node);
                }
                Digit digit = new Digit(Integer.parseInt(array[array.length -1]),nums);
                all_problems.add(digit);
                nums.clear();
                line = reader.readLine();
            }
        }
        catch(IOException e) {
            System.out.print("error " + e);
        }

    }

}
