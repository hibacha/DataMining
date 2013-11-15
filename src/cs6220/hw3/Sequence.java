package cs6220.hw3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Sequence {
	
	private List<Event> events=new ArrayList<Event>();
	private int id;
	private Map<Short,ArrayList<SmartPointer>> hash = new HashMap<Short,ArrayList<SmartPointer>>();
	
	public Sequence() {
	}
	
	
	public Sequence(List<Event> loe) {
		events=loe;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public Sequence clone(){
		List<Event> list=new ArrayList<Event>();
		for(Event s: events){
			list.add(s.clone());
		}
		Sequence clone=new Sequence(list);
		return clone;
	}
	public void append(Event e){
		events.add(e);
	}
	public void mergeCandidate(Short candidate){
		events.get(events.size()-1).mergeCandidate(candidate);
	}
	
	public Event getLastEvent(){
		return events.get(events.size()-1);
	}
	
	public SmartPointer isEventIn(Short candidate, SmartPointer spointer){
		List<Event> sublist =events.subList(spointer.getEventIndex(), events.size());
		SmartPointer newPointer=new SmartPointer();
		for(int i=0;i<sublist.size();i++){
			Event e= sublist.get(i);
			int code =e.isEventIn(candidate, i==0?spointer.getItemIndex():0);
			if(code==0){
				newPointer.setEventIndex(spointer.getEventIndex()+i+1);
			  	 newPointer.setItemIndex(0);
			  	 return newPointer;
			}else if(code>0){
			     newPointer.setEventIndex(spointer.getEventIndex()+i);
			     newPointer.setItemIndex(code);
			     return newPointer;
		    }
		}
		return null;
	}
	
	public SmartPointer isAssembleIn(List<Short> prefixLast, Short candidate, SmartPointer spointer){
		List<Event> sublist =events.subList(spointer.getEventIndex(), events.size());
		SmartPointer newPointer=new SmartPointer();
		for(int i=0;i<sublist.size();i++){
			Event e= sublist.get(i);
			//only the first event needs the offset for item index
			int code =e.isAssembleIn(prefixLast,candidate, i==0?spointer.getItemIndex():0);
			//TODO
			if(code==0){
				 newPointer.setEventIndex(spointer.getEventIndex()+i+1);
			  	 newPointer.setItemIndex(0);
			  	 return newPointer;
			}else if(code>0){
			     newPointer.setEventIndex(spointer.getEventIndex()+i);
			     newPointer.setItemIndex(code);
			     return newPointer;
		    }
		}
		return null;
	}
	
	@Override
	public String toString() {
		return "Sequence [events=" + events + "]";
	}
    
}
