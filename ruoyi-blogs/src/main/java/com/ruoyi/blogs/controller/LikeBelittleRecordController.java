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
import com.ruoyi.blogs.domain.LikeBelittleRecord;
import com.ruoyi.blogs.service.ILikeBelittleRecordService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 点赞点踩记录Controller
 * 
 * @author tryCatch
 * @date 2021-08-16
 */
@RestController
@RequestMapping("/blogs/record")
public class LikeBelittleRecordController extends BaseController
{
    @Autowired
    private ILikeBelittleRecordService likeBelittleRecordService;

    /**
     * 查询点赞点踩记录列表
     */
    @PreAuthorize("@ss.hasPermi('blogs:record:list')")
    @GetMapping("/list")
    public TableDataInfo list(LikeBelittleRecord likeBelittleRecord)
    {
        startPage();
        List<LikeBelittleRecord> list = likeBelittleRecordService.selectLikeBelittleRecordList(likeBelittleRecord);
        return getDataTable(list);
    }

    /**
     * 导出点赞点踩记录列表
     */
    @PreAuthorize("@ss.hasPermi('blogs:record:export')")
    @Log(title = "点赞点踩记录", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(LikeBelittleRecord likeBelittleRecord)
    {
        List<LikeBelittleRecord> list = likeBelittleRecordService.selectLikeBelittleRecordList(likeBelittleRecord);
        ExcelUtil<LikeBelittleRecord> util = new ExcelUtil<LikeBelittleRecord>(LikeBelittleRecord.class);
        return util.exportExcel(list, "点赞点踩记录数据");
    }

    /**
     * 获取点赞点踩记录详细信息
     */
    @PreAuthorize("@ss.hasPermi('blogs:record:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(likeBelittleRecordService.selectLikeBelittleRecordById(id));
    }

    /**
     * 新增点赞点踩记录
     */
    @PreAuthorize("@ss.hasPermi('blogs:record:add')")
    @Log(title = "点赞点踩记录", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody LikeBelittleRecord likeBelittleRecord)
    {
        return toAjax(likeBelittleRecordService.insertLikeBelittleRecord(likeBelittleRecord));
    }

    /**
     * 修改点赞点踩记录
     */
    @PreAuthorize("@ss.hasPermi('blogs:record:edit')")
    @Log(title = "点赞点踩记录", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody LikeBelittleRecord likeBelittleRecord)
    {
        return toAjax(likeBelittleRecordService.updateLikeBelittleRecord(likeBelittleRecord));
    }

    /**
     * 删除点赞点踩记录
     */
    @PreAuthorize("@ss.hasPermi('blogs:record:remove')")
    @Log(title = "点赞点踩记录", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(likeBelittleRecordService.deleteLikeBelittleRecordByIds(ids));
    }
}
