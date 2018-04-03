package com.idc.model;

/**
 * Created by mylove on 2016/12/8.
 */

/***
 * 资源类型
 */

public enum PermType {
    // 利用构造函数传参
    MENU(1000), BUTTON(1003);

    // 定义私有变量
    private int permissionType;

    // 构造函数，枚举类型只能为私有

    public int getPermissionType() {
        return permissionType;
    }

    public void setPermissionType(int permissionType) {
        this.permissionType = permissionType;
    }

    private PermType(int permissionType) {
        this.permissionType = permissionType;
    }

    public static PermType codeOf(int code) {
        for (PermType e : PermType.values()) {
            if (e.permissionType == code) {
                return e;
            }
        }
        return PermType.MENU;
    }

    @Override
    public String toString() {
        return String.valueOf(this.permissionType);
    }
}