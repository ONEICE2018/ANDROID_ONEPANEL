package com.oneice.onepanel.Manager;

import android.app.Application;
import android.os.Environment;

import com.oneice.onepanel.Fragments.OneLifeFragment;
import com.oneice.onepanel.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;

public class OneFileManager {
    public List<String> updataBinFiles           ;//存储当前updatabin路径下的bin文件的名字
    public FileOutputStream binFileWirter=null ;
    private String binFileBaceDirPath= Environment.getExternalStorageDirectory().getAbsolutePath()+ File.separator+"updatabin";
    File WirtingFile;//每个对象只能操作一个bin文件
    public OneFileManager(){
        File dirChaker = new File(binFileBaceDirPath+ File.separator+"添加updata文件夹.txt");
        // 文件夹不存在则创建
        if (!dirChaker.getParentFile().exists()) {
            dirChaker.getParentFile().mkdirs();
        }
        init();
    }
     public  String loadUpdataBinFilePath(String binnameNeedbin){
        return binFileBaceDirPath+File.separator+binnameNeedbin;
    }


    public  void init(){
        updataBinFiles=getBinInBasePath();
//        for(String bf:updataBinFiles)
//        {
//            System.out.println(bf);
//        }
    }
    //创建升级文件
    public void  createNewUpdataBin(String binNameNeedbin){
        File creater  =new File(binFileBaceDirPath+File.separator+binNameNeedbin);
        if (!creater.exists()) {
            try {
                creater.createNewFile();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }else{
            //删掉后重建
            creater.delete();
            createNewUpdataBin(binNameNeedbin);
        }
    }
    //获取文件 一般紧跟 createNewUpdataBin使用
public void getBinFileWirter(String binNameNeedbin){
            String back=binFileBaceDirPath+File.separator+binNameNeedbin;
            File chacker=new File(back);
            if (chacker.exists()) {
                try {
                    binFileWirter=new FileOutputStream(chacker);
                    //失能binlistview
                    MainActivity.mainActivity.remoteFragmentUpdata.disableUpdataBinlistView();
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }else {
                binFileWirter=null;
            }
    }
    public void writToBinfile(byte[] data,int start,int end ){
        if(binFileWirter!=null){
            try {
                binFileWirter.write(data,start,end);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    public void binWriterClose(){
        //使能binlistview
        MainActivity.mainActivity.remoteFragmentUpdata.enableUpdataBinlistView();
        if(binFileWirter!=null){
            try {
                binFileWirter.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    /**
     * 获取路径下的所有文件/文件夹
     *
     * @param directoryPath  需要遍历的文件夹路径
     * @param isAddDirectory 是否将子文件夹的路径也添加到list集合中
     * @return
     */
    public List<String> getAllbinFile(String directoryPath, boolean isAddDirectory) {
        List<String> list = new ArrayList<String>();
        File baseFile = new File(directoryPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            if (file.isDirectory()) {
                if (isAddDirectory) {
                    list.add(file.getAbsolutePath());
                }
                list.addAll(getAllbinFile(file.getAbsolutePath(), isAddDirectory));
            } else {
                String filename = file.getAbsolutePath();
                if (filename.endsWith(".bin")) {
                    list.add(filename);
                }
            }
        }
        return list;
    }
    public List<String> getBinInBasePath() {

        List<String> list = new ArrayList<String>();
        File baseFile = new File(binFileBaceDirPath);
        if (baseFile.isFile() || !baseFile.exists()) {
            return list;
        }
        File[] files = baseFile.listFiles();
        for (File file : files) {
            String filename = file.getAbsolutePath();
            if (filename.endsWith(".bin")) {
                list.add(file.getName());
            }
        }
        return list;
    }


}
