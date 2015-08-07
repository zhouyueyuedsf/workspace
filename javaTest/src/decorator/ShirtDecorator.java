package decorator;

public class ShirtDecorator extends PersonDecorator {
	public ShirtDecorator(Person person) {
		// TODO Auto-generated constructor stub
		super(person);
	}
	public void show() {
		// TODO Auto-generated method stub
		this.getPerson().show();//获得对象的引用,由于super(person)已经调用过this.person = person
		this.shirtDecorator();
	}
	public void shirtDecorator(){
		System.out.println("我穿上白色的衬衫,哈哈");
	}
	public void noDecorator() {
		// TODO Auto-generated method stub
		
	}
}
