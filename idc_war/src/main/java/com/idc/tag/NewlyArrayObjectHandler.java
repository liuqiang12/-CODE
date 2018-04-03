package com.idc.tag;

import com.alibaba.druid.support.spring.DruidNativeJdbcExtractor;
import com.idc.model.IdcReNewlyModel;
import oracle.jdbc.internal.OracleConnection;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.sql.STRUCT;
import oracle.sql.StructDescriptor;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.TypeException;
import org.apache.ibatis.type.TypeHandler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 2017/9/29.
 */
//这个jdbcType对应mapper文件中对应的jdbcType
//如果此处不做配置也可以在mybatis-config.xml中配置
@MappedJdbcTypes(JdbcType.ARRAY)
public class NewlyArrayObjectHandler implements TypeHandler {
    private static final Log log = LogFactory.getLog(NewlyArrayObjectHandler.class);
    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        //获取OracleConnection
        DruidNativeJdbcExtractor druidNativeJdbcExtractor = new DruidNativeJdbcExtractor();
        OracleConnection oracleConn=(OracleConnection)druidNativeJdbcExtractor.getNativeConnection(ps.getConnection());
        //这个parameter就是我们自己在mapper中传入的参数
        List<IdcReNewlyModel> idcReNewlyList = null;
        StructDescriptor sd = new StructDescriptor("IDC_RE_NEWLY_META",oracleConn);
        ArrayDescriptor des_newly_TABLE = ArrayDescriptor.createDescriptor("IDC_RE_NEWLY_ARRAY",oracleConn);
        STRUCT[] result = null;
        if (parameter == null) {
            if (jdbcType == null) {
                throw new TypeException("JDBC requires that the JdbcType must be specified for all nullable parameters.");
            }
            try {
                idcReNewlyList = new ArrayList<IdcReNewlyModel>();
                result = new STRUCT[idcReNewlyList.size()];
                ARRAY  array = new ARRAY(des_newly_TABLE, oracleConn, result);
                ps.setArray(i, array);
            } catch (SQLException e) {
                throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . " +
                        "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. " +
                        "Cause: " + e, e);
            }
        }else{
            idcReNewlyList = (ArrayList<IdcReNewlyModel>) parameter ;
            result = new STRUCT[idcReNewlyList.size()];
            log.debug("[[[[[组装相应的数据:定义的格式数据]]]]]----------------");
            for(int index = 0; index < idcReNewlyList.size(); index++){
                IdcReNewlyModel idcReNewlyModel = idcReNewlyList.get(index);
                Object[] objects = new Object[6];
            /*判断如果是null 则不需要往里面保存*/
                if( idcReNewlyModel.getId() != null){objects[0] = idcReNewlyModel.getId();}else{objects[0] = null;}
                objects[1] = 0;
                if( idcReNewlyModel.getName() != null ){ objects[2] = idcReNewlyModel.getName(); }else{objects[2] = null;}
                if( idcReNewlyModel.getCategory() != null ){  objects[3] = idcReNewlyModel.getCategory(); }else{objects[3] = null;}
                if( idcReNewlyModel.getDesc() != null ){  objects[4] = idcReNewlyModel.getDesc();  }else{objects[4] = null;}
                if( idcReNewlyModel.getCreateTime() != null ){  objects[5] = idcReNewlyModel.getCreateTime();  }else{objects[5] = null;}
                result[index] = new STRUCT(sd,oracleConn,objects);
            }

            //OK
            ARRAY oracle_array = new ARRAY(des_newly_TABLE,oracleConn,result);
            ps.setArray(i, oracle_array);
        }
    }

    @Override
    public Object getResult(ResultSet resultSet, String s) throws SQLException {
        return null;
    }

    @Override
    public Object getResult(ResultSet resultSet, int i) throws SQLException {
        return null;
    }

    @Override
    public Object getResult(CallableStatement callableStatement, int i) throws SQLException {
        return null;
    }
}
