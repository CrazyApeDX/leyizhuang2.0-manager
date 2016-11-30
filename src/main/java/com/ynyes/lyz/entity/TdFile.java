package com.ynyes.lyz.entity;

import java.util.Date;


/**
 * 数据库
 * 
 * @author Sharon
 *
 */

public class TdFile {

    private String title;
    
    private Date modifyTime;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
