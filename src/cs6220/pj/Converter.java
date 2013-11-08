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
    private  Hashtable<String,Set<Integer>> subreddit2ImageSetTable=new Hashtable<String, Set<Integer>>();
    
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Converter c = new Converter();
		c.readFromGivenURL(READ_PATH);
		//c.mergeSubmissionToList();
		c.mergeImageToList();
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
				statSubmissionTimesPerSubreddit(values);
				statDistinctImageTimesPerSubreddit(values);
			}
		}
	}

	private void statDistinctImageTimesPerSubreddit(String[] values) {
		String key=values[6].trim();
		if(subreddit2ImageSetTable.get(key)==null){
			Set<Integer> imageSet = new HashSet<Integer>();
			Integer imageId= Integer.parseInt(values[0]);
			imageSet.add(imageId);
			subreddit2ImageSetTable.put(key, imageSet);
		}else{
			Integer imageId= Integer.parseInt(values[0]);
			Set<Integer> imageSet = subreddit2ImageSetTable.get(key);
			if(!imageSet.contains(imageId)){
				imageSet.add(imageId);
			}
		}
		
	}

	private void statSubmissionTimesPerSubreddit(String[] values) {
		String key=values[6].trim();
		Integer submissionsTimes = subreddit2submissionsTimesTable.get(key);
		if(submissionsTimes==null){
		   subreddit2submissionsTimesTable.put(key, 1);
		}
		else{
		   subreddit2submissionsTimesTable.put(key,subreddit2submissionsTimesTable.get(key)+1);
		}
	}

	private void mergeImageToList(){
		Enumeration<String> enumerator = subreddit2ImageSetTable.keys();
		List<Pair> list=new ArrayList<Pair>();
		while(enumerator.hasMoreElements()){
		  String key=enumerator.nextElement();
		  Set<Integer> imageSet = subreddit2ImageSetTable.get(key);
		  list.add(new Pair(key,imageSet.size()));
		}
		showStat(list);
	}
	private void mergeSubmissionToList(){
		Enumeration<String> enumerator= subreddit2submissionsTimesTable.keys();
		List<Pair> list=new ArrayList<Pair>();
		while(enumerator.hasMoreElements()){
			String key=enumerator.nextElement();
			int number = subreddit2submissionsTimesTable.get(key); 
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
