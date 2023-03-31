package org.example;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TimerTask;

public abstract class DirWatcher extends TimerTask {


    private String dirPath;
    private HashMap<File,Long>hm;
    private File fileArray[];
    public DirWatcher(String dirPath){

        this.dirPath=dirPath;

        hm=new HashMap<>();

        fileArray=new File(this.dirPath).listFiles();

        for(int i=0;i<fileArray.length;++i){
            File file=fileArray[i];
            hm.put(file,file.lastModified());
        }

        //we take backUp of all file present in current directory for the first time
        //when our application start
        backUpFileForFirstTime();
    }

    private void backUpFileForFirstTime() {
        BackUpFolder.backUpDirectory(dirPath);
    }

    @Override
    public void run() {
       //fileArray= Arrays.stream(new File(dirPath).listFiles()).filter(file->file.isFile())

       //checking if any file is added or modified
       checkForAddOrModify(fileArray);

       //checking if any file is deleted from current directory
       checkForDelete(fileArray);

    }
    private void checkForAddOrModify(File[] fileArray){

        for(File file:fileArray){

            //lastTime is used to store lastModified time for current file
            Long lastTime=hm.containsKey(file)?hm.get(file):null;


            if(lastTime == null){
                //newly added file
                hm.put(file,file.lastModified());
                //System.out.println(file+ " this file is newly added");
                onChange(file,"add");
            }
            else if(lastTime != file.lastModified()){
                //this file is modified
                hm.put(file, file.lastModified());
                //System.out.println(file+" this file is modified");
                onChange(file,"modify");
            }
        }
    }

    private void checkForDelete(File[] fileArray){

        //for storing all file present in hashMap
        HashSet<File> fileFromHm=new HashSet<>();

        //adding file to hashset from hashmap
        //i.e all file which is present in previous check
        for(File file:hm.keySet()){
            fileFromHm.add(file);
        }

        //removing all file which is present in previous check and current check both
        for(File file:fileArray){
            fileFromHm.remove(file);
        }

        //if fileFromHm is not empty
        //it means some of the file which is present in previous check
        //is now not present in current check
        //i.e that particular file is deleted
        for(File file : fileFromHm){
            onChange(file,"delete");

            //we have to remove it from hashmap also
            //as the file is now deleted
            hm.remove(file);
        }
    }
    protected abstract void onChange(File file,String action);
}
