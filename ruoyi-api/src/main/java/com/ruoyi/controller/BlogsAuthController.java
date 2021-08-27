package com.ruoyi.controller;

import com.ruoyi.blogs.domain.Comment;
import com.ruoyi.blogs.domain.LikeBelittleRecord;
import com.ruoyi.blogs.domain.Note;
import com.ruoyi.blogs.service.ICommentService;
import com.ruoyi.blogs.service.ILikeBelittleRecordService;
import com.ruoyi.blogs.service.INoteService;
import com.ruoyi.blogs.utils.SensitiveUtils;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * @author hubi
 * @date 2021/8/1
 */
@Api("博客认证Api")
@RequestMapping("/blogs/auth")
@RestController
@CrossOrigin
public class BlogsAuthController extends BaseController {

    @Autowired
    private INoteService noteService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private SensitiveUtils sensitiveUtils;

    @Autowired
    private ILikeBelittleRecordService likeBelittleRecordService;

    @ApiOperation("评论")
    @PostMapping("/comment")
    public AjaxResult comment(@Validated Comment comment){
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        String userName = SecurityUtils.getUsername();
        // 判断是覅包含敏感词
        Integer hasSensitiveWord = sensitiveUtils.hasSensitiveWord(comment.getContent()) ? 1 : 0;
        comment.setHasSensitiveWord(hasSensitiveWord);
        comment.setUserId(userId);
        comment.setUserName(userName);
        comment.setDelFlag(Comment.DEL_FLAG_NO);
        comment.setCreateTime(DateUtils.getNowDate());
        return AjaxResult.success();
    }


    @ApiOperation("删除评论")
    @DeleteMapping("/comment/{id}")
    public AjaxResult comment(@PathVariable Integer id){
        Comment comment = commentService.selectCommentById(id);
        if(comment == null){
            return AjaxResult.success();
        }
        // 只有博客的发布者 和 评论者才能删除评论
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();

        // 本人删除的情况
        if(userId == comment.getUserId()){
            comment.setDelFlag(Comment.DEL_FLAG_YES);
            comment.setDelUserName(SecurityUtils.getUsername());
            comment.setDelTime(DateUtils.getNowDate());
            return toAjax(commentService.updateComment(comment));
        }

        // 博客管理员删除的情况
        Note note = noteService.selectNoteById(comment.getNoteId());
        if(note == null){
            return AjaxResult.error("评论删除失败 文章已被删除");
        }

        if(userId == note.getAuthorId()){
            comment.setDelFlag(Comment.DEL_FLAG_YES);
            comment.setDelUserName(note.getUserName());
            comment.setDelTime(DateUtils.getNowDate());
            return toAjax(commentService.updateComment(comment));
        }

        return AjaxResult.error("评论删除失败");
    }

    @ApiOperation("点赞")
    @PostMapping("/like/{noteId}")
    @Transactional
    public AjaxResult like(@PathVariable String noteId){
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        LikeBelittleRecord params = new LikeBelittleRecord();
        params.setOpType(LikeBelittleRecord.OP_LICK);
        params.setUserId(userId);
        params.setNoteId(noteId);
        List<LikeBelittleRecord> likeBelittleRecords = likeBelittleRecordService.selectLikeBelittleRecordList(params);
        if(likeBelittleRecords.isEmpty()){
            likeBelittleRecordService.insertLikeBelittleRecord(params);
            Note note = noteService.selectNoteById(noteId);
            long likeNum =  note.getLikeNum().longValue() + 1L;
            note.setLikeNum(likeNum);
            noteService.updateNote(note);
        }
        return AjaxResult.success();
    }

    @ApiOperation("撤销点赞")
    @PostMapping("/unLike/{noteId}")
    public  AjaxResult unLike(@PathVariable String noteId){
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        LikeBelittleRecord params = new LikeBelittleRecord();
        params.setOpType(LikeBelittleRecord.OP_LICK);
        params.setUserId(userId);
        params.setNoteId(noteId);
        List<LikeBelittleRecord> likeBelittleRecords = likeBelittleRecordService.selectLikeBelittleRecordList(params);
        if(!likeBelittleRecords.isEmpty()){
            LikeBelittleRecord likeBelittleRecord = likeBelittleRecords.get(0);
            likeBelittleRecordService.deleteLikeBelittleRecordById(likeBelittleRecord.getId());
            Note note = noteService.selectNoteById(noteId);
            long likeNum =  note.getLikeNum().longValue() - 1L;
            note.setLikeNum(likeNum);
            noteService.updateNote(note);
        }
        return AjaxResult.success();
    }

    @ApiOperation("点踩")
    @PostMapping("/belittle/{noteId}")
    public AjaxResult belittle(@PathVariable String noteId){
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        LikeBelittleRecord params = new LikeBelittleRecord();
        params.setOpType(LikeBelittleRecord.OP_BELITTLE);
        params.setUserId(userId);
        params.setNoteId(noteId);
        List<LikeBelittleRecord> likeBelittleRecords = likeBelittleRecordService.selectLikeBelittleRecordList(params);
        if(likeBelittleRecords.isEmpty()){
            likeBelittleRecordService.insertLikeBelittleRecord(params);
            Note note = noteService.selectNoteById(noteId);
            long belittleNum =  note.getBelittleNum().longValue() + 1L;
            note.setBelittleNum(belittleNum);
            noteService.updateNote(note);
        }
        return AjaxResult.success();
    }



    @ApiOperation("撤销点踩")
    @PostMapping("/unBelittle/{noteId}")
    public  AjaxResult unBelittle(@PathVariable String noteId){
        Long userId = SecurityUtils.getLoginUser().getUser().getUserId();
        LikeBelittleRecord params = new LikeBelittleRecord();
        params.setOpType(LikeBelittleRecord.OP_BELITTLE);
        params.setUserId(userId);
        params.setNoteId(noteId);
        List<LikeBelittleRecord> likeBelittleRecords = likeBelittleRecordService.selectLikeBelittleRecordList(params);
        if(!likeBelittleRecords.isEmpty()){
            LikeBelittleRecord likeBelittleRecord = likeBelittleRecords.get(0);
            likeBelittleRecordService.deleteLikeBelittleRecordById(likeBelittleRecord.getId());
            Note note = noteService.selectNoteById(noteId);
            long belittleNum =  note.getBelittleNum().longValue() - 1L;
            note.setBelittleNum(belittleNum);
            noteService.updateNote(note);
        }
        return AjaxResult.success();
    }



}
