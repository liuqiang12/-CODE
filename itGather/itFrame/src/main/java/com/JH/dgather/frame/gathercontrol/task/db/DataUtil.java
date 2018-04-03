/*
 * @(#)DataUtil.java 12/27/2011
 *
 * Copyright 2011 Zone, All rights reserved.
 */
package com.JH.dgather.frame.gathercontrol.task.db;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;

import org.apache.log4j.Logger;

import com.JH.dgather.frame.common.db.ConnectionManager;
import com.JH.dgather.frame.gathercontrol.task.UserTask;
import com.JH.dgather.frame.gathercontrol.task.bean.PeriodTaskBean;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskBean;
import com.JH.dgather.frame.gathercontrol.task.bean.TaskObjBean;
import com.JH.dgather.frame.globaldata.GloableDataArea;
import com.JH.dgather.frame.globaldata.TaskRunFlag;
import com.JH.dgather.frame.util.DateFormate;

public class DataUtil {
	private Logger logger = Logger.getLogger(DataUtil.class);

	//设置执行时间为null的周期任务的执行时间
	public void updateExecTimeforNullUserTask() {
		LinkedList<TaskBean> taskLs = new LinkedList<TaskBean>();
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Date execTime = null;
		String tmpDate = "";
		try {
			conn = ConnectionManager.getConnection();
			String curTime = DateFormate.getDateTime();
			TaskBean tb=null;
			// 提取周期任务
			String sql = "select taskId,Periods,PeriodsDay,PeriodsTime,PeriodsEndTime,IntervalTime from task_manager a, task_period b where a.tasktype=1 and a.runflag=0 and a.execTime is null and a.taskId = b.taskId and b.status=0 and nodeId=?";
			ps = conn.prepareStatement(sql);
			ps.setInt(1, GloableDataArea.distributedNode);
			rs = ps.executeQuery();
			while (rs.next()) {
				tb = new TaskBean();
				tb.setTaskId(rs.getInt("taskId"));
				
				// 任务起始时间
				String gatherEndTime = "";
				String gatherStartTime = "";
				if (rs.getString("PeriodsEndTime") == null || rs.getString("PeriodsEndTime").trim().equals("")) {
					gatherEndTime = DateFormate.getDateAdd(new Date(), 0) + " 24:00";// 如果没有定义结束时间则默认为24：00)
				} else {
					gatherEndTime = DateFormate.getDateAdd(new Date(), 0) + " " + rs.getString("PeriodsEndTime");
				}
				
				if (rs.getString("PeriodsTime") == null || rs.getString("PeriodsTime").trim().equals("")) {
					gatherStartTime = DateFormate.getDateAdd(new Date(), 0) + " 00:00";// 如果没有定义起始时间则默认为00：00)
				} else {
					gatherStartTime = DateFormate.getDateAdd(new Date(), 0) + " " + rs.getString("PeriodsTime");
				}
				Date periodEndTime = DateFormate.strToDate(gatherEndTime, "yyyy-MM-dd HH:mm");
				Date periodStartTime = DateFormate.strToDate(gatherStartTime, "yyyy-MM-dd HH:mm");
				
				switch (rs.getInt("Periods")) {
				case 1: // 自定义
					// 下一次执行时间 = 结束时间 + 间隔时间
					execTime = DateFormate.strToDate(curTime);
					// 如果执行时间小于起始时间，那么执行时间为起始时间。
					if (execTime.before(periodStartTime)) {
						execTime = periodStartTime;
					}

					// 通过计算得到的下一次执行时间大于 周期任务的结束时间
					// 那么下一次执行时间为第二天的 周期任务的起始时间
					if (execTime.after(periodEndTime)) {
						tmpDate = DateFormate.getDateAdd(new Date(), 1) + " " + rs.getString("PeriodsEndTime");
						execTime = DateFormate.strToDate(tmpDate);
					}
					break;
				case 0:// 按天采集
					execTime = DateFormate.strToDate(curTime);
					// 如果执行时间小于起始时间，那么执行时间为起始时间。
					if (execTime.before(periodStartTime)) {
						tmpDate = DateFormate.getDateAdd(new Date(), 0) + " " + rs.getString("PeriodsTime");
					}else{
						tmpDate = DateFormate.getDateAdd(new Date(), 1) + " " + rs.getString("PeriodsTime");
					}
					execTime = DateFormate.strToDate(tmpDate);
					break;
				case 2:// 按周采集
					tmpDate = DateFormate.getDAYOFWEEK(rs.getInt("PeriodsDay")) + " " + rs.getString("PeriodsTime")+":00";
					execTime = DateFormate.strToDate(tmpDate);
					break;
					/*
					tmpDate = DateFormate.getDAYOFWEEK(rs.getInt("GatherPeriodsDay")) + " " + rs.getString("GatherPeriodsTime")+":00";
					execTime = DateFormate.strToDate(tmpDate,null);
					if(DateFormate.strToDate(DateFormate.getDateAdd(execTime, -7),null).after(DateFormate.strToDate(curTime,null))){
						execTime = DateFormate.strToDate(DateFormate.getDateAdd(execTime, -7),null);
					}
					 */
				case 3:// 按月采集
					tmpDate = DateFormate.getDAYOFMONTH(rs.getInt("PeriodsDay")) + " " + rs.getString("PeriodsTime")+":00";
					execTime = DateFormate.strToDate(tmpDate);
					break;
				case 4:// 按时采集,需考虑采集结束时间段,如果计算出的时间正好在限制时间段内，则下次执行时间则从开始时间决定
					
					execTime = DateFormate.AddMinute(new Date(), rs.getInt("IntervalTime"));
					tmpDate = DateFormate.dateToStr(execTime, "HH:mm");
					if (gatherEndTime.compareTo(rs.getString("PeriodsTime")) > 0)// 结束时间大于启动时间
					{
						if (tmpDate.compareTo(gatherEndTime) < 0 && tmpDate.compareTo(rs.getString("PeriodsTime")) > 0) {
							tmpDate = DateFormate.dateToStr(new Date(), "yyyy-MM-dd") + " " + rs.getString("PeriodsTime");
							execTime = DateFormate.strToDate(tmpDate);
						}
					} else// 结束时间小于启动时间
					{
						if (tmpDate.compareTo(gatherEndTime) > 0 || tmpDate.compareTo(rs.getString("PeriodsTime")) < 0) {
							tmpDate = DateFormate.dateToStr(DateFormate.addDate(new Date(), 1), "yyyy-MM-dd") + " " + rs.getString("GatherPeriodsTime");
							execTime = DateFormate.strToDate(tmpDate);
						}

					}
					break;
				default:
					execTime = DateFormate.strToDate(curTime, "yyyy-MM-dd HH:mm:ss");
					break;
				}
				tb.setExecTime(execTime);
				taskLs.add(tb);
			}
			sql = "update Task_manager set ExecTime=TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') where taskid=?";
			for (TaskBean taskBean : taskLs) {
				try {
					ps = conn.prepareStatement(sql);
					ps.setString(1, DateFormate.dateToStr(taskBean.getExecTime(), "yyyy-MM-dd HH:mm:ss"));
					ps.setInt(2, taskBean.getTaskId());
					ps.executeUpdate();
				} catch (Exception e) {
					logger.error(e);
				}
			}
			
		} catch (Exception e) {
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
	}
	/*
	 * 提取本节点等待执行的任务
	 */
	public LinkedList<TaskBean> getUserTask() {
		LinkedList<TaskBean> taskLs = null;
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			conn = ConnectionManager.getConnection();
			String curTime = DateFormate.getDateTime();

			// 提取临时任务
			String sql = "select a.TaskId,a.taskClass,a.ExecTime,a.taskType,a.userId,a.taskName,a.kpiid,a.extend from task_manager a" + " where tasktype=0 and RunFlag=0 and( execTime is null  or execTime<=TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss')) and nodeId=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, curTime);
			ps.setInt(2, GloableDataArea.distributedNode);
			logger.debug("提取临时任务："+sql);
			rs = ps.executeQuery();
			TaskBean tb = null;
			if (rs == null)
				return null;
			taskLs = new LinkedList<TaskBean>();
			while (rs.next()) {
				tb = new TaskBean();
				tb.setTaskId(rs.getInt("TaskId"));
//				if (rs.getString("ExecTime") != null && (!rs.getString("ExecTime").isEmpty()) && (!rs.getString("ExecTime").equals("null")))
//					tb.setExecTime(rs.getDate("ExecTime"));
				tb.setGatherClass(rs.getInt("taskClass"));
				tb.setTaskType(rs.getInt("taskType"));
				tb.setUserId(rs.getString("userId"));
				tb.setTaskName(rs.getString("taskName"));
				tb.setExecTime(DateFormate.strToDate(curTime, "yyyy-MM-dd HH:mm:ss"));
				tb.setKpiId(rs.getInt("KPIID"));
				tb.setTaskextend(rs.getString("extend"));
				taskLs.add(tb);

			}
			ps.close();
			rs.close();

			// 提取周期任务
			sql = "select * from task_manager a, task_period b where a.tasktype=1 and a.runflag=0 and ( a.exectime is null  or a.exectime<=TO_DATE(?,'yyyy-mm-dd hh24:mi:ss')) and a.taskid = b.taskid and b.status=0 and nodeid=? ";
			ps = conn.prepareStatement(sql);
			ps.setString(1, curTime);
			ps.setInt(2, GloableDataArea.distributedNode);
			rs = ps.executeQuery();
			while (rs.next()) {
				tb = new TaskBean();
				tb.setTaskId(rs.getInt("taskid"));
				if (rs.getString("ExecTime") != null && (!rs.getString("ExecTime").isEmpty()) && (!rs.getString("ExecTime").equals("null")))
					tb.setExecTime(rs.getDate("ExecTime"));
				tb.setGatherClass(rs.getInt("taskClass"));
				tb.setTaskType(rs.getInt("taskType"));
				tb.setUserId(rs.getString("userId"));
				tb.setTaskName(rs.getString("taskName"));
				tb.setKpiId(rs.getInt("KPIID"));
				tb.setExecTime(DateFormate.strToDate(curTime, "yyyy-MM-dd HH:mm:ss"));

				taskLs.add(tb);
			}
			//logger.info("提取任务SQL："+sql);
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return taskLs;
	}

	public HashMap<Integer, TaskObjBean> getGatherObj(UserTask task) {
		HashMap<Integer, TaskObjBean> objHs = null;
		TaskObjBean obj = null;
		PeriodTaskBean periodTaskBean = null;
		Connection conn = null;
		int taskid = task.getTaskId();
		PreparedStatement ps = null;

		ResultSet rs = null;
		try {
			conn = ConnectionManager.getConnection();
			String sql;
			if (task.getTaskType() == 1)// 周期任务
			{
				/**
				 * 提取周期任务周期表
				 */
				sql = "SELECT * FROM task_period WHERE taskId=?";
				logger.info("提取周期任务周期表:" + sql);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, taskid);
				rs = ps.executeQuery();
				if (rs.next()) {
					periodTaskBean = new PeriodTaskBean(taskid);
					// xdwang 2012-5-23 如果周期任务为按天，且周期任务的间隔时间为0，默认为24小时
					int pred = rs.getInt("Periods");
					int inst = rs.getInt("IntervalTime");
					if (pred == 1 && inst <= 0) {
						inst = 24 * 60;
					}

					periodTaskBean.setGatherPeriods(pred);
					periodTaskBean.setGatherPeriodsDay(rs.getInt("PeriodsDay"));
					periodTaskBean.setGatherPeriodsTime(rs.getString("PeriodsTime"));
					periodTaskBean.setGatherPeriodsEndTime(rs.getString("PeriodsEndTime"));
					periodTaskBean.setGatherIntervalTime(inst);
					periodTaskBean.setStatus(rs.getInt("status"));
					task.setPeriodTask(periodTaskBean);
				}

				rs.close();
				ps.close();

				int gatherclass = 0;
				sql = "SELECT taskclass from task_manager where taskId=" + taskid;
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				logger.info("提取周期任务周期表:" + sql);
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				if (rs.next()) {
					gatherclass = rs.getInt("taskclass");
					}

				rs.close();
				ps.close();
				sql = "SELECT a.Id,a.taskId,a.GoId,a.gatherIntervalTime,a.DeviceExecTime,a.ExtendInfo,b.portId,b.PortIndex,b.PortName "
						+ "FROM task_PublicObject a  left join task_DevicePort b on a.id=b.gatherObjId  WHERE a.taskId=? ORDER BY a.goid";
				logger.info("提取公共采集对象及端口信息:" + sql);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, taskid);
				rs = ps.executeQuery();
				if (rs == null) {
					logger.error("rs = null !");
					return null;
				}
				objHs = new HashMap<Integer, TaskObjBean>();
				int goid = -1;
				while (rs.next()) {
					/**
					 * 提取任务信息
					 */
					if (goid != rs.getInt("goid")) {
						//muyp debug
						//if(obj!=null)
						//	logger.info("获取任务id="+goid+"port数量="+obj.getPortNameLs().get(goid).size());
						//
						goid = rs.getInt("goid");
						obj = new TaskObjBean();
						obj.setGatherId(rs.getInt("id"));
						obj.setTaskid(taskid);
						obj.setGoid(goid);
						obj.setGatherIntervalTime(rs.getInt("gatherIntervalTime"));
						if (rs.getString("DeviceExecTime") != null)
							obj.setExecTime(DateFormate.strToDate(rs.getString("DeviceExecTime"), "yyyy-MM-dd HH:mm:ss"));
						objHs.put(obj.getGoid(), obj);
					}
				}
			} else {
				int gatherclass = 0;
				sql = "SELECT taskclass from task_manager where taskId=" + taskid;
				ps = conn.prepareStatement(sql);
				rs = ps.executeQuery();
				logger.info("提取周期任务周期表:" + sql);
				if (rs.next()) {
					gatherclass = rs.getInt("taskclass");
					}

				rs.close();
				ps.close();
					sql = "select id, taskid,goid,ExtendInfo,ngto.portName,PortIndex "
							+ "from task_TempObject ngto LEFT JOIN net_port res on ngto.GoId=res.DeviceId and ngto.PortName=res.PortName where taskid=? order by goid";
				logger.info("提取公共采集对象及端口信息:" + sql);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, taskid);
				rs = ps.executeQuery();
				if (rs == null)
					return null;
				objHs = new HashMap<Integer, TaskObjBean>();
				int goid = -1;
				while (rs.next()) {
					if (goid != rs.getInt("goid")) {
						goid = rs.getInt("goid");
						obj = new TaskObjBean();
						obj.setGatherId(rs.getInt("id"));
						obj.setTaskid(taskid);
						obj.setGoid(goid);
						obj.setExtendInfo(rs.getString("ExtendInfo"));
						objHs.put(obj.getGoid(), obj);
					}

				}
			}
		} catch (Exception e) {
			logger.error("", e);
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return objHs;
	}

	/**
	 * 获取net_taskManger表中状态为正在执行状态的任务
	 * 
	 * @return
	 */
	public LinkedList<TaskBean> getDoingTask() {
		Connection conn = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		LinkedList<TaskBean> tList = null;
		try {
			conn = ConnectionManager.getConnection();
			String sql = "select * from task_manager where runflag=?" ;
			ps = conn.prepareStatement(sql);
			ps.setInt(1, TaskRunFlag.DOING);
			rs = ps.executeQuery();
			tList = new LinkedList<TaskBean>();
			while (rs.next()) {
				TaskBean tBean = new TaskBean();
				tBean.setTaskType(rs.getInt("tasktype"));
				tBean.setTaskId(rs.getInt("taskId"));
				tBean.setExecTime(DateFormate.strToDate(rs.getString("execTime"), "yyyy-MM-dd HH:mm:ss"));
				tList.add(tBean);
			}
			rs.close();
			ps.close();
		} catch (Exception e) {
			logger.error("出现异常！", e);
		} finally {
			ConnectionManager.release(conn, ps, rs);
		}
		return tList;
	}

	private void getPeriodTaskInfoImpl(UserTask userTask, Connection conn) {
		String sql = "select * from task_period where taskId=?" ;
		logger.info("提取周期任务SQL："+sql);
		PreparedStatement ps = null;
		ResultSet rs = null;
		try {
			ps = conn.prepareStatement(sql);
			ps.setInt(1, userTask.getTaskId());
			rs = ps.executeQuery();
			if (rs.next()) {
				userTask.setRunFlag(TaskRunFlag.WAITING);
				PeriodTaskBean periodTaskBean = new PeriodTaskBean(userTask.getTaskId());
				// xdwang 2012-5-23 如果周期任务为按天，且周期任务的间隔时间为0，默认为24小时
				int pred = rs.getInt("Periods");
				int inst = rs.getInt("IntervalTime");
				if (pred == 1 && inst <= 0) {
					inst = 24 * 60;
				}

				periodTaskBean.setGatherPeriods(pred);
				periodTaskBean.setGatherPeriodsDay(rs.getInt("PeriodsDay"));
				periodTaskBean.setGatherPeriodsTime(rs.getString("PeriodsTime"));
				periodTaskBean.setGatherPeriodsEndTime(rs.getString("PeriodsEndTime"));
				periodTaskBean.setGatherIntervalTime(inst);
				periodTaskBean.setStatus(rs.getInt("status"));

				userTask.setPeriodTask(periodTaskBean);
			} else {
				userTask.setRunFlag(TaskRunFlag.FINAL);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			ConnectionManager.closeStatement(ps);
			ConnectionManager.closeResultSet(rs);
		}
	}

	/**
	 * 根据任务ID，提取周期任务信息
	 */
	public void getPeriodTaskInfo(LinkedList<UserTask> userTaskLs) {
		Connection conn = null;
		try {
			conn = ConnectionManager.getConnection();
			for (UserTask uTask : userTaskLs) {
				getPeriodTaskInfoImpl(uTask, conn);
			}
		} catch (Exception e) {
			logger.error("出现异常！", e);
		} finally {
			ConnectionManager.closeConnection(conn);
		}
	}

	/**
	 * 底层重新启动时，将正在执行的任务更新为失败状态
	 */
	public void reflush_TaskManager(LinkedList<TaskBean> uTaskLs) {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionManager.getConnection();
			String sql = "update task_manager set runflag=?, execTime=TO_DATE(?, 'yyyy-mm-dd hh24:mi:ss') where taskid=?";
			ps = conn.prepareStatement(sql);
			for (TaskBean bean : uTaskLs) {
				ps.setInt(1, bean.getRunFlag());
				if (bean.getExecTime() == null || "".equals(bean.getExecTime()))
					ps.setString(2, DateFormate.dateToStr(new java.util.Date(), "yyyy-MM-dd HH:mm:ss"));
				else
					ps.setString(2, DateFormate.dateToStr(bean.getExecTime(), "yyyy-MM-dd HH:mm:ss"));
				ps.setInt(3, bean.getTaskId());
				ps.executeUpdate();
			}
			ps.close();

		} catch (Exception e) {
			logger.error("出现异常！", e);
		} finally {
			ConnectionManager.release(conn, ps, null);
		}
	}

	/*
	 * 针对Net_TaskManager表存储任务结果信息
	 */
	public void updateNet_TaskManager(UserTask task) throws SQLException {
		Connection conn = null;
		PreparedStatement ps = null;
		try {
			conn = ConnectionManager.getConnection();
			String sql = "update task_manager set execTime=TO_DATE(?,'yyyy-mm-dd hh24:mi:ss'),runflag=?  where taskid=?";
			ps = conn.prepareStatement(sql);
			// ps.setDate(1, (Date) task.getExecTime());
			if (task.getExecTime() == null || "".equals(task.getExecTime()))
				ps.setString(1, DateFormate.dateToStr(new java.util.Date(), "yyyy-MM-dd HH:mm:ss"));
			else
				ps.setString(1, DateFormate.dateToStr(task.getExecTime(), "yyyy-MM-dd HH:mm:ss"));
			if (task.getTaskType() == 0) { // 临时任务，状态至为当前的状态
				ps.setInt(2, task.getRunFlag());
			} else {
				logger.info("重新将周期任务的运行状态置为0, taskName: " + task.getTaskName());
				// 周期任务，状态至为等待状态
				ps.setInt(2, 0);
			}
			ps.setInt(3, task.getTaskId());
			ps.executeUpdate();
		} finally {
			ConnectionManager.release(conn, ps, null);
		}
	}

	/**
	 * 更新任务运行状态
	 */
	public void updateNetTaskManagerRunFlag(List<UserTask> userTaskLs, int newRunFlag) {
		Connection conn = null;
		PreparedStatement ps = null;
		int oldFlag = 0;
		int id = 0;
		try {
			conn = ConnectionManager.getConnection();
			if (conn != null) {
				java.util.Date d = new java.util.Date();
				String SQL = "update task_manager a set a.runflag=?,a.execTime=TO_DATE(?,'yyyy-mm-dd hh24:mi:ss') where  ','||?||',' like '%,'||a.taskId||',%'";
				
				String s = "" ;
				for (UserTask userTask : userTaskLs) {
					TaskBean taskBean = userTask.getUserTaskBean();
					taskBean.setExecTime(d);
					if (id == 0) {
						s += taskBean.getTaskId();
						id++;
					} else {
						s += "," + taskBean.getTaskId();
					}
					oldFlag = taskBean.getRunFlag();
					taskBean.setRunFlag(newRunFlag);
					taskBean.getTaskRunResult().addInfo("任务状态更新进数据库内。状态更新序列为: " + oldFlag + "->" + newRunFlag);

				}
				logger.debug("执行SQL：" + SQL);
				ps = conn.prepareStatement(SQL);
				ps.setInt(1, newRunFlag);
				ps.setString(2, DateFormate.dateToStr(d, "yyyy-MM-dd HH:mm:ss"));
				ps.setString(3, s);
				ps.executeUpdate();
			} else {
				logger.error("获取数据库连接对象为null!");
			}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		} finally {
			ConnectionManager.release(conn, ps, null);
		}
	}

	/**
	 * 删除XML文件
	 * @return
	 * @throws IOException
	 */
	public boolean deleteXMLFile(String filePath) throws Exception {
		if (filePath == null || "".equals(filePath)) {
			throw new Exception("文件路径为空！");
		}
		File file = new File(filePath);
		if (file.exists()) {
			file.delete();
		}
		file = null;
		return true;
	}
	
	public void getPeriodTask(UserTask task) {
		PeriodTaskBean periodTaskBean = null;
		Connection conn = null;
		int taskid = task.getTaskId();
		PreparedStatement ps = null;

		ResultSet rs = null;
		try {
				conn = ConnectionManager.getConnection();
				String sql;
				sql = "SELECT * FROM task_period WHERE taskId=?";
			//	logger.info("提取周期任务周期表:" + sql);
				ps = conn.prepareStatement(sql);
				ps.setInt(1, taskid);
				rs = ps.executeQuery();
				if (rs.next()) {
					periodTaskBean = new PeriodTaskBean(taskid);
					// 如果周期任务为按天，且周期任务的间隔时间为0，默认为24小时
					int pred = rs.getInt("periods");
					int inst = rs.getInt("IntervalTime");
					if (pred == 1 && inst <= 0) {
						inst = 24 * 60;
					}

					periodTaskBean.setGatherPeriods(pred);
					periodTaskBean.setGatherPeriodsDay(rs.getInt("PeriodsDay"));
					periodTaskBean.setGatherPeriodsTime(rs.getString("PeriodsTime"));
					periodTaskBean.setGatherPeriodsEndTime(rs.getString("PeriodsEndTime"));
					periodTaskBean.setGatherIntervalTime(inst);
					periodTaskBean.setStatus(rs.getInt("status"));
					task.setPeriodTask(periodTaskBean);
				}
		}catch(Exception e){
					e.printStackTrace();
		}		
		finally{
			ConnectionManager.release(conn, ps, rs);
		}
			
	}
}