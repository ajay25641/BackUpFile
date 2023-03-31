package org.example;

import org.apache.commons.io.FileUtils;

import java.io.File;

public class BackUpFolder {
    private static String desPath=Constants.destinationDirectory;
    public static void backUpFile(File srcFile)  {
        File desDir=new File(desPath);

        try{
            FileUtils.copyFileToDirectory(srcFile, desDir, false);
            System.out.println("file added to backUp folder successfully");
        }
        catch (Exception e){
            System.out.println(e);
        }

    }

    public static void backUpDirectory(String dirPath) {

        File srcDir=new File(dirPath);
        File desDir=new File(desPath);

        try {
            FileUtils.copyDirectory(srcDir,desDir);
            System.out.println("Current directory is backed up successfully at location "+desDir.getAbsolutePath());
        }
        catch (Exception e){
            System.out.println(e);
        }

    }
}
