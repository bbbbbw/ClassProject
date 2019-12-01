package com.app.classproject.model;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Instructions {
    //binary
    public static final int[] HLT_opc = {0, 0, 0, 0, 0, 0};
    public static final int[] LDR_opc = {0, 0, 0, 0, 0, 1};
    public static final int[] STR_opc = {0, 0, 0, 0, 1, 0};
    public static final int[] LDA_opc = {0, 0, 0, 0, 1, 1};
    public static final int[] LDX_opc = {1, 0, 1, 0, 0, 1};
    public static final int[] STX_opc = {1, 0, 1, 0, 1, 0};
    // part 2
    public static final int[] JZ_opc = {0, 0, 1, 0, 1, 0};
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
    public static final int[] TRAP_opc = {0, 1, 1, 1, 1, 0};
    public static final int[] SRC_opc = {0, 1, 1, 1, 1, 1};
    public static final int[] RRC_opc = {1, 0, 0, 0, 0, 0};
    public static final int[] IN_opc = {1, 1, 1, 1, 0, 1};
    public static final int[] OUT_opc = {1, 1, 1, 1, 1, 0};
    public static final int[] CHK_opc = {1, 1, 1, 1, 1, 1};
    //part 4 
    public static final int[] FADD_opc = {1, 0, 0, 0, 0, 1};
    public static final int[] FSUB_opc = {1, 0, 0, 0, 1, 0};
    public static final int[] VADD_opc = {1, 0, 0, 0, 1, 1};
    public static final int[] VSUB_opc = {1, 0, 0, 1, 0, 0};
    public static final int[] CNVRT_opc = {1, 0, 0, 1, 0, 1};
    public static final int[] LDFR_opc = {1, 1, 0, 0, 1, 0};
    public static final int[] STFR_opc = {1, 1, 0, 0, 1, 1};

    //decmial
    // part 1
    public static final int HLTopc = 0;
    public static final int LDRopc = 1;
    public static final int STRopc = 2;
    public static final int LDAopc = 3;
    public static final int LDXopc = 41;
    public static final int STXopc = 42;

    // part 2
    public static final int JZopc = 10;
    public static final int JNEopc = 11;
    public static final int JCCopc = 12;
    public static final int JMAopc = 13;
    public static final int JSRopc = 14;
    public static final int RFSopc = 15;
    public static final int SOBopc = 16;
    public static final int JGEopc = 17;
    public static final int AMRopc = 4;
    public static final int SMRopc = 5;
    public static final int AIRopc = 6;
    public static final int SIRopc = 7;
    public static final int MLTopc = 20;
    public static final int DVDopc = 21;
    public static final int TRRopc = 22;
    public static final int ANDopc = 23;
    public static final int ORRopc = 24;
    public static final int NOTopc = 25;
    public static final int SRCopc = 31;
    public static final int RRCopc = 32;
    public static final int INopc = 61;
    public static final int OUTopc = 62;
    public static final int CHKopc = 63;

    // Part 3
    public static final int TRAPopc = 30;

    public static final int KEYBOARD = 0;
    public static final int PRINTER = 1;
    public static final int CARD_READER = 2;
    public static final int CHAR_PRINTER = 30;
    public static final int OUTER_FILE = 31;
    //part 4
    public static final int FAADopc = 33;
    public static final int FSUBopc = 34;
    public static final int VAADopc = 35;
    public static final int VSUBopc = 36;
    public static final int CNVRTopc = 37;
    public static final int LDFRopc = 50;
    public static final int STFRopc = 51;
    //general

    public Computer computer;
    public memory instruction = new memory();
    public int EA, tempR, temp1, temp2;
    public int tempArr[];
    public int temp1Arr[];
    public int temp2Arr[];
    public int[] memVal;

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
        switch (instruction.opc) {
            case 0:
                return HALT();
            case 1:
            	LDRStage2();
                return LDRStage3();
            case 2:
            	STRStage2();
                return STRStage3();
            case 3:
            	LDAStage2();
                return LDAStage3();
            case 41:
            	LDXStage2();
                return LDXStage3();
            case 42:
            	STXStage2();
                return STXStage3();
            case 10:
            	JZStage2();
                return JZStage3();
            case 11:
            	JNEStage2();
                return JNEStage3();
            case 12:
            	JCCStage2();
                return JCCStage3();
            case 13:
            	JMAStage2();
                return JMAStage3();
            case 14:
            	JSRStage2();
                return JSRStage3();
            case 15:
                return RFS();
            case 16:
            	SOBStage2();
                return SOBStage3();
            case 17:
            	JGEStage2();
                return JGEStage3();
            case 4:
            	AMRStage2();
                return AMRStage3();
            case 5:
            	SMRStage2();
                return SMRStage3();
            case 6:
            	AIRStage2();
                return AIRStage3();
            case 7:
            	SIRStage2();
                return SIRStage3();
            case 20:
            	MLTStage2();
                return MLTStage3();
            case 21:
            	DVDStage2();
                return DVDStage3();
            case 22:
            	TRRStage2();
                return TRRStage3();
            case 23:
            	ANDStage2();
                return ANDStage3();
            case 24:
            	ORRStage2();
                return ORRStage3();
            case 25:
            	NOTStage2();
                return NOTStage3();
            case 31:
            	SRCStage2();
                return SRCStage3();
            case 32:
            	RRCStage2();
                return RRCStage3();
            case 33:
                return FADD();
            case 34:
                return FSUB();
            case 35:
                return VADD();
            case 36:
                return VSUB();
            case 37:
                return CNVRT();
            case 50:
                return LDFR(); 
            case 51:
                return STFR();
            case 61:
                return IN();
            case 62:
            	OUTStage2();
                return OUTStage3();
            case 30:
                return TRAP();
            default:
                computer.mfr.setErr(1, 1);
                return Computer.SUCCESS_RET_CODE;
        }
    }

    /**
     * Calculate effective address
     */
    private int getEffectiveAdr() {
        if (instruction.iad == 0) {
            // No indirect addressing
            switch (instruction.idr) {
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
            switch (instruction.idr) {
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
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int LDRStage2() {
    	EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        // Check cache
        memVal = checkCache(EA);

        computer.mar.setValue(EA);
        computer.mbr.setValue(memVal);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);

        System.out.println("\nRAM[" + EA + "] = " + computer.RAM[EA].mem + ", gpr[" + instruction.gpr + "] = " + computer.gpr[instruction.gpr].getBase10Value());
        System.out.println("Loading RAM[" + EA + "] into gpr[" + instruction.gpr + "]\n");
        
        return 1;
    }

    /**
     * Load register from memory
     */
    public int LDRStage3() {
        switch (instruction.gpr) {
            case 0:
                computer.gpr[0].setValue(memVal);
                break;
            case 1:
                computer.gpr[1].setValue(memVal);
                break;
            case 2:
                computer.gpr[2].setValue(memVal);
                break;
            case 3:
                computer.gpr[3].setValue(memVal);
                break;
            default:
                return Computer.ERROR_RET_CODE;
        }

        System.out.println("RAM[" + EA + "] = " + Arrays.toString(memVal) + ", gpr[" + instruction.gpr + "] = " + computer.gpr[instruction.gpr].getBase10Value() + "\n");
        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int STRStage2() {
        EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        if (this.checkReserved(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);

        System.out.println("\nRAM[" + EA + "] = " + computer.RAM[EA].mem + ", gpr[" + instruction.gpr + "] = " + computer.gpr[instruction.gpr].getBase10Value());
        System.out.println("Storing gpr[" + instruction.gpr + "] into RAM[" + EA + "]");
        
        return 1;
    }

    /**
     * Store register to memory
     */
    public int STRStage3() {
        switch (instruction.gpr) {
            case 0:
                computer.mbr.setValue(computer.gpr[0].getValue());
                System.arraycopy(computer.gpr[0].getValue(), 0, computer.RAM[EA].MEM, 0, 16);
                // computer.RAM[EA].MEM = computer.gpr[0].getValue();
                computer.cache.addToCache(EA, computer.gpr[0].getValue());
                computer.RAM[EA].setup();
                break;
            case 1:
                computer.mbr.setValue(computer.gpr[1].getValue());
                System.arraycopy(computer.gpr[1].getValue(), 0, computer.RAM[EA].MEM, 0, 16);
                // computer.RAM[EA].MEM = computer.gpr[1].getValue();
                computer.cache.addToCache(EA, computer.gpr[1].getValue());
                computer.RAM[EA].setup();
                break;
            case 2:
                computer.mbr.setValue(computer.gpr[2].getValue());
                System.arraycopy(computer.gpr[2].getValue(), 0, computer.RAM[EA].MEM, 0, 16);
                // computer.RAM[EA].MEM = computer.gpr[2].getValue();
                computer.cache.addToCache(EA, computer.gpr[2].getValue());
                computer.RAM[EA].setup();
                break;
            case 3:
                computer.mbr.setValue(computer.gpr[3].getValue());
                System.arraycopy(computer.gpr[3].getValue(), 0, computer.RAM[EA].MEM, 0, 16);
                // computer.RAM[EA].MEM = computer.gpr[3].getValue();
                computer.cache.addToCache(EA, computer.gpr[3].getValue());
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
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int LDAStage2() {
        EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }

        computer.mbr.setValue(EA);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
        
        return 1;
    }

    /**
     * Load Register with Address
     */
    public int LDAStage3() {
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
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int LDXStage2() {
        EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        // Check cache
        memVal = checkCache(EA);

        computer.mar.setValue(EA);
        computer.mbr.setValue(memVal);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
        
        return 1;
    }

    /**
     * Load Index Register from Memory
     */
    public int LDXStage3() {
        switch (instruction.idr) {
            case 1:
                computer.idx[0].setValue(memVal);
                return Computer.SUCCESS_RET_CODE;
            case 2:
                computer.idx[1].setValue(memVal);
                return Computer.SUCCESS_RET_CODE;
            case 3:
                computer.idx[2].setValue(memVal);
                return Computer.SUCCESS_RET_CODE;
            default:
                System.out.println("Error");
                return Computer.ERROR_RET_CODE;
        }
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int STXStage2() {
    	EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        if (this.checkReserved(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        computer.mar.setValue(EA);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
        
        return 1;
    }

    /**
     * Store Index Register to Memory
     */
    public int STXStage3() {
        switch (instruction.idr) {
            case 1:
                computer.mbr.setValue(computer.idx[0].getValue());
                computer.cache.addToCache(EA, computer.idx[0].getValue());
                // System.arraycopy(computer.idx[1].getValue(), 0, computer.RAM[EA].MEM, 0, 16);
                // computer.RAM[EA].MEM = computer.idx[0].getValue();
                // computer.RAM[EA].setup();
                return Computer.SUCCESS_RET_CODE;
            case 2:
                computer.mbr.setValue(computer.idx[1].getValue());
                computer.cache.addToCache(EA, computer.idx[1].getValue());
                // System.arraycopy(computer.idx[1].getValue(), 0, computer.RAM[EA].MEM, 0, 16);
                // computer.RAM[EA].MEM = computer.idx[1].getValue();
                // computer.RAM[EA].setup();
                return Computer.SUCCESS_RET_CODE;
            case 3:
                computer.mbr.setValue(computer.idx[2].getValue());
                computer.cache.addToCache(EA, computer.idx[2].getValue());
                // System.arraycopy(computer.idx[1].getValue(), 0, computer.RAM[EA].MEM, 0, 16);
                // computer.RAM[EA].MEM = computer.idx[2].getValue();
                // computer.RAM[EA].setup();
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
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int JZStage2() {
    	EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        if (this.checkReserved(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        tempR = getValueFromRById(instruction.gpr);
        
        return 1;
    }
    
    /**
     * Jump if Zero
     */
    public int JZStage3() {
        if (tempR == 0) {
            computer.pc.setValue(EA);
        } else {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
        }
        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int JNEStage2() {
    	EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        if (this.checkReserved(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        tempR = getValueFromRById(instruction.gpr);
        
        return 1;
    }
    
    /**
     * Jump if not Equal
     */
    public int JNEStage3() {
        if (tempR != 0) {
            computer.pc.setValue(EA);
        } else {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
        }
        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int JCCStage2() {
    	EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        if (this.checkReserved(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        
        return 1;
    }

    /**
     * Jump If Condition Code cc replaces r for this instruction
     */
    public int JCCStage3() {
        switch (this.instruction.gpr) {
            case 0:
                if (computer.ccr[0].getBase10Value() == 1) {
                    computer.pc.setValue(EA);
                } else {
                    computer.pc.setValue(computer.pc.getBase10Value() + 1);
                }
                return Computer.SUCCESS_RET_CODE;
            case 1:
                if (computer.ccr[1].getBase10Value() == 1) {
                    computer.pc.setValue(EA);
                } else {
                    computer.pc.setValue(computer.pc.getBase10Value() + 1);
                }
                return Computer.SUCCESS_RET_CODE;
            case 2:
                if (computer.ccr[2].getBase10Value() == 1) {
                    computer.pc.setValue(EA);
                } else {
                    computer.pc.setValue(computer.pc.getBase10Value() + 1);
                }
                return Computer.SUCCESS_RET_CODE;
            case 3:
                if (computer.ccr[3].getBase10Value() == 1) {
                    computer.pc.setValue(EA);
                } else {
                    computer.pc.setValue(computer.pc.getBase10Value() + 1);
                }
                return Computer.SUCCESS_RET_CODE;
            default:
                System.out.println("Error");
                return Computer.ERROR_RET_CODE;
        }

    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int JMAStage2() {
    	EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        if (this.checkReserved(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        
        return 1;
    }

    /**
     * Unconditional Jump To Address
     * r is ignored in this instruction
     */
    public int JMAStage3() {
        computer.pc.setValue(EA);

        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int JSRStage2() {
    	EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        if (this.checkReserved(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        
        return 1;
    }

    /**
     * Jump and Save Return Address
     * R0 should contain pointer to arguments
     * Argument list should end with –1 (all 1s) value
     */
    public int JSRStage3() {
        computer.gpr[3].setValue(computer.pc.getBase10Value() + 1);
        computer.pc.setValue(EA);

        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Return From Subroutine w/ return code
     * as Immed portion (optional) stored in the instruction’s address field.
     * IX, I fields are ignored.
     */
    public int RFS() {
        computer.gpr[0].setValue(instruction.address);
        computer.pc.setValue(computer.gpr[3].getValue());
        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int SOBStage2() {
    	EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        if (this.checkReserved(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        
        return 1;
    }

    /**
     * Subtract One and Branch
     */
    public int SOBStage3() {
        switch (instruction.gpr) {
            case 0:
                computer.gpr[0].setValue(computer.gpr[0].getBase10Value() - 1);
                if (computer.gpr[0].getBase10Value() > 0) {
                    computer.pc.setValue(EA);
                } else {
                    computer.pc.setValue(computer.pc.getBase10Value() + 1);
                }
                break;
            case 1:
                computer.gpr[1].setValue(computer.gpr[1].getBase10Value() - 1);
                if (computer.gpr[1].getBase10Value() > 0) {
                    computer.pc.setValue(EA);
                } else {
                    computer.pc.setValue(computer.pc.getBase10Value() + 1);
                }
                break;
            case 2:
                computer.gpr[2].setValue(computer.gpr[2].getBase10Value() - 1);
                if (computer.gpr[2].getBase10Value() > 0) {
                    computer.pc.setValue(EA);
                } else {
                    computer.pc.setValue(computer.pc.getBase10Value() + 1);
                }
                break;
            case 3:
                computer.gpr[3].setValue(computer.gpr[3].getBase10Value() - 1);
                if (computer.gpr[3].getBase10Value() > 0) {
                    computer.pc.setValue(EA);
                } else {
                    computer.pc.setValue(computer.pc.getBase10Value() + 1);
                }
            default:
                System.out.println("Error");
                return Computer.ERROR_RET_CODE;
        }
        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int JGEStage2() {
    	EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        if (this.checkReserved(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        
        return 1;
    }

    /**
     * Jump Greater Than or Equal To
     */
    public int JGEStage3() {
        switch (instruction.gpr) {
            case 0:
                if (computer.gpr[0].getBase10Value() >= 0) {
                    computer.pc.setValue(EA);
                } else {
                    computer.pc.setValue(computer.pc.getBase10Value() + 1);
                }
                break;
            case 1:
                if (computer.gpr[1].getBase10Value() >= 0) {
                    computer.pc.setValue(EA);
                } else {
                    computer.pc.setValue(computer.pc.getBase10Value() + 1);
                }
                break;
            case 2:
                if (computer.gpr[2].getBase10Value() >= 0) {
                    computer.pc.setValue(EA);
                } else {
                    computer.pc.setValue(computer.pc.getBase10Value() + 1);
                }
                break;
            case 3:
                if (computer.gpr[3].getBase10Value() >= 0) {
                    computer.pc.setValue(EA);
                } else {
                    computer.pc.setValue(computer.pc.getBase10Value() + 1);
                }
                break;
            default:
                System.out.println("Error");
                return Computer.ERROR_RET_CODE;
        }
        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int AMRStage2() {
        EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }
        memVal = checkCache(EA);

        computer.mar.setValue(EA);
        computer.mbr.setValue(memVal);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
        
        return 1;
    }

    /**
     * Add Memory To Register
     */
    public int AMRStage3() {
        switch (instruction.gpr) {
            case 0:
                if (computer.gpr[0].getBase10Value() + computer.mbr.getBase10Value() > 65535) {
                    computer.ccr[0].setValue(1);
                    // computer.gpr[0].setValue(65535);
                } else {
                    computer.ccr[0].setValue(0);
                    computer.gpr[0].setValue(computer.gpr[0].getBase10Value() + computer.mbr.getBase10Value());
                }
                break;
            case 1:
                if (computer.gpr[1].getBase10Value() + computer.mbr.getBase10Value() > 65535) {
                    computer.ccr[0].setValue(1);
                    // computer.gpr[1].setValue(65535);
                } else {
                    computer.ccr[0].setValue(0);
                    computer.gpr[1].setValue(computer.gpr[1].getBase10Value() + computer.mbr.getBase10Value());
                }
                break;
            case 2:
                if (computer.gpr[2].getBase10Value() + computer.mbr.getBase10Value() > 65535) {
                    computer.ccr[0].setValue(1);
                    // computer.gpr[2].setValue(65535);
                } else {
                    computer.ccr[0].setValue(0);
                    computer.gpr[2].setValue(computer.gpr[2].getBase10Value() + computer.mbr.getBase10Value());
                }
                break;
            case 3:
                if (computer.gpr[3].getBase10Value() + computer.mbr.getBase10Value() > 65535) {
                    computer.ccr[0].setValue(1);
                    // computer.gpr[3].setValue(65535);
                } else {
                    computer.ccr[0].setValue(0);
                    computer.gpr[3].setValue(computer.gpr[3].getBase10Value() + computer.mbr.getBase10Value());
                }
                break;
            default:
                System.out.println("Error");
                return Computer.ERROR_RET_CODE;
        }
        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int SMRStage2() {
        EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return -1;
        }

        memVal = checkCache(EA);

        computer.mar.setValue(EA);
        computer.mbr.setValue(memVal);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
        
        return 1;
    }

    /**
     * Subtract Memory From Register
     */
    public int SMRStage3() {
        switch (instruction.gpr) {
            case 0:
                if (computer.gpr[0].getBase10Value() - computer.mbr.getBase10Value() < 0) {
                    computer.ccr[1].setValue(1);
                    computer.gpr[0].setValue(computer.mbr.getBase10Value() - computer.gpr[0].getBase10Value());
                } else {
                    computer.ccr[1].setValue(0);
                    computer.gpr[0].setValue(computer.gpr[0].getBase10Value() - computer.mbr.getBase10Value());
                }
                break;
            case 1:
                if (computer.gpr[1].getBase10Value() - computer.mbr.getBase10Value() < 0) {
                    computer.ccr[1].setValue(1);
                    computer.gpr[1].setValue(computer.mbr.getBase10Value() - computer.gpr[1].getBase10Value());
                } else {
                    computer.ccr[1].setValue(0);
                    computer.gpr[1].setValue(computer.gpr[1].getBase10Value() - computer.mbr.getBase10Value());
                }
                break;
            case 2:
                if (computer.gpr[2].getBase10Value() - computer.mbr.getBase10Value() < 0) {
                    computer.ccr[1].setValue(1);
                    computer.gpr[2].setValue(computer.mbr.getBase10Value() - computer.gpr[2].getBase10Value());
                } else {
                    computer.ccr[1].setValue(0);
                    computer.gpr[2].setValue(computer.gpr[2].getBase10Value() - computer.mbr.getBase10Value());
                }
                break;
            case 3:
                if (computer.gpr[3].getBase10Value() - computer.mbr.getBase10Value() < 0) {
                    computer.ccr[1].setValue(1);
                    computer.gpr[3].setValue(computer.mbr.getBase10Value() - computer.gpr[3].getBase10Value());
                } else {
                    computer.ccr[1].setValue(0);
                    computer.gpr[3].setValue(computer.gpr[3].getBase10Value() - computer.mbr.getBase10Value());
                }
                break;
            default:
                System.out.println("Error");
                return Computer.ERROR_RET_CODE;
        }
        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int AIRStage2() {
    	computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
        
        return 1;
    }

    /**
     * Add Immediate to Register
     * IX and I are ignored in this instruction
     */
    public int AIRStage3() {
    	switch (instruction.gpr) {
            case 0:
                computer.gpr[0].setValue(computer.gpr[0].getBase10Value() + instruction.address);
                break;
            case 1:
                computer.gpr[1].setValue(computer.gpr[1].getBase10Value() + instruction.address);
                break;
            case 2:
                computer.gpr[2].setValue(computer.gpr[2].getBase10Value() + instruction.address);
                break;
            case 3:
                computer.gpr[3].setValue(computer.gpr[3].getBase10Value() + instruction.address);
                break;
            default:
                System.out.println("Error");
                return Computer.ERROR_RET_CODE;
        }
        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int SIRStage2() {
    	computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
        
        return 1;
    }
    
    /**
     * Subtract Immediate from Register
     */
    public int SIRStage3() {
        switch (instruction.gpr) {
            case 0:
                computer.gpr[0].setValue(computer.gpr[0].getBase10Value() - instruction.address);
                break;
            case 1:
                computer.gpr[1].setValue(computer.gpr[1].getBase10Value() - instruction.address);
                break;
            case 2:
                computer.gpr[2].setValue(computer.gpr[2].getBase10Value() - instruction.address);
                break;
            case 3:
                computer.gpr[3].setValue(computer.gpr[3].getBase10Value() - instruction.address);
                break;
            default:
                System.out.println("Error");
                return Computer.ERROR_RET_CODE;
        }
        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int MLTStage2() {
    	if (instruction.rx != 0 && instruction.rx != 2 || instruction.ry != 0 && instruction.ry != 2) {
            System.out.println("Error !");
            return -2;
        }
    	
    	return 1;
    }

    /**
     * Multiply Register by Register
     */
    public int MLTStage3() {
        if (instruction.rx == 0 || instruction.rx == 2 && instruction.ry == 0 || instruction.ry == 2) {
            int data1 = this.getValueFromRById(instruction.rx);
            int data2 = this.getValueFromRById(instruction.ry);
            int temp = data1 * data2;

            if (temp < Integer.MAX_VALUE && temp > Integer.MIN_VALUE) {
                int next = 0;
                if (instruction.rx == 0) {
                    next = 1;
                } else {
                    next = 3;
                }

                String temp1 = this.InttoBinary32(temp);
                this.setValueToRById(instruction.rx, Integer.parseInt(temp1.substring(0, 16), 2));
                this.setValueToRById(next, Integer.parseInt(temp1.substring(16), 2));
            } else {
                System.out.println("OVERFLOW");
                computer.ccr[0].setValue(1);
            }
        }
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int DVDStage2() {
    	if (instruction.rx != 0 && instruction.rx != 2 || instruction.ry != 0 && instruction.ry != 2) {
            System.out.println("Error !");
            return -2;
        }
    	
    	return 1;
    }

    /**
     * Divide Register by Register
     */
    public int DVDStage3() {
    	if (instruction.rx == 0 || instruction.rx == 2 && instruction.ry == 0 || instruction.ry == 2) {
            int data1 = this.getValueFromRById(instruction.rx);
            int data2 = this.getValueFromRById(instruction.ry);
            if (data2 != 0) {
                temp1 = data1 / data2;
                temp2 = data1 % data2;
                int next = 0;

                if (instruction.rx == 0) {
                    next = 1;
                } else {
                    next = 3;
                }
                String quotient = this.InttoBinary16(temp1);
                String remainder = this.InttoBinary16(temp2);
                System.out.println(quotient + "   " + remainder);
                this.setValueToRById(instruction.rx, Integer.parseInt(quotient, 2));
                this.setValueToRById(next, Integer.parseInt(remainder, 2));
            } else {
                System.out.println("DIVZERO");
                computer.ccr[2].setValue(1);
            }
        }
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int TRRStage2() {
    	temp1 = getValueFromRById(instruction.rx);
        temp2 = getValueFromRById(instruction.ry);
        
        return 1;
    }

    /**
     * Test the Equality of Register and Register
     */
    public int TRRStage3() {
        if (temp1 == temp2) {
            computer.ccr[3].setValue(1);
        } else {
            computer.ccr[3].setValue(0);
        }
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Logical And of Register and Register
     */
    public int ANDd() {
        temp1 = getValueFromRById(instruction.rx);
        temp2 = getValueFromRById(instruction.ry);

        setValueToRById(instruction.rx, temp1 & temp2);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int ANDStage2() {
    	temp1Arr = computer.gpr[instruction.rx].getValue();
        temp2Arr = computer.gpr[instruction.ry].getValue();
        
        return 1;
    }

    public int ANDStage3() {
        for (int i = 0; i < 16; i++) {
            if (temp2Arr[i] == 0) {
                temp1Arr[i] = 0;
            }
        }
        computer.gpr[instruction.rx].setValue(temp1);
        computer.pc.setValue(temp1);
        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Logical Or of Register and Register
     */
    public int ORRr() {
        temp1 = getValueFromRById(instruction.rx);
        temp2 = getValueFromRById(instruction.ry);

        setValueToRById(instruction.rx, temp1 | temp2);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int ORRStage2() {
    	temp1Arr = computer.gpr[instruction.rx].getValue();
        temp2Arr = computer.gpr[instruction.ry].getValue();
        
        return 1;
    }
    
    public int ORRStage3() {
        for (int i = 0; i < 16; i++) {
            if (temp2Arr[i] == 1) {
                temp1Arr[i] = 1;
            }
        }
        computer.gpr[instruction.rx].setValue(temp1);
        computer.pc.setValue(temp1);
        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Logical Not of Register To Register
     */
    public int NOTt() {
        temp1 = getValueFromRById(instruction.rx);
        setValueToRById(instruction.rx, ~temp1);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int NOTStage2() {
    	tempArr = computer.gpr[instruction.rx].getValue();
    	
    	return 1;
    }
    
    public int NOTStage3() {
    	for (int i = 0; i < 16; i++) {
            if (tempArr[i] == 1) {
                tempArr[i] = 0;
            } else {
                tempArr[i] = 1;
            }
        }
        computer.gpr[instruction.rx].setValue(tempArr);
        computer.pc.setValue(tempArr);
        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int SRCStage2() {
    	tempR = getValueFromRById(instruction.gpr);
    	
    	return 1;
    }

    /**
     * Shift Register by Count
     */
    public int SRCStage3() {
    	if (instruction.al == 0) {
            if (instruction.lr == 0) {
                tempR = (tempR >> instruction.count);
            }
            if (instruction.lr == 1) {
                tempR = (tempR << instruction.count);
            }
        }
       
  /*    if(instruction.al == 1) {
        	
        	if(instruction.lr == 0) {
        		if(tempR >= 0) {
        			tempR = (tempR >>> instruction.count);
        		}else {
        				String x =Integer.toBinaryString(tempR >>> instruction.count);
        				x = x.replace("1111111111111111","");
        				tempR = Integer.parseInt(x,2);
        			}
        		}
        	}
        
        	if(instruction.lr == 1) {
        		tempR = tempR <<  instruction.count;
        	}
   */

        this.setValueToRById(instruction.gpr, tempR);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int RRCStage2() {
    	tempR = getValueFromRById(instruction.gpr);
    	
    	return 1;
    }

    /**
     * Rotate Register by Count
     */
    public int RRCStage3() {
        String binaryR = this.InttoBinary16(tempR);
        char temp[] = new char[16];

        for (int i = 0; i < 16; i++) {
            temp[i] = binaryR.charAt(i);
        }

        if (instruction.lr == 1) {
            for (int i = 0; i < 16 - instruction.count; i++) {
                temp[i] = binaryR.charAt(i + instruction.count);
            }
            for (int i = 0; i < instruction.count; i++) {
                temp[16 - instruction.count + i] = binaryR.charAt(i);
            }
        }

        if (instruction.lr == 0) {
            for (int i = 0; i < 16 - instruction.count; i++) {
                temp[instruction.count + i] = binaryR.charAt(i);
            }
            for (int i = 0; i < instruction.count; i++) {
                temp[i] = binaryR.charAt(16 - instruction.count + i);
            }
        }

        String temp1 = "";
        for (int i = 0; i < 16; i++) {
            temp1 = temp1 + temp[i];
        }

        this.setValueToRById(instruction.gpr, Integer.parseInt(temp1));
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }


    /**
     * Input Character To Register from Device
     */
    public int IN() {
        if (instruction.did == 0) { // keyboard
            computer.stopForInput = 1;
        } else if (instruction.did == 31) { // Outer file
            int in = computer.reader.readOneChar();
            return continueIn(in);
        }
        return Computer.SUCCESS_RET_CODE;
    }

    public int continueIn(int input) {
        switch (instruction.r) {
            case 0:
                computer.gpr[0].setValue((int) input);
                break;
            case 1:
                computer.gpr[1].setValue((int) input);
                break;
            case 2:
                computer.gpr[2].setValue((int) input);
                break;
            case 3:
                computer.gpr[3].setValue((int) input);
                break;
            default:
                return Computer.ERROR_RET_CODE;
        }
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }
    
    /**
     * Stage 2 of RISC pipeline.
     * @return 1 if further stages should be executed. -1 if no further stages should be executed, no error. -2 if no further stages should be executed, with error
     */
    public int OUTStage2() {
    	tempR = this.getValueFromRById(instruction.r);
    	
    	return 1;
    }

    /**
     * Output Character to Device from Register
     */
    public int OUTStage3() {
        if (instruction.did == 1) { // integer printer
            computer.printer += " " + Integer.toString(tempR);
        } else if (instruction.did == 30) { // character printer
            if (tempR > 31 && tempR < 127) {
                StringBuffer temp = new StringBuffer();
                temp.append((char) tempR);
                computer.printer += temp.toString();
            }
        }
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Check Device Status to Register,
     */

    public int CHK() {
        if (instruction.did == KEYBOARD) {
            this.setValueToRById(instruction.r, 0);
        }
        if (instruction.did == PRINTER) {
            this.setValueToRById(instruction.r, 1);
        }
        if (instruction.did == CARD_READER) {
            this.setValueToRById(instruction.r, 2);
        }
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }

    public int HALT() {
        String haltInstruction = Arrays.toString(instruction.MEM).replaceAll("\\[|\\]|,|\\s", "");
        if (haltInstruction.substring(8, 16).equals("00000000")) {
            System.out.println("HALT!");
            System.out.println("Stop the machine!");
            computer.status = 0;
            return Computer.HLT_RET_CODE;
        }
        return Computer.SUCCESS_RET_CODE;
    }

    public int TRAP() {
        if (instruction.trapcode < 0 || instruction.trapcode > 15) {
            computer.mfr.setErr(1, 2);
        }

        // Calculate PC + 1
        int[] pcVal = computer.pc.getValue();
        int[] pcPlus1 = new int[12];

        for (int i = 11; i >= 0; i--) {
            if (pcVal[i] == 0) {
                pcPlus1[i] = 1;
                break;
            } else {
                pcPlus1[i] = 0;
            }
        }

        // Store PC + 1 in memory location 2
        computer.RAM[2].MEM = pcPlus1;

        // Execute routine whose address is in memory location 0 + trap code
        computer.pc.setValue(computer.RAM[0].mem + computer.tcr.getBase10Value() * 10);

        return Computer.SUCCESS_RET_CODE;
    }


    //Floating Add Memory To Register
    public int FADD() {
        EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return Computer.SUCCESS_RET_CODE;
        }
        memVal = checkCache(EA);
        computer.mar.setValue(EA);
        computer.mbr.setValue(memVal);

        int MAX_VALUE = 2 ^ 6;
        int MIN_VALUE = -2 ^ 6 - 1;

        int valueFR = 0;
        int valueEA = 0;
        if (instruction.iad == 0) {
            switch (instruction.fr) {
                case 0:
                    valueFR = computer.fr[0].getBase10Value();
                    valueEA = computer.mbr.getBase10Value();
                    //c(fr) -> c(fr) + c(EA)
                    int result = valueFR + valueEA;
                    if (result > MAX_VALUE && result < MIN_VALUE) {
                        computer.ccr[0].setValue(1);
                    } else {
                        computer.fr[0].setValue(result);
                    }
                    break;
                case 1:
                    valueFR = computer.fr[1].getBase10Value();
                    valueEA = computer.mbr.getBase10Value();
                    //c(fr) -> c(fr) + c(EA)
                    int result1 = valueFR + valueEA;
                    if (result1 > MAX_VALUE && result1 < MIN_VALUE) {
                        computer.ccr[0].setValue(1);
                    } else {
                        computer.fr[1].setValue(result1);
                    }
                    break;
                default:
                    return Computer.ERROR_RET_CODE;
            }
        } else {
            switch (instruction.fr) {
                case 0:
                    valueFR = computer.fr[0].getBase10Value();
                    valueEA = computer.RAM[computer.mbr.getBase10Value()].mem;
                    //c(fr) -> c(fr) + c((EA))
                    int result = valueFR + valueEA;
                    if (result > MAX_VALUE && result < MIN_VALUE) {
                        computer.ccr[0].setValue(1);
                    } else {
                        computer.fr[0].setValue(result);
                    }
                    break;
                case 1:
                    valueFR = computer.fr[1].getBase10Value();
                    valueEA = computer.RAM[computer.mbr.getBase10Value()].mem;
                    //c(fr) -> c(fr) + c(c(EA))
                    int result1 = valueFR + valueEA;
                    if (result1 > MAX_VALUE && result1 < MIN_VALUE) {
                        computer.ccr[0].setValue(1);
                    } else {
                        computer.fr[1].setValue(result1);
                    }
                    break;
                default:
                    return Computer.ERROR_RET_CODE;
            }
        }
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }

    // Floating Subtract Memory From Register
    public int FSUB() {
        EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return Computer.SUCCESS_RET_CODE;
        }
        memVal = checkCache(EA);
        computer.mar.setValue(EA);
        computer.mbr.setValue(memVal);

        int MAX_VALUE = 2 ^ 6;
        int MIN_VALUE = -2 ^ 6 - 1;

        int valueFR = 0;
        int valueEA = 0;
        if (instruction.iad == 0) {

            switch (instruction.fr) {
                case 0:
                    valueFR = computer.fr[0].getBase10Value();
                    valueEA = computer.mbr.getBase10Value();
                    //c(fr) -> c(fr) - c(EA)
                    int result = valueFR - valueEA;
                    if (result > MAX_VALUE && result < MIN_VALUE) {
                        computer.ccr[0].setValue(0);
                    } else {
                        computer.fr[0].setValue(result);
                    }
                    break;
                case 1:
                    valueFR = computer.fr[1].getBase10Value();
                    valueEA = computer.mbr.getBase10Value();
                    //c(fr) -> c(fr) - c(EA)
                    int result1 = valueFR - valueEA;
                    if (result1 > MAX_VALUE && result1 < MIN_VALUE) {
                        computer.ccr[0].setValue(0);
                    } else {
                        computer.fr[1].setValue(result1);
                    }
                    break;
                default:
                    return Computer.ERROR_RET_CODE;
            }
        } else {
            switch (instruction.fr) {
                case 0:
                    valueFR = computer.fr[0].getBase10Value();
                    valueEA = computer.RAM[computer.mbr.getBase10Value()].mem;
                    //c(fr) -> c(fr) - c((EA))
                    int result = valueFR - valueEA;
                    if (result > MAX_VALUE && result < MIN_VALUE) {
                        computer.ccr[0].setValue(0);
                    } else {
                        computer.fr[0].setValue(result);
                    }
                    break;
                case 1:
                    valueFR = computer.fr[1].getBase10Value();
                    valueEA = computer.RAM[computer.mbr.getBase10Value()].mem;
                    //c(fr) -> c(fr) - c(c(EA))
                    int result1 = valueFR - valueEA;
                    if (result1 > MAX_VALUE && result1 < MIN_VALUE) {
                        computer.ccr[0].setValue(0);
                    } else {
                        computer.fr[1].setValue(result1);
                    }
                    break;
                default:
                    return Computer.ERROR_RET_CODE;
            }
        }

        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }
    
    
     /*public int CNVRT(){
    	EA = getEffectiveAdr();
    	if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return Computer.SUCCESS_RET_CODE;
        }
    	instruction.F = this.getValueFromRById(instruction.gpr);
    	memVal = checkCache(EA);
        computer.mar.setValue(EA);
        computer.mbr.setValue(memVal);
    	
        if(instruction.F == 0){
        	this.setValueToRById(instruction.gpr, Math.round(computer.mbr.getBase10Value()));
        }
    	if(instruction.F == 1){
    		computer.fr[0].setValue(computer.mbr.getValue());
    		System.out.print((float)computer.fr[0].getBase10Value());
    	}
    	computer.pc.setValue(computer.pc.getBase10Value() + 1);
    	return Computer.SUCCESS_RET_CODE;
    }
    */

    //Load Floating Register From Memory, fr = 0..1
    public int LDFR() {
        EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return Computer.SUCCESS_RET_CODE;
        }

        String exp = "0000000";
        String man = "00000000";
        memVal = checkCache(EA);

        computer.mar.setValue(EA);
        computer.mbr.setValue(memVal);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);

        int expI = computer.mbr.getBase10Value();
        computer.mar.setValue(EA + 1);
        computer.mbr.setValue(computer.mar.getValue());

        int manI = computer.mbr.getBase10Value();

        String temp = Integer.toString(expI);
        exp = exp.substring(0, 7 - temp.length()) + temp;
        String temp1 = Integer.toString(manI);
        man = temp1 + man.substring(temp1.length());

        String frs = exp + man;
        this.setFRByNum(instruction.fr, Integer.parseInt(frs, 2));


        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }


    // Store Floating Register To Memory, fr = 0..1
    public int STFR() {
        EA = getEffectiveAdr();
        if (this.checkBeyond(EA) == 1) {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
            computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
            return Computer.SUCCESS_RET_CODE;
        }

        switch (instruction.fr) {

            case 0:
                int cfr = computer.fr[0].getBase10Value();

                String buffer = "0000000000000000";
                String frs = Integer.toBinaryString(cfr);
                if (frs.length() < 16)
                    frs = buffer.substring(0, 16 - frs.length()) + frs;

                int man = Integer.parseInt(frs.substring(8, 16), 2);
                int exp = Integer.parseInt(frs.substring(0, 8), 2);

                computer.mar.setValue(EA);
                computer.mbr.setValue(exp);
                computer.cache.addToCache(computer.mar.getBase10Value(), computer.mbr.getValue());

                computer.mar.setValue(EA + 1);
                computer.mbr.setValue(man);
                computer.cache.addToCache(computer.mar.getBase10Value(), computer.mbr.getValue());

                computer.pc.setValue(computer.pc.getBase10Value() + 1);
                break;
            case 1:
                int cfr1 = computer.fr[1].getBase10Value();

                String buffer1 = "0000000000000000";
                String frs1 = Integer.toBinaryString(cfr1);
                if (frs1.length() < 16)
                    frs = buffer1.substring(0, 16 - frs1.length()) + frs1;

                int man1 = Integer.parseInt(frs1.substring(8, 16), 2);
                int exp1 = Integer.parseInt(frs1.substring(0, 8), 2);

                computer.mar.setValue(EA);
                computer.mbr.setValue(exp1);
                computer.cache.addToCache(computer.mar.getBase10Value(), computer.mbr.getValue());

                computer.mar.setValue(EA + 1);
                computer.mbr.setValue(man1);
                computer.cache.addToCache(computer.mar.getBase10Value(), computer.mbr.getValue());

                computer.pc.setValue(computer.pc.getBase10Value() + 1);
                break;
            default:
                return Computer.ERROR_RET_CODE;
        }
        return Computer.SUCCESS_RET_CODE;

    }


    //set value by NUM to floating register fr0-fr1
    public void setFRByNum(int num, int fr) {
        if (num == 0) {
            computer.fr[0].setValue(fr);
        }
        if (num == 1) {
            computer.fr[1].setValue(fr);
        }
    }


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
        switch (id) {
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

    /**
     * Checks if EA is stored in cache. If not, store in cache
     *
     * @param EA
     * @return Value stored at EA
     */
    public int[] checkCache(int EA) {
        memVal = computer.cache.checkCache(EA);
        if (memVal == null) {
            // Address not found in cache
            memVal = computer.RAM[EA].MEM;

            // Add address to cache
            computer.cache.addToCache(EA, memVal);
        }

        return memVal;
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

    public int checkReserved(int value) {
        if (value < 6 && value > 0) {
            computer.mfr.setErr(1, 3);
            return 1;
        } else {
            computer.mfr.setValue(0);
            return 0;
        }
    }

    public int checkBeyond(int value) {
        if (value > 2047 || value < 0) {
            computer.mfr.setErr(1, 0);
            return 1;
        } else {
            computer.mfr.setValue(0);
            return 0;
        }
    }

}
