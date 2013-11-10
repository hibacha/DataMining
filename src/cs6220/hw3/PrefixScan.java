package cs6220.hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PrefixScan {

	/**
	 * @param args
	 */
	
	private static  double min_support_count=0.4;
	private static List<Sequence> seqDB;
	public PrefixScan() {
		seqDB=Parser.readFromGivenURL();
		min_support_count*=seqDB.size();
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PrefixScan p=new PrefixScan();
	    List<Short> prefix=new ArrayList<Short>();
		p.prefixSpanByGivenItem((short)65, prefix, initPjDB());
	}
	
	private static ProjectedDB initPjDB(){
		ProjectedDB pjDB=new ProjectedDB();
		for(int i=0;i<seqDB.size();i++){
			pjDB.addPostFix(i, new SmartPointer());
		}
		return pjDB;
	}
	
	private void prefixSpanByGivenItem(Short candidate, List<Short> prefix,ProjectedDB pdb){
		HashMap<Integer, SmartPointer> pjDB = pdb.getPjDB();
		Set<Integer> keys = pdb.getPjDB().keySet();
		Iterator<Integer> iterator = keys.iterator();
		int supportCount=0;
		while(iterator.hasNext()){
			Integer seqId= iterator.next();
			SmartPointer postfix = pjDB.get(seqId);
			Sequence sq = seqDB.get(seqId);
			if(sq.isEventIn(candidate, postfix)){
				supportCount++;
			}
			
		}
		if(supportCount>=min_support_count){
			System.out.println(candidate+"is appended to prefix");
		}
	}
	
//	private boolean checkByGivenCandidate(Short candidate,ProjectedDB pdb){
//		pdb.get
//	}
//	private boolean isAppendToLastEleOfPrefix(short candidate,ProjectedDB pdb){
//		
//		return false;
//	}
//	private boolean isAppendToPrefix(short candidate,List<Sequence>){
//		return false;
//	}
}
