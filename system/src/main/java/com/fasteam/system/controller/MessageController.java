package com.fasteam.system.controller;

import com.fasteam.common.dto.ResponseWrapper;
import com.fasteam.common.util.ResponseBuilder;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.system.service.MessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 消息提醒
 * Created by Administrator on 2017/11/2.
 */
@Controller
@RequestMapping("/message")
public class MessageController {
    private static final Logger LOGGER = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private MessageService messageService;


    /**
     * 全部标记已读
     */
    @RequestMapping(value = "/allPurpose", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper allPurpose(HttpServletRequest request) {
        try {
            messageService.updateReadUserIds(AuthUtil.getId(request));
            return ResponseBuilder.ok();
        } catch (Exception e) {
            LOGGER.error("全部标记已读失败", e);
            return ResponseBuilder.error("全部标为已读失败！");
        }
    }

}
