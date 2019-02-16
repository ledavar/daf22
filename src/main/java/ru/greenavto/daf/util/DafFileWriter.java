package ru.greenavto.daf.util;

import java.io.*;

public class DafFileWriter {


    public static void write(String s) {

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(new File("/home/ag/dev/DAF/jobs2.txt")))) {
            bw.write(s);
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

}
