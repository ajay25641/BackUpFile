package org.example;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;

public class BackUpFile {
    private String desPath="C:\\Users\\ajay1.kumar\\Desktop\\BackUp";
    public void backUpFile(File srcFile)  {
        File desDir=new File(desPath);

        try{
            FileUtils.copyFileToDirectory(srcFile, desDir, false);
            System.out.println("file added to backUp folder successfully");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
