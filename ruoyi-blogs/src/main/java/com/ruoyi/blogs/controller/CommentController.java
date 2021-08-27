package com.ruoyi.blogs.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.blogs.domain.Comment;
import com.ruoyi.blogs.service.ICommentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 博客评论管理Controller
 *
 * @author tryCatch
 * @date 2021-08-15
 */
@RestController
@RequestMapping("/blogs/comment")
public class CommentController extends BaseController {
    @Autowired
    private ICommentService commentService;

    /**
     * 查询博客评论管理列表
     */
    @PreAuthorize("@ss.hasPermi('blogs:comment:list')")
    @GetMapping("/list")
    public TableDataInfo list(Comment comment) {
        startPage();
        List<Comment> list = commentService.selectCommentList(comment);
        return getDataTable(list);
    }

    /**
     * 导出博客评论管理列表
     */
    @PreAuthorize("@ss.hasPermi('blogs:comment:export')")
    @Log(title = "博客评论管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Comment comment) {
        List<Comment> list = commentService.selectCommentList(comment);
        ExcelUtil<Comment> util = new ExcelUtil<Comment>(Comment.class);
        return util.exportExcel(list, "博客评论管理数据");
    }

    /**
     * 获取博客评论管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('blogs:comment:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Integer id) {
        return AjaxResult.success(commentService.selectCommentById(id));
    }

    /**
     * 新增博客评论管理
     */
    @PreAuthorize("@ss.hasPermi('blogs:comment:add')")
    @Log(title = "博客评论管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Comment comment) {
        return toAjax(commentService.insertComment(comment));
    }

    /**
     * 修改博客评论管理
     */
    @PreAuthorize("@ss.hasPermi('blogs:comment:edit')")
    @Log(title = "博客评论管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Comment comment) {
        return toAjax(commentService.updateComment(comment));
    }

    /**
     * 删除博客评论管理
     */
    @PreAuthorize("@ss.hasPermi('blogs:comment:remove')")
    @Log(title = "博客评论管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(commentService.deleteCommentByIds(ids));
    }
}
