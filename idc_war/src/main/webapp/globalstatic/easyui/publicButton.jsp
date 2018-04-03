<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="jbpm" uri="http://jbpm.idc.tag.cn/" %>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="jbpmSecurity" uri="http://jbpmSecurity.idc.tag.cn/" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>

</head>
<body>
    <input type="hidden" name="idcRunTicket.prodInstId" value="${idcTicket.prodInstId}">
    <input type="hidden" name="idcRunTicket.prodInstId" value="${idcTicket.prodInstId}">

</body>

<script type="text/javascript">
/**
 * 审批的按钮控制:此时还是需要利用任务名称来处理....
 */
    //审批按钮_政企_同意
    var button_agree = '<sec:authorize access="hasAnyRole('ROLE_button_agree')" >true</sec:authorize>';
    //审批按钮_政企_删除
    var button_delete = '<sec:authorize access="hasAnyRole('ROLE_button_delete')" >true</sec:authorize>';
    //审批按钮_政企_驳回
    var button_reject = '<sec:authorize access="hasAnyRole('ROLE_button_reject')" >true</sec:authorize>';
    //审批按钮_政企_作废
    var button_rubbish = '<sec:authorize access="hasAnyRole('ROLE_button_rubbish')" >true</sec:authorize>';

    //审批_自有_同意
    var button_self_agree = '<sec:authorize access="hasAnyRole('ROLE_button_self_agree')" >true</sec:authorize>';
    //审批_自有_删除
    var button_self_delete = '<sec:authorize access="hasAnyRole('ROLE_button_self_delete')" >true</sec:authorize>';
    //审批_自有_驳回
    var button_self_reject = '<sec:authorize access="hasAnyRole('ROLE_button_self_reject')" >true</sec:authorize>';
    //审批_自有_作废
    var button_self_rubbish = '<sec:authorize access="hasAnyRole('ROLE_button_self_rubbish')" >true</sec:authorize>';

function applyBtns(taskName,formKey,ticketStatus,canPermission,applyId,loginUserId){

    var btns = ['关闭'];//默认都有同意和关闭
    //如果是申请中，第一个是提交申请,同时最后一个添加作废按钮
    var applyingArr = ['预占申请','变更申请','停机申请','复通申请','下线申请','开通申请','变更开通申请'];
    /*状态:  1同意、0初始化工单、  -1不同意|驳回、作废-2、删除到回收站-3、2结束*/
    if((button_delete  || button_self_delete) &&  canPermission != 'true'){
        //作废、删除回收[后台过滤不显示]、结束
        if(button_delete  || button_self_delete){
            btns.splice(0,0,'删除');
        }
    }else if(ticketStatus == 1 && canPermission=='true'){
        //同意
        if(button_agree || button_self_agree){
            var lastStepFormReg = /comment_form$/g;   //验证是否是流程最后评分一步
            var isLastStep = lastStepFormReg.test(formKey);   //如果为true说明是最后评分这一步

            if(button_delete  || button_self_delete){
                btns.splice(0,0,'删除');
            }

            if((button_reject || button_self_reject) && !isLastStep){    //评分这一步不能驳回！，其他都能驳回
                btns.splice(0,0,'驳回');
            }
            if(isLastStep){
                //最后评分一步，必须是申请人才能评分
                if(applyId == loginUserId){
                    btns.splice(0,0,'评分');
                }
            }else{
                btns.splice(0,0,'同意');
            }
        }
    }else if(ticketStatus == -1 &&  canPermission=='true'){
        //驳回后，如果有删除权限就可以删除
        if(button_delete  || button_self_delete){
            btns.splice(0,0,'删除');
        }
        //驳回后，如果有作废权限就可以作废
        if(button_rubbish  || button_self_rubbish){
            btns.splice(0,0,'作废');
        }
        //驳回后，如果有作废权限就可以作废
        if(button_agree  || button_self_agree){
            btns.splice(0,0,'重新提交');
        }
    }else{
        //alert("按钮   查看！")
        var lastStepFormReg = /comment_form$/g;
        var isLastStep = lastStepFormReg.test(formKey);
        if(isLastStep){

        }
    }
    return btns;
}

/**
 * 对应的方法集合:从btn2开始
 * @param btns
 */
function applyBtnFun(taskName,formKey,ticketStatus,canPermission,applyId,loginUserId){

    /*=========================*/
    //var btns = [yesJbpmFun,rubbishJbpmTicketFun,rejectJbpmTicketFun,delJbpmTicketFun,closeJbpmTicketFun];//默认都有同意和关闭
    var btns = [closeJbpmTicketFun];//默认都有同意和关闭

    if((button_delete  || button_self_delete) &&  canPermission != 'true'){
        //作废、删除回收[后台过滤不显示]、结束
        if(button_delete  || button_self_delete){
            btns.splice(0,0,delJbpmTicketFun);
        }
    }else if(ticketStatus == 1 &&  canPermission=='true'){
        //同意
        if(button_agree || button_self_agree){

            var lastStepFormReg = /comment_form$/g;   //验证是否是流程最后评分一步
            var isLastStep = lastStepFormReg.test(formKey);   //如果为true说明是最后评分这一步

            if(button_delete  || button_self_delete){
                btns.splice(0,0,delJbpmTicketFun);
            }

            if((button_reject || button_self_reject) && !isLastStep){    //评分这一步不能驳回！，其他都能驳回
                btns.splice(0,0,rejectJbpmTicketFun);
            }
            if(isLastStep){
                //只有登陆人就是申请人，才能进行评分
                if(applyId == loginUserId){
                    btns.splice(0,0,yesJbpmFun);
                }
            }else{
                btns.splice(0,0,yesJbpmFun);
            }
        }
    }else if(ticketStatus == -1 &&  canPermission=='true'){
        //驳回后，如果有删除权限就可以删除
        if(button_delete  || button_self_delete){
            btns.splice(0,0,delJbpmTicketFun);
        }
        //驳回后，如果有作废权限就可以作废
        if(button_rubbish  || button_self_rubbish){
            btns.splice(0,0,rubbishJbpmTicketFun);
        }
        //驳回后，如果有作废权限就可以作废
        if(button_agree  || button_self_agree){
            btns.splice(0,0,yesJbpmFun);
        }
    }else{
        //alert("按钮查看！")
        var lastStepFormReg = /comment_form$/g;
        var isLastStep = lastStepFormReg.test(formKey);
        if(isLastStep){

        }
    }

    var btnsOptions = {};
    for(var i = 0 ;i < btns.length; i++){
        btnsOptions["btn"+(i+1)] = btns[i];
    }

    return btnsOptions;
}

</script>
</html>