package cs6220.hw3;

import java.io.IOException;

public class Runner {

	/**
	 * @param args
	 */
	private static ProjectedDB initPjDB(long size){
		ProjectedDB pjDB=new ProjectedDB();
		for(int i=0;i<size;i++){
			pjDB.addPostFix(i, new SmartPointer());
		}
		return pjDB;
	}
	public static double min_suppport_count=0;
	//public static List<Sequence> originalDB=null;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	   // originalDB=Parser.generateDBFromFile();
		long size = SeqReader.getMaxLineNumber()+1;
		min_suppport_count=0.4*(size);
		
		
		ProjectedDB initProjectedDB=initPjDB(size);
		for(int i=65;i<=71;i++){
			ProjectedDB a_pj=PrefixScan.prefixSpanByGivenItem(false,(short) i,
					initProjectedDB);
			helper(a_pj);
		}
		
	}
			
	private static void helper(ProjectedDB pjDb) throws IOException{
		if(pjDb==null) return;
		for(int i=65;i<=71;i++){
			ProjectedDB pj_append = PrefixScan.prefixSpanByGivenItem(false,(short) i,pjDb);
			ProjectedDB pj_assemble = PrefixScan.prefixSpanByGivenItem(true,(short)i , pjDb);
			helper(pj_append);
			helper(pj_assemble);
		}
	}
	
	

}
