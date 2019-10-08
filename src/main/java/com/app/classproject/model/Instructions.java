package com.app.classproject.model;

import java.util.Arrays;

public class Instructions {
// part 1
    	public static final int[] HLT_opc = {0, 0, 0, 0, 0, 0};
   	public static final int[] LDR_opc = {0, 0, 0, 0, 0, 1};
    	public static final int[] STR_opc = {0, 0, 0, 0, 1, 0};
   	public static final int[] LDA_opc = {0, 0, 0, 0, 1, 1};
   	public static final int[] LDX_opc = {1, 0, 1, 0, 0, 1};
   	public static final int[] STX_opc = {1, 0, 1, 0, 1, 0};
    
// part 2
        public static final int[] JZ_opc  = {0, 0, 1, 0, 1, 0};
	public static final int[] JNE_opc = {0, 0, 1, 0, 1, 1};
	public static final int[] JCC_opc = {0, 0, 1, 1, 0, 0};
	public static final int[] JMA_opc = {0, 0, 1, 1, 0, 1};
	public static final int[] JSR_opc = {0, 0, 1, 1, 1, 0};
	public static final int[] RFS_opc = {0, 0, 1, 1, 1, 1};
        public static final int[] SOB_opc = {0, 1, 0, 0, 0, 0};
	public static final int[] JGE_opc = {0, 1, 0, 0, 0, 1};
        public static final int[] AMR_opc = {0, 0, 0, 1, 0, 0};
	public static final int[] SMR_opc = {0, 0, 0, 1, 0, 1};
	public static final int[] AIR_opc = {0, 0, 0, 1, 1, 0};
	public static final int[] SIR_opc = {0, 0, 0, 1, 1, 1};
	public static final int[] MLT_opc = {0, 1, 0, 1, 0, 0};
	public static final int[] DVD_opc = {0, 1, 0, 1, 0, 1};
	public static final int[] TRR_opc = {0, 1, 0, 1, 1, 0};
        public static final int[] AND_opc = {0, 1, 0, 1, 1, 1};
	public static final int[] ORR_opc = {0, 1, 1, 0, 0, 0};
	public static final int[] NOT_opc = {0, 1, 1, 0, 0, 1};
	public static final int[] SRC_opc = {0, 1, 1, 1, 1, 1};
	public static final int[] RRC_opc = {1, 0, 0, 0, 0, 0};
	public static final int[] IN_opc  = {1, 1, 1, 1, 0, 1};
	public static final int[] OUT_opc = {1, 1, 1, 1, 1, 0};
	public static final int[] CHK_opc = {1, 1, 1, 1, 1, 1};
	public static final int	OVERFLOW	= 0;
	public static final int	UNDERFLOW	= 1;
	public static final int	DIVZERO		= 2;
	public static final int	EQUALORNOT	= 3;
    
    public Computer computer;
    public memory instruction = new memory();

    public Instructions(int[] instruction, Computer computer) {
        this.computer = computer;
        this.instruction.MEM = instruction;
        this.instruction.setup();
    }

    /**
     * Execute instruction
     */
    public int execute() {
        System.out.println(Arrays.toString(instruction.MEM));
        switch(instruction.opc) {
  //part 1  
            case 0:
                return Computer.HLT_RET_CODE;
            case 1:
                return LDR();
            case 2:
                return STR();
            case 3:
                return LDA();
            case 41:
                return LDX();
            case 42:
                return STX();
  //part 2 
            case 10:
            	return JZ();
            case 11:
            	return JNE();
            case 12:
            	return JCC();
            case 13:
            	return JMA();
            case 14:
            	return JSR();
            case 15:
            	return RFS();
            case 16:
            	return SOB();
            case 17:
            	return JGE();
            case 4:
            	return AMR();
            case 5:
            	return SMR();
            case 6:
            	return AIR();
            case 7:
            	return SIR();
            case 20:
            	return MLT();
            case 21:
            	return DVD();
            case 22:
            	return TRR();
            case 23:
            	return AND();
            case 24:
            	return ORR();
            case 25:
            	return NOT();
            case 31:
            	return SRC();
            case 32:
            	return RRC();
            case 61:
            	return IN();
            case 62:
            	return OUT();
            case 63:
            	return CHK();
            default:
                return Computer.ERROR_RET_CODE;
        }
    }

