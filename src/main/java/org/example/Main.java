package org.example;

import java.io.File;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static void main(String args[]) {
        String path="C:\\Users\\ajay1.kumar\\Desktop\\Test";
        TimerTask task = new DirWatcher(path ) {
            protected void onChange(File file, String action )  {
                // here we code the action on a change
                System.out.println
                        ( "File "+ file.getName() +" action: " + action );

                if(action=="add" || action=="modify"){
                    new BackUpFile().backUpFile(file);
                }
            }
        };

        Timer timer = new Timer();
        timer.schedule( task , new Date(), 1000 );
    }
}