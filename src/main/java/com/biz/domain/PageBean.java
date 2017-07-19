package com.biz.domain;/**
 * Created by dell on 2017/7/18.
 */

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @description
 * @create 2017-07-18 10:55
 **/
public class PageBean {
    //当前页
    private Integer currPage;
    //每页显示页数
    private Integer pageSize;
    //总记录数
    private Integer total;
    //数据集合

    public Integer getCurrPage() {
        return currPage;
    }

    public void setCurrPage(Integer currPage) {
        this.currPage = currPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }

    List rows ;
    public PageBean() {
        rows = new ArrayList();
    }

}
