package com.app.classproject.model;

/**
 * RISC 5-stage instruction pipeline
 */
public class Pipeline {
	public Pipeline() {
		
	}
	
	// Stage 1. Read instructions from address in memory whose value is present in PC
	public Instructions instructionFetch(memory[] RAM, Register pc, Computer computer) {
		return new Instructions(RAM[pc.getBase10Value()].MEM, computer);
	}
}
