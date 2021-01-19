package com.oneice.onepanel.remoteFragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import com.oneice.onepanel.Fragments.OneLifeFragment;
import com.oneice.onepanel.MainActivity;
import com.oneice.onepanel.R;
import com.oneice.onepanel.activity_communication.RefrashFregments;
import com.oneice.onepanel.onetools.ConvertCode;

public class RemoteFragmentUpdata extends OneLifeFragment implements RefrashFregments {
    //远程同步
    Button remotebinlistget;
    ListView remotebin_list;
    ArrayAdapter<String> binlistadapter;
    ProgressBar progress_remotbinlistget;
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
        progress_remotbinlistget=view.findViewById(R.id.progress_remotbinlistget);
        remotebin_list=(ListView)view.findViewById(R.id.remotebin_list);
        binlistadapter = new ArrayAdapter<String>(view.getContext(), android.R.layout.simple_list_item_1, MainActivity.mainActivity.remotManager.remote_updata_bin);
        remotebin_list.setAdapter(binlistadapter);
        remotebin_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                //代码块 msgbox弹出
                //创建一个 AlertDialog.Builder 对象
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                //给对话框添加title
                builder.setTitle("♞♘♞♘♞♘♞♘♞♘");
                //给对话框添加内容
                builder.setMessage("this bin: "+MainActivity.mainActivity.remotManager.remote_updata_bin.get(position)+" you can:");
                builder.setNegativeButton("Download", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        System.out.println("bin download");
                        downloadBinFileName=MainActivity.mainActivity.remotManager.remote_updata_bin.get(position);

                        MainActivity.mainActivity.senddatas(ConvertCode.string2HexString("Download:bin:"+downloadBinFileName+";"));

                    }
                });
                builder.setPositiveButton("exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                //切记勿忘~开启dialog
                builder.show();



            }
        });
        remotebinlistget=(Button)view.findViewById(R.id.remote_bin);
        remotebinlistget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //远程remotebin获取

                    //拉取bin列表第一步
                    String str = "pull:bin:getbinlist;";
                    MainActivity.mainActivity.senddatas(ConvertCode.string2HexString(str));
            }
            });
       }


}
