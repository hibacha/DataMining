package cs6220.hw3;

import java.util.HashMap;

public class ProjectedDB {
	
	@Override
	public String toString() {
//		return "ProjectedDB [prefix=" + prefix + ", \n pjDB=" + pjDB + "]";
		return "prefix=" + prefix ;
	}

	private Sequence prefix=new Sequence();

	
	
	public Sequence getPrefix() {
		return prefix;
	}

	public void setPrefix(Sequence prefix) {
		this.prefix = prefix;
	}

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
