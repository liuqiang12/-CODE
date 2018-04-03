package com.idc.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.idc.model.IdcDevice;
import org.apache.poi.ss.formula.functions.T;
import system.data.page.PageBean;
import system.data.supper.mapper.SuperMapper;
import com.idc.model.IdcMcb;
/**
 * <br>
 * <b>实体类</b><br>
 * <b>功能：业务表</b>IDC_MCB:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 05,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 */
public interface IdcMcbMapper extends SuperMapper<IdcMcb, Long>{
	/**
	 *   Special code can be written here 
	 */
	public List<IdcMcb> queryListPageByName(PageBean<T> page);

	public List<Map<String,Object>> getAllMcbInfos();

	public List<Map<String,Object>> queryListPageMap(PageBean<T> page);

	public List<Map<String,Object>> queryListByObjectMap(IdcMcb idcMcb);

	/*通过mcbId  解除客户机架与mcb的绑定关系*/
	void unbindServiceRackById(Long mcbId) throws Exception;

	/*通过mcbIds  绑定客户机架与mcb的绑定关系*/
	void bindServiceRackById(Map<String, Object> map) throws Exception;

	/*修改MCB状态并绑定客户架 map中key:id-MCBID，status-状态，customerId-客户ID，customerName-客户名称，ticketId-工单号*/
	void updateMcbStatusByIds(List<Map<String, Object>> list) throws Exception;

	/*获取当前PDF架mcb总数及空闲mcb数*/
	Map<String, Object> queryMcbTotalAndFreeMcbByPdfId(Long pdfId);
}

 