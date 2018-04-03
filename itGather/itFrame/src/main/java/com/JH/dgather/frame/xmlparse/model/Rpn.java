package com.JH.dgather.frame.xmlparse.model;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Rpn {
	final static Map<String, Integer> operationmap = new HashMap<String, Integer>();
	
	static {
		operationmap.put("#", 0);
		operationmap.put("+", 1);
		operationmap.put("-", 1);
		operationmap.put("*", 2);
		operationmap.put("/", 2);
		operationmap.put("(", 3);
		operationmap.put(")", 3);
	}
	
	Stack<String> operationStack = new Stack<String>();
	Stack<String> rpnStack = new Stack<String>();
	
	public float calculate(String[] rpnarray) {
		Stack<String> stack = new Stack<String>();
		for(String ch : rpnarray) {
			if(isOperation(ch)) {
				if("+".equals(ch)) {
					float a = Float.parseFloat(stack.pop());
					float b = Float.parseFloat(stack.pop());
					stack.push((a + b) + "");
				}
				else if("-".equals(ch)) {
					float a = Float.parseFloat(stack.pop());
					float b = Float.parseFloat(stack.pop());
					stack.push((b - a) + "");
				}
				else if("*".equals(ch)) {
					float a = Float.parseFloat(stack.pop());
					float b = Float.parseFloat(stack.pop());
					stack.push((a * b) + "");
				}
				else if("/".equals(ch)) {
					float a = Float.parseFloat(stack.pop());
					float b = Float.parseFloat(stack.pop());
					stack.push((b / a) + "");
				}
			} else {
				stack.push(ch);
			}
		}
		
		return Float.parseFloat(stack.pop());
	}
	
	public String[] toRpn(String expr) {
		operationStack.push("#");
		rec(getExprArray(expr), 0);
		while(!operationStack.isEmpty()) {
			rpnStack.push(operationStack.pop());
		}
		int size = rpnStack.size() - 1;
		String[] rpn = new String[size];
		for(int i = 0; i < size; i++) {
			rpn[i] = rpnStack.get(i);
		}
		return rpn;
	}

	public void rec(String[] array, int index) {
		String ch = array[index];
		String oper = null;
		
		if(!isOperation(ch)) {
			rpnStack.push(ch);
		} else if(!operationStack.isEmpty() && "(".equals(operationStack.top()) && ")".equals(ch)) {
			operationStack.pop();
		} else if(!operationStack.isEmpty() && "(".equals(operationStack.top())) {
			operationStack.push(ch);
		} else {
			if("(".equals(ch)) {
				operationStack.push(ch);
			} else if(")".equals(ch)) {
				while(!operationStack.isEmpty() && !(oper = operationStack.pop()).equals("(")) {
					rpnStack.push(oper);
				}
			} else {
				if(!operationStack.isEmpty() && getPriority(ch) > getPriority(operationStack.top())) {
					operationStack.push(ch);
				} else {
					while(!operationStack.isEmpty()) {
						oper = operationStack.top();
						
						if(getPriority(oper) < getPriority(ch) || "(".equals(oper)) {
							operationStack.push(ch);
							break;
						} else {
							rpnStack.push(operationStack.pop());
						}
					}
				}
			}
		}
		
		if(++ index < array.length) {
			rec(array, index);
		}
	}
	
	public boolean isOperation(String ch) {
		return operationmap.containsKey(ch);
	}
	
	public int getPriority(String oper) {
		return operationmap.get(oper);
	}
	
	public static String[] getExprArray(String expr) {
		List<String> array = new ArrayList<String>();
		int index = 0, start = 0;
		while(index < expr.length()) {
			char c = expr.charAt(index ++);
			if(operationmap.containsKey(c + "")) {
				if(start != (index - 1))
					array.add(expr.substring(start, index - 1));
				array.add(c + "");
				start = index;
			}
		}
		if(start != index)
			array.add(expr.substring(start, index));
		return array.toArray(new String[array.size()]);
	}
	
	public class Stack<T> {
		private static final int DEFAULT_STACK_SIZE = 128;
		
		private T[] m_delegate;
		private int count = 0;
		
		public Stack() {
			this(DEFAULT_STACK_SIZE);
		}
		
		@SuppressWarnings("unchecked")
		public Stack(final int size) {
			m_delegate = (T[]) new Object[size];
		}
		
		public void push(T element) {
			if(count == m_delegate.length) {
				throw new RuntimeException("Stack overflow.");
			}
			
			m_delegate[count ++] = element;
		}
		
		public T pop() {
			if(count == 0) {
				throw new RuntimeException("Cannot call pop on an empty stack.");
			}
			
			return m_delegate[-- count];
		}
		
		public T top() {
			if(count == 0) {
				throw new RuntimeException("The current stack is empty");
			}

			return m_delegate[count - 1];
		}
		
		public T get(int index) {
			if(index > count) {
				throw new RuntimeException("Stack index out of range: " + index);
			}
			return m_delegate[index];
		}
		
		public int size() {
			return count;
		}
		
		public boolean isEmpty() {
			return count == 0;
		}
		
		public void clear() {
			count = 0;
		}
	}
}
