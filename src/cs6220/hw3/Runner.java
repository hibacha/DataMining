package cs6220.hw3;

import java.util.List;

public class Runner {

	/**
	 * @param args
	 */
	private static ProjectedDB initPjDB(List<Sequence> seqDB){
		ProjectedDB pjDB=new ProjectedDB();
		for(int i=0;i<seqDB.size();i++){
			pjDB.addPostFix(i, new SmartPointer());
		}
		return pjDB;
	}
	public static double min_suppport_count=0;
	public static List<Sequence> originalDB=null;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
	    originalDB=Parser.generateDBFromFile();
		min_suppport_count=0.4*originalDB.size();
		
		
		ProjectedDB initProjectedDB=initPjDB(originalDB);
		ProjectedDB a_pj=PrefixScan.prefixSpanByGivenItem(false,(short) 171,
				initProjectedDB);
		//System.out.println(a_pj);
		helper(a_pj);
		
		
//		ProjectedDB aa_pj=PrefixScan.prefixSpanByGivenItem(true,(short) 66, a_pj);
//		System.out.println(aa_pj);
		
//		ProjectedDB aax_pj=PrefixScan.prefixSpanByGivenItem(false,(short) 65, aa_pj);
//		System.out.println(aax_pj);
		
		
	}
			
	private static void helper(ProjectedDB pjDb){
		if(pjDb==null) return;
		for(int i=65;i<=71;i++){
			ProjectedDB pj_append = PrefixScan.prefixSpanByGivenItem(false,(short) i,
					pjDb);
			ProjectedDB pj_assemble = PrefixScan.prefixSpanByGivenItem(true,(short)i , pjDb);
			helper(pj_append);
			helper(pj_assemble);
		}
	}
	
	

}
