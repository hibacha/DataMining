package cs6220.hw1;

import weka.classifiers.trees.J48;


public class MyBFTree extends Trainer {

	

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		if(args.length!=2){
			System.out.println("syntax: <run_script> <input_test_file> <output_file>");
			System.exit(1);
		}
		String input = args[0];
		String output = args[1];
                String prefix = System.getProperty("user.dir");
                
		if(!input.startsWith("/")){
		       input= prefix+"/"+input;
		}
		if(!output.startsWith("/")){
		       output= prefix + "/" + output;
		}
		convertFromTxtToCSV();
		convertFromCSVToArff();
		buildClassifier(input,output, new J48());
		

	}
	
	
}

