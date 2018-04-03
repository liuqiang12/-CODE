package utils;

import java.util.ResourceBundle;

/**
 * Created by DELL on 2017/9/1.
 */
public class PropertyConfig {

    private static String propertyFileName = "config.common";
    private static PropertyConfig propertyConfig;

    public String getPropertyValue(String key) {
        ResourceBundle resourcesTable = null;
        try {
            resourcesTable = ResourceBundle.getBundle(propertyFileName);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("配置文件缺失，请检查class目录下的" + propertyFileName + "配置文件" + e.getMessage());
        }
        try {
            String value = resourcesTable.getString(key);
            return value;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("配置文件配置项缺失，请检查class目录下的" + propertyFileName + "配置文件中的[" + key + "]  : " + e.getMessage());

        }
        return null;
    }

    public static PropertyConfig getInstance(){
        if(propertyConfig == null){
            propertyConfig = new PropertyConfig();
        }
        return propertyConfig;
    }

}
