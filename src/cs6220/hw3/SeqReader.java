package cs6220.hw3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class SeqReader {

	/**
	 * @param args
	 */
	private long currentLineNumber=0;
	private BufferedReader bf;
	private FileReader fr;
	public SeqReader(String path) throws FileNotFoundException {
	    File file = new File(path);
		fr=new FileReader(file);
		bf=new BufferedReader(fr);
	}
	
	public long getCurrentLineNumber() {
		return currentLineNumber;
	}

	public String getLineStrByLineNumber(long expectedLineNumber) throws IOException{
		String str=null;
		long diff = expectedLineNumber-currentLineNumber;
		for(long i=diff;i>=0;i--){
			 str=bf.readLine();
			 currentLineNumber++;
		}
//		if(expectedLineNumber==currentLineNumber){
//		    str=bf.readLine();
//			currentLineNumber++;
//		}
		return str;
	}
	
	public void close() throws IOException{
		bf.close();
		fr.close();
	}
	
	public static long getMaxLineNumber() throws IOException{
		SeqReader seq=new SeqReader(Parser.PATH);
		long lineNumber=0;
		String str=null;
		while((str=seq.getLineStrByLineNumber(lineNumber))!=null){
			lineNumber++;
		}
		return lineNumber-1;
	}
	
	public Sequence getSequenceObjByLineNumber(long lineNumber) throws IOException{
		String str=this.getLineStrByLineNumber(lineNumber);
		//System.out.println("line:"+lineNumber);
		if(str==null){
			throw new RuntimeException("Please check your format pattern");
		}
		return Parser.convert2SeqObj(str);
	}
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println(SeqReader.getMaxLineNumber());
		SeqReader sr=new SeqReader(Parser.PATH);
		String line=sr.getLineStrByLineNumber(90);
		System.out.println(line);
	}

}
