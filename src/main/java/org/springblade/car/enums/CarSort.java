package org.springblade.car.enums;

/**
 * @Auther: wangqiaoqing
 * @Date: 2018/7/30
 * @Description:
 */
public enum CarSort {
    //排序
    TIME(1,"t.listtime desc"),SPRICE(2,"pprice asc"),EPRICE(3,"pprice desc"),
    ;

    public Integer id;
    public String value;

    CarSort(Integer id, String value) {
        this.id = id;
        this.value = value;
    }
    /**
     * 获取对应属性值
     * @param id
     * @return
     */
    public static String getValue(Integer id){
        CarSort[] alarmLevels = CarSort.values();
        for (CarSort alarmLevel : alarmLevels) {
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
    public static CarSort getRoleType(Integer id){
        CarSort[] alarmLevels = CarSort.values();
        for (CarSort alarmLevel : alarmLevels) {
            if(id.intValue() == alarmLevel.id.intValue()){
                return alarmLevel;
            }
        }
        return null;
    }
}
