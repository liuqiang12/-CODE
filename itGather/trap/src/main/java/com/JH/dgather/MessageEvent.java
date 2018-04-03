package com.JH.dgather;

import java.util.Date;

public class MessageEvent {
	   private String message;
	    private String time;
	    private String sourceIP;
	    private String sourcePort;
	    private int facility;
	    private int level;
	    public String getMessage() {
	        return this.message;
	    }
	    public void setMessage(String message) {
	        this.message = message;
	    }
	    public String getTime() {
	        return this.time;
	    }
	    public void setTime(String time) {
	        this.time = time;
	    }
	    public String getSourceIP() {
	        return this.sourceIP;
	    }
	    public void setSourceIP(String sourceIP) {
	        this.sourceIP = sourceIP;
	    }
	    public int getFacility() {
	        return this.facility;
	    }
	    public void setFacility(int facility) {
	        this.facility = facility;
	    }
	    public int getLevel() {
	        return this.level;
	    }
	    public void setLevel(int level) {
	        this.level = level;
	    }
		public String getSourcePort() {
			return sourcePort;
		}
		public void setSourcePort(String sourcePort) {
			this.sourcePort = sourcePort;
		}
	    
	    
}
