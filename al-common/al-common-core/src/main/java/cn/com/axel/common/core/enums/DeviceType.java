package cn.com.axel.common.core.enums;

/**
 * @author: axel
 * @description: 设备类型
 * @date: 2022/12/2 18:19
 */
public enum DeviceType {
    //浏览器
    Web("0"),
    //手机端
    APP("1"),
    //微信
    WX("2");
    private final String value;

    DeviceType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}