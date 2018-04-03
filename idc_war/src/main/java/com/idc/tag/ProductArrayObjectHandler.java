package com.idc.tag;

import com.alibaba.druid.support.spring.DruidNativeJdbcExtractor;
import com.idc.model.IdcReProduct;
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
public class ProductArrayObjectHandler implements TypeHandler {
    private static final Log log = LogFactory.getLog(ProductArrayObjectHandler.class);
    @Override
    public void setParameter(PreparedStatement ps, int i, Object parameter, JdbcType jdbcType) throws SQLException {
        //获取OracleConnection
        DruidNativeJdbcExtractor druidNativeJdbcExtractor = new DruidNativeJdbcExtractor();
        OracleConnection oracleConn=(OracleConnection)druidNativeJdbcExtractor.getNativeConnection(ps.getConnection());
        //这个parameter就是我们自己在mapper中传入的参数
        List<IdcReProduct> idcReProductList = null;
        StructDescriptor sd = new StructDescriptor("IDC_RE_PRODUCT_META",oracleConn);
        ArrayDescriptor des_PRODUCET_TABLE = ArrayDescriptor.createDescriptor("IDC_RE_PRODUCT_ARRAY",oracleConn);
        STRUCT[] result = null;
        if (parameter == null) {
            if (jdbcType == null) {
                throw new TypeException("JDBC requires that the JdbcType must be specified for all nullable parameters.");
            }
            try {
                idcReProductList = new ArrayList<IdcReProduct>();
                result = new STRUCT[idcReProductList.size()];
                ARRAY  array = new ARRAY(des_PRODUCET_TABLE, oracleConn, result);
                ps.setArray(i, array);
            } catch (SQLException e) {
                throw new TypeException("Error setting null for parameter #" + i + " with JdbcType " + jdbcType + " . " +
                        "Try setting a different JdbcType for this parameter or a different jdbcTypeForNull configuration property. " +
                        "Cause: " + e, e);
            }
        }else{
            log.debug("[[[[[组装相应的数据:定义的格式数据]]]]]----------------");
            idcReProductList = (ArrayList<IdcReProduct>)parameter;
            result = new STRUCT[idcReProductList.size()];
            for(int index = 0; index < idcReProductList.size(); index++){
                IdcReProduct idcReProduct = idcReProductList.get(index);
                Object[] objects = new Object[21];
            /*判断如果是null 则不需要往里面保存*/
                if( idcReProduct.getId() != null){objects[0] = idcReProduct.getId();}else{objects[0] = null;}
                objects[1] = 0;
                if( idcReProduct.getProdInstId() != null ){ objects[2] = idcReProduct.getProdInstId();}else{objects[2] = null;}
                if( idcReProduct.getIsActive() != null ){ objects[3] = idcReProduct.getIsActive(); }else{objects[3] = 1;}
                if( idcReProduct.getProdStatus() != null ){  objects[4] = idcReProduct.getProdStatus(); }else{objects[4] = null;}
                if( idcReProduct.getProdName() != null ){  objects[5] = idcReProduct.getProdName();  }else{objects[5] = null;}
                if( idcReProduct.getApplyId() != null ){ objects[6] = idcReProduct.getApplyId();  }else{objects[6] = null;}
                if( idcReProduct.getCreateTime() != null ){  objects[7] = idcReProduct.getCreateTime();  }else{objects[7] = null;}
                if( idcReProduct.getCustomerId() != null ){ objects[8] = idcReProduct.getCustomerId(); }else{objects[8] = null;}
                if( idcReProduct.getEndTime() != null ){ objects[9] = idcReProduct.getEndTime(); }else{objects[9] = null;}
                if( idcReProduct.getProdCategory() != null ){ objects[10] = idcReProduct.getProdCategory(); }else{objects[10] = 1;}
                if( idcReProduct.getProcticketStatus() != null ){ objects[11] = idcReProduct.getProcticketStatus(); }else{objects[11] = 10;}
                if( idcReProduct.getApplyName() != null ){  objects[12] = idcReProduct.getApplyName(); }else{objects[12] = null;}
                //if( idcReProduct.getCustomerName() != null ){ objects[13] = idcReProduct.getCustomerName(); }else{objects[13] = null;}
                objects[13] ="3333";
                if( idcReProduct.getValidity() != null ){  objects[14] = idcReProduct.getValidity();  }else{objects[14] = null;}
                if( idcReProduct.getIdcName() != null ){  objects[15] = idcReProduct.getIdcName(); }else{objects[15] = "900001";}
                if( idcReProduct.getBusName() != null ){   objects[16] = idcReProduct.getBusName();  }else{objects[16] = null;}
                if( idcReProduct.getApplyerName() != null ){  objects[17] = idcReProduct.getApplyerName(); }else{objects[17] = null;}
                if( idcReProduct.getApplyerRoles() != null ){  objects[18] = idcReProduct.getApplyerRoles();  }else{objects[18] = null;}
                if( idcReProduct.getApplyerPhone() != null ){  objects[19] = idcReProduct.getApplyerPhone();  }else{objects[19] = null;}
                objects[20] = null;
                result[index] = new STRUCT(sd,oracleConn,objects);
            }
            //OK
            ARRAY oracle_array = new ARRAY(des_PRODUCET_TABLE,oracleConn,result);

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
