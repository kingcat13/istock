package com.kingzoo.kingcat.project.istock;

import java.io.*;

/**
 * Created by gonghongrui on 15/9/8.
 */
public class T {

    public static void main(String[] args) throws IOException {
        FileInputStream f = new FileInputStream("/Users/gonghongrui/Desktop/a.csv");
//        InputStreamReader f=new InputStreamReader(new FileInputStream("/Users/gonghongrui/Desktop/aa.csv"),"utf-8");
        BufferedReader dr=new BufferedReader(new InputStreamReader(f,"utf-8"));


        OutputStreamWriter osr=new OutputStreamWriter(new FileOutputStream("/Users/gonghongrui/Desktop/aa1.csv"),"utf-8");

        BufferedWriter bw = new BufferedWriter(osr);

        String line =  dr.readLine();
        while(line!= null){
            System.out.println(line);
            line = "\t"+line.replaceFirst(",","\t,");
            bw.newLine();
            bw.append(line);
            line = dr.readLine();
        }
        bw.flush();
        bw.close();
    }
}
