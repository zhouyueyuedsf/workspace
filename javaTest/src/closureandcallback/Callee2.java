package closureandcallback;

public class Callee2 extends MyIncrement {
	private int i = 0;

	// 该increment方法为MyIncrement的重写，跟接口完全不相关，而又得实现接口方法，只有使用内部类
	public void increment() {
		super.increment();
		i++;
		System.out.println(i);
	}
	//内部类
	private class Closure implements Increamentable {

		public void increment() {
			// TODO Auto-generated method stub
			Callee2.this.increment();
		}

	}

	Increamentable getCallbackReference() {
		return new Closure();
	}

}
