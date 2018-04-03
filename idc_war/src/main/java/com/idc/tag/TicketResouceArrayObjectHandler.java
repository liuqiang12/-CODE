package com.idc.tag;

import com.alibaba.druid.support.spring.DruidNativeJdbcExtractor;
import com.idc.model.IdcRunTicketResource;
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
public class TicketResouceArrayObjectHandler implements TypeHandler {
    private static final Log log = LogFactory.getLog(TicketResouceArrayObjectHandler.class);
    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        //获取OracleConnection
        DruidNativeJdbcExtractor druidNativeJdbcExtractor = new DruidNativeJdbcExtractor();
        OracleConnection oracleConn=(OracleConnection)druidNativeJdbcExtractor.getNativeConnection(ps.getConnection());
        //这个parameter就是我们自己在mapper中传入的参数
        List<IdcRunTicketResource> idcRunTicketResourceList = null;
        StructDescriptor sd = new StructDescriptor("IDC_RUN_TICKET_RESOURCE_META",oracleConn);
        ArrayDescriptor des_ticketResource_TABLE = ArrayDescriptor.createDescriptor("IDC_RUN_TICKET_RESOURCE_ARRAY",oracleConn);
        STRUCT[] result = null;
        if (parameter == null) {
            if (jdbcType == null) {
                throw new TypeException("JDBC requires that the JdbcType must be specified for all nullable parameters.");
            }
            try {
                idcRunTicketResourceList = new ArrayList<IdcRunTicketResource>();
                result = new STRUCT[idcRunTicketResourceList.size()];
                ARRAY  array = new ARRAY(des_ticketResource_TABLE, oracleConn, result);
                ps.setArray(i, array);
            } catch (SQLException e) {
                throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . " +
                        "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. " +
                        "Cause: " + e, e);
            }
        }else{
            log.debug("[[[[[组装相应的数据:定义的格式数据IdcRunTicketResource]]]]]----------------");
            idcRunTicketResourceList = (ArrayList<IdcRunTicketResource>)parameter;
            result = new STRUCT[idcRunTicketResourceList.size()];
            for(int index = 0; index < idcRunTicketResourceList.size(); index++){
                IdcRunTicketResource idcRunTicketResource = idcRunTicketResourceList.get(index);
                Object[] objects = new Object[19];
                /*判断如果是null 则不需要往里面保存*/
                if( idcRunTicketResource.getId() != null){objects[0] = idcRunTicketResource.getId();}else{objects[0] = null;}
                if( idcRunTicketResource.getTicketInstId() != null){objects[1] = idcRunTicketResource.getTicketInstId();}else{objects[1] = null;}
                if( idcRunTicketResource.getResourceid() != null ){ objects[2] = idcRunTicketResource.getResourceid();}else{objects[2] = null;}
                if( idcRunTicketResource.getStatus() != null ){ objects[3] = idcRunTicketResource.getStatus(); }else{objects[3] = 1;}
                if( idcRunTicketResource.getManual() != null ){  objects[4] = idcRunTicketResource.getManual(); }else{objects[4] = 1;}
                objects[5] = 0;//版本号
                if( idcRunTicketResource.getCreateTime() != null ){ objects[6] = idcRunTicketResource.getCreateTime();  }else{objects[6] = null;}
                if( idcRunTicketResource.getCategory() != null ){  objects[7] = idcRunTicketResource.getCategory();  }else{objects[7] = null;}
                if( idcRunTicketResource.getTicketCategory() != null ){ objects[8] = idcRunTicketResource.getTicketCategory(); }else{objects[8] = null;}

                if( idcRunTicketResource.getIpType() != null ){ objects[9] = idcRunTicketResource.getIpType(); }else{objects[9] = null;}

                if( idcRunTicketResource.getStatusPre() != null ){ objects[10] = idcRunTicketResource.getStatusPre(); }else{objects[10] = 1;}
                if( idcRunTicketResource.getRackId() != null ){ objects[11] = idcRunTicketResource.getRackId(); }else{objects[11] = null;}

                if( idcRunTicketResource.getResourcename() != null ){  objects[12] = idcRunTicketResource.getResourcename(); }else{objects[12] = null;}
                if( idcRunTicketResource.getProdInstId() != null ){ objects[13] = idcRunTicketResource.getProdInstId(); }else{objects[13] = null;}
                if( idcRunTicketResource.getTicketRackGrid() != null ){  objects[14] = idcRunTicketResource.getTicketRackGrid();  }else{objects[14] = null;}
                if( idcRunTicketResource.getEndip() != null ){  objects[15] = idcRunTicketResource.getEndip();  }else{objects[15] = null;}
                if( idcRunTicketResource.getBegip() != null ){  objects[16] = idcRunTicketResource.getBegip();  }else{objects[16] = null;}
                if( idcRunTicketResource.getRackOrRackUnit() != null ){  objects[17] = idcRunTicketResource.getRackOrRackUnit();  }else{objects[17] = null;}
                if( idcRunTicketResource.getUsedRackUnIt() != null ){  objects[18] = idcRunTicketResource.getUsedRackUnIt();  }else{objects[18] = null;}

                result[index] = new STRUCT(sd,oracleConn,objects);
            }
            //OK
            ARRAY oracle_array = new ARRAY(des_ticketResource_TABLE,oracleConn,result);

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
