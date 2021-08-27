package com.ruoyi.blogs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.blogs.mapper.CommentMapper;
import com.ruoyi.blogs.domain.Comment;
import com.ruoyi.blogs.service.ICommentService;

/**
 * 博客评论管理Service业务层处理
 * 
 * @author tryCatch
 * @date 2021-08-15
 */
@Service
public class CommentServiceImpl implements ICommentService 
{
    @Autowired
    private CommentMapper commentMapper;

    /**
     * 查询博客评论管理
     * 
     * @param id 博客评论管理ID
     * @return 博客评论管理
     */
    @Override
    public Comment selectCommentById(Integer id)
    {
        return commentMapper.selectCommentById(id);
    }

    /**
     * 查询博客评论管理列表
     * 
     * @param comment 博客评论管理
     * @return 博客评论管理
     */
    @Override
    public List<Comment> selectCommentList(Comment comment)
    {
        return commentMapper.selectCommentList(comment);
    }

    /**
     * 新增博客评论管理
     * 
     * @param comment 博客评论管理
     * @return 结果
     */
    @Override
    public int insertComment(Comment comment)
    {
        comment.setCreateTime(DateUtils.getNowDate());
        return commentMapper.insertComment(comment);
    }

    /**
     * 修改博客评论管理
     * 
     * @param comment 博客评论管理
     * @return 结果
     */
    @Override
    public int updateComment(Comment comment)
    {
        return commentMapper.updateComment(comment);
    }

    /**
     * 批量删除博客评论管理
     * 
     * @param ids 需要删除的博客评论管理ID
     * @return 结果
     */
    @Override
    public int deleteCommentByIds(String[] ids)
    {
        return commentMapper.deleteCommentByIds(ids);
    }

    /**
     * 删除博客评论管理信息
     * 
     * @param id 博客评论管理ID
     * @return 结果
     */
    @Override
    public int deleteCommentById(String id)
    {
        return commentMapper.deleteCommentById(id);
    }
}
