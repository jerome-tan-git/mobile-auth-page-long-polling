package com.jerome;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class MonitorRedis extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	public MonitorRedis()
	{
		super();
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
 
		System.out.println("Init reids");
		RedisThread rt = new RedisThread();
		Thread t = new Thread(rt);
		t.start();
	}
}
