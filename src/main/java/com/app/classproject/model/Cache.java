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
	 * @return value (base 2 array) or null
	 */
	public int[] checkCache(int address) {
		for(int i = 0; i < cacheLine.size(); i++) {
			if(cacheLine.get(i).key == address) {
				return cacheLine.get(i).value;
			}
		}
		
		return null;
	}
	
	/**
	 * Check to see if cache contains value for given address
	 * @param address
	 * @return value (base 10 integer) or -1
	 */
	public int checkCacheInt(int address) {
		for(int i = 0; i < cacheLine.size(); i++) {
			if(cacheLine.get(i).key == address) {
				return getBase10Value(cacheLine.get(i).value);
			}
		}
		
		return -1;
	}
	
    /**
     * Covert value from base 2 array to base 10 integer and return
     */
    public int getBase10Value(int[] base2Arr) {
        int base10Value = 0;
        int multiplier = 1;

        for (int i = base2Arr.length - 1; i >= 0; i--) {
            if (base2Arr[i] == 1) {
                base10Value += multiplier;
            }
            multiplier *= 2;
        }

        return base10Value;
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
