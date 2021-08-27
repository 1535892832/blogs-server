package com.ruoyi.blogs.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.blogs.mapper.LikeBelittleRecordMapper;
import com.ruoyi.blogs.domain.LikeBelittleRecord;
import com.ruoyi.blogs.service.ILikeBelittleRecordService;

/**
 * 点赞点踩记录Service业务层处理
 * 
 * @author tryCatch
 * @date 2021-08-16
 */
@Service
public class LikeBelittleRecordServiceImpl implements ILikeBelittleRecordService 
{
    @Autowired
    private LikeBelittleRecordMapper likeBelittleRecordMapper;

    /**
     * 查询点赞点踩记录
     * 
     * @param id 点赞点踩记录ID
     * @return 点赞点踩记录
     */
    @Override
    public LikeBelittleRecord selectLikeBelittleRecordById(String id)
    {
        return likeBelittleRecordMapper.selectLikeBelittleRecordById(id);
    }

    /**
     * 查询点赞点踩记录列表
     * 
     * @param likeBelittleRecord 点赞点踩记录
     * @return 点赞点踩记录
     */
    @Override
    public List<LikeBelittleRecord> selectLikeBelittleRecordList(LikeBelittleRecord likeBelittleRecord)
    {
        return likeBelittleRecordMapper.selectLikeBelittleRecordList(likeBelittleRecord);
    }

    /**
     * 新增点赞点踩记录
     * 
     * @param likeBelittleRecord 点赞点踩记录
     * @return 结果
     */
    @Override
    public int insertLikeBelittleRecord(LikeBelittleRecord likeBelittleRecord)
    {
        likeBelittleRecord.setCreateTime(DateUtils.getNowDate());
        return likeBelittleRecordMapper.insertLikeBelittleRecord(likeBelittleRecord);
    }

    /**
     * 修改点赞点踩记录
     * 
     * @param likeBelittleRecord 点赞点踩记录
     * @return 结果
     */
    @Override
    public int updateLikeBelittleRecord(LikeBelittleRecord likeBelittleRecord)
    {
        likeBelittleRecord.setUpdateTime(DateUtils.getNowDate());
        return likeBelittleRecordMapper.updateLikeBelittleRecord(likeBelittleRecord);
    }

    /**
     * 批量删除点赞点踩记录
     * 
     * @param ids 需要删除的点赞点踩记录ID
     * @return 结果
     */
    @Override
    public int deleteLikeBelittleRecordByIds(String[] ids)
    {
        return likeBelittleRecordMapper.deleteLikeBelittleRecordByIds(ids);
    }

    /**
     * 删除点赞点踩记录信息
     * 
     * @param id 点赞点踩记录ID
     * @return 结果
     */
    @Override
    public int deleteLikeBelittleRecordById(String id)
    {
        return likeBelittleRecordMapper.deleteLikeBelittleRecordById(id);
    }
}
