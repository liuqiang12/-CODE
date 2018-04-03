package com.idc.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 树形结构模型
 * @author Administrator
 *
 */
public class TreeModel {
    private String text;//显示的名称
    private Integer id;//主键ID

    private String state = "open";//节点状态，'open' 或 'closed'，默认：'open'。如果为'closed'的时候，将不自动展开该节点。
    private Boolean checked = false;//表示该节点是否被选中。

    private String homeCityId;
    private Map<String, Object> attributes;// 被添加到节点的自定义属性。

    private Boolean isparent;//是父节点吗:true  or false
    private List<TreeModel> children;
    public List<TreeModel> addChildren(TreeModel treeModel){
        if (children == null) {
            children =   new ArrayList<TreeModel>();
        }
        if(children.isEmpty() || !children.contains(treeModel)){
            children.add(treeModel);
        }
        return children;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public Boolean getIsparent() {
        return isparent;
    }
    public void setIsparent(Boolean isparent) {
        this.isparent = isparent;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Boolean getChecked() {
        return checked;
    }
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }
    public List<TreeModel> getChildren() {
        return children;
    }
    public void setChildren(List<TreeModel> children) {
        this.children = children;
    }
    public Map<String, Object> getAttributes() {
        return attributes;
    }
    public void setAttributes(Map<String, Object> attributes) {
        this.attributes = attributes;
    }
    public String getHomeCityId() {
        return homeCityId;
    }
    public void setHomeCityId(String homeCityId) {
        this.homeCityId = homeCityId;
    }

}
