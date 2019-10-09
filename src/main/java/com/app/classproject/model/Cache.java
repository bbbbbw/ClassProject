package com.app.classproject.model;

import java.util.LinkedList;

public class Cache {
	private int numCacheLines = 16;
	private LinkedList<CacheLine> cacheLine;
	
	public Cache() {
		this.cacheLine = new LinkedList<CacheLine>();
		
	}
	
	public LinkedList<CacheLine> getCacheLine(){
		return cacheLine;
	}

	public void addToCache(int key, String value) {
		if(cacheLine.size() >= numCacheLines) {
			cacheLine.removeLast(); // FIFO
		}
		else {
			cacheLine.addFirst(new CacheLine(key, value));
		}
	}
	
	public class CacheLine {
		private int key; // Address
		private String value; // Data stored at address
		
		public CacheLine(int key, String value) {
			this.key = key;
			this.value = value;
		}
		
		public int getKey() {
			return key;
		}
		
		public void setKey(int key) {
			this.key = key;
		}
		
		public String getValue() {
			return value;
		}
		
		public void setValue(String value) {
			this.value = value;
		}
	}
}
