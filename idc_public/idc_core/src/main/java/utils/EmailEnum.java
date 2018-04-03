package utils;

public enum EmailEnum {

    /*此枚举还没有完成，待用，后面视情况使用*/

    email_Adress_From(""),
    ee("");
    private final String value;

    EmailEnum(final String value) {
        this.value = value;
    }
    public String value() {
        return this.value;
    }
}
