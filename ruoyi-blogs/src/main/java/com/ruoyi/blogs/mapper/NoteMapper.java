package com.ruoyi.blogs.mapper;

import java.util.List;
import com.ruoyi.blogs.domain.Note;
import org.aspectj.weaver.ast.Not;

/**
 * 笔记管理Mapper接口
 * 
 * @author tryCatch
 * @date 2021-07-21
 */
public interface NoteMapper 
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

    public List<Note> selectNoteSimpleList(Note note);

    public List<Note> selectNoteOfficialRecommend(Note note);

    public List<Note> selectNoteNewest(Note note);

    /**
     * 新增笔记管理
     * 
     * @param note 笔记管理
     * @return 结果
     */
    public int insertNote(Note note);

    /**
     * 修改笔记管理
     * 
     * @param note 笔记管理
     * @return 结果
     */
    public int updateNote(Note note);

    /**
     * 改变笔记显示状态
     * @param note
     * @return
     */
    public int updateNoteVisible(Note note);

    /***
     * 官方推荐
     * @param note
     * @return
     */
    public int updateOfficialRecommend(Note note);

    /**
     * 删除笔记管理
     * 
     * @param id 笔记管理ID
     * @return 结果
     */
    public int deleteNoteById(String id);

    /**
     * 批量删除笔记管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteNoteByIds(String[] ids);
}
