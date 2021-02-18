package com.oneice.onepanel.remoteFragments;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.oneice.onepanel.MainActivity;
import com.oneice.onepanel.R;
import com.oneice.onepanel.onetools.PinyinUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class RemoteListViewAdapter extends ArrayAdapter<String> implements Filterable {
//    private MyFilter myFilter;
//    private ArrayList<String> mOriginalValues;

    public RemoteListViewAdapter(@NonNull Context context, int resource, @NonNull List<String> objects) {
        super(context, resource, objects);

    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup){
        if (view==null){
            view= LayoutInflater.from(MainActivity.mainActivity.remoteFragmentUpdata.getContext()).inflate(R.layout.item_remoteupdatabinlits,null);
        }
       TextView textView= (TextView) view.findViewById(R.id.updatabin_item_text);
        String itemtext="空";
if(i<MainActivity.mainActivity.remotManager.remote_updata_bin.size()){
        itemtext=MainActivity.mainActivity.remotManager.remote_updata_bin.get(i);
}
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


//    private final Object mLock = new Object();
//
//    @Override
//    public Filter getFilter() {
//        if (myFilter == null) {
//            myFilter = new MyFilter();
//        }
//        return myFilter;
//    }
//
//    class MyFilter extends Filter {
//
//        @Override
//        protected FilterResults performFiltering(CharSequence prefix) {
//            // 持有过滤操作完成之后的数据。该数据包括过滤操作之后的数据的值以及数量。 count:数量 values包含过滤操作之后的数据的值
//            FilterResults results = new FilterResults();
//
//            if (mOriginalValues == null) {
//                synchronized (mLock) {
//                    // 将list的用户 集合转换给这个原始数据的ArrayList
//                    mOriginalValues = new ArrayList<String>(MainActivity.mainActivity.remotManager.remote_updata_bin);
//                }
//            }
//            if (prefix == null || prefix.length() == 0) {
//                synchronized (mLock) {
//                    ArrayList<String> list = new ArrayList<String>(
//                            mOriginalValues);
//                    results.values = list;
//                    results.count = list.size();
//                }
//            } else {
//                // 做正式的筛选
//                String prefixString = prefix.toString().toLowerCase();
//
//                // 声明一个临时的集合对象 将原始数据赋给这个临时变量
//                final ArrayList<String> values = mOriginalValues;
//
//                final int count = values.size();
//
//                // 新的集合对象
//                final ArrayList<String> newValues = new ArrayList<String>(
//                        count);
//
//                for (int i = 0; i < count; i++) {
//                    // 如果姓名的前缀相符或者电话相符就添加到新的集合
//                    final String value = (String) values.get(i);
//
//                    if (value.startsWith(prefixString))
//                    {
//                        newValues.add(value);
//                    }
//                }
//                // 然后将这个新的集合数据赋给FilterResults对象
//                results.values = newValues;
//                results.count = newValues.size();
//            }
//
//            return results;
//        }
//
//        @Override
//        protected void publishResults(CharSequence constraint,
//                                      FilterResults results) {
//            // 重新将与适配器相关联的List重赋值一下
//            MainActivity.mainActivity.remotManager.remote_updata_bin = (List<String>) results.values;
//
//            if (results.count > 0) {
//                notifyDataSetChanged();
//            } else {
//               notifyDataSetInvalidated();
//            }
//        }
//
//    }


}
