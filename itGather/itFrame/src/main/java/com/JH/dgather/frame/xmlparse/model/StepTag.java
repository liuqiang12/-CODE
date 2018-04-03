package com.JH.dgather.frame.xmlparse.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Element;

import com.JH.dgather.frame.common.cmd.CMDService;
import com.JH.dgather.frame.common.exception.TelnetException;
import com.JH.dgather.frame.xmlparse.OperaterHandle;
import com.JH.dgather.frame.xmlparse.beans.VarBean;
import com.JH.dgather.frame.xmlparse.exception.TagException;

public class StepTag extends BaseTag {
	private Logger logger = Logger.getLogger(StepTag.class);

	private List<BaseTag> tagLs = new ArrayList<BaseTag>();

	public StepTag(Element element, BaseTag parent) throws TagException {
		super("step", element, parent);
		this.parse();
	}

	protected void parse() throws TagException {
		String errMsg = "";
		errMsg = this.getTagName() + " 标签规则: \r\n";
		errMsg += "【不包含任何属性】\r\n";
		errMsg += "【可包含的子标签】\r\n";
		errMsg += "<command> <matcher> <split> <step> <result> <replace> <forEach>";

		/**
		 * 判断step标签属性值
		 */
		List propLs = this.getElement().attributes();
		if (propLs.size() > 0) {
			logger.error(this.getTagName() + "标签不得包含任务属性值!");
			logger.error(errMsg);
			throw new TagException(this.getTagName() + "标签不得包含任务属性值!");
		}

		/**
		 * 获取step标签下的内容
		 */
		List tagls = this.getElement().elements();
		if (tagls.size() > 0) {
			for (Iterator it = tagls.iterator(); it.hasNext();) {
				Element em = (Element) it.next();
				if (em.getName().equals("step")) {
					StepTag stepT = new StepTag(em, this);
					tagLs.add(stepT);
				} else if (em.getName().equals("command")) {
					checkSameTag("command");
					CommandTag commandTag = new CommandTag(em, this);
					tagLs.add(commandTag);
				} else if (em.getName().equals("matcher")) {
					checkSameTag("matcher");
					MatcherTag matcherTag = new MatcherTag(em, this);
					tagLs.add(matcherTag);
				} else if (em.getName().equals("split")) {
					checkSameTag("split");
					SplitTag splitTag = new SplitTag(em, this);
					tagLs.add(splitTag);
				} else if (em.getName().equals("result")) {
					ResultTag resultTag = new ResultTag(em, this);
					tagLs.add(resultTag);
				} else if (em.getName().equals("replace")) {
					checkSameTag("replace");
					ReplaceTag replaceTag = new ReplaceTag(em, this);
					tagLs.add(replaceTag);
				} else if (em.getName().equals("foreach")) {
					ForeachTag foreachTag = new ForeachTag(em, this);
					tagLs.add(foreachTag);
				} else if (em.getName().equals("validate")) {
					ValidateTag validateTag = new ValidateTag(em, this);
					tagLs.add(validateTag);
				} else if (em.getName().equals("source")) {
					SourceTag sourceTag = new SourceTag(em, this);
					tagLs.add(sourceTag);
				} else if (em.getName().equals("if")) {
					IfTag ifTag = new IfTag(em, this);
					tagLs.add(ifTag);
				} 
				else if("objId".equals(em.getName())) {
					ObjIdTag oidtag = new ObjIdTag(em, this);
					tagLs.add(oidtag);
				} else if("capture".equals(em.getName())) {
					CaptureTag captureTag = new CaptureTag(em, this);
					tagLs.add(captureTag);
				} else if("snmpResult".equals(em.getName())) {
					SnmpResultTag tag = new SnmpResultTag(em, this);
					tagLs.add(tag);
				}
				else {
					logger.error(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
					logger.error(errMsg);
					throw new TagException(this.getTagName() + " 标签不允许包含子标签: " + em.getName());
				}
			}
		} else {
			logger.error(this.getTagName() + "标签内没做配置!");
		}
	}

	/**
	 * <code>judgeSaveTag</code> 判断当前的标签表列中是否已包含相同名称的标签。因为有些标签只能出现一次
	 * 
	 * @param tagName
	 *            : 需要判断的标签名称
	 * @return
	 */
	private void checkSameTag(String tagName) throws TagException {
		for (BaseTag tag : this.tagLs) {
			if (tag.getTagName().equals(tagName)) {
				throw new TagException(this.getTagName() + "标签中不得重复出现 " + tag.getTagName());
			}
		}
	}

	/**
	 * 
	 * @param tl
	 * @param handle
	 * @param log
	 * @param localVarHm
	 *            局部变量
	 * @return
	 * @throws Exception
	 * @throws IOException
	 * @throws TelnetException
	 */
	public void process(CMDService tl, OperaterHandle handle, List<String> logLs, HashMap<String, VarBean> localVarHm) throws TelnetException, IOException, Exception {
		String result = "";
		logger.debug("执行step标签操作！");
		for (BaseTag tag : this.tagLs) {
			if (tag.getTagName().equals("command")) {
				CommandTag commandTag = (CommandTag) tag;
				commandTag.process(tl, handle, logLs, localVarHm);
				result = commandTag.getResult();
			} else if (tag.getTagName().equals("matcher")) {
				MatcherTag matcherTag = (MatcherTag) tag;
				result = matcherTag.process(handle, result, logLs);
			} else if (tag.getTagName().equals("foreach")) {
				ForeachTag foreachTag = (ForeachTag) tag;
				foreachTag.process(tl, handle, logLs, localVarHm);
			} else if (tag.getTagName().equals("result")) {
				ResultTag resultTag = (ResultTag) tag;
				VarBean vb = resultTag.process(handle, result, logLs);
				handle.addResult(resultTag.getName(), vb.getObject());
			} else if (tag.getTagName().equals("split")) {
				SplitTag splitTag = (SplitTag) tag;
				splitTag.process(handle, logLs, localVarHm);
			} else if (tag.getTagName().equals("source")) {
				SourceTag sourceTag = (SourceTag) tag;
				sourceTag.process(handle);
			}
		}
	}

//	public void process(SnmpService snmpService, OperaterHandle handle,
//			List<String> recordLs, Object localVarHm) throws Exception {
//		logger.debug("执行step标签操作！");
//		String ret = null;
//		for (BaseTag tag : this.tagLs) {
//			if(tag.getTagName().equals("objId")) {
//				ObjIdTag t = (ObjIdTag) tag;
//				ret = t.process(snmpService, handle);
//			} else if(tag.getTagName().equals("capture")) {
//				CaptureTag t = (CaptureTag) tag;
//				t.process(handle, ret);
//			} else if("snmpResult".equals(tag.getTagName())) {
//				SnmpResultTag restag = (SnmpResultTag) tag;
//				restag.precess(handle);
//			}
//		}
//	}
	
	/**
	 * @return the tagLs
	 */
	public List<BaseTag> getTagLs() {
		return tagLs;
	}

}
