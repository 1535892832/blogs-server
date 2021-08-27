package com.ruoyi.blogs.mapper;

import java.util.List;
import com.ruoyi.blogs.domain.NoteGroup;

/**
 * 笔记分类管理Mapper接口
 * 
 * @author tryCatch
 * @date 2021-07-21
 */
public interface NoteGroupMapper 
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
     * 删除笔记分类管理
     * 
     * @param groupId 笔记分类管理ID
     * @return 结果
     */
    public int deleteNoteGroupById(String groupId);

    /**
     * 批量删除笔记分类管理
     * 
     * @param groupIds 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoteGroupByIds(String[] groupIds);
}
