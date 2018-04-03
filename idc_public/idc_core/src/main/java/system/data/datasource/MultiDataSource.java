package system.data.datasource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

public class MultiDataSource extends AbstractRoutingDataSource{
	private final Log log = LogFactory.getLog(this.getClass());
	@Override
	protected Object determineCurrentLookupKey() {
		log.info(DataSourceContextHolder.getDataSourceType());
		return DataSourceContextHolder.getDataSourceType();
	}

}
