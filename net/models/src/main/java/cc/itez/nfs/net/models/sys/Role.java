package cc.itez.nfs.net.models.sys;

import cc.itez.nfs.net.models.BaseModel;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * 数据库模型 -> 角色
 */
@Data
@Accessors(chain = true)
public class Role extends BaseModel {
    /**
     * 角色名
     */
    private String name;
    /**
     * 关联菜单表
     */
    private List<Menu> menus;
}
