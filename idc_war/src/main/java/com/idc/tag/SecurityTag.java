package com.idc.tag;

import com.idc.model.ProductCategory;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;

import javax.servlet.ServletRequest;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;
import java.util.List;

/**
 * 的标签
 */
@Controller
public class SecurityTag extends BodyTagSupport {
    private static final Log log = LogFactory.getLog(SecurityTag.class);
    private static final long serialVersionUID = 3894558024533163703L;
    private List items;//可能传递附件信息
    private String module;
    private Integer moreThan;//至少一个
    private Boolean hasSecurity;//是否有权限:true有  相反没有
    private String comment;//说明
    /*标签类别
    TICKET_RESOURCE_MODULE:工单的时候进行处理
    SIMPLE_MODULE:简单权限
    */
    private Object item;//可以代表任意对象
    public static  String httpSuffix;
    private ServletRequest request;
    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        httpSuffix = pageContext.getServletContext().getContextPath();
        request = pageContext.getRequest();
        StringBuffer sb = new StringBuffer();

        /*********************************合同附件相关控件 start******************************************/
        try {
            if (module != null && "TICKET_RESOURCE_MODULE".equalsIgnoreCase(module) && item != null) {
                if(isIncludeForTicketResource((ProductCategory)item)){
                    return EVAL_BODY_INCLUDE;
                }
            }else if(module != null && "SIMPLE_MODULE".equalsIgnoreCase(module) && hasSecurity != null){
                if(hasSecurity){
                    return EVAL_BODY_INCLUDE;
                }
            }
            /***** 控制是否存在资源分配。资源分配是否属于编辑状态。通过工单ID。获取是否存在资源。如果存在 *****/
        } catch (Exception e) {
            e.printStackTrace();
            return EVAL_BODY_INCLUDE;
        }
        /*********************************合同附件相关控件 end******************************************/
        return SKIP_BODY;
    }
    public Boolean isIncludeForTicketResource(ProductCategory productCategory){
        //只要存在一个就可以
        if(moreThan != null && moreThan > 0){
            if(productCategory.getAdd()){
                return Boolean.TRUE;
            }else if(productCategory.getServer()){
                return Boolean.TRUE;
            }else if(productCategory.getIp()){
                return Boolean.TRUE;
            }else if(productCategory.getPort()){
                return Boolean.TRUE;
            }else if(productCategory.getRack()){
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }


    public List getItems() {
        return items;
    }

    public void setItems(List items) {
        this.items = items;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Object getItem() {
        return item;
    }

    public void setItem(Object item) {
        this.item = item;
    }

    public static String getHttpSuffix() {
        return httpSuffix;
    }

    public static void setHttpSuffix(String httpSuffix) {
        SecurityTag.httpSuffix = httpSuffix;
    }

    public ServletRequest getRequest() {
        return request;
    }

    public void setRequest(ServletRequest request) {
        this.request = request;
    }

    public Integer getMoreThan() {
        return moreThan;
    }

    public void setMoreThan(Integer moreThan) {
        this.moreThan = moreThan;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Boolean getHasSecurity() {
        return hasSecurity;
    }

    public void setHasSecurity(Boolean hasSecurity) {
        this.hasSecurity = hasSecurity;
    }
}
