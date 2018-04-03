package com.idc.model.ip;

/**
 * IP 状态改变
 * Created by mylove on 2017/8/14.
 */
public enum ALLCTYPE {
    FREE(0, "空闲"), USED(1, "已用"), ALLCUSED(2, "分配占用"), WAITRECY(3, "等待回收");
    private long type;
    private String name;

    ALLCTYPE(long type, String name) {
        this.type = type;
        this.name = name;
    }

    public long getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String toString() {
        return String.valueOf(this.getType());
    }

    public static void main(String[] args) {
        ALLCTYPE a = ALLCTYPE.ALLCUSED;
        System.out.println(a.equals(USED));
        System.out.println(ALLCTYPE.USED);
    }
}
