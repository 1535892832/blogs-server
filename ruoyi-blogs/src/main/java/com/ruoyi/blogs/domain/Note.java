package com.ruoyi.blogs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 笔记管理对象 bs_note
 * 
 * @author tryCatch
 * @date 2021-07-21
 */
public class Note extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    public static final String TYPE_MD = "markdown";

    public static final String TYPE_WORD = "word";

    /** 笔记id */
    private String id;

    /** 分类id */
    @Excel(name = "分类id")
    private Long groupId;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    @Excel(name = "描述")
    private String description;

    @Excel(name = "封面图")
    private String coverPicture;

    /** 浏览量 */
    @Excel(name = "浏览量")
    private Long pageViewNum;

    /** 收藏 */
    @Excel(name = "收藏")
    private Long collectNum;

    /** 内容 */
    @Excel(name = "内容")
    private String content;

    @Excel(name = "是否显示")
    private Integer visible;

    /** 点赞数 */
    @Excel(name = "点赞数")
    private Long likeNum;

    @Excel(name = "用户名")
    private String userName;

    /** 作者id */
    @Excel(name = "作者id")
    private Long authorId;

    @Excel(name = "类型")
    private String type;

    @Excel(name = "是否官方推荐")
    private Integer isOfficialRecommend;

    private Long belittleNum;

    public Long getBelittleNum() {
        return belittleNum;
    }

    public void setBelittleNum(Long belittleNum) {
        this.belittleNum = belittleNum;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getIsOfficialRecommend() {
        return isOfficialRecommend;
    }

    public void setIsOfficialRecommend(Integer isOfficialRecommend) {
        this.isOfficialRecommend = isOfficialRecommend;
    }

    public Integer getVisible() {
        return visible;
    }

    public void setVisible(Integer visible) {
        this.visible = visible;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setGroupId(Long groupId) 
    {
        this.groupId = groupId;
    }

    public Long getGroupId() 
    {
        return groupId;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setPageViewNum(Long pageViewNum) 
    {
        this.pageViewNum = pageViewNum;
    }

    public Long getPageViewNum() 
    {
        return pageViewNum;
    }
    public void setCollectNum(Long collectNum) 
    {
        this.collectNum = collectNum;
    }

    public Long getCollectNum() 
    {
        return collectNum;
    }
    public void setContent(String content) 
    {
        this.content = content;
    }

    public String getContent() 
    {
        return content;
    }
    public void setLikeNum(Long likeNum) 
    {
        this.likeNum = likeNum;
    }

    public Long getLikeNum() 
    {
        return likeNum;
    }
    public void setAuthorId(Long authorId) 
    {
        this.authorId = authorId;
    }

    public Long getAuthorId() 
    {
        return authorId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCoverPicture() {
        return coverPicture;
    }

    public void setCoverPicture(String coverPicture) {
        this.coverPicture = coverPicture;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("groupId", getGroupId())
            .append("title", getTitle())
            .append("pageViewNum", getPageViewNum())
            .append("collectNum", getCollectNum())
            .append("content", getContent())
            .append("likeNum", getLikeNum())
            .append("authorId", getAuthorId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