    /**
     * Calculate effective address
     */
    private int getEffectiveAdr() {
        if(instruction.iad == 0) {
            // No indirect addressing
            switch(instruction.idr) {
                case 0:
                    // No indexing
                    return instruction.address;
                case 1:
                    return computer.idx[0].getBase10Value() + instruction.address;
                case 2:
                    return computer.idx[1].getBase10Value() + instruction.address;
                case 3:
                    return computer.idx[2].getBase10Value() + instruction.address;
                default:
                    return -1;
            }
        } else {
            // Indirect addressing
            switch(instruction.idr) {
                case 0:
                    // No indexing
                    return computer.RAM[instruction.address].mem;
                case 1:
                    return computer.RAM[instruction.address + computer.idx[0].getBase10Value()].mem;
                case 2:
                    return computer.RAM[instruction.address + computer.idx[1].getBase10Value()].mem;
                case 3:
                    return computer.RAM[instruction.address + computer.idx[2].getBase10Value()].mem;
                default:
                    return -1;
            }
        }
    }

    /**
     * Load register from memory
     */
    public int LDR() {
        int EA = getEffectiveAdr();
        computer.mar.setValue(EA);
        computer.mbr.setValue(computer.RAM[EA].MEM);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);

        System.out.println("\nRAM[" + EA + "] = " + computer.RAM[EA].mem + ", gpr[" + instruction.gpr + "] = " + computer.gpr[instruction.gpr].getBase10Value());
        System.out.println("Loading RAM[" + EA + "] into gpr[" + instruction.gpr + "]");

        switch(instruction.gpr) {
            case 0:
                computer.gpr[0].setValue(computer.RAM[EA].MEM);
                break;
            case 1:
                computer.gpr[1].setValue(computer.RAM[EA].MEM);
                break;
            case 2:
                computer.gpr[2].setValue(computer.RAM[EA].MEM);
                break;
            case 3:
                computer.gpr[3].setValue(computer.RAM[EA].MEM);
                break;
            default:
                return Computer.ERROR_RET_CODE;
        }

        System.out.println("RAM[" + EA + "] = " + computer.RAM[EA].mem + ", gpr[" + instruction.gpr + "] = " + computer.gpr[instruction.gpr].getBase10Value() + "\n");
        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Store register to memory
     */
    public int STR() {
        int EA = getEffectiveAdr();
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);

        System.out.println("\nRAM[" + EA + "] = " + computer.RAM[EA].mem + ", gpr[" + instruction.gpr + "] = " + computer.gpr[instruction.gpr].getBase10Value());
        System.out.println("Storing gpr[" + instruction.gpr + "] into RAM[" + EA + "]");

        switch(instruction.gpr) {
            case 0:
                computer.mbr.setValue(computer.gpr[0].getValue());
                computer.RAM[EA].MEM = computer.gpr[0].getValue();
                computer.RAM[EA].setup();
                break;
            case 1:
                computer.mbr.setValue(computer.gpr[1].getValue());
                computer.RAM[EA].MEM = computer.gpr[1].getValue();
                computer.RAM[EA].setup();
                break;
            case 2:
                computer.mbr.setValue(computer.gpr[2].getValue());
                computer.RAM[EA].MEM = computer.gpr[2].getValue();
                computer.RAM[EA].setup();
                break;
            case 3:
                computer.mbr.setValue(computer.gpr[3].getValue());
                computer.RAM[EA].MEM = computer.gpr[3].getValue();
                computer.RAM[EA].setup();
                break;
            default:
                System.out.println("Error");
                return Computer.ERROR_RET_CODE;
        }

