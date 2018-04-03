package com.utils;


import org.restlet.data.Form;
import org.restlet.resource.ClientResource;

/**
 * Created by DELL on 2017/9/5.
 */
public class RestJaxClient {
    public static void main(String[] args) throws Exception{
         /*Component component = new Component();
        component.getServers().add(Protocol.HTTP,8083);

        component.start();*/
        hand();
    }

    public static void hand(){
        /*
            这个地址是可以调用：
            ClientResource client = new ClientResource("http://localhost:8182/jbpm/ticket/1111111");
        */
        ClientResource client = new ClientResource("http://localhost:8080/idc/web_rs/jbpm/insert_xxx");

        try {
            Form form = new Form();
            form.add("vcid", "2");
            form.add("dialogid","DID0001");
            form.add("flowno","FL0001");
            form.add("skillno","2");
            form.add("priority","222");
            String result = client.put(form.getWebRepresentation()).getText();
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
