package closureandcallback;

public class MyIncrement {
	public void increment(){
		System.out.println("Other operation");
	}
	static void f(MyIncrement mi){
		mi.increment();//调用了Callee2的increment方法
	}
}
