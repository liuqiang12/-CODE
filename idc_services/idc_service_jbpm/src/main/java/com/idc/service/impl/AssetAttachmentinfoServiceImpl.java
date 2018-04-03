package com.idc.service.impl;

import com.idc.mapper.AssetAttachmentinfoMapper;
import com.idc.mapper.IdcLinkOutportMapper;
import com.idc.mapper.IdcTransmissionFormMapper;
import com.idc.model.*;
import com.idc.service.AssetAttachmentinfoService;
import com.idc.service.FTPService;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import utils.DevContext;
import utils.LOCALMAC;
import utils.plugins.excel.Guid;
import utils.typeHelper.FTPUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>ASSET_ATTACHMENTINFO:ASSET_ATTACHMENTINFO<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 05,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("assetAttachmentinfoService")
public class AssetAttachmentinfoServiceImpl extends SuperServiceImpl<AssetAttachmentinfo, Long> implements AssetAttachmentinfoService {
	private static final Log log = LogFactory.getLog(AssetAttachmentinfoServiceImpl.class);
	@Autowired
	private AssetAttachmentinfoMapper assetAttachmentinfoMapper;
	@Autowired
	private FTPService ftpService;
	@Autowired
	private IdcLinkOutportMapper idcLinkOutportMapper;
	@Autowired
	private IdcTransmissionFormMapper idcTransmissionFormMapper;

	@Override
	public Map<String,Object> call_AttachmentHandleSecurity(Map<String, Object> params) {
		return assetAttachmentinfoMapper.call_AttachmentHandleSecurity(params);
	}

	/**
	 * 通过合同id获取不同的附件
	 * @param attachmentinfoMap
	 * @return
	 */
	@Override
	public List<AssetAttachmentinfo> getAttachmentinfoByRelationalId(Map<String,String> attachmentinfoMap){
		return assetAttachmentinfoMapper.getAttachmentinfoByRelationalId(attachmentinfoMap);
	}
	/**
	 * 获取附件信息
	 * @param id
	 * @return
	 */
	@Override
	public AssetAttachmentinfo getAttachmentById(Long id){
		return assetAttachmentinfoMapper.getAttachmentById(id);
	}

