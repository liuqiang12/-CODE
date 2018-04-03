<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String contextPath = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <jsp:include page="/globalstatic/easyui/head.jsp"></jsp:include>
    <script src="//res.layui.com/layui/build/layui.js" charset="utf-8"></script>
    <link rel="stylesheet" href="//res.layui.com/layui/build/css/layui.css" media="all">
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-default/index.css">
    <script src="https://unpkg.com/vue@2.2.6"></script>
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
    <style>
        .datagrid-row {
            height: 27px;
        }
    </style>
</head>
<body>
<select>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>
    <option>1</option>


</select>


<select name="interest" lay-filter="aihao">
    <option value=""></option>
    <option value="0">写作</option>
    <option value="1" selected="">阅读</option>
    <option value="2">游戏</option>
    <option value="3">音乐</option>
    <option value="4">旅行</option>
</select>

<div style="padding: 5px;" id="requestParamSettins">
    客户名称:<input class="easyui-textbox" id="btsname" style="width:150px;text-align: left;" data-options="">
    <input id="resourceType" value="" type="hidden">
    客户类型:<input class="easyui-combobox" data-options="
					valueField: 'value',
					textField: 'label',
					editable:false,
					data: [{
						label: 'VIP',
						value: '1'
					},{
						label: '普通',
						value: '2'
					},{
						label: '告警',
						value: '3'
					}]
					" style="width:150px;text-align: left;">
    <a href="javascript:void(0);" onclick="loadGrid('gridId')" class="easyui-linkbutton"
       data-options="iconCls:'icon-search'">查询</a>
    <!-- 其实就是发布流程 -->
    <a href="javascript:void(0)" class="easyui-linkbutton" data-options="iconCls:'icon-add',plain:true"
       onclick="publish()">新建预受理清单</a>
</div>
<div id="dd">
    <el-select v-model="value" placeholder="请选择">
        <el-option
                v-for="item in options"
                :label="item.label"
                :value="item.value">
        </el-option>
    </el-select>
</div>
<!-- 写到一张界面中的table -->
<table class="easyui-datagrid" id="gridId"
       data-options="singleSelect:true,rownumbers:true,pagination:true,fitColumns:true,pageSize:15,pageList:[15,20,25,35,40],fit:true,loadFilter:function(data){return {total : data.totalRecord,rows : data.items}},onBeforeLoad : function(param){param['pageNo'] = param['page'];param['pageSize'] = param['rows'];return true;}"></table>
<div id="mm1" class="easyui-menu" style="width:75px;">
    <div iconCls="icon-search" id="menuSearch">查看</div>
</div>
<div id="mm2" class="easyui-menu" style="width:75px;">
    <!-- 			    <div  iconCls="icon-edit"  id="menuEdit">编辑</div>   -->
    <!-- 			    <div  iconCls="icon-save"  id="menuSave">提交</div>   -->
    <div iconCls="icon-ok" id="menuOk">开通业务</div>
    <div iconCls="icon-search" id="menuSearch">查看</div>
</div>
<div id="mm3" class="easyui-menu" style="width:75px;">
    <div iconCls="icon-edit" id="menuEdit">编辑</div>
    <div iconCls="icon-save" id="menuSave">提交</div>
    <!-- 			    <div iconCls="icon-ok" id="menuOk">开通业务</div> -->
    <div iconCls="icon-cancel" id="menuCancel">删除</div>
    <div iconCls="icon-search" id="menuSearch">查看</div>
    <!-- 			    <div iconCls="icon-settting" id="menuSettting">归档</div> -->
</div>
<div id="mm4" class="easyui-menu" style="width:75px;">
    <div iconCls="icon-edit" id="menuEdit">编辑</div>
    <div iconCls="icon-save" id="menuSave">提交</div>
    <!-- 			    <div iconCls="icon-ok" id="menuOk">开通业务</div> -->
    <div iconCls="icon-cancel" id="menuCancel">删除</div>
    <div iconCls="icon-search" id="menuSearch">查看</div>
    <!-- 			    <div iconCls="icon-settting" id="menuSettting">归档</div> -->
</div>

<script>
    var Main = {
        data() {
            return {
                options: [{
                    value: '选项1',
                    label: '黄金糕'
                }, {
                    value: '选项2',
                    label: '双皮奶'
                }, {
                    value: '选项3',
                    label: '蚵仔煎'
                }, {
                    value: '选项4',
                    label: '龙须面'
                }, {
                    value: '选项5',
                    label: '北京烤鸭'
                }],
                value: ''
            }
        }
    }
    var Ctor = Vue.extend(Main)
    new Ctor().$mount('#dd')
</script>
</body>
</html>