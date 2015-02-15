package interfaceCallback;

public class Printer {
	ICallBack ic;
	public void setCallBack(ICallBack ic){
		this.ic = ic;
	}
	public void excute(){
		ic.print();//这个去回调了PrintHandler匿名内部类里面的方法
	}
}
