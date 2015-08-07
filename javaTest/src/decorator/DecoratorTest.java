package decorator;

public class DecoratorTest {
	/*
	 * 优点:从下面的测试代码可以看出,此模式优点是可以扩展很多功能,如果GG再想穿一双皮鞋就在代码里面加一个皮
	 * 鞋的类,以便于代码调整.而且也可以随意排列组合
	 * 缺点:创造了很多对象,产生了一定的管理问题
	 */
	public static void main(String[] args) {
		
		Person person = new GG();
		person.show();
		Person personJean = new JeanDecorator(person);
		personJean.show();
		Person personShirt = new ShirtDecorator(personJean);
		personShirt.show();
	}
}
