package interfaceCallback;

public class PrintHandler {

	/**
	 * 我们把其他俩个类当作API
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Printer printer = new Printer();
	
		//匿名内部类提过print()方法，在API中print()方法被调用
		printer.setCallBack(new ICallBack() {
			public void print() {
				// TODO Auto-generated method stub
				System.out.print("API 调用了我");
			}
		});
		printer.excute();
	}
	
}
