package com.ruoyi.blogs.controller;

import java.util.List;
import java.util.Set;

import com.ruoyi.blogs.utils.PermissionUtils;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.SecurityUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.servletapi.SecurityContextHolderAwareRequestWrapper;
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
import com.ruoyi.blogs.domain.Banner;
import com.ruoyi.blogs.service.IBannerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * Banner管理Controller
 * 
 * @author tryCatch
 * @date 2021-08-01
 */
@RestController
@RequestMapping("/blogs/banner")
public class BannerController extends BaseController
{
    @Autowired
    private IBannerService bannerService;


    /**
     * 查询Banner管理列表
     */
    @PreAuthorize("@ss.hasPermi('blogs:banner:list')")
    @GetMapping("/list")
    public TableDataInfo list(Banner banner)
    {
        if(!PermissionUtils.hasPermission(SecurityUtils.getLoginUser().getPermissions(),UserConstants.PERMISSION_BANNER_LIST_ALL)){
            banner.setUserName(SecurityUtils.getUsername());
        }
        startPage();
        List<Banner> list = bannerService.selectBannerList(banner);
        return getDataTable(list);
    }

    /**
     * 导出Banner管理列表
     */
    @PreAuthorize("@ss.hasPermi('blogs:banner:export')")
    @Log(title = "Banner管理", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(Banner banner)
    {
        List<Banner> list = bannerService.selectBannerList(banner);
        ExcelUtil<Banner> util = new ExcelUtil<Banner>(Banner.class);
        return util.exportExcel(list, "Banner管理数据");
    }

    /**
     * 获取Banner管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('blogs:banner:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id)
    {
        return AjaxResult.success(bannerService.selectBannerById(id));
    }

    /**
     * 新增Banner管理
     */
    @PreAuthorize("@ss.hasPermi('blogs:banner:add')")
    @Log(title = "Banner管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Banner banner)
    {
        banner.setUserName(SecurityUtils.getUsername());
        banner.setUserId(SecurityUtils.getLoginUser().getUser().getUserId());
        return toAjax(bannerService.insertBanner(banner));
    }

    /**
     * 修改Banner管理
     */
    @PreAuthorize("@ss.hasPermi('blogs:banner:edit')")
    @Log(title = "Banner管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Banner banner)
    {
        return toAjax(bannerService.updateBanner(banner));
    }

    /**
     * 删除Banner管理
     */
    @PreAuthorize("@ss.hasPermi('blogs:banner:remove')")
    @Log(title = "Banner管理", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable String[] ids)
    {
        return toAjax(bannerService.deleteBannerByIds(ids));
    }
}
