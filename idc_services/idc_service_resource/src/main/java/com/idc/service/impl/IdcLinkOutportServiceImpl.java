package com.idc.service.impl;

import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import com.idc.mapper.IdcLinkOutportMapper;
import com.idc.model.IdcLinkOutport;
import com.idc.service.IdcLinkOutportService;
import utils.typeHelper.MapHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_LINK_INFO:链路信息表<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Sep 28,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcLinkInfoService")
public class IdcLinkOutportServiceImpl extends SuperServiceImpl<IdcLinkOutport, Long> implements IdcLinkOutportService {
    @Autowired
    private IdcLinkOutportMapper mapper;

    /*获取链路基本信息及调单号、附件  page*/
    @Override
    public List<Map<String, Object>> queryLinkOutportListPage(PageBean<T> page, Object param) {
        page.setParams(MapHelper.queryCondition(param));
        return mapper.queryLinkOutportListPage(page);
    }

    /*获取链路基本信息及调单号、附件*/
    @Override
    public List<Map<String, Object>> queryLinkOutportList(Map<String, Object> map) {
        return mapper.queryLinkOutportList(map);
    }

    @Override
    public void saveOrUpdateLink(IdcLinkOutport idcLinkOutport, Long id, HttpServletRequest request) throws Exception {
//		String idcTransmissionCode = request.getParameter("idcTransmissionCode");
//		String note = request.getParameter("idcTransmissionNote");
//		String idcTransmissionId = request.getParameter("idcTransmissionId");
//		IdcTransmissionForm idcTransmissionForm = new IdcTransmissionForm();
//		if(id!=null){//修改
//			if(idcTransmissionId!=null&&!"".equals(idcTransmissionId)){//有编码才能上传附件
//				idcTransmissionForm.setIdcTransmissionCode(idcTransmissionCode);
//				idcTransmissionForm.setNote(note);
//				idcTransmissionForm.setIdcTransmissionId(Long.parseLong(idcTransmissionId));
//				idcTransmissionFormMapper.updateByObject(idcTransmissionForm);
//				//先删除原来的附件在上传
//				deleteAssetAttachmentinfo(idcTransmissionId);
//				//上传附件
//				uploadAttachment(request,idcTransmissionForm.getIdcTransmissionId());
//			}
//			idcLinkOutport.setIdcLinkInfoId(id);
//			idcLinkOutport.setIdcTransmissionId(idcTransmissionForm.getIdcTransmissionId());
//			mapper.updateByObject(idcLinkOutport);
//		}else{//新增   可能会有多条链路
//			//添加链路信息
//			List<IdcLinkOutport> outportList = new ArrayList<>();
//			String localOdfPort = idcLinkOutport.getIdcLinkLocalOdfPort();//本端ODF端口
//			String targetOdfPort = idcLinkOutport.getIdcLinkTargetOdfPort();//对端ODF端口
//			String localDevicePort = idcLinkOutport.getIdcLinkLocalDevicePort();//本端设备端口
//			String targetDevicePort = idcLinkOutport.getIdcLinkTargetDevicePort();//对端设备端口
//			List<String> localOdfPortArr = null;
//			List<String> targetOdfPortArr = null;
//			List<String> localDevicePortArr = null;
//			List<String> targetDevicePortArr = null;
//			if(localOdfPort!=null&&!"".equals(localOdfPort)&&targetOdfPort!=null&&!"".equals(targetOdfPort)){//用本端ODF端口就有对端ODF端口信息
//				localOdfPortArr = Arrays.asList(localOdfPort.split(","));
//				targetOdfPortArr = Arrays.asList(targetOdfPort.split(","));
//			}else{
//				idcLinkOutport.setIdcLinkLocalOdfPort(null);
//				idcLinkOutport.setIdcLinkTargetOdfPort(null);
//			}
//			if(localDevicePort!=null&&!"".equals(localDevicePort)&&targetDevicePort!=null&&!"".equals(targetDevicePort)){//用本端设备端口就有对端设备端口信息
//				localDevicePortArr = Arrays.asList(localDevicePort.split(","));
//				targetDevicePortArr = Arrays.asList(targetDevicePort.split(","));
//			}else{
//				idcLinkOutport.setIdcLinkLocalDevicePort(null);
//				idcLinkOutport.setIdcLinkTargetDevicePort(null);
//			}
//			if(localOdfPortArr!=null&&localOdfPortArr.size()>0){//有本对端ODF端口信息
//				for(int i=0;i<localOdfPortArr.size();i++){
//					IdcLinkOutport param = new IdcLinkOutport();
//					loadIdcLinkOutportInfo(param,idcLinkOutport);
//					param.setIdcLinkLocalOdfPort(localOdfPortArr.get(i));
//					param.setIdcLinkTargetOdfPort(targetOdfPortArr.get(i));
//					if(localDevicePortArr!=null&&localDevicePortArr.size()>0){
//						param.setIdcLinkLocalDevicePort(localDevicePortArr.get(i));
//						param.setIdcLinkTargetDevicePort(targetDevicePortArr.get(i));
//					}
//					outportList.add(param);
//				}
//			}else if(localDevicePortArr!=null&&localDevicePortArr.size()>0){//没有本对端ODF端口信息   有本对端设备端口信息
//				for(int i=0;i<localDevicePortArr.size();i++){
//					IdcLinkOutport param = new IdcLinkOutport();
//					loadIdcLinkOutportInfo(param,idcLinkOutport);
//					param.setIdcLinkLocalDevicePort(localDevicePortArr.get(i));
//					param.setIdcLinkTargetDevicePort(targetDevicePortArr.get(i));
//					outportList.add(param);
//				}
//			}else{//没有本对端ODF端口信息   没有对端设备端口信息
//				outportList.add(idcLinkOutport);
//			}
//			//去重
//			HashSet set = new HashSet(outportList);
//			outportList.clear();
//			outportList.addAll(set);
//			//新增
//			for(IdcLinkOutport linkOutport:outportList){
//				//添加调度信息
//				if(idcTransmissionCode!=null&&!"".equals(idcTransmissionCode)){//有编码才能上传附件
//					idcTransmissionForm.setIdcTransmissionCode(idcTransmissionCode);
//					idcTransmissionForm.setNote(note);
//					idcTransmissionFormMapper.insert(idcTransmissionForm);
//					//上传附件
//					uploadAttachment(request,idcTransmissionForm.getIdcTransmissionId());
//				}
//				linkOutport.setIdcTransmissionId(idcTransmissionForm.getIdcTransmissionId());
//			}
//			mapper.insertList(outportList);
//		}
    }
//	//将链路信息封装到另一个对象
//	public void loadIdcLinkOutportInfo(IdcLinkOutport outportA,IdcLinkOutport outportB){
//		outportA.setIdcLinkFromArea(outportB.getIdcLinkFromArea());
//		outportA.setIdcLinkRoomName(outportB.getIdcLinkRoomName());
//		outportA.setIdcLinkLocalRouteName(outportB.getIdcLinkLocalRouteName());
//		outportA.setIdcLinkCabinetPosition(outportB.getIdcLinkCabinetPosition());
//		outportA.setIdcLinkDeviceModel(outportB.getIdcLinkDeviceModel());
//		outportA.setIdcLinkTapeWidth(outportB.getIdcLinkTapeWidth());
//		outportA.setIdcLinkLocalOdfPort(outportB.getIdcLinkLocalOdfPort());
//		outportA.setIdcLinkLocalDevicePort(outportB.getIdcLinkLocalDevicePort());
//		outportA.setIdcLinkTargetRoomName(outportB.getIdcLinkTargetRoomName());
//		outportA.setIdcLinkTargetRouteName(outportB.getIdcLinkTargetRouteName());
//		outportA.setIdcLinkTargetDeviceModel(outportB.getIdcLinkTargetDeviceModel());
//		outportA.setIdcLinkTargetOdfPort(outportB.getIdcLinkTargetOdfPort());
//		outportA.setIdcLinkTargetDevicePort(outportB.getIdcLinkTargetDevicePort());
//		outportA.setNote(outportB.getNote());
//		outportA.setIdcTransmissionId(outportB.getIdcTransmissionId());
//	}
//	//上传附件
//	public void uploadAttachment(HttpServletRequest request,Long id) throws Exception{
//		// 转型为MultipartHttpRequest：
//		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
//		// 获得文件：
//		MultiValueMap<String,MultipartFile > mr = multipartRequest.getMultiFileMap();
//		SysUserinfo sysUserinfo = getPrincipal();
//		Integer userId = sysUserinfo.getId();
//		// 获得文件名：
//		/*String filename = file.getOriginalFilename();*/
//		// 获得输入流：
//		FTPUtils ftpUtils = null;
//		if(DevContext.IDC_UPLOADFILE_USE_FTP != null && "true".equalsIgnoreCase(DevContext.IDC_UPLOADFILE_USE_FTP)){
//			log.debug("需要通过ftp方式传递:[一般是夸主机的情况.......这种情况少用]");
//			ftpUtils = FTPUtils.getInstance();
//		}
//		for(String fileKey : mr.keySet()){
//			MultipartFile file= mr.getFirst(fileKey);
//			// 原始文件名
//			String originalFileName = file.getOriginalFilename();
//			if(originalFileName != null && !"".equals(originalFileName)){
//				// 获取文件后缀名（用来生成存放该种类型的所有文件）
//				String extendName = originalFileName.substring(originalFileName.lastIndexOf("."));
//				//文件的二进制
//				byte[] fileBytes = file.getBytes();
//				//文件的大小
//				//此处还是通过FTP方式上传附件.
//				Long size = file.getSize();
//				AssetAttachmentinfo assetAttachmentinfo = new AssetAttachmentinfo();
//				log.debug("此处不再将附件信息保存到ORACLE中。如果遇到附件比较大。则会使ORACLE查询压力剧增,采用FTP或者直接保存文件上传服务器方式。。。。start");
//				String fileName = Guid.getAttchUUID("IDC_TRANSMISSION_FORM[ID_"+id+"]",extendName);
//				Boolean uploadSUCESS = false;
//				if(DevContext.IDC_UPLOADFILE_USE_FTP != null && "true".equalsIgnoreCase(DevContext.IDC_UPLOADFILE_USE_FTP)){
//					log.debug("需要通过ftp方式传递:[一般是夸主机的情况.......这种情况少用]");
//					uploadSUCESS = ftpService.pushLocalFileUploadFTP(
//							ftpUtils,
//							file.getInputStream(),
//							fileName,
//							"idc_transmission_form"
//					);
//					assetAttachmentinfo.setFtpAttachName(fileName);
//
//					assetAttachmentinfo.setFtpInfo("利用FTP保存,"+DevContext.FTP_ADDR+":" +
//							""+DevContext.FTP_DIR+"/idc_transmission_form"+
//							":LOCALMAC:["+ LOCALMAC.getLocalMac()+"]::status:"+uploadSUCESS);
//
//				}else{
//					//直接保存到本机服务器
//					String outFilePath = ftpService.pushSystemUploadFile(file.getInputStream(),fileName);
//					if(outFilePath != null){
//						uploadSUCESS = true;
//					}
//					//如果不是本地则直接利用附件路径进行获取
//					assetAttachmentinfo.setFtpAttachName(outFilePath);
//					assetAttachmentinfo.setFtpInfo("直接保存{"+outFilePath+"}:idc_transmission_form"+
//							":LOCALMAC:["+ LOCALMAC.getLocalMac()+"]::status:"+uploadSUCESS);
//				}
//				log.debug("此处不再将附件信息保存到ORACLE中。如果遇到附件比较大。则会使ORACLE查询压力剧增,采用FTP或者直接保存文件上传服务器方式。。。。end");
//
//				assetAttachmentinfo.setLogicTablename("IDC_TRANSMISSION_FORM");
//				assetAttachmentinfo.setLogicColumn("IDC_TRANSMISSION_ID");
//				assetAttachmentinfo.setAttachName(originalFileName);
//				assetAttachmentinfo.setAttachSuffix(extendName);
//				assetAttachmentinfo.setAttachSize(size);
//				assetAttachmentinfo.setRelationalValue(String.valueOf(id));
//				assetAttachmentinfo.setCreateUserid(Long.valueOf(userId));
//				assetAttachmentinfo.setCreateTime(new Date());
//				assetAttachmentinfoMapper.insertAttachInfo(assetAttachmentinfo);
//			}
//		}
//	}
//	/**
//	 * 登录的用户信息：如果还需要其他的数据，请保存在用户信息实体类中
//	 * @return
//	 */
//	public SysUserinfo getPrincipal(){
//		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		if (principal instanceof SysUserinfo) {
//			return (SysUserinfo) principal;
//		}else{
//			return null;
//		}
//	}
//	/*修改附件的时候    先删除附件及文件   再上传*/
//	public void deleteAssetAttachmentinfo(String idcTransmissionId) throws Exception {
//		//根据idcTransmissionId找到附件
//		AssetAttachmentinfo assetAttachmentinfoQ = new AssetAttachmentinfo();
//		assetAttachmentinfoQ.setRelationalValue(idcTransmissionId);
//		assetAttachmentinfoQ.setLogicTablename("IDC_TRANSMISSION_FORM");
//		assetAttachmentinfoQ.setLogicColumn("IDC_TRANSMISSION_ID");
//		AssetAttachmentinfo assetAttachmentinfo = assetAttachmentinfoMapper.getAssetAttachmentinfoBy(assetAttachmentinfoQ);
//		if (assetAttachmentinfo != null) {
//			//删除附件
//			String ftpAttachName = assetAttachmentinfo.getFtpAttachName();
//			FTPUtils ftpUtils = null;
//			if (DevContext.IDC_UPLOADFILE_USE_FTP != null && "true".equalsIgnoreCase(DevContext.IDC_UPLOADFILE_USE_FTP)) {
//				logger.debug("需要通过ftp方式传递:[一般是跨主机的情况:这种情况少用.......]");
//				ftpUtils = FTPUtils.getInstance();
//				//删除附件
//				//这个是文件名称:
//				String ftp_next_dir = Guid.getKhPreChar(ftpAttachName);
//				if (ftp_next_dir != null && !"".equalsIgnoreCase(ftp_next_dir)) {
//					ftp_next_dir = ftp_next_dir.toLowerCase();
//				}
//				//FTP删除附件信息
//				Boolean delSuccess = ftpUtils.deleteFile(ftp_next_dir, ftpAttachName);
//				if (delSuccess) {
//					assetAttachmentinfoMapper.removeAttachById(assetAttachmentinfo.getId());
//				}
//			} else {
//				logger.debug("删除本地文件信息");
//				String localFilePath = Guid.getFirstDkhPreChar(assetAttachmentinfo.getFtpInfo());
//				if (localFilePath == null || "".equalsIgnoreCase(localFilePath)) {
//				/*文件缺失*/
//					logger.error("附件ID:[" + assetAttachmentinfo.getId() + "],对应的ftpInfo的数据为空或者格式不对，请检查ERROR!ERROR!ERROR!");
//				} else {
//					File file = new File(localFilePath);
//					if (!file.exists()) {
//						logger.error("---------------文件缺失---------------");
//					} else {
//						logger.debug("开始删除本地文件:[" + assetAttachmentinfo.getAttachName() + "]");
//						try {
//							file.delete();
//							assetAttachmentinfoMapper.removeAttachById(assetAttachmentinfo.getId());
//						} catch (Exception e) {
//							e.printStackTrace();
//						}
//					}
//				}
//			}
//		}
//	}
}
