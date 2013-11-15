package com.jerome;

import org.codehaus.jettison.json.JSONArray;

import javax.servlet.AsyncContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * @author Mathieu Carbou (mathieu.carbou@gmail.com)
 */
public final class ReverseAjaxServlet extends HttpServlet {

	private final Queue<AsyncContext> asyncContexts = new ConcurrentLinkedQueue<AsyncContext>();
	public static ConcurrentHashMap<String, ConnectionObject> map = new ConcurrentHashMap<String, ConnectionObject>();
	private final Random random = new Random();
	private final Thread generator = new Thread("Event generator") {
		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {

				while (!asyncContexts.isEmpty()) {
					
					AsyncContext asyncContext = asyncContexts.poll();
					String id = asyncContext.getRequest().getParameter("id");
					if (id != null && !id.trim().equals(""))
					{
//						if (ReverseAjaxServlet.map.containsKey(id))
//						{
//							ConnectionObject co = ReverseAjaxServlet.map.get(id);
//							co.getContext().complete();
//							ReverseAjaxServlet.map.remove(id);
//						}
						ConnectionObject co = new ConnectionObject();
						co.setContext(asyncContext);
						co.setLastHeartbeat(System.currentTimeMillis());
						ReverseAjaxServlet.map.put(id, co);
						System.out.println("Add new context: " + id);
					}
//					ValidThread vt = new ValidThread(asyncContext);
//					Thread x = new Thread(vt);
//					x.start();
				}

			}
		}
	};

	@Override
	public void init() throws ServletException {
		generator.start();
	}

	@Override
	public void destroy() {
		generator.interrupt();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		AsyncContext asyncContext = req.startAsync();
		asyncContext.setTimeout(0);
		asyncContexts.offer(asyncContext);
	}
}
