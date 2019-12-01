package com.app.classproject.model;

import com.app.classproject.controller.NewActionController;

/**
 * RISC 5-stage instruction pipeline
 */
public class Pipeline {
	Computer computer;
	
	// Interface registers to hold intermediate output between 2 stages
	Instructions interfaceRegister1 = null;
	Instructions interfaceRegister2 = null;
	Instructions interfaceRegister3 = null;
	Instructions interfaceRegister4 = null;
	
	public Pipeline(Computer computer) {
		this.computer = computer;
	}
	
	/**
	 * Move all instructions through pipeline
	 */
	public int incrementClock() {
		if(interfaceRegister4 != null) {
			writeBack();
			interfaceRegister4 = null;
		}
		if(interfaceRegister3 != null) {
			memoryAccess();
			interfaceRegister3 = null;
		}
		if(interfaceRegister2 != null) {
			int retCode = instructionExecute();
			if(retCode != Computer.SUCCESS_RET_CODE) {
				return retCode;
			}
			interfaceRegister2 = null;
		}
		if(interfaceRegister1 != null) {
			if(instructionDecode() == 2) {
				return Computer.ERROR_RET_CODE;
			}
			interfaceRegister1 = null;
		}
		
		instructionFetch();
		
		return Computer.SUCCESS_RET_CODE;
	}
	
	/**
	 * Stage 1. Read instructions from address in memory whose value is present in PC 
	 */
	public void instructionFetch() {
		interfaceRegister1 = new Instructions(computer.RAM[computer.pc.getBase10Value()].MEM, computer);;
	}
	
	/**
	 * Stage 2. Decode instruction & access register file
	 */
	public int instructionDecode() {
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
	    	interfaceRegister2 = interfaceRegister1;	
	    }
	    return retCode;
	}
	
	/**
	 * Stage 3. Perform ALU operations
	 */
	public int instructionExecute() {
		int retCode;
	    switch (interfaceRegister2.instruction.opc) {
	        case 0:
	            retCode = interfaceRegister2.HALT();
	            break;
	        case 1:
	        	retCode = interfaceRegister2.LDRStage3();
	        	break;
	        case 2:
	        	retCode = interfaceRegister2.STRStage3();
	        	break;
	        case 3:
	        	retCode = interfaceRegister2.LDAStage3();
	        	break;
	        case 41:
	        	retCode = interfaceRegister2.LDXStage3();
	        	break;
	        case 42:
	        	retCode = interfaceRegister2.STXStage3();
	        	break;
	        case 10:
	        	retCode = interfaceRegister2.JZStage3();
	        	break;
	        case 11:
	        	retCode = interfaceRegister2.JNEStage3();
	        	break;
	        case 12:
	        	retCode = interfaceRegister2.JCCStage3();
	        	break;
	        case 13:
	        	retCode = interfaceRegister2.JMAStage3();
	        	break;
	        case 14:
	        	retCode = interfaceRegister2.JSRStage3();
	        	break;
	        case 15:
	        	retCode = interfaceRegister2.RFS();
	        	break;
	        case 16:
	        	retCode = interfaceRegister2.SOBStage3();
	        	break;
	        case 17:
	        	retCode = interfaceRegister2.JGEStage3();
	        	break;
	        case 4:
	        	retCode = interfaceRegister2.AMRStage3();
	        	break;
	        case 5:
	        	retCode = interfaceRegister2.SMRStage3();
	        	break;
	        case 6:
	        	retCode = interfaceRegister2.AIRStage3();
	        	break;
	        case 7:
	        	retCode = interfaceRegister2.SIRStage3();
	        	break;
	        case 20:
	        	retCode = interfaceRegister2.MLTStage3();
	        	break;
	        case 21:
	        	retCode = interfaceRegister2.DVDStage3();
	        	break;
	        case 22:
	        	retCode = interfaceRegister2.TRRStage3();
	        	break;
	        case 23:
	        	retCode = interfaceRegister2.ANDStage3();
	        	break;
	        case 24:
	        	retCode = interfaceRegister2.ORRStage3();
	        	break;
	        case 25:
	        	retCode = interfaceRegister2.NOTStage3();
	        	break;
	        case 31:
	        	retCode = interfaceRegister2.SRCStage3();
	        	break;
	        case 32:
	        	retCode = interfaceRegister2.RRCStage3();
	        	break;
	        case 61:
	        	retCode = interfaceRegister2.IN();
	        	break;
	        case 62:
	        	retCode = interfaceRegister2.OUTStage3();
	        	break;
	        case 30:
	        	retCode = interfaceRegister2.TRAP();
	        	break;
	        default:
	            interfaceRegister2.computer.mfr.setErr(1, 1);
	            retCode = Computer.SUCCESS_RET_CODE;
	    }
	    
	    return retCode;
	}
	
	/**
	 * Stage 4. Memory operands are read and written from/to the memory present in the instruction
	 */
	public void memoryAccess() {
		
	}
	
	/**
	 * Stage 5. Computed/fetched value is written back to the register present in the instructions
	 */
	public void writeBack() {
		
	}
}
