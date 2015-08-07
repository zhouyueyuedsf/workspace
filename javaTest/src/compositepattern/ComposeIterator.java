package compositepattern;
import java.util.Iterator;
import java.util.Stack;

public class ComposeIterator implements Iterator {
	private Stack<Iterator> stack = new Stack<Iterator>();

	public ComposeIterator(Iterator iterator) {
		stack.push(iterator);
	}

	@Override
	public boolean hasNext() {
		// TODO Auto-generated method stub
		if (stack.empty()) {
			return false;
		}
		Iterator iterator = stack.peek();
		if (!iterator.hasNext()) {//如果迭代器下一个没有了就弹出
			stack.pop();
			return hasNext();
		} else {
			return true;
		}
	}

	@Override
	public Object next() {
		// TODO Auto-generated method stub
		if (hasNext()) {
			Iterator iterator = stack.peek();//只是查看,并不是弹出
			MenuComponent mMenuComponent = (MenuComponent) iterator.next();
			stack.push(mMenuComponent.getIterator());
			return mMenuComponent;
		}
		return null;
	}

	@Override
	public void remove() {
		// TODO Auto-generated method stub

	}

}
