package closureandcallback;

public class Callee1 implements Increamentable {
	private int i=0;
	public void increment() {
		// TODO Auto-generated method stub
		i++;
		System.out.println(i);
	}

}
