package net.java.amateras.xlsbeans;

import java.lang.reflect.Method;

/**
 * 
 * @author Naoki Takezoe
 */
public class NeedPostProcess {
	
	private Object target;
	private Method method;
	
	public NeedPostProcess(Object target, Method method){
		this.target = target;
		this.method = method;
	}
	
	/**
	 * @return Returns the method.
	 */
	public Method getMethod() {
		return method;
	}
	/**
	 * @return Returns the target.
	 */
	public Object getTarget() {
		return target;
	}
	
	
	
}
