package javaInterface_Abstract;

import javaInterface_Abstract.TestActivity.ILoginListener;
import javaInterface_Abstract.TestActivity.ISignUpListener;

public class TestjavaInerface	extends  BaseActivity	implements ISignUpListener,ILoginListener {
	@Override
	public void onLoginSuccess() {
		// TODO Auto-generated method stub
		
	}

	public void onLoginFailure(String msg) {
		// TODO Auto-generated method stub
		
	}

	public void onSignUpSuccess() {
		// TODO Auto-generated method stub
		
	}

	public void onSignUpFailure(String msg) {
		// TODO Auto-generated method stub
		
	}

}
