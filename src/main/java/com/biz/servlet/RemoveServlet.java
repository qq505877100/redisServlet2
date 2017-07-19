package com.biz.servlet;/**
 * Created by dell on 2017/7/18.
 */

import com.biz.domain.Student;
import com.biz.util.JedisUtils;
import com.biz.util.JsonUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 * @author
 * @description
 * @create 2017-07-18 14:19
 **/
public class RemoveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //设置提示信息返回浏览器
        StringBuilder sb = new StringBuilder();
        String id = req.getParameter("id");
        //先查询在删除
        Set<String> keys = JedisUtils.getKeys("student:" + id);
        if (keys.size() == 0 || keys == null) {
            //提示删除失败，不存在此人
            sb.append("{\"result\":false,\"msg\":\"删除失败，不存在此人信息！！！\"}");
        } else {
            //删除逻辑，删除两部分的信息
            JedisUtils.delKey("student:" + id);
            JedisUtils.delSortedValue("student","student:" + id);
            sb.append("{\"result\":true,\"msg\":\"删除成功！！！\"}");
        }

        //发送信息到浏览器端
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("charset:utf-8;text/json");
        resp.getWriter().print(sb.toString());
    }
}
