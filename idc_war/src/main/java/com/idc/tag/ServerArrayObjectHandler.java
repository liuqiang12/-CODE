package com.idc.tag;

import com.alibaba.druid.support.spring.DruidNativeJdbcExtractor;
import com.idc.model.IdcReServerModel;
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
public class ServerArrayObjectHandler implements TypeHandler {
    private static final Log log = LogFactory.getLog(ServerArrayObjectHandler.class);
    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        //获取OracleConnection
        DruidNativeJdbcExtractor druidNativeJdbcExtractor = new DruidNativeJdbcExtractor();
        OracleConnection oracleConn=(OracleConnection)druidNativeJdbcExtractor.getNativeConnection(ps.getConnection());
        //这个parameter就是我们自己在mapper中传入的参数
        List<IdcReServerModel> idcReServerList = null;
        StructDescriptor sd = new StructDescriptor("IDC_RE_SERVER_META",oracleConn);
        ArrayDescriptor des_server_TABLE = ArrayDescriptor.createDescriptor("IDC_RE_SERVER_ARRAY",oracleConn);
        STRUCT[] result = null;
        if (parameter == null) {
            if (jdbcType == null) {
                throw new TypeException("JDBC requires that the JdbcType must be specified for all nullable parameters.");
            }
            try {
                idcReServerList = new ArrayList<IdcReServerModel>();
                result = new STRUCT[idcReServerList.size()];
                ARRAY  array = new ARRAY(des_server_TABLE, oracleConn, result);
                ps.setArray(i, array);
            } catch (SQLException e) {
                throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . " +
                        "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. " +
                        "Cause: " + e, e);
            }
        }else{
            idcReServerList = (ArrayList<IdcReServerModel>) parameter;
            result = new STRUCT[idcReServerList.size()];
            log.debug("[[[[[组装相应的数据:定义的格式数据]]]]]----------------");
            for(int index = 0; index < idcReServerList.size(); index++){
                IdcReServerModel idcReServerModel = idcReServerList.get(index);
                Object[] objects = new Object[16];
            /*判断如果是null 则不需要往里面保存*/
                if( idcReServerModel.getId() != null){objects[0] = idcReServerModel.getId();}else{objects[0] = null;}
                objects[1] = 0;
                if( idcReServerModel.getTypeMode() != null ){ objects[2] = idcReServerModel.getTypeMode(); }else{objects[2] = null;}
                if( idcReServerModel.getBrand() != null ){  objects[3] = idcReServerModel.getBrand(); }else{objects[3] = null;}
                if( idcReServerModel.getCode() != null ){  objects[4] = idcReServerModel.getCode(); }else{objects[4] = null;}
                if( idcReServerModel.getSpecNumber() != null ){  objects[5] = idcReServerModel.getSpecNumber(); }else{objects[5] = null;}
                if( idcReServerModel.getRatedPower() != null ){  objects[6] = idcReServerModel.getRatedPower();  }else{objects[6] = null;}
                if( idcReServerModel.getNum() != null ){  objects[7] = idcReServerModel.getNum();  }else{objects[7] = null;}
                if( idcReServerModel.getDesc() != null ){  objects[8] = idcReServerModel.getDesc();  }else{objects[8] = null;}
                if( idcReServerModel.getCreateTime() != null ){  objects[9] = idcReServerModel.getCreateTime();  }else{objects[9] = null;}
                if( idcReServerModel.getSource() != null ){  objects[10] = idcReServerModel.getSource();  }else{objects[10] = null;}
                if( idcReServerModel.getCPU() != null ){  objects[11] = idcReServerModel.getCPU();  }else{objects[11] = null;}
                if( idcReServerModel.getMEM() != null ){  objects[12] = idcReServerModel.getMEM();  }else{objects[12] = null;}
                if( idcReServerModel.getMemory() != null ){  objects[13] = idcReServerModel.getMemory();  }else{objects[13] = null;}
                if( idcReServerModel.getOS() != null ){  objects[14] = idcReServerModel.getOS();  }else{objects[14] = null;}
                if( idcReServerModel.getRackUnit() != null ){  objects[15] = idcReServerModel.getRackUnit();  }else{objects[15] = null;}
                result[index] = new STRUCT(sd,oracleConn,objects);
            }
            //OK
            ARRAY oracle_array = new ARRAY(des_server_TABLE,oracleConn,result);
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
