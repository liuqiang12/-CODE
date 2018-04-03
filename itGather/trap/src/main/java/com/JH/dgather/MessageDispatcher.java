package com.JH.dgather;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Collection;
import java.util.Date;
import java.util.Vector;

import org.apache.log4j.Logger;




public class MessageDispatcher {
	private Logger logger = Logger.getLogger(MessageDispatcher.class);
	private static final long serialVersionUID = -7158976627583148519L;
	private static MessageDispatcher disp = new MessageDispatcher();
	private Vector<MessageListenerIF> listeners = new Vector<MessageListenerIF>(
			0);
	
	public MessageDispatcher() {;}
	
	public static MessageDispatcher getInstance() {
		return disp;
	}

	public synchronized void addMessageListener(MessageListenerIF sml) {
		// System.out.println("add listener");

		listeners.add(sml);
		// System.out.println("listener.size=="+listeners.size());
	}

	public synchronized boolean removeMessageListener(MessageListenerIF sml) {
		return listeners.remove(sml);
	}

	public synchronized void fireMessageEvent(MessageEvent event) {
		logger.debug("收到消息开始分发:"+event.getSourceIP());
		Collection<MessageListenerIF> clone = (Collection<MessageListenerIF>) listeners
				.clone();
		for (MessageListenerIF listener : clone) {
			listener.actionPerformed(event);
		}
	}
/*
	private MessageEvent transform(MessageEvent event) {
		MessageEvent lEvent = new MessageEvent();
		lEvent.setFacility(event.getFacility());
		lEvent.setLevel(event.getLevel());
		lEvent.setMessage(event.getMessage());
		try {
			InetAddress iaddress = InetAddress.getByName(event.getSourceIP());
			lEvent.setSourceIP(iaddress.getHostAddress());
			String msg = "收到来至" + iaddress + "消息：" + lEvent.getMessage();
			logger.info(msg);
			//fileRecord.appendContext(msg);
		} catch (UnknownHostException e) {
			lEvent.setSourceIP(event.getSourceIP());
			logger.error("错误", e);
		}

		lEvent.setTime(event.getTime() == null ? new Date() : event.getTime());

		return lEvent;
	}
*/

	public static void main() {
		MessageListener sl = new MessageListener() {

			@Override
			public void actionPerformed(MessageEvent event) {
				event.getMessage();
				event.getSourceIP();
				event.getTime();
			}

		};
		MessageDispatcher disp = MessageDispatcher.getInstance();
		disp.addMessageListener(sl);
	}
}