	@Override
	public 	List<AssetAttachmentinfo> queryAttachmentListPage(PageBean pageBean, Map<String, Object> map){
		pageBean.setParams(map);
		return assetAttachmentinfoMapper.queryAttachmentListPage(pageBean);
	}
	/**
	 * 删除附件
	 * @param id
	 * @throws Exception
	 */
	@Override
	public void removeAttachById(Long id) throws  Exception{
		assetAttachmentinfoMapper.removeAttachById(id);
	}
	@Override
	public void saveAcceptAdjunct(HttpServletRequest request,Map<String,Object> paramMap) throws  Exception{
		String ticketInstId = String.valueOf(paramMap.get("ticketInstId"));
		String prodInstId = String.valueOf(paramMap.get("prodInstId"));
		String attachmentType = String.valueOf(paramMap.get("attachmentType"));

		SysUserinfo sysUserinfo = getPrincipal();
		Integer userId = sysUserinfo.getId();
		String attachmentMaker=sysUserinfo.getNick() +"(ID:"+ userId+")";

		// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得文件：
		MultiValueMap<String,MultipartFile > mr = multipartRequest.getMultiFileMap();
		// 获得文件名：
		/*String filename = file.getOriginalFilename();*/
		// 获得输入流：
		FTPUtils ftpUtils = null;
		if(DevContext.IDC_UPLOADFILE_USE_FTP != null && "true".equalsIgnoreCase(DevContext.IDC_UPLOADFILE_USE_FTP)){
			log.debug("需要通过ftp方式传递:[一般是夸主机的情况.......这种情况少用]");
			ftpUtils = FTPUtils.getInstance();
		}
		for(String fileKey : mr.keySet()){
			MultipartFile file= mr.getFirst(fileKey);
			// 原始文件名
			String originalFileName = file.getOriginalFilename();
			if(originalFileName != null && !"".equals(originalFileName)){
				// 获取文件后缀名（用来生成存放该种类型的所有文件）
				String extendName = originalFileName.substring(originalFileName.lastIndexOf("."));
				//文件的二进制
				byte[] fileBytes = file.getBytes();
				//文件的大小
				//此处还是通过FTP方式上传附件.
				Long size = file.getSize();
				AssetAttachmentinfo assetAttachmentinfo = new AssetAttachmentinfo();
				log.debug("此处不再将附件信息保存到ORACLE中。如果遇到附件比较大。则会使ORACLE查询压力剧增,采用FTP或者直接保存文件上传服务器方式。。。。start");
				String fileName = Guid.getAttchUUID(DevContext.IDC_RE_PRODUCT+"[ID_"+prodInstId+"]",extendName);
				Boolean uploadSUCESS = false;
				if(DevContext.IDC_UPLOADFILE_USE_FTP != null && "true".equalsIgnoreCase(DevContext.IDC_UPLOADFILE_USE_FTP)){
					log.debug("需要通过ftp方式传递:[一般是夸主机的情况.......这种情况少用]");
					uploadSUCESS = ftpService.pushLocalFileUploadFTP(
							ftpUtils,
							file.getInputStream(),
							fileName,
							DevContext.FTP_IDC_RE_PRODUCT_PATH
					);
					assetAttachmentinfo.setFtpAttachName(fileName);

					assetAttachmentinfo.setFtpInfo("利用FTP保存,"+DevContext.FTP_ADDR+":" +
							""+DevContext.FTP_DIR+"/"+DevContext.FTP_IDC_RE_PRODUCT_PATH+"" +
							":LOCALMAC:["+ LOCALMAC.getLocalMac()+"]::status:"+uploadSUCESS);

				}else{
					//直接保存到本机服务器
					String outFilePath = ftpService.pushSystemUploadFile(file.getInputStream(),fileName);
					if(outFilePath != null){
						uploadSUCESS = true;
					}
					//如果不是本地则直接利用附件路径进行获取
					assetAttachmentinfo.setFtpAttachName(outFilePath);
					assetAttachmentinfo.setFtpInfo("直接保存{"+outFilePath+"}:"+DevContext.FTP_IDC_RE_PRODUCT_PATH+"" +
							":LOCALMAC:["+ LOCALMAC.getLocalMac()+"]::status:"+uploadSUCESS);
				}
				log.debug("此处不再将附件信息保存到ORACLE中。如果遇到附件比较大。则会使ORACLE查询压力剧增,采用FTP或者直接保存文件上传服务器方式。。。。end");

				assetAttachmentinfo.setLogicTablename(DevContext.IDC_RE_PRODUCT);
				assetAttachmentinfo.setLogicColumn("PROD_INST_ID");
				assetAttachmentinfo.setAttachName(originalFileName);
				assetAttachmentinfo.setAttachSuffix(extendName);
				assetAttachmentinfo.setAttachSize(size);
				assetAttachmentinfo.setRelationalValue(prodInstId);
				//assetAttachmentinfo.setAttachByte(fileBytes);
				assetAttachmentinfo.setCreateUserid(Long.valueOf(userId));
				assetAttachmentinfo.setTicketInstId(Long.valueOf(ticketInstId));//[这个其实这里没有使用]
				assetAttachmentinfo.setProdInstId(Long.valueOf(prodInstId));
				assetAttachmentinfo.setAttachmentType(attachmentType);
				assetAttachmentinfo.setAttachmentMaker(attachmentMaker);
				if(paramMap.get("logicTablename")!=null){
					assetAttachmentinfo.setLogicTablename(paramMap.get("logicTablename").toString());
				}

				if(uploadSUCESS != null && uploadSUCESS == true){
					assetAttachmentinfoMapper.insertAttachInfo(assetAttachmentinfo);
				}
			}
		}
	}
	/**
	 * 登录的用户信息：如果还需要其他的数据，请保存在用户信息实体类中
	 * @return
	 */
	public SysUserinfo getPrincipal(){
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if (principal instanceof SysUserinfo) {
			return (SysUserinfo) principal;
		}else{
			return null;
		}
	}


