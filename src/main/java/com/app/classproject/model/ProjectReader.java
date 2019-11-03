package com.app.classproject.model;

import java.io.*;

public class ProjectReader {
    private static String fileName = "target.txt";
    private String paragraph;
    private int ind;

    public ProjectReader() {
        try {
            DataInputStream in = new DataInputStream(new FileInputStream(fileName));
            BufferedReader br  = new BufferedReader(new InputStreamReader(in));
            paragraph = " " + br.readLine() + " ";
        } catch (IOException e) {
            paragraph = " ";
        }
        ind  = 0;
    }

    public int readOneChar() {
        if (ind < paragraph.length()) {
            ind += 1;
            return (int)paragraph.charAt(ind - 1);
        } else {
            return 0;
        }
    }
}
