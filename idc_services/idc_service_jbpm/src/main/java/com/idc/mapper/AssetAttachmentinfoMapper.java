package com.idc.mapper;

import com.idc.model.AssetAttachmentinfo;
import org.apache.ibatis.annotations.Param;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;

import java.util.List;
import java.util.Map;

public interface AssetAttachmentinfoMapper extends SuperMapper<AssetAttachmentinfo, Long>{
	/**
	 * 通过合同id获取不同的附件
	 * @param attachmentinfoMap
	 * @return
	 */
    List<AssetAttachmentinfo> getAttachmentinfoByRelationalId(Map<String, String> attachmentinfoMap);

    List<AssetAttachmentinfo> queryAttachmentListPage(PageBean pageBean);


	List<Map<String,Object>> getAttachmentinfoByTicketInstIdListPage(PageBean pageBean);

	Map<String,Object> call_AttachmentHandleSecurity(Map<String, Object> params);

	/**
	 * 保存附件信息
	 * @param assetAttachmentinfo
	 * @throws Exception
	 */
    void insertAttachInfo(AssetAttachmentinfo assetAttachmentinfo) throws Exception;
	/**
	 * 获取附件信息
	 * @param id
	 * @return
	 */
    AssetAttachmentinfo getAttachmentById(Long id);
	/**
	 * 删除附件
	 * @param id
	 * @throws Exception
	 */
    void removeAttachById(Long id) throws  Exception;
	void updateAttachData(AssetAttachmentinfo assetAttachmentinfo) throws Exception;
	List<Long> getTicketInsId(@Param("id") Long id);
	List<Long> getIdsByContractId(Long id);

	/*获取附件 通过表明 字段  字段值*/
	AssetAttachmentinfo getAssetAttachmentinfoByParams(AssetAttachmentinfo assetAttachmentinfo);
	List<AssetAttachmentinfo> getAttachmentinfoByTicketInstIdList(@Param("logicColumn") String logicColumn, @Param("prodInstId") Long prodInstId);

	List<AssetAttachmentinfo> getAttachmentinfoByTicketIdList(@Param("ticketInstId") String ticketInstId, @Param("prodInstId") String prodInstId, @Param("logicTablename") String logicTablename);
}

 