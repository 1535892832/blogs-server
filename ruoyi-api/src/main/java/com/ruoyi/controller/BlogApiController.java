package com.ruoyi.controller;

import com.ruoyi.blogs.domain.Banner;
import com.ruoyi.blogs.domain.Comment;
import com.ruoyi.blogs.domain.Note;
import com.ruoyi.blogs.service.IBannerService;
import com.ruoyi.blogs.service.ICommentService;
import com.ruoyi.blogs.service.INoteService;
import com.ruoyi.blogs.utils.PermissionUtils;
import com.ruoyi.blogs.utils.SensitiveUtils;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.UserStatus;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.domain.vo.UserInfoVO;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.system.service.ISysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author hubi
 * @date 2021/8/15
 */
@Api("博客开放Api")
@CrossOrigin
@RestController
@RequestMapping("/blogs/anon")
public class BlogApiController extends BaseController {

    @Autowired
    private ISysUserService userService;

    @Autowired
    private IBannerService bannerService;

    @Autowired
    private ISysUserService sysUserService;

    @Autowired
    private INoteService noteService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ICommentService commentService;

    @Autowired
    private SensitiveUtils sensitiveUtils;


    @Autowired
    private ThreadPoolTaskExecutor threadPoolTaskExecutor;


    @ApiOperation("获取用户信息")
    @GetMapping("/{username}/user")
    public AjaxResult index(@PathVariable String username) {
        SysUser user = userService.selectUserByUserName(username);
        if (user == null) {
            return AjaxResult.error("用户不存在");
        }
        if (UserStatus.DELETED.getCode().equals(user.getDelFlag())) {
            // 用户已被删除
            return AjaxResult.error("用户不存在");
        }
        if (UserStatus.DISABLE.getCode().equals(user.getStatus())) {
            // 用户已被封号
            return AjaxResult.error("该账号已被封禁");
        }
        UserInfoVO userInfo = new UserInfoVO(user);
        return AjaxResult.success(userInfo);
    }


    @ApiOperation("获取banner图")
    @GetMapping("/{username}/banner")
    public AjaxResult banner(@PathVariable String username) {
        Banner banner = new Banner();
        banner.setUserName(username);
        List<Banner> banners = bannerService.selectBannerList(banner);
        return AjaxResult.success(banners);
    }

    @ApiOperation("推荐博主")
    @GetMapping("/recommend/user")
    public TableDataInfo recommendUser() {
        startPage();
        List<SysUser> sysUserList = sysUserService.selectUserList(new SysUser());
        List<UserInfoVO> recommendUserList = sysUserList.stream().map(UserInfoVO::new).collect(Collectors.toList());
        return getDataTable(recommendUserList);
    }

    @ApiOperation("博客列表")
    @GetMapping("/{username}/blogsList")
    public TableDataInfo blogsList(@PathVariable String username) {
        SysUser sysUser = sysUserService.selectUserByUserName(username);
        if (sysUser == null) {
            return new TableDataInfo();
        }
        // 如果是官方博客返回所有用户数据
        Set<String> menuPermission = permissionService.getMenuPermission(sysUser);
        Note note = new Note();
        if (!PermissionUtils.hasPermission(menuPermission, UserConstants.PERMISSION_NOTE_LIST_ALL)) {
            note.setUserName(SecurityUtils.getUsername());
        }
        startPage();
        List<Note> notes = noteService.selectNoteSimpleList(note);
        return getDataTable(notes);
    }

    @ApiOperation("首页推荐")
    @GetMapping("/blogsList/recommend")
    public TableDataInfo blogsListOfficialRecommend() {
        startPage();
        List<Note> notes = noteService.selectNoteOfficialRecommend(new Note());
        return getDataTable(notes);
    }

    @ApiOperation("首页最新")
    @GetMapping("/blogsList/newest")
    public TableDataInfo blogsListNewest() {
        startPage();
        List<Note> notes = noteService.selectNoteNewest(new Note());
        return getDataTable(notes);
    }


    @ApiOperation("博客详情")
    @GetMapping("/{username}/blogsDetail/{noteId}")
    public AjaxResult noteDetail(@PathVariable String username, @PathVariable String noteId) {
        SysUser sysUser = sysUserService.selectUserByUserName(username);
        if (sysUser == null) {
            return AjaxResult.success("博客不存在");
        }

        // 查询评论
        Note note = noteService.selectNoteById(noteId);
        Comment params = new Comment();
        params.setDelFlag(Comment.DEL_FLAG_NO);
        params.setNoteId(note.getId());
        List<Comment> comments = commentService.selectCommentList(params);
        // 敏感词过滤处理
        comments = sensitiveUtils.desensitization(comments);

        // TODO 此处可以使用Redis做缓存
        threadPoolTaskExecutor.execute(() -> {
            // 更新浏览量
            note.setPageViewNum(note.getPageViewNum() + 1L);
            noteService.updateNote(note);
        });

        UserInfoVO userInfo = new UserInfoVO(sysUser);
        AjaxResult success = AjaxResult.success();
        success.put("user", userInfo);
        success.put("note", note);
        success.put("comments", comments);
        return success;
    }

}
