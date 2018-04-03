package com.JH.dgather.frame.xmlparse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.JH.dgather.frame.common.cmd.CMDService;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.bo.CommandBo;
import com.JH.dgather.frame.xmlparse.bo.ForeachBo;
import com.JH.dgather.frame.xmlparse.bo.IfBo;
import com.JH.dgather.frame.xmlparse.bo.MatcherBo;
import com.JH.dgather.frame.xmlparse.bo.ReplaceBo;
import com.JH.dgather.frame.xmlparse.bo.ResultBo;
import com.JH.dgather.frame.xmlparse.bo.SourceBo;
import com.JH.dgather.frame.xmlparse.bo.SplitBo;
import com.JH.dgather.frame.xmlparse.bo.StepBo;
import com.JH.dgather.frame.xmlparse.bo.ValidateBo;
import com.JH.dgather.frame.xmlparse.bo.iface.IBase;
import com.JH.dgather.frame.xmlparse.bo.iface.IStepBase;
import com.JH.dgather.frame.xmlparse.exception.DataStorageException;
import com.JH.dgather.frame.xmlparse.exception.TagException;
import com.JH.dgather.frame.xmlparse.model.BaseTag;
import com.JH.dgather.frame.xmlparse.model.CaptureTag;
import com.JH.dgather.frame.xmlparse.model.ObjIdTag;
import com.JH.dgather.frame.xmlparse.model.SnmpResultTag;
import com.JH.dgather.frame.xmlparse.service.IBaseService;

/**
 * systemRules.xml中step标签业务处理类
 * 
 * @author zhengbin
 * 
 */
public class StepTagServiceImpl implements IBaseService {
	private static Logger logger = Logger.getLogger(StepTagServiceImpl.class);

	// step标签对应的element对象
	private Element stepEl = null;

	public StepTagServiceImpl(Element element) {
		stepEl = element;
	}

	@Override
	public IBase parse() {
		StepBo stepBo = null;

		// 获取必要参数（由于step没有必要参数，因此stepBo为无数据对象）
		try {
			stepBo = getTagAttribute();
		} catch (TagException e) {
			logger.error(e.getMessage(), e);
			return null;
		}

		// 获取step的子节点信息并放入stepBo，获取成功返回true
		if (isConnSubTag(stepBo)) {
			return stepBo;
		} else {
			return null;
		}
	}

	/**
	 * 从xml的step节点信息中获取标签属性
	 * 
	 * @return step标签业务对象
	 * @throws TagException
	 *             标签不允许包含属性异常
	 */
	@SuppressWarnings("rawtypes")
	private StepBo getTagAttribute() throws TagException {
		StepBo stepBo = new StepBo();
		// 遍历step节点的所有属性
		List propLs = stepEl.attributes();
		// step节点没有属性
		if (propLs.size() > 0) {
			throw new TagException("step标签不得包含任何属性值!");
		}
		return stepBo;
	}

