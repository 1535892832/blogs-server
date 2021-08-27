package com.ruoyi.blogs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * Banner管理对象 bs_banner
 * 
 * @author tryCatch
 * @date 2021-08-01
 */
public class Banner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** id */
    private String id;

    /** banner标题 */
    @Excel(name = "banner标题")
    private String title;

    /** banner图 */
    @Excel(name = "banner图")
    private String imgUrl;

    /** 是否外链 */
    @Excel(name = "是否外链")
    private Long isOutside;

    /** 外链链接 */
    @Excel(name = "外链链接")
    private String outsideHref;

    /** 排序序号 */
    @Excel(name = "排序序号")
    private Long orderNum;

    /** 是否显示 0显示 1不显示 */
    @Excel(name = "是否显示 0显示 1不显示")
    private Long visible;

    /** 用户名 */
    @Excel(name = "用户名")
    private String userName;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    public void setId(String id) 
    {
        this.id = id;
    }

    public String getId() 
    {
        return id;
    }
    public void setTitle(String title) 
    {
        this.title = title;
    }

    public String getTitle() 
    {
        return title;
    }
    public void setImgUrl(String imgUrl) 
    {
        this.imgUrl = imgUrl;
    }

    public String getImgUrl() 
    {
        return imgUrl;
    }
    public void setIsOutside(Long isOutside) 
    {
        this.isOutside = isOutside;
    }

    public Long getIsOutside() 
    {
        return isOutside;
    }
    public void setOutsideHref(String outsideHref) 
    {
        this.outsideHref = outsideHref;
    }

    public String getOutsideHref() 
    {
        return outsideHref;
    }
    public void setOrderNum(Long orderNum) 
    {
        this.orderNum = orderNum;
    }

    public Long getOrderNum() 
    {
        return orderNum;
    }
    public void setVisible(Long visible) 
    {
        this.visible = visible;
    }

    public Long getVisible() 
    {
        return visible;
    }
    public void setUserName(String userName) 
    {
        this.userName = userName;
    }

    public String getUserName() 
    {
        return userName;
    }
    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("title", getTitle())
            .append("imgUrl", getImgUrl())
            .append("isOutside", getIsOutside())
            .append("outsideHref", getOutsideHref())
            .append("orderNum", getOrderNum())
            .append("visible", getVisible())
            .append("userName", getUserName())
            .append("userId", getUserId())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
