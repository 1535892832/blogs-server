package com.ruoyi.blogs.controller;

import java.util.List;

import com.ruoyi.common.utils.SecurityUtils;
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
import com.ruoyi.blogs.domain.NoteGroup;
import com.ruoyi.blogs.service.INoteGroupService;
import com.ruoyi.common.utils.poi.ExcelUtil;

/**
 * 笔记分类管理Controller
 * 
 * @author tryCatch
 * @date 2021-07-21
 */
@RestController
@RequestMapping("/blogs/group")
public class NoteGroupController extends BaseController
{
    @Autowired
    private INoteGroupService noteGroupService;

    /**
     * 查询笔记分类管理列表
     */
    @PreAuthorize("@ss.hasPermi('blogs:group:list')")
    @GetMapping("/list")
    public AjaxResult list(NoteGroup noteGroup)
    {
        List<NoteGroup> list = noteGroupService.selectNoteGroupList(noteGroup);
        return AjaxResult.success(list);
    }

    @GetMapping("/treeselect")
    public AjaxResult treeselect(NoteGroup noteGroup)
    {
        noteGroup.setVisible(NoteGroup.STATUS_SHOW);
        List<NoteGroup> noteGroups = noteGroupService.selectNoteGroupList(noteGroup);
        return AjaxResult.success(noteGroupService.buildNoteGroupTreeSelect(noteGroups));
    }


    /**
     * 导出笔记分类管理列表
     */
    @PreAuthorize("@ss.hasPermi('blogs:group:export')")
    @Log(title = "笔记分类管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(NoteGroup noteGroup)
    {
        List<NoteGroup> list = noteGroupService.selectNoteGroupList(noteGroup);
        ExcelUtil<NoteGroup> util = new ExcelUtil<NoteGroup>(NoteGroup.class);
        return util.exportExcel(list, "笔记分类管理数据");
    }

    /**
     * 获取笔记分类管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('blogs:group:query')")
    @GetMapping(value = "/{groupId}")
    public AjaxResult getInfo(@PathVariable("groupId") String groupId)
    {
        return AjaxResult.success(noteGroupService.selectNoteGroupById(groupId));
    }

    /**
     * 新增笔记分类管理
     */
    @PreAuthorize("@ss.hasPermi('blogs:group:add')")
    @Log(title = "笔记分类管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody NoteGroup noteGroup)
    {
        noteGroup.setCreateBy(SecurityUtils.getUsername());
        return toAjax(noteGroupService.insertNoteGroup(noteGroup));
    }

    /**
     * 修改笔记分类管理
     */
    @PreAuthorize("@ss.hasPermi('blogs:group:edit')")
    @Log(title = "笔记分类管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody NoteGroup noteGroup)
    {
        return toAjax(noteGroupService.updateNoteGroup(noteGroup));
    }

    /**
     * 删除笔记分类管理
     */
    @PreAuthorize("@ss.hasPermi('blogs:group:remove')")
    @Log(title = "笔记分类管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{groupIds}")
    public AjaxResult remove(@PathVariable String[] groupIds)
    {
        return toAjax(noteGroupService.deleteNoteGroupByIds(groupIds));
    }
}
