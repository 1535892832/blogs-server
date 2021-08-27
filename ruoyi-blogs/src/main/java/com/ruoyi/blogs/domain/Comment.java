package com.ruoyi.blogs.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * 博客评论管理对象 bs_comment
 * 
 * @author tryCatch
 * @date 2021-08-15
 */
public class Comment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public static final Integer DEL_FLAG_NO = 0;

    public static final Integer DEL_FLAG_YES = 1;

    private Integer id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 用户名称 */
    @Excel(name = "用户名称")
    private String userName;

    /** 笔记id */
    @Excel(name = "笔记id")
    private String noteId;

    /** 评论的上级id */
    @Excel(name = "评论的上级id")
    private Long parentId;

    /** 内容 */
    @Excel(name = "内容")
    @NotBlank(message = "评论内容不能为空")
    @Size(max = 200)
    private String content;

    /** 是否删除 */
    private Integer delFlag;

    /** 删除时间 */
    private Date delTime;

    /** 删除人的账号 */
    private String delUserName;

    private Integer hasSensitiveWord;

    public Integer getHasSensitiveWord() {
        return hasSensitiveWord;
    }

    public void setHasSensitiveWord(Integer hasSensitiveWord) {
        this.hasSensitiveWord = hasSensitiveWord;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setNoteId(String noteId) 
    {
        this.noteId = noteId;
    }

    public String getNoteId() 
    {
        return noteId;
    }
    public void setParentId(Long parentId) 
    {
        this.parentId = parentId;
    }

    public Long getParentId() 
    {
        return parentId;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setDelFlag(Integer delFlag) 
    {
        this.delFlag = delFlag;
    }

    public Integer getDelFlag() 
    {
        return delFlag;
    }
    public void setDelTime(Date delTime) 
    {
        this.delTime = delTime;
    }

    public Date getDelTime() 
    {
        return delTime;
    }
    public void setDelUserName(String delUserName) 
    {
        this.delUserName = delUserName;
    }

    public String getDelUserName() 
    {
        return delUserName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("userName", getUserName())
            .append("noteId", getNoteId())
            .append("parentId", getParentId())
            .append("content", getContent())
            .append("delFlag", getDelFlag())
            .append("createTime", getCreateTime())
            .append("delTime", getDelTime())
            .append("delUserName", getDelUserName())
            .toString();
    }
}
