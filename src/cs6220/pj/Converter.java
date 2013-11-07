package cs6220.pj;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Converter {
	
	private static String PATH="/Users/zhouyf/Dropbox/DataMining/reddit/redditSubmissions.csv";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Converter c=new Converter();
		c.readFromGivenURL(PATH);
	}
	
	private void readFromGivenURL(String url)  {
		File file = new File(url);
		try {
			parseFile(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void parseFile(File file) throws IOException {
		FileReader fr = new FileReader(file);
		BufferedReader bf = new BufferedReader(fr);
		String str = "";
		int count = 0;
		while ((str = bf.readLine()) != null) {
		   System.out.println(str);
		   count++;
		}
		bf.close();
		fr.close();
		System.out.println(count);
	}
}
