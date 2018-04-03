package modules.utils;

import org.activiti.engine.ProcessEngineConfiguration;

/**
 * Created by DELL on 2017/6/22.
 */
public class CreateActTables {

    public void createActivityTable(){
        ProcessEngineConfiguration p = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration();
        p.setJdbcDriver("oracle.jdbc.driver.OracleDriver");
        p.setJdbcUrl("jdbc:oracle:thin:@192.168.0.240:1521:dcim");
        p.setJdbcUsername("dcim");
        p.setJdbcPassword("dcim");
        /*p.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_CREATE_DROP);*/
        p.setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);
        p.buildProcessEngine();
    }
}
