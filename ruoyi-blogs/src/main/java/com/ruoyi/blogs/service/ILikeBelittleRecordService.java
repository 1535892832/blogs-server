package com.ruoyi.blogs.service;

import java.util.List;
import com.ruoyi.blogs.domain.LikeBelittleRecord;

/**
 * 点赞点踩记录Service接口
 * 
 * @author tryCatch
 * @date 2021-08-16
 */
public interface ILikeBelittleRecordService 
{
    /**
     * 查询点赞点踩记录
     * 
     * @param id 点赞点踩记录ID
     * @return 点赞点踩记录
     */
    public LikeBelittleRecord selectLikeBelittleRecordById(String id);

    /**
     * 查询点赞点踩记录列表
     * 
     * @param likeBelittleRecord 点赞点踩记录
     * @return 点赞点踩记录集合
     */
    public List<LikeBelittleRecord> selectLikeBelittleRecordList(LikeBelittleRecord likeBelittleRecord);

    /**
     * 新增点赞点踩记录
     * 
     * @param likeBelittleRecord 点赞点踩记录
     * @return 结果
     */
    public int insertLikeBelittleRecord(LikeBelittleRecord likeBelittleRecord);

    /**
     * 修改点赞点踩记录
     * 
     * @param likeBelittleRecord 点赞点踩记录
     * @return 结果
     */
    public int updateLikeBelittleRecord(LikeBelittleRecord likeBelittleRecord);

    /**
     * 批量删除点赞点踩记录
     * 
     * @param ids 需要删除的点赞点踩记录ID
     * @return 结果
     */
    public int deleteLikeBelittleRecordByIds(String[] ids);

    /**
     * 删除点赞点踩记录信息
     * 
     * @param id 点赞点踩记录ID
     * @return 结果
     */
    public int deleteLikeBelittleRecordById(String id);
}
