package com.fasteam.common.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;

/**
 * Description:  com.fasteam.common.util
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
public final class SecurityUtil {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityUtil.class);
    private static MessageDigest messageDigest;

    private SecurityUtil() {
    }

    public static synchronized String md5(String str) {
        if (StringUtils.isBlank(str)) {
            return null;
        } else {
            messageDigest.update(str.getBytes());
            String result = byteArrayToHexString(messageDigest.digest());
            messageDigest.reset();
            return result;
        }
    }

    public static synchronized String getFileMD5String(File file) throws IOException {
        FileInputStream in = new FileInputStream(file);
        FileChannel ch = in.getChannel();
        MappedByteBuffer byteBuffer = ch.map(FileChannel.MapMode.READ_ONLY, 0L, file.length());
        messageDigest.update(byteBuffer);
        String result = byteArrayToHexString(messageDigest.digest());
        messageDigest.reset();
        return result;
    }

    public static synchronized String getFileMD5String(MultipartFile file) throws IOException {
        messageDigest.update(file.getBytes());
        String result = byteArrayToHexString(messageDigest.digest());
        messageDigest.reset();
        return result;
    }

    private static String byteArrayToHexString(byte[] in) {
        int i = 0;
        if (in != null && in.length > 0) {
            String[] pseudo = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B", "C", "D", "E", "F"};

            StringBuffer out;
            for(out = new StringBuffer(in.length * 2); i < in.length; ++i) {
                byte var6 = (byte)(in[i] & 240);
                var6 = (byte)(var6 >>> 4);
                var6 = (byte)(var6 & 15);
                out.append(pseudo[var6]);
                var6 = (byte)(in[i] & 15);
                out.append(pseudo[var6]);
            }

            return out.toString();
        } else {
            return null;
        }
    }

    static {
        try {
            messageDigest = MessageDigest.getInstance("MD5");
        } catch (Exception var1) {
            LOG.error("MD5 Digest init failed", var1);
        }

    }
}
