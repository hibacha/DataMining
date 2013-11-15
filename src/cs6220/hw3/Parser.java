package cs6220.hw3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {

	/**
	 * @param args
	 */
//	public static final String PATH="/Users/zhouyf/Dropbox/DataMining/hw3/truncate2.data";
//	public static final String PATH="/Users/zhouyf/Dropbox/DataMining/hw3/500K5K20_a.data";
	public static final String PATH="/Users/zhouyf/Dropbox/DataMining/hw3/little.data";
	
	public static void main(String[] args) {
		generateDBFromFile();
	}
	
	public static Sequence convert2SeqObj(String line) {
		List<Short> listOfNumber = new ArrayList<Short>();
//		String str = "10 1 1806 1 3436 1 4024 2 2256 3544 1 3827 1 1798 1 3695 1 2004 2 3068 3602 1 4618";
		Pattern p = Pattern.compile("\\d+");
		Matcher m = p.matcher(line);
		while (m.find()) {
			listOfNumber.add(Short.valueOf(line.substring(m.start(), m.end())));
		}
		Sequence sq = parse(listOfNumber);
		return sq;
	}

	
	public static List<Sequence> generateDBFromFile() {
		File file = new File(PATH);
		List<Sequence> seqDataBase=null;
		try {
			seqDataBase = parseFile(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return seqDataBase;
	}
	
	private static List<Sequence> parseFile(File file) throws IOException {

		List<Sequence> seqDataBase=new ArrayList<Sequence>();
		
		FileReader fr = new FileReader(file);
		BufferedReader bf = new BufferedReader(fr);
		String str = "";
		int count = 0;
		while ((str = bf.readLine()) != null) {
			count++;
			Sequence sq=convert2SeqObj(str);
			seqDataBase.add(sq);
			if(count%10000==0){
				System.out.println(count);
			};
		}
		return seqDataBase;
	}

	public static Sequence parse(List<Short> listOfNumber) {
		Short expectedSizeOfSequence = listOfNumber.get(0);
		int currentIndex = 1;
		List<Event> events = new ArrayList<Event>();
		for (int i = currentIndex; i < listOfNumber.size();) {
			short numberOfItems = listOfNumber.get(i);
			List<Short> items = new ArrayList<Short>();
			for (int j = 0; j < numberOfItems; j++) {
				items.add(listOfNumber.get(i + j + 1));
			}
			events.add(new Event(items));
			i += numberOfItems + 1;
		}
		if (events.size() != expectedSizeOfSequence) {
			System.err.println("check on ");
		}
		return new Sequence(events);

	}

	public static void findNext() {

	}

}
