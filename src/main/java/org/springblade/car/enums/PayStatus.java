package org.springblade.car.enums;

/**
 * @Auther: wangqiaoqing
 * @Date: 2018/7/30
 * @Description:
 */
public enum PayStatus {
    //1已支付，2支付完成未填写资料，3资料完成提交,4会员到期需要重缴费
    PAYING(1,"待支付(支付失败)"),FILL(2,"支付完成未填写资料"),FILLED(3,"资料完成提交"),
    REPAY(4,"会员到期需要重缴费");

    public Integer id;
    public String value;

    PayStatus(Integer id, String value) {
        this.id = id;
        this.value = value;
    }
    /**
     * 获取对应属性值
     * @param id
     * @return
     */
    public static String getValue(Integer id){
        PayStatus[] alarmLevels = PayStatus.values();
        for (PayStatus alarmLevel : alarmLevels) {
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
    public static PayStatus getRoleType(Integer id){
        PayStatus[] alarmLevels = PayStatus.values();
        for (PayStatus alarmLevel : alarmLevels) {
            if(id.intValue() == alarmLevel.id.intValue()){
                return alarmLevel;
            }
        }
        return null;
    }
}
