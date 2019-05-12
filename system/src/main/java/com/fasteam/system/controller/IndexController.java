package com.fasteam.system.controller;

import com.fasteam.common.constant.BaseConst;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.system.configuration.AppProperties;
import com.fasteam.system.dao.SysUserDao;
import com.fasteam.system.domain.Message;
import com.fasteam.system.domain.SysUser;
import com.fasteam.system.service.MessageService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
@Controller
@RequestMapping("/")
public class IndexController {

    @RequestMapping
    public ModelAndView index() {
        return new ModelAndView("redirect:/index");
    }

    @Autowired
    private SysUserDao sysUserDao;
    @Autowired
    private MessageService messageService;
    @Autowired
    private AppProperties appProperties;

    @RequestMapping("/index")
    public String index(Model model, HttpServletRequest request) {
        LoginUser loginUser = AuthUtil.getUser(request);
        model.addAttribute("systemResources", loginUser.getSystemResourceList());
        model.addAttribute("topResources", loginUser.getTopResourceList());
        model.addAttribute("username", AuthUtil.getName());
        SysUser sysUser = sysUserDao.getById(loginUser.getId());
        if (StringUtils.isNotBlank(sysUser.getName())
                && StringUtils.isNotBlank(sysUser.getSex())
                && StringUtils.isNotBlank(sysUser.getIdCard())
                && StringUtils.isNotBlank(sysUser.getTelephone())) {
            model.addAttribute("openWriteUserInfo", "onOpen");
        }
        model.addAttribute("user", sysUser);

        //是否有未读的消息
        model.addAttribute("countNoReadByUser", messageService.countNoReadByUser(loginUser.getId()));
        //前10条消息
        List<Message> messageList = messageService.listTop(10);
        model.addAttribute("messageList", messageList);
        model.addAttribute("env", BaseConst.ENV);
        if ("wz".equals(appProperties.deskTheme)) {
            return "index_wz";
        } else if ("win".equals(appProperties.deskTheme)) {
            return "index_win";
        } else if ("yc".equals(appProperties.deskTheme)) {
            return "index_yc";
        } else if ("orgin".equals(appProperties.deskTheme)) {
            return "index";
        } else {
            return "index_win";
        }
    }
}
