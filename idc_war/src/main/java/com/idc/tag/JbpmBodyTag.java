package com.idc.tag;

import com.idc.service.IdcReProddefService;
import com.idc.service.IdcReProductService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.io.IOException;

/**
 * 的标签
 */
@Controller
public class JbpmBodyTag extends BodyTagSupport {
    private static final Log log = LogFactory.getLog(JbpmBodyTag.class);
    private static final long serialVersionUID = 3894558024533163703L;
    private String module;
    private Object ticketItem;
    private String prodInstId;
    private String title;
    private IdcReProddefService idcReProddefService;
    private IdcReProductService idcReProductService;
    private ServletRequest request;
    public static  String httpSuffix;
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        httpSuffix = pageContext.getServletContext().getContextPath();
        request = pageContext.getRequest();
        StringBuffer sb = new StringBuffer();
        if (module == null) {

        } else if("TICKET_PRODUCET_MODULE".equals(module)){

            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }
    @Override
    public int doEndTag() throws JspException {
        try {
            if (bodyContent != null) {
                bodyContent.writeOut(bodyContent.getEnclosingWriter());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }
    @Override
    public int doAfterBody() {
       return SKIP_BODY;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Object getTicketItem() {
        return ticketItem;
    }

    public void setTicketItem(Object ticketItem) {
        this.ticketItem = ticketItem;
    }

    public String getProdInstId() {
        return prodInstId;
    }

    public void setProdInstId(String prodInstId) {
        this.prodInstId = prodInstId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
