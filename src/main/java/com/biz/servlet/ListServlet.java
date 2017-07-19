package com.biz.servlet;/**
 * Created by dell on 2017/7/14.
 */

import com.biz.domain.PageBean;
import com.biz.domain.Student;
import com.biz.util.JedisUtils;
import com.biz.util.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

/**
 * @author
 * @description
 * @create 2017-07-14 17:38
 **/
public class ListServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //实现查询所有学生信息.用hash存储学生信息。//同时实现分页page,rows
        //获取分页信息
        String page = req.getParameter("page");
        String rows = req.getParameter("rows");
        //如果页面没有传参数过来，设置page,rows的默认值。
        if (StringUtils.isEmpty(page) || StringUtils.isEmpty(rows)) {
            page = "1";
            rows = "10";
        }
        PageBean pageBean = new PageBean();
        pageBean.setCurrPage(Integer.parseInt(page));
        pageBean.setPageSize(Integer.parseInt(rows));

        //查询出总的学生人数
        int allCounts = JedisUtils.getAllCounts("student:*");
        pageBean.setTotal(allCounts);
        //对key进行分页
        int offset = (pageBean.getCurrPage() - 1) * pageBean.getPageSize();
        Set<String> studentKeys = JedisUtils.getRangeByScore("student", offset, pageBean.getPageSize());
        Iterator<String> iterator = studentKeys.iterator();

        //分页查询出学生数据存储到pageBean的rows中。
        while(iterator.hasNext()) {
            String key = iterator.next();
            String studentJson = JedisUtils.get(key);
            //json转为对象
            Student student = JsonUtils.jsonToObject(studentJson, Student.class);
            pageBean.getRows().add(student);
        }

        resp.setCharacterEncoding("utf-8");
        resp.setContentType("charset:utf-8;text/json");
        //将pageBean转化为json返回浏览器
        String pageBeanJson = JsonUtils.objectToJson(pageBean);
        resp.getWriter().print(pageBeanJson);
    }
}
