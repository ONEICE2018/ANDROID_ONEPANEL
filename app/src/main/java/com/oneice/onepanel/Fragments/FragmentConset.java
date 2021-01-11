package com.oneice.onepanel.Fragments;

import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.oneice.onepanel.MainActivity;
import com.oneice.onepanel.R;
import com.oneice.onepanel.activity_communication.RefrashFregments;
public class FragmentConset extends OneLifeFragment implements RefrashFregments, View.OnClickListener {
    EditText edit_Freq         ;
    EditText edit_Width        ;
    EditText edit_Duty         ;
    EditText edit_Phase        ;

    EditText edit_step_Freq    ;
    EditText edit_step_Width   ;
    EditText edit_step_Duty    ;
    EditText edit_step_Phase   ;
    Button enter_Freq    ;
    Button step_Reduce_Freq    ;
    Button    step_Add_Freq    ;
    Button       enter_Width   ;
    Button step_Reduce_Width   ;
    Button    step_Add_Width   ;
    Button       enter_Duty    ;
    Button step_Reduce_Duty    ;
    Button    step_Add_Duty    ;
    Button       enter_Phase   ;
    Button step_Reduce_Phase   ;
    Button    step_Add_Phase   ;
    TextView text_Freq;
    TextView text_Width;
    TextView text_Duty;
    TextView text_phase;
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        View view=inflater .inflate(R.layout .fragment_conset ,container,false) ;

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        refrashinit(getView());
    }

    @Override
    public void refrashinterface(String refname) {
        if(this.fragmentLife)
        {
            if(MainActivity.addrManager.valuemap.containsKey(refname))
            {//刷新存在项
                if(refname=="频率设置"){
                    int seedata=MainActivity.addrManager.valuemap.get(refname);
                    text_Freq.setText("Freq:"+seedata+"MHz");
                    return;}
                if(refname=="相位设置"){
                    text_phase.setText("Phase:"+MainActivity.addrManager.valuemap.get(refname)+"°");
                    return;}
                if(refname=="占空比设置"){
                    text_Duty.setText("Duty:"+MainActivity.addrManager.valuemap.get(refname)+"%");
                    return;}
                if(refname=="脉冲宽度设置"){
                    text_Width.setText("Width:"+MainActivity.addrManager.valuemap.get(refname)+"us");
                    return;}
            }
        }
    }

    @Override
    public void refrashinit(final View view) {
        enter_Freq          =(Button)view.findViewById(R.id.enter_Freq);
        step_Reduce_Freq    =(Button)view.findViewById(R.id.step_Reduce_Freq);
        step_Add_Freq       =(Button)view.findViewById(R.id.   step_Add_Freq);
        enter_Width         =(Button)view.findViewById(R.id.      enter_Width);
        step_Reduce_Width   =(Button)view.findViewById(R.id.step_Reduce_Width);
        step_Add_Width      =(Button)view.findViewById(R.id.   step_Add_Width);
        enter_Duty          =(Button)view.findViewById(R.id.      enter_Duty);
        step_Reduce_Duty    =(Button)view.findViewById(R.id.step_Reduce_Duty);
        step_Add_Duty       =(Button)view.findViewById(R.id.   step_Add_Duty);
        enter_Phase         =(Button)view.findViewById(R.id.      enter_Phase);
        step_Reduce_Phase   =(Button)view.findViewById(R.id.step_Reduce_Phase);
        step_Add_Phase      =(Button)view.findViewById(R.id.   step_Add_Phase);
        edit_Freq           =(EditText)view.findViewById(R.id.edit_Freq);
        edit_Width          =(EditText)view.findViewById(R.id.edit_Width);
        edit_Duty           =(EditText)view.findViewById(R.id.edit_Duty);
        edit_Phase          =(EditText)view.findViewById(R.id.edit_Phase);

        edit_step_Freq      =(EditText)view.findViewById(R.id. edit_step_Freq);
        edit_step_Width     =(EditText)view.findViewById(R.id.edit_step_Width);
        edit_step_Duty      =(EditText)view.findViewById(R.id. edit_step_Duty);
        edit_step_Phase     =(EditText)view.findViewById(R.id.edit_step_Phase);
        text_Freq           =(TextView)view.findViewById(R.id.text_Freq);
        text_Width          =(TextView)view.findViewById(R.id.text_Width);
        text_Duty           =(TextView)view.findViewById(R.id.text_Duty);
        text_phase          =(TextView)view.findViewById(R.id.text_Phase);

        enter_Freq.setOnClickListener(this);
        step_Reduce_Freq.setOnClickListener(this);
        step_Add_Freq.setOnClickListener(this);
        enter_Width.setOnClickListener(this);
        step_Reduce_Width.setOnClickListener(this);
        step_Add_Width.setOnClickListener(this);
        enter_Duty.setOnClickListener(this);
        step_Reduce_Duty.setOnClickListener(this);
        step_Add_Duty.setOnClickListener(this);
        enter_Phase.setOnClickListener(this);
        step_Reduce_Phase.setOnClickListener(this);
        step_Add_Phase.setOnClickListener(this);

        text_Freq.setText("Freq:"+MainActivity.addrManager.valuemap.get("频率设置")+"MHz");
        text_phase.setText("Phase:"+MainActivity.addrManager.valuemap.get("相位设置")+"°");
        text_Duty.setText("Duty:"+MainActivity.addrManager.valuemap.get("占空比设置")+"%");
        text_Width.setText("Width:"+MainActivity.addrManager.valuemap.get("脉冲宽度设置")+"us");

        edit_Freq.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO
                    onClick(view.findViewById( R.id.enter_Freq));
                }
                return false;
            }
        });
                edit_Width.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            // TODO
                            onClick(view.findViewById( R.id.enter_Width));
                        }
                        return false;
                    }
                });
        edit_Duty.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO
                    onClick(view.findViewById( R.id.enter_Duty));
                }
                return false;
            }
        });
                edit_Phase.setOnEditorActionListener(new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_DONE) {
                            // TODO
                            onClick(view.findViewById( R.id.enter_Phase));
                        }
                        return false;
                    }
                });


    }


    @Override
    public void onClick(View v) {
       // MainActivity.mainActivity.sendconmod();
switch (v.getId()){
        //**Freq*//
        case R.id.enter_Freq:
        if(!edit_Freq.getText().toString().equals("")){
            String data=edit_Freq.getText().toString();
            int da=Integer.valueOf(data);
            int k=MainActivity.mainActivity.addrManager.addrmap.get("频率设置");
            MainActivity.mainActivity.sendconmod(k,da);
        }
        break;
        case R.id.step_Reduce_Freq:
        if(!edit_step_Freq.getText().toString().equals("")){
            int sdata=MainActivity.mainActivity.addrManager.valuemap.get("频率设置")-Integer.valueOf(edit_step_Freq.getText().toString());
            if(MainActivity.mainActivity.addrManager.valuemap.get("频率设置")<Integer.valueOf(edit_step_Freq.getText().toString())){
                sdata=0;
            }
            MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("频率设置"),sdata);
        }
        break;
        case R.id.step_Add_Freq:
        if(!edit_step_Freq.getText().toString().equals("")){
            int sdata=MainActivity.mainActivity.addrManager.valuemap.get("频率设置")+Integer.valueOf(edit_step_Freq.getText().toString());
            if(sdata>60000){
                sdata=60000;
            }
            MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("频率设置"),sdata);
        }
        break;
        //**Width*//
        case R.id.enter_Width:
        if(!edit_Width.getText().toString().equals("")){
            MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("脉冲宽度设置"),Integer.valueOf(edit_Width.getText().toString()));
        }
        break;
        case R.id.step_Reduce_Width:
        if(!edit_step_Width.getText().toString().equals("")){
            int sdata=MainActivity.mainActivity.addrManager.valuemap.get("脉冲宽度设置")-Integer.valueOf(edit_step_Width.getText().toString());
            if(MainActivity.mainActivity.addrManager.valuemap.get("脉冲宽度设置")<Integer.valueOf(edit_step_Width.getText().toString())){
                sdata=0;
            }
            MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("脉冲宽度设置"),sdata);
        }
        break;
        case R.id.step_Add_Width:
        if(!edit_step_Width.getText().toString().equals("")){
            int sdata=MainActivity.mainActivity.addrManager.valuemap.get("脉冲宽度设置")+Integer.valueOf(edit_step_Width.getText().toString());
            if(sdata>60000){
                sdata=60000;
            }
            MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("脉冲宽度设置"),sdata);
        }
        break;
        //**Duty*//
        case R.id.enter_Duty:
        if(!edit_Duty.getText().toString().equals("")){
            MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("占空比设置"),Integer.valueOf(edit_Duty.getText().toString()));
        }
        break;
        case R.id.step_Reduce_Duty:
        if(!edit_step_Duty.getText().toString().equals("")){
            int sdata=MainActivity.mainActivity.addrManager.valuemap.get("占空比设置")-Integer.valueOf(edit_step_Duty.getText().toString());
            if(MainActivity.mainActivity.addrManager.valuemap.get("占空比设置")<Integer.valueOf(edit_step_Duty.getText().toString())){
                sdata=0;
            }
            MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("占空比设置"),sdata);
        }
        break;
        case R.id.step_Add_Duty:
        if(!edit_step_Duty.getText().toString().equals("")){
            int sdata=MainActivity.mainActivity.addrManager.valuemap.get("占空比设置")+Integer.valueOf(edit_step_Duty.getText().toString());
            if(sdata>60000){
                sdata=60000;
            }
            MainActivity.mainActivity. sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("占空比设置"),sdata);
        }
        break;
        //**Phase*//
        case R.id.enter_Phase:
        if(!edit_Phase.getText().toString().equals("")){
            String editstring=edit_Phase.getText().toString();
            MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("相位设置"),Integer.valueOf(edit_Phase.getText().toString()));
        }
        break;
        case R.id.step_Reduce_Phase:
        if(!edit_step_Phase.getText().toString().equals("")){
            int sdata=MainActivity.mainActivity.addrManager.valuemap.get("相位设置")-Integer.valueOf(edit_step_Phase.getText().toString());
            if(MainActivity.mainActivity.addrManager.valuemap.get("相位设置")<Integer.valueOf(edit_step_Phase.getText().toString())){
                sdata=0;
            }
            MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("相位设置"),sdata);
        }
        break;
        case R.id.step_Add_Phase:
        if(!edit_step_Phase.getText().toString().equals("")){
            int sdata=MainActivity.mainActivity.addrManager.valuemap.get("相位设置")+Integer.valueOf(edit_step_Phase.getText().toString());
            if(sdata>60000){
                sdata=60000;
            }
            MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("相位设置"),sdata);
        }
        break;
    }
    }
}
