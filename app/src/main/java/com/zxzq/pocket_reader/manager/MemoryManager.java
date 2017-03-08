package com.zxzq.pocket_reader.manager;

import android.app.ActivityManager;
import android.content.Context;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

/**
 * Created by Administrator on 2016/12/27 0027.
 */
public class MemoryManager {
    /**
     * 获取手机内置SD卡路径
     *
     * @return
     */
    public static String getPhoneInSDCardPath() {
        String sdcardState = Environment.getExternalStorageState();
        if (!sdcardState.equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        return Environment.getExternalStorageDirectory().getAbsolutePath();
    }

    /**
     * 获取手机外置SD卡路径
     *
     * @return
     */
    public static String getPhoneOutSDCardPath() {
        Map<String, String> map = System.getenv();
        if (map.containsKey("SECONDARY_STORAGE")) {
            String paths = map.get("SECONDARY_STORAGE");
            String[] path = paths.split(":");
            if (path == null || path.length <= 0) {
                return null;
            }
            return path[0];
        }
        return null;
    }

    /**
     * 获取手机外置SD卡大小
     *
     * @param context
     * @return
     */

    public static long getPhoneOutSDCardSize(Context context) {
        try {
            File path = new File(getPhoneOutSDCardPath());
            if (path == null) {
                return 0l;
            }
            StatFs statFs = new StatFs(path.getPath());
            long blockSize = statFs.getBlockSize();
            long blockCountLong = statFs.getBlockCount();
            return blockCountLong * blockSize;
        } catch (Exception e) {
            Toast.makeText(context, "外置存储卡异常", Toast.LENGTH_SHORT).show();
            return 0l;
        }
    }

    /**
     * 获取手机内置SD卡大小
     *
     * @return
     */
    public static long getPhoneInSDCardSize() {
        File file = new File(getPhoneInSDCardPath());
        if(file==null){
            return 0l;
        }else {
            StatFs statFs = new StatFs(file.getPath());
            long blockCountLong = statFs.getBlockCount();
            long blockSizeLong = statFs.getBlockSize();
            return blockCountLong * blockSizeLong;
        }
    }

    /**
     * 获取手机内置SD卡空余大小
     *
     * @return
     */
    public static long getPhoneInSDCardFreeSize() {
        File file = new File(getPhoneInSDCardPath());
        StatFs statFs = new StatFs(file.getPath());
        long blockSize = statFs.getBlockSize();
        long availableBlocks = statFs.getAvailableBlocks();
        return blockSize * availableBlocks;
    }

    /**
     * 获取手机外置SD卡空余大小
     *
     * @return
     */
    public static long getPhoneOutSDCardFreeSize(Context context) {
        try {
            File file = new File(getPhoneOutSDCardPath());
            if (file == null) {
                return 0l;
            }
            StatFs statFs = new StatFs(file.getPath());
            long blockSize = statFs.getBlockSize();
            long availableBlocks = statFs.getAvailableBlocks();
            return blockSize * availableBlocks;
        } catch (Exception e) {
            Toast.makeText(context, "外置SD卡异常", Toast.LENGTH_SHORT).show();
            return 0l;
        }
    }

    /**
     * 手机本身总存储大小
     * @return
     */
    public static long getPhoneSelfAllSize(){
        File path = new File(getPhoneInSDCardPath());
        StatFs statFs = new StatFs(path.getPath());
        long blockSize = statFs.getBlockSize();
        long blockCount = statFs.getBlockCount();
        long SDCardSize=blockSize*blockCount;
        path =Environment.getRootDirectory();
        statFs=new StatFs(path.getPath());
        blockSize=statFs.getBlockSize();
        blockCount=statFs.getBlockCount();
        long rootSize=blockCount*blockSize;
        path=Environment.getDownloadCacheDirectory();
        statFs=new StatFs(path.getPath());
        blockCount=statFs.getBlockCount();
        blockSize=statFs.getBlockSize();
        long downLoadSize=blockSize*blockCount;
        path=Environment.getDataDirectory();
        statFs=new StatFs(path.getPath());
        blockCount=statFs.getBlockCount();
        blockSize=statFs.getBlockSize();
        long dataSize=blockSize*blockCount;
        return SDCardSize+rootSize+downLoadSize+dataSize;
    }

    /**
     * 手机本身总空闲大小
     * @return
     */
    public static long getPhoneSelfFreeAllSize(){
        File path = new File(getPhoneInSDCardPath());
        StatFs statFs = new StatFs(path.getPath());
        long blockSize = statFs.getBlockSize();
        long availableBlocks = statFs.getAvailableBlocks();
        long SDCardSize=blockSize*availableBlocks;
        path =Environment.getRootDirectory();
        statFs=new StatFs(path.getPath());
        blockSize=statFs.getBlockSize();
        availableBlocks=statFs.getAvailableBlocks();
        long rootSize=availableBlocks*blockSize;
        path=Environment.getDownloadCacheDirectory();
        statFs=new StatFs(path.getPath());
        availableBlocks=statFs.getAvailableBlocks();
        blockSize=statFs.getBlockSize();
        long downLoadSize=blockSize*availableBlocks;
        path=Environment.getDataDirectory();
        statFs=new StatFs(path.getPath());
        blockSize=statFs.getBlockSize();
        availableBlocks=statFs.getAvailableBlocks();
        long dataSize=blockSize*availableBlocks;
        return SDCardSize+rootSize+downLoadSize+dataSize;
    }

    /**
     * 获取手机自身空间大小
     * @return
     */
    public static long getPhoneSelfSize(){
        File path =Environment.getRootDirectory();
        StatFs statFs=new StatFs(path.getPath());
        long blockSize=statFs.getBlockSize();
        long blockCount = statFs.getBlockCount();
        long rootSize=blockSize*blockCount;
        path=Environment.getDownloadCacheDirectory();
        statFs=new StatFs(path.getPath());
        blockCount=statFs.getBlockCount();
        blockSize=statFs.getBlockSize();
        long downLoadSize=blockSize*blockCount;
        path=Environment.getDataDirectory();
        statFs=new StatFs(path.getPath());
        blockCount=statFs.getBlockCount();
        blockSize=statFs.getBlockSize();
        long dataSize=blockSize*blockCount;
        long aa=dataSize+downLoadSize+rootSize;
        return rootSize+downLoadSize+dataSize;
    }
    public static long getPhoneSelfFreeSize(){
        File path = Environment.getRootDirectory();
        StatFs statFs = new StatFs(path.getPath());
        int blockSize = statFs.getBlockSize();
        int availableBlocks = statFs.getAvailableBlocks();
        long rootFreeSize=blockSize*availableBlocks;
        path=Environment.getDownloadCacheDirectory();
        statFs=new StatFs(path.getPath());
        blockSize=statFs.getBlockSize();
        availableBlocks=statFs.getAvailableBlocks();
        long downLoadSize=blockSize*availableBlocks;
        path=Environment.getDataDirectory();
        statFs=new StatFs(path.getPath());
        blockSize=statFs.getBlockSize();
        availableBlocks=statFs.getAvailableBlocks();
        long dataSize=blockSize*availableBlocks;
        return rootFreeSize+downLoadSize+dataSize;
    }
    /**
     * 获取手机内存总容量
     */
    public static double getPhoneMemoryTotalSize(){
        BufferedReader stream =null;
        try {
             stream=new BufferedReader(new FileReader("/proc/meminfo"));
            String s = stream.readLine();
            String[] split = s.split("\\s+");
            return Double.valueOf(split[1])/1024/1024;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {

            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    /**
     * 获取手机内存空余容量
     * @param context
     * @return
     */
    public static double getPhoneMemoryFreeSize(Context context){
        ActivityManager.MemoryInfo info = new ActivityManager.MemoryInfo();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        am.getMemoryInfo(info);
        return (double)info.availMem/1024/1024/1024;
    }

}
