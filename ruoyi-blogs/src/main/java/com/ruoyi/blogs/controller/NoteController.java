package com.ruoyi.blogs.controller;

import java.util.List;
import com.ruoyi.blogs.utils.PermissionUtils;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.blogs.domain.Note;
import com.ruoyi.blogs.service.INoteService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 笔记管理Controller
 *
 * @author tryCatch
 * @date 2021-07-21
 */
@RestController
@RequestMapping("/blogs/note")
public class NoteController extends BaseController {

    @Autowired
    private INoteService noteService;

    /**
     * 查询笔记管理列表
     */
    @PreAuthorize("@ss.hasPermi('blogs:note:list')")
    @GetMapping("/list")
    public TableDataInfo list(Note note) {
        if(!PermissionUtils.hasPermission(SecurityUtils.getLoginUser().getPermissions(),UserConstants.PERMISSION_NOTE_LIST_ALL)){
            note.setUserName(SecurityUtils.getUsername());
        }
        startPage();
        List<Note> list = noteService.selectNoteList(note);
        return getDataTable(list);
    }

    /**
     * 导出笔记管理列表
     */
    @PreAuthorize("@ss.hasPermi('blogs:note:export')")
    @Log(title = "笔记管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Note note) {
        List<Note> list = noteService.selectNoteList(note);
        ExcelUtil<Note> util = new ExcelUtil<Note>(Note.class);
        return util.exportExcel(list, "笔记管理数据");
    }

    // 改变状态
    @PutMapping("/changeVisible")
    @PreAuthorize("@ss.hasPermi('blogs:note:edit')")
    public AjaxResult changeVisible(@RequestBody Note note) {
        note.setUpdateTime(DateUtils.getNowDate());
        return toAjax(noteService.updateNoteVisible(note));
    }

    // 官方推荐
    @PutMapping("/changeOfficialRecommend")
    @PreAuthorize("@ss.hasPermi('blogs:note:officialRecommend')")
    public AjaxResult changeOfficialRecommend(@RequestBody Note note){
        return toAjax(noteService.updateOfficialRecommend(note));
    }

    /**
     * 获取笔记管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('blogs:note:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(noteService.selectNoteById(id));
    }

    /**
     * 新增笔记管理
     */
    @PreAuthorize("@ss.hasPermi('blogs:note:add')")
    @Log(title = "笔记管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Note note) {
        return toAjax(noteService.insertNote(note));
    }

    /**
     * 修改笔记管理
     */
    @PreAuthorize("@ss.hasPermi('blogs:note:edit')")
    @Log(title = "笔记管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Note note) {
        return toAjax(noteService.updateNote(note));
    }

    /**
     * 删除笔记管理
     */
    @PreAuthorize("@ss.hasPermi('blogs:note:remove')")
    @Log(title = "笔记管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids) {
        return toAjax(noteService.deleteNoteByIds(ids));
    }


}
