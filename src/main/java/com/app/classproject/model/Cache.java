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

	public void addToCache(int key, int[] value) {
		if(cacheLine.size() >= numCacheLines) {
			cacheLine.removeLast(); // FIFO
		}
		else {
			cacheLine.addFirst(new CacheLine(key, value));
		}
	}
	
	/**
	 * Check to see if cache contains value for given address
	 * @param address
	 * @return value or null
	 */
	public int[] checkCache(int address) {
		for(int i = 0; i < cacheLine.size(); i++) {
			if(cacheLine.get(i).key == address) {
				return cacheLine.get(i).value;
			}
		}
		
		return null;
	}
	
	public class CacheLine {
		private int key; // Address
		private int[] value; // Data stored at address
		
		public CacheLine(int key, int[] value) {
			this.key = key;
			this.value = value.clone();
		}
		
		public int getKey() {
			return key;
		}
		
		public void setKey(int key) {
			this.key = key;
		}
		
		public int[] getValue() {
			return value;
		}
		
		public void setValue(int[] value) {
			this.value = value.clone();
		}
	}
}
