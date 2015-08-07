package flyweight;
//具体享元类
public class MySweetWord {
	private String mychar;
	public MySweetWord(String mychar){
		this.mychar = mychar;
	}
	public void display() {
		// TODO Auto-generated method stub
		System.out.println(mychar);
	}
	
}
