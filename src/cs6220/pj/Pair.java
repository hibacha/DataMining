package cs6220.pj;

public class Pair implements Comparable<Pair>{
	private String name;
	private Integer number;
	
	public Pair(String name, Integer number){
		this.name=name;
		this.number=number;
	}
	
	@Override
	public String toString() {
		return "Pair [name=" + name + ", number=" + number + "]";
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	@Override
	public int compareTo(Pair o) {
		// TODO Auto-generated method stub
		return this.getNumber()-o.getNumber();
	}

}
