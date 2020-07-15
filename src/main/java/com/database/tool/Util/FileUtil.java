package com.database.tool.Util;

import java.io.File;

public class FileUtil {

    public static boolean initDirectory(String path) {
        boolean flag = true;
        File f = new File(path);
        if (!f.exists()) {
            flag = f.mkdirs(); //Create a directory
        }
        return flag;
    }
}
