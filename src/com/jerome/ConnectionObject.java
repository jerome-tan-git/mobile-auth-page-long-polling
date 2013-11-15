package com.jerome;

import javax.servlet.AsyncContext;

public class ConnectionObject {
	private AsyncContext context;
	private long lastHeartbeat;
	public AsyncContext getContext() {
		return context;
	}
	public void setContext(AsyncContext context) {
		this.context = context;
	}
	public long getLastHeartbeat() {
		return lastHeartbeat;
	}
	public void setLastHeartbeat(long lastHeartbeat) {
		this.lastHeartbeat = lastHeartbeat;
	}
}
