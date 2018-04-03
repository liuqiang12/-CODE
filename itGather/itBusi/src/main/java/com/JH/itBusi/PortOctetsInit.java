package com.JH.itBusi;
/*起线程目的是考虑后面在这里做数据统一入库
 * */
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.itBusi.comm.DataUtil;
import com.JH.itBusi.globaldata.GloablePara;

public class PortOctetsInit implements Runnable {
	private DataUtil db;
	private boolean runFlag = true;//0执行，1中断
	public PortOctetsInit() {
			
	}

	@Override
	public void run() {
		db = new DataUtil();
		GloablePara.PortOctets_curr_Hs =db.getPortOctets();
	/*	while(GloableDataArea.runflag){
			 try{
				 System.out.println("hello");
				 Thread.sleep(1000);
				 }catch(Exception e){}
		}*/
		
	}


}
