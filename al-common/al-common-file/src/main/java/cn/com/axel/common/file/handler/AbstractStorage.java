package cn.com.axel.common.file.handler;


import cn.com.axel.common.core.exception.MyRuntimeException;
import cn.com.axel.common.core.utils.FileUtils;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @description: 抽象缓存处理类
 * @author: axel
 * @date: 2023/3/14 10:24
 */
public abstract class AbstractStorage implements Storage {
    /**
     * 接口地址
     */
    private final String address;

    public AbstractStorage(String address) {
        this.address = address;
    }

    /**
     * 获取本地服务端文件访问路径（父类为其他路径时可重写）
     *
     * @param filePath  文件全路径
     * @param isPrivate 是否私有
     * @return 返回文件访问路径
     */
    @Override
    public String buildUrl(String filePath, Integer isPrivate) {
        int index = filePath.lastIndexOf("/");
        String keyName;
        if (index > 0) {
            keyName = filePath.substring(index + 1);
        } else {
            keyName = filePath;
        }
        return FileUtils.formatFilePath(address) + "/" + keyName;
    }

    @Override
    public InputStream getInputStream(String filePath) {
        try {
            Resource resource = loadAsResource(filePath);
            return resource.getInputStream();
        } catch (IOException e) {
            throw new MyRuntimeException("错误：获取文件流异常", e);
        }
    }

    @Override
    public File getFile(String filePath) {
        Resource resource = loadAsResource(filePath);
        try {
            return resource.getFile();
        } catch (IOException e) {
            throw new MyRuntimeException("错误：获取文件异常", e);
        }
    }
}
