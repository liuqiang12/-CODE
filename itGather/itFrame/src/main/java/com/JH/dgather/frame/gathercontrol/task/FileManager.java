package com.JH.dgather.frame.gathercontrol.task;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.dom.DOMCDATA;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.XMLWriter;

import com.JH.dgather.PropertiesHandle;
import com.JH.dgather.frame.common.bean.ConfigAnalyseDevice;
import com.JH.dgather.frame.common.bean.ConfigBackupDevice;
import com.JH.dgather.frame.common.bean.DeviceInfoBean;
import com.JH.dgather.frame.gathercontrol.task.BusinessBean.Net_LineInfo;
import com.JH.dgather.frame.gathercontrol.task.BusinessBean.Net_TraceInfo;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskObjBean;
import com.JH.dgather.frame.globaldata.TaskRunFlag;
import com.JH.dgather.frame.util.DateFormate;
import com.JH.dgather.frame.util.DateUtil;

public class FileManager {
	private Logger logger = Logger.getLogger(FileManager.class);
	public String resultFilePath = PropertiesHandle.getResuouceInfo("gatherResultFilePath");

	/**
	 * 获取存储采集结果文件的路径
	 * 
	 * @param taskName
	 * @return filepath
	 */
	public String getResultFilePath(String taskName) {
		String tName = taskName;
		tName = tName.replaceAll("/", "");
		tName = tName.replaceAll("\\\\", "");

		if (resultFilePath.endsWith("/")) {
			return resultFilePath + tName + DateFormate.dateToStr(new Date(), "yyyyMMddHHmmss") + ".xml";
		} else
			return resultFilePath + "/" + tName + DateFormate.dateToStr(new Date(), "yyyyMMddHHmmss") + ".xml";
	}

	/**
	 * 将采集对象结果存储文本文件 如果对象执行失败，则存储失败原因
	 * 
	 * @param overTaskLs
	 * @return
	 * @throws IOException 
	 */
	public boolean saveXMLFile(ArrayList<UserTask> overTaskLs) throws IOException {
		boolean isSuccess = false;
		OutputStreamWriter fileWriter = null;
		for (UserTask task : overTaskLs) {

			Document document = DocumentHelper.createDocument();
			// 生成一个接点
			Element rows = document.addElement("ROWS");

			Element row, time, goId, deviceName, result, message;

			try {
				// TIME
				String timeStr = DateUtil.getDateTime();

				logger.info(task.getTaskName() + " 任务采集对象结果将存储到文件'" + task.getResultFile() + "'中的记录个数为："+task.getHmObj().size());
				for (Entry<Integer, TaskObjBean> entry : task.getHmObj().entrySet()) {
					
					// 生成ROWS的一个接点
					row = rows.addElement("ROW");
					time = row.addElement("TIME");
					// 生成time里面的值
					time.add(new DOMCDATA(timeStr));
					// GO ID
					goId = row.addElement("GOID");
					goId.add(new DOMCDATA(Integer.toString(entry.getValue().getGoid())));

					// DEVICE NAME
					deviceName = row.addElement("DEVICENAME");
					//logger.info("写人文件的对象是："+entry.getValue().getGatherObj().getClass());
					if (entry.getValue().getGatherObj() instanceof DeviceInfoBean)
						deviceName.add(new DOMCDATA("设备名：" + ((DeviceInfoBean) entry.getValue().getGatherObj()).getDeviceName()));
					else if (entry.getValue().getGatherObj() instanceof Net_LineInfo)
						deviceName.add(new DOMCDATA("链路名：" + ((Net_LineInfo) entry.getValue().getGatherObj()).getLineName()));
					else if(entry.getValue().getGatherObj() instanceof ConfigBackupDevice)
						deviceName.add(new DOMCDATA("设备名：" + ((ConfigBackupDevice) entry.getValue().getGatherObj()).getName()));
					else if(entry.getValue().getGatherObj() instanceof ConfigAnalyseDevice)
						deviceName.add(new DOMCDATA("设备名：" + ((ConfigAnalyseDevice) entry.getValue().getGatherObj()).getName()));
					else if(entry.getValue().getGatherObj() instanceof Net_TraceInfo){
						deviceName.add(new DOMCDATA("Trace名：" + ((Net_TraceInfo) entry.getValue().getGatherObj()).getTraceName()));
					}
					else
						deviceName.add(new DOMCDATA(Integer.toString(entry.getValue().getGoid())));

					// RESULT
					result = row.addElement("RESULT");
					if (entry.getValue().getResult() == TaskRunFlag.WAITING)
						result.add(new DOMCDATA("未执行"));
					else if (entry.getValue().getResult() == TaskRunFlag.SUCCESS ||entry.getValue().getResult() == TaskRunFlag.DOING)
						result.add(new DOMCDATA("成功"));
					else if (entry.getValue().getResult() == TaskRunFlag.FINAL)
						result.add(new DOMCDATA("失败"));
					else if (entry.getValue().getResult() == TaskRunFlag.TIMEOUT)
						result.add(new DOMCDATA("执行超时失败"));
					else if (entry.getValue().getResult() == TaskRunFlag.WAITINGFINAL)
						result.add(new DOMCDATA("执行失败"));
					else
						result.add(new DOMCDATA(Integer.toString(entry.getValue().getResult())));
					// MESSAGE
					message = row.addElement("MESSAGE");
					String messageStr = "";
					if (entry.getValue().getResult() == TaskRunFlag.WAITINGFINAL) {
						message.add(new DOMCDATA("对象执行失败"));
					} else if (entry.getValue().getResult() == TaskRunFlag.WAITING) {
						message.add(new DOMCDATA("对象未执行"));
					} else if (entry.getValue().getResult() == TaskRunFlag.TIMEOUT){
						message.add(new DOMCDATA("设备执行超时被中断"));
					} else {
						messageStr = entry.getValue().getErrorMsg();
						if (entry.getValue().getResult() == TaskRunFlag.SUCCESS || messageStr==null || "".equals(messageStr))
							message.add(new DOMCDATA("对象执行成功"));
						else
							message.add(new DOMCDATA(messageStr));
					}
				}

				logger.info(task.getTaskName() + " 准备开始写入文件'" + task.getResultFile() + "'");
				// 写文件部分
				// 读取文件
				fileWriter = new OutputStreamWriter(new FileOutputStream(task.getResultFile()), "UTF-8");
				// 设置文件编码
				OutputFormat xmlFormat = OutputFormat.createPrettyPrint();
				xmlFormat.setEncoding("UTF-8");

				// 创建写文件方法
				XMLWriter xmlWriter = new XMLWriter(fileWriter, xmlFormat);
				// 写入文件
				xmlWriter.write(document);
				// 关闭
				xmlWriter.close();
				fileWriter.close();
				logger.info(task.getTaskName() + " 写入文件'" + task.getResultFile() + "'结束");
			} catch (Exception e) {
				logger.error("配置文件分析写入start文件时失败,文件路径：" + task.getResultFile(), e);
			}finally{
				if(fileWriter!=null)
					fileWriter.close();
			}

		}
		return isSuccess;

	}
}