    @Override
    public void updateAttachData(AssetAttachmentinfo assetAttachmentinfo) throws Exception {
        assetAttachmentinfoMapper.updateAttachData(assetAttachmentinfo);
    }

    @Override
    public List<Long> getTicketInsId(Long id) {
        return assetAttachmentinfoMapper.getTicketInsId(id);
    }

    @Override
    public List<Long> getIdsByContractId(Long id) {
        return assetAttachmentinfoMapper.getIdsByContractId(id);
    }

	/**
	 *
	 */
	@Override
	public List<Map<String,Object>> getAttachmentinfoByTicketInstIdListPage(PageBean pageBean,Map<String,Object> paramMap){
		pageBean.setParams(paramMap);
		return assetAttachmentinfoMapper.getAttachmentinfoByTicketInstIdListPage(pageBean);
	}

	/*保存链路信息*/
	@Override
	public void saveOrUpdateLinkFrom(IdcLinkOutport idcLinkOutport, Long id, HttpServletRequest request) throws Exception {
		String idcTransmissionCode = request.getParameter("idcTransmissionCode");
		String note = request.getParameter("idcTransmissionNote");
		String idcTransmissionId = request.getParameter("idcTransmissionId");
		IdcTransmissionForm idcTransmissionForm = new IdcTransmissionForm();
		if (id != null) {//修改
			if (idcTransmissionId != null && !"".equals(idcTransmissionId)) {//有编码才能上传附件
				idcTransmissionForm.setIdcTransmissionCode(idcTransmissionCode);
				idcTransmissionForm.setNote(note);
				idcTransmissionForm.setIdcTransmissionId(Long.parseLong(idcTransmissionId));
				idcTransmissionFormMapper.updateByObject(idcTransmissionForm);
				//先删除原来的附件在上传
				deleteAssetAttachmentinfo(idcTransmissionId);
				//上传附件
				uploadAttachment(request, idcTransmissionForm.getIdcTransmissionId());
			}
			idcLinkOutport.setIdcLinkInfoId(id);
			idcLinkOutport.setIdcTransmissionId(idcTransmissionForm.getIdcTransmissionId());
			idcLinkOutportMapper.updateByObject(idcLinkOutport);
		} else {//新增   可能会有多条链路
			//添加链路信息
			List<IdcLinkOutport> outportList = new ArrayList<>();
            String localOdfPort = idcLinkOutport.getIdcLinkLocalOdfPort() == null ? "" : idcLinkOutport.getIdcLinkLocalOdfPort();//本端ODF端口
            String targetOdfPort = idcLinkOutport.getIdcLinkTargetOdfPort() == null ? "" : idcLinkOutport.getIdcLinkTargetOdfPort();//对端ODF端口
            String localDevicePort = idcLinkOutport.getIdcLinkLocalDevicePort() == null ? "" : idcLinkOutport.getIdcLinkLocalDevicePort();//本端设备端口
            String targetDevicePort = idcLinkOutport.getIdcLinkTargetDevicePort() == null ? "" : idcLinkOutport.getIdcLinkTargetDevicePort();//对端设备端口
            List<String> localOdfPortArr = new ArrayList<>();
            List<String> targetOdfPortArr = new ArrayList<>();
            List<String> localDevicePortArr = new ArrayList<>();
            List<String> targetDevicePortArr = new ArrayList<>();
            while (localOdfPort.indexOf(',') > -1) {
                String str = localOdfPort.substring(0, localOdfPort.indexOf(','));
                localOdfPort = localOdfPort.substring(localOdfPort.indexOf(',') + 1);
                localOdfPortArr.add(str);
            }
            localOdfPortArr.add(localOdfPort);
            while (targetOdfPort.indexOf(',') > -1) {
                String str = targetOdfPort.substring(0, targetOdfPort.indexOf(','));
                targetOdfPort = targetOdfPort.substring(targetOdfPort.indexOf(',') + 1);
                targetOdfPortArr.add(str);
            }
            targetOdfPortArr.add(targetOdfPort);
            while (localDevicePort.indexOf(',') > -1) {
                String str = localDevicePort.substring(0, localDevicePort.indexOf(','));
                localDevicePort = localDevicePort.substring(localDevicePort.indexOf(',') + 1);
                localDevicePortArr.add(str);
            }
            localDevicePortArr.add(localDevicePort);
            while (targetDevicePort.indexOf(',') > -1) {
                String str = targetDevicePort.substring(0, targetDevicePort.indexOf(','));
                targetDevicePort = targetDevicePort.substring(targetDevicePort.indexOf(',') + 1);
                targetDevicePortArr.add(str);
            }
            targetDevicePortArr.add(targetDevicePort);
            for (int i = 0; i < localOdfPortArr.size(); i++) {
                IdcLinkOutport param = new IdcLinkOutport();
                loadIdcLinkOutportInfo(param, idcLinkOutport);
                param.setIdcLinkLocalOdfPort(localOdfPortArr.get(i));
                param.setIdcLinkTargetOdfPort(targetOdfPortArr.get(i));
                param.setIdcLinkLocalDevicePort(localDevicePortArr.get(i));
                param.setIdcLinkTargetDevicePort(targetDevicePortArr.get(i));
                outportList.add(param);
            }
			//去重
			HashSet set = new HashSet(outportList);
			outportList.clear();
			outportList.addAll(set);
			//新增
			for (IdcLinkOutport linkOutport : outportList) {
				//添加调度信息
				if (idcTransmissionCode != null && !"".equals(idcTransmissionCode)) {//有编码才能上传附件
					idcTransmissionForm.setIdcTransmissionCode(idcTransmissionCode);
					idcTransmissionForm.setNote(note);
					idcTransmissionFormMapper.insert(idcTransmissionForm);
					//上传附件
					uploadAttachment(request, idcTransmissionForm.getIdcTransmissionId());
				}
				linkOutport.setIdcTransmissionId(idcTransmissionForm.getIdcTransmissionId());
			}
			idcLinkOutportMapper.insertList(outportList);
		}
	}

