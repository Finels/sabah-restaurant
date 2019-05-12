package com.fasteam.system.controller;

import com.fasteam.common.util.StringUtil;
import com.fasteam.security.auth.AppAuthentication;
import com.fasteam.security.auth.AppAuthenticationToken;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.system.configuration.AppProperties;
import com.fasteam.system.service.SysUserService;
import com.fasteam.system.CaptchaFactory;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.security.cert.X509Certificate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Administrator on 2017/9/10.
 */
@RequestMapping("/auth")
@Controller
public class AuthController {
    public static final Logger LOG = LoggerFactory.getLogger(AuthController.class);
    public static final String SECURITY_FORM_USERNAME_KEY = "username";
    public static final String SECURITY_FORM_PASSWORD_KEY = "password";
    public static final String CHECK_CODE = "checkCode";
    public static final String CHECK_MODE = "checkMode";
    public static final String NEED_CHECK_CODE = "needCheckCode";
    public static final String RETURN_URL = "returnUrl";

    @Value("${login.success.url}")
    private String loginSuccessUrl;

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private SysUserService sysUserService;
    @Value("${system.baseUrl}")
    private String baseReturnUrl;
    @Value("${ssl.url}")
    private String sslUrl;
    @Autowired
    private AppProperties appProperties;

    @RequestMapping("login")
    public String login(@RequestParam(value = "returnUrl", required = false) String returnUrl,
                        ModelMap model, HttpSession session, HttpServletRequest request) {
        model.put("username", session.getAttribute("username"));
        model.put("password", session.getAttribute("password"));
        model.put("checkCode", session.getAttribute("checkCode"));
         model.put("sslUrl", sslUrl);
        String message = (String) session.getAttribute("message");
        if (StringUtils.isNotEmpty(message)) {
            model.put("message", message);
            model.put("needCheckCode", "1");
        }
        model.put("returnUrl", returnUrl);
//        return "login" + ("wz".equals(BaseConst.ENV) ? "_" + BaseConst.ENV : "");
        return "login" + (StringUtils.isNotEmpty(appProperties.deskTheme) ? "_" + appProperties.deskTheme : "");
    }

    @RequestMapping("ptLogin")
    public String ptLogin(@RequestParam(value = "returnUrl", required = false) String returnUrl,
                          ModelMap model, HttpSession session, HttpServletRequest request) {
        model.put("username", session.getAttribute("username"));
        model.put("password", session.getAttribute("password"));
        model.put("checkCode", session.getAttribute("checkCode"));
        String message = (String) session.getAttribute("message");
        if (StringUtils.isNotEmpty(message)) {
            model.put("message", message);
            model.put("needCheckCode", "1");
        }
        model.put("returnUrl", returnUrl);
        return "login_pt";
    }

