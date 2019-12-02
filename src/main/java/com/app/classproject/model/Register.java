package com.app.classproject.model;

import javafx.util.Pair;

public class Register {
    enum Type {
        GPR, // General purpose register
        IDX, // Index register
        PC, // Program counter
        CCR, // Condition code register
        IR, // Instruction register
        MAR, // Memory address register
        MBR, // Memory buffer register
        MFR, // Machine fault register
        TCR, // Trap code register
        FR   //Floating point register
    }

    private Type type;
    private int value[]; // Base 2 array

    public Register(Register.Type type) {
        this.type = type;

        
        int size = -1; // Unit is bits
        if (type == Register.Type.CCR) {
            size = 1;
        } else if (type == Register.Type.MFR || type == Register.Type.TCR) {
            size = 4;
        } else if (type == Register.Type.PC) {
            size = 12;
        } else if (type == Register.Type.GPR || type == Register.Type.IR || type == Register.Type.MAR || type == Register.Type.MBR 
                   || type == Register.Type.IDX || type == Register.Type.FR) {
            size = 16;
        } else {
            System.out.println("Unknown register type: " + type);
        }

        value = new int[size];
    }

    public int[] getValue() {
        return value;
    }

    /**
     * Input is base 2 array
     */
    public void setValue(int[] value) {
        System.arraycopy(value, 0, this.value, 0, this.value.length);
    }
    
    public void setErr(int val, int pos) {
    	if(val >= 0 && val <= 1 && pos >=0 && pos < 4){
    		this.value[pos]=val;
    	}
        
    }
    /**
     * Input is base 10 integer
     */
    public void setValue(int value) {
        String binaryStr = Integer.toBinaryString(value);

        // Fill in values from end
        int i = binaryStr.length() - 1;
        int j;
        for (j = this.value.length - 1; j >= 0; j--) {
            this.value[j] = Integer.parseInt(Character.toString(binaryStr.charAt(i)));
            i--;
            if (i < 0) {
                break;
            }
        }

        // Fill beginning with 0's
        for (j = j - 1; j >= 0; j--) {
            this.value[j] = 0;
        }
    }

    /**
     * Covert value from base 2 array to base 10 integer and return
     */
    public int getBase10Value() {
        int base10Value = 0;
        int multiplier = 1;

        for (int i = value.length - 1; i >= 0; i--) {
            if (value[i] == 1) {
                base10Value += multiplier;
            }
            multiplier *= 2;
        }

        return base10Value;
    }

    public double calflo() {
        if (this.type == Type.FR) {
            int sign, exp, man;
            sign = value[0];
            StringBuilder builder = new StringBuilder();
            for (int i = 1; i < 8; i++) {
                builder.append(value[i]);
            }
            exp = Integer.parseInt(builder.toString(), 2);
            builder = new StringBuilder();
            for (int i = 8; i < 16; i++) {
                builder.append(value[i]);
            }
            man = Integer.parseInt(builder.toString(), 2);
            return (double) (1 - 2 * sign) * Math.pow(2, exp - 63) * (1.0 + (double) man / 256);
        } else {
            return 0;
        }
    }

    public void calem(double fval) {
        int sign = 0;
        if (fval < 0) {
            sign = 1;
        }

        int ing = (int) (Math.abs(fval));
        double fac = (Math.abs(fval)) - ing;

        String MAN = Integer.toBinaryString(ing);
        System.out.println("MAN " + MAN);

        int len = MAN.length() - 1;
        int bias = 63;
        int exp = len + bias;

        while ((MAN.length() < 18) && (fac != 0)) {
            fac *= 2;
            int x = (int) fac;
            fac -= x;
            MAN = MAN + x;
            if (ing == 0) {
                exp--;
            }
        }

        while (MAN.charAt(0) != '1') {
            MAN = MAN.substring(1);
        }
        MAN = MAN.substring(1);

        while (MAN.length() < 8) {
            MAN = MAN + "0";
        }
        if (MAN.length() > 8) {
            MAN = MAN.substring(0, 8);
        }
        String EXP = Integer.toBinaryString(exp);
        while (EXP.length() < 7) {
            EXP = "0" + EXP;
        }
        if (EXP.length() > 7) {
            EXP = EXP.substring(0, 7);
        }
        String temp = Integer.toString(sign) + EXP + MAN;

        for (int i = 0; i < temp.length(); i++) {
            this.value[i] = (int) temp.charAt(i) - 48;
        }
    }
}
