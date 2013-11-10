package cs6220.hw3;

import java.util.List;

public class Event {

	private List<Short> items;
	public Event(List<Short> los) {
      items=los;
	}
	public int getSize(){
		return items.size();
	}
	
	public boolean isEventIn(Short candidate, Integer itemIndex){
		if(getSize()==1){
			return candidate==items.get(0);
		}else{
			if(itemIndex!=0){
				return  false;
			}
			return items.indexOf(candidate)!=-1;
		    
		}
	}
	
	public boolean isAssembleIn(Short prefixLast, Short candidate, Integer itemIndex){
        
		 if(itemIndex!=0){
			 return items.get(itemIndex)==candidate;
		 }else{
			 if(items.size()>1){
				 int cindex = items.indexOf(candidate);
				 if(cindex>0&&items.get(cindex-1)==prefixLast){
					 return true;
				 }else{
					 return false;
				 }
			 }else{
				 return false;
			 }
		 }
	}
	
	@Override
	public String toString() {
		StringBuilder sb=new StringBuilder();
		sb.append("(");
		for(Short e:items){
			sb.append(String.copyValueOf(Character.toChars(e)).toLowerCase()).append(",");
		}
		sb.setCharAt(sb.length()-1, ')');
		return sb.toString();
	}
	
}
