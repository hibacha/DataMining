package cs6220.hw3;

import java.util.ArrayList;
import java.util.List;

public class Event {

	private List<Short> items=new ArrayList<Short>();
	
	public List<Short> getItems() {
		return items;
	}

	public void setItems(List<Short> items) {
		this.items = items;
	}

	public Event clone(){
		List<Short> clonedItems=new ArrayList<Short>();
		for(Short s:items){
			clonedItems.add(s);
		}
		Event cloneEvent=new Event(clonedItems);
	   	return cloneEvent;
	}
	
	public void mergeCandidate(Short candidate){
	    items.add(candidate);
	}
	
	public Event(List<Short> los) {
      items=los;
	}
	public Event(Short item){
		items.add(item);
	}
	public int getSize(){
		return items.size();
	}
	/**
	 * 
	 * @param candidate
	 * @param itemIndex
	 * @return candidate not in return -1
	 * 		   candidate match single return 0
	 * 		   candidate match last  return 0
	 * 		   candidate match not last return next index
	 */
	public int isEventIn(Short candidate, Integer itemIndex){
		List<Short> sublist=items.subList(itemIndex, items.size());
		if(getSize()==1){
			return candidate.equals(items.get(0))?0:-1;
		}else{
			if(itemIndex!=0){
				return  -1;
			}
			int indexOfCandidate = sublist.indexOf(candidate)+itemIndex;
			if(indexOfCandidate==-1){
				return -1;
			}else if(indexOfCandidate==(items.size()-1)){
				return 0;
			}else{
				return indexOfCandidate+1;
			}
		}
	}
	
	public int isAssembleIn(List<Short> prefixLast, Short candidate, Integer itemIndex){
		List<Short> sublist=items.subList(itemIndex, items.size());
		 if(itemIndex!=0){
			 int indexOfCandidate = sublist.indexOf(candidate);
			 if((indexOfCandidate+itemIndex)<items.size()-1&&indexOfCandidate!=-1)
				 return indexOfCandidate+itemIndex+1;
			 else if((indexOfCandidate+itemIndex)==items.size()-1)
				 return 0;
			 else
				 return -1;
		 }else{
			 if(items.size()>1){
				
				 if(prefixLast.contains(candidate)){
					 return -1;
				 }
				 if(sublist.containsAll(prefixLast)&&sublist.contains(candidate)){
					 int indexOfCandidate=sublist.indexOf(candidate)+itemIndex;
					 if(indexOfCandidate<items.size()-1&&indexOfCandidate!=-1)
						 return indexOfCandidate+1;
					 else if(indexOfCandidate==items.size()-1)
						 return 0;
					 else
						 return -1;
				 }else{
					 return -1;
				 }
			 }else{
				 return -1;
			 }
		 }
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("(");
		for(Short e:items){
		//	sb.append(String.copyValueOf(Character.toChars(e)).toLowerCase()).append(",");
			sb.append(e).append(",");
		}
		sb.setCharAt(sb.length()-1, ')');
		return sb.toString();
	}
	
}
