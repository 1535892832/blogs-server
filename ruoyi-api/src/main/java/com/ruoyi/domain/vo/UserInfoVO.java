package com.ruoyi.domain.vo;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.entity.SysUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * @author hubi
 * @date 2021/7/26
 */
@Data
@NoArgsConstructor
public class UserInfoVO {

    @Excel(name = "登录名称")
    private String userName;

    /** 用户昵称 */
    @Excel(name = "用户名称")
    private String nickName;

    @Excel(name = "用户性别", readConverterExp = "0=男,1=女,2=未知")
    private String sex;

    /** 用户头像 */
    private String avatar;

    public UserInfoVO(SysUser sysUser){
        BeanUtils.copyProperties(sysUser,this);
    }

}
