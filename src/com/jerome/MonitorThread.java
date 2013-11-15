package com.jerome;

import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;

public class MonitorThread implements Runnable{

	@Override
	public void run() {
		while(true)
		{
			if(ReverseAjaxServlet.map!=null && ReverseAjaxServlet.map.size()>0)
			{
				Set<String> keys = ReverseAjaxServlet.map.keySet();
				for(String key: keys)
				{
					ConnectionObject co = ReverseAjaxServlet.map.get(key);
					HttpServletResponse peer = (HttpServletResponse) co.getContext().getResponse();
					String id =  co.getContext().getRequest().getParameter("id");
					try {
						
						peer.getWriter().write(
								new JSONArray().put("").toString());
						peer.setStatus(HttpServletResponse.SC_OK);
						peer.setContentType("application/json");
						peer.flushBuffer();
						co.getContext().complete();
						ReverseAjaxServlet.map.remove(id);
						co = null;
					} catch (Exception e) {
						System.out.println("ID: " + id +" closed, " + e.getMessage() );
					}
				}
			}
			try {
				Thread.sleep(30000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
