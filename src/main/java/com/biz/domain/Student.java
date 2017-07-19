package com.biz.domain;/**
 * Created by dell on 2017/7/14.
 */

/**
 * @author
 * @description
 * @create 2017-07-14 17:44
 **/
public class Student {
    private String id;
    private String name;
    private String birthday;
    private String remark;
    private Integer avgScore;
    public Student(){}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getAvgScore() {
        return avgScore;
    }

    public void setAvgScore(Integer avgScore) {
        this.avgScore = avgScore;
    }

    @Override
    public String toString() {
        return "Student{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday='" + birthday + '\'' +
                ", remark='" + remark + '\'' +
                ", avgScore=" + avgScore +
                '}';
    }
}