    @RequestMapping("ssdLogin")
    public ModelAndView ssdLogin(@RequestParam(value = "noCheck", required = false) String noCheck, HttpServletRequest request) {
        LOG.info("=========证书开始访问=========");
        //证书验证
        X509Certificate[] cers = (X509Certificate[]) request.getAttribute("javax.servlet.request.X509Certificate");
        if (cers == null || cers.length == 0) {
            LOG.info("=========证书无值=========");
        } else {
            //证书登录
            X509Certificate gacert = cers[0];
            String strDN = gacert.getSubjectDN().toString();
//            String strDN = "CN=何王伟 330327198708252879, OU=00, OU=02, O=02, L=00, L=03, ST=33, C=CN";
            LOG.info("=============证书有值，值为:" + strDN
            );
            //取出用户身份证
            String userIdCard = "";
            String regex15 = "([1-9]\\\\d{7}((0\\\\d)|(1[0-2]))(([0|1|2]\\\\d)|3[0-1])\\\\d{3})";
            String regex18 = "[1-9]\\d{5}[ ]?[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])[ ]?((\\d{4})|(\\d{3}[xX]))";
            Matcher m15 = Pattern.compile(regex15).matcher(strDN);
            Matcher m18 = Pattern.compile(regex18).matcher(strDN);
            if (m15.find()) {
                userIdCard = m15.group();
            } else if (m18.find()) {
                userIdCard = m18.group();
            }
            if (!StringUtil.isNullOrEmpty(userIdCard)) {
                //根据身份证查询
                LoginUser user = sysUserService.getByIdCard(userIdCard);
                if (user != null && !StringUtil.isNullOrEmpty(user.getUsername())) {
                    ModelAndView modelAndView = new ModelAndView("redirect:" + baseReturnUrl + "/auth/check");
                    modelAndView.addObject(SECURITY_FORM_USERNAME_KEY, user.getUsername());
                    modelAndView.addObject(SECURITY_FORM_PASSWORD_KEY, user.getPassword());
                    modelAndView.addObject(CHECK_MODE, "3");
                    return modelAndView;
                }
            }
        }
        ModelAndView error = new ModelAndView("redirect:" + baseReturnUrl + "/auth/login");
        error.addObject("message", "暂无系统登录权限");
        return error;
    }


    @RequestMapping("/check")
    public ModelAndView check(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        String username = request.getParameter(SECURITY_FORM_USERNAME_KEY);
        String password = request.getParameter(SECURITY_FORM_PASSWORD_KEY);
        String checkCode = request.getParameter(CHECK_CODE);
        int checkMode = new Integer(request.getParameter(CHECK_MODE));
        String needCheckCode = request.getParameter(NEED_CHECK_CODE);
        String returnUrl = request.getParameter(RETURN_URL);

        username = StringUtils.isBlank(username) ? "" : username.trim();
        if (password == null) {
            password = "";
        }
        if ("1".equals(needCheckCode) && (StringUtils.isEmpty(checkCode) || !CaptchaFactory.getInstance().validate(request, checkCode))) {
            session.setAttribute(SECURITY_FORM_USERNAME_KEY, username);
            session.setAttribute(SECURITY_FORM_PASSWORD_KEY, password);
            session.setAttribute(CHECK_CODE, checkCode);
            session.setAttribute("message", "验证码有误，请重新输入！");
            throw new AuthenticationServiceException("验证码有误，请重新输入！");
        }
        if (StringUtils.isNotBlank(username)) {
            AppAuthenticationToken appAuthenticationToken = new AppAuthenticationToken(username, password, checkMode);
            AppAuthentication appAuthentication = (AppAuthentication) authenticationManager.authenticate(appAuthenticationToken);

            String checkMessage = appAuthentication.getMessage();
            if (StringUtils.isNotBlank(checkMessage)) {
                session.setAttribute(SECURITY_FORM_USERNAME_KEY, username);
                session.setAttribute(SECURITY_FORM_PASSWORD_KEY, password);
                session.setAttribute(CHECK_CODE, checkCode);
                session.setAttribute("message", checkMessage);
                throw new AuthenticationServiceException(checkMessage);
            }
            SecurityContextHolder.getContext().setAuthentication(appAuthentication);
        }
        //登录成功跳转
        if (StringUtils.isNotEmpty(returnUrl)) {
            try {
                response.sendRedirect(returnUrl);
                return null;
            } catch (IOException e) {
                LOG.error("登录成功后页面跳转出错！", e);
            }
        }
        return new ModelAndView("redirect:" + loginSuccessUrl);
    }

    @RequestMapping("logout")
    public ModelAndView logout(HttpSession session) {
        session.invalidate();
        SecurityContextHolder.clearContext();
        //退出日志 TODO
        ModelAndView redirect = new ModelAndView("redirect:/auth/login");
        redirect.addObject("sslUrl", sslUrl);
        return redirect;
    }

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        CaptchaFactory.getInstance().writeCaptchaImage(request, response);
    }
}
