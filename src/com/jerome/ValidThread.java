package com.jerome;

import javax.servlet.AsyncContext;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

public class ValidThread implements Runnable {

	private AsyncContext context;

	public ValidThread(AsyncContext _context) {
		this.context = _context;
	}

	@Override
	public void run() {

		HttpServletResponse peer = (HttpServletResponse) context.getResponse();
		String id = context.getRequest().getParameter("id");
		int count = 0;
		while (true) {
			count++;
			try {
				if (Util.isPassed(id)) {
					try {
						peer.getWriter().write(
								new JSONArray().put("pass").toString());
						peer.setStatus(HttpServletResponse.SC_OK);
						peer.setContentType("application/json");
						context.complete();
						break;
					} catch (Exception e) {

					}
				} else {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			} catch (Exception e) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			} 
//			if (count >= 30) {
//				try {
//					peer.getWriter()
//							.write(new JSONArray().put("no").toString());
//					peer.setStatus(HttpServletResponse.SC_OK);
//					peer.setContentType("application/json");
//					context.complete();
//					break;
//				} catch (Exception e) {
//					break;
//				} 
//
//			}
		}

	}

}
