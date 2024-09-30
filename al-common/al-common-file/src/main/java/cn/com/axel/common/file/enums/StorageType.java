package cn.com.axel.common.file.enums;

/**
 * @description: 缓存类型
 * @author: axel
 * @date: 2023/1/5 16:47
 */
public enum StorageType {
    本地("local"),
    阿里云("aliyun"),
    七牛云("qiniu");
    private final String value;

    StorageType(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }

    public static StorageType getStorageType(String value) {
        for (StorageType type : StorageType.values()) {
            if (type.toString().equalsIgnoreCase(value)) {
                return type;
            }
        }
        return StorageType.本地;
    }
}
