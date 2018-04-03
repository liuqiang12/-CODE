<fieldset class="fieldsetCls fieldsetHeadCls">
    <legend>&diams;服务信息</legend>
    <table class="kv-table">
        <tr>
            <td class="kv-label" style="width: 200px;">名称</td>
            <td class="kv-content">
                <input class="easyui-textbox" name="idcNetServiceinfo.icpsrvname" value="${idcNetServiceinfo.icpsrvname}"  id="icpsrvname" data-options="required:true,validType:'length[0,64]',width:200"/>
            </td>
            <td class="kv-label">所属用户</td>
            <td class="kv-content">
                <input class="easyui-textbox" value="${idcReCustomer.name}" data-options="required:true,validType:'length[0,128]',width:200"/>
            </td>
        </tr>
        <tr>
            <td class="kv-label">服务内容</td>
            <td class="kv-content">
                <input class="easyui-combobox" data-options="
                    valueField: 'value',
                    textField: 'label',
                    required:true,
                    editable:false,
                    width:200,
                    url:'<%=request.getContextPath()%>/assetBaseinfo/combobox/1000'" value="${idcNetServiceinfo.icpsrvcontentcode}"  name="idcNetServiceinfo.icpsrvcontentcode"/>

            </td>
            <td class="kv-label">备案类型</td>
            <td class="kv-content">
                <input class="easyui-combobox" data-options="
                        valueField: 'value',
                        textField: 'label',
                        required:true,
                        editable:false,
                        width:200,
                        data: [{
                            label: '无',
                            value: '0'
                        },{
                            label: '经营性网站',
                            value: '1'
                        },{
                            label: '非经营性网站',
                            value: '2'
                        },{
                            label: 'SP',
                            value: '3'
                        },{
                            label: 'BBS',
                            value: '4'
                        },{
                            label: '其它',
                            value: '999'
                        }]" value="${idcNetServiceinfo.icprecordtype}"  name="idcNetServiceinfo.icprecordtype" />
            </td>
        </tr>
        <tr>
            <td class="kv-label">备案号[许可证号]</td>
            <td class="kv-content">
                <input class="easyui-textbox"  name="idcNetServiceinfo.icprecordno" value="${idcNetServiceinfo.icprecordno}" data-options="required:true,validType:'length[0,64]',width:200"/>
            </td>
            <td class="kv-label">接入方式</td>
            <td class="kv-content">
                <input class="easyui-combobox" readonly="readonly" data-options="
                                            valueField: 'value',
                                            textField: 'label',
                                            required:true,
                                            width:200,
                                            editable:false,
                                            onChange:icpaccesstypeOnChange,
                                            data: [{
                                                label: '专线',
                                                value: '0'
                                            },{
                                                label: '虚拟主机',
                                                value: '1'
                                            },{
                                                label: '主机托管',
                                                value: '2'
                                            },{
                                                label: '整机租用',
                                                value: '3'
                                            },{
                                                label: '其它',
                                                value: '999'
                                            }]" value="${idcNetServiceinfo.icpaccesstype}"  name="ins_icpaccesstype" id="icpaccesstype"/>
            </td>
        </tr>
        <tr>
            <td class="kv-label">域名信息</td>
            <td class="kv-content">
                <input class="easyui-textbox" readonly="readonly" name="ins_icpdomain" value="${idcNetServiceinfo.icpdomain}"  id="ins_icpdomain" data-options="required:true,validType:'length[0,128]',width:200"/>
            </td>
            <td class="kv-label">业务类型</td>
            <td class="kv-content">
                <input class="easyui-combobox" readonly="readonly" data-options="
                                            valueField: 'value',
                                            textField: 'label',
                                            required:true,
                                            width:200,
                                            editable:false,
                                            data: [{
                                                label: 'IDC业务',
                                                value: '1'
                                            },{
                                                label: 'ISP业务',
                                                value: '2'
                                            }]" value="${idcNetServiceinfo.icpbusinesstype}"  name="ins_icpbusinesstype" id="ins_icpbusinesstype"/>
            </td>
        </tr>
    </table>
</fieldset>