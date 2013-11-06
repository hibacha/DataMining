package cs6220.hw2;

public class Entropy {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(entropy(1,2));
		System.out.println(entropy(6,2));
		System.out.println(entropy(8, 5));
		System.out.println(entropy(1, 1));
		System.out.println(entropy(6, 2));
		System.out.println(entropy(3, 5));
		System.out.println(entropy(6, 6));
		System.out.println(entropy(3, 1));
		System.out.println("@"+entropy(9, 7));
		System.out.println(entropy(4, 2));
		System.out.println(entropy(1, 0));
		System.out.println(entropy(3, 5));
		
		System.out.println(entropy(3, 4));
		System.out.println(entropy(2, 4));
	}
	public static double entropy(double a, double b){
		double Pa= a/(a+b);
		double Pb= b/(a+b);
		
		return -1*(Pa*log2(Pa)+Pb*(log2(Pb)));
	}
	
	public static  double log2(double a){
		return Math.log10(a)/Math.log10(2);
	}
}
