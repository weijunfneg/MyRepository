package com.iflytek.utils;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;

public class TransactionHandler extends DataSourceUtils implements InvocationHandler{
	
	private Object target;
	
	public void setTarget(Object target) {
		this.target = target;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		Connection conn=getConnection();
		try {
			conn.setAutoCommit(false);
			Object res=method.invoke(target, args);
			conn.commit();
			return res;
		} catch (Exception e) {
			conn.rollback();
			e.printStackTrace();
		}
		return null;
	}
	
	public Object getProxy() {
		return Proxy.newProxyInstance(
				this.getClass().getClassLoader(),
				this.target.getClass().getInterfaces(), 
				this
			);
	}
	

}
