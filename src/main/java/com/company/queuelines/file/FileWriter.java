package com.company.queuelines.file;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class FileWriter {

    public static void writeLine(String fileName, String line) throws IOException {
        PrintWriter out = null;
        try {
            out = new PrintWriter(new BufferedWriter(new java.io.FileWriter(fileName, true)));
            out.println(line);
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

}
