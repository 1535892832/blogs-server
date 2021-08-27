package com.ruoyi.blogs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.blogs.mapper.BannerMapper;
import com.ruoyi.blogs.domain.Banner;
import com.ruoyi.blogs.service.IBannerService;

/**
 * Banner管理Service业务层处理
 * 
 * @author tryCatch
 * @date 2021-08-01
 */
@Service
public class BannerServiceImpl implements IBannerService 
{
    @Autowired
    private BannerMapper bannerMapper;

    /**
     * 查询Banner管理
     * 
     * @param id Banner管理ID
     * @return Banner管理
     */
    @Override
    public Banner selectBannerById(String id)
    {
        return bannerMapper.selectBannerById(id);
    }

    /**
     * 查询Banner管理列表
     * 
     * @param banner Banner管理
     * @return Banner管理
     */
    @Override
    public List<Banner> selectBannerList(Banner banner)
    {
        return bannerMapper.selectBannerList(banner);
    }

    /**
     * 新增Banner管理
     * 
     * @param banner Banner管理
     * @return 结果
     */
    @Override
    public int insertBanner(Banner banner)
    {
        banner.setId(IdUtils.fastSimpleUUID());
        banner.setCreateTime(DateUtils.getNowDate());
        return bannerMapper.insertBanner(banner);
    }

    /**
     * 修改Banner管理
     * 
     * @param banner Banner管理
     * @return 结果
     */
    @Override
    public int updateBanner(Banner banner)
    {
        banner.setUpdateTime(DateUtils.getNowDate());
        return bannerMapper.updateBanner(banner);
    }

    /**
     * 批量删除Banner管理
     * 
     * @param ids 需要删除的Banner管理ID
     * @return 结果
     */
    @Override
    public int deleteBannerByIds(String[] ids)
    {
        return bannerMapper.deleteBannerByIds(ids);
    }

    /**
     * 删除Banner管理信息
     * 
     * @param id Banner管理ID
     * @return 结果
     */
    @Override
    public int deleteBannerById(String id)
    {
        return bannerMapper.deleteBannerById(id);
    }
}
