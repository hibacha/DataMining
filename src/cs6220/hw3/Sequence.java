package cs6220.hw3;

import java.util.List;

public class Sequence {
	
	private List<Event> events;
	private int id;
	
	public Sequence(List<Event> loe) {
		events=loe;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isEventIn(Short candidate, SmartPointer spointer){
		List<Event> sublist =events.subList(spointer.getEventIndex(), events.size());
		for(Event e:sublist){
			if(e.isEventIn(candidate, spointer.getItemIndex()))
				return true;
		}
		return false;
	}
	
	public boolean isAssembleIn(Short prefixLast, Short candidate, SmartPointer spointer){
		List<Event> sublist =events.subList(spointer.getEventIndex(), events.size());
		for(Event e:sublist){
			if(e.isAssembleIn(prefixLast, candidate, spointer.getItemIndex()))
				return true;
		}
		return false;
	}
	
	@Override
	public String toString() {
		return "Sequence [events=" + events + "]";
	}
    
}
