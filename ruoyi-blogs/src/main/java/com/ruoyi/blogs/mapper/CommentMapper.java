package com.ruoyi.blogs.mapper;

import java.util.List;
import com.ruoyi.blogs.domain.Comment;

/**
 * 博客评论管理Mapper接口
 * 
 * @author tryCatch
 * @date 2021-08-15
 */
public interface CommentMapper 
{
    /**
     * 查询博客评论管理
     * 
     * @param id 博客评论管理ID
     * @return 博客评论管理
     */
    public Comment selectCommentById(Integer id);

    /**
     * 查询博客评论管理列表
     * 
     * @param comment 博客评论管理
     * @return 博客评论管理集合
     */
    public List<Comment> selectCommentList(Comment comment);

    /**
     * 新增博客评论管理
     * 
     * @param comment 博客评论管理
     * @return 结果
     */
    public int insertComment(Comment comment);

    /**
     * 修改博客评论管理
     * 
     * @param comment 博客评论管理
     * @return 结果
     */
    public int updateComment(Comment comment);

    /**
     * 删除博客评论管理
     * 
     * @param id 博客评论管理ID
     * @return 结果
     */
    public int deleteCommentById(String id);

    /**
     * 批量删除博客评论管理
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteCommentByIds(String[] ids);
}
