package closureandcallback;

public class Callbacks {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Callee1 c1 = new Callee1();
		Callee2 c2 = new Callee2();
		MyIncrement.f(c2);
		Caller caller1 = new Caller(c1);
		Caller caller2 = new Caller(c2.getCallbackReference());
		
		caller1.go();//调用了自己的Increment函数
		caller1.go();
		caller2.go();
		caller2.go();
	}

}