    //删除链路信息   附件信息
    @Override
    public void deleteLinkInfoAndAchment(List<String> linkList, List<String> transList) throws Exception {
        if (linkList != null) {
            //删除链路信息
            idcLinkOutportMapper.deleteByList(linkList);
            //删除调度信息
            idcTransmissionFormMapper.deleteByList(transList);
            //删除附件信息
            for (String idcTransmissionId : transList) {
                deleteAssetAttachmentinfo(idcTransmissionId);
            }
        }
    }

    //下载附件   通过字段值
    @Override
    public void downLoadFileByTransmissionId(HttpServletResponse response, String idcTransmissionId) throws Exception {
        //根据idcTransmissionId找到附件
        AssetAttachmentinfo assetAttachmentinfoQ = new AssetAttachmentinfo();
        assetAttachmentinfoQ.setRelationalValue(idcTransmissionId);
        assetAttachmentinfoQ.setLogicTablename("IDC_TRANSMISSION_FORM");
        assetAttachmentinfoQ.setLogicColumn("IDC_TRANSMISSION_ID");
        AssetAttachmentinfo assetAttachmentinfo = assetAttachmentinfoMapper.getAssetAttachmentinfoByParams(assetAttachmentinfoQ);
        String ftpAttachName = assetAttachmentinfo.getFtpAttachName();
        /**
         * 读取文件流情况
         */
        FTPUtils ftpUtils = null;
        if (DevContext.IDC_UPLOADFILE_USE_FTP != null && "true".equalsIgnoreCase(DevContext.IDC_UPLOADFILE_USE_FTP)) {
            logger.debug("需要通过ftp方式传递:[一般是跨主机的情况:这种情况少用.......]");
            ftpUtils = FTPUtils.getInstance();
            //这个是文件名称:
            String ftp_next_dir = Guid.getKhPreChar(ftpAttachName);
            if (ftp_next_dir != null && !"".equalsIgnoreCase(ftp_next_dir)) {
                ftp_next_dir = ftp_next_dir.toLowerCase();
            }
            //文件流
            InputStream in = ftpUtils.downLoadFile(ftp_next_dir, ftpAttachName);
            /**
             * 附件信息
             */
            downAsmentFile(in, response, assetAttachmentinfo.getAttachName());
        } else {
            logger.debug("然后获取本地的附件流[.......]");
            String localFilePath = Guid.getFirstDkhPreChar(assetAttachmentinfo.getFtpInfo());
            if (localFilePath == null || "".equalsIgnoreCase(localFilePath)) {
                /*文件缺失*/
                logger.error("附件ID:[" + assetAttachmentinfo.getId() + "],对应的ftpInfo的数据为空或者格式不对，请检查ERROR!ERROR!ERROR!");
            } else {
                File file = new File(localFilePath);
                if (!file.exists()) {
                    logger.error("---------------文件缺失---------------");
                } else {
                    logger.debug("开始下载相应的文件:[" + assetAttachmentinfo.getAttachName() + "]");
                    FileInputStream in = new FileInputStream(file);
					response.reset(); // 非常重要
					downAsmentFile(in, response, assetAttachmentinfo.getAttachName());
                }
            }
        }
    }


