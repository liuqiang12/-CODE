package com.idc.service.impl;

import com.idc.mapper.IdcRackMapper;
import com.idc.model.ExecuteResult;
import com.idc.model.IdcDevice;
import com.idc.model.IdcRack;
import org.apache.commons.collections.map.HashedMap;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import system.data.page.PageBean;
import system.data.supper.service.impl.SuperServiceImpl;
import com.idc.mapper.IdcMcbMapper;
import com.idc.model.IdcMcb;
import com.idc.service.IdcMcbService;
import utils.typeHelper.MapHelper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * <b>业务实现层</b><br>
 * <b>功能：业务表</b>IDC_MCB:${tableData.tableComment}<br>
 * <b>作者：Administrator</b><br>
 * <b>日期：</b> Jun 05,2017 <br>
 * <b>版权所有：<b>版权所有(C) 2016<br>
 * <b>默认的数据源是"mysql_master"<br>
 * <b>如果单独的方法使用备用数据源，在方法上增加注解如:@DataSource(value = "mysql_salve")<br>
 */
@Service("idcMcbService")
public class IdcMcbServiceImpl extends SuperServiceImpl<IdcMcb, Long> implements IdcMcbService {
	@Autowired
	private IdcMcbMapper mapper;
	@Autowired
	private IdcRackMapper idcRackMapper;

	public List<IdcMcb> queryListPageByName(PageBean<T> page, Object param){
		//这里讲查询条件进行处理
		page.setParams(MapHelper.queryCondition(param));
		//真正执行查询分页
		return mapper.queryListPageByName(page);
	}

	public List<Map<String,Object>> getAllMcbInfos(){
		return mapper.getAllMcbInfos();
	}

	@Override
	public List<Map<String, Object>> queryListPageMap(PageBean<T> page, Object param) {
		//这里讲查询条件进行处理
		page.setParams(MapHelper.queryCondition(param));
		//真正执行查询分页
		return mapper.queryListPageMap(page);
	}

	@Override
	public List<Map<String, Object>> queryListByObjectMap(IdcMcb idcMcb) {
		return mapper.queryListByObjectMap(idcMcb);
	}

	/*通过mcbId  解除客户机架与mcb的绑定关系*/
	@Override
	public void unbindServiceRackById(Long mcbId) throws Exception {
		mapper.unbindServiceRackById(mcbId);
	}

	/*通过mcbIds  绑定客户机架与mcb的绑定关系*/
	@Override
	public void bindServiceRackById(Map<String, Object> map) throws Exception {
		mapper.bindServiceRackById(map);
	}

	/*修改MCB状态并绑定客户架 map中key:id-MCBID，status-状态，customerId-客户ID，customerName-客户名称，ticketId-工单号*/
	@Override
	public void updateMcbStatusByMcbIds(List<Map<String, Object>> list, Long pdfId) throws Exception {
		if (list != null && list.size() > 0) {
			//修改MCB状态 并绑定客户架
			mapper.updateMcbStatusByIds(list);
			//查看当前机架空闲机位数
//			if (pdfId == null) {
//				Long id = Long.parseLong(list.get(0).get("id").toString());
//				IdcMcb idcMcb = mapper.getModelById(id);
//				pdfId = idcMcb.getPwrInstalledrackId();
//			}
		}
		//更新PDF架状态   暂时不更新
		//updatePdfStatusByPdfId(pdfId);
	}

	//修改MCB状态后去更新其PDF架状态
	public void updatePdfStatusByPdfId(Long pdfId) throws Exception {
		//获取当前PDF架mcb总数及空闲mcb数
		Map<String, Object> mcbNumMap = mapper.queryMcbTotalAndFreeMcbByPdfId(pdfId);
		int mcbTotal = mcbNumMap.get("MCBTOTAL") == null ? 0 : Integer.parseInt(mcbNumMap.get("MCBTOTAL").toString());
		int freemcbnum = mcbNumMap.get("FREEMCBNUM") == null ? 0 : Integer.parseInt(mcbNumMap.get("FREEMCBNUM").toString());
		if (freemcbnum > 0) {//当存在空闲mcb时，将机架状态改为20-可用
			IdcRack idcRack = new IdcRack();
			idcRack.setId(pdfId.intValue());
			if (freemcbnum < mcbTotal) {
				idcRack.setStatus(20);
			} else {
				idcRack.setStatus(40);
			}
			idcRackMapper.updateByObject(idcRack);
		} else {//当不存在空闲机位时，将机架状态改为60-在服
			IdcRack idcRack = new IdcRack();
			idcRack.setId(pdfId.intValue());
			idcRack.setStatus(60);
			idcRackMapper.updateByObject(idcRack);
		}
	}
}
