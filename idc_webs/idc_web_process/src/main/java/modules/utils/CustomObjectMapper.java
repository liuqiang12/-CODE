package modules.utils;

import com.bpm.ModelController;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by DELL on 2017/6/7.
 */
public class CustomObjectMapper  extends ObjectMapper {
    private Logger logger = LoggerFactory.getLogger(CustomObjectMapper.class);
    public CustomObjectMapper(){
        super();
        logger.debug("CustomObjectMapperCustomObjectMapperCustomObjectMapperCustomObjectMapperCustomObjectMapper");
        this.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        this.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        //this.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES,true);
    }
}
