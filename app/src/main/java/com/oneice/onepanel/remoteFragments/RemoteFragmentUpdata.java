package com.oneice.onepanel.remoteFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.oneice.onepanel.Fragments.OneLifeFragment;
import com.oneice.onepanel.MainActivity;
import com.oneice.onepanel.R;
import com.oneice.onepanel.activity_communication.RefrashFregments;
import com.oneice.onepanel.onetools.ConvertCode;

import java.util.ArrayList;
import java.util.List;

public class RemoteFragmentUpdata extends OneLifeFragment implements RefrashFregments {
    //远程同步
    Button remotebinlistget;
    ListView remotebin_list;
    RemoteListViewAdapter binlistadapter;
    ProgressBar progress_remotbinlistget;
    EditText editfindbinfile;

    //bin文件下载
   public static String downloadBinFileName="";

    public int getFileTLen() {
        return fileTLen;
    }

    public void setFileTLen(int fileTLen) {
        this.fileTLen = fileTLen;
    }

    private  int fileTLen=0;
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        View view=inflater .inflate(R.layout .remote_fragment_updata ,container,false) ;
        refrashinit(view);
        return view;
    }

    @Override
    public void refrashinterface(String refname) {
        if(refname=="remoteFragmentUpdata:upingBinList;")
        {//正在从服务器拉取列表 不可操作
            //remotebin_list.setVisibility(View.GONE);
            this.progress_remotbinlistget.setProgress(0);
        }
        if(refname=="remoteFragmentUpdata:RefUpdataBinList;"){//刷新bin列表

            //binlistadapter.notifyDataSetChanged();
            this.progress_remotbinlistget.setProgress(0);
           // remotebin_list.setVisibility(View.VISIBLE);

          MainActivity.showmsgs("remoteFragmentUpdata;");



        }
        if(refname.contains("remoteFragmentUpdata:upingPro:")&&refname.endsWith(";"))
        {
            refname = ConvertCode.getSubString(refname, "remoteFragmentUpdata:upingPro:");
            refname = ConvertCode.getSubString(refname, ";");
            if (refname == null || refname == "") {
                return;
            }
            int num = Integer.valueOf(refname);
            this.progress_remotbinlistget.setProgress(num);
        }

    }

    @Override
    public void refrashinit(View view) {
        editfindbinfile=(EditText)view.findViewById(R.id.editfindbinfile) ;
        progress_remotbinlistget=view.findViewById(R.id.progress_remotbinlistget);
        remotebin_list=(ListView)view.findViewById(R.id.remotebin_list);
        binlistadapter = new RemoteListViewAdapter(view.getContext(), R.layout.item_remoteupdatabinlits, MainActivity.mainActivity.remotManager.remote_updata_bin);
        remotebin_list.setAdapter(binlistadapter);
        remotebin_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                        downloadBinFileName=MainActivity.mainActivity.remotManager.remote_updata_bin.get(position);
//                       MainActivity.mainActivity.senddatas(ConvertCode.string2HexString("Download:bin:"+downloadBinFileName+";"));
                if(MainActivity.mainActivity.tcpclient.getIP().equals(MainActivity.mainActivity.remotManager.getRemoteIP())) {
                    MainActivity.mainActivity.oneFileManager.init();//重装文件列表
                    if(MainActivity.mainActivity.oneFileManager.updataBinFiles.contains(RemoteFragmentUpdata.downloadBinFileName)) {
                         //代码块 msgbox弹出
                            //创建一个 AlertDialog.Builder 对象
                            AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                            //给对话框添加title
                            builder.setTitle("警告！");
                            //给对话框添加内容
                            builder.setMessage("文件 " + RemoteFragmentUpdata.downloadBinFileName + " 已存在是否覆盖？");
                            builder.setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    upDataBinDownloadStart();
                                }
                            });
                            builder.setPositiveButton("NO", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    MainActivity.mainActivity.remoteFragmentUpdata.needreDownLoad = false;
                                }
                            });
                            //切记勿忘~开启dialog
                            builder.show();
                        }else{
                            upDataBinDownloadStart();
                        }
                    }

                }


        });
        remotebinlistget=(Button)view.findViewById(R.id.remote_bin);
        remotebinlistget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //远程remotebin获取

                //拉取bin列表第一步
            if(MainActivity.mainActivity.tcpclient.getIP().equals(MainActivity.mainActivity.remotManager.getRemoteIP())) {
                String str = "pull:bin:getbinlist;";
                MainActivity.mainActivity.senddatas(ConvertCode.string2HexString(str));
             }else{
                MainActivity.showmsgs("无法获取远程列表，因为没有连接远程服务器");

            }
            }
            });

        editfindbinfile.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {



            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
               // binlistadapter.getFilter().filter(editfindbinfile.getText().toString());


                for(int i =0;i<MainActivity.mainActivity.remotManager.remote_updata_bin.size();i++) {

                    String fileter=MainActivity.mainActivity.remotManager.remote_updata_bin.get(i);
                    if (!fileter.startsWith(editfindbinfile.getText().toString())) {
                       remotebin_list.setSelection(i);

                    }
                }

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
       }
    public static boolean needreDownLoad=false;
    public static boolean bindownloadhasbeenstart=false;
    public void upDataBinDownloadStart(){
        MainActivity.mainActivity.remoteFragmentUpdata.needreDownLoad=true;
        MainActivity.mainActivity.remoteFragmentUpdata.setFileTLen(0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                bindownloadhasbeenstart=false;
                int outtimeflag=0;
                while(MainActivity.mainActivity.remoteFragmentUpdata.needreDownLoad&&outtimeflag<4){
                    MainActivity.mainActivity.senddatas(ConvertCode.string2HexString("Download:bin:"+downloadBinFileName+";"));
                    try {
                        Thread.sleep(3000);//2S内没一次成功的bin解析则继续发送
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    outtimeflag++;
                }
                if(MainActivity.mainActivity.remoteFragmentUpdata.needreDownLoad&&outtimeflag>=4){
                    MainActivity.mainActivity.oneFileManager.binWriterClose();//如果超时了就关闭文件流资源
                }
            }
        }).start();
    }
    public void disableUpdataBinlistView(){
        remotebin_list.setVisibility(View.INVISIBLE);
    }
    public void enableUpdataBinlistView(){
        MainActivity.showmsgs("remoteFragmentUpdata;");
    }


}
