package com.ruoyi.blogs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.blogs.mapper.NoteMapper;
import com.ruoyi.blogs.domain.Note;
import com.ruoyi.blogs.service.INoteService;

/**
 * 笔记管理Service业务层处理
 * 
 * @author tryCatch
 * @date 2021-07-21
 */
@Service
public class NoteServiceImpl implements INoteService 
{
    @Autowired
    private NoteMapper noteMapper;

    /**
     * 查询笔记管理
     * 
     * @param id 笔记管理ID
     * @return 笔记管理
     */
    @Override
    public Note selectNoteById(String id)
    {
        return noteMapper.selectNoteById(id);
    }

    /**
     * 查询笔记管理列表
     * 
     * @param note 笔记管理
     * @return 笔记管理
     */
    @Override
    public List<Note> selectNoteList(Note note)
    {
        return noteMapper.selectNoteList(note);
    }

    @Override
    public List<Note> selectNoteSimpleList(Note note) {
        return noteMapper.selectNoteSimpleList(note);
    }

    /**
     * 新增笔记管理
     * 
     * @param note 笔记管理
     * @return 结果
     */
    @Override
    public int insertNote(Note note)
    {
        note.setId(IdUtils.fastSimpleUUID());
        note.setAuthorId(SecurityUtils.getLoginUser().getUser().getUserId());
        note.setUserName(SecurityUtils.getUsername());
        note.setCreateTime(DateUtils.getNowDate());
        return noteMapper.insertNote(note);
    }

    @Override
    public int updateNoteVisible(Note note) {
        return noteMapper.updateNoteVisible(note);
    }

    @Override
    public int updateOfficialRecommend(Note note) {
        return noteMapper.updateOfficialRecommend(note);
    }

    @Override
    public List<Note> selectNoteOfficialRecommend(Note note) {
        return noteMapper.selectNoteOfficialRecommend(note);
    }

    @Override
    public List<Note> selectNoteNewest(Note note) {
        return noteMapper.selectNoteNewest(note);
    }

    /**
     * 修改笔记管理
     * 
     * @param note 笔记管理
     * @return 结果
     */
    @Override
    public int updateNote(Note note)
    {
        note.setUpdateTime(DateUtils.getNowDate());
        return noteMapper.updateNote(note);
    }

    /**
     * 批量删除笔记管理
     * 
     * @param ids 需要删除的笔记管理ID
     * @return 结果
     */
    @Override
    public int deleteNoteByIds(String[] ids)
    {
        return noteMapper.deleteNoteByIds(ids);
    }

    /**
     * 删除笔记管理信息
     * 
     * @param id 笔记管理ID
     * @return 结果
     */
    @Override
    public int deleteNoteById(String id)
    {
        return noteMapper.deleteNoteById(id);
    }
}
