package com.app.classproject.model;

public class Register {
    enum Type {
        GPR, // General purpose register
        IDX, // Index register
        PC, // Program counter
        CCR, // Condition code register
        IR, // Instruction register
        MAR, // Memory address register
        MBR, // Memory buffer register
        MFR // Machine fault register
    }

    private Type type;
    private int value[]; // Base 2 array

    public Register(Register.Type type) {
        this.type = type;

        int size = -1; // Unit is bits
        if(type == Register.Type.CCR || type == Register.Type.MFR) {
            size = 4;
        } else if(type == Register.Type.PC) {
            size = 12;
        } else if(type == Register.Type.GPR || type == Register.Type.IR || type == Register.Type.MAR || type == Register.Type.MBR || type == Register.Type.IDX) {
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

    /**
     * Input is base 10 integer
     */
    public void setValue(int value) {
        String binaryStr = Integer.toBinaryString(value);

        // Fill in values from end
        int i = binaryStr.length() - 1;
        int j;
        for(j = this.value.length - 1; j >= 0; j--) {
            this.value[j] = Integer.parseInt(Character.toString(binaryStr.charAt(i)));
            i--;
            if(i < 0) {
                break;
            }
        }

        // Fill beginning with 0's
        for(j = j - 1; j >= 0; j--) {
            this.value[j] = 0;
        }
    }

    /**
     * Covert value from base 2 array to base 10 integer and return
     */
    public int getBase10Value() {
        int base10Value = 0;
        int multiplier = 1;

        for(int i = value.length - 1; i >= 0; i--) {
            if(value[i] == 1) {
                base10Value += multiplier;
            }
            multiplier *= 2;
        }

        return base10Value;
    }
}
