package org.example;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String args[]) {
        String path=Constants.sourceDirectory;
        TimerTask task = new DirWatcher(path ) {
            @Override
            protected void onChange(File file , String action) {

                System.out.println("File "+file.getName()+" action : "+action);

                if(action=="add" || action=="modify"){
                    if(file.isFile()){
                        BackUpFolder.backUpFile(file);
                    }
                    //update it later
                    /*else{
                        BackUpFolder.backUpDirectory(file.getAbsolutePath());
                    }*/
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule( task , new Date(), 1000 );
    }
}
