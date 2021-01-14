package com.oneice.onepanel;
//mainactivity_special
import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;
import androidx.viewpager.widget.ViewPager;

import com.oneice.onepanel.Fragments.FragmentConset;
import com.oneice.onepanel.Fragments.FragmentProtect;
import com.oneice.onepanel.Fragments.FragmentUnit;
import com.oneice.onepanel.Fragments.FragmentWarning;
import com.oneice.onepanel.Fragments.OneLifeFragment;
import com.oneice.onepanel.Fragments.TabFragmentPagerAdapter;
import com.oneice.onepanel.Manager.AddrManager;
import com.oneice.onepanel.onetools.ConvertCode;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private List<OneLifeFragment> list;
    private ViewPager myViewPager;
    private TabFragmentPagerAdapter adapter;
    FragmentConset  fragmentConset;
    FragmentProtect fragmentProtect;
    FragmentUnit    fragmentUnit;
    FragmentWarning fragmentWarning;
    public  static MainActivity mainActivity=null;
    Button findaddr;
    Button restart;
    Button localremote;
    Button internalS;
    Button updata;
    ProgressBar progress_updata;
    Button clearmsgbox;
    DrawerLayout mainDrawer;
    Button openNetView,openConView;
    static TextView msgbox;
    TextView showfram;
    //通信连接相关
    Socket TcpSocket = null;
    EditText IP,Port;
    PrintWriter tcpWriter;
    BufferedReader tcpReader;
    Button tcpClient;
    TcpClient tcpclient=null;
    public static AddrManager addrManager;
    //viewpage切换
    Button viewpage_conset;
    Button viewpage_protect;
    Button viewpage_unit;
    Button viewpage_warning;
    //控制相关
    Button RF                  ;
    Button Power               ;
    Button       enter_Pset    ;
    Button step_Reduce_Pset    ;
    Button    step_Add_Pset    ;
    EditText edit_Pset         ;
    EditText edit_step_Pset    ;
    Thread tasker;
    Thread refer;
    static asker  modbusAsker=null;

    //刷新控件
    TextView text_PWD;
    TextView text_PWR;
    TextView text_ANR;
    TextView text_Temp;
    TextView CURR1,CURR2,CURR3,CURR4,CURR5,CURR6,CURR7,CURR8;
    TextView text_flow0,text_flow_temp0;
    TextView text_Volt;
    TextView text_Pset;
    TextView text_Vswr;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivity=this;
        // setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);//横屏
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();                                     //影藏标题栏方法1
            //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);           //影藏标题栏方法2
        }
        init();
        listenerinit();
        //开始就创建一个不断运行的线程 用于发送数据
        modbusAsker=new asker();
        tasker=new Thread(modbusAsker);
        tasker.start();
        requestPermission();
    }
    @Override
    public void finish() {
        //super.finish();//activity永远不会自动退出了，而是处于后台。
        moveTaskToBack(true);

    }

    public void requestPermission() {

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            Toast.makeText(this, "申请权限", Toast.LENGTH_SHORT).show();

            // 申请 相机 麦克风权限
            ActivityCompat.requestPermissions(this, new String[]{
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 100);
        }
    }


    /*

     * 方 法 名：init()

     * 功   能：变量初始化

     * @param ：无

     * @return：无

     * @throws：无

     * @data  ：

     */
    void init(){
        text_PWD  =(TextView)findViewById(R.id.text_PWD);
        text_PWR  =(TextView)findViewById(R.id.text_PWR);
        text_ANR  =(TextView)findViewById(R.id.text_ANR);
        text_Temp =(TextView)findViewById(R.id.text_Temp);
        text_flow0=(TextView)findViewById(R.id.text_flow0);
        text_flow_temp0=(TextView)findViewById(R.id.text_flow_temp0);
        CURR1 =(TextView)findViewById(R.id.CURR_1);
        CURR2 =(TextView)findViewById(R.id.CURR_2);
        CURR3 =(TextView)findViewById(R.id.CURR_3);
        CURR4 =(TextView)findViewById(R.id.CURR_4);
        CURR5 =(TextView)findViewById(R.id.CURR_5);
        CURR6 =(TextView)findViewById(R.id.CURR_6);
        CURR7 =(TextView)findViewById(R.id.CURR_7);
        CURR8 =(TextView)findViewById(R.id.CURR_8);
        text_Volt =(TextView)findViewById(R.id.text_Volt);
        text_Pset =(TextView)findViewById(R.id.text_Pset);
        text_Vswr =(TextView)findViewById(R.id.text_Vswr);
        viewpage_conset =(Button)findViewById(R.id.viewpage_conset);
        viewpage_protect=(Button)findViewById(R.id.viewpage_protect);
        viewpage_unit   =(Button)findViewById(R.id.viewpage_unit);
        viewpage_warning=(Button)findViewById(R.id.viewpage_warning);
        addrManager=new AddrManager();
        addrManager.addrInit();
        showfram=(TextView)findViewById(R.id.showfram);
        findaddr=(Button)findViewById(R.id.findaddr) ;
        restart=(Button)findViewById(R.id.restart) ;
        localremote=(Button)findViewById(R.id.local) ;

        internalS=(Button)findViewById(R.id.internalS) ;
        updata=(Button)findViewById(R.id.updata);
        progress_updata=(ProgressBar)findViewById(R.id.progress_updata);

        clearmsgbox=(Button)findViewById(R.id.clear_msgbox);
        mainDrawer=(DrawerLayout)findViewById(R.id.mainDrawer);
        openNetView=(Button)findViewById(R.id.openNetView);
        openConView=(Button)findViewById(R.id.openConView);
        msgbox=(TextView)findViewById(R.id.msgbox);
        msgbox.setMovementMethod(ScrollingMovementMethod.getInstance());
        //通信相关
        tcpClient=(Button)findViewById(R.id.client_TCP);

        IP=(EditText)findViewById(R.id.ip);
        Port=(EditText)findViewById(R.id.port);
        RF                  =(Button)findViewById(R.id.rf);
        Power               =(Button)findViewById(R.id.power);
        enter_Pset          =(Button)findViewById(R.id.      enter_Pset);
        step_Reduce_Pset    =(Button)findViewById(R.id.step_Reduce_Pset);
        step_Add_Pset       =(Button)findViewById(R.id.   step_Add_Pset);
        edit_Pset           =(EditText)findViewById(R.id.edit_Pset);
        edit_step_Pset      =(EditText)findViewById(R.id.edit_step_Pset);




        myViewPager = (ViewPager) findViewById(R.id.mainViewPager);
        //绑定点击事件
//        myViewPager.setOnPageChangeListener(new MyPagerChangeListener()) ;
        //把Fragment添加到List集合里面
        list = new ArrayList<>();


        fragmentConset=new FragmentConset();
        fragmentProtect=new FragmentProtect();
        fragmentUnit=new FragmentUnit();
        fragmentWarning=new FragmentWarning();

        list.add(fragmentConset);
        list.add(fragmentProtect);
        list.add(fragmentUnit);
        list.add(fragmentWarning);
        adapter = new TabFragmentPagerAdapter(getSupportFragmentManager(), list);
        myViewPager.setAdapter(adapter);
        myViewPager.setCurrentItem(0);  //初始化显示第一个页面
        ((OneLifeFragment)(adapter.getItem(myViewPager.getCurrentItem()))).setFragmentLife(true);
        myViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((OneLifeFragment)(adapter.getItem(position))).setFragmentLife(true);
                viewpage_botton_ch(position);
                for(int i=0;i<adapter.getCount();i++)
                {
                    if(i!=position){
                        ((OneLifeFragment)(adapter.getItem(i))).setFragmentLife(false);
                    }
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        Bundle bundle = new Bundle();
        bundle.putString("DATA","dsa");//这里的values就是我们要传的值
        adapter.getItem(0).setArguments(bundle);
    }

    /*

     * 方 法 名：listenerinit()

     * 功    能：注册主界面各个控件的监听器

     * @param： 无

     * @return：无

     * @throws：无

     * @data：

     */
    void listenerinit(){
        findaddr   .setOnClickListener(this);
        restart    .setOnClickListener(this);
        localremote.setOnClickListener(this);

        internalS  .setOnClickListener(this);
        updata     .setOnClickListener(this);
        viewpage_conset   .setOnClickListener(this);
        viewpage_protect  .setOnClickListener(this);
        viewpage_unit     .setOnClickListener(this);
        viewpage_warning  .setOnClickListener(this);
        clearmsgbox.setOnClickListener(this);
        openNetView.setOnClickListener(this);
        openConView.setOnClickListener(this);

        //通信相关
        tcpClient.setOnClickListener(this);

        //控制相关
        RF.setOnClickListener(this);
        Power.setOnClickListener(this);
        enter_Pset.setOnClickListener(this);
        step_Reduce_Pset.setOnClickListener(this);
        step_Add_Pset.setOnClickListener(this);
        edit_Pset.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    // TODO
                    onClick(findViewById( R.id.enter_Pset));
                }
                return false;
            }
        });


    }


    public void Tcpislife(boolean life){
        if(life){
            modbusAsker.life=true;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tcpClient.setBackgroundResource(R.drawable.button_on);
                }
            });
            showmsgs("connect true");
        }else{
            modbusAsker.life=false;
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tcpClient.setBackgroundResource(R.drawable.button_off);
                }
            });
            showmsgs("connect false");
        }


    }

    /**
     *
     * 发送区
     * */
    final static int READREGISTER=0x03;      //0x03	读寄存器数据
    final static int WRITEREGISTER_ONE=0x06; //0x06	写单个寄存器数据
    final static int WRITEREGISTER_MORE=0x10;//0x10	写多个寄存器数据

    public int getADDR() {
        return ADDR;
    }

    public void setADDR(int ADDR) {
        this.ADDR = ADDR;
    }

    private int ADDR=10;
    boolean askBussy=false;
    public void sendconmod(int addr, int set){ //单地址写控制
        int wite=0;
        while(askBussy) {
            wite++;
            if(wite>100)
                break;
            try {

                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        //当为忙时要排队
        byte[] sbuilder=new byte[8];
        sbuilder[0]=(byte)ADDR;
        sbuilder[1]=(byte)WRITEREGISTER_ONE;
        sbuilder[2]=(byte)(addr/256);
        sbuilder[3]=(byte)(addr%256);
        sbuilder[4]=(byte)(set/256);
        sbuilder[5]=(byte)(set%256);
        int crc= ConvertCode.crcget(0,sbuilder,6);
        sbuilder[6]=(byte)(crc/256);
        sbuilder[7]=(byte)(crc%256);
        byte[] sbuilder1=new byte[14];

        sbuilder1[0]=0;
        sbuilder1[1]=0;
        sbuilder1[2]=0;
        sbuilder1[3]=0;
        sbuilder1[4]=0;
        sbuilder1[5]=0;

        for(int i=0;i<8;i++){
            sbuilder1[i+6]=  sbuilder[i];
        }

        senddatas(ConvertCode.bytes2HexString(sbuilder1));
        askBussy=true;//有反馈的让访问忙为 真
    }

    void modbusAsker(int addr, int len){

        byte[] sbuilder=new byte[8];

        sbuilder[0]=(byte)ADDR;
        sbuilder[1]=(byte)READREGISTER;
        sbuilder[2]=(byte)(addr/256);
        sbuilder[3]=(byte)(addr%256);
        sbuilder[4]=(byte)(len/256);
        sbuilder[5]=(byte)(len%256);
        int crc=ConvertCode.crcget(0,sbuilder,6);
        sbuilder[6]=(byte)(crc/256);
        sbuilder[7]=(byte)(crc%256);
        byte[] sbuilder1=new byte[14];

        sbuilder1[0]=0;
        sbuilder1[1]=0;
        sbuilder1[2]=0;
        sbuilder1[3]=0;
        sbuilder1[4]=0;
        sbuilder1[5]=0;

        for(int i=0;i<8;i++){
            sbuilder1[i+6]=  sbuilder[i];
        }

        senddatas(ConvertCode.bytes2HexString(sbuilder1));
        askBussy=true;//有反馈的让访问忙为 真
    }

    int nowBaseAddr=0;//记录当前访问的modbus的首地址
    int askT=9;
    public int getAskT() {
        return askT;
    }
    public void setAskT(int askT) {
        this.askT = askT;
    }

    public class asker implements  Runnable{

        public Boolean getLife() {
            return life;
        }

        public void setLife(Boolean life) {
            this.life = life;
        }
        private Boolean life=false;
        public  Iterator<Map.Entry<Integer, Integer>> entries=null ;
        int modbuSeeBusy=0;
        int forbeterflag=0;
        boolean forbeter=false;

        @Override
        public void run() {
            while(true){
                if(life){

                    if(!askBussy){
                        modbuSeeBusy=0;
                        if( addrManager.getFirstAddrLenMap()!=null&& addrManager.getFirstAddrLenMap().size()>0){
                            if (entries == null) {
                                entries = addrManager.getFirstAddrLenMap().entrySet().iterator();
                            }
                            if (entries.hasNext()) {
                                Map.Entry<Integer, Integer> entry = entries.next();
                                nowBaseAddr = entry.getKey();
                                //低地址优化
                                if(forbeterflag<2&&nowBaseAddr>=0x0100)//优化间隔刷新插件
                                {
                                    forbeterflag++;
                                    entries = addrManager.getFirstAddrLenMap().entrySet().iterator();
                                    if (entries.hasNext()){
                                        entry = entries.next();
                                        nowBaseAddr = entry.getKey();
                                    }
                                }
                                if(!Updating) {
                                    modbusAsker(nowBaseAddr, entry.getValue());
                                }
                            }else{
                                forbeterflag=0;
                                entries = addrManager.getFirstAddrLenMap().entrySet().iterator();
                            }
                        }
                    }else{
                        modbuSeeBusy++;
                        if(modbuSeeBusy>40){//发了多少次依然在忙 就证明多半是断开了
                            modbuSeeBusy=0;
                            System.out.println("out time");
                            askBussy=false;
                        }
                    }
                }else
                    {
                    entries =null;
                    }
                try
                {
                    Thread.currentThread().sleep(askT);//毫秒

                }
                catch(Exception e){}
            }

        }
    }


    int disconflag=0;
    void senddatas(String data){

        if(!Updating) {
            if (tcpclient == null || !tcpclient.getLife()) {

            } else {
                if(modbusAsker.life)
                tcpSend(data);
            }
        }

    }
    /**
     *解析区
     * */

    void analysis_updata(byte[] data,int tlen){

        if(tlen>10&&tlen<17){//长度校验提升效率与准确性
            int a=8;
            if(a+3<tlen&&data[a]=='d'&&data[a+1]=='e'&&data[a+2]=='a'&&data[a+3]=='r'){
                //准备发送下一条
                if(Updating){
                    Updatapro++ ;
                    doUpData();
                }
                return;
            }
            if(a+3<tlen&&data[a]=='n'&&data[a+1]=='o'&&data[a+2]=='c'&&data[a+3]=='o'){
                //数据接收完毕 准备发送下一条
                showmsgs("CHIP START CODE FAILED!");
                showmsgs("PLEACE FIER CODE-->IT'S NEED!");
                return;
            }
            if(a+3<tlen&&data[a]=='o'&&data[a+1]=='v'&&data[a+2]=='e'&&data[a+3]=='r'){
                //升级完成

                showmsgs("☞SUCCESS!☜");
                Message msg=new Message();
                msg.obj="updataend";
                handler.sendMessage(msg);
            }
            if(a+3<tlen&&data[a]=='l'&&data[a+1]=='o'&&data[a+2]=='s'&&data[a+3]=='t'){
                showmsgs("☠☠FAIL! TYRAGAIN！☠☠");
                updata_over();
                //升级失败
            }

        }

    }




    //modbus解析
    List<Byte> getdata=null;
    List<Byte> contxt=null;
    static int Afaddr=0;
    int framrecoder=0;
    long startTime;
    public void analysis_Modbus(byte[] data,int tlen){
        analysis_updata(data,tlen);
        if(Afaddr==1){
            showmsgs("ADDR "+data[6]);
            setADDR(data[6]);
            Afaddr=0;
            askBussy=false;
            return;
        }

        for(int i=6; i<7;i++){//不做循环
            if((data[i]&0xff)==(ADDR&0xff)){

                if((i+1<tlen)&&((data[i+1]&0xff)==READREGISTER)){
                    if(i+1+2<tlen){
                        int len=0;
                        len=data[i+2];
                        //长度校验
                        if(tlen==len+9){

                            if(contxt!=null)
                                contxt.clear();
                            else
                                contxt=new ArrayList<>();

                            for(int j=0;j<len&&(j+9)<tlen;j++){
                                contxt.add(data[9+j]);
                            }
                            modbusPutdatas(contxt);
                            askBussy=false;//读取数据又返回 需要解除占用
                            return;
                        }
                    }
                }
                //设置返回
                if(((data[7]&0xff)==WRITEREGISTER_ONE)){
                    if(tlen>6){
                        int waddr =0,wvaue=0;
                        waddr=(0xff&data[8])*256+(data[9]&0xff);
                        wvaue=(0xff&data[10])*256;
                        wvaue= wvaue      + (0xff&data[11]);
                        if (addrManager.vaddrmap.containsKey(waddr)){
                            String conS=addrManager.vaddrmap.get(waddr);
                            if(addrManager.valuemap.containsKey(conS)){
                                if(addrManager.valuemap.get(conS)!=wvaue){
                                    addrManager.valuemap.put(conS,wvaue) ;
                                    Message msg=new Message();
                                    msg.obj=conS;
                                    refransh(conS);
                                    showmsgs(conS+" "+wvaue+" OK");
                                }
                            }
                        }
                        //  readback_busy=0;//一次数据交易结束
                    }
                    askBussy=false;
                }



            }
        }
    }
    public void modbusPutdatas(List<Byte> data){
        int  needlen =addrManager.getFirstAddrLenMap().get(nowBaseAddr)*2;
        if(data.size() == needlen) {
            for (int i = 0; i < needlen; i++) {
                int index = nowBaseAddr + i;
                if (addrManager.addrmap.containsValue(index)) {
                    if (i * 2 + 1 < data.size()) {
                        int v = 0;
                        v = (data.get(i * 2) & 0xff) * 256;
                        int v1 = data.get(i * 2 + 1) & 0xff;
                        v = v + v1;
                        if (addrManager.valuemap.containsKey(addrManager.vaddrmap.get(index)) && addrManager.valuemap.get(addrManager.vaddrmap.get(index)) != v) {
                            addrManager.valuemap.put(addrManager.vaddrmap.get(index), v);
                            // valuechanged=true;
                            refransh(addrManager.vaddrmap.get(index));
                        }
                    }
                }
            }
        }else showmsgs("DBE:N:"+needlen+" T:"+data.size());

    }

    /*

     * 方 法 名：refransh(String refname)

     * 功    能：根据反馈刷新界面

     * @param：String refname 刷新控件名称

     * @return：无

     * @throws：无

     * @data：

     */
    public void ui_refrash(final String refname){
        fragmentConset.refrashinterface(refname);
        fragmentProtect.refrashinterface(refname);
        fragmentUnit.refrashinterface(refname);
        fragmentWarning.refrashinterface(refname);

        if(addrManager.valuemap.containsKey(refname))
        {//刷新存在项
            if(refname=="环形器反射功率"){
                text_ANR.setText("ANR:"+addrManager.valuemap.get(refname)+"W");
                return;
            }
            if(refname=="整机输出功率"){
                text_PWD.setText("PWD:"+addrManager.valuemap.get(refname)+"W");
                return;
            }
            if(refname=="整机反射功率"){
                text_PWR.setText("PWR:"+addrManager.valuemap.get(refname)+"W");
                return;}
            if(refname=="整机驻波比值"){
                text_Vswr.setText("Vswr:"+addrManager.valuemap.get(refname)/10.0);
                return;}
            if(refname=="整机温度值"){
                text_Temp.setText("Temp:"+addrManager.valuemap.get(refname)/10.0+"℃");
                return ;}
            if(refname=="整机电压值"){
                text_Volt.setText("Volt:"+addrManager.valuemap.get(refname)/10.0+"V");
                return;}

            if(refname=="flow0"){
                text_flow0.setText("Flow:"+addrManager.valuemap.get(refname)/10.0+"L/Min");
                return;}
            if(refname=="flow_temp0"){
                text_flow_temp0.setText("FlowTem:"+addrManager.valuemap.get(refname)/10.0+"℃");
                return;}

            if(refname=="CURR_1"){
                CURR1.setText("Curr:"+addrManager.valuemap.get(refname)/10.0+"A");
                return;}
            if(refname=="CURR_2"){
                CURR2.setText("Curr:"+addrManager.valuemap.get(refname)/10.0+"A");
                return;}
            if(refname=="CURR_3"){
                CURR3.setText("Curr:"+addrManager.valuemap.get(refname)/10.0+"A");
                return;}
            if(refname=="CURR_4"){
                CURR4.setText("Curr:"+addrManager.valuemap.get(refname)/10.0+"A");
                return;}
            if(refname=="CURR_5"){
                CURR5.setText("Curr:"+addrManager.valuemap.get(refname)/10.0+"A");
                return;}
            if(refname=="CURR_6"){
                CURR4.setText("Curr:"+addrManager.valuemap.get(refname)/10.0+"A");
                return;}
            if(refname=="CURR_7"){
                CURR7.setText("Curr:"+addrManager.valuemap.get(refname)/10.0+"A");
                return;}
            if(refname=="CURR_8"){
                CURR8.setText("Curr:"+addrManager.valuemap.get(refname)/10.0+"A");
                return;}


            if(refname=="功率设置"){
                text_Pset.setText("Pset:"+addrManager.valuemap.get(refname)+"W");
                return;}

            //刷新控件
            if(refname=="远程本地切换"){
                if(addrManager.valuemap.get(refname)==1)
                {
                    localremote.setText("LOCAL");
                    localremote.setBackgroundResource(R.drawable.button_on);
                }else{
                    localremote.setText("REMOTE");
                    localremote.setBackgroundResource(R.drawable.button_off);
                }
                return;
            }
            if(refname=="电源开关"){
                if(addrManager.valuemap.get(refname)==1)
                {
                    showmsgs("power is ON");
                    Power.setText("POWEROFF");
                    Power.setBackgroundResource(R.drawable.button_on);
                }else{
                    showmsgs("power is OFF");
                    Power.setText("POWERON");
                    Power.setBackgroundResource(R.drawable.button_off);
                }
                return;
            }
            if(refname== "射频开关"){

                if(addrManager.valuemap.get(refname)==1)
                {
                    showmsgs("rf is ON");
                    RF.setText("RF_OFF");
                    RF.setBackgroundResource(R.drawable.button_on);
                }else{
                    showmsgs("rf is OFF");
                    RF.setText("RF_ON");
                    RF.setBackgroundResource(R.drawable.button_off);
                }
                return;
            }
            if(refname=="内外信号源切换"){
                if(addrManager.valuemap.get(refname)==1)
                {
                    internalS.setText("INTERNALS");
                    internalS.setBackgroundResource(R.drawable.button_on);
                }else{
                    internalS.setText("EXTERNALS");
                    internalS.setBackgroundResource(R.drawable.button_off);
                }
                return;
            }



            if(refname=="告警信息"){
                //1开启 0关闭。 0位到6位依次为：输出过功率、反射过功率、驻波、温度、电压、电流 连锁（详见注释）

                if(addrManager.valuemap.get(refname)==0x00){
                    //无警告
                }else
                if(((addrManager.valuemap.get(refname)>>0)&0x01)==1){
                    showmsgs("Output Overpower");
                }else
                if(((addrManager.valuemap.get(refname)>>1)&0x01)==1){
                    showmsgs("Reflective overpower");
                }else
                if(((addrManager.valuemap.get(refname)>>2)&0x01)==1){

                    showmsgs("Standing wave anomaly");

                }else
                if(((addrManager.valuemap.get(refname)>>3)&0x01)==1){
                    showmsgs("Temperature anomaly");
                }else
                if(((addrManager.valuemap.get(refname)>>4)&0x01)==1){
                    showmsgs("Voltage abnormality");
                }else
                if(((addrManager.valuemap.get(refname)>>5)&0x01)==1){
                    showmsgs("Abnormal current");
                }else
                if(((addrManager.valuemap.get(refname)>>6)&0x01)==1){
                    showmsgs("Interlocking anomaly");
                }
                return;
            }

        }
    }

    public void refransh(final String refname){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ui_refrash(refname);
            }
        });

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg){
            //获得通过handler.sendEmptyMessage发送的消息编码

            String rfname=msg.obj.toString();
            if(rfname.equals("updataend")){
                //代码块 msgbox弹出
                //创建一个 AlertDialog.Builder 对象
                AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
                //给对话框添加title
                builder.setTitle("msg");
                //给对话框添加内容
                builder.setMessage("RESTART NOW?");

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        updata_over();
                    }
                });

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        tcpSend("000000000000"+ConvertCode.string2HexString("resys"));
                        updata_over();
                        showmsgs("if the device does not support software restarts,manually restart when need!");
                    }
                });
                //切记勿忘~开启dialog
                builder.show();
            }else if(rfname.equals("updatastart")){
                doUpData();
                updatastartsendok=true;
                showmsgs("UPSTART");
            }else{
                String nowmsg = mainActivity.msgbox.getText().toString();
                mainActivity.msgbox.setText(nowmsg+"\n"+rfname);
                if(nowmsg.length()>1000){
                    mainActivity.msgbox.setText("");
                }

            }
            /* 处理代码 */
        }
    };





    /*

     * 方 法 名：addrighter(String addr,int port)

     * 功    能：检测输入IP地址和端口号的正确性

     * @param：String addr,IP地址 ,int port 访问的目标端口号

     * @return：boolean true 检测无误 false 检测出有误

     * @throws：无

     * @data：

     */
    boolean addrighter(String addr,int port){
        if(port>1023&&port<65536){
            int len;
            String[] data=addr.split("\\.");
            if( (len=data.length )==4){
                return true;

            }else{

                showmsgs("IP input erro\n" +
                        "form:?.?.?.?");
                return false;
            }
        }else{
            showmsgs("port num input erro\n" +
                    "scope:1024-65535");
            return false;
        }
    }
    /*

     * 方 法 名：tcpSend(String msg)

     * 功    能：TCP客户端模式发送接口

     * @param：String msg，发送的内容

     * @return：无

     * @throws：无

     * @data：

     */
    void tcpSend(String  msg){
        //发送数据
        //        if(TcpSocket!=null&&TcpSocket.isConnected()){
        //            tcpWriter.println(msg);
        //        }
        if(tcpclient!=null&&tcpclient.getLife()){
            tcpclient.getChannel().writeAndFlush(msg);
        }
    }


    /*

     * 方 法 名：showmsgs(String msg)

     * 功    能：一些调试信息的显示

     * @param：msg 显示的内容

     * @return：无

     * @throws：无

     * @data：

     */
    public static void showmsgs(String msg){
        Message msgs=new Message();
        msgs.obj=msg;
        mainActivity.handler.sendMessage(msgs);
    }

    public void viewpage_botton_ch(int w){
        switch (w){
            case 0:
                viewpage_conset.setBackgroundColor(0xFF0000FF);
                viewpage_protect.setBackgroundColor(0);
                viewpage_unit.setBackgroundColor(0);
                viewpage_warning.setBackgroundColor(0);
                break;
            case 1:
                viewpage_conset.setBackgroundColor(0);
                viewpage_protect.setBackgroundColor(0xFF0000FF);
                viewpage_unit.setBackgroundColor(0);
                viewpage_warning.setBackgroundColor(0);
                break;
            case 2:
                viewpage_conset.setBackgroundColor(0);
                viewpage_protect.setBackgroundColor(0);
                viewpage_unit.setBackgroundColor(0xFF0000FF);
                viewpage_warning.setBackgroundColor(0);
                break;
            case 3:
                viewpage_conset.setBackgroundColor(0);
                viewpage_protect.setBackgroundColor(0);
                viewpage_unit.setBackgroundColor(0);
                viewpage_warning.setBackgroundColor(0xFF0000FF);
                break;
        }

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onClick(View v) {
        consend(v.getId());//控制相关
        //主界面
        switch (v.getId()){
            case R.id.findaddr:
                byte[] sbuilder=new byte[8];
                sbuilder[0]=(byte)ADDR;
                sbuilder[1]=(byte)READREGISTER;
                sbuilder[2]=(byte)(0x0011/256);
                sbuilder[3]=(byte)(0x0011%256);
                sbuilder[4]=(byte)(0x0002/256);
                sbuilder[5]=(byte)(0x0002%256);
                int crc=ConvertCode.crcget(0,sbuilder,6);
                sbuilder[6]=(byte)(crc/256);
                sbuilder[7]=(byte)(crc%256);
                senddatas(ConvertCode.bytes2HexString(sbuilder));
                askBussy=true;//有反馈的让访问忙为 真

                Afaddr=1;

                break;
            case R.id.clear_msgbox:
                msgbox.setText("");
                break;
            case R.id.openConView:
                mainDrawer.openDrawer(Gravity.END);
                break;
            case R.id.openNetView:
                mainDrawer.openDrawer(Gravity.START);
                break;

            case R.id.client_TCP:
                if(tcpclient!=null&&tcpclient.channel!=null&&tcpclient.getLife())
                {
                    modbusAsker.life=false;//停止moudbus访问
                    tcpclient.channel.disconnect();
                    tcpclient.channel.close();
                }
                else
                if(!IP.getText().toString().equals("")&&!Port.getText().toString().equals("")){

                    if(addrighter(IP.getText().toString(),Integer.parseInt(Port.getText().toString())))
                    {
                        tcpclient=new TcpClient(IP.getText().toString(),Integer.parseInt(Port.getText().toString()));
                        System.out.println("TCP1111");
                        Afaddr=1;
                    }
                }
                break;
            case R.id.updata:

                //创建一个 AlertDialog.Builder 对象
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                //给对话框添加title
                builder.setTitle("Updata?");
                //给对话框添加内容
                builder.setMessage("make sure your bin");

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                        intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
                        intent.addCategory(Intent.CATEGORY_OPENABLE);
                        startActivityForResult(intent,1);
                    }
                });
                //切记勿忘~开启dialog
                builder.show();
                break;
            case R.id.viewpage_conset:

                myViewPager.setCurrentItem(0);
                viewpage_botton_ch(0);
                break;
            case R.id.viewpage_protect :
                viewpage_botton_ch(1);
                myViewPager.setCurrentItem(1);
                break;
            case R.id.viewpage_unit:
                int c=myViewPager.getChildCount();
                c=c;
                viewpage_botton_ch(2);
                myViewPager.setCurrentItem(2);
                break;
            case R.id.viewpage_warning:
                viewpage_botton_ch(3);
                myViewPager.setCurrentItem(3);
                break;
        }

    }
    /**
     * 单地址写 控制指令发送
     * */
    public void   consend(int id){
        switch (id){
            case R.id.restart:
                sendconmod(addrManager.addrmap.get("系统复位"),1);
                break;
            case R.id.local:
                if(localremote.getText().equals("LOCAL"))
                {
                    sendconmod(addrManager.addrmap.get("远程本地切换"),1);
                }else{
                    sendconmod(addrManager.addrmap.get("远程本地切换"),0);
                }
                break;
            case R.id.internalS:
                if(localremote.getText().equals("INTERNALS"))
                {
                    sendconmod(addrManager.addrmap.get("内外信号源切换"),1);
                }else{
                    sendconmod(addrManager.addrmap.get("内外信号源切换"),0);
                }
                break;

            case R.id.rf:
                if(RF.getText().equals("RF_ON")){
                    sendconmod(addrManager.addrmap.get("射频开关"),1);
                }else{
                    sendconmod(addrManager.addrmap.get("射频开关"),0);
                }
                break;
            case R.id.power:
                //if(addrManager.addrmap.containsKey("电源开关"))
                if(Power.getText().equals("POWERON")){
                    sendconmod(addrManager.addrmap.get("电源开关"),1);
                }else{
                    sendconmod(addrManager.addrmap.get("电源开关"),0);
                }
                break;
            //**Pset*//
            case R.id.enter_Pset:
                if(!edit_Pset.getText().toString().equals("")){
                    sendconmod(addrManager.addrmap.get("功率设置"),Integer.valueOf(edit_Pset.getText().toString()));
                }
                break;
            case R.id.step_Reduce_Pset:
                if(!edit_step_Pset.getText().toString().equals("")){
                    int sdata=addrManager.valuemap.get("功率设置")-Integer.valueOf(edit_step_Pset.getText().toString());
                    if(addrManager.valuemap.get("功率设置")<Integer.valueOf(edit_step_Pset.getText().toString())){
                        sdata=0;
                    }
                    sendconmod(addrManager.addrmap.get("功率设置"),sdata);
                }
                break;
            case R.id.step_Add_Pset:
                if(!edit_step_Pset.getText().toString().equals("")){
                    int sdata=addrManager.valuemap.get("功率设置")+Integer.valueOf(edit_step_Pset.getText().toString());
                    if(sdata>60000){
                        sdata=60000;
                    }
                    sendconmod(addrManager.addrmap.get("功率设置"),sdata);
                }
                break;
            default:break;
        }

    }
    byte[] buffer = new byte[1024];
    private void readLocalFile() throws IOException {
        String fileName = "abcd.raw";
        InputStream inputStream = getAssets().open(fileName);
        int n = -1;
        while ((n = inputStream.read(buffer,0,1024)) != -1) {
            //buffer为读出来的二进制数据，长度1024，最后一段数据小于1024
        }
        inputStream.close();
    }




    public class FileUtils {

        private Context context;

        public FileUtils(Context context) {
            this.context = context;
        }

        public String getFilePathByUri(Uri uri) {
            // 以 file:// 开头的
            if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
                return uri.getPath();
            }
            // 以/storage开头的也直接返回
            if (isOtherDocument(uri)) {
                return uri.getPath();
            }
            // 版本兼容的获取！
            String path = getFilePathByUri_BELOWAPI11(uri);
            if (path != null) {
                // LogUtils.d("getFilePathByUri_BELOWAPI11获取到的路径为：" + path);
                return path;
            }
            path = getFilePathByUri_API11to18(uri);
            if (path != null) {
                // LogUtils.d("getFilePathByUri_API11to18获取到的路径为：" + path);
                return path;
            }
            path = getFilePathByUri_API19(uri);
            //LogUtils.d("getFilePathByUri_API19获取到的路径为：" + path);
            return path;
        }

        private String getFilePathByUri_BELOWAPI11(Uri uri) {
            // 以 content:// 开头的，比如 content://media/extenral/images/media/17766
            if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
                String path = null;
                String[] projection = new String[]{MediaStore.Images.Media.DATA};
                Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
                if (cursor != null) {
                    if (cursor.moveToFirst()) {
                        int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        if (columnIndex > -1) {
                            path = cursor.getString(columnIndex);
                        }
                    }
                    cursor.close();
                }
                return path;
            }
            return null;
        }

        private String getFilePathByUri_API11to18(Uri contentUri) {
            String[] projection = {MediaStore.Images.Media.DATA};
            String result = null;
            CursorLoader cursorLoader = new CursorLoader(context, contentUri, projection, null, null, null);
            Cursor cursor = cursorLoader.loadInBackground();
            if (cursor != null) {
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                cursor.moveToFirst();
                result = cursor.getString(column_index);
                cursor.close();
            }
            return result;
        }

        private String getFilePathByUri_API19(Uri uri) {
            // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
            if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme()) && Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                if (DocumentsContract.isDocumentUri(context, uri)) {
                    if (isExternalStorageDocument(uri)) {
                        // ExternalStorageProvider
                        String docId = DocumentsContract.getDocumentId(uri);
                        String[] split = docId.split(":");
                        String type = split[0];
                        if ("primary".equalsIgnoreCase(type)) {
                            if (split.length > 1) {
                                return Environment.getExternalStorageDirectory() + "/" + split[1];
                            } else {
                                return Environment.getExternalStorageDirectory() + "/";
                            }
                            // This is for checking SD Card
                        }
                    } else if (isDownloadsDocument(uri)) {
                        //下载内容提供者时应当判断下载管理器是否被禁用
                        int stateCode = context.getPackageManager().getApplicationEnabledSetting("com.android.providers.downloads");
                        if (stateCode != 0 && stateCode != 1) {
                            return null;
                        }
                        String id = DocumentsContract.getDocumentId(uri);
                        // 如果出现这个RAW地址，我们则可以直接返回!
                        if (id.startsWith("raw:")) {
                            return id.replaceFirst("raw:", "");
                        }
                        if (id.contains(":")) {
                            String[] tmp = id.split(":");
                            if (tmp.length > 1) {
                                id = tmp[1];
                            }
                        }
                        Uri contentUri = Uri.parse("content://downloads/public_downloads");
                        // LogUtils.d("测试打印Uri: " + uri);
                        try {
                            contentUri = ContentUris.withAppendedId(contentUri, Long.parseLong(id));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        String path = getDataColumn(contentUri, null, null);
                        if (path != null) return path;
                        // 兼容某些特殊情况下的文件管理器!
                        String fileName = getFileNameByUri(uri);
                        if (fileName != null) {
                            path = Environment.getExternalStorageDirectory().toString() + "/Download/" + fileName;
                            return path;
                        }
                    } else if (isMediaDocument(uri)) {
                        // MediaProvider
                        String docId = DocumentsContract.getDocumentId(uri);
                        String[] split = docId.split(":");
                        String type = split[0];
                        Uri contentUri = null;
                        if ("image".equals(type)) {
                            contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                        } else if ("video".equals(type)) {
                            contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                        } else if ("audio".equals(type)) {
                            contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                        }
                        String selection = "_id=?";
                        String[] selectionArgs = new String[]{split[1]};
                        return getDataColumn(contentUri, selection, selectionArgs);
                    }
                }
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private String getFileNameByUri(Uri uri) {
            String relativePath = getFileRelativePathByUri_API18(uri);
            if (relativePath == null) relativePath = "";
            final String[] projection = {
                    MediaStore.MediaColumns.DISPLAY_NAME
            };
            try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DISPLAY_NAME);
                    return relativePath + cursor.getString(index);
                }
            }
            return null;
        }

        private String getFileRelativePathByUri_API18(Uri uri) {
            final String[] projection;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.Q) {
                projection = new String[]{
                        MediaStore.MediaColumns.RELATIVE_PATH
                };
                try (Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null)) {
                    if (cursor != null && cursor.moveToFirst()) {
                        int index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.RELATIVE_PATH);
                        return cursor.getString(index);
                    }
                }
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.KITKAT)
        private String getDataColumn(Uri uri, String selection, String[] selectionArgs) {
            final String column = MediaStore.Images.Media.DATA;
            final String[] projection = {column};
            try (Cursor cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    final int column_index = cursor.getColumnIndexOrThrow(column);
                    return cursor.getString(column_index);
                }
            } catch (IllegalArgumentException iae) {
                iae.printStackTrace();
            }
            return null;
        }

        private boolean isExternalStorageDocument(Uri uri) {
            return "com.android.externalstorage.documents".equals(uri.getAuthority());
        }

        private boolean isOtherDocument(Uri uri) {
            // 以/storage开头的也直接返回
            if (uri != null && uri.getPath() != null) {
                String path = uri.getPath();
                if (path.startsWith("/storage")) {
                    return true;
                }
                if (path.startsWith("/external_files")) {
                    return true;
                }
            }
            return false;
        }

        private boolean isDownloadsDocument(Uri uri) {
            return "com.android.providers.downloads.documents".equals(uri.getAuthority());
        }

        private boolean isMediaDocument(Uri uri) {
            return "com.android.providers.media.documents".equals(uri.getAuthority());
        }
    }
    boolean updatastartsendok=false;
    int upwithbusyflag=0;
    void updata_start(){
        flen=0;
        Updatapro=0;
        Updating=true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while(askBussy){
                    upwithbusyflag++;
                    if(upwithbusyflag>10){
                        upwithbusyflag=0;
                        askBussy=false;
                    }
                    try {
                        Thread.sleep(30);//等待发完
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ;}//等待发送完毕
                try {
                    Thread.sleep(30);//等待发完
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                updatastartsendok=false;
                while(!updatastartsendok){
                    Message msg=new Message();
                    msg.obj="updatastart";
                    handler.sendMessage(msg);
                    try {
                        Thread.sleep(500);//等待发完
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

            }
        }).start();


        progress_updata.setVisibility(View.VISIBLE);
    }
    //升级结束时调用
    void updata_over(){
        Updatapro=0;
        Updating=false;
        progress_updata.setVisibility(View.GONE);
        progress_updata.setProgress(0);
//     if(tcpclient.channel!=null){
//     tcpclient.disconnect();
//     }
        if(inputStream!=null){
            try {
                inputStream.close();
                inputStream=null;
            } catch (IOException e) {
                e.printStackTrace();
                inputStream=null;
            }
        }

    }
    long flen=0;
    final int upsendlen=128;
    public void doUpData(){
        if(inputStream!=null){
            if(Updatapro==0){//发送开始指令
                tcpSend("000000000000"+ConvertCode.string2HexString("start"));
            }else{

                byte buffer[] = new byte[upsendlen];
                int len = 0;
                try {
                    if ((len = inputStream.read(buffer,0,buffer.length))>0) {
                        //buffer为读出来的二进制数据，长度1024，最后一段数据小于1024
                        //System.out.println(ConvertCode.bytes2HexString(buffer,len));
                        byte datalen[]={(byte) (len>>8), (byte) (len&0xff)};
                        flen=flen+len;
                        progress_updata.setProgress((int)(100*((flen*1.0)/appsize)));
                        String sdata="000000000000"+ConvertCode.string2HexString("yr")+ConvertCode.bytes2HexString(datalen)+ConvertCode.bytes2HexString(buffer,len)+ConvertCode.string2HexString("o");
                        tcpSend(sdata);
                    }else{
                        byte datalen[]={(byte) (flen>>24), (byte) (flen>>16),(byte) (flen>>8), (byte) (flen&0xff)};
                        String sdata="000000000000"+ConvertCode.string2HexString("fn")+ConvertCode.bytes2HexString(datalen)+ConvertCode.string2HexString("nf");
                        tcpSend(sdata);
                        showmsgs("data  over");
                        //  updata_over();//如果是boot 那这步是必要的
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }


    }

    private static long getFileSize(File file) throws Exception {
        long size = 0;
        int len=0;
        byte[] buffer=new byte[2048];
        if (file.exists()) {
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            size = fis.available();
            // while((len = fis.read(buffer,0,buffer.length))>0){
            //     size=size+len;
            // }
            fis.close();
        } else {
            size=0;
        }
        return size;
    }



    // 专为Android4.4设计的从Uri获取文件绝对路径，以前的方法已不好使
    @SuppressLint("NewApi")
    public static String getPathByUri4kitkat(final Context context, final Uri uri) {
        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            if (isExternalStorageDocument(uri)) {// ExternalStorageProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            } else if (isDownloadsDocument(uri)) {// DownloadsProvider
                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                        Long.valueOf(id));
                return getDataColumn(context, contentUri, null, null);
            } else if (isMediaDocument(uri)) {// MediaProvider
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                final String selection = "_id=?";
                final String[] selectionArgs = new String[] { split[1] };
                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {// MediaStore
            // (and
            // general)
            return getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {// File
            return uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context
     *            The context.
     * @param uri
     *            The Uri to query.
     * @param selection
     *            (Optional) Filter used in the query.
     * @param selectionArgs
     *            (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = { column };
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri
     *            The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public FileInputStream inputStream = null;
    boolean Updating=false;
    int Updatapro=0;
    long appsize=0;
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {//是否选择，没选择就不会继续
            Uri uri = data.getData();//得到uri，后面就是将uri转化成file的过程。
            String path= null;//uri.getPath().toString();//getRealFilePath(this,uri);
            //FileUtils fu=new FileUtils(this);
            path= getPathByUri4kitkat(this,uri);//fu.getFilePathByUri_BELOWAPI11(uri);

            int splen=path.split("\\.").length;
            if(splen>=2) {
                String spbin[] = new String[splen];
                spbin = path.split("\\.");
                if(spbin[splen-1].equals("bin")){
                    File file = new File(path);
                    //在之前调用防止 因为avlable方法造成的隐患
                    try {
                        appsize=getFileSize(file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    try {
                        inputStream = new FileInputStream(file);

                        updata_start();
                    } catch (IOException e) {
                        e.printStackTrace();
                        inputStream=null;
                    }

                }else{
                    showmsgs("file name erro:"+path);
                    //  Toast.makeText(this,"file name erro:"+path,Toast.LENGTH_SHORT).show();
                }
            }else{
                showmsgs("file name erro:"+path);

            }
        }
    }


}

