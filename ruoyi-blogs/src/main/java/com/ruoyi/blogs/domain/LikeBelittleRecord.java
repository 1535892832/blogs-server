package com.ruoyi.blogs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 点赞点踩记录对象 bs_like_belittle_record
 * 
 * @author tryCatch
 * @date 2021-08-16
 */
public class LikeBelittleRecord extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public static final Integer OP_LICK = 0;

    public static final Integer OP_BELITTLE = 1;

    /** id */
    private String id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 笔记id */
    @Excel(name = "笔记id")
    private String noteId;

    /** 操作 0 点赞  1 点踩 */
    @Excel(name = "操作 0 点赞  1 点踩")
    private Integer opType;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setNoteId(String noteId) 
    {
        this.noteId = noteId;
    }

    public String getNoteId() 
    {
        return noteId;
    }
    public void setOpType(Integer opType) 
    {
        this.opType = opType;
    }

    public Integer getOpType() 
    {
        return opType;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("noteId", getNoteId())
            .append("opType", getOpType())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
