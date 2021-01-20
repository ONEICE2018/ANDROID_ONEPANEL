package com.oneice.onepanel.remoteFragments;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.oneice.onepanel.MainActivity;
import com.oneice.onepanel.R;

import java.io.File;
import java.util.List;

public class RemoteListViewAdapter extends ArrayAdapter<String> {


    public RemoteListViewAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        if (view==null){
            view= LayoutInflater.from(MainActivity.mainActivity.remoteFragmentUpdata.getContext()).inflate(R.layout.item_remoteupdatabinlits,null);
        }
        TextView textView= (TextView) view.findViewById(R.id.updatabin_item_text);
        String itemtext=MainActivity.mainActivity.remotManager.remote_updata_bin.get(i);
        textView.setText(itemtext);
        File contextCreater=new File(MainActivity.mainActivity.oneFileManager.loadUpdataBinFilePath(itemtext));
        if(MainActivity.mainActivity.oneFileManager.updataBinFiles.contains(itemtext)){
            if(contextCreater.length()==0)
            {
                textView.setTextColor(0xAAAAAAAA);
            }else{
            textView.setTextColor(0xAA00FF00);
            }
        }else{
            textView.setTextColor(0xAAFF0000);
        }

        return view;
    }
}
