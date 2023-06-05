package cc.itez.nfs.net.enums.models;

public enum BaseStatus {
    /**
     * 启用
     */
    ENABLED(1),
    /**
     * 禁用
     */
    DISABLED(0),
    /**
     * 删除
     */
    DELETED(-1);
    public final int val;

    BaseStatus(int val) {
        this.val = val;
    }
}
