package decorator;
//人的装饰类
public abstract class PersonDecorator implements Person {
	private Person person;
	
	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public PersonDecorator(Person person) {
		// TODO Auto-generated constructor stub
		this.person = person;//引用
	}
	public abstract void show();
}
