package org.example;

import java.util.*;
import java.io.*;

public abstract class DirWatcher extends TimerTask {
    private String path;
    private File filesArray [];
    private HashMap<String,Long> dir;
    private HashMap<String,File>nameMapping;

        public DirWatcher(String path) {
        this.path = path;

        filesArray = new File(path).listFiles();
        dir=new HashMap<>();
        nameMapping=new HashMap<>();
        // transfer to the hashmap be used a reference and keep the
        // lastModfied value
        for(int i = 0; i < filesArray.length; i++) {
            dir.put(filesArray[i].getName(),filesArray[i].lastModified());
            nameMapping.put(filesArray[i].getName(),filesArray[i]);
        }
    }

    public final void run() {
        HashSet<File> checkedFiles = new HashSet<>();
        filesArray = new File(path).listFiles();
        //System.out.println(filesArray);
        // scan the files and check for modification/addition
        for(int i = 0; i < filesArray.length; i++) {

            Long current = dir.get(filesArray[i].getName());
            System.out.println(current);
            if(current!=null) checkedFiles.add(filesArray[i]);

            if (current == null) {
                // new file
                dir.put(filesArray[i].getName(),filesArray[i].lastModified());
                nameMapping.put(filesArray[i].getName(),filesArray[i]);
                onChange(filesArray[i], "add");
            }
            else if (current != filesArray[i].lastModified()){
                // modified file
                dir.put(filesArray[i].getName(), filesArray[i].lastModified());
                nameMapping.put(filesArray[i].getName(),filesArray[i]);
                onChange(filesArray[i], "modify");
            }
        }

       /* // now check for deleted files
        Set ref = dir.keySet();
        //ref.removeAll(checkedFiles);
        for(File file:checkedFiles) ref.remove(file.getName());

        Iterator it = ref.iterator();
        while (it.hasNext()) {
            File deletedFile = (String) it.next();
            dir.remove(deletedFile);
            onChange(deletedFile, "delete");
        }*/

        Set ref=dir.keySet();
        for(File file:checkedFiles) ref.remove(file.getName());

        Iterator itr=ref.iterator();
        while(itr.hasNext()){
            File deletedFiles=nameMapping.get((String)itr.next());
            dir.remove(deletedFiles.getName());
            nameMapping.remove(deletedFiles.getName());
            onChange(deletedFiles,"delete");
        }

    }

    protected abstract void onChange( File file, String action );
}
