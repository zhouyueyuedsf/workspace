package closureandcallback;

public class Caller{
	private Increamentable callbackReference;
	Caller(Increamentable cbh){
		callbackReference = cbh;
	}
	void go(){
		callbackReference.increment();
	}
	
}