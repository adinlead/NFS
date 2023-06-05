package cc.itez.nfs.net.models.sys;

import cc.itez.nfs.net.models.BaseModel;

/**
 * 数据库模型 -> 菜单
 */
public class Menu extends BaseModel {
    /**
     * 菜单名
     */
    public String name;
    /**
     * 菜单路径
     */
    public String path;
    /**
     * 菜单权限
     * 权限管理应当基于菜单，可以使用`/*`做单路径匹配或者`/**`做多路径匹配
     */
    public String permit;
    /**
     * 父级菜单
     */
    public Menu parent;
}
