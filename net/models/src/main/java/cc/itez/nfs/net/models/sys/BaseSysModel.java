package cc.itez.nfs.net.models.sys;

import cc.itez.nfs.net.models.BaseModel;
import lombok.Data;

@Data
public class BaseSysModel extends BaseModel {
    /**
     * 状态
     * @see cc.itez.nfs.net.enums.models.BaseStatus
     */
    private int status;
}
