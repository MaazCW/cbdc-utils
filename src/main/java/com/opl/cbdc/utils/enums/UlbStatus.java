package com.opl.cbdc.utils.enums;

public enum UlbStatus {
    ULB_ONLINE(1, "Online"),
    ULB_OFFLINE(2, "Offline"),
    ULB_REJECT(3, "Reject"),
    ULB_RECOMMENDED(4, "Recommended"),
    ULB_HOLD(5, "Hold"),
    PENDING_FOR_ULB(12,"Pending Form Ulb");

    private Integer id;
    private String value;

    private UlbStatus(Integer id, String value) {
        this.id = id;
        this.value = value;
    }

    public Integer getId() {
        return this.id;
    }

    public String getValue() {
        return this.value;
    }

    public static UlbStatus fromId(Integer v) {
        UlbStatus[] var1 = values();
        int var2 = var1.length;

        for (int var3 = 0; var3 < var2; ++var3) {
            UlbStatus c = var1[var3];
            if (c.id.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v != null ? v.toString() : null);
    }

    public static UlbStatus[] getAll() {
        return values();
    }
}
