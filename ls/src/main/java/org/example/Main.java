package org.example;

import java.io.File;
import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {

        Scanner sc =new Scanner(System.in);

        System.out.println("Please enter the full path : ");
        String path = sc.nextLine();

        File directory = new File(path);

        if(!directory.exists() || !directory.isDirectory()){
            System.out.println("Path does not exist or is not a directory");
        }

        File[] files =  directory.listFiles();

        if (files == null) {
            System.out.println("Empty Directory ");
        }
        else {
            for (File file : files){

                String type = file.isDirectory() ? "<DIR>" : "<FILE>";
                String read = file.canRead() ? "r" : "-";
                String write = file.canWrite() ? "w" : "-";
                String hidden = file.isHidden() ? "h" : "-" ;

                System.out.println(
                        file.getAbsolutePath() + " " + type + " " + read + write + hidden
                );

            }
        }

    }
}