package com.zxzq.pocket_reader.manager;

import com.zxzq.pocket_reader.filter.MyFilter;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by Administrator on 2017/3/2 0002.
 */
public class FileManager {


    private ArrayList<String> mTXTpath = new ArrayList<>();
    private ArrayList<String> mTXTName=new ArrayList<>();
    public ArrayList<String> getTXTpath(File file, MyFilter myFilter) {
        getTXTFilePath(file,myFilter);
        return mTXTpath;
    }

    public void setTXTpath(ArrayList<String> TXTpath) {

        mTXTpath = TXTpath;
    }

    public ArrayList<String> getTXTName(File file,MyFilter myFilter) {
        getTXTFileName(file,myFilter);
        return mTXTName;
    }

    public void setTXTName(ArrayList<String> TXTName) {
        mTXTName = TXTName;
    }

    public void getTXTFileName(File file, MyFilter myFilter) {
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (myFilter.accept(file1)) {
                mTXTName.add(file1.getName());
            } else if (file1.isDirectory()) {
                getTXTFileName(file1, myFilter);
            }
        }
    }
    public void getTXTFilePath(File file, MyFilter myFilter) {
        File[] files = file.listFiles();
        for (File file1 : files) {
            if (myFilter.accept(file1)) {
                mTXTpath.add(file1.getAbsolutePath());
            } else if (file1.isDirectory()) {
                getTXTFilePath(file1, myFilter);
            }
        }
    }
}
