package com.app.classproject.model;

import java.util.LinkedList;

/**
 * 16 line cache
 */
public class Cache {
	private LinkedList<CacheLine> cacheLine;
	
	public Cache() {
		this.cacheLine = new LinkedList<CacheLine>();
		
	}
	
	public LinkedList<CacheLine> getCacheLine(){
		return cacheLine;
	}

	public void addToCache(int address, String value) {
		if(cacheLine.size() >= 16) {
			cacheLine.removeLast(); // FIFO
		}
		else {
			cacheLine.addFirst(new CacheLine(address, value));
		}
	}
	
	public class CacheLine {
		private int tag;
		private String data;
		
		public CacheLine(int tag, String data ) {
			this.tag = tag;
			this.data = data;
		}
		
		public int getTag() {
			return tag;
		}
		
		public void setTag(int tag) {
			this.tag = tag;
		}
		
		public String getData() {
			return data;
		}
		
		public void setData(String data) {
			this.data = data;
		}
	}
}
