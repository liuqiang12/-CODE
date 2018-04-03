package com.idc.service;

import com.idc.model.AssetAttachmentinfo;
import com.idc.model.IdcLinkOutport;
import system.data.page.PageBean;
import system.data.supper.service.SuperService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/**
 * <br>
 * <b>业务接口</b><br>
 * <b>功能：业务表</b>ASSET_ATTACHMENTINFO:ASSET_ATTACHMENTINFO<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jul 05,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface AssetAttachmentinfoService extends SuperService<AssetAttachmentinfo, Long>{
	/**
	 * 通过合同id获取不同的附件
	 * @param attachmentinfoMap
	 * @return
	 */
    List<AssetAttachmentinfo> getAttachmentinfoByRelationalId(Map<String, String> attachmentinfoMap) ;

	/**
	 * 获取附件信息
	 * @param id
	 * @return
	 */
    AssetAttachmentinfo getAttachmentById(Long id);

	Map<String,Object> call_AttachmentHandleSecurity(Map<String, Object> params);

	/**
	 * 删除附件
	 * @param id
	 * @throws Exception
	 */
    void removeAttachById(Long id) throws  Exception;
	void saveAcceptAdjunct(HttpServletRequest request, Map<String, Object> ticketMap) throws  Exception;
	void updateAttachData(AssetAttachmentinfo assetAttachmentinfo) throws Exception;
	List<Long> getTicketInsId(Long id);
	List<Long> getIdsByContractId(Long id);

	/**
	 *
	 */
	List<Map<String,Object>> getAttachmentinfoByTicketInstIdListPage(PageBean pageBean, Map<String, Object> paramMap);

    //保存链路信息
    void saveOrUpdateLinkFrom(IdcLinkOutport idcLinkOutport, Long id, HttpServletRequest request) throws Exception;

    //删除链路信息   附件信息
    void deleteLinkInfoAndAchment(List<String> linkList, List<String> transList) throws Exception;

    //下载附件  通过字段ID
    void downLoadFileByTransmissionId(HttpServletResponse response, String idcTransmissionId) throws Exception;

	/**
	 *
	 * @return
	 */
	List<AssetAttachmentinfo> getAttachmentinfoByTicketInstIdList(String logicColumn, Long prodInstId);

	List<AssetAttachmentinfo> queryAttachmentListPage(PageBean pageBean, Map<String, Object> map);
	/**
	 *
	 * @return
	 */
	List<AssetAttachmentinfo> getAttachmentinfoByTicketIdList(String ticketInstId, String prodInstId, String logicTablename);

}
