package cs6220.hw1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.Enumeration;

import weka.classifiers.Classifier;
import weka.classifiers.bayes.NaiveBayesUpdateable;
import weka.classifiers.meta.AttributeSelectedClassifier;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;


public class Increamental extends Trainer {

	

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
		buildClassifier(input,output, new AttributeSelectedClassifier());
		

	}
	
	public static void buildClassifier(String pathToTest, String pathToResult,Classifier cls) throws Exception{

		/* train arff test */
		 ArffLoader loader = new ArffLoader();
		 loader.setFile(new File(Constant.TRAINARFF));
		 Instances structure = loader.getDataSet();
		 
		/* arff test path*/
		File output = new File(Constant.TESTARFF);
		FileWriter fw = new FileWriter(output);
		BufferedWriter bw = new BufferedWriter(fw);
		
		/*  path to test file   */
		File file=new File(pathToTest);
		FileReader fr = new FileReader(file);
		BufferedReader br = new BufferedReader(fr);
		
	
		/**/
		File result = new File(pathToResult);
		FileWriter resultFW= new FileWriter(result);
		BufferedWriter resutlBW = new BufferedWriter(resultFW);
		
		
		bw.append("@relation "+structure.relationName()+"\n");
		@SuppressWarnings("unchecked")
		Enumeration<Attribute> enums = structure.enumerateAttributes();
		while (enums.hasMoreElements()) {
			Attribute attribute = (Attribute) enums.nextElement();
			bw.append(attribute.toString()+"\n");
		}
		bw.append("@data\n");
		String str = "";
		while ((str = br.readLine()) != null) {
			String[] features = str.split("\t");
			trim(features);
			String data = assembleByDeli(features, ",")+",?";
			bw.append(data + "\n");
		}
		
		
		bw.flush();
		bw.close();
		br.close();
		
		 structure.setClassIndex(structure.numAttributes() - 1);
		 //set implementation of a classifier
		 cls.buildClassifier(structure);
		 
		 NaiveBayesUpdateable nb = new NaiveBayesUpdateable();
		 nb.buildClassifier(structure);
		 Instance current;
		 while ((current = loader.getNextInstance(structure)) != null)
		   nb.updateClassifier(current);
		 
		 ArffLoader test = new ArffLoader();
		 test.setFile(new File(Constant.TESTARFF));
		 Instances teststructure = test.getDataSet();
		 teststructure.setClassIndex(teststructure.numAttributes()-1);
		 
		 @SuppressWarnings("unchecked")
		 Enumeration<Instance> e =teststructure.enumerateInstances();
		 // resutlBW.append("Category");
		 boolean isFirstLine=true;
		 while (e.hasMoreElements()) {
			Instance instance = (Instance) e.nextElement();
			String prefix="\n";
			if(isFirstLine){
			   prefix="";
			   isFirstLine=false;
			}
			if(nb.classifyInstance(instance)==0){
				resutlBW.append(prefix+"<=50K");
			}else{
				resutlBW.append(prefix+">50K");
			}
			
		}
		 
		 resutlBW.flush();
		 resutlBW.close();
		 
	}
	
	
}

