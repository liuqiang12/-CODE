package utils.strategy.code.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by DELL on 2017/9/18.
 */
public class AssetAttachMain {
    private static final Log log = LogFactory.getLog(AssetAttachMain.class);

    public static void main(String[] args)throws Exception{
        rechangeAttachInfoData();
    }
    public static void rechangeAttachInfoData() throws Exception{
        /**
         * 1:获取jdbc
         */
        //获取执行 sql
        JdbcHelper jdbcHelper = JdbcHelper.getInstance();
        try {
            //预定义的sql
            String sqlcontent = "select * from ASSET_ATTACHMENTINFO t";
            PreparedStatement pst = jdbcHelper.load(sqlcontent, "config/jdbc.properties").getPst();
            ResultSet rs=null;
            rs=pst.executeQuery();
            while(rs.next()){

                String ATTACH_NAME = rs.getString("ATTACH_NAME");
                String LOGIC_TABLENAME = rs.getString("LOGIC_TABLENAME");
                Boolean LOGIC_COLUMN = rs.getBoolean("LOGIC_COLUMN");
                String ATTACH_SUFFIX = rs.getString("ATTACH_SUFFIX");
                System.out.println(ATTACH_SUFFIX);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            jdbcHelper.close();
        }
    }
}