        System.out.println("RAM[" + EA + "] = " + computer.RAM[EA].mem + ", gpr[" + instruction.gpr + "] = " + computer.gpr[instruction.gpr].getBase10Value() + "\n");
        return Computer.SUCCESS_RET_CODE;

    }

    /**
     *  Load Register with Address
     */
    public int LDA() {
        int EA = getEffectiveAdr();
        computer.mbr.setValue(EA);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);

        switch (instruction.gpr) {
            case 0:
                computer.gpr[0].setValue(EA);
                return Computer.SUCCESS_RET_CODE;
            case 1:
                computer.gpr[1].setValue(EA);
                return Computer.SUCCESS_RET_CODE;
            case 2:
                computer.gpr[2].setValue(EA);
                return Computer.SUCCESS_RET_CODE;
            case 3:
                computer.gpr[3].setValue(EA);
                return Computer.SUCCESS_RET_CODE;
            default:
                System.out.println("Error");
                return Computer.ERROR_RET_CODE;
        }
    }

    /**
     *   Load Index Register from Memory
     */
    public int LDX() {
        int EA = getEffectiveAdr();
        computer.mar.setValue(EA);
        computer.mbr.setValue(computer.RAM[EA].MEM);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);

        switch(instruction.idr) {
            case 1:
                computer.idx[0].setValue(computer.RAM[EA].MEM);
                return Computer.SUCCESS_RET_CODE;
            case 2:
                computer.idx[1].setValue(computer.RAM[EA].MEM);
                return Computer.SUCCESS_RET_CODE;
            case 3:
                computer.idx[2].setValue(computer.RAM[EA].MEM);
                return Computer.SUCCESS_RET_CODE;
            default:
                System.out.println("Error");
                return Computer.ERROR_RET_CODE;
        }
    }

    /**
     *  Store Index Register to Memory
     */
    public int STX() {
        int EA = getEffectiveAdr();
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);

        switch (instruction.idr) {
            case 1:
                computer.mbr.setValue(computer.idx[0].getValue());
                computer.RAM[EA].MEM = computer.idx[0].getValue();
                computer.RAM[EA].setup();
                return Computer.SUCCESS_RET_CODE;
            case 2:
                computer.mbr.setValue(computer.idx[1].getValue());
                computer.RAM[EA].MEM = computer.idx[1].getValue();
                computer.RAM[EA].setup();
                return Computer.SUCCESS_RET_CODE;
            case 3:
                computer.mbr.setValue(computer.idx[2].getValue());
                computer.RAM[EA].MEM = computer.idx[2].getValue();
                computer.RAM[EA].setup();
                return Computer.SUCCESS_RET_CODE;
            default:
                System.out.println("Error");
                return Computer.ERROR_RET_CODE;
        }
    }
 /**
     * part 2, transfer instruction
    */
    
    /**
     *  Jump if Zero
     */
    
   public int JZ(){
	   int EA = getEffectiveAdr();	
	   int tempR = getValueFromRById(instruction.gpr);
	   if(tempR == 0) {
	              computer.pc.setValue(EA);
	   }else {
				  computer.pc.setValue(computer.pc.getBase10Value() + 1);
   }
	   return Computer.SUCCESS_RET_CODE;
  }
   
  

/**
    *  Jump if not Equal
    */
   
   public int JNE() {
	   int EA = getEffectiveAdr();
	   int tempR = getValueFromRById(instruction.gpr);
	   if(tempR != 0) {
           computer.pc.setValue(EA);
	   	}else {
			  computer.pc.setValue(computer.pc.getBase10Value() + 1);
	   	}
	   	return Computer.SUCCESS_RET_CODE;
   }
  
   /**
    *  Jump If Condition Code cc replaces r for this instruction
    */
   
   public int JCC() {
	   int EA = getEffectiveAdr();
	   switch(instruction.ccr) {
	   case 0:
		   if(computer.ccr[0].getValue()[instruction.ccr] == 1){
			   computer.pc.setValue(EA);
		   }else {
		   computer.pc.setValue(computer.pc.getBase10Value() + 1);
	   	}
		   return Computer.SUCCESS_RET_CODE;
	   case 1:
		   if(computer.ccr[1].getValue()[instruction.ccr] == 1){
			   computer.pc.setValue(EA);
		   }else {
		   computer.pc.setValue(computer.pc.getBase10Value() + 1);
	   	}
		   return Computer.SUCCESS_RET_CODE;
	   case 2:
		   if(computer.ccr[2].getValue()[instruction.ccr] == 1){
			   computer.pc.setValue(EA);
		   }else {
		   computer.pc.setValue(computer.pc.getBase10Value() + 1);
	   	}
		   return Computer.SUCCESS_RET_CODE;
	   case 3:
		   if(computer.ccr[3].getValue()[instruction.ccr] == 1){
			   computer.pc.setValue(EA);
		   }else {
		   computer.pc.setValue(computer.pc.getBase10Value() + 1);
	   		}
		   return Computer.SUCCESS_RET_CODE;
		   default:
			   System.out.println("Error");
               return Computer.ERROR_RET_CODE;
	   }
	  
  }
   
   /** 
    *  Unconditional Jump To Address
    *  r is ignored in this instruction
    */
   
   public int JMA() {
	   int EA = getEffectiveAdr();
	   computer.pc.setValue(EA);
	   
	return Computer.SUCCESS_RET_CODE;
   }
   
   /** 
    *  Jump and Save Return Address
    *  R0 should contain pointer to arguments
    *  Argument list should end with –1 (all 1s) value
    */
   
   public int JSR() {
	   int EA = getEffectiveAdr();
	   computer.gpr[3].setValue(computer.pc.getBase10Value() + 1);
	   computer.pc.setValue(EA);
	   
	  return Computer.SUCCESS_RET_CODE;
   }
   
   /** 
    *  Return From Subroutine w/ return code 
    *  as Immed portion (optional) stored in the instruction’s address field. 
    *  IX, I fields are ignored.
    */
   
   public int RFS(int immed) {
	   computer.gpr[0].setValue(immed);
	   computer.pc.setValue(computer.gpr[3].getValue());
	   return Computer.SUCCESS_RET_CODE;
   }
   
   /** 
    *  Subtract One and Branch
    */
   
   public int SOB() {
	   int EA = getEffectiveAdr();
	   switch(instruction.gpr) {
	   case 0:
		   computer.gpr[0].setValue(computer.gpr[0].getBase10Value()-1);
		   if(computer.gpr[0].getBase10Value()>0)
			   computer.pc.setValue(EA);
		   break;
	   case 1:
		   computer.gpr[1].setValue(computer.gpr[1].getBase10Value()-1);
		   if(computer.gpr[1].getBase10Value()>0)
			   computer.pc.setValue(EA);
		   break;
	   case 2:
		   computer.gpr[2].setValue(computer.gpr[2].getBase10Value()-1);
		   if(computer.gpr[2].getBase10Value()>0)
			   computer.pc.setValue(EA);
		   break;
	   case 3:
		   computer.gpr[3].setValue(computer.gpr[3].getBase10Value()-1);
		   if(computer.gpr[3].getBase10Value()>0)
			   computer.pc.setValue(EA);
		  
	   default:
			   System.out.println("Error");
			   return Computer.ERROR_RET_CODE;
	   }
	   return Computer.SUCCESS_RET_CODE;
   }
   
   /** 
    *  Jump Greater Than or Equal To
    */
   
   public int JGE() {
	   int EA = getEffectiveAdr();
	   switch(instruction.gpr) {
	   case 0:
		   if(computer.gpr[0].getBase10Value() >= 0)
			   computer.pc.setValue(EA);
		   break;
	   case 1:
		   if(computer.gpr[1].getBase10Value() >= 0)
			   computer.pc.setValue(EA);
		   break;	
	   case 2:
		   if(computer.gpr[2].getBase10Value() >= 0)
			   computer.pc.setValue(EA);
		   break;
	   case 3:
		   if(computer.gpr[3].getBase10Value() >= 0)
			   computer.pc.setValue(EA);
		   break;
		   default:
			   System.out.println("Error");
			   return Computer.ERROR_RET_CODE;
	   }
	   return Computer.SUCCESS_RET_CODE;
   }
   
   /** 
    *  Add Memory To Register
    */
   public int AMR() {
	   int EA = getEffectiveAdr();
	   computer.mar.setValue(EA);
       	   computer.mbr.setValue(computer.RAM[EA].MEM);
       	   computer.pc.setValue(computer.pc.getBase10Value() + 1);
       	   computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
	   switch(instruction.gpr) {
	   case 0:
		   computer.gpr[0].setValue(computer.gpr[0].getBase10Value() + computer.RAM[EA].mem);
		   break;              
	   case 1:
		   computer.gpr[1].setValue(computer.gpr[1].getBase10Value() + computer.RAM[EA].mem);
		   break;
	   case 2:
		   computer.gpr[2].setValue(computer.gpr[2].getBase10Value() + computer.RAM[EA].mem);
	   break;
	   case 3:
		   computer.gpr[3].setValue(computer.gpr[3].getBase10Value() + computer.RAM[EA].mem);
	   break;
	   default:
		   System.out.println("Error");
		   return Computer.ERROR_RET_CODE;
	   }
	   return Computer.SUCCESS_RET_CODE;
   }
   
   /** 
    *  Subtract Memory From Register
    */
   public int SMR() {
		int EA = getEffectiveAdr();
		computer.mar.setValue(EA);
       		computer.mbr.setValue(computer.RAM[EA].MEM);
       		computer.pc.setValue(computer.pc.getBase10Value() + 1);
       		computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
        switch(instruction.gpr) {
    		case 0:	
    		    	computer.gpr[0].setValue(computer.gpr[0].getBase10Value() - computer.RAM[EA].mem);
    			break;
    		case 1:
   		    	computer.gpr[1].setValue(computer.gpr[1].getBase10Value() - computer.RAM[EA].mem);
    			break;
    		case 2:	
   		    	computer.gpr[2].setValue(computer.gpr[2].getBase10Value() - computer.RAM[EA].mem);
    			break;
    		case 3:
   		    	computer.gpr[3].setValue(computer.gpr[3].getBase10Value() - computer.RAM[EA].mem);
    			break;
    	   default:
    		   System.out.println("Error");
    		   return Computer.ERROR_RET_CODE;
       }
       return Computer.SUCCESS_RET_CODE;
   }
   
   /** 
    *  Add  Immediate to Register
    *  IX and I are ignored in this instruction
    */
   	public int AIR(int immed) {
   		int EA = getEffectiveAdr();
		computer.mar.setValue(EA);
     		computer.mbr.setValue(computer.RAM[EA].MEM);
     		computer.pc.setValue(computer.pc.getBase10Value() + 1);
     		computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
   
    	 switch(instruction.gpr) {
   		case 0:
   			computer.gpr[0].setValue(computer.gpr[0].getBase10Value() + immed);
   			break;
   		case 1:
   			computer.gpr[1].setValue(computer.gpr[1].getBase10Value() + immed);
   			break;
   		case 2:
   			computer.gpr[2].setValue(computer.gpr[2].getBase10Value() + immed);
   			break;
   		case 3:
   			computer.gpr[3].setValue(computer.gpr[3].getBase10Value() + immed);
    			break;
    			default:
    				System.out.println("Error");
      		    return 	Computer.ERROR_RET_CODE;
    		}
    	 return Computer.SUCCESS_RET_CODE;
     }
   	  
   
   
   /** 
    *  Subtract  Immediate  from Register
    */
   
   	public int SIR(int immed) {
   		int EA = getEffectiveAdr();
   		computer.mar.setValue(EA);
        	computer.mbr.setValue(computer.RAM[EA].MEM);
        	computer.pc.setValue(computer.pc.getBase10Value() + 1);
        	computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
   		switch(instruction.gpr) {
   		case 0:
   			computer.gpr[0].setValue(computer.gpr[0].getBase10Value() - immed);
   			break;
   		case 1:
   			computer.gpr[1].setValue(computer.gpr[1].getBase10Value() - immed);
   			break;
   		case 2:
   			computer.gpr[2].setValue(computer.gpr[2].getBase10Value() - immed);
   			break;
   		case 3:
   			computer.gpr[3].setValue(computer.gpr[3].getBase10Value() - immed);
   			break;
   			default:
   				System.out.println("Error");
     		    return 	Computer.ERROR_RET_CODE;
   		}
   		return Computer.SUCCESS_RET_CODE;
    }
   /** 
    *  Multiply Register by Register
    */

      public int MLT() {
	   if (instruction.rx != 0 && instruction.rx != 2 || instruction.ry != 0 && instruction.ry != 2) {
			System.out.println("Error !");
			return Computer.ERROR_RET_CODE;
		}
	   if (instruction.rx == 0 || instruction.rx == 2 && instruction.ry == 0 || instruction.ry == 2) {
		   int data1 = this.getValueFromRById(instruction.rx);
		   int data2 = this.getValueFromRById(instruction.ry);
		   int temp  = data1 * data2;
		   
		   if(temp < Integer.MAX_VALUE && temp > Integer.MIN_VALUE ) {
			   int next = 0;
			   if(instruction.rx == 0) {
				   next = 1 ;
			   }else {
				   next = 3;
			   }
			   
			  String temp1 = this.InttoBinary32(temp);
			   System.out.println(temp1);
			  this.setValueToRById(instruction.rx, Integer.parseInt(temp1.substring(0, 16), 2));
			  this.setValueToRById(next, Integer.parseInt(temp1.substring(16), 2));
		   }else {
			   System.out.println("OVERFLOW");
			   computer.ccr[3].setValue(OVERFLOW);
		   }
	   } 
	   return Computer.SUCCESS_RET_CODE;
   }
	
   /** 
    *  Divide Register by Register
    */
   
	public int DVD() {
	   if (instruction.rx != 0 && instruction.rx != 2 || instruction.ry != 0 && instruction.ry != 2) {
			System.out.println("Error !");
			return Computer.ERROR_RET_CODE;
		}
	   
	   if (instruction.rx == 0 || instruction.rx == 2 && instruction.ry == 0 || instruction.ry == 2) {
		   int data1 = this.getValueFromRById(instruction.rx);
		   int data2 = this.getValueFromRById(instruction.ry);
		   if(data2 != 0){
			   int temp1 = data1 / data2 ;
			   int temp2 = data1 % data2 ;
			   int next = 0;
			   
			   if(instruction.rx == 0) {
				   next = 1;
			   }else {
				   next = 3;
			   }
			   String quotient = this.InttoBinary16(temp1);
			   String remainder = this.InttoBinary16(temp2);
			   System.out.println(quotient + "   " + remainder);
			   this.setValueToRById(instruction.rx, Integer.parseInt(quotient, 2));
			   this.setValueToRById(next, Integer.parseInt(remainder, 2));
		   }else {
			   System.out.println("DIVZERO");
			   computer.ccr[3].setValue(DIVZERO);
		   }
	   }
	   return Computer.SUCCESS_RET_CODE;
   }
   /** 
    *  Test the Equality of Register and Register
    */
   public int TRR() {
	   int temp1 = getValueFromRById(instruction.rx);
	   int temp2 = getValueFromRById(instruction.ry);
	   if(temp1 == temp2) {
		   computer.ccr[4].setValue(4);
	   }else {
		   computer.ccr[4].setValue(0);
	   }
	   return Computer.SUCCESS_RET_CODE;
   }
	   
   /** 
    *  Logical And of Register and Register
    */
   	public int AND() {
   		int temp1 = getValueFromRById(instruction.rx);
   		int temp2 = getValueFromRById(instruction.ry);
   		
   		setValueToRById(instruction.rx, temp1 & temp2);
   		return Computer.SUCCESS_RET_CODE;
   	}
   
   
    /** 
     *  Logical Or of Register and Register
     */
   	public int ORR() {
   		int temp1 = getValueFromRById(instruction.rx);
   		int temp2 = getValueFromRById(instruction.ry);
   		
   		setValueToRById(instruction.rx, temp1 | temp2);
   		return Computer.SUCCESS_RET_CODE;
   	}
   	
   	
    /** 
     *  Logical Not of Register To Register
     */
   	public int Not() {
   		int temp1 = getValueFromRById(instruction.rx);
   		setValueToRById(instruction.rx, ~temp1);
   		return Computer.SUCCESS_RET_CODE;
   	}
   	
   	
    /** 
     *  Shift Register by Count
     */
   	public int SRC() {
   		int tempR = getValueFromRById(instruction.gpr);
   		if(instruction.lr == 1){
   			this.setValueToRById(instruction.gpr, tempR << instruction.count);
   		}
   		if(instruction.lr == 0) {
   			this.setValueToRById(instruction.gpr, tempR >> instruction.count);
   		}
   		return Computer.SUCCESS_RET_CODE;
   	}
   	
  
   	/** 
     *  Rotate Register by Count
     */
   
   	/** 
     *  Input Character To Register from Device
     */
   	
   	/** 
     *  Output Character to Device from Register
     */
   	

 // get value by ID from general register R0-R3
    public int getValueFromRById(int id) {
    	int temp = 0;
    	switch (id) {
    		case 0:
    			temp = computer.gpr[0].getBase10Value();
    			break;
    		case 1:
    			temp = computer.gpr[1].getBase10Value();
    			break;
    		case 2:
    			temp = computer.gpr[2].getBase10Value();
    			break;
    		case 3:
    			temp = computer.gpr[3].getBase10Value();
    			break;
    		default:
    			System.out.println("Error");
    			break;
    	}
    	return temp;
    }
    
 // set value by ID to general register R0-R3
    public void setValueToRById(int id, int value) {
    	switch(id) {
    	case 0:
    		computer.gpr[0].setValue(value);
    	case 1:
    		computer.gpr[1].setValue(value);
    	case 2:
    		computer.gpr[2].setValue(value);
    	case 3:
    		computer.gpr[3].setValue(value);
    		default:
    			System.out.println("Error");
		break;
    	}
    }
	
 
     private String InttoBinary32(int num) {
    	
    	String temp = Integer.toBinaryString(num);
    	
    	for (int i = temp.length(); i < 32; i++) {
			temp = "0" + temp;
		}
		return temp;
    }
    
    private String InttoBinary16(int num) {
    	
    	String temp = Integer.toBinaryString(num);

		for (int i = temp.length(); i < 16; i++) {
			temp = "0" + temp;
		}
		return temp;
    }
    
    public void printInfo() {

    }
}
