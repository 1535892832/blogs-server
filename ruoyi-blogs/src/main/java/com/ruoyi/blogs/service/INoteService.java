package com.ruoyi.blogs.service;

import java.util.List;
import com.ruoyi.blogs.domain.Note;

/**
 * 笔记管理Service接口
 * 
 * @author tryCatch
 * @date 2021-07-21
 */
public interface INoteService 
{
    /**
     * 查询笔记管理
     * 
     * @param id 笔记管理ID
     * @return 笔记管理
     */
    public Note selectNoteById(String id);

    /**
     * 查询笔记管理列表
     * 
     * @param note 笔记管理
     * @return 笔记管理集合
     */
    public List<Note> selectNoteList(Note note);

    /**
     * 查询手机端列表
     * @param note
     * @return
     */
    public List<Note> selectNoteSimpleList(Note note);

    /**
     * 新增笔记管理
     * 
     * @param note 笔记管理
     * @return 结果
     */
    public int insertNote(Note note);

    public int updateNoteVisible(Note note);

    public int updateOfficialRecommend(Note note);

    public List<Note> selectNoteOfficialRecommend(Note note);

    public List<Note> selectNoteNewest(Note note);

    /**
     * 修改笔记管理
     * 
     * @param note 笔记管理
     * @return 结果
     */
    public int updateNote(Note note);

    /**
     * 批量删除笔记管理
     * 
     * @param ids 需要删除的笔记管理ID
     * @return 结果
     */
    public int deleteNoteByIds(String[] ids);

    /**
     * 删除笔记管理信息
     * 
     * @param id 笔记管理ID
     * @return 结果
     */
    public int deleteNoteById(String id);
}
