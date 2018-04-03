package com.JH.itBusi.globaldata;
/*由于流量采集做大量数据update 造成数据库响应慢，考虑不做UPDATE
 * */
import java.util.HashMap;

import com.JH.itBusi.comm.db.PortCapBean;

public  class GloablePara {
	static public HashMap<Integer, HashMap<Long, PortCapBean>> PortOctets_curr_Hs = null;//记录端口当前流量信息,<devicid,<porindex,portcapbean>>
	
}
