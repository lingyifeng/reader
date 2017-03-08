package com.zxzq.pocket_reader.filter;

import java.io.File;
import java.io.FileFilter;

/**
 * Created by Administrator on 2017/1/6 0006.
 */
public class MyFilter implements FileFilter {
    private String[] str;

    public MyFilter(String... str) {
        this.str = str;
    }

    @Override
    public boolean accept(File file) {
        for(int i=0;i<str.length;i++){
            if(file.getName().endsWith(str[i])){
                return true;
            }
        }
        return false;
    }
}
