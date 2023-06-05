package cc.itez.nfs.net.models.sys;

import cc.itez.nfs.net.models.BaseModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * 数据库模型 -> 用户
 */
@Data
@Accessors(chain = true)
public class User extends BaseModel {
    /**
     * 昵称
     */
    private String name;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;
    /**
     * 盐值
     */
    private String salt;
    /**
     * 用户头像
     */
    private String portrait;
    /**
     * 上次登录时间
     */
    private Date lastLoginTime;
    /**
     * 角色
     */
    private List<Role> roles;
}