	/**
	 *
	 */
	@Override
	public List<AssetAttachmentinfo> getAttachmentinfoByTicketInstIdList(String logicColumn,Long prodInstId) {
		return assetAttachmentinfoMapper.getAttachmentinfoByTicketInstIdList(logicColumn,prodInstId);
	}
	/**
	 *
	 */
	@Override
	public List<AssetAttachmentinfo> getAttachmentinfoByTicketIdList(String ticketInstId,String prodInstId,String logicTablename ) {
		return assetAttachmentinfoMapper.getAttachmentinfoByTicketIdList(ticketInstId,prodInstId,logicTablename );
	}

	//将链路信息封装到另一个对象
	public void loadIdcLinkOutportInfo(IdcLinkOutport outportA, IdcLinkOutport outportB) {
		outportA.setIdcLinkFromArea(outportB.getIdcLinkFromArea());
		outportA.setIdcLinkRoomName(outportB.getIdcLinkRoomName());
		outportA.setIdcLinkLocalRouteName(outportB.getIdcLinkLocalRouteName());
		outportA.setIdcLinkCabinetPosition(outportB.getIdcLinkCabinetPosition());
		outportA.setIdcLinkDeviceModel(outportB.getIdcLinkDeviceModel());
		outportA.setIdcLinkTapeWidth(outportB.getIdcLinkTapeWidth());
		outportA.setIdcLinkLocalOdfPort(outportB.getIdcLinkLocalOdfPort());
		outportA.setIdcLinkLocalDevicePort(outportB.getIdcLinkLocalDevicePort());
		outportA.setIdcLinkTargetRoomName(outportB.getIdcLinkTargetRoomName());
		outportA.setIdcLinkTargetRouteName(outportB.getIdcLinkTargetRouteName());
		outportA.setIdcLinkTargetDeviceModel(outportB.getIdcLinkTargetDeviceModel());
		outportA.setIdcLinkTargetOdfPort(outportB.getIdcLinkTargetOdfPort());
		outportA.setIdcLinkTargetDevicePort(outportB.getIdcLinkTargetDevicePort());
		outportA.setNote(outportB.getNote());
		outportA.setIdcTransmissionId(outportB.getIdcTransmissionId());
	}

