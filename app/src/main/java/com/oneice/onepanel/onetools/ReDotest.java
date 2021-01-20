package com.oneice.onepanel.onetools;
//重新经过一定的延时重新做某事
public class ReDotest extends Thread {
    private int REDOTIME =3;
    private int overdotimeflag=0;
    private int DoWhat=0;
   public static final int DoUpdataBinReDownload=1 ;

    public  ReDotest(int doWhat,int redotime){
       this.REDOTIME=redotime;
       this.DoWhat=doWhat;
    }
    @Override
    public void run(){
    while(REDOTIME>overdotimeflag)
    {
        switch (DoWhat)
        {
            case DoUpdataBinReDownload://重新开始升级文件的数据下载

                break;

        }
    }
    switch (DoWhat)
    {
        case DoUpdataBinReDownload://完毕

            break;

    }



    }


}
