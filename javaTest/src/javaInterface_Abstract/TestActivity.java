package javaInterface_Abstract;

public class TestActivity {

	public interface ILoginListener {
		void onLoginSuccess();//��������������
		void onLoginFailure(String msg);
	}
	private ILoginListener loginListener;
	public void setOnLoginListener(ILoginListener loginListener){
		this.loginListener  = loginListener;
	}
	
	public interface  ISignUpListener {
		void onSignUpSuccess();
		void onSignUpFailure(String msg);
		void onLoginSuccess();
	}
	private ISignUpListener signUpLister;
	public void setOnSignUpListener(ISignUpListener signUpLister){
		this.signUpLister = signUpLister;
	}
	public static void main(String[] args) {
		
	}
}