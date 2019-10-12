package com.app.classproject.model;

import java.util.Arrays;

/**
 * this class simulates the  individual Random access memory entry or words
 * The mem represent a 16-bit word which will be stored in the RAM.
 * the rest of variables are constructed to help calculate and store the decimal values of corresponding binary arrays
 * UPPER CASE VARIABLES are  binarys arrays and lower case variables are decimal number
 * It has 4 methods
 */
public class memory {

    // use array to represent the memory

    public int[] MEM = new int[16]; // Memory in binary array
    public int mem; // decimal memory integer

    public int[] RL = new int[6]; // 6 digits reserved location in binary array ex:011110
    public int opc; // translate RL into opcode decimal number ex: 36

    // load and store instruction & logical instructions
    public int[] R = new int[2]; // 3 digits general purpose registers in binary array
    public int[] IX = new int[2]; // 2 digits index registers in binary array
    public int iad; // 1 digit indirect address indicator in binary array
    public int[] ADDR = new int[5]; // 5 digits for 32 locations in binary array

    public int idr; // translate IX index register number decimal number
    public int gpr; // translate R into general purpose register decimal number
    public int address; // translate addr into integer number decimal number

    // opcode 20-25
    // register to register operations.
    public int[] RX = new int[2]; // 2 digits registers in binary array
    public int[] RY = new int[2]; // 2 digits registers in binary array
    public int rx;// register x in decimal number
    public int ry;// register y in decimal number

    // opcode 31-32
    // shift and rotate instructions
    public int r;
    public int al;// Arithmetic Shift or logical;
    public int lr;// left or right?
    public int[] COUNT = new int[4]; // 4 digits for number of count notation in binary array
    public int count;// number of the movement;

    //opcode 36
    public int trapcode;// trap code;

    // opcode 61-63
    // I/O operations
    public int[] DID = new int[5]; // 5 digits for devices ID in binary array
    public int did;// Device ID;




    /*
     * this method initialize the the memory 16-bit word with all zeros 0
     *
     */


    public void ini() {
        for (int i = 0; i < MEM.length; i++) {
            MEM[i] = 0;
        }
    }


    /*
     * this method prints the data hold in the current memory class
     * First, it will prints the word in binary array.
     * Second, it will prints the value of the opcode.
     * Third, it will prints the corresponding parameters.
     */
    public void print() {


        System.out.println("mem" + Arrays.toString(MEM));
        System.out.println("RL" + Arrays.toString(RL));
        System.out.println("opc" + opc);
        if (this.opc == 36) {
            System.out.println("trap code ");
            System.out.println("trap code " + trapcode);
        } else if (this.opc == 0) {
            System.out.println("halt ");
        } else if ((this.opc >= 1 && this.opc < 8) || (this.opc >= 10 && this.opc <= 17) || this.opc == 41
                || this.opc == 42) {
            System.out.println("Load/Store Instructions ");
            System.out.println("IX" + Arrays.toString(IX));
            System.out.println("R" + Arrays.toString(R));
            System.out.println("iad" + iad);
            System.out.println("ADDR" + Arrays.toString(ADDR));
            System.out.println("---------------------------------------------------------");
            System.out.println("idr" + idr);
            System.out.println("gpr" + gpr);
            System.out.println("address" + address);

        } else if ((this.opc >= 20 && this.opc <= 25)) {
            System.out.println("register to register ");
            System.out.println("RX" + Arrays.toString(RX));
            System.out.println("RY" + Arrays.toString(RY));
            System.out.println("---------------------------------------------------------");
            System.out.println("rx" + rx);
            System.out.println("ry" + ry);
        } else if (this.opc >= 31 && this.opc <= 32) {
            System.out.println("shift and rotate instructions seup");
            System.out.println("R" + Arrays.toString(R));
            System.out.println("COUNT" + Arrays.toString(COUNT));
            System.out.println("---------------------------------------------------------");
            System.out.println("r" + r);
            System.out.println("al" + al);
            System.out.println("lr" + lr);
            System.out.println("count" + count);

        } else if (this.opc >= 61 && this.opc <= 63) {
            System.out.println("I/O Operations ");
            System.out.println("R" + Arrays.toString(R));
            System.out.println("DEVICEID" + Arrays.toString(DID));
            System.out.println("---------------------------------------------------------");
            System.out.println("r" + r);
            System.out.println("deviceID" + did);
        } else {
            System.out.println("Illegal Operation Code ");
        }


    }

    /*
     * this method calculate the data hold in the current memory class
     * First, it will calculates the operation code for the first 6 bit.
     * Second, using the opcode, it transform the rest binary array into corresponding structure
     * Third, it will calculate the decimal values of the corresponding parameter.
     */
    public void setup() {

        // disassemble memory and read opcode
        System.arraycopy(MEM, 0, memory.this.RL, 0, 6);
        StringBuilder builder = new StringBuilder();
        for (int value : RL) {
            builder.append(value);
        }
        String tmp = builder.toString();
        //calculate the decimal value of whole binary array.
        builder = new StringBuilder();
        for (int value : MEM) {
            builder.append(value);
        }
        tmp = builder.toString();
        this.mem = Integer.parseInt(tmp, 2);

        this.opc = Integer.parseInt(tmp, 2);
        //different opcode cases
        if (this.opc == 36) {
            System.out.println("trap code setup");
        } else if (this.opc == 0 && MEM[10] == 0 && MEM[11] == 0 && MEM[12] == 0 && MEM[13] == 0 && MEM[14] == 0 && MEM[15] == 0) {
            System.out.println("HALT");
        } else if ((this.opc >= 1 && this.opc < 8) || (this.opc >= 10 && this.opc <= 17) || this.opc == 41
                || this.opc == 42) {
//			System.out.println("Load/Store Instructions");
            System.arraycopy(MEM, 6, memory.this.R, 0, 2);
            System.arraycopy(MEM, 8, memory.this.IX, 0, 2);
            System.arraycopy(MEM, 11, memory.this.ADDR, 0, 5);
            // make array into string and calculate value
            builder = new StringBuilder();
            for (int value : R) {
                builder.append(value);
            }
            tmp = builder.toString();
            this.gpr = Integer.parseInt(tmp, 2);


            builder = new StringBuilder();


            for (int value : IX) {
                builder.append(value);
            }
            tmp = builder.toString();
            this.idr = Integer.parseInt(tmp, 2);
            builder = new StringBuilder();
            this.iad = MEM[10];
            for (int value : ADDR) {
                builder.append(value);
            }
            tmp = builder.toString();
            this.address = Integer.parseInt(tmp, 2);


//			this.print();
        } else if ((this.opc >= 20 && this.opc <= 25)) {
            System.out.println("register to register setup");
            System.arraycopy(mem, 6, memory.this.RX, 0, 2);
            System.arraycopy(mem, 8, memory.this.RY, 0, 2);
            builder = new StringBuilder();
            for (int value : RX) {
                builder.append(value);
            }
            tmp = builder.toString();
            this.rx = Integer.parseInt(tmp, 2);

            builder = new StringBuilder();
            for (int value : RY) {
                builder.append(value);
            }
            tmp = builder.toString();
            this.ry = Integer.parseInt(tmp, 2);

        } else if (this.opc >= 31 && this.opc <= 32) {
            System.out.println("shift and rotate instructions setup");
            System.arraycopy(mem, 6, memory.this.R, 0, 2);
            builder = new StringBuilder();
            for (int value : R) {
                builder.append(value);
            }
            tmp = builder.toString();
            this.r = Integer.parseInt(tmp, 2);
            this.al = MEM[8];
            this.lr = MEM[9];
            System.arraycopy(mem, 12, memory.this.COUNT, 0, 4);
            builder = new StringBuilder();
            for (int value : COUNT) {
                builder.append(value);
            }
            tmp = builder.toString();
            this.count = Integer.parseInt(tmp, 2);

        } else if (this.opc >= 61 && this.opc <= 63) {
            System.out.println("I/O Operations setup");
            System.arraycopy(mem, 6, memory.this.R, 0, 2);
            builder = new StringBuilder();
            for (int value : R) {
                builder.append(value);
            }
            tmp = builder.toString();
            this.r = Integer.parseInt(tmp, 2);
            System.arraycopy(mem, 11, memory.this.DID, 0, 5);
            builder = new StringBuilder();
            for (int value : DID) {
                builder.append(value);
            }
            tmp = builder.toString();
            this.did = Integer.parseInt(tmp, 2);
        } else {

        }
//		this.print();
    }

    public int[] loadval() {
        String VAL = Integer.toBinaryString(this.mem);
        // make sure there are 0000s in front of binary numbers
        while (VAL.length() < 16) {
            VAL = "0" + VAL;
        }
        //check length(value) of the binary array
        if (VAL.length() > 16) {
            System.out.println("error value! load");
            return null;
        }
        for (int i = 0; i < VAL.length(); i++) {
            this.MEM[i] = (int) VAL.charAt(i) - 48;
        }
        this.setup();
        return this.MEM;
    }
    /*
     * this method construct the data hold in the current memory class in to a 16-bit binary array.
     * First, it will calculates the operation code for the first 6 bit.
     * Second, using the opcode, it finds corresponding structure of the binary array and looking for the corresponding decimal values
     * Third, it will construct the array.
     * Forth, it will update the corresponding value in the memory class.
     * @return the content of 16-bit binary array only as an array.
     *
     */

    public int[] load() {

        String OPC = Integer.toBinaryString(this.opc);
        // make sure there are 0000s in front of binary numbers
        while (OPC.length() < 6) {
            OPC = "0" + OPC;
        }
        //check length(value) of the binary array
        if (OPC.length() > 6) {
            System.out.println("error opcode! load");
            return null;
        }

        // opcode check
        if (this.opc == 36) {
            System.out.println("trap code load");

            String TRP = Integer.toBinaryString(this.trapcode);
            while (TRP.length() < 4) {
                TRP = "0" + TRP;
            }
            if (TRP.length() > 4) {
                System.out.println("error Trapcode! load");
            }
            String temp = OPC + "000000" + TRP;
            //transfer String into an array.
            for (int i = 0; i < temp.length(); i++) {
                this.MEM[i] = (int) temp.charAt(i) - 48;
            }
            this.setup();
            return this.MEM;
        } else if (this.opc == 0) {
            System.out.println("halt load");
            for (int i = 0; i < 16; i++) {
                this.MEM[i] = 0;
            }
            this.setup();
            return this.MEM;

        } else if ((this.opc >= 1 && this.opc < 8) || (this.opc >= 10 && this.opc <= 17) || this.opc == 41
                || this.opc == 42) {
            System.out.println("Load/Store Instructions load");
            String GPR = Integer.toBinaryString(this.gpr);
            while (GPR.length() < 2) {
                GPR = "0" + GPR;
            }
            if (GPR.length() > 2) {
                System.out.println("error gpr! load");
            }
            String IDR = Integer.toBinaryString(this.idr);
            while (IDR.length() < 2) {
                IDR = "0" + IDR;
            }
            if (IDR.length() > 2) {
                System.out.println("error idr! load");
            }

            String IAD = Integer.toBinaryString(this.iad);
            while (IAD.length() < 1) {
                IAD = "0" + IAD;
            }
            if (IAD.length() > 1) {
                System.out.println("error iad! load");
            }
            String ADDRE = Integer.toBinaryString(this.address);
            while (ADDRE.length() < 5) {
                ADDRE = "0" + ADDRE;
            }
            if (ADDRE.length() > 5) {
                System.out.println("error address! load");
            }
            String temp = OPC + GPR + IDR + IAD + ADDRE;

            for (int i = 0; i < temp.length(); i++) {
                this.MEM[i] = (int) temp.charAt(i) - 48;
            }
            this.setup();
            return this.MEM;

        } else if ((this.opc >= 20 && this.opc <= 25)) {
            System.out.println("register to register load");
            String RX = Integer.toBinaryString(rx);
            while (RX.length() < 2) {
                RX = "0" + RX;
            }
            if (RX.length() > 2) {
                System.out.println("error RX! load");
            }
            String RY = Integer.toBinaryString(ry);
            while (RY.length() < 2) {
                RY = "0" + RY;
            }
            if (RY.length() > 2) {
                System.out.println("error RY! load");
            }
            String temp = OPC + RX + RY + "000000";

            for (int i = 0; i < temp.length(); i++) {
                this.MEM[i] = (int) temp.charAt(i) - 48;
            }
            this.setup();
            return this.MEM;

        } else if (this.opc >= 31 && this.opc <= 32) {
            System.out.println("shift and rotate instructions load");
            String R = Integer.toBinaryString(r);
            while (R.length() < 2) {
                R = "0" + R;
            }
            if (R.length() > 2) {
                System.out.println("error R!  shift and rotate load");
            }
            String AL = Integer.toBinaryString(this.al);
            while (AL.length() < 1) {
                AL = "0" + AL;
            }
            if (AL.length() > 1) {
                System.out.println("error AL! shift and rotate loadload");
            }
            String LR = Integer.toBinaryString(this.lr);
            while (LR.length() < 1) {
                LR = "0" + LR;
            }
            if (LR.length() > 1) {
                System.out.println("error LR! shift and rotate load");
            }
            String COUNT = Integer.toBinaryString(this.count);
            while (COUNT.length() < 4) {
                COUNT = "0" + COUNT;
            }
            if (COUNT.length() > 4) {
                System.out.println("error COUNT! shift and rotate load");
            }
            String temp = OPC + R + AL + LR + "00" + COUNT;

            for (int i = 0; i < temp.length(); i++) {
                this.MEM[i] = (int) temp.charAt(i) - 48;
            }
            this.setup();
            return this.MEM;

        } else if (this.opc >= 61 && this.opc <= 63) {
            System.out.println("I/O Operations load");

            String R = Integer.toBinaryString(r);
            while (R.length() < 2) {
                R = "0" + R;
            }
            if (R.length() > 2) {
                System.out.println("error R! I/O Operations load");
            }

            String DID = Integer.toBinaryString(this.did);
            while (DID.length() < 5) {
                DID = "0" + DID;
            }
            if (DID.length() > 5) {
                System.out.println("error device ID! I/O Operations load");
            }
            String temp = OPC + R + "000" + DID;

            for (int i = 0; i < temp.length(); i++) {
                this.MEM[i] = (int) temp.charAt(i) - 48;
            }
            this.setup();
            return this.MEM;

        } else {
            System.out.println("Illegal Operation Code load");
            return null;

        }
    }

//    public static void main(String args[]) {
//        memory tmp = new memory();
//        tmp.ini();
//        System.out.println(Arrays.toString(tmp.MEM));
//        int temp[] = { 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0 };
//        tmp.MEM=temp;
//        tmp.setup();
//        System.out.println(Arrays.toString(tmp.MEM));
//        tmp.load();
//        System.out.println(Arrays.toString(tmp.MEM));
//
//        System.out.println("test1");
//        System.out.println("***************");
//        System.out.println("***************");
//        System.out.println("***************");
//        memory test1 = new memory();
//        test1.opc=61;
//        test1.r= 2;
//        test1.did=12;
//        temp=test1.load();
//        System.out.println(Arrays.toString(temp));
//
//        System.out.println("test2");
//        System.out.println("***************");
//        System.out.println("***************");
//        System.out.println("***************");
//        memory test2 = new memory();
//        test2.opc = 31;
//        test2.r=3;
//        test2.al=1;
//        test2.lr=0;
//        test2.count= 12;
//        temp=test2.load();
//        System.out.println(Arrays.toString(temp));
//
//
//        System.out.println("test3");
//        System.out.println("***************");
//        System.out.println("***************");
//        System.out.println("***************");
//        memory test3 = new memory();
//        test3.rx=1;
//        test3.ry=5;
//        temp=test3.load();
//        System.out.println(Arrays.toString(temp));
//
//        System.out.println("test4");
//        System.out.println("***************");
//        System.out.println("***************");
//        System.out.println("***************");
//
//        memory test4 = new memory();
//        test4.opc= 42;
//        test4.r= 3;
//        test4.gpr=3;
//        test4.idr=2;
//        test4.iad=0;
//        test4.address=31;
//        temp=test4.load();
//        System.out.println(Arrays.toString(temp));
//
//
//        System.out.println("test5");
//        System.out.println("***************");
//        System.out.println("***************");
//        System.out.println("***************");
//        memory test5 = new memory();
//
//        test5.opc=36;
//        test5.trapcode=15;
//        temp=test5.load();
//        System.out.println(Arrays.toString(temp));
//
//
//        System.out.println("test6");
//        System.out.println("***************");
//        System.out.println("***************");
//        System.out.println("***************");
//        memory test6 = new memory();
//        test6.opc=0;
//        temp=test6.load();
//        System.out.println(Arrays.toString(temp));
//    }
}
