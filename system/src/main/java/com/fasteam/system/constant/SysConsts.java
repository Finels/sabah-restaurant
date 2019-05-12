package com.fasteam.system.constant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServlet;
import java.io.File;

/**
 * 系统常量
 */
public final class SysConsts extends HttpServlet {
    private SysConsts() {

    }

    private static final Logger LOG = LoggerFactory.getLogger(SysConsts.class);

    public static final String TOP_LOGO = "topLogo";
    public static final String TOP_LOGO_NAME = "%s_top%s";        //groupCode_top.源图片后缀

    public static final String GROUP_TOP_CODE = "NI";           //顶级用户组编码net image
    public static final String GROUP_CODE_SEPARATOR = "_";      // 用户组编码的分隔符
    public static final String GROUP_CODE_FORMAT = "%s" + GROUP_CODE_SEPARATOR + "%s"; //用户组code的格式 "上级组code_本级id"

    public static final String RESOURCE_DEFAULT_FATHER_ID = "0";     //默认的资源父ID(顶级ID)

    public static final String SNAP_FILE_PATH = "%s" + File.separatorChar + "%s" + File.separatorChar + "%s.html";  //任务线索快照路径

//    public void init() throws ServletException {
//        initSysParam();
    // 读取系统序列号用于分布式@2013-9-27
//        getSn();
//        mkLink();
//    }

}
