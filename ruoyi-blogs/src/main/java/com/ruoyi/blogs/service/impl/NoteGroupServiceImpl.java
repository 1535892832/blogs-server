package com.ruoyi.blogs.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import com.ruoyi.common.core.domain.TreeSelect;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.blogs.mapper.NoteGroupMapper;
import com.ruoyi.blogs.domain.NoteGroup;
import com.ruoyi.blogs.service.INoteGroupService;

/**
 * 笔记分类管理Service业务层处理
 * 
 * @author tryCatch
 * @date 2021-07-21
 */
@Service
public class NoteGroupServiceImpl implements INoteGroupService 
{
    @Autowired
    private NoteGroupMapper noteGroupMapper;

    /**
     * 查询笔记分类管理
     * 
     * @param groupId 笔记分类管理ID
     * @return 笔记分类管理
     */
    @Override
    public NoteGroup selectNoteGroupById(String groupId)
    {
        return noteGroupMapper.selectNoteGroupById(groupId);
    }

    /**
     * 查询笔记分类管理列表
     * 
     * @param noteGroup 笔记分类管理
     * @return 笔记分类管理
     */
    @Override
    public List<NoteGroup> selectNoteGroupList(NoteGroup noteGroup)
    {
        return noteGroupMapper.selectNoteGroupList(noteGroup);
    }

    @Override
    public List<NoteGroup> buildNoteGroupTree(List<NoteGroup> noteGroups) {
        List<NoteGroup> returnList = new ArrayList<NoteGroup>();
        List<Long> tempList = new ArrayList<Long>();
        for (NoteGroup noteGroup : noteGroups)
        {
            tempList.add(Long.valueOf(noteGroup.getGroupId()));
        }
        for (Iterator<NoteGroup> iterator = noteGroups.iterator(); iterator.hasNext();)
        {
            NoteGroup noteGroup =  iterator.next();
            // 如果是顶级节点, 遍历该父节点的所有子节点
            if (!tempList.contains(noteGroup.getParentId()))
            {
                recursionFn(noteGroups, noteGroup);
                returnList.add(noteGroup);
            }
        }
        if (returnList.isEmpty())
        {
            returnList = noteGroups;
        }
        return returnList;
    }



    @Override
    public List<TreeSelect> buildNoteGroupTreeSelect(List<NoteGroup> noteGroups) {
        List<NoteGroup> noteGroupsTrees = buildNoteGroupTree(noteGroups);
        return noteGroupsTrees.stream().map(v->{
            TreeSelect treeSelect = new TreeSelect();
            treeSelect.setId(Long.valueOf(v.getGroupId()));
            treeSelect.setLabel(v.getGroupName());
            List<TreeSelect> children = v.getChildren().stream().map(v1 -> {
                NoteGroup noteGroup = (NoteGroup) v1;
                TreeSelect cTreeSelect = new TreeSelect();
                cTreeSelect.setId(Long.valueOf(noteGroup.getGroupId()));
                cTreeSelect.setLabel(noteGroup.getGroupName());
                return cTreeSelect;
            }).collect(Collectors.toList());
            treeSelect.setChildren(children);
            return treeSelect;
        }).collect(Collectors.toList());
    }


    /**
     * 递归列表
     */
    private void recursionFn(List<NoteGroup> list, NoteGroup noteGroup)
    {
        // 得到子节点列表
        List<NoteGroup> childList = getChildList(list, noteGroup);
        noteGroup.setChildren(childList);
        for (NoteGroup nChild : childList)
        {
            if (hasChild(list, nChild))
            {
                recursionFn(list, nChild);
            }
        }
    }


    /**
     * 得到子节点列表
     */
    private List<NoteGroup> getChildList(List<NoteGroup> list, NoteGroup t)
    {
        List<NoteGroup> tlist = new ArrayList<>();
        Iterator<NoteGroup> it = list.iterator();
        while (it.hasNext())
        {
            NoteGroup n =  it.next();
            if (StringUtils.isNotNull(n.getParentId()) && n.getParentId().longValue() == Long.valueOf(t.getGroupId()))
            {
                tlist.add(n);
            }
        }
        return tlist;
    }

    /**
     * 判断是否有子节点
     */
    private boolean hasChild(List<NoteGroup> list, NoteGroup t)
    {
        return getChildList(list, t).size() > 0 ? true : false;
    }

    /**
     * 新增笔记分类管理
     * 
     * @param noteGroup 笔记分类管理
     * @return 结果
     */
    @Override
    public int insertNoteGroup(NoteGroup noteGroup)
    {
        noteGroup.setCreateTime(DateUtils.getNowDate());
        return noteGroupMapper.insertNoteGroup(noteGroup);
    }

    /**
     * 修改笔记分类管理
     * 
     * @param noteGroup 笔记分类管理
     * @return 结果
     */
    @Override
    public int updateNoteGroup(NoteGroup noteGroup)
    {
        noteGroup.setUpdateTime(DateUtils.getNowDate());
        return noteGroupMapper.updateNoteGroup(noteGroup);
    }

    /**
     * 批量删除笔记分类管理
     * 
     * @param groupIds 需要删除的笔记分类管理ID
     * @return 结果
     */
    @Override
    public int deleteNoteGroupByIds(String[] groupIds)
    {
        return noteGroupMapper.deleteNoteGroupByIds(groupIds);
    }

    /**
     * 删除笔记分类管理信息
     * 
     * @param groupId 笔记分类管理ID
     * @return 结果
     */
    @Override
    public int deleteNoteGroupById(String groupId)
    {
        return noteGroupMapper.deleteNoteGroupById(groupId);
    }
}
