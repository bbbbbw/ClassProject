package com.app.classproject.model;

/**
 * RISC 5-stage instruction pipeline
 */
public class Pipeline {
	Computer computer;
	
	/**
	 * Holds immediate output between 2 stages
	 */
	public class InterfaceRegister {
		public Instructions instruction;
		public int EA, tempR, temp1, temp2;
		public int tempArr[], temp1Arr[], temp2Arr[], memVal[];
		
		public InterfaceRegister(Instructions instruction) {
			this.instruction = instruction;
		}
	}
	
	
	
	// Interface registers to hold intermediate output between 2 stages
	InterfaceRegister interfaceRegister1 = null;
	InterfaceRegister interfaceRegister2 = null;
	InterfaceRegister interfaceRegister3 = null;
	InterfaceRegister interfaceRegister4 = null;
	
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
		interfaceRegister1 = new InterfaceRegister(new Instructions(computer.RAM[computer.pc.getBase10Value()].MEM, computer));
	}
	
	/**
	 * Stage 2. Decode instruction & access register file
	 */
	public int instructionDecode() {
		int retCode = 1;
		
	    switch (interfaceRegister1.instruction.instruction.opc) {
	    case 1:
	        retCode = interfaceRegister1.instruction.LDRStage2();
	        break;
	    case 2:
	    	retCode = interfaceRegister1.instruction.STRStage2();
	    	break;
	    case 3:
	    	retCode = interfaceRegister1.instruction.LDAStage2();
	    	break;
	    case 41:
	    	retCode = interfaceRegister1.instruction.LDXStage2();
	    	break;
	    case 42:
	    	retCode = interfaceRegister1.instruction.STXStage2();
	    	break;
	    case 10:
	    	retCode = interfaceRegister1.instruction.JZStage2();
	    	break;
	    case 11:
	    	retCode = interfaceRegister1.instruction.JNEStage2();
	    	break;
	    case 12:
	    	retCode = interfaceRegister1.instruction.JCCStage2();
	    	break;
	    case 13:
	    	retCode = interfaceRegister1.instruction.JMAStage2();
	    	break;
	    case 14:
	    	retCode = interfaceRegister1.instruction.JSRStage2();
	    	break;
	    case 16:
	    	retCode = interfaceRegister1.instruction.SOBStage2();
	    	break;
	    case 17:
	    	retCode = interfaceRegister1.instruction.JGEStage2();
	    	break;
	    case 4:
	    	retCode = interfaceRegister1.instruction.AMRStage2();
	    	break;
	    case 5:
	    	retCode = interfaceRegister1.instruction.SMRStage2();
	    	break;
	    case 6:
	    	retCode = interfaceRegister1.instruction.AIRStage2();
	    	break;
	    case 7:
	    	retCode = interfaceRegister1.instruction.SIRStage2();
	    	break;
	    case 20:
	    	retCode = interfaceRegister1.instruction.MLTStage2();
	    	break;
	    case 21:
	    	retCode = interfaceRegister1.instruction.DVDStage2();
	    	break;
	    case 22:
	    	retCode = interfaceRegister1.instruction.TRRStage2();
	    	break;
	    case 23:
	    	retCode = interfaceRegister1.instruction.ANDStage2();
	    	break;
	    case 24:
	    	retCode = interfaceRegister1.instruction.ORRStage2();
	    	break;
	    case 25:
	    	retCode = interfaceRegister1.instruction.NOTStage2();
	    	break;
	    case 31:
	    	retCode = interfaceRegister1.instruction.SRCStage2();
	    	break;
	    case 32:
	    	retCode = interfaceRegister1.instruction.RRCStage2();
	    	break;
	    case 62:
	    	retCode = interfaceRegister1.instruction.OUTStage2();
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
	    switch (interfaceRegister2.instruction.instruction.opc) {
	        case 0:
	            retCode = interfaceRegister2.instruction.HALT();
	            break;
	        case 1:
	        	retCode = interfaceRegister2.instruction.LDRStage3();
	        	break;
	        case 2:
	        	retCode = interfaceRegister2.instruction.STRStage3();
	        	break;
	        case 3:
	        	retCode = interfaceRegister2.instruction.LDAStage3();
	        	break;
	        case 41:
	        	retCode = interfaceRegister2.instruction.LDXStage3();
	        	break;
	        case 42:
	        	retCode = interfaceRegister2.instruction.STXStage3();
	        	break;
	        case 10:
	        	retCode = interfaceRegister2.instruction.JZStage3();
	        	break;
	        case 11:
	        	retCode = interfaceRegister2.instruction.JNEStage3();
	        	break;
	        case 12:
	        	retCode = interfaceRegister2.instruction.JCCStage3();
	        	break;
	        case 13:
	        	retCode = interfaceRegister2.instruction.JMAStage3();
	        	break;
	        case 14:
	        	retCode = interfaceRegister2.instruction.JSRStage3();
	        	break;
	        case 15:
	        	retCode = interfaceRegister2.instruction.RFS();
	        	break;
	        case 16:
	        	retCode = interfaceRegister2.instruction.SOBStage3();
	        	break;
	        case 17:
	        	retCode = interfaceRegister2.instruction.JGEStage3();
	        	break;
	        case 4:
	        	retCode = interfaceRegister2.instruction.AMRStage3();
	        	break;
	        case 5:
	        	retCode = interfaceRegister2.instruction.SMRStage3();
	        	break;
	        case 6:
	        	retCode = interfaceRegister2.instruction.AIRStage3();
	        	break;
	        case 7:
	        	retCode = interfaceRegister2.instruction.SIRStage3();
	        	break;
	        case 20:
	        	retCode = interfaceRegister2.instruction.MLTStage3();
	        	break;
	        case 21:
	        	retCode = interfaceRegister2.instruction.DVDStage3();
	        	break;
	        case 22:
	        	retCode = interfaceRegister2.instruction.TRRStage3();
	        	break;
	        case 23:
	        	retCode = interfaceRegister2.instruction.ANDStage3();
	        	break;
	        case 24:
	        	retCode = interfaceRegister2.instruction.ORRStage3();
	        	break;
	        case 25:
	        	retCode = interfaceRegister2.instruction.NOTStage3();
	        	break;
	        case 31:
	        	retCode = interfaceRegister2.instruction.SRCStage3();
	        	break;
	        case 32:
	        	retCode = interfaceRegister2.instruction.RRCStage3();
	        	break;
	        case 61:
	        	retCode = interfaceRegister2.instruction.IN();
	        	break;
	        case 62:
	        	retCode = interfaceRegister2.instruction.OUTStage3();
	        	break;
	        case 30:
	        	retCode = interfaceRegister2.instruction.TRAP();
	        	break;
	        default:
	            interfaceRegister2.instruction.computer.mfr.setErr(1, 1);
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
