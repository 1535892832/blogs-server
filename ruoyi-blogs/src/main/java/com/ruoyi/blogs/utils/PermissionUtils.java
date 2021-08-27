package com.ruoyi.blogs.utils;

import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.utils.StringUtils;

import java.util.Collection;

/**
 * @author hubi
 * @date 2021/8/8
 * 权限工具类
 */
public class PermissionUtils {

    /**
     * 判断用户是否拥有指定权限
     * @param permissions
     * @param permission
     * @return
     */
    public static boolean hasPermission(Collection<String> permissions,String permission){
        if(StringUtils.isEmpty(permissions) || StringUtils.isBlank(permission)){
          return false;
        }
        return permissions.stream().anyMatch(v-> v.equals(UserConstants.PERMISSION_ALL) || v.equals(permission));
    }

}
