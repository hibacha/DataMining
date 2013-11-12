package cs6220.hw3;

public class SmartPointer {
	private Integer eventIndex=0;
	private Integer itemIndex=0;
	public Integer getEventIndex() {
		return eventIndex;
	}
	public void setEventIndex(Integer eventIndex) {
		this.eventIndex = eventIndex;
	}
	public Integer getItemIndex() {
		return itemIndex;
	}
	public void setItemIndex(Integer itemIndex) {
		this.itemIndex = itemIndex;
	}
	@Override
	public String toString() {
		return "SmartPointer [eventIndex=" + eventIndex + ", itemIndex="
				+ itemIndex + "]\n";
	}
	
}
