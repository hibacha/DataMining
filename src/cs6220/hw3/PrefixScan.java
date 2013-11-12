package cs6220.hw3;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class PrefixScan {

	/**
	 * @param args
	 */
	
	public static ProjectedDB prefixSpanByGivenItem(boolean isAssemble, Short candidate, ProjectedDB pdb){
		HashMap<Integer, SmartPointer> pjDB = pdb.getPjDB();
		Set<Integer> keys = pdb.getPjDB().keySet();
		Iterator<Integer> iterator = keys.iterator();
		int supportCount=0;
		ProjectedDB newpjDB = new ProjectedDB();
		
		while(iterator.hasNext()){
			Integer seqId= iterator.next();
			SmartPointer postfix = pjDB.get(seqId);
			Sequence sq = Runner.originalDB.get(seqId);
			SmartPointer newPointer=null;
			if(isAssemble){
				Event lastEvent = pdb.getPrefix().getLastEvent();
				newPointer = sq.isAssembleIn(lastEvent.getItems(), candidate, postfix);
			}else{
				newPointer = sq.isEventIn(candidate, postfix);
			}
			if(newPointer!=null){
				supportCount++;
				newpjDB.addPostFix(seqId, newPointer);
			}
		}
		if(supportCount>=Runner.min_suppport_count){
			System.out.println(candidate+"is appended to prefix");
			if(isAssemble){
				Sequence cloned = pdb.getPrefix().clone();
				cloned.mergeCandidate(candidate);
				newpjDB.setPrefix(cloned);
			}else{
				Sequence cloned = pdb.getPrefix().clone();
				cloned.append(new Event(candidate));
				newpjDB.setPrefix(cloned);
			}
			System.out.println(newpjDB);
			return newpjDB;
		}
		
		return null;
	}
	
}
