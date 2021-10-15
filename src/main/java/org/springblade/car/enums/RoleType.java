package org.springblade.car.enums;

/**
 * @Auther: wangqiaoqing
 * @Date: 2018/7/30
 * @Description:
 */
public enum RoleType {
    VISITOR(1,"游客"),MEMBER(2,"个人"),MERCHANT(3,"商家");

    public Integer id;
    public String value;

    RoleType(Integer id, String value) {
        this.id = id;
        this.value = value;
    }
    /**
     * 获取对应属性值
     * @param id
     * @return
     */
    public static String getValue(Integer id){
        RoleType[] alarmLevels = RoleType.values();
        for (RoleType alarmLevel : alarmLevels) {
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
    public static RoleType getRoleType(Integer id){
        RoleType[] alarmLevels = RoleType.values();
        for (RoleType alarmLevel : alarmLevels) {
            if(id.intValue() == alarmLevel.id.intValue()){
                return alarmLevel;
            }
        }
        return null;
    }
}
