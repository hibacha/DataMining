package cs6220.pj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.List;
import java.util.Set;

public class Converter {

	private static String READ_PATH = "/Users/zhouyf/Dropbox/DataMining/reddit/normalizedReddit.csv";
    private  Hashtable<String,Integer> subreddit2submissionsTimesTable=new Hashtable<String, Integer>();
    private  Hashtable<String,Integer> image2submissionsTimesTable=new Hashtable<String, Integer>();
    private  Hashtable<String,Set<String>> subreddit2ImageSetTable=new Hashtable<String, Set<String>>();
    private  Hashtable<String,Set<String>> image2SubredditSetTable=new Hashtable<String, Set<String>>();
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Converter c = new Converter();
		c.readFromGivenURL(READ_PATH);
		
	}

	private void readFromGivenURL(String url) {
		File file = new File(url);
		try {
			parseFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void parseFile(File file) throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader bf = new BufferedReader(fr);
		String str = "";
		int count = 0;
		while ((str = bf.readLine()) != null) {
			count++;
			String[] values = str.split(",", 11);
			if (count > 1) {
				statSubmissionTimesPerSubreddit(values,6,subreddit2submissionsTimesTable);
				statSubmissionTimesPerSubreddit(values,0,image2submissionsTimesTable);
				statDistinctImageTimesPerSubreddit(values,6,0,subreddit2ImageSetTable);
				statDistinctImageTimesPerSubreddit(values,0,6,image2SubredditSetTable);
			}
		}
		System.out.println("@@@@@@@@@@@[subreddit,submissionTimes]");
		mergeSubmissionToList(subreddit2submissionsTimesTable);
		System.out.println("@@@@@@@@@@@[image,submissionTimes]");
		mergeSubmissionToList(image2submissionsTimesTable);
		System.out.println("@@@@@@@@@@@[subreddit,dictinctImageTimes]");
		mergeImageToList(subreddit2ImageSetTable);
		System.out.println("@@@@@@@@@@@[image, different subreddit Number]");
		mergeImageToList(image2SubredditSetTable);
		
	}

	private void statDistinctImageTimesPerSubreddit(String[] values, int baseIndex, int aggregationIndex, Hashtable<String,Set<String>> table) {
		String key=values[baseIndex].trim();
		if(table.get(key)==null){
			Set<String> imageSet = new HashSet<String>();
			String imageId= values[aggregationIndex];
			imageSet.add(imageId);
			table.put(key, imageSet);
		}else{
			String imageId= values[aggregationIndex];
			Set<String> imageSet = table.get(key);
			if(!imageSet.contains(imageId)){
				imageSet.add(imageId);
			}
		}
		
	}

	private void statSubmissionTimesPerSubreddit(String[] values,int fieldIndex,Hashtable<String,Integer> table) {
		String key=values[fieldIndex].trim();
		Integer submissionsTimes = table.get(key);
		if(submissionsTimes==null){
			table.put(key, 1);
		}
		else{
			table.put(key,table.get(key)+1);
		}
	}

	private void mergeImageToList(Hashtable<String,Set<String>> table){
		Enumeration<String> enumerator = table.keys();
		List<Pair> list=new ArrayList<Pair>();
		while(enumerator.hasMoreElements()){
		  String key=enumerator.nextElement();
		  Set<String> imageSet = table.get(key);
		  list.add(new Pair(key,imageSet.size()));
		}
		showStat(list);
	}
	private void mergeSubmissionToList(Hashtable<String,Integer> table){
		Enumeration<String> enumerator= table.keys();
		List<Pair> list=new ArrayList<Pair>();
		while(enumerator.hasMoreElements()){
			String key=enumerator.nextElement();
			int number = table.get(key); 
			list.add(new Pair(key,number));
		}
		showStat(list);
		
	}
	private void showStat(List<Pair> list) {
		Collections.sort(list);
		for(Pair pair:list){
			System.out.println(pair);
		}
	}
}
