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
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.UUID;

/**
 * @author
 * @description 添加学生信息的方法
 * @create 2017-07-18 13:54
 **/
public class AddServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //解决乱码问题
        req.setCharacterEncoding("utf-8");
        Map<String, String[]> map = req.getParameterMap();
        String name = req.getParameter("name");
        Student student = new Student();
        //提示信息
        StringBuilder sb = new StringBuilder();
        try {
            BeanUtils.populate(student,map);
            String id = UUID.randomUUID().toString().replace("-","");
            student.setId(id);

            //保存学生信息到redis数据库，分两步，第一保存基本信息，第二，保存分数到sorted-set中
            String json = JsonUtils.objectToJson(student);
            JedisUtils.set("student:" + student.getId(),json);

            JedisUtils.zadd("student",student.getAvgScore(),"student:" + student.getId());
            //拼接返回提醒信息
            sb.append("{'result':true,'msg':'添加成功！！！'}");
        } catch (Exception e) {
            sb.append("{'result':false,'msg':'添加失败！！！'}");
            e.printStackTrace();
        }
        //发送信息到浏览器端
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("charset:utf-8;text/json");
        resp.getWriter().print(sb.toString());
    }
}
