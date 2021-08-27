package com.ruoyi.blogs.service;

import java.util.List;
import com.ruoyi.blogs.domain.NoteGroup;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.core.domain.entity.SysDept;

/**
 * 笔记分类管理Service接口
 * 
 * @author tryCatch
 * @date 2021-07-21
 */
public interface INoteGroupService 
{
    /**
     * 查询笔记分类管理
     * 
     * @param groupId 笔记分类管理ID
     * @return 笔记分类管理
     */
    public NoteGroup selectNoteGroupById(String groupId);

    /**
     * 查询笔记分类管理列表
     * 
     * @param noteGroup 笔记分类管理
     * @return 笔记分类管理集合
     */
    public List<NoteGroup> selectNoteGroupList(NoteGroup noteGroup);


    public List<NoteGroup> buildNoteGroupTree(List<NoteGroup> noteGroups);


    public List<TreeSelect> buildNoteGroupTreeSelect(List<NoteGroup> noteGroups);

    /**
     * 新增笔记分类管理
     * 
     * @param noteGroup 笔记分类管理
     * @return 结果
     */
    public int insertNoteGroup(NoteGroup noteGroup);

    /**
     * 修改笔记分类管理
     * 
     * @param noteGroup 笔记分类管理
     * @return 结果
     */
    public int updateNoteGroup(NoteGroup noteGroup);

    /**
     * 批量删除笔记分类管理
     * 
     * @param groupIds 需要删除的笔记分类管理ID
     * @return 结果
     */
    public int deleteNoteGroupByIds(String[] groupIds);

    /**
     * 删除笔记分类管理信息
     * 
     * @param groupId 笔记分类管理ID
     * @return 结果
     */
    public int deleteNoteGroupById(String groupId);
}
