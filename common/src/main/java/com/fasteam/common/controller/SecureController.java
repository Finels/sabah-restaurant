package com.fasteam.common.controller;

import com.fasteam.common.util.SecureStringEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

/**
 * Description:  com.fasteam.common.controller
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/3/24
 */
@Controller
public abstract class SecureController {
    public SecureController() {
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new SecureStringEditor(true, true, true));
    }
}
