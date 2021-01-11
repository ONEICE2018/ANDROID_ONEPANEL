package com.oneice.onepanel.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.oneice.onepanel.MainActivity;
import com.oneice.onepanel.R;
import com.oneice.onepanel.activity_communication.RefrashFregments;

public class FragmentUnit extends OneLifeFragment implements RefrashFregments {
    //插件信息
    TextView mc_cn1_scgl1,mc_cn1_scgl2,mc_cn1_scgl3,mc_cn1_scgl4,mc_cn1_scgl5,mc_cn1_scgl6;
    TextView mc_cn1_fsgl1,mc_cn1_fsgl2,mc_cn1_fsgl3,mc_cn1_fsgl4,mc_cn1_fsgl5,mc_cn1_fsgl6;
    TextView mc_cn1_zbb1 ,mc_cn1_zbb2 ,mc_cn1_zbb3 ,mc_cn1_zbb4 ,mc_cn1_zbb5 ,mc_cn1_zbb6 ;
    TextView mc_cn1_dy1,mc_cn1_dy2,mc_cn1_dy3,mc_cn1_dy4,mc_cn1_dy5,mc_cn1_dy6;
    TextView mc_cn1_wd1,mc_cn1_wd2,mc_cn1_wd3,mc_cn1_wd4,mc_cn1_wd5,mc_cn1_wd6;
    TextView mc_cn1_dl11,mc_cn1_dl12,mc_cn1_dl13,mc_cn1_dl14,mc_cn1_dl15,mc_cn1_dl16;
    TextView mc_cn1_dl21,mc_cn1_dl22,mc_cn1_dl23,mc_cn1_dl24,mc_cn1_dl25,mc_cn1_dl26;
    TextView mc_cn1_dl31,mc_cn1_dl32,mc_cn1_dl33,mc_cn1_dl34,mc_cn1_dl35,mc_cn1_dl36;
    TextView mc_cn1_dl41,mc_cn1_dl42,mc_cn1_dl43,mc_cn1_dl44,mc_cn1_dl45,mc_cn1_dl46;
    TextView mc_cn1_dl51,mc_cn1_dl52,mc_cn1_dl53,mc_cn1_dl54,mc_cn1_dl55,mc_cn1_dl56;
    TextView mc_cn1_dl61,mc_cn1_dl62,mc_cn1_dl63,mc_cn1_dl64,mc_cn1_dl65,mc_cn1_dl66;
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        View view=inflater .inflate(R.layout .fragment_units ,container,false) ;
        refrashinit(view);
        return view;
    }

    @Override
    public void refrashinterface(String refname) {
  if(this.fragmentLife){

//     new Thread(new Runnable() {
//    @Override
//    public void run() {

          if(MainActivity.addrManager.addrmap.containsKey(refname)){
              int mcaddr=MainActivity.addrManager.addrmap.get(refname);
              for(int i=0;i<(MainActivity.addrManager.getUnitsNum()-MainActivity.addrManager.getNowPage()*6)&&i<6;i++){
                  if(mcaddr==MainActivity.addrManager. basemcMap.get("插件警告信息基地址")+MainActivity.addrManager.getNowPage()*6*0x40+i*0x40)
                  {
                      //1开启 0关闭。 0位到5位依次为：输出过功率、反射过功率、驻波、温度、电压、电流（详见注释）
                      if(MainActivity.addrManager.valuemap.get(refname)==0x00){
                          //无警告
                      }else
                      if(((MainActivity.addrManager.valuemap.get(refname)>>0)&0x01)==1){
                         MainActivity.showmsgs("Pi"+MainActivity.addrManager.getNowPage()*6+i+1+"OverPwd");
                      }else
                      if(((MainActivity.addrManager.valuemap.get(refname)>>1)&0x01)==1){
                          MainActivity.showmsgs("Reflective"+MainActivity.addrManager.getNowPage()*6+i+1+"OverPwr");
                      }else
                      if(((MainActivity.addrManager.valuemap.get(refname)>>2)&0x01)==1){
                          MainActivity.showmsgs("Standing wave"+MainActivity.addrManager.getNowPage()*6+i+1+"anomaly");
                      }else
                      if(((MainActivity.addrManager.valuemap.get(refname)>>3)&0x01)==1){
                          MainActivity.showmsgs("Temperature"+MainActivity.addrManager.getNowPage()*6+i+1+"anomaly");
                      }else
                      if(((MainActivity.addrManager.valuemap.get(refname)>>4)&0x01)==1){
                          MainActivity.showmsgs("Voltage"+MainActivity.addrManager.getNowPage()*6+i+1+"anomaly");
                      }else
                      if(((MainActivity.addrManager.valuemap.get(refname)>>5)&0x01)==1){
                          MainActivity.showmsgs("Abnormal"+MainActivity.addrManager.getNowPage()*6+i+1+"anomaly");
                      }else
                      if(((MainActivity.addrManager.valuemap.get(refname)>>6)&0x01)==1){
                          MainActivity.showmsgs("Interlocking"+MainActivity.addrManager.getNowPage()*6+i+1+"anomaly");
                      }
                      return;
                  }
                  if(mcaddr==MainActivity.addrManager. basemcMap.get("插件输出功率基地址")+MainActivity.addrManager.getNowPage()*6*0x40+i*0x40)
                  {
                      switch (i+1){
                          case 1:mc_cn1_scgl1.setText(""+MainActivity.addrManager.valuemap.get(refname)); break;
                          case 2:mc_cn1_scgl2.setText(""+MainActivity.addrManager.valuemap.get(refname));  break;
                          case 3:mc_cn1_scgl3.setText(""+MainActivity.addrManager.valuemap.get(refname));  break;
                          case 4:mc_cn1_scgl4.setText(""+MainActivity.addrManager.valuemap.get(refname));  break;
                          case 5:mc_cn1_scgl5.setText(""+MainActivity.addrManager.valuemap.get(refname));  break;
                          case 6:mc_cn1_scgl6.setText(""+MainActivity.addrManager.valuemap.get(refname));  break;
                      }

                      return;
                  }
                  if(mcaddr==MainActivity.addrManager. basemcMap.get("插件反射功率基地址")+MainActivity.addrManager.getNowPage()*6*0x40+i*0x40)
                  {
                      switch (i+1){
                          case 1:mc_cn1_fsgl1.setText(""+MainActivity.addrManager.valuemap.get(refname)); break;
                          case 2:mc_cn1_fsgl2.setText(""+MainActivity.addrManager.valuemap.get(refname));  break;
                          case 3:mc_cn1_fsgl3.setText(""+MainActivity.addrManager.valuemap.get(refname));  break;
                          case 4:mc_cn1_fsgl4.setText(""+MainActivity.addrManager.valuemap.get(refname));  break;
                          case 5:mc_cn1_fsgl5.setText(""+MainActivity.addrManager.valuemap.get(refname));  break;
                          case 6:mc_cn1_fsgl6.setText(""+MainActivity.addrManager.valuemap.get(refname));  break;
                      }
                      return;
                  }
                  if(mcaddr==MainActivity.addrManager. basemcMap.get("插件驻波比基地址")+MainActivity.addrManager.getNowPage()*6*0x40+i*0x40)
                  {
                      switch (i+1){
                          case 1:mc_cn1_zbb1.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0); break;
                          case 2:mc_cn1_zbb2.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 3:mc_cn1_zbb3.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 4:mc_cn1_zbb4.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 5:mc_cn1_zbb5.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 6:mc_cn1_zbb6.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                      }
                      return;
                  }
                  if(mcaddr==MainActivity.addrManager. basemcMap.get("插件温度值基地址")+MainActivity.addrManager.getNowPage()*6*0x40+i*0x40)
                  {
                      switch (i+1){

                          case 1:mc_cn1_wd1.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0); break;
                          case 2:mc_cn1_wd2.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 3:mc_cn1_wd3.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 4:mc_cn1_wd4.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 5:mc_cn1_wd5.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 6:mc_cn1_wd6.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                      }
                      return;
                  }
                  if(mcaddr==MainActivity.addrManager. basemcMap.get("插件电压值基地址")+MainActivity.addrManager.getNowPage()*6*0x40+i*0x40)
                  {
                      switch (i+1){

                          case 1:mc_cn1_dy1.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0); break;
                          case 2:mc_cn1_dy2.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 3:mc_cn1_dy3.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 4:mc_cn1_dy4.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 5:mc_cn1_dy5.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 6:mc_cn1_dy6.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                      }
                      return;
                  }
                  if(mcaddr==MainActivity.addrManager. basemcMap.get("插件电流1基地址")+MainActivity.addrManager.getNowPage()*6*0x40+i*0x40)
                  {
                      switch (i+1){
                          case 1:mc_cn1_dl11.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0); break;
                          case 2:mc_cn1_dl12.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 3:mc_cn1_dl13.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 4:mc_cn1_dl14.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 5:mc_cn1_dl15.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 6:mc_cn1_dl16.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                      }
                      return;
                  }
                  if(mcaddr==MainActivity.addrManager. basemcMap.get("插件电流2基地址")+MainActivity.addrManager.getNowPage()*6*0x40+i*0x40)
                  {
                      switch (i+1){
                          case 1:mc_cn1_dl21.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0); break;
                          case 2:mc_cn1_dl22.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 3:mc_cn1_dl23.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 4:mc_cn1_dl24.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 5:mc_cn1_dl25.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 6:mc_cn1_dl26.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                      }
                      return;
                  }
                  if(mcaddr==MainActivity.addrManager. basemcMap.get("插件电流3基地址")+MainActivity.addrManager.getNowPage()*6*0x40+i*0x40)
                  {
                      switch (i+1){
                          case 1:mc_cn1_dl31.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0); break;
                          case 2:mc_cn1_dl32.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 3:mc_cn1_dl33.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 4:mc_cn1_dl34.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 5:mc_cn1_dl35.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 6:mc_cn1_dl36.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                      }
                      return;
                  }
                  if(mcaddr==MainActivity.addrManager. basemcMap.get("插件电流4基地址")+MainActivity.addrManager.getNowPage()*6*0x40+i*0x40)
                  {
                      switch (i+1){
                          case 1:mc_cn1_dl41.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0); break;
                          case 2:mc_cn1_dl42.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 3:mc_cn1_dl43.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 4:mc_cn1_dl44.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 5:mc_cn1_dl45.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 6:mc_cn1_dl46.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                      }
                      return;
                  }
                  if(mcaddr==MainActivity.addrManager. basemcMap.get("插件电流5基地址")+MainActivity.addrManager.getNowPage()*6*0x40+i*0x40)
                  {
                      switch (i+1){
                          case 1:mc_cn1_dl51.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 2:mc_cn1_dl52.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 3:mc_cn1_dl53.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 4:mc_cn1_dl54.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 5:mc_cn1_dl55.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                          case 6:mc_cn1_dl56.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0);  break;
                      }
                      return;
                  }
                  if(mcaddr==MainActivity.addrManager. basemcMap.get("插件电流6基地址")+MainActivity.addrManager.getNowPage()*6*0x40+i*0x40)
                  {
                      switch (i+1){
                          case 1:mc_cn1_dl61.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0 );  break;
                          case 2:mc_cn1_dl62.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0 );  break;
                          case 3:mc_cn1_dl63.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0 );  break;
                          case 4:mc_cn1_dl64.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0 );  break;
                          case 5:mc_cn1_dl65.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0 );  break;
                          case 6:mc_cn1_dl66.setText(""+MainActivity.addrManager.valuemap.get(refname)/10.0 );  break;
                      }
                      return;
                  }

              }
          }



//    }
//
//}).start();


      
      
  }
    }

    @Override
    public void refrashinit(View view) {
        //插件信息
        mc_cn1_scgl1=(TextView)view.findViewById(R.id.mc_cn1_scgl1);mc_cn1_scgl2=(TextView)view.findViewById(R.id.mc_cn1_scgl2);mc_cn1_scgl3=(TextView)view.findViewById(R.id.mc_cn1_scgl3);mc_cn1_scgl4=(TextView)view.findViewById(R.id.mc_cn1_scgl4);mc_cn1_scgl5=(TextView)view.findViewById(R.id.mc_cn1_scgl5);mc_cn1_scgl6=(TextView)view.findViewById(R.id.mc_cn1_scgl6);
        mc_cn1_fsgl1=(TextView)view.findViewById(R.id.mc_cn1_fsgl1);mc_cn1_fsgl2=(TextView)view.findViewById(R.id.mc_cn1_fsgl2);mc_cn1_fsgl3=(TextView)view.findViewById(R.id.mc_cn1_fsgl3);mc_cn1_fsgl4=(TextView)view.findViewById(R.id.mc_cn1_fsgl4);mc_cn1_fsgl5=(TextView)view.findViewById(R.id.mc_cn1_fsgl5);mc_cn1_fsgl6=(TextView)view.findViewById(R.id.mc_cn1_fsgl6);
        mc_cn1_zbb1 =(TextView)view.findViewById(R.id.mc_cn1_zbb1 );mc_cn1_zbb2 =(TextView)view.findViewById(R.id.mc_cn1_zbb2 );mc_cn1_zbb3 =(TextView)view.findViewById(R.id.mc_cn1_zbb3 );mc_cn1_zbb4 =(TextView)view.findViewById(R.id.mc_cn1_zbb4 );mc_cn1_zbb5 =(TextView)view.findViewById(R.id.mc_cn1_zbb5 );mc_cn1_zbb6 =(TextView)view.findViewById(R.id.mc_cn1_zbb6 );
        mc_cn1_dy1  =(TextView)view.findViewById(R.id.mc_cn1_dy1  );mc_cn1_dy2  =(TextView)view.findViewById(R.id.mc_cn1_dy2  );mc_cn1_dy3  =(TextView)view.findViewById(R.id.mc_cn1_dy3  );mc_cn1_dy4  =(TextView)view.findViewById(R.id.mc_cn1_dy4  );mc_cn1_dy5  =(TextView)view.findViewById(R.id.mc_cn1_dy5  );mc_cn1_dy6  =(TextView)view.findViewById(R.id.mc_cn1_dy6  );
        mc_cn1_wd1  =(TextView)view.findViewById(R.id.mc_cn1_wd1  );mc_cn1_wd2  =(TextView)view.findViewById(R.id.mc_cn1_wd2  );mc_cn1_wd3  =(TextView)view.findViewById(R.id.mc_cn1_wd3  );mc_cn1_wd4  =(TextView)view.findViewById(R.id.mc_cn1_wd4  );mc_cn1_wd5  =(TextView)view.findViewById(R.id.mc_cn1_wd5  );mc_cn1_wd6  =(TextView)view.findViewById(R.id.mc_cn1_wd6  );
        mc_cn1_dl11 =(TextView)view.findViewById(R.id.mc_cn1_dl11 );mc_cn1_dl12 =(TextView)view.findViewById(R.id.mc_cn1_dl12 );mc_cn1_dl13 =(TextView)view.findViewById(R.id.mc_cn1_dl13 );mc_cn1_dl14 =(TextView)view.findViewById(R.id.mc_cn1_dl14 );mc_cn1_dl15 =(TextView)view.findViewById(R.id.mc_cn1_dl15 );mc_cn1_dl16 =(TextView)view.findViewById(R.id.mc_cn1_dl16 );
        mc_cn1_dl21 =(TextView)view.findViewById(R.id.mc_cn1_dl21 );mc_cn1_dl22 =(TextView)view.findViewById(R.id.mc_cn1_dl22 );mc_cn1_dl23 =(TextView)view.findViewById(R.id.mc_cn1_dl23 );mc_cn1_dl24 =(TextView)view.findViewById(R.id.mc_cn1_dl24 );mc_cn1_dl25 =(TextView)view.findViewById(R.id.mc_cn1_dl25 );mc_cn1_dl26 =(TextView)view.findViewById(R.id.mc_cn1_dl26 );
        mc_cn1_dl31 =(TextView)view.findViewById(R.id.mc_cn1_dl31 );mc_cn1_dl32 =(TextView)view.findViewById(R.id.mc_cn1_dl32 );mc_cn1_dl33 =(TextView)view.findViewById(R.id.mc_cn1_dl33 );mc_cn1_dl34 =(TextView)view.findViewById(R.id.mc_cn1_dl34 );mc_cn1_dl35 =(TextView)view.findViewById(R.id.mc_cn1_dl35 );mc_cn1_dl36 =(TextView)view.findViewById(R.id.mc_cn1_dl36 );
        mc_cn1_dl41 =(TextView)view.findViewById(R.id.mc_cn1_dl41 );mc_cn1_dl42 =(TextView)view.findViewById(R.id.mc_cn1_dl42 );mc_cn1_dl43 =(TextView)view.findViewById(R.id.mc_cn1_dl43 );mc_cn1_dl44 =(TextView)view.findViewById(R.id.mc_cn1_dl44 );mc_cn1_dl45 =(TextView)view.findViewById(R.id.mc_cn1_dl45 );mc_cn1_dl46 =(TextView)view.findViewById(R.id.mc_cn1_dl46 );
        mc_cn1_dl51 =(TextView)view.findViewById(R.id.mc_cn1_dl51 );mc_cn1_dl52 =(TextView)view.findViewById(R.id.mc_cn1_dl52 );mc_cn1_dl53 =(TextView)view.findViewById(R.id.mc_cn1_dl53 );mc_cn1_dl54 =(TextView)view.findViewById(R.id.mc_cn1_dl54 );mc_cn1_dl55 =(TextView)view.findViewById(R.id.mc_cn1_dl55 );mc_cn1_dl56 =(TextView)view.findViewById(R.id.mc_cn1_dl56 );
        mc_cn1_dl61 =(TextView)view.findViewById(R.id.mc_cn1_dl61 );mc_cn1_dl62 =(TextView)view.findViewById(R.id.mc_cn1_dl62 );mc_cn1_dl63 =(TextView)view.findViewById(R.id.mc_cn1_dl63 );mc_cn1_dl64 =(TextView)view.findViewById(R.id.mc_cn1_dl64 );mc_cn1_dl65 =(TextView)view.findViewById(R.id.mc_cn1_dl65 );mc_cn1_dl66 =(TextView)view.findViewById(R.id.mc_cn1_dl66 );
        //只支持6插件
         for(int i=0;i<6;i++)
         {
             switch (i){
                 case 0:
                      mc_cn1_scgl1.setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->pwd")       );
                      mc_cn1_fsgl1.setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->pwr")       );
                      mc_cn1_zbb1 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->vswr") /10.0);
                      mc_cn1_dy1  .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->temp") /10.0);
                      mc_cn1_wd1  .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->volt") /10.0);
                      mc_cn1_dl11 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr1")/10.0);
                      mc_cn1_dl21 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr2")/10.0);
                      mc_cn1_dl31 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr3")/10.0);
                      mc_cn1_dl41 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr4")/10.0);
                      mc_cn1_dl51 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr5")/10.0);
                      mc_cn1_dl61 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr6")/10.0);
                     break;
                 case 1:
                     mc_cn1_scgl2.setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->pwd")       );
                     mc_cn1_fsgl2.setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->pwr")       );
                     mc_cn1_zbb2 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->vswr") /10.0);
                     mc_cn1_dy2  .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->temp") /10.0);
                     mc_cn1_wd2  .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->volt") /10.0);
                     mc_cn1_dl12 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr1")/10.0);
                     mc_cn1_dl22 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr2")/10.0);
                     mc_cn1_dl32 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr3")/10.0);
                     mc_cn1_dl42 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr4")/10.0);
                     mc_cn1_dl52 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr5")/10.0);
                     mc_cn1_dl62 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr6")/10.0);
                     break;
                 case 2:
                    mc_cn1_scgl3.setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->pwd")       );
                    mc_cn1_fsgl3.setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->pwr")       );
                    mc_cn1_zbb3 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->vswr") /10.0);
                    mc_cn1_dy3  .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->temp") /10.0);
                    mc_cn1_wd3  .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->volt") /10.0);
                    mc_cn1_dl13 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr1")/10.0);
                    mc_cn1_dl23 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr2")/10.0);
                    mc_cn1_dl33 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr3")/10.0);
                    mc_cn1_dl43 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr4")/10.0);
                    mc_cn1_dl53 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr5")/10.0);
                    mc_cn1_dl63 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr6")/10.0);
                     break;
                 case 3:
                    mc_cn1_scgl4.setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->pwd")       );
                    mc_cn1_fsgl4.setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->pwr")       );
                    mc_cn1_zbb4 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->vswr") /10.0);
                    mc_cn1_dy4  .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->temp") /10.0);
                    mc_cn1_wd4  .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->volt") /10.0);
                    mc_cn1_dl14 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr1")/10.0);
                    mc_cn1_dl24 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr2")/10.0);
                    mc_cn1_dl34 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr3")/10.0);
                    mc_cn1_dl44 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr4")/10.0);
                    mc_cn1_dl54 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr5")/10.0);
                    mc_cn1_dl64 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr6")/10.0);
                     break;
                 case 4:
                     mc_cn1_scgl5.setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->pwd")       );
                     mc_cn1_fsgl5.setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->pwr")       );
                     mc_cn1_zbb5 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->vswr") /10.0);
                     mc_cn1_dy5  .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->temp") /10.0);
                     mc_cn1_wd5  .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->volt") /10.0);
                     mc_cn1_dl15 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr1")/10.0);
                     mc_cn1_dl25 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr2")/10.0);
                     mc_cn1_dl35 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr3")/10.0);
                     mc_cn1_dl45 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr4")/10.0);
                     mc_cn1_dl55 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr5")/10.0);
                     mc_cn1_dl65 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr6")/10.0);
                     break;
                 case 5:
                     mc_cn1_scgl6.setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->pwd")       );
                     mc_cn1_fsgl6.setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->pwr")       );
                     mc_cn1_zbb6 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->vswr") /10.0);
                     mc_cn1_dy6  .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->temp") /10.0);
                     mc_cn1_wd6  .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->volt") /10.0);
                     mc_cn1_dl16 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr1")/10.0);
                     mc_cn1_dl26 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr2")/10.0);
                     mc_cn1_dl36 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr3")/10.0);
                     mc_cn1_dl46 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr4")/10.0);
                     mc_cn1_dl56 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr5")/10.0);
                     mc_cn1_dl66 .setText(""+MainActivity.addrManager.valuemap.get("unit"+i+"->curr6")/10.0);
                     break;
             }

         }
    }


}