	//上传附件
	public void uploadAttachment(HttpServletRequest request, Long id) throws Exception {
		// 转型为MultipartHttpRequest：
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 获得文件：
		MultiValueMap<String, MultipartFile> mr = multipartRequest.getMultiFileMap();
		SysUserinfo sysUserinfo = getPrincipal();
		Integer userId = sysUserinfo.getId();
		// 获得文件名：
		/*String filename = file.getOriginalFilename();*/
		// 获得输入流：
		FTPUtils ftpUtils = null;
		if (DevContext.IDC_UPLOADFILE_USE_FTP != null && "true".equalsIgnoreCase(DevContext.IDC_UPLOADFILE_USE_FTP)) {
			log.debug("需要通过ftp方式传递:[一般是夸主机的情况.......这种情况少用]");
			ftpUtils = FTPUtils.getInstance();
		}
		for (String fileKey : mr.keySet()) {
			MultipartFile file = mr.getFirst(fileKey);
			// 原始文件名
			String originalFileName = file.getOriginalFilename();
			if (originalFileName != null && !"".equals(originalFileName)) {
				// 获取文件后缀名（用来生成存放该种类型的所有文件）
				String extendName = originalFileName.substring(originalFileName.lastIndexOf("."));
				//文件的二进制
				byte[] fileBytes = file.getBytes();
				//文件的大小
				//此处还是通过FTP方式上传附件.
				Long size = file.getSize();
				AssetAttachmentinfo assetAttachmentinfo = new AssetAttachmentinfo();
				log.debug("此处不再将附件信息保存到ORACLE中。如果遇到附件比较大。则会使ORACLE查询压力剧增,采用FTP或者直接保存文件上传服务器方式。。。。start");
				String fileName = Guid.getAttchUUID("IDC_TRANSMISSION_FORM[ID_" + id + "]", extendName);
				Boolean uploadSUCESS = false;
				if (DevContext.IDC_UPLOADFILE_USE_FTP != null && "true".equalsIgnoreCase(DevContext.IDC_UPLOADFILE_USE_FTP)) {
					log.debug("需要通过ftp方式传递:[一般是夸主机的情况.......这种情况少用]");
					uploadSUCESS = ftpService.pushLocalFileUploadFTP(
							ftpUtils,
							file.getInputStream(),
							fileName,
							"idc_transmission_form"
					);
					assetAttachmentinfo.setFtpAttachName(fileName);

					assetAttachmentinfo.setFtpInfo("利用FTP保存," + DevContext.FTP_ADDR + ":" +
							"" + DevContext.FTP_DIR + "/idc_transmission_form" +
							":LOCALMAC:[" + LOCALMAC.getLocalMac() + "]::status:" + uploadSUCESS);

				} else {
					//直接保存到本机服务器
					String outFilePath = ftpService.pushSystemUploadFile(file.getInputStream(), fileName);
					if (outFilePath != null) {
						uploadSUCESS = true;
					}
					//如果不是本地则直接利用附件路径进行获取
					assetAttachmentinfo.setFtpAttachName(outFilePath);
					assetAttachmentinfo.setFtpInfo("直接保存{" + outFilePath + "}:idc_transmission_form" +
							":LOCALMAC:[" + LOCALMAC.getLocalMac() + "]::status:" + uploadSUCESS);
				}
				log.debug("此处不再将附件信息保存到ORACLE中。如果遇到附件比较大。则会使ORACLE查询压力剧增,采用FTP或者直接保存文件上传服务器方式。。。。end");

				assetAttachmentinfo.setLogicTablename("IDC_TRANSMISSION_FORM");
				assetAttachmentinfo.setLogicColumn("IDC_TRANSMISSION_ID");
				assetAttachmentinfo.setAttachName(originalFileName);
				assetAttachmentinfo.setAttachSuffix(extendName);
				assetAttachmentinfo.setAttachSize(size);
				assetAttachmentinfo.setRelationalValue(String.valueOf(id));
				assetAttachmentinfo.setCreateUserid(Long.valueOf(userId));
				assetAttachmentinfo.setCreateTime(new Date());
				assetAttachmentinfoMapper.insertAttachInfo(assetAttachmentinfo);
			}
		}
	}

