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

import androidx.fragment.app.Fragment;

import com.oneice.onepanel.MainActivity;
import com.oneice.onepanel.R;
import com.oneice.onepanel.activity_communication.RefrashFregments;

public class FragmentProtect extends OneLifeFragment implements RefrashFregments, View.OnClickListener {
    //保护设置界面
    Button PSW_Temp;
    Button PSW_Voalt;
    Button PSW_Curr;
    Button PSW_Pwd;
    Button PSW_Pwr;
    Button PSW_Vswr;
    Button enter_Pro_Temp;
    Button enter_Pro_Voalt;
    Button enter_Pro_Curr;
    Button enter_Pro_Pwd;
    Button enter_Pro_Pwr;
    Button enter_Pro_Vswr;
    EditText edit_Pro_Temp;
    EditText edit_Pro_Voalt;
    EditText edit_Pro_Curr;
    EditText edit_Pro_Pwd;
    EditText edit_Pro_Pwr;
    EditText edit_Pro_Vswr;

    TextView text_Pro_Temp;
    TextView text_Pro_Voalt;
    TextView text_Pro_Curr;
    TextView text_Pro_Pwd;
    TextView text_Pro_Pwr;
    TextView text_Pro_Vswr;
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        View view=inflater .inflate(R.layout .fragment_protect ,container,false) ;
        refrashinit(view);
        return view;
    }

    @Override
    public void refrashinterface(String refname) {
           if(this.fragmentLife){
               if(MainActivity.addrManager.valuemap.containsKey(refname))
               {//刷新存在项
                   if(refname=="整机输出过功率保护门限设置"){
                       text_Pro_Pwd.setText("Pwd:"+MainActivity.addrManager.valuemap.get(refname)+"W");
                       return;}
                   if(refname== "整机反射过功率门限设置"){
                       text_Pro_Pwr.setText("Pwd:"+MainActivity.addrManager.valuemap.get(refname)+"W");
                       return;}
                   if(refname=="整机驻波保护门限设置"){
                       text_Pro_Vswr.setText("Vswr:"+MainActivity.addrManager.valuemap.get(refname)/10.0+"W");
                       return;}
                   if(refname== "整机温度保护门限设置"){
                       text_Pro_Temp.setText("Temp:"+MainActivity.addrManager.valuemap.get(refname)/10.0+"℃");
                       return;}
                   if(refname=="整机电压保护门限设置"){
                       text_Pro_Voalt.setText("Volt:"+MainActivity.addrManager.valuemap.get(refname)+"V");
                       return;}
                   if(refname=="整机电流保护门限设置"){
                       text_Pro_Curr.setText("Curr:"+MainActivity.addrManager.valuemap.get(refname)/10.0+"A");
                       return;}
                   if(refname=="整机输出过功率保护开/关"){

                       if(MainActivity.addrManager.valuemap.get(refname)==1)
                       {
                           PSW_Pwd.setText("OFF");
                           PSW_Pwd.setBackgroundResource(R.drawable.button_on);

                       }else{
                           PSW_Pwd.setText("ON");
                           PSW_Pwd.setBackgroundResource(R.drawable.button_off);
                       }
                       return;
                   }
                   if(refname=="整机反射过功率开/关"){
                       if(MainActivity.addrManager.valuemap.get(refname)==1)
                       {
                           PSW_Pwr.setText("OFF");
                           PSW_Pwr.setBackgroundResource(R.drawable.button_on);
                       }else{
                           PSW_Pwr.setText("ON");
                           PSW_Pwr.setBackgroundResource(R.drawable.button_off);
                       }
                       return;
                   }


                   if(refname=="整机驻波保护开/关"){
                       if(MainActivity.addrManager.valuemap.get(refname)==1)
                       {
                           PSW_Vswr.setText("OFF");
                           PSW_Vswr.setBackgroundResource(R.drawable.button_on);
                       }else{
                           PSW_Vswr.setText("ON");
                           PSW_Vswr.setBackgroundResource(R.drawable.button_off);
                       }
                       return;
                   }
                   if(refname=="整机温度保护开/关"){
                       if(MainActivity.addrManager.valuemap.get(refname)==1)
                       {
                           PSW_Temp.setText("OFF");
                           PSW_Temp.setBackgroundResource(R.drawable.button_on);
                       }else{
                           PSW_Temp.setText("ON");
                           PSW_Temp.setBackgroundResource(R.drawable.button_off);
                       }
                       return;
                   }

                   if(refname=="整机电压保护开/关"){
                       if(MainActivity.addrManager.valuemap.get(refname)==1)
                       {
                           PSW_Voalt.setText("OFF");
                           PSW_Voalt.setBackgroundResource(R.drawable.button_on);
                       }else{
                           PSW_Voalt.setText("ON");
                           PSW_Voalt.setBackgroundResource(R.drawable.button_off);
                       }
                       return;
                   }
                   

                   if(refname=="整机电流保护开/关"){
                       if(MainActivity.addrManager.valuemap.get(refname)==1)
                       {
                           PSW_Curr.setText("OFF");
                           PSW_Curr.setBackgroundResource(R.drawable.button_on);
                       }else{
                           PSW_Curr.setText("ON");
                           PSW_Curr.setBackgroundResource(R.drawable.button_off);
                       }
                       return;
                   }
                   

               }
               
               
           }
    }

    @Override
    public void refrashinit(final View view) {
        //保护设置界面
        PSW_Temp         =(Button)view.findViewById(R.id.PSW_Temp        )  ;
        PSW_Voalt        =(Button)view.findViewById(R.id.PSW_Voalt       )  ;
        PSW_Curr         =(Button)view.findViewById(R.id.PSW_Curr        )  ;
        PSW_Pwd          =(Button)view.findViewById(R.id.PSW_Pwd         )  ;
        PSW_Pwr          =(Button)view.findViewById(R.id.PSW_Pwr         )  ;
        PSW_Vswr         =(Button)view.findViewById(R.id.PSW_Vswr        )  ;
        enter_Pro_Temp   =(Button)view.findViewById(R.id.enter_Pro_Temp  )  ;
        enter_Pro_Voalt  =(Button)view.findViewById(R.id.enter_Pro_Voalt )  ;
        enter_Pro_Curr   =(Button)view.findViewById(R.id.enter_Pro_Curr  )  ;
        enter_Pro_Pwd    =(Button)view.findViewById(R.id.enter_Pro_Pwd   )  ;
        enter_Pro_Pwr    =(Button)view.findViewById(R.id.enter_Pro_Pwr   )  ;
        enter_Pro_Vswr   =(Button)view.findViewById(R.id.enter_Pro_Vswr  )  ;
        edit_Pro_Temp   =(EditText)view.findViewById(R.id.edit_Pro_Temp )  ;
        edit_Pro_Voalt  =(EditText)view.findViewById(R.id.edit_Pro_Voalt)  ;
        edit_Pro_Curr   =(EditText)view.findViewById(R.id.edit_Pro_Curr )  ;
        edit_Pro_Pwd    =(EditText)view.findViewById(R.id.edit_Pro_Pwd  )  ;
        edit_Pro_Pwr    =(EditText)view.findViewById(R.id.edit_Pro_Pwr  )  ;
        edit_Pro_Vswr   =(EditText)view.findViewById(R.id.edit_Pro_Vswr )  ;
        text_Pro_Temp    =(TextView)view.findViewById(R.id.text_Pro_Temp );
        text_Pro_Voalt   =(TextView)view.findViewById(R.id.text_Pro_Voalt);
        text_Pro_Curr    =(TextView)view.findViewById(R.id.text_Pro_Curr );
        text_Pro_Pwd     =(TextView)view.findViewById(R.id.text_Pro_Pwd  );
        text_Pro_Pwr     =(TextView)view.findViewById(R.id.text_Pro_Pwr  );
        text_Pro_Vswr    =(TextView)view.findViewById(R.id.text_Pro_Vswr );
        //保护界面
        PSW_Temp.setOnClickListener(this);
        PSW_Voalt.setOnClickListener(this);
        PSW_Curr.setOnClickListener(this);
        PSW_Pwd.setOnClickListener(this);
        PSW_Pwr.setOnClickListener(this);
        PSW_Vswr.setOnClickListener(this);
        enter_Pro_Temp.setOnClickListener(this);
        enter_Pro_Voalt.setOnClickListener(this);
        enter_Pro_Curr.setOnClickListener(this);
        enter_Pro_Pwd.setOnClickListener(this);
        enter_Pro_Pwr.setOnClickListener(this);
        enter_Pro_Vswr.setOnClickListener(this);

        edit_Pro_Curr.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO
                    onClick(view.findViewById( R.id.enter_Pro_Curr));
                }
                return false;
            }
        });
        edit_Pro_Pwd.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO
                    onClick(view.findViewById( R.id.enter_Pro_Pwd));
                }
                return false;
            }
        });
        edit_Pro_Pwr.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO
                    onClick(view.findViewById( R.id.enter_Pro_Pwr));
                }
                return false;
            }
        });
        edit_Pro_Temp.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO
                    onClick(view.findViewById( R.id.enter_Pro_Temp));
                }
                return false;
            }
        });
        edit_Pro_Voalt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO
                    onClick(view.findViewById( R.id.enter_Pro_Voalt));
                }
                return false;
            }
        });
        edit_Pro_Vswr.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO
                    onClick(view.findViewById( R.id.enter_Pro_Vswr));
                }
                return false;
            }
        });
        text_Pro_Pwd.setText("Pwd:"+MainActivity.addrManager.valuemap.get("整机输出过功率保护门限设置")+"W");
        text_Pro_Pwr.setText("Pwd:"+MainActivity.addrManager.valuemap.get( "整机反射过功率门限设置")+"W");
        text_Pro_Vswr.setText("Vswr:"+MainActivity.addrManager.valuemap.get("整机驻波保护门限设置")/10.0+"W");
        text_Pro_Temp.setText("Temp:"+MainActivity.addrManager.valuemap.get( "整机温度保护门限设置")/10.0+"℃");
        text_Pro_Voalt.setText("Volt:"+MainActivity.addrManager.valuemap.get("整机电压保护门限设置")+"V");
        text_Pro_Curr.setText("Curr:"+MainActivity.addrManager.valuemap.get("整机电流保护门限设置")/10.0+"A");
        if(MainActivity.addrManager.valuemap.get("整机输出过功率保护开/关")==1)
        {
            PSW_Pwd.setText("OFF");
            PSW_Pwd.setBackgroundResource(R.drawable.button_on);

        }else{
            PSW_Pwd.setText("ON");
            PSW_Pwd.setBackgroundResource(R.drawable.button_off);
        }

        if(MainActivity.addrManager.valuemap.get("整机反射过功率开/关")==1)
        {
            PSW_Pwr.setText("OFF");
            PSW_Pwr.setBackgroundResource(R.drawable.button_on);
        }else{
            PSW_Pwr.setText("ON");
            PSW_Pwr.setBackgroundResource(R.drawable.button_off);
        }




        if(MainActivity.addrManager.valuemap.get("整机驻波保护开/关")==1)
        {
            PSW_Vswr.setText("OFF");
            PSW_Vswr.setBackgroundResource(R.drawable.button_on);
        }else{
            PSW_Vswr.setText("ON");
            PSW_Vswr.setBackgroundResource(R.drawable.button_off);
        }
        if(MainActivity.addrManager.valuemap.get("整机温度保护开/关")==1)
        {
            PSW_Temp.setText("OFF");
            PSW_Temp.setBackgroundResource(R.drawable.button_on);
        }else{
            PSW_Temp.setText("ON");
            PSW_Temp.setBackgroundResource(R.drawable.button_off);
        }


        if(MainActivity.addrManager.valuemap.get("整机电压保护开/关")==1)
        {
            PSW_Voalt.setText("OFF");
            PSW_Voalt.setBackgroundResource(R.drawable.button_on);
        }else{
            PSW_Voalt.setText("ON");
            PSW_Voalt.setBackgroundResource(R.drawable.button_off);
        }
        if(MainActivity.addrManager.valuemap.get("整机电流保护开/关")==1)
        {
            PSW_Curr.setText("OFF");
            PSW_Curr.setBackgroundResource(R.drawable.button_on);
        }else
            {
            PSW_Curr.setText("ON");
            PSW_Curr.setBackgroundResource(R.drawable.button_off);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
        case R.id.PSW_Temp       :if(PSW_Temp .getText().toString().equals("ON")){MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap     .get("整机温度保护开/关"              ),1);}else {MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("整机温度保护开/关"   ),0);} break;
        case R.id.PSW_Voalt      :if(PSW_Voalt.getText().toString().equals("ON")){MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap     .get("整机电压保护开/关"              ),1);}else {MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("整机电压保护开/关"   ),0);} break;
        case R.id.PSW_Curr       :if(PSW_Curr .getText().toString().equals("ON")){MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap     .get("整机电流保护开/关"              ),1);}else {MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("整机电流保护开/关"   ),0);} break;
        case R.id.PSW_Pwd        :if(PSW_Pwd  .getText().toString().equals("ON")){MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap     .get("整机输出过功率保护开/关"        ),1);}else {MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("整机输出过功率保护开/关"),0);} break;
        case R.id.PSW_Pwr        :if(PSW_Pwr  .getText().toString().equals("ON")){MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap     .get("整机反射过功率开/关"           ),1);} else {MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("整机反射过功率开/关" ),0);} break;
        case R.id.PSW_Vswr       :if(PSW_Vswr .getText().toString().equals("ON")){MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap     .get("整机驻波保护开/关"             ),1);}  else {MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("整机驻波保护开/关"    ),0);} break;
        case R.id.enter_Pro_Temp : if(!edit_Pro_Temp .getText().toString().equals("")){MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("整机温度保护门限设置"          ), Math.round(Float.valueOf(edit_Pro_Temp .getText().toString())*10));}break;
        case R.id.enter_Pro_Voalt: if(!edit_Pro_Voalt.getText().toString().equals("")){MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("整机电压保护门限设置"          ), Math.round(Float.valueOf(edit_Pro_Voalt .getText().toString())*10));}break;
        case R.id.enter_Pro_Curr : if(!edit_Pro_Curr .getText().toString().equals("")){MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("整机电流保护门限设置"          ), Math.round(Float.valueOf(edit_Pro_Curr .getText().toString())*10));}break;
        case R.id.enter_Pro_Pwd  : if(!edit_Pro_Pwd  .getText().toString().equals("")){MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("整机输出过功率保护门限设置"   ),  Integer.valueOf(edit_Pro_Pwd .getText().toString()));}break;
        case R.id.enter_Pro_Pwr  : if(!edit_Pro_Pwr  .getText().toString().equals("")){MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("整机反射过功率门限设置"       ),  Integer.valueOf(edit_Pro_Pwr .getText().toString()));}break;
        case R.id.enter_Pro_Vswr : if(!edit_Pro_Vswr .getText().toString().equals("")){MainActivity.mainActivity.sendconmod(MainActivity.mainActivity.addrManager.addrmap.get("整机驻波保护门限设置"         ),  Math.round(Float.valueOf(edit_Pro_Vswr .getText().toString())*10));}break;
       }
    }
}
