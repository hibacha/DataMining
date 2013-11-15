package cs6220.hw3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class PrefixScan {

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static
	<T extends Comparable<? super T>> List<T> asSortedList(Collection<T> c) {
	  List<T> list = new ArrayList<T>(c);
	  Collections.sort(list);
	  return list;
	}
	public static ProjectedDB prefixSpanByGivenItem(boolean isAssemble, Short candidate, ProjectedDB pdb) throws IOException{
		HashMap<Integer, SmartPointer> pjDB = pdb.getPjDB();
		Set<Integer> keys = pdb.getPjDB().keySet();
		int supportCount=0;
		ProjectedDB newpjDB = new ProjectedDB();
		Iterator<Integer> iterator=keys.iterator();
		//SeqReader sr = new SeqReader(Parser.PATH);
		while(iterator.hasNext()){
			Integer seqId=iterator.next();
//			System.out.println("lineNO:"+seqId);
			SmartPointer postfix = pjDB.get(seqId);
			Sequence sq = Runner.originalDB.get(seqId);;
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
			//System.out.println(candidate+"is appended to prefix");
			if(isAssemble){
				Sequence cloned = pdb.getPrefix().clone();
				cloned.mergeCandidate(candidate);
				newpjDB.setPrefix(cloned);
				newpjDB.setRelativeSupport((double)supportCount/Runner.size);
			}else{
				Sequence cloned = pdb.getPrefix().clone();
				cloned.append(new Event(candidate));
				newpjDB.setPrefix(cloned);
				newpjDB.setRelativeSupport((double)supportCount/Runner.size);
			}
			System.out.println(newpjDB);
			return newpjDB;
		}
		
		return null;
	}
	
}
