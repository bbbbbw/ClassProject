package com.app.classproject.model;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Instructions {
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
    public static final int[] SRC_opc = {0, 1, 1, 1, 1, 1};
    public static final int[] RRC_opc = {1, 0, 0, 0, 0, 0};
    public static final int[] IN_opc = {1, 1, 1, 1, 0, 1};
    public static final int[] OUT_opc = {1, 1, 1, 1, 1, 0};
    public static final int[] CHK_opc = {1, 1, 1, 1, 1, 1};

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

	public static final int	KEYBOARD	= 0;
	public static final int	PRINTER		= 1;
	public static final int	CARD_READER	= 2;

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
        switch (instruction.opc) {
            case 0:
                return HALT();
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
            default:
                return Computer.ERROR_RET_CODE;
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
     * Load register from memory
     */
    public int LDR() {
        int EA = getEffectiveAdr();
        
        // Check cache
        int[] memVal = checkCache(EA);
        
        computer.mar.setValue(EA);
        computer.mbr.setValue(memVal);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);

        System.out.println("\nRAM[" + EA + "] = " + computer.RAM[EA].mem + ", gpr[" + instruction.gpr + "] = " + computer.gpr[instruction.gpr].getBase10Value());
        System.out.println("Loading RAM[" + EA + "] into gpr[" + instruction.gpr + "]\n");

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
     * Store register to memory
     */
    public int STR() {
        int EA = getEffectiveAdr();
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);

        System.out.println("\nRAM[" + EA + "] = " + computer.RAM[EA].mem + ", gpr[" + instruction.gpr + "] = " + computer.gpr[instruction.gpr].getBase10Value());
        System.out.println("Storing gpr[" + instruction.gpr + "] into RAM[" + EA + "]");

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
     * Load Register with Address
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
     * Load Index Register from Memory
     */
    public int LDX() {
        int EA = getEffectiveAdr();
        
        // Check cache
        int[] memVal = checkCache(EA);
        
        computer.mar.setValue(EA);
        computer.mbr.setValue(memVal);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);

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
     * Store Index Register to Memory
     */
    public int STX() {
        int EA = getEffectiveAdr();
	    
        computer.mar.setValue(EA);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);

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
     * Jump if Zero
     */

    public int JZ() {
        int EA = getEffectiveAdr();
        int tempR = getValueFromRById(instruction.gpr);
        if (tempR == 0) {
            computer.pc.setValue(EA);
        } else {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
        }
        return Computer.SUCCESS_RET_CODE;
    }


    /**
     * Jump if not Equal
     */

    public int JNE() {
        int EA = getEffectiveAdr();
        int tempR = getValueFromRById(instruction.gpr);
        if (tempR != 0) {
            computer.pc.setValue(EA);
        } else {
            computer.pc.setValue(computer.pc.getBase10Value() + 1);
        }
        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Jump If Condition Code cc replaces r for this instruction
     */

    public int JCC() {
        int EA = getEffectiveAdr();
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
     * Unconditional Jump To Address
     * r is ignored in this instruction
     */

    public int JMA() {
        int EA = getEffectiveAdr();
        computer.pc.setValue(EA);

        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Jump and Save Return Address
     * R0 should contain pointer to arguments
     * Argument list should end with –1 (all 1s) value
     */

    public int JSR() {
        int EA = getEffectiveAdr();
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
     * Subtract One and Branch
     */

    public int SOB() {
        int EA = getEffectiveAdr();
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
     * Jump Greater Than or Equal To
     */

    public int JGE() {
        int EA = getEffectiveAdr();
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
     * Add Memory To Register
     */
    public int AMR() {
        int EA = getEffectiveAdr();
        int[] memVal = checkCache(EA);
        
        computer.mar.setValue(EA);
        computer.mbr.setValue(memVal);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
        
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
     * Subtract Memory From Register
     */
    public int SMR() {
        int EA = getEffectiveAdr();
        int[] memVal = checkCache(EA);
        
        computer.mar.setValue(EA);
        computer.mbr.setValue(memVal);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
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
     * Add Immediate to Register
     * IX and I are ignored in this instruction
     */
    public int AIR() {
        int EA = getEffectiveAdr();
        computer.mar.setValue(EA);
        computer.mbr.setValue(computer.RAM[EA].MEM);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);

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
     * Subtract Immediate from Register
     */

    public int SIR() {
        int EA = getEffectiveAdr();
        computer.mar.setValue(EA);
        computer.mbr.setValue(computer.RAM[EA].MEM);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        computer.ir.setValue(computer.RAM[computer.pc.getBase10Value()].MEM);
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
     * Multiply Register by Register
     */
    public int MLT() {
        if (instruction.rx != 0 && instruction.rx != 2 || instruction.ry != 0 && instruction.ry != 2) {
            System.out.println("Error !");
            return Computer.ERROR_RET_CODE;
        }
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
     * Divide Register by Register
     */
    public int DVD() {
        if (instruction.rx != 0 && instruction.rx != 2 || instruction.ry != 0 && instruction.ry != 2) {
            System.out.println("Error !");
            return Computer.ERROR_RET_CODE;
        }

        if (instruction.rx == 0 || instruction.rx == 2 && instruction.ry == 0 || instruction.ry == 2) {
            int data1 = this.getValueFromRById(instruction.rx);
            int data2 = this.getValueFromRById(instruction.ry);
            if (data2 != 0) {
                int temp1 = data1 / data2;
                int temp2 = data1 % data2;
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
     * Test the Equality of Register and Register
     */
    public int TRR() {
        int temp1 = getValueFromRById(instruction.rx);
        int temp2 = getValueFromRById(instruction.ry);
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
        int temp1 = getValueFromRById(instruction.rx);
        int temp2 = getValueFromRById(instruction.ry);

        setValueToRById(instruction.rx, temp1 & temp2);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }

    public int AND() {
        int temp1 [] = computer.gpr[instruction.rx].getValue();
        int temp2 [] = computer.gpr[instruction.ry].getValue();
        
        for(int i = 0; i < 16; i++) {
        	if(temp2[i] == 0) {
        		temp1[i] = 0;
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
        int temp1 = getValueFromRById(instruction.rx);
        int temp2 = getValueFromRById(instruction.ry);

        setValueToRById(instruction.rx, temp1 | temp2);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }
    public int ORR() {
        int temp1 [] = computer.gpr[instruction.rx].getValue();
        int temp2 [] = computer.gpr[instruction.ry].getValue();
        
        for(int i = 0; i < 16; i++) {
        	if(temp2[i] == 1) {
        		temp1[i] = 1;
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
        int temp1 = getValueFromRById(instruction.rx);
        setValueToRById(instruction.rx, ~temp1);
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }

    public int NOT() {
        int temp [] = computer.gpr[instruction.rx].getValue();
        
        for(int i = 0; i < 16; i++) {
        	if(temp[i] == 1) {
        		temp[i] = 0;
        	}else {
        		temp[i]=1;
        	}
        }
        computer.gpr[instruction.rx].setValue(temp);
        computer.pc.setValue(temp);
        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Shift Register by Count
     */

    public int SRC() {
        int tempR = getValueFromRById(instruction.gpr);
        
        if(instruction.al == 0) {
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
     * Rotate Register by Count
     */
    public int RRC() {
        int tempR = getValueFromRById(instruction.gpr);
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
            int in =  computer.reader.readOneChar();
            return continueIn(in);
        }
        return Computer.SUCCESS_RET_CODE;
    }

    public int continueIn(int input) {
        switch (instruction.r) {
            case 0:
                computer.gpr[0].setValue((int)input);
                break;
            case 1:
                computer.gpr[1].setValue((int)input);
                break;
            case 2:
                computer.gpr[2].setValue((int)input);
                break;
            case 3:
                computer.gpr[3].setValue((int)input);
                break;
            default:
                return Computer.ERROR_RET_CODE;
        }
        computer.pc.setValue(computer.pc.getBase10Value() + 1);
        return Computer.SUCCESS_RET_CODE;
    }

    /**
     * Output Character to Device from Register
     */
   public int OUT() {
        if (instruction.did == 1) {
            int val = this.getValueFromRById(instruction.r);
            if (computer.printType == 0) {
                computer.printer += " " + Integer.toString(val);
            } else if (computer.printType == 1 && val > 31) {
                StringBuffer temp = new StringBuffer();
                temp.append((char)val);
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
    
    	 if(instruction.did == KEYBOARD) {
    		this.setValueToRById(instruction.r, 0);
    	}
    	 if(instruction.did == PRINTER) {
    		this.setValueToRById(instruction.r, 1);
    	}
    	 if(instruction.did == CARD_READER) {
    		this.setValueToRById(instruction.r, 2);
    	}
    	computer.pc.setValue(computer.pc.getBase10Value() + 1);
    	return Computer.SUCCESS_RET_CODE;
    }
    
    public int HALT() {
    	String haltInstruction = Arrays.toString(instruction.MEM).replaceAll("\\[|\\]|,|\\s", "");
    	if (haltInstruction.substring(8,16).equals("00000000")) {
    		System.out.println("HALT!");
    		System.out.println("Stop the machine!");
    		return Computer.HLT_RET_CODE;
    	}
    	return Computer.SUCCESS_RET_CODE;
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
     * @param EA
     * @return Value stored at EA
     */
    public int[] checkCache(int EA) {
        int[] memVal = computer.cache.checkCache(EA);
        if(memVal == null) {
        	// Address not found in cache
        	memVal = computer.RAM[EA].MEM;
        	
        	// Add address to cache
        	computer.cache.addToCache(EA, memVal);
        }
        
        return memVal;
    }
}
