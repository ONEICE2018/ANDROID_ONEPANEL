package com.oneice.onepanel.Manager;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.oneice.onepanel.MainActivity;
import com.oneice.onepanel.onetools.ConvertCode;
import com.oneice.onepanel.remoteFragments.RemoteFragmentUpdata;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class RemotManager {

    public RemotManager(){
        this.remote_updata_bin=new ArrayList<String>();
    }

    public boolean isRemoteConnected() {
        return remoteConnected;
    }

    public void setRemoteConnected(boolean remoteConnected) {
        this.remoteConnected = remoteConnected;
    }

    private boolean remoteConnected=false;

    public String getRemoteIP() {
        return remoteIP;
    }
    /*
     * 解析远程服务器指令
     * */
    public void analysis_remote(byte[] data,int flen)
    {
        //第一步反馈：升级bin文件数量
        String strdata=new String(data);
        if(strdata.contains("pull:bin:size:")&&strdata.endsWith(";")){
            strdata = ConvertCode.getSubString(strdata, "pull:bin:size:");
            strdata = ConvertCode.getSubString(strdata, ";");
            if (strdata == null || strdata == "") {
                return;
            }
            int num = Integer.valueOf(strdata);
            if(num==0)
            {
                MainActivity.showmsgs("no updata bin file");
            }else
            {
                //bin获取第二步:请求拿到第一个数据，清空列表，记录总数量，置位拉取编号标志位。

                MainActivity.mainActivity.remoteFragmentUpdata.refrashinterface("remoteFragmentUpdata:upingBinList;");
                pullbinnum=num;
                getPullbinnumflag=0;
                String str = "pull:bin:num:"+getPullbinnumflag+";";
                MainActivity.mainActivity.senddatas(ConvertCode.string2HexString(str));
                remote_updata_bin.clear();
            }
        }else
        if (strdata.contains("pull:bin:num:") && strdata.endsWith(";")) {
            if(strdata=="pull:bin:num:erro:overarea;"){
                MainActivity.showmsgs(strdata);
                return;
            }

            strdata = ConvertCode.getSubString(strdata, "pull:bin:num:");
            strdata = ConvertCode.getSubString(strdata, ";");
            if(strdata.split(":").length!=2) {
                return;
            }
            String[] binNumName=strdata.split(":");
            if (binNumName[0] == null || binNumName[0] == "") {
                return;
            }
            int num = Integer.valueOf(binNumName[0]);
            if (num == getPullbinnumflag) {
                remote_updata_bin.add(binNumName[1]);
                System.out.println("binlist get binname:"+binNumName[1]+";");
                getPullbinnumflag++;
                MainActivity.mainActivity.remoteFragmentUpdata.refrashinterface("remoteFragmentUpdata:upingPro:"+(int)(((getPullbinnumflag*1.0)/pullbinnum)*100)+";");
                if(getPullbinnumflag<pullbinnum){
                    String str = "pull:bin:num:"+getPullbinnumflag+";";
                    MainActivity.mainActivity.senddatas(ConvertCode.string2HexString(str));
                }else{
                    MainActivity.mainActivity.remoteFragmentUpdata.refrashinterface("remoteFragmentUpdata:RefUpdataBinList;");
                    System.out.println("binlist getover num="+remote_updata_bin.size()+";");
                }
            } else {
                MainActivity.showmsgs("erro:bin data dislocation");
            }
        }else

            //下载bin
        if (strdata.contains("Download:Bin:") && strdata.endsWith(":Start;")) {
            strdata = ConvertCode.getSubString(strdata, "Download:Bin:");
            strdata = ConvertCode.getSubString(strdata, ":Start;");
            //Download:Bin:" + binname + ":Start;
            if (strdata == null || strdata == "") {
                MainActivity.showmsgs("Download:bin:erro:contextnull;");
                MainActivity.mainActivity.senddatas(ConvertCode.string2HexString("DownLoad:Erro_CloseBinfile;"));
                DownLoadBinOver();
                return;
            }
            if(strdata.equals(RemoteFragmentUpdata.downloadBinFileName))
            {
               //查看是否文件以经存在
                   MainActivity.mainActivity.oneFileManager.init();
                  if(!MainActivity.mainActivity.remoteFragmentUpdata.bindownloadhasbeenstart&& MainActivity.mainActivity.oneFileManager.updataBinFiles.contains(RemoteFragmentUpdata.downloadBinFileName)) {
                       MainActivity.mainActivity.remoteFragmentUpdata.bindownloadhasbeenstart=true;
                       MainActivity.showmsgs("Download:Dialog:RemakeFile:Show;");//让mainactivity显示
                  }else{
                      MainActivity.mainActivity.oneFileManager.createNewUpdataBin(RemoteFragmentUpdata.downloadBinFileName);//创建文件
                      MainActivity.mainActivity.oneFileManager.getBinFileWirter(RemoteFragmentUpdata.downloadBinFileName)  ;//获取传输通道
                      MainActivity.mainActivity.senddatas(ConvertCode.string2HexString("Download:NextBin:OKStart;"));//bin文件下载1：开始下载
                      MainActivity.mainActivity.remoteFragmentUpdata.setFileTLen(0);
                  }
            }else
                {
                    MainActivity.showmsgs("DownloadbinNamefild");
                    MainActivity.mainActivity.senddatas(ConvertCode.string2HexString("DownLoad:Erro_CloseBinfile;"));
                    DownLoadBinOver();
                }
        }else
            if(strdata.equals("Download:bin:erro:namenull;")||
                    strdata.equals("Download:bin:erro:noSuchFile")||
                    strdata.equals("Download:bin:erro:contextnull;")||
                    strdata.equals("Download:bin:erro:contextERRO;")
            ) {
                MainActivity.showmsgs(strdata);
                DownLoadBinOver();
            }
         else if(strdata.contains("Dowload:BinFream:")&&strdata.endsWith(";"))  {
                strdata = ConvertCode.getSubString(strdata, "Dowload:BinFream:");
                strdata = ConvertCode.getSubString(strdata, ";");
                if (strdata == null || strdata == "") {
                    MainActivity.showmsgs("Download:bin:erro:contextnull;");
                    MainActivity.mainActivity.senddatas(ConvertCode.string2HexString("DownLoad:Erro_CloseBinfile;"));
                    DownLoadBinOver();
                    return;
                }
                byte[] getdata=ConvertCode.hexString2Bytes(strdata);

                MainActivity.mainActivity.remoteFragmentUpdata.setFileTLen(
                        MainActivity.mainActivity.remoteFragmentUpdata.getFileTLen()+ getdata.length-2
                );
                MainActivity.mainActivity.oneFileManager.writToBinfile(getdata,2,getdata.length-2);
                if(getdata.length>2){//先把进度拿出来
                    int pro=getdata[0]<<8|getdata[1];
                    MainActivity.mainActivity.remoteFragmentUpdata.refrashinterface("remoteFragmentUpdata:upingPro:"+pro+";");
                }
                String SavebinStrng=ConvertCode.bytes2HexString(getdata,2,getdata.length);

                //bin文件下载2：把存入的每一帧数据再取出来反馈到服务器寻求下一帧数据
                String backstr=SavebinStrng;
                if(MainActivity.mainActivity.remoteFragmentUpdata.needreDownLoad){
                    MainActivity.mainActivity.remoteFragmentUpdata.needreDownLoad=false;
                }
                MainActivity.mainActivity.senddatas(ConvertCode.string2HexString("Download:NextBin:"+backstr+";"));
            }
         else  if(strdata.contains("Dowload:BinOver:")&&strdata.endsWith(";"))  {
                strdata = ConvertCode.getSubString(strdata, "Dowload:BinOver:");
                strdata = ConvertCode.getSubString(strdata, ";");
                if (strdata == null || strdata == "") {
                    MainActivity.showmsgs("Download:bin:erro:contextnull;");
                    MainActivity.mainActivity.senddatas(ConvertCode.string2HexString("DownLoad:Erro_CloseBinfile;"));
                    DownLoadBinOver();
                    return;
                }
                int flean =Integer.valueOf(strdata);
                if( MainActivity.mainActivity.remoteFragmentUpdata.getFileTLen()==flean)
                {
                    MainActivity.showmsgs("Download:bin:Over:FileLen:"+flean+";");

                }else{


                }
                DownLoadBinOver();
            }

    }
    void DownLoadBinOver(){
         MainActivity.mainActivity.oneFileManager.binWriterClose();
        MainActivity.mainActivity.oneFileManager.init();
         MainActivity.showmsgs("remoteFragmentUpdata;");
         MainActivity.mainActivity.remoteFragmentUpdata.refrashinterface("remoteFragmentUpdata:upingPro:"+0+";");
    }
    private String remoteIP="120.79.56.190";//我的远程服务器地址
    //private String remoteIP="192.168.1.100";
    public List<String> remote_updata_bin;
    private int pullbinnum=0,getPullbinnumflag=0;
}
