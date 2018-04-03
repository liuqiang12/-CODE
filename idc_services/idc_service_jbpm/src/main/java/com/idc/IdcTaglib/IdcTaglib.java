package com.idc.IdcTaglib;

import org.apache.taglibs.standard.lang.support.ExpressionEvaluatorManager;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by WCG on 2017/9/20.
 */
public class IdcTaglib extends SimpleTagSupport {

    @Override
    public void doTag() throws JspException, IOException {

        JspWriter out = getJspContext().getOut();
        out.println("<input class=\"easyui-textbox\" readonly=\"readonly\" data-options=\"required:true,width:150\" " +
                "name=\"name\" value=\"${idcReCustomer.name}\"  " +
                "id=\"name\" data-options=\"validType:'length[0,64]'\"/>");
        out.println("");
    }




    /*public void setValue(Object value)throws JspException{
        this.value = ExpressionEvaluatorManager.evaluate(
                "value", value.toString(), Object.class, this, pageContext);
    }*/




}