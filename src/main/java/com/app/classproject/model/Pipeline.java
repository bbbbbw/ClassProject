package com.app.classproject.model;

/**
 * RISC 5-stage instruction pipeline
 */
public class Pipeline {
	// Interface registers to hold intermediate output between 2 stages
	Instructions interfaceRegister1;
	int interfaceRegister2;
	
	public Pipeline() {
		
	}
	
	// Stage 1. Read instructions from address in memory whose value is present in PC
	public Instructions instructionFetch(memory[] RAM, Register pc, Computer computer) {
		Instructions instruction = new Instructions(RAM[pc.getBase10Value()].MEM, computer);
		interfaceRegister1 = instruction;
		return new Instructions(RAM[pc.getBase10Value()].MEM, computer);
	}
	
	// Stage 2. Decode instruction & access register file
	public void instructionDecode() {
		int retCode = 1;
		
	    switch (interfaceRegister1.instruction.opc) {
	    case 1:
	        retCode = interfaceRegister1.LDRStage2();
	        break;
	    case 2:
	    	retCode = interfaceRegister1.STRStage2();
	    	break;
	    case 3:
	    	retCode = interfaceRegister1.LDAStage2();
	    	break;
	    case 41:
	    	retCode = interfaceRegister1.LDXStage2();
	    	break;
	    case 42:
	    	retCode = interfaceRegister1.STXStage2();
	    	break;
	    case 10:
	    	retCode = interfaceRegister1.JZStage2();
	    	break;
	    case 11:
	    	retCode = interfaceRegister1.JNEStage2();
	    	break;
	    case 12:
	    	retCode = interfaceRegister1.JCCStage2();
	    	break;
	    case 13:
	    	retCode = interfaceRegister1.JMAStage2();
	    	break;
	    case 14:
	    	retCode = interfaceRegister1.JSRStage2();
	    	break;
	    case 16:
	    	retCode = interfaceRegister1.SOBStage2();
	    	break;
	    case 17:
	    	retCode = interfaceRegister1.JGEStage2();
	    	break;
	    case 4:
	    	retCode = interfaceRegister1.AMRStage2();
	    	break;
	    case 5:
	    	retCode = interfaceRegister1.SMRStage2();
	    	break;
	    case 6:
	    	retCode = interfaceRegister1.AIRStage2();
	    	break;
	    case 7:
	    	retCode = interfaceRegister1.SIRStage2();
	    	break;
	    case 20:
	    	retCode = interfaceRegister1.MLTStage2();
	    	break;
	    case 21:
	    	retCode = interfaceRegister1.DVDStage2();
	    	break;
	    case 22:
	    	retCode = interfaceRegister1.TRRStage2();
	    	break;
	    case 23:
	    	retCode = interfaceRegister1.ANDStage2();
	    	break;
	    case 24:
	    	retCode = interfaceRegister1.ORRStage2();
	    	break;
	    case 25:
	    	retCode = interfaceRegister1.NOTStage2();
	    	break;
	    case 31:
	    	retCode = interfaceRegister1.SRCStage2();
	    	break;
	    case 32:
	    	retCode = interfaceRegister1.RRCStage2();
	    	break;
	    case 62:
	    	retCode = interfaceRegister1.OUTStage2();
	        break;
	    }
	    
	    if(retCode == 1) {
	    	interfaceRegister2 = interfaceRegister1.instruction.opc;	
	    }
	}
	
	// Stage 3. Perform ALU operations
	public void instructionExecute() {
		
	}
	
	// Stage 4. Memory operands are read and written from/to the memory present in the instruction
	public void memoryAccess() {
		
	}
	
	// Stage 5. Computed/fetched value is written back to the register present in the instructions
	public void writeBack() {
		
	}
}
