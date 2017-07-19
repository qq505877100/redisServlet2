package com.biz.util;/**
 * Created by dell on 2017/7/14.
 */

import com.alibaba.fastjson.JSON;
import com.biz.domain.Student;

/**
 * @author
 * @description
 * @create 2017-07-14 18:13
 **/
public class JsonUtils {
    public static <T> String objectToJson(T t) {
        Object o = JSON.toJSON(t);
        String json = JSON.toJSONString(t);
        return json;
    }

    public static <T> T jsonToObject(String json,Class<T> clazz) {
        T t = JSON.parseObject(json, clazz);
        return t;
    }
   /* public static void main(String[] args) {
        Student student = new Student();
        student.setId("001");
        student.setName("zhangsan");
        student.setBirthday("2017-09-09");
        student.setRemark("ssss");
        student.setAvgScore(33);

        String str = objectToJson(student);
        System.out.println(str);

        Student student1 = jsonToObject(str, Student.class);

        System.out.println(student1);
    }*/
}
