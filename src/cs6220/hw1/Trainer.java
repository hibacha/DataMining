package cs6220.hw1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Enumeration;

import weka.classifiers.Classifier;
import weka.classifiers.trees.J48graft;
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
		File file = new File(System.getProperty("user.dir")+"/"+Constant.TRAININGSETTXT);
		File output = new File(System.getProperty("user.dir")+"/"+Constant.TRAINCSV);
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

	public static void trim(String[] strs) {
		for (int i = 0; i < strs.length; i++) {
			strs[i] = strs[i].trim();
		}
	}

	public static void convertFromCSVToArff() throws IOException {
		CSVLoader loader = new CSVLoader();
		loader.setSource(new File(System.getProperty("user.dir")+"/"+Constant.TRAINCSV));
		Instances data = loader.getDataSet();

		// save ARFF
		ArffSaver saver = new ArffSaver();
		saver.setInstances(data);
		saver.setFile(new File(System.getProperty("user.dir")+"/"+Constant.TRAINARFF));
		saver.writeBatch();
	}


	
	public static void buildClassifier(String pathToTest, String pathToResult,Classifier cls) throws Exception{

		/* train arff test */
		 ArffLoader loader = new ArffLoader();
		 loader.setFile(new File(System.getProperty("user.dir")+"/"+Constant.TRAINARFF));
		 Instances structure = loader.getDataSet();
		 
		/* arff test path*/
		File output = new File(System.getProperty("user.dir")+"/"+Constant.TESTARFF);
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
		 
		 ArffLoader test = new ArffLoader();
		 test.setFile(new File(System.getProperty("user.dir")+"/"+Constant.TESTARFF));
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
			if(cls.classifyInstance(instance)==0){
				resutlBW.append(prefix+"<=50K");
			}else{
				resutlBW.append(prefix+">50K");
			}
			
		}
		 
		 resutlBW.flush();
		 resutlBW.close();
		 
	}
}

