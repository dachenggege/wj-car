package org.springblade.car.enums;

/**
 * @Auther: wangqiaoqing
 * @Date: 2018/7/30
 * @Description:
 */
public enum AuditStatus {
    //会员审核状态1审核中,2审核通过，3审核不通过
    AUDITING(1,"审核中"),PASS(2,"审核通过"),NOPASS(3,"审核不通过");

    public Integer id;
    public String value;

    AuditStatus(Integer id, String value) {
        this.id = id;
        this.value = value;
    }
    /**
     * 获取对应属性值
     * @param id
     * @return
     */
    public static String getValue(Integer id){
        AuditStatus[] alarmLevels = AuditStatus.values();
        for (AuditStatus alarmLevel : alarmLevels) {
            if(id.intValue() == alarmLevel.id.intValue()){
                return alarmLevel.value;
            }
        }
        return "";
    }

    /**
     * 获取对应属性值
     * @param id
     * @return
     */
    public static AuditStatus getRoleType(Integer id){
        AuditStatus[] alarmLevels = AuditStatus.values();
        for (AuditStatus alarmLevel : alarmLevels) {
            if(id.intValue() == alarmLevel.id.intValue()){
                return alarmLevel;
            }
        }
        return null;
    }
}
