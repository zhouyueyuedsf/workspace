package flyweight;

public class SpecialSweetWord implements BaseSweetWord{
	private String mychar;
	public SpecialSweetWord(String mychar) {
		// TODO Auto-generated constructor stub
		this.mychar = mychar;
	}
	public void display() {
		// TODO Auto-generated method stub
		System.out.println(mychar);
	}
}
