package com.app.classproject.model;

import java.io.IOException;

/**
 * Define the virtual computer, initialize registers and memory, and pre-load and run program.
 */
public class Computer {
    public static int ERROR_RET_CODE = -1;
    public static int HLT_RET_CODE = 0;
    public static int SUCCESS_RET_CODE = 1;

    public Register[] gpr = new Register[4];
    public Register[] idx = new Register[3]; // X0-X2
    public Register[] ccr = new Register[4];
    public Register pc, ir, mar, mbr, mfr, tcr;

    public String printer;

    public memory[] RAM = new memory[2048];
    public Cache cache = new Cache();

    public int status; // 1: working, 0: halt, -1: error

    public int stopForInput; // Force the computer to stop and wait until the required input is ready.

    public ProjectReader reader; // A reader for computer to read outer files (test program 2)


    public Computer() {
        // Initialize registers
        for (int i = 0; i < gpr.length; i++) {
            gpr[i] = new Register(Register.Type.GPR);
        }
        for (int i = 0; i < idx.length; i++) {
            idx[i] = new Register(Register.Type.IDX);
        }
        for (int i = 0; i < ccr.length; i++) {
            ccr[i] = new Register(Register.Type.CCR);
        }
        pc = new Register(Register.Type.PC);
        ir = new Register(Register.Type.IR);
        mar = new Register(Register.Type.MAR);
        mbr = new Register(Register.Type.MBR);
        mfr = new Register(Register.Type.MFR);
        tcr = new Register(Register.Type.TCR);

        printer = "";

        // Initialize memory
        for (int i = 0; i < RAM.length; i++) {
            RAM[i] = new memory();
            RAM[i].ini();
        }

        // Initialize memory address 0 for TRAP instruction. (Memory address 1800)
        RAM[0].MEM = new int[] {0, 0, 0, 0, 1, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0};
        
        initializeTrapCodeRoutines();

        status = 1;

        stopForInput = 0;

        reader = new ProjectReader();
    }


    public enum MachineFaultCode {

        IllEGAL_RESERVED_LOCATION(0, "Illegal Memory Address to Reserved Locations. MFR set to binary 0001"),
        ILLEGAL_TRAP_CODE(1, "Illegal TRAP code. MFR set to binary 0010"),
        ILLEGAL_OP_CODE(2, "Illegal Operation Code. MFR set to 0100"),
        ILLEGAL_BEYOND_ADDRESS(3, "Illegal Memory Address beyond 2048 (memory installed).  MFR set to binary 1000");

        int value;
        String message;

        private MachineFaultCode(int value, String message) {
            this.value = value;
            this.message = message;
        }

        public int getFaultValue() {
            return this.value;
        }

        public String getFaultMessage() {
            return this.message;
        }
    }


    ///we do not need this function

    /**
     * Build 16-bit instruction array based on given input
     */
    /*
    public int[] buildInstruction(int[] opcode, int[] IX, int[] R, int I, int[] address) {
        int[] instruction = new int[16];

        // Set opcode
        for (int i = 0; i < 6; i++) {
            instruction[i] = opcode[i];
        }

        // Set IX
        instruction[6] = IX[0];
        instruction[7] = IX[1];

        // Set R
        instruction[8] = R[0];
        instruction[9] = R[1];

        // Set I
        instruction[10] = I;

        // Set Address
        for (int i = 11; i < 16; i++) {
            instruction[i] = address[i - 11];
        }

        return instruction;
    }
    
    */


    /**
     * Specify instructions and load into memory
     */
    public void loadProgram() {
        // Program starts at RAM[6]
        pc.setValue(6);

        // Set register values
        idx[0].setValue(1);
        idx[1].setValue(2);
        idx[2].setValue(3);

        // Set memory values
        RAM[25].mem = 26;
        RAM[25].loadval();
        //RAM[25].MEM = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0}; // 26
        //RAM[25].setup();
        /*
        RAM[26].MEM = new int[]{0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0}; // 26794
        RAM[26].setup();
        RAM[28].MEM = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1}; // 29
        RAM[28].setup();
        RAM[29].MEM = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1}; // 351
        RAM[29].setup();
        RAM[30].MEM = new int[]{0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0}; // 18650
        RAM[30].setup();
        RAM[31].MEM = new int[]{0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1}; // 7083
        RAM[31].setup();
        */
        RAM[26].mem = 26794;
        RAM[26].loadval();
        RAM[28].mem = 29;
        RAM[28].loadval();
        RAM[29].mem = 351;
        RAM[29].loadval();
        RAM[30].mem = 18650;
        RAM[30].loadval();
        RAM[31].mem = 7083;
        RAM[31].loadval();
        memory test4 = new memory();

        // Load register 0 from RAM[31]. LDR, no indexing, direct
        //RAM[6].MEM = buildInstruction(Instructions.LDR_opc, new int[]{0, 0}, new int[]{0, 0}, 0, new int[]{1, 1, 1, 1, 1});
        //RAM[6].setup();
        test4 = new memory();
        test4.opc = Instructions.LDRopc;
        test4.gpr = 0;
        test4.idr = 0;
        test4.iad = 0;
        test4.address = 31;
        test4.load();
        RAM[6] = test4;


        // Load register 2 from RAM[30]. LDR, indexing 2, direct
        //RAM[7].MEM = buildInstruction(Instructions.LDR_opc, new int[]{0, 1}, new int[]{1, 0}, 0, new int[]{1, 1, 1, 0, 1});
        //RAM[7].setup();
        test4 = new memory();
        test4.opc = Instructions.LDRopc;
        test4.gpr = 1;
        test4.idr = 2;
        test4.iad = 0;
        test4.address = 30;
        test4.load();
        RAM[7] = test4;

        // Load register 1 from RAM[29]. LDR, no indexing, indirect
        //RAM[8].MEM = buildInstruction(Instructions.LDR_opc, new int[]{0, 0}, new int[]{0, 1}, 1, new int[]{1, 1, 1, 0, 0});
        //RAM[8].setup();
        test4 = new memory();
        test4.opc = Instructions.LDRopc;
        test4.idr = 0;
        test4.iad = 1;
        test4.address = 29;
        test4.load();
        RAM[8] = test4;
        // Load register 3 from RAM[29]. LDR, indexing 3, indirect
        //RAM[9].MEM = buildInstruction(Instructions.LDR_opc, new int[]{1, 0}//error//, new int[]{1, 1}, 1, new int[]{1, 1, 0, 1, 0});
        //RAM[9].setup();
        test4 = new memory();
        test4.opc = Instructions.LDRopc;
        test4.gpr = 3;
        test4.idr = 3;
        test4.iad = 1;
        test4.address = 29;
        test4.load();
        RAM[9] = test4;

        // Store register 0 to RAM[28]. STR, no indexing, direct
        //RAM[10].MEM = buildInstruction(Instructions.STR_opc, new int[]{0, 0}, new int[]{0, 0}, 0, new int[]{1, 1, 1, 0, 0});
        //RAM[10].setup();
        test4 = new memory();
        test4.opc = Instructions.STRopc;
        test4.gpr = 0;
        test4.idr = 0;
        test4.iad = 0;
        test4.address = 28;
        test4.load();
        RAM[10] = test4;
        // Store register 1 to RAM[27]. STR, indexing 1, no indirect
        //RAM[11].MEM = buildInstruction(Instructions.STR_opc, new int[]{1, 1}, new int[]{0, 1}, 0, new int[]{1, 1, 0, 0, 0});
        //RAM[11].setup();
        test4 = new memory();
        test4.opc = Instructions.STRopc;
        test4.gpr = 1;
        test4.idr = 1;
        test4.iad = 0;
        test4.address = 27;
        test4.load();
        RAM[11] = test4;
        // Store register 2 to RAM[26]. STR, no indexing, indirect
        //RAM[12].MEM = buildInstruction(Instructions.STR_opc, new int[]{0, 0}, new int[]{1, 0}, 1, new int[]{1, 1, 0, 0, 1});
        //RAM[12].setup();
        test4 = new memory();
        test4.opc = Instructions.STRopc;
        test4.gpr = 1;
        test4.idr = 1;
        test4.iad = 0;
        test4.address = 27;
        test4.load();
        RAM[12] = test4;
        // Store register 3 to RAM[26]. STR, indexing 3 , indirect
        //RAM[13].MEM = buildInstruction(Instructions.STR_opc, new int[]{0, 1}, new int[]{1, 1}, 1, new int[]{1, 1, 0, 0, 0});
        //RAM[13].setup();
        test4 = new memory();
        test4.opc = Instructions.STRopc;
        test4.gpr = 3;
        test4.idr = 3;
        test4.iad = 1;
        test4.address = 26;
        test4.load();
        RAM[13] = test4;
        // Load register 3 with effective address. LDA, indexing, direct
        test4 = new memory();
        test4.opc = Instructions.LDAopc;
        test4.gpr = 3;
        test4.idr = 2;
        test4.iad = 0;
        test4.address = 31;
        test4.load();
        RAM[14] = test4;

        // Load register 3 with effective address. LDA, indexing, indirect
        test4 = new memory();
        test4.opc = Instructions.LDAopc;
        test4.gpr = 3;
        test4.idr = 2;
        test4.iad = 1;
        test4.address = 31;
        test4.load();
        RAM[15] = test4;

        // Load Index register 2 from Memory. LDX, indexing, direct
        test4 = new memory();
        test4.opc = Instructions.LDXopc;
        test4.gpr = 3;
        test4.idr = 2;
        test4.iad = 0;
        test4.address = 31;
        test4.load();
        RAM[16] = test4;

        // Load Index register 2 from Memory. LDX, indexing, indirect
        test4 = new memory();
        test4.opc = Instructions.LDXopc;
        test4.gpr = 3;
        test4.idr = 2;
        test4.iad = 1;
        test4.address = 27;
        test4.load();
        RAM[17] = test4;

        // Store Index Register 2 to Memory. STX, indexing, direct
        test4 = new memory();
        test4.opc = Instructions.STXopc;
        test4.gpr = 3;
        test4.idr = 2;
        test4.iad = 0;
        test4.address = 31;
        test4.load();
        RAM[18] = test4;

        // Store Index Register 2 to Memory. STX, indexing, indirect
        test4 = new memory();
        test4.opc = Instructions.STXopc;
        test4.gpr = 3;
        test4.idr = 2;
        test4.iad = 1;
        test4.address = 29;
        test4.load();
        RAM[19] = test4;


        //MFS test
        //MFS 0001 Illegal Memory Address to Reserved Locations 
        test4 = new memory();
        test4.opc = Instructions.STRopc;
        test4.gpr = 0;
        test4.idr = 0;
        test4.iad = 0;
        test4.address = 3;
        test4.load();
        RAM[1001] = test4;

        //MFS 0010 Illegal TRAP code
        test4 = new memory();
        test4.opc = 30;
        test4.trapcode = 15;
        test4.load();
        test4.trapcode = 17;
        RAM[1002] = test4;

        // MFS 0100 Illegal Operation Code
        test4 = new memory();
        test4.MEM = new int[]{1, 1, 1, 0, 1, 1, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1}; // opc59
        test4.opc = 59;
        RAM[1003] = test4;

        //MFS 1000 Memory Address beyond 2048
        RAM[30].mem = 2048;
        RAM[30].loadval();

        test4 = new memory();
        test4.opc = Instructions.LDRopc;
        test4.gpr = 0;
        test4.idr = 0;
        test4.iad = 1;
        test4.address = 30;
        test4.load();
        RAM[1004] = test4;


        ir.setValue(RAM[6].MEM);
    }


    public void loadTestProgramOne() {
        // Program starts at RAM[6]
        pc.setValue(6);

        // Set register values
        gpr[0].setValue(20);
        gpr[2].setValue(20);
        gpr[3].setValue(65535);
        idx[1].setValue(500);
        idx[2].setValue(521);

        // Set memory values
        RAM[31].mem = 500;
        RAM[31].loadval();
        RAM[500].mem = 1;
        RAM[500].loadval();

        // Load instructions
        memory tempInstruction;

        /*
        Requests 20 numbers from the user, store them and print them
         */
        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;//1
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 31;
        tempInstruction.load();
        RAM[6] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.AMRopc;//4
        tempInstruction.gpr = 1;
        tempInstruction.idr = 2;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[7] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;//2
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 31;
        tempInstruction.load();
        RAM[8] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDXopc;//41
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 1;
        tempInstruction.load();
        RAM[9] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDXopc;//41
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 31;
        tempInstruction.load();
        RAM[10] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.INopc;//61
        tempInstruction.r = 1;
        tempInstruction.did = 0;
        tempInstruction.load();
        RAM[11] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.OUTopc;//62
        tempInstruction.r = 1;
        tempInstruction.did = 1;
        tempInstruction.load();
        RAM[12] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;//2
        tempInstruction.gpr = 1;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[13] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.SMRopc;//5
        tempInstruction.gpr = 0;
        tempInstruction.idr = 2;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[14] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JNEopc;//11
        tempInstruction.gpr = 0;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 6;
        tempInstruction.load();
        RAM[15] = tempInstruction;

        /*
        Requests a number from the user, print it and store in address 521
         */
        tempInstruction = new memory();
        tempInstruction.opc = Instructions.INopc;//61
        tempInstruction.r = 1;
        tempInstruction.did = 0;
        tempInstruction.load();
        RAM[16] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.OUTopc;//62
        tempInstruction.r = 1;
        tempInstruction.did = 1;
        tempInstruction.load();
        RAM[17] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;//2
        tempInstruction.gpr = 1;
        tempInstruction.idr = 3;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[18] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;//2
        tempInstruction.gpr = 3;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 6;
        tempInstruction.load();
        RAM[19] = tempInstruction;

        /*
        Compare last input and 20 numbers
         */
        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 3;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[20] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.SMRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[21] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.SMRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 6;
        tempInstruction.load();
        RAM[22] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JCCopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 3;
        tempInstruction.iad = 0;
        tempInstruction.address = 26;
        tempInstruction.load();
        RAM[23] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;//1
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 31;
        tempInstruction.load();
        RAM[24] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.SMRopc;//5
        tempInstruction.gpr = 1;
        tempInstruction.idr = 2;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[25] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;//2
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 31;
        tempInstruction.load();
        RAM[26] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDXopc;//41
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 22;
        tempInstruction.load();
        RAM[27] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDXopc;//41
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 31;
        tempInstruction.load();
        RAM[28] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.SMRopc;//5
        tempInstruction.gpr = 2;
        tempInstruction.idr = 2;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[29] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JMAopc;//13
        tempInstruction.gpr = 0;
        tempInstruction.idr = 3;
        tempInstruction.iad = 0;
        tempInstruction.address = 31;
        tempInstruction.load();
        RAM[30] = tempInstruction;

        /* jump to if find a closer value and jump back */
        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[547] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 3;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[548] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.SMRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[549] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;//2
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 6;
        tempInstruction.load();
        RAM[550] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JMAopc;//13
        tempInstruction.gpr = 0;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 24;
        tempInstruction.load();
        RAM[551] = tempInstruction;

        /* Check if loop ends. If so, print out the final value */
        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JNEopc;//11
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 20;
        tempInstruction.load();
        RAM[552] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.OUTopc;//62
        tempInstruction.r = 0;
        tempInstruction.did = 1;
        tempInstruction.load();
        RAM[553] = tempInstruction;

        ir.setValue(RAM[6].MEM);
    }


    public void loadTestProgramTwo() {
        // Program starts at RAM[8]
        pc.setValue(9);

        // Set register values
        gpr[2].setValue(600);
        gpr[3].setValue(500);
        idx[1].setValue(600);
        idx[2].setValue(500);

        // Set memory values
        RAM[6].mem = 600;
        RAM[6].loadval();
        RAM[7].mem = 500;
        RAM[7].loadval();
        RAM[8].mem = 44;
        RAM[8].loadval();
        RAM[500].mem = 1;
        RAM[500].loadval();
        RAM[600].mem = 1;
        RAM[600].loadval();

        // Load instructions
        memory tempInstruction;

        /*
        Read the paragraph from target file, store them and print them
         */
        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 6;
        tempInstruction.load();
        RAM[9] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.AMRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 2;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[10] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 6;
        tempInstruction.load();
        RAM[11] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDXopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 1;
        tempInstruction.load();
        RAM[12] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDXopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 6;
        tempInstruction.load();
        RAM[13] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.INopc;
        tempInstruction.r = 1;
        tempInstruction.did = 31;
        tempInstruction.load();
        RAM[14] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.OUTopc;
        tempInstruction.r = 1;
        tempInstruction.did = 30;
        tempInstruction.load();
        RAM[15] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[16] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JNEopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 9;
        tempInstruction.load();
        RAM[17] = tempInstruction;

        /*
        Require a word split by characters from user, every time one character is accepted and stored until receiving an input with only space
         */
        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 7;
        tempInstruction.load();
        RAM[18] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.AMRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 2;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[19] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 7;
        tempInstruction.load();
        RAM[20] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDXopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 1;
        tempInstruction.load();
        RAM[21] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDXopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 7;
        tempInstruction.load();
        RAM[22] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.INopc;
        tempInstruction.r = 1;
        tempInstruction.did = 0;
        tempInstruction.load();
        RAM[23] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[24] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JNEopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 18;
        tempInstruction.load();
        RAM[25] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 9;
        tempInstruction.load();
        RAM[26] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 3;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 10;
        tempInstruction.load();
        RAM[27] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 11;
        tempInstruction.load();
        RAM[28] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 12;
        tempInstruction.load();
        RAM[29] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDXopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 30;
        tempInstruction.load();
        RAM[30] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDXopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 8;
        tempInstruction.load();
        RAM[31] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 0;
        tempInstruction.iad = 1;
        tempInstruction.address = 9;
        tempInstruction.load();
        RAM[32] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 1;
        tempInstruction.address = 10;
        tempInstruction.load();
        RAM[33] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JZopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 30;
        tempInstruction.load();
        RAM[34] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.TRRopc;
        tempInstruction.rx = 0;
        tempInstruction.ry = 1;
        tempInstruction.load();
        RAM[35] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JCCopc;
        tempInstruction.gpr = 3;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 17;
        tempInstruction.load();
        RAM[36] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDAopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 3;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[37] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 10;
        tempInstruction.load();
        RAM[38] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.SIRopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 31;
        tempInstruction.load();
        RAM[39] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.SIRopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 1;
        tempInstruction.load();
        RAM[40] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JZopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 7;
        tempInstruction.load();
        RAM[41] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.SIRopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 14;
        tempInstruction.load();
        RAM[42] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JZopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 11;
        tempInstruction.load();
        RAM[43] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 9;
        tempInstruction.load();
        RAM[44] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.AIRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 1;
        tempInstruction.load();
        RAM[45] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 9;
        tempInstruction.load();
        RAM[46] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 10;
        tempInstruction.load();
        RAM[47] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.AIRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 1;
        tempInstruction.load();
        RAM[48] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 10;
        tempInstruction.load();
        RAM[49] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JMAopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 30;
        tempInstruction.load();
        RAM[50] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 11;
        tempInstruction.load();
        RAM[51] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.AIRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 1;
        tempInstruction.load();
        RAM[52] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 11;
        tempInstruction.load();
        RAM[53] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JMAopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 0;
        tempInstruction.iad = 1;
        tempInstruction.address = 8;
        tempInstruction.load();
        RAM[54] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 12;
        tempInstruction.load();
        RAM[55] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.AIRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 1;
        tempInstruction.load();
        RAM[56] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 12;
        tempInstruction.load();
        RAM[57] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 3;
        tempInstruction.load();
        RAM[58] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 11;
        tempInstruction.load();
        RAM[59] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JMAopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 0;
        tempInstruction.iad = 1;
        tempInstruction.address = 8;
        tempInstruction.load();
        RAM[60] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 7;
        tempInstruction.load();
        RAM[61] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 10;
        tempInstruction.load();
        RAM[62] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.SIRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 1;
        tempInstruction.load();
        RAM[63] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.TRRopc;
        tempInstruction.rx = 1;
        tempInstruction.ry = 2;
        tempInstruction.load();
        RAM[64] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JCCopc;
        tempInstruction.gpr = 3;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 23;
        tempInstruction.load();
        RAM[65] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JMAopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 0;
        tempInstruction.iad = 1;
        tempInstruction.address = 8;
        tempInstruction.load();
        RAM[66] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDAopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 3;
        tempInstruction.iad = 0;
        tempInstruction.address = 0;
        tempInstruction.load();
        RAM[67] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.AIRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 1;
        tempInstruction.load();
        RAM[68] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.STRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 10;
        tempInstruction.load();
        RAM[69] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 0;
        tempInstruction.iad = 1;
        tempInstruction.address = 10;
        tempInstruction.load();
        RAM[70] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.OUTopc;
        tempInstruction.r = 1;
        tempInstruction.did = 30;
        tempInstruction.load();
        RAM[71] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JNEopc;
        tempInstruction.gpr = 1;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 24;
        tempInstruction.load();
        RAM[72] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.JMAopc;
        tempInstruction.gpr = 0;
        tempInstruction.idr = 1;
        tempInstruction.iad = 0;
        tempInstruction.address = 31;
        tempInstruction.load();
        RAM[73] = tempInstruction;

        // 74 is reserved as empty

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 12;
        tempInstruction.load();
        RAM[75] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.AIRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 1;
        tempInstruction.load();
        RAM[76] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.OUTopc;
        tempInstruction.r = 2;
        tempInstruction.did = 1;
        tempInstruction.load();
        RAM[77] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.LDRopc;
        tempInstruction.gpr = 2;
        tempInstruction.idr = 0;
        tempInstruction.iad = 0;
        tempInstruction.address = 11;
        tempInstruction.load();
        RAM[78] = tempInstruction;

        tempInstruction = new memory();
        tempInstruction.opc = Instructions.OUTopc;
        tempInstruction.r = 2;
        tempInstruction.did = 1;
        tempInstruction.load();
        RAM[79] = tempInstruction;

        ir.setValue(RAM[9].MEM);
    }

    /**
     * Build 16-bit instruction array based on given input
     */
    /*
    public int[] buildInstruction(int[] opcode, int[] IX, int[] R, int I, int[] address) {
        int[] instruction = new int[16];

        // Set opcode
        for (int i = 0; i < 6; i++) {
            instruction[i] = opcode[i];
        }

        // Set IX
        instruction[6] = IX[0];
        instruction[7] = IX[1];

        // Set R
        instruction[8] = R[0];
        instruction[9] = R[1];

        // Set I
        instruction[10] = I;

        // Set Address
        for (int i = 11; i < 16; i++) {
            instruction[i] = address[i - 11];
        }

        return instruction;
    }
*/

    /**
     * Specify instructions and load into memory
     */
  /*
    public void loadProgram() {
        // Program starts at RAM[6]
        pc.setValue(6);

        // Set register values
        idx[0].setValue(1);
        idx[1].setValue(2);
        idx[2].setValue(3);

        // Set memory values
        RAM[25].MEM = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1, 0}; // 26
        RAM[25].setup();
        RAM[26].MEM = new int[]{0, 1, 1, 0, 1, 0, 0, 0, 1, 0, 1, 0, 1, 0, 1, 0}; // 26794
        RAM[26].setup();
        RAM[28].MEM = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1}; // 29
        RAM[28].setup();
        RAM[29].MEM = new int[]{0, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0, 1, 1, 1, 1, 1}; // 351
        RAM[29].setup();
        RAM[30].MEM = new int[]{0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 1, 0, 1, 0}; // 18650
        RAM[30].setup();
        RAM[31].MEM = new int[]{0, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 1, 1}; // 7083
        RAM[31].setup();

        // Load register 0 from RAM[31]. LDR, no indexing, no indirect
        RAM[6].MEM = buildInstruction(Instructions.LDR_opc, new int[]{0, 0}, new int[]{0, 0}, 0, new int[]{1, 1, 1, 1, 1});
        RAM[6].setup();

        // Load register 2 from RAM[30]. LDR, indexing, no indirect
        RAM[7].MEM = buildInstruction(Instructions.LDR_opc, new int[]{0, 1}, new int[]{1, 0}, 0, new int[]{1, 1, 1, 0, 1});
        RAM[7].setup();

        // Load register 1 from RAM[29]. LDR, no indexing, indirect
        RAM[8].MEM = buildInstruction(Instructions.LDR_opc, new int[]{0, 0}, new int[]{0, 1}, 1, new int[]{1, 1, 1, 0, 0});
        RAM[8].setup();

        // Load register 3 from RAM[29]. LDR, indexing, indirect
        RAM[9].MEM = buildInstruction(Instructions.LDR_opc, new int[]{1, 0}, new int[]{1, 1}, 1, new int[]{1, 1, 0, 1, 0});
        RAM[9].setup();

        // Store register 0 to RAM[28]. STR, no indexing, no indirect
        RAM[10].MEM = buildInstruction(Instructions.STR_opc, new int[]{0, 0}, new int[]{0, 0}, 0, new int[]{1, 1, 1, 0, 0});
        RAM[10].setup();

        // Store register 1 to RAM[27]. STR, indexing, no indirect
        RAM[11].MEM = buildInstruction(Instructions.STR_opc, new int[]{1, 1}, new int[]{0, 1}, 0, new int[]{1, 1, 0, 0, 0});
        RAM[11].setup();

        // Store register 2 to RAM[26]. STR, no indexing, indirect
        RAM[12].MEM = buildInstruction(Instructions.STR_opc, new int[]{0, 0}, new int[]{1, 0}, 1, new int[]{1, 1, 0, 0, 1});
        RAM[12].setup();

        // Store register 3 to RAM[26]. STR, indexing, indirect
        RAM[13].MEM = buildInstruction(Instructions.STR_opc, new int[]{0, 1}, new int[]{1, 1}, 1, new int[]{1, 1, 0, 0, 0});
        RAM[13].setup();


        // Load register 3 with effective address. LDA, indexing, direct
        memory test4 = new memory();
        test4.opc = 3;
        test4.gpr = 3;
        test4.idr = 2;
        test4.iad = 0;
        test4.address = 31;
        test4.load();
        RAM[14] = test4;

        // Load register 3 with effective address. LDA, indexing, indirect
        test4 = new memory();
        test4.opc = 3;
        test4.gpr = 3;
        test4.idr = 2;
        test4.iad = 1;
        test4.address = 31;
        test4.load();
        RAM[15] = test4;

        // Load Index register 2 from Memory. LDX, indexing, direct
        test4 = new memory();
        test4.opc = 41;
        test4.gpr = 3;
        test4.idr = 2;
        test4.iad = 0;
        test4.address = 31;
        test4.load();
        RAM[16] = test4;

        // Load Index register 2 from Memory. LDX, indexing, indirect
        test4 = new memory();
        test4.opc = 41;
        test4.gpr = 3;
        test4.idr = 2;
        test4.iad = 1;
        test4.address = 26;
        test4.load();
        RAM[17] = test4;

        // Store Index Register 2 to Memory. STX, indexing, direct
        test4 = new memory();
        test4.opc = 42;
        test4.gpr = 3;
        test4.idr = 2;
        test4.iad = 0;
        test4.address = 31;
        test4.load();
        RAM[18] = test4;

        // Store Index Register 2 to Memory. STX, indexing, indirect
        test4 = new memory();
        test4.opc = 42;
        test4.gpr = 3;
        test4.idr = 2;
        test4.iad = 1;
        test4.address = 29;
        test4.load();
        RAM[19] = test4;

        ir.setValue(RAM[6].MEM);
    }
    
    */
    public void MFR() {
        int mfr = this.mfr.getBase10Value();

        if (mfr == 0) {
            return;
        } else {
            // Calculate PC + 1
            int[] pcVal = this.pc.getValue();
            int[] pcP1 = new int[12];

            for (int i = 11; i >= 0; i--) {
                if (pcVal[i] == 0) {
                    pcP1[i] = 1;
                    break;
                } else {
                    pcP1[i] = 0;
                }
            }
            // Store PC + 1 in memory location 4
            this.RAM[4].MEM = pcP1;

            // Execute routine whose address is in memory location 4+ machine fault code
            this.pc.setValue(this.RAM[1].mem + mfr * 4);

        }
    }

    /**
     * Run the program and print register/memory information after each instruction
     */
    public void runProgram() {
        if (stopForInput == 1) {
            return;
        }
        MFR();
        Instructions curInstruction = new Instructions(RAM[pc.getBase10Value()].MEM, this);
        int executionResult = curInstruction.execute();
        if (executionResult == SUCCESS_RET_CODE) {
            this.status = 1;
            runProgram();
        } else if (executionResult == ERROR_RET_CODE) {
            this.status = -1;
        } else if (executionResult == HLT_RET_CODE) {
            this.status = 0;
        }
    }

    /**
     * Get the next instruction and execute
     */
    public void singleStep() {
        if (stopForInput == 1) {
            return;
        }
        MFR();
        Instructions curInstruction = new Instructions(RAM[pc.getBase10Value()].MEM, this);
        int executionResult = curInstruction.execute();
        if (executionResult == SUCCESS_RET_CODE) {
            this.status = 1;
        } else if (executionResult == ERROR_RET_CODE) {
            this.status = -1;
        } else if (executionResult == HLT_RET_CODE) {
            this.status = 0;
        }
    }

    /**
     * Continue program after pausing
     */
    public void continueIn(int input) {
        stopForInput = 0;
        Instructions curInstruction = new Instructions(RAM[pc.getBase10Value()].MEM, this);
        int executionResult = curInstruction.continueIn(input);
        if (executionResult == SUCCESS_RET_CODE) {
            this.status = 1;
        } else if (executionResult == ERROR_RET_CODE) {
            this.status = -1;
        } else if (executionResult == HLT_RET_CODE) {
            this.status = 0;
        }
    }
    
    /**
     * Initialize 16 trap code routines
     */
    public void initializeTrapCodeRoutines() {
    	// Routine 1
    	RAM[1800].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}; // Load 0 into R0
        RAM[1801].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 0
        RAM[1802].MEM = new int[] {0, 0, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 2
    	RAM[1810].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Load 1 into R0
        RAM[1811].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 1
        RAM[1812].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 3
        RAM[1820].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0}; // Load 2 into R0
        RAM[1821].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 2
        RAM[1822].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 4
        RAM[1830].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Load 3 into R0
        RAM[1831].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 3
        RAM[1832].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 5
        RAM[1840].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0}; // Load 4 into R0
        RAM[1841].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 4
        RAM[1842].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 6
        RAM[1850].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 0, 1}; // Load 5 into R0
        RAM[1851].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 5
        RAM[1852].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 7
        RAM[1860].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 0}; // Load 6 into R0
        RAM[1861].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 6
        RAM[1862].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 8
        RAM[1870].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1}; // Load 7 into R0
        RAM[1871].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 7
        RAM[1872].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 9
        RAM[1880].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0}; // Load 8 into R0
        RAM[1881].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 8
        RAM[1882].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 10
        RAM[1890].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1}; // Load 9 into R0
        RAM[1891].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 9
        RAM[1892].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 11
        RAM[1900].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 0}; // Load 10 into R0
        RAM[1901].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 10
        RAM[1902].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 12
        RAM[1910].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 0, 1, 1}; // Load 11 into R0
        RAM[1911].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 11
        RAM[1912].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 13
        RAM[1920].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0}; // Load 12 into R0
        RAM[1921].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 12
        RAM[1922].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 14
        RAM[1930].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 0, 1}; // Load 13 into R0
        RAM[1931].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 13
        RAM[1932].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 15
        RAM[1940].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0}; // Load 14 into R0
        RAM[1941].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 14
        RAM[1942].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
        
        // Routine 16
        RAM[1950].MEM = new int[] {0, 0, 0, 0, 1, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1}; // Load 15 into R0
        RAM[1951].MEM = new int[] {1, 1, 1, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1}; // Print 15
        RAM[1952].MEM = new int[] {0, 0, 1, 0, 1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1}; // Jump back to memory location 3
    }
}
