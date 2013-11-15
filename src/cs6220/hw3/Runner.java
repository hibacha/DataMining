package cs6220.hw3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

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
	public static List<Sequence> originalDB=null;
	public static long size=0;
	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
	    originalDB=Parser.generateDBFromFile();
//		long size = SeqReader.getMaxLineNumber()+1;
	    size = originalDB.size();
	    System.out.println(size);
		min_suppport_count=0.01*size;
		
		
		ProjectedDB initProjectedDB=initPjDB(size);
		List<Short> supItems=new ArrayList<Short>();
		List<ProjectedDB> exeQueue = new ArrayList<ProjectedDB>();
		
		for(short i=1;i<=5000;i++){
			ProjectedDB a_pj=PrefixScan.prefixSpanByGivenItem(false,i,
					initProjectedDB);
			
			if(a_pj!=null){
				supItems.add(i);
				exeQueue.add(a_pj);
			}
			//helper(a_pj);
		}
		
		Iterator<ProjectedDB> iterator = exeQueue.iterator();
		while(iterator.hasNext()){
			ProjectedDB nextPjDB = iterator.next();
			helper(nextPjDB, supItems);
		}
	}
			
	private static void helper(ProjectedDB pjDb, List<Short> supportedItems) throws IOException{
		if(pjDb==null) return;
		if(pjDb.getPjDB().size()<min_suppport_count){
			System.err.println("caocao");
			return;}
		List<Short> supItems=new ArrayList<Short>();
		List<ProjectedDB> exeQueue = new ArrayList<ProjectedDB>();
		Iterator<Short> itemIteraotr = supportedItems.iterator();
		while(itemIteraotr.hasNext()){
			Short candidate = itemIteraotr.next();
			ProjectedDB pj_append = PrefixScan.prefixSpanByGivenItem(false,candidate,pjDb);
			ProjectedDB pj_assemble = PrefixScan.prefixSpanByGivenItem(true,candidate, pjDb);
			
			if(pj_append!=null||pj_assemble!=null){
				supItems.add(candidate);
			}
			if(pj_append!=null){
				exeQueue.add(pj_append);
			}
			if(pj_assemble!=null){
				exeQueue.add(pj_assemble);
			}
//			helper(pj_append);
//			helper(pj_assemble);
		}
		Iterator<ProjectedDB> newPjDBIterator = exeQueue.iterator();
		while(newPjDBIterator.hasNext()){
			ProjectedDB newPjDB = newPjDBIterator.next();
			helper(newPjDB, supportedItems);
		}
		
		
		
	}
	
	public static Set<Short> initSupportedItem(){
		Set<Short> set=new HashSet<Short>();
		for(short i=1;i<=5000;i++){
			set.add(i);
		}
		return set;
	}

}
