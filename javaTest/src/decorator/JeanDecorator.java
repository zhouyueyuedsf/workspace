package decorator;
//装饰类中的牛仔裤类
public class JeanDecorator extends PersonDecorator{
	public JeanDecorator(Person person) {
		// TODO Auto-generated constructor stub
		super(person);//调用父类构造函数
	}
	public void show() {
		// TODO Auto-generated method stub
		this.getPerson().show();//
		this.jeansDecorator();
	}
	public void jeansDecorator(){
		System.out.println("我穿上了牛仔裤,哈哈");
	}
	public void noDecorator() {
		// TODO Auto-generated method stub
		
	}

}
