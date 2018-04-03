package com.idc.tag;

import com.alibaba.druid.support.spring.DruidNativeJdbcExtractor;
import com.idc.model.CreateTicketModel;
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
public class TicketPropertyArrayObjectHandler implements TypeHandler {
    private static final Log log = LogFactory.getLog(TicketPropertyArrayObjectHandler.class);
    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        //获取OracleConnection
        DruidNativeJdbcExtractor druidNativeJdbcExtractor = new DruidNativeJdbcExtractor();
        OracleConnection oracleConn=(OracleConnection)druidNativeJdbcExtractor.getNativeConnection(ps.getConnection());
        //这个parameter就是我们自己在mapper中传入的参数
        List<CreateTicketModel> createTicketModelList = null;
        StructDescriptor sd = new StructDescriptor("JBPM_TICKET_PROPERTY_META",oracleConn);
        ArrayDescriptor des_ticketProperty_TABLE = ArrayDescriptor.createDescriptor("JBPM_TICKET_PROPERTY_ARRAY",oracleConn);
        STRUCT[] result = null;
        if (parameter == null) {
            if (jdbcType == null) {
                throw new TypeException("JDBC requires that the JdbcType must be specified for all nullable parameters.");
            }
            try {
                createTicketModelList = new ArrayList<CreateTicketModel>();
                result = new STRUCT[createTicketModelList.size()];
                ARRAY  array = new ARRAY(des_ticketProperty_TABLE, oracleConn, result);
                ps.setArray(i, array);
            } catch (SQLException e) {
                throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . " +
                        "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. " +
                        "Cause: " + e, e);
            }
        }else{
            log.debug("[[[[[组装相应的数据:定义的格式数据 CreateTicketModel]]]]]----------------");
            createTicketModelList = (ArrayList<CreateTicketModel>)parameter;
            result = new STRUCT[createTicketModelList.size()];
            for(int index = 0; index < createTicketModelList.size(); index++){
                CreateTicketModel createTicketModel = createTicketModelList.get(index);
                Object[] objects = new Object[18];
                /*判断如果是null 则不需要往里面保存*/
                if( createTicketModel.getProdInstId() != null){objects[0] = createTicketModel.getProdInstId();}else{objects[0] = null;}
                if( createTicketModel.getProdCategory() != null){objects[1] = createTicketModel.getProdCategory();}else{objects[1] = 1;}
                if( createTicketModel.getBusname() != null ){ objects[2] = createTicketModel.getBusname();}else{objects[2] = null;}
                if( createTicketModel.getParentTicketId() != null ){ objects[3] = createTicketModel.getParentTicketId(); }else{objects[3] = null;}
                if( createTicketModel.getTicketCategoryFrom() != null ){  objects[4] = createTicketModel.getTicketCategoryFrom(); }else{objects[4] = null;}
                if( createTicketModel.getTicketCategoryTo() != null ){  objects[5] = createTicketModel.getTicketCategoryTo(); }else{objects[5] = null;}
                if( createTicketModel.getCustomerId() != null ){ objects[6] = createTicketModel.getCustomerId();  }else{objects[6] = null;}
                if( createTicketModel.getCustomerName() != null ){  objects[7] = createTicketModel.getCustomerName();  }else{objects[7] = null;}
                objects[7]=null;
                if( createTicketModel.getAttributeName() != null ){ objects[8] = createTicketModel.getAttributeName(); }else{objects[8] = null;}
                if( createTicketModel.getContactMobile() != null ){ objects[9] = createTicketModel.getContactMobile(); }else{objects[9] = null;}
                if( createTicketModel.getFormKeyFrom() != null ){ objects[10] = createTicketModel.getFormKeyFrom(); }else{objects[10] = null;}
                if( createTicketModel.getFormKeyTo() != null ){ objects[11] = createTicketModel.getFormKeyTo(); }else{objects[11] = null;}
                if( createTicketModel.getProcessdefinitonkeyFrom() != null ){ objects[12] = createTicketModel.getProcessdefinitonkeyFrom(); }else{objects[12] = null;}
                if( createTicketModel.getProcessdefinitonkeyTo() != null ){ objects[13] = createTicketModel.getProcessdefinitonkeyTo(); }else{objects[13] = null;}
                if( createTicketModel.getProcticketStatus() != null ){ objects[14] = createTicketModel.getProcticketStatus(); }else{objects[14] = null;}
                if( createTicketModel.getApplyUserId() != null ){ objects[15] = createTicketModel.getApplyUserId(); }else{objects[15] = null;}
                if( createTicketModel.getApplyUserName() != null ){ objects[16] = createTicketModel.getApplyUserName(); }else{objects[16] = null;}
                if( createTicketModel.getTicketStatus() != null ){ objects[17] = createTicketModel.getTicketStatus(); }else{objects[17] = null;}
                result[index] = new STRUCT(sd,oracleConn,objects);
            }
            //OK
            ARRAY oracle_array = new ARRAY(des_ticketProperty_TABLE,oracleConn,result);

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
