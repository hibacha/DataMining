package cs6220.hw3;

import java.util.HashMap;
import java.util.List;

public class ProjectedDB {
	
	private List<Event> prefix;
	public HashMap<Integer, SmartPointer> getPjDB() {
		return pjDB;
	}

	public void setPjDB(HashMap<Integer, SmartPointer> pjDB) {
		this.pjDB = pjDB;
	}

	private HashMap<Integer, SmartPointer> pjDB = new HashMap<Integer, SmartPointer>();

	public ProjectedDB() {
		// TODO Auto-generated constructor stub
	}

	public void addPostFix(Integer id, SmartPointer pointer) {
		pjDB.put(id,pointer);
	}

	

}
