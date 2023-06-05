package cc.itez.nfs.net.models;

import lombok.Data;

import java.util.Date;

@Data
public class BaseModel {
    /**
     * ID
     */
    private Long id;
    /**
     * 备注信息
     */
    private String remark;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
}
