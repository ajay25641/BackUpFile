package org.example;

import java.util.*;
import java.io.*;

public abstract class DirWatcher extends TimerTask {
    private String path;
    private File filesArray [];
    private HashMap<File,Long> dir;


    /*public DirWatcher(String path) {
        this.path=path;
    }*/

    public DirWatcher(String path) {
        this.path = path;

        filesArray = new File(path).listFiles();
        dir=new HashMap<>();
        // transfer to the hashmap be used a reference and keep the
        // lastModfied value
        for(int i = 0; i < filesArray.length; i++) {
            dir.put(filesArray[i],filesArray[i].lastModified());
        }
    }

    public final void run() {
        HashSet<File> checkedFiles = new HashSet<>();
        filesArray = new File(path).listFiles();
        System.out.println(filesArray);
        // scan the files and check for modification/addition
        for(int i = 0; i < filesArray.length; i++) {

            Long current = dir.get(filesArray[i]);
            System.out.println(current);
            if(current!=null) checkedFiles.add(filesArray[i]);

            if (current == null) {
                // new file
                dir.put(filesArray[i],filesArray[i].lastModified());
                onChange(filesArray[i], "add");
            }
            else if (current != filesArray[i].lastModified()){
                // modified file
                dir.put(filesArray[i], filesArray[i].lastModified());
                onChange(filesArray[i], "modify");
            }
        }

        // now check for deleted files
        Set ref = dir.keySet();
        ref.removeAll(checkedFiles);

        Iterator it = ref.iterator();
        while (it.hasNext()) {
            File deletedFile = (File)it.next();
            dir.remove(deletedFile);
            onChange(deletedFile, "delete");
        }
    }

    protected abstract void onChange( File file, String action );
}
