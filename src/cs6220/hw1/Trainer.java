package cs6220.hw1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

import weka.classifiers.Classifier;
import weka.classifiers.functions.VotedPerceptron;
import weka.classifiers.rules.DecisionTable;
import weka.classifiers.trees.BFTree;
import weka.classifiers.trees.J48graft;
import weka.classifiers.trees.REPTree;
import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;
import weka.core.converters.ArffLoader;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class Trainer {

	/**
	 * @param args
	 */
	public static final String[] FEATURES_HEADER = { "age", "workclass",
			"fnlwgt", "education", "education-num", "marital-status",
			"occupation", "relationship", "race", "sex", "capital-gain",
			"captital-loss", "hours-per-week", "native-country", "income" };

	public static String assembleByDeli(String[] array, String deli) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++) {
			sb.append(array[i]);
			if (i != array.length - 1) {
				sb.append(deli);
			}
		}
		return sb.toString();
	}

	public static void convertFromTxtToCSV() throws IOException {
		File file = new File(Constant.TRAININGSETTXT);
		File output = new File(Constant.TRAINCSV);
		parseFile(file, output);
	}

	public static void parseFile(File file, File output) throws IOException {
		FileReader fr = new FileReader(file);
		FileWriter fw = new FileWriter(output);

		BufferedReader br = new BufferedReader(fr);
		BufferedWriter bw = new BufferedWriter(fw);
		String str = "";
		String header = assembleByDeli(FEATURES_HEADER, ",");

		bw.append(header + "\n");
		while ((str = br.readLine()) != null) {
			String[] features = str.split("\t");
			trim(features);
			String data = assembleByDeli(features, ",");
			bw.append(data + "\n");
		}
		bw.flush();
		bw.close();
		br.close();

	}

	private static void trim(String[] strs) {
		for (int i = 0; i < strs.length; i++) {
			strs[i] = strs[i].trim();
		}
	}

	public static void convertFromCSVToArff() throws IOException {
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(Constant.TRAINCSV));
		Instances data = loader.getDataSet();

		// save ARFF
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(Constant.TRAINARFF));
		saver.writeBatch();
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		if(args.length!=2){
			System.out.println("syntax: <run_script> <input_test_file> <output_file>");
			System.exit(1);
		}
		String input = args[0];
		String output = args[1];
		
		convertFromTxtToCSV();
		convertFromCSVToArff();
		buildClassifier(input,output);
		

	}
	
	public static void buildClassifier(String pathToTest, String pathToResult) throws Exception{

		/* train arff test */
		 ArffLoader loader = new ArffLoader();
		 loader.setFile(new File(Constant.TRAINARFF));
		 Instances structure = loader.getDataSet();
		 
		/* arff test path*/
		File output = new File(Constant.LESSTESTARFF);
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
		 Classifier cls = new DecisionTable();
		 cls.buildClassifier(structure);
		 
		 ArffLoader test = new ArffLoader();
		 test.setFile(new File(Constant.LESSTESTARFF));
		 Instances teststructure = test.getDataSet();
		 teststructure.setClassIndex(teststructure.numAttributes()-1);
		 
		 @SuppressWarnings("unchecked")
		Enumeration<Instance> e =teststructure.enumerateInstances();
		 resutlBW.append("Category");
		 while (e.hasMoreElements()) {
			Instance instance = (Instance) e.nextElement();
		//	System.out.print(cls.distributionForInstance(instance)[0]+":"+cls.classifyInstance(instance));
			if(cls.classifyInstance(instance)==0){
				//System.out.println("\n"+"<=50K");
				resutlBW.append("\n"+"<=50K");
			}else{
//				System.out.println("\n"+">50K");
				resutlBW.append("\n"+">50K");
			}
			
		}
		 
		 resutlBW.flush();
		 resutlBW.close();
		 
	}
}
