package com.app.classproject.model;

public class ComputerUI {
    public Register[] gpr = new Register[4];
    public Register[] idx = new Register[3]; // X0-X2
    public Register[] ccr = new Register[4];
    public Register pc, ir, mar, mbr, mfr;
    public String printer;


    public int status;
    public String nextInstruction;

    public int stopForInput;

    public String computerMessage;


    public ComputerUI(Computer computer) {
        for (int i = 0; i < gpr.length; i++) {
            gpr[i] = computer.gpr[i];
        }
        for (int i = 0; i < idx.length; i++) {
            idx[i] = computer.idx[i];
        }
        for (int i = 0; i < ccr.length; i++) {
            ccr[i] = computer.ccr[i];
        }
        pc = computer.pc;
        ir = computer.ir;
        mar = computer.mar;
        mbr = computer.mbr;
        mfr = computer.mfr;
        printer = computer.printer;

        int[] temp = mfr.getValue();
        if (temp[0] == 1) {
            computerMessage = "Machine Fault 0: Illegal Memory Address to Reserved Locations";
        } else if (temp[1] == 1) {
            computerMessage = "Machine Fault 1: Illegal TRAP code";
        } else if (temp[2] == 1) {
            computerMessage = "Machine Fault 2: Illegal Operation Code";
        } else if (temp[3] == 1) {
            computerMessage = "Machine Fault 3: Illegal Memory Address beyond 2048 (memory installed)";
        } else {
            computerMessage = "/";
        }

        stopForInput = computer.stopForInput;

        status = computer.status;

        // Instruction in binary to instruction in string
        memory instruction = computer.RAM[pc.getBase10Value()];
        switch (instruction.opc) {
            //part 1
            case 1:
                nextInstruction = "LDR " + Integer.toString(instruction.gpr) + ", " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            case 2:
                nextInstruction = "STR " + Integer.toString(instruction.gpr) + ", " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            case 3:
                nextInstruction = "LDA " + Integer.toString(instruction.gpr) + ", " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            case 41:
                nextInstruction = "LDX " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            case 42:
                nextInstruction = "STX " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            //part 2
            case 10:
                nextInstruction = "JZ " + Integer.toString(instruction.gpr) + ", " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            case 11:
                nextInstruction = "JNE " + Integer.toString(instruction.gpr) + ", " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            case 12:
                nextInstruction = "JCC " + Integer.toString(instruction.gpr) + ", " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            case 13:
                nextInstruction = "JMA " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            case 14:
                nextInstruction = "JSR " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            case 15:
                nextInstruction = "RFS " + Integer.toString(instruction.address);
                break;
            case 16:
                nextInstruction = "SOB " + Integer.toString(instruction.gpr) + ", " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            case 17:
                nextInstruction = "JGE " + Integer.toString(instruction.gpr) + ", " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            case 4:
                nextInstruction = "AMR " + Integer.toString(instruction.gpr) + ", " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            case 5:
                nextInstruction = "SMR " + Integer.toString(instruction.gpr) + ", " + Integer.toString(instruction.idr) + ", " + Integer.toString(instruction.address) + ", " + Integer.toString(instruction.iad);
                break;
            case 6:
                nextInstruction = "AIR " + Integer.toString(instruction.gpr) + ", " + Integer.toString(instruction.address);
                break;
            case 7:
                nextInstruction = "SIR " + Integer.toString(instruction.gpr) + ", " + Integer.toString(instruction.address);
                break;
            case 20:
                nextInstruction = "MLT " + Integer.toString(instruction.rx) + ", " + Integer.toString(instruction.ry);
                break;
            case 21:
                nextInstruction = "DVD " + Integer.toString(instruction.rx) + ", " + Integer.toString(instruction.ry);
                break;
            case 22:
                nextInstruction = "TRR " + Integer.toString(instruction.rx) + ", " + Integer.toString(instruction.ry);
                break;
            case 23:
                nextInstruction = "AND " + Integer.toString(instruction.rx) + ", " + Integer.toString(instruction.ry);
                break;
            case 24:
                nextInstruction = "ORR " + Integer.toString(instruction.rx) + ", " + Integer.toString(instruction.ry);
                break;
            case 25:
                nextInstruction = "NOT " + Integer.toString(instruction.rx);
                break;
            case 31:
                nextInstruction = "SRC " + Integer.toString(instruction.r) + ", " + Integer.toString(instruction.count) + ", " + Integer.toString(instruction.lr) + ", " + Integer.toString(instruction.al);
                break;
            case 32:
                nextInstruction = "RRC " + Integer.toString(instruction.r) + ", " + Integer.toString(instruction.count) + ", " + Integer.toString(instruction.lr) + ", " + Integer.toString(instruction.al);
                break;
            case 61:
                nextInstruction = "IN " + Integer.toString(instruction.r) + ", " + Integer.toString(instruction.did);
                break;
            case 62:
                nextInstruction = "OUT " + Integer.toString(instruction.r) + ", " + Integer.toString(instruction.did);
                break;
            case 63:
                nextInstruction = "CHK " + Integer.toString(instruction.r) + ", " + Integer.toString(instruction.did);
                break;
            default:
                nextInstruction = "N/A";
                break;
        }
    }
}
