/*
 * Activiti Modeler component part of the Activiti project
 * Copyright 2005-2014 Alfresco Software, Ltd. All rights reserved.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */

/*
 * Assignment
 */
var KisBpmAssignmentCtrl = [ '$scope', '$modal', function($scope, $modal) {

    // Config for the modal window
    var opts = {
        template:  '../activiti-moduler/editor-app/configuration/properties/assignment-popup.html?version=' + Date.now(),
        scope: $scope
    };

    // Open the dialog
    $modal(opts);
    //直接弹出框:[此时是否需要处理]

}];

/*控制器*/
var KisBpmAssignmentPopupCtrl = [ '$scope', function($scope) {
    	
    // Put json representing assignment on scope
    if ($scope.property.value !== undefined && $scope.property.value !== null
        && $scope.property.value.assignment !== undefined
        && $scope.property.value.assignment !== null) 
    {
        $scope.assignment = $scope.property.value.assignment;
    } else {
        $scope.assignment = {};
    }

    if ($scope.assignment.candidateUsers == undefined || $scope.assignment.candidateUsers.length == 0)
    {
    	$scope.assignment.candidateUsers = [{value: ''}];
    }
    
    // Click handler for + button after enum value
    var userValueIndex = 1;
    $scope.addCandidateUserValue = function(index) {
        /*新增用户 首先进行弹出框*/
        top.layer.open({
            type: 2,
            title: "用户信息",
            shadeClose: false,
            shade: 0.8,
            btn: ['确定','关闭'],
            maxmin : true,
            area: ['850px', '580px'],
            /*弹出框设置*/
            content: contextPath+"/userinfo/sysUserinfoGridPageToAct.do",//iframe的url
            /*弹出框*/
            cancel: function(idx, layero){
                //右上角关闭回调
                top.layer.close(idx);
            },yes: function(idx, layero){
                var childIframeid = layero.find('iframe')[0]['name'];
                var chidlwin = top.document.getElementById(childIframeid).contentWindow;
                //被选中的节点信息
                var recordDatas = chidlwin.getWinCheckedRecord("gridId") || [];

                var id = recordDatas[0]?recordDatas[0]['id']:null;
                var username = recordDatas[0]?recordDatas[0]['username']:null;
                /*设置相应的值*/
                $scope.$apply(function(){
                    $scope.assignment.candidateUsers[index].value = 'username_' + username + '@id_' + id;
                    //splice() 方法向/从数组中添加/删除项目，然后返回被删除的项目  0 不会删除
                    //$scope.assignment.candidateGroups.splice(index + 1, 0, {value: 'value ' + groupValueIndex++});
                    $scope.assignment.candidateUsers.splice(index + 1, 0, {value: null});
                });
                top.layer.close(idx)
            },no: function(idx, layero){
                //按钮【按钮一】的回调
                top.layer.close(idx)
            }
        });

    };

    // Click handler for - button after enum value
    $scope.removeCandidateUserValue = function(index) {
        $scope.assignment.candidateUsers.splice(index, 1);
    };
    
    if ($scope.assignment.candidateGroups == undefined || $scope.assignment.candidateGroups.length == 0)
    {
    	$scope.assignment.candidateGroups = [{value: ''}];
    }
    
    var groupValueIndex = 1;
    $scope.addCandidateGroupValue = function(index) {
    	/*新增用户组*/
    	/*首先进行弹出框*/
        top.layer.open({
            type: 2,
            title: "角色信息",
            shadeClose: false,
            shade: 0.8,
            btn: ['确定','关闭'],
            maxmin : true,
            area: ['850px', '580px'],
            /*弹出框设置*/
            content: contextPath+"/sysrole/sysRoleGridPageToAct.do",//iframe的url
			/*弹出框*/
            cancel: function(idx, layero){
                //右上角关闭回调
                top.layer.close(idx);
            },yes: function(idx, layero){
                var childIframeid = layero.find('iframe')[0]['name'];
                var chidlwin = top.document.getElementById(childIframeid).contentWindow;
                //被选中的节点信息
                var recordDatas = chidlwin.getWinCheckedRecord("gridId") || [];

                var id = recordDatas[0]?recordDatas[0]['id']:null;
                var role_key = recordDatas[0]?recordDatas[0]['role_key']:null;
                /*设置相应的值*/
                $scope.$apply(function(){
                    $scope.assignment.candidateGroups[index].value = 'role_key_' + role_key + '@id_' + id;
                    //splice() 方法向/从数组中添加/删除项目，然后返回被删除的项目  0 不会删除
                    //$scope.assignment.candidateGroups.splice(index + 1, 0, {value: 'value ' + groupValueIndex++});
                    $scope.assignment.candidateGroups.splice(index + 1, 0, {value: null});
                });
                top.layer.close(idx)
            },no: function(idx, layero){
                //按钮【按钮一】的回调
                top.layer.close(idx)
            }
        });
    };

    // Click handler for - button after enum value
    $scope.removeCandidateGroupValue = function(index) {
        $scope.assignment.candidateGroups.splice(index, 1);
    };

    $scope.save = function() {

        $scope.property.value = {};
        handleAssignmentInput($scope);
        $scope.property.value.assignment = $scope.assignment;
        
        $scope.updatePropertyInModel($scope.property);
        $scope.close();
    };

    // Close button handler
    $scope.close = function() {
    	handleAssignmentInput($scope);
    	$scope.property.mode = 'read';
    	$scope.$hide();
    };
    
    var handleAssignmentInput = function($scope) {
    	if ($scope.assignment.candidateUsers)
    	{
	    	var emptyUsers = true;
	    	var toRemoveIndexes = [];
	        for (var i = 0; i < $scope.assignment.candidateUsers.length; i++)
	        {
	        	if ($scope.assignment.candidateUsers[i].value != '')
	        	{
	        		emptyUsers = false;
	        	}
	        	else
	        	{
	        		toRemoveIndexes[toRemoveIndexes.length] = i;
	        	}
	        }
	        
	        for (var i = 0; i < toRemoveIndexes.length; i++)
	        {
	        	$scope.assignment.candidateUsers.splice(toRemoveIndexes[i], 1);
	        }
	        
	        if (emptyUsers)
	        {
	        	$scope.assignment.candidateUsers = undefined;
	        }
    	}
        
    	if ($scope.assignment.candidateGroups)
    	{
	        var emptyGroups = true;
	        var toRemoveIndexes = [];
	        for (var i = 0; i < $scope.assignment.candidateGroups.length; i++)
	        {
	        	if ($scope.assignment.candidateGroups[i].value != '')
	        	{
	        		emptyGroups = false;
	        	}
	        	else
	        	{
	        		toRemoveIndexes[toRemoveIndexes.length] = i;
	        	}
	        }
	        
	        for (var i = 0; i < toRemoveIndexes.length; i++)
	        {
	        	$scope.assignment.candidateGroups.splice(toRemoveIndexes[i], 1);
	        }
	        
	        if (emptyGroups)
	        {
	        	$scope.assignment.candidateGroups = undefined;
	        }
    	}
    };
}];