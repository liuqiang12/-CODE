package com.idc.servlet;

import org.restlet.Request;
import org.restlet.Response;
import org.restlet.Restlet;
import org.restlet.data.MediaType;
import org.restlet.ext.servlet.ServletAdapter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by DELL on 2017/9/6.
 */
public class RestHttpServlet extends HttpServlet {
    private ServletAdapter adapter;

    public void init() throws ServletException {
        super.init();
        this.adapter = new ServletAdapter(getServletContext());

        Restlet trace = new Restlet(this.adapter.getContext()) {
            public void handle(Request req, Response res) {
                getLogger().info("Hello World");
                res.setEntity("Hello World!", MediaType.TEXT_PLAIN);
            }
        };

        this.adapter.setNext(trace);
    }

    protected void service(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        this.adapter.service(req, res);
    }
}