	/**
	 * 是否关联子标签
	 * 
	 * @param ruleBo
	 *            rule标签业务对象（保存父子标签关联）
	 * @return true：关联成功 false：关联失败
	 */
	@SuppressWarnings("rawtypes")
	private boolean isConnSubTag(StepBo stepBo) {
		List<IStepBase> stepBaseList = new ArrayList<IStepBase>();
		// 遍历step节点下子节点信息
		for (Iterator stepBaseI = stepEl.elementIterator(); stepBaseI.hasNext();) {
			Element element = (Element) stepBaseI.next();
			// 当解析的标签tag为step时，对tag进行处理
			if ("step".equals(element.getName())) {
				IBaseService baseService = new StepTagServiceImpl(element);
				StepBo tempStepBo = (StepBo) baseService.parse();
				// 如果step标签解析成功，则保存信息到内存中
				if (null != tempStepBo) {
					stepBaseList.add(tempStepBo);
				} else {
					// stepTag解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			}
			// tagName为command，则按command标签处理解析
			else if ("command".equals(element.getName())) {
				// 如果存在相同的command标签，则解析失败
				if (stepBo.isExistCommand()) {
					logger.error("command标签不能在step标签中重复出现。");
					return false;
				}

				// 解析command标签
				IBaseService baseService = new CommandTagServiceImpl(element);
				CommandBo commandBo = (CommandBo) baseService.parse();
				// 如果command标签解析成功，则保存信息到内存中
				if (null != commandBo) {
					stepBaseList.add(commandBo);
					// 设置command标签已存在标记
					stepBo.setExistCommand(true);
				} else {
					// commandTag解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			}
			// tagName为matcher，按matcher标签处理
			else if ("matcher".equals(element.getName())) {
				// 如果存在相同的matcher标签，则解析失败
				if (stepBo.isExistMatcher()) {
					logger.error("matcher标签不能在step标签中重复出现。");
					return false;
				}

				// 解析matcher标签
				IBaseService baseService = new MatcherTagServiceImpl(element);
				MatcherBo matcherBo = (MatcherBo) baseService.parse();
				// 如果matcher标签解析成功，则保存信息到内存中
				if (null != matcherBo) {
					stepBaseList.add(matcherBo);
					// 设置matcher标签已存在标记
					stepBo.setExistMatcher(true);
				} else {
					// matcherTag解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			} else if ("split".equals(element.getName())) {
				// 如果存在相同的split标签，则解析失败
				if (stepBo.isExistSplit()) {
					logger.error("split标签不能在step标签中重复出现。");
					return false;
				}

				// 解析split标签
				IBaseService baseService = new SplitTagServiceImpl(element);
				SplitBo splitBo = (SplitBo) baseService.parse();
				// 如果split标签解析成功，则保存信息到内存中
				if (null != splitBo) {
					stepBaseList.add(splitBo);
					// 设置split标签已存在标记
					stepBo.setExistSplit(true);
				} else {
					// split解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			} else if ("result".equals(element.getName())) {
				// 解析result标签
				IBaseService baseService = new ResultTagServiceImpl(element);
				ResultBo resultBo = (ResultBo) baseService.parse();
				// 如果result标签解析成功，则保存信息到内存中
				if (null != resultBo) {
					stepBaseList.add(resultBo);
				} else {
					// result解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			} else if ("replace".equals(element.getName())) {
				// 如果存在相同的replace标签，则解析失败
				if (stepBo.isExistReplace()) {
					logger.error("replace标签不能在split标签中重复出现。");
					return false;
				}
				// 解析replace标签
				IBaseService baseService = new ReplaceTagServiceImpl(element);
				ReplaceBo replaceBo = (ReplaceBo) baseService.parse();
				// 如果replace标签解析成功，则保存信息到内存中
				if (null != replaceBo) {
					stepBaseList.add(replaceBo);
					// 设置replace标签已存在标记
					stepBo.setExistReplace(true);
				} else {
					// replace解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			} else if ("foreach".equals(element.getName())) {
				IBaseService baseService = new ForeachTagServiceImpl(element);
				ForeachBo foreachBo = (ForeachBo) baseService.parse();
				// 如果foreach标签解析成功，则保存信息到内存中
				if (null != foreachBo) {
					stepBaseList.add(foreachBo);
				} else {
					// foreach解析失败原因的日志信息在parse（）方法中打印此处不再重复记录日志
					return false;
				}
			} else if ("validate".equals(element.getName())) {
				// 解析validate标签
				IBaseService baseService = new ValidateTagServiceImpl(element);
				ValidateBo validateBo = (ValidateBo) baseService.parse();
				// 如果validate标签解析成功，则保存信息到内存
				if (null != validateBo) {
					stepBaseList.add(validateBo);
				} else {
					return false;
				}
			} else if ("source".equals(element.getName())) {
				IBaseService baseService = new SourceTagServiceImpl(element);
				SourceBo sourceBo = (SourceBo) baseService.parse();
				// 如果source标签解析成功，则保存信息到内存
				if (null != sourceBo) {
					stepBaseList.add(sourceBo);
				} else {
					return false;
				}
			} else if ("if".equals(element.getName())) {
				IBaseService baseService = new IfTagServiceImpl(element);
				IfBo ifBo = (IfBo) baseService.parse();
				// 如果if标签解析成功，则保存信息到内存
				if (null != ifBo) {
					stepBaseList.add(ifBo);
				} else {
					return false;
				}
				// } else if ("objId".equals(em.getName())) {
				// ObjIdTag oidtag = new ObjIdTag(em, this);
				// tagLs.add(oidtag);
				// } else if ("capture".equals(em.getName())) {
				// CaptureTag captureTag = new CaptureTag(em, this);
				// tagLs.add(captureTag);
				// } else if ("snmpResult".equals(em.getName())) {
				// SnmpResultTag tag = new SnmpResultTag(em, this);
				// tagLs.add(tag);
			} else {
				logger.error("step 标签不允许包含子标签: " + element.getName());
				return false;
			}
		}

		// 成功解析出step标签下子节点信息时，解析结果大于0，解析成功
		if (0 < stepBaseList.size()) {
			stepBo.setStepBaseList(stepBaseList);
			return true;
		} else {
			logger.error("解析配置文件失败，无法从配置文件中解析出step的子节点信息，" + stepBo);
			return false;
		}
	}

	/**
	 * step标签业务执行方法
	 * 
	 * @param cmdService
	 *            服务器信息
	 * @param handle
	 *            操作类
	 * @param stepBo
	 *            step标签业务对象
	 * @param localVarHm
	 *            临时参数
	 * @return 执行结果：true执行成功false执行失败
	 */
	public static boolean process(CMDService cmdService, OperaterHandle handle,
			StepBo stepBo, Map<String, VarBean> localVarHm) {
		if (logger.isDebugEnabled()) {
			logger.debug("执行step标签操作！" + stepBo);
		}
		if (null == stepBo) {
			logger.error("step标签业务对象为空，无法获取step的子标签信息");
			return false;
		}
		List<IStepBase> stepBaseList = stepBo.getStepBaseList();
		// 保存回显结果信息
		String result = "";
		// 循环遍历step标签下子标签
		for (IStepBase stepBase : stepBaseList) {
			// 判断step标签下子标签类型，如果为command，执行command业务
			if (("command").equals(stepBase.getTagNameByObject())) {
				// 如果command指令执行成功，直接获取结果
				result = CommandTagServiceImpl.process(cmdService, handle,
						(CommandBo) stepBase, localVarHm);
				if (null == result) {
					return false;
				}
			}
			// 如果标签类型为matcher，则执行matcher业务
			else if (("matcher").equals(stepBase.getTagNameByObject())) {
				// 如果matcher指令执行成功，直接从matcher标签业务对象中获取结果
				result = MatcherTagServiceImpl.process(handle,
						(MatcherBo) stepBase, result);
				if (null == result) {
					return false;
				}
			} else if (("foreach").equals(stepBase.getTagNameByObject())) {
				// 如果foreach指令执行失败则返回false
				if (!ForeachTagServiceImpl.process(cmdService, handle,
						(ForeachBo) stepBase, localVarHm)) {
					return false;
				}
			} else if ("result".equals(stepBase.getTagNameByObject())) {
				Map<String, VarBean> localMap = new HashMap<String, VarBean>();
				VarBean vb = ResultTagServiceImpl.process(handle,
						(ResultBo) stepBase, result, localMap);
				if (null != vb) {
					try {
						handle.addResult(((ResultBo) stepBase).getName(),
								vb.getObject());
					} catch (DataStorageException e) {
						logger.error("result标签执行结果放入全局参数异常"
								+ ((ResultBo) stepBase), e);
						return false;
					}
				}
			} else if ("split".equals(stepBase.getTagNameByObject())) {
				// 如果执行split指令失败，则返回false
				if (!SplitTagServiceImpl.process(handle, (SplitBo) stepBase,
						localVarHm)) {
					return false;
				}
			} else if ("source".equals(stepBase.getTagNameByObject())) {
				if (!SourceTagServiceImpl.process(handle, (SourceBo) stepBase)) {
					return false;
				}
			}
		}
		return true;
	}

	private List<BaseTag> tagLs = null;

//	public void process(SnmpService snmpService, OperaterHandle handle,
//			List<String> recordLs, Object localVarHm) throws Exception {
//		logger.debug("执行step标签操作！");
//		String ret = null;
//		for (BaseTag tag : this.tagLs) {
//			if (tag.getTagName().equals("objId")) {
//				ObjIdTag t = (ObjIdTag) tag;
//				ret = t.process(snmpService, handle);
//			} else if (tag.getTagName().equals("capture")) {
//				CaptureTag t = (CaptureTag) tag;
//				t.process(handle, ret);
//			} else if ("snmpResult".equals(tag.getTagName())) {
//				SnmpResultTag restag = (SnmpResultTag) tag;
//				restag.precess(handle);
//			}
//		}
//	}
}
