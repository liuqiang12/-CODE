package system.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * Component
 * Created by DELL on 2017/9/7.
 */
@Component
public class RESTClient {
    @Autowired
    private RestTemplate template;

    private final static String url = "http://localhost:8080/SpringRestWS/restful/";

    public String show() {
        return template.getForObject(url + "show.do", String.class, new String[]{});
    }

    public String getUserById(String id) {
        return template.getForObject(url + "get/{id}.do", String.class, id);
    }

    public String addUser(String user) {
        return template.postForObject(url + "add.do?user={user}", null, String.class, user);
    }

    public String editUser(String user) {
        template.put(url + "edit.do?user={user}", null, user);
        return user;
    }

    public String removeUser(String id) {
        template.delete(url + "/remove/{id}.do", id);
        return id;
    }
}
