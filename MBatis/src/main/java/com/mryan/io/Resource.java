package com.mryan.io;

import java.io.InputStream;

/**
 * @description： 读取配置文件
 * @Author MRyan
 * @Date 2021/7/27 22:37
 * @Version 1.0
 */
public class Resource {

    /**
     * 根据路径读取配置文件到输入流
     *
     * @param path
     * @return
     */
    public static InputStream getResourceAsSteam(String path) {
        return Resource.class.getClassLoader().getResourceAsStream(path);
    }
}
