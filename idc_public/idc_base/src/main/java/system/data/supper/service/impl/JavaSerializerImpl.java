package system.data.supper.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;
import system.data.supper.service.JavaSerializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化
 */
@Component(value="javaSerializer")
public class JavaSerializerImpl implements JavaSerializer {
	 
    private static Log log = LogFactory.getLog(JavaSerializerImpl.class);
    /**
     * 序列化
     */
    public byte[] serialize(Object obj) {
    	
        ObjectOutputStream oos = null;
        
        ByteArrayOutputStream baos = null;
        
        try {
        
        	baos = new ByteArrayOutputStream();
            
        	oos = new ObjectOutputStream(baos);
            
        	oos.flush();
            
        	oos.writeObject(obj);
            
        	return baos.toByteArray();
        
        } catch (Exception e) {
            log.error("JavaSerializer.serialize"+e.getMessage(),e);
        }
        return null;
    }
    /**
     * 反序列化
     */
    public Object unserialize(byte[] str) {
    	
        ByteArrayInputStream bais = null;
        
        try {
        
        	bais = new ByteArrayInputStream(str);
            
        	ObjectInputStream ois = new ObjectInputStream(bais);
            
        	return ois.readObject();
        
        } catch (Exception e) {
            log.error("JavaSerializer.unserialize",e);
        }
        return null;
    }
}