	/*修改附件的时候    先删除附件及文件   再上传*/
	public void deleteAssetAttachmentinfo(String idcTransmissionId) throws Exception {
		//根据idcTransmissionId找到附件
		AssetAttachmentinfo assetAttachmentinfoQ = new AssetAttachmentinfo();
		assetAttachmentinfoQ.setRelationalValue(idcTransmissionId);
		assetAttachmentinfoQ.setLogicTablename("IDC_TRANSMISSION_FORM");
		assetAttachmentinfoQ.setLogicColumn("IDC_TRANSMISSION_ID");
		AssetAttachmentinfo assetAttachmentinfo = assetAttachmentinfoMapper.getAssetAttachmentinfoByParams(assetAttachmentinfoQ);
		if (assetAttachmentinfo != null) {
			//删除附件
			String ftpAttachName = assetAttachmentinfo.getFtpAttachName();
			FTPUtils ftpUtils = null;
			if (DevContext.IDC_UPLOADFILE_USE_FTP != null && "true".equalsIgnoreCase(DevContext.IDC_UPLOADFILE_USE_FTP)) {
				logger.debug("需要通过ftp方式传递:[一般是跨主机的情况:这种情况少用.......]");
				ftpUtils = FTPUtils.getInstance();
				//删除附件
				//这个是文件名称:
				String ftp_next_dir = Guid.getKhPreChar(ftpAttachName);
				if (ftp_next_dir != null && !"".equalsIgnoreCase(ftp_next_dir)) {
					ftp_next_dir = ftp_next_dir.toLowerCase();
				}
				//FTP删除附件信息
				Boolean delSuccess = ftpUtils.deleteFile(ftp_next_dir, ftpAttachName);
				if (delSuccess) {
					assetAttachmentinfoMapper.removeAttachById(assetAttachmentinfo.getId());
				}
			} else {
				logger.debug("删除本地文件信息");
				String localFilePath = Guid.getFirstDkhPreChar(assetAttachmentinfo.getFtpInfo());
				if (localFilePath == null || "".equalsIgnoreCase(localFilePath)) {
				/*文件缺失*/
					logger.error("附件ID:[" + assetAttachmentinfo.getId() + "],对应的ftpInfo的数据为空或者格式不对，请检查ERROR!ERROR!ERROR!");
				} else {
					File file = new File(localFilePath);
					if (!file.exists()) {
						logger.error("---------------文件缺失---------------");
					} else {
						logger.debug("开始删除本地文件:[" + assetAttachmentinfo.getAttachName() + "]");
						try {
							file.delete();
							assetAttachmentinfoMapper.removeAttachById(assetAttachmentinfo.getId());
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}

    //下载附件
    public void downAsmentFile(InputStream in, HttpServletResponse response, String fileName) {
        if (fileName == null || "".equalsIgnoreCase(fileName)) {
            logger.error("附件名称没有" + fileName);
        }

        if (in != null) {
            OutputStream out = null;
            try {
				response.setHeader("Content-Disposition", "attachment; filename=" + new String(fileName.getBytes("gb2312"), "ISO8859-1"));
				BufferedInputStream br = new BufferedInputStream(in);
				byte[] buf = new byte[1024];
				int len = 0;
				out = response.getOutputStream();
				while ((len = br.read(buf)) > 0) {
					out.write(buf, 0, len);
					out.flush();
				}
			} catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (out != null) {
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
