package com.oneice.onepanel.Manager;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public   class AddrManager {
    public  static Map<String , Integer> addrmap,valuemap,basemcMap;//vaddrmap 用来反向找key
    public  static Map<Integer , String> vaddrmap;
    public   Map<Integer, Integer> getFirstAddrLenMap() {
        return firstAddrLenMap;
    }

    public void setFirstAddrLenMap(Map<Integer, Integer> firstAddrLenMap) {
        this.firstAddrLenMap = firstAddrLenMap;
    }

    private static volatile Map<Integer , Integer> firstAddrLenMap=null;

    public int getUnitsNum() {
        return unitsNum;
    }

    public void setUnitsNum(int unitsNum) {
        this.unitsNum = unitsNum;
        init_units();
    }

    private int  unitsNum=6;// 插件数

    public int getNowPage() {
        return nowPage;
    }

    public void setNowPage(int nowPage) {
        this.nowPage = nowPage;
    }

    private int nowPage=0;

    public void addrInit(){
        addrmap=new HashMap<String,Integer>();

        addrmap.put("系统复位", 0x0010);
        addrmap.put("本机地址", 0x0011);
        addrmap.put("RS232波特率设置", 0x0012);
        addrmap.put("RS485波特率设置", 0x0013);
        addrmap.put("IP地址1/2", 0x0014);
        addrmap.put("IP地址3/4", 0x0015);
        addrmap.put("子网掩码1/2", 0x0016);
        addrmap.put("子网掩码3/4", 0x0017);
        addrmap.put("网关1/2", 0x0018);
        addrmap.put("网关3/4", 0x0019);
        addrmap.put("网络端口", 0x001A);
        addrmap.put("远程本地切换", 0x001B);
        addrmap.put("校准1参数", 0x001C);
        addrmap.put("校准2参数", 0x001D);
        addrmap.put("校准3参数", 0x001E);
        addrmap.put("OPENLOOP", 0x007F);//开环控制
        addrmap.put("电源开关", 0x0080);
        addrmap.put("射频开关", 0x0081);
        addrmap.put("功率设置", 0x0082);
        addrmap.put("频率设置", 0x0083);
        addrmap.put("相位设置", 0x0084);
        addrmap.put("脉冲重复频率", 0x0085);
        addrmap.put("占空比设置", 0x0086);
        addrmap.put("脉冲宽度设置", 0x0087);
        addrmap.put("内外信号源切换", 0x0088);
        addrmap.put("告警信息", 0x00A0);
        addrmap.put("整机输出功率", 0x00A1);
        addrmap.put("整机反射功率", 0x00A2);
        addrmap.put("整机驻波比值", 0x00A3);
        addrmap.put("整机温度值", 0x00A4);
        addrmap.put("整机电压值", 0x00A5);
        addrmap.put("CURR_1", 0x00A7);
        addrmap.put("CURR_2", 0x00A8);
        addrmap.put("CURR_3", 0x00A9);
        addrmap.put("CURR_4", 0x00AA);
        addrmap.put("CURR_5", 0x00AB);
        addrmap.put("CURR_6", 0x00AC);
        addrmap.put("CURR_7", 0x00AD);
        addrmap.put("CURR_8", 0x00AE);
        addrmap.put("环形器反射功率", 0x00B2);
        addrmap.put("环形器反射驻波比", 0x00B3);
        addrmap.put("flow_temp0", 0xB4);
        addrmap.put("flow0", 0xB5);
        addrmap.put("整机输出过功率保护开/关", 0x00C0   );
        addrmap.put("整机输出过功率保护门限设置", 0x00C1  );
        addrmap.put("整机反射过功率开/关", 0x00C2  );
        addrmap.put("整机反射过功率模式", 0x00C3  );
        addrmap.put("整机反射过功率门限设置", 0x00C4  );
        addrmap.put("整机驻波保护开/关", 0x00C5  );
        addrmap.put("整机驻波保护模式", 0x00C6  );
        addrmap.put("整机驻波保护门限设置", 0x00C7  );
        addrmap.put("整机温度保护开/关", 0x00C8  );
        addrmap.put("整机温度保护门限设置", 0x00C9  );
        addrmap.put("整机电压保护开/关", 0x00CA  );
        addrmap.put("整机电压保护门限设置", 0x00CB  );
        addrmap.put("整机电流保护开/关", 0x00CC  );
        addrmap.put("整机电流保护门限设置", 0x00CD  );

        valuemap=new HashMap<String,Integer>();

        //VALUEINIT
        valuemap.put("系统复位", 0x0010);
        valuemap.put("本机地址", 0x0011);
        valuemap.put("RS232波特率设置", 0x0012);
        valuemap.put("RS485波特率设置", 0x0013);
        valuemap.put("IP地址1/2", 0x0014);
        valuemap.put("IP地址3/4", 0x0015);
        valuemap.put("子网掩码1/2", 0x0016);
        valuemap.put("子网掩码3/4", 0x0017);
        valuemap.put("网关1/2", 0x0018);
        valuemap.put("网关3/4", 0x0019);
        valuemap.put("网络端口", 0x001A);
        valuemap.put("远程本地切换", 0x001B);
        valuemap.put("校准1参数", 0x001C);
        valuemap.put("校准2参数", 0x001D);
        valuemap.put("校准3参数", 0x001E);
        valuemap.put("OPENLOOP", 0x007F);//开环控制
        valuemap.put("电源开关", 0x0080);
        valuemap.put("射频开关", 0x0081);
        valuemap.put("功率设置", 0x0082);
        valuemap.put("频率设置", 0x0083);
        valuemap.put("相位设置", 0x0084);
        valuemap.put("脉冲重复频率", 0x0085);
        valuemap.put("占空比设置", 0x0086);
        valuemap.put("脉冲宽度设置", 0x0087);
        valuemap.put("内外信号源切换", 0x0088);
        valuemap.put("告警信息", 0x00A0);
        valuemap.put("整机输出功率", 0x00A1);
        valuemap.put("整机反射功率", 0x00A2);
        valuemap.put("整机驻波比值", 0x00A3);
        valuemap.put("整机温度值", 0x00A4);
        valuemap.put("整机电压值", 0x00A5);
        valuemap.put("CURR_1", 0x00A7);
        valuemap.put("CURR_2", 0x00A8);
        valuemap.put("CURR_3", 0x00A9);
        valuemap.put("CURR_4", 0x00AA);
        valuemap.put("CURR_5", 0x00AB);
        valuemap.put("CURR_6", 0x00AC);
        valuemap.put("CURR_7", 0x00AD);
        valuemap.put("CURR_8", 0x00AE);
        valuemap.put("环形器反射功率", 0x00B2);
        valuemap.put("环形器反射驻波比", 0x00B3);
        valuemap.put("flow_temp0", 0xB4);
        valuemap.put("flow0", 0xB5);
        valuemap.put("整机输出过功率保护开/关", 0x00C0   );
        valuemap.put("整机输出过功率保护门限设置", 0x00C1  );
        valuemap.put("整机反射过功率开/关", 0x00C2  );
        valuemap.put("整机反射过功率模式", 0x00C3  );
        valuemap.put("整机反射过功率门限设置", 0x00C4  );
        valuemap.put("整机驻波保护开/关", 0x00C5  );
        valuemap.put("整机驻波保护模式", 0x00C6  );
        valuemap.put("整机驻波保护门限设置", 0x00C7  );
        valuemap.put("整机温度保护开/关", 0x00C8  );
        valuemap.put("整机温度保护门限设置", 0x00C9  );
        valuemap.put("整机电压保护开/关", 0x00CA  );
        valuemap.put("整机电压保护门限设置", 0x00CB  );
        valuemap.put("整机电流保护开/关", 0x00CC  );
        valuemap.put("整机电流保护门限设置", 0x00CD  );

        //2019/11/22新增功率计实时功率位
//        valuemap.put("功率计下显示值",0x00);
//        valuemap.put("功率计上显示值",0x00);
        basemcMap=new HashMap<>();
        basemcMap.put("插件警告信息基地址" , 0x0100);
        basemcMap.put("插件输出功率基地址" , 0x0101);
        basemcMap.put("插件反射功率基地址" , 0x0102);
        basemcMap.put("插件驻波比基地址"   , 0x0103 );
        basemcMap.put("插件温度值基地址"   , 0x0104 );
        basemcMap.put("插件电压值基地址"   , 0x0105 );
        basemcMap.put("插件电流1基地址"    ,  0x0120 );
        basemcMap.put("插件电流2基地址"    ,  0x0121 );
        basemcMap.put("插件电流3基地址"    ,  0x0122 );
        basemcMap.put("插件电流4基地址"    ,  0x0123 );
        basemcMap.put("插件电流5基地址"    ,  0x0124 );
        basemcMap.put("插件电流6基地址"    ,  0x0125 );
        init_units();
    }
    void init_units(){
        //插件地址
        //固化插件地址
        for(int i=0;i<unitsNum;i++){
            addrmap.put(new String("unit"+i+"->warring")  ,   0x0100+i*0x40);
            addrmap.put(new String("unit"+i+"->pwd"    )  ,   0x0101+i*0x40);
            addrmap.put(new String("unit"+i+"->pwr"    )  ,   0x0102+i*0x40);
            addrmap.put(new String("unit"+i+"->vswr"   )   ,  0x0103+i*0x40);
            addrmap.put(new String("unit"+i+"->temp"   )   ,  0x0104+i*0x40);
            addrmap.put(new String("unit"+i+"->volt"   )   ,  0x0105+i*0x40);
            addrmap.put(new String("unit"+i+"->curr1"  )   ,  0x0120+i*0x40);
            addrmap.put(new String("unit"+i+"->curr2"  )   ,  0x0121+i*0x40);
            addrmap.put(new String("unit"+i+"->curr3"  )   ,  0x0122+i*0x40);
            addrmap.put(new String("unit"+i+"->curr4"  )   ,  0x0123+i*0x40);
            addrmap.put(new String("unit"+i+"->curr5"  )   ,  0x0124+i*0x40);
            addrmap.put(new String("unit"+i+"->curr6"  )   ,  0x0125+i*0x40);
            //VALUEINIT
            valuemap.put(new String("unit"+i+"->warring")  ,  10);
            valuemap.put(new String("unit"+i+"->pwd"    )  ,  10);
            valuemap.put(new String("unit"+i+"->pwr"    )  ,  10);
            valuemap.put(new String("unit"+i+"->vswr"   )   , 10);
            valuemap.put(new String("unit"+i+"->temp"   )   , 10);
            valuemap.put(new String("unit"+i+"->volt"   )   , 10);
            valuemap.put(new String("unit"+i+"->curr1"  )   , 10);
            valuemap.put(new String("unit"+i+"->curr2"  )   , 10);
            valuemap.put(new String("unit"+i+"->curr3"  )   , 10);
            valuemap.put(new String("unit"+i+"->curr4"  )   , 10);
            valuemap.put(new String("unit"+i+"->curr5"  )   , 10);
            valuemap.put(new String("unit"+i+"->curr6"  )   , 10);
        }
        oderAddr();//初始化完后调用
        putdata2vaddr();//最后将valuemap和addrmap的缩影map附上值
    }

    void putdata2vaddr(){
          vaddrmap=new LinkedHashMap<>();
           valuemap.keySet();
           for(String key:addrmap.keySet()){
               vaddrmap.put(addrmap.get(key),key);
           }
    }

    public static  void oderAddr(){
        int maxaddr=0;
        if(firstAddrLenMap!=null){
            firstAddrLenMap.clear();
        }else {
            firstAddrLenMap = new LinkedHashMap<>();
        }
        //第一步找打最大地址
        for(String key:addrmap.keySet()){
            if(maxaddr<addrmap.get(key)){
                maxaddr=addrmap.get(key);
            }
        }
        int recording=0,baseaddr=0,asklen = 0;
        int MAXLEN=24;  //数据的最大反馈长度 可调
        //第二步遍历排序
        for(int i=0;i<=maxaddr;i++){
            if (addrmap.containsValue(i)){
                if(recording==0){
                    baseaddr=i;
                    asklen=0;
                }
                asklen++;
                recording=1;
                if(asklen==MAXLEN){
                    firstAddrLenMap.put(baseaddr,asklen);
                    asklen=0;
                    recording=0;
                }
            }else{
                if(recording==1){
                    if(asklen>0){
                        firstAddrLenMap.put(baseaddr,asklen);
                    }
                }
                recording=0;
             }
            if(i==maxaddr&&asklen>0){//记录下最后一组
                firstAddrLenMap.put(baseaddr,asklen);
            }
        }
    }


}
