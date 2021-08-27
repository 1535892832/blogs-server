package com.ruoyi.blogs.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.TreeEntity;

/**
 * 笔记分类管理对象 bs_note_group
 * 
 * @author tryCatch
 * @date 2021-07-21
 */
public class NoteGroup extends TreeEntity
{
    private static final long serialVersionUID = 1L;


    public static final Long STATUS_SHOW = 0L;

    /** 分类id */
    private String groupId;

    /** 分类名称 */
    @Excel(name = "分类名称")
    private String groupName;

    /** 是否显示 */
    @Excel(name = "是否显示")
    private Long visible;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    /** 图标 */
    @Excel(name = "图标")
    private String icon;

    /** 备注 */
    @Excel(name = "备注")
    private String mark;

    public void setGroupId(String groupId) 
    {
        this.groupId = groupId;
    }

    public String getGroupId() 
    {
        return groupId;
    }
    public void setGroupName(String groupName) 
    {
        this.groupName = groupName;
    }

    public String getGroupName() 
    {
        return groupName;
    }
    public void setVisible(Long visible) 
    {
        this.visible = visible;
    }

    public Long getVisible() 
    {
        return visible;
    }
    public void setStatus(Long status) 
    {
        this.status = status;
    }

    public Long getStatus() 
    {
        return status;
    }
    public void setIcon(String icon) 
    {
        this.icon = icon;
    }

    public String getIcon() 
    {
        return icon;
    }
    public void setMark(String mark) 
    {
        this.mark = mark;
    }

    public String getMark() 
    {
        return mark;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("groupId", getGroupId())
            .append("groupName", getGroupName())
            .append("parentId", getParentId())
            .append("orderNum", getOrderNum())
            .append("visible", getVisible())
            .append("status", getStatus())
            .append("icon", getIcon())
            .append("createBy", getCreateBy())
            .append("mark", getMark())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
