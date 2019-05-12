package com.fasteam.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasteam.common.constant.ResponseCodeEnum;
import com.fasteam.common.controller.SecureController;
import com.fasteam.common.dto.ResponseWrapper;
import com.fasteam.common.orm.dto.Pagination;
import com.fasteam.common.util.ResponseBuilder;
import com.fasteam.common.util.SecurityUtil;
import com.fasteam.common.util.StringUtil;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.system.constant.SysConsts;
import com.fasteam.system.domain.SysRole;
import com.fasteam.system.domain.SysUser;
import com.fasteam.system.query.SysUserQuery;
import com.fasteam.system.service.SysRoleService;
import com.fasteam.system.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 系统用户
 */
@RequestMapping("sysUser")
@Controller
public class SysUserController extends SecureController {
    private static final Logger LOGGER = LoggerFactory.getLogger(SysUserController.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;

    @RequestMapping(value = "/main", method = {RequestMethod.GET, RequestMethod.POST})
    public String main(SysUserQuery sysUserQuery,
                       @RequestParam(value = "_secondMenu", required = false) String _secondMenu,
                       @RequestParam(value = "pageNo", defaultValue = Pagination.DEFAULT_PAGE_NO) int pageNo,
                       @RequestParam(value = "pageSize", defaultValue = Pagination.DEFAULT_PAGE_SIZE) int pageSize,
                       HttpServletRequest request, Model model) {
        LoginUser loginUser = AuthUtil.getUser(request);
        if (AuthUtil.isUser(loginUser)) { //普通用户只能看自己
            sysUserQuery.setId(loginUser.getId());
        } else {
            if (StringUtils.isBlank(sysUserQuery.getGroupCode())) {
                sysUserQuery.setGroupCode(loginUser.getGroupCode());
            }
//            if (loginUser.getDataArea().contains(DataArea.L.getCode())) {
//                sysUserRequest.setDataArea(DataArea.L.getCode());
//            }
            List<SysRole> roleList = sysRoleService.listByGroupCode(loginUser.getGroupCode());
            model.addAttribute("roleList", roleList);
        }
        if (SysConsts.GROUP_TOP_CODE.equals(sysUserQuery.getSelectGroupCode())) {
            sysUserQuery.setSelectGroupCode(null);
        }
        Pagination pagination = sysUserService.listByPage(sysUserQuery, pageNo, pageSize);
        model.addAttribute("page", pagination);
        model.addAttribute("sysUserQuery", sysUserQuery);
        if (StringUtils.isNotEmpty(_secondMenu)) {
            model.addAttribute("systemMenu", _secondMenu);
        } else {
            model.addAttribute("systemMenu", "sysUser");
        }

        return "user/user-main";
    }

    @RequestMapping(value = "/list", method = {RequestMethod.GET, RequestMethod.POST})
    public String list(SysUserQuery sysUserQuery,
                       @RequestParam(value = "pageNo", defaultValue = Pagination.DEFAULT_PAGE_NO) int pageNo,
                       @RequestParam(value = "pageSize", defaultValue = Pagination.DEFAULT_PAGE_SIZE) int pageSize,
                       Model model) {
//        LoginUser loginUser = AuthUtil.getUser(request);
//        if (AuthUtil.isUser(loginUser)) {
//            sysUserRequest.setId(loginUser.getId());
//        }

        if (SysConsts.GROUP_TOP_CODE.equals(sysUserQuery.getSelectGroupCode())) {
            sysUserQuery.setSelectGroupCode(null);
        }

        Pagination pagination = sysUserService.listByPage(sysUserQuery, pageNo, pageSize);
        model.addAttribute("sysUserQuery", sysUserQuery);
        model.addAttribute("page", pagination);
        return "user/user-list";
    }

    @RequestMapping(value = "/preEdit", method = RequestMethod.GET)
    public String preEdit(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            SysUser sysUser = sysUserService.getById(id);
            model.addAttribute("sysUser", sysUser);
        }
        return "user/user-edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper save(SysUser sysUser) {
        ResponseWrapper response = new ResponseWrapper();
        try {
            sysUserService.save(sysUser);
            response.setCode(ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("保存用户出错：", e);
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }

    @RequestMapping(value = "/update/info", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper updateInfo(SysUser sysUser) {
        ResponseWrapper response = new ResponseWrapper();
        try {
            sysUserService.updateInfo(sysUser);
            response.setCode(ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("更新用户基本信息出错：", e);
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }

    @RequestMapping(value = "/checkUsername")
    @ResponseBody
    public Boolean checkUsername(@RequestParam(value = "username") String username) {
        return !sysUserService.checkUsername(username);
    }

    @RequestMapping(value = "/checkUserHadDel")
    @ResponseBody
    public ResponseWrapper checkUserHadDel(@RequestParam(value = "username") String username) {
        ResponseWrapper response = new ResponseWrapper();
        if (sysUserService.checkUserHadDel(username)) {
            response.setCode(ResponseCodeEnum.SUCCESS);
        } else {
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }

    @RequestMapping(value = "/activateUser")
    @ResponseBody
    public ResponseWrapper activateUser(@RequestParam(value = "username") String username,
                                        HttpServletRequest request) {
        ResponseWrapper response = new ResponseWrapper();
        try {
            LoginUser loginUser = AuthUtil.getUser(request);
            sysUserService.activateUser(username);
            response.setCode(ResponseCodeEnum.SUCCESS);
            response.setMessage("用户【" + username + "】已恢复使用，初始密码：123456，用户组：" + loginUser.getGroupName() + "，请及时修改密码和用户组！");
        } catch (Exception e) {
            LOGGER.error("激活用户出错：", e);
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }

    @RequestMapping(value = "/prePasswordReset", method = RequestMethod.GET)
    public String prePasswordReset(@RequestParam(value = "id") Integer id, Model model) {
        model.addAttribute("id", id);
        return "user/password-reset";
    }

    @RequestMapping(value = "/prePasswordEdit", method = RequestMethod.GET)
    public String prePasswordEdit(@RequestParam(value = "id") Integer id, Model model) {
        model.addAttribute("id", id);
        return "user/password-edit";
    }


    @RequestMapping(value = "/passwordEdit", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper passwordEdit(@RequestParam(value = "id") Long id,
                                        @RequestParam(value = "password") String password) {
        ResponseWrapper response = new ResponseWrapper();
        try {
            sysUserService.editPassword(id, SecurityUtil.md5(password.trim()));
            response.setCode(ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("密码修改失败：", e);
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper register(SysUser sysUser) {
        ResponseWrapper response = new ResponseWrapper();
        try {
            sysUserService.registerUser(sysUser);
            response.setCode(ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            LOGGER.error("用户注册失败：", e);
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper del(@RequestParam("ids") String ids) {
        ResponseWrapper response = new ResponseWrapper();
        if (StringUtils.isNotBlank(ids)) {
            try {
                sysUserService.batchDel(StringUtil.strToList(ids));
                response.setCode(ResponseCodeEnum.SUCCESS);
            } catch (Exception e) {
                LOGGER.error("删除用户失败！", e);
                response.setCode(ResponseCodeEnum.FAIL);
            }
        }
        return response;
    }

    @RequestMapping(value = "/loginUser", method = RequestMethod.GET)
    @ResponseBody
    public ResponseWrapper loginUser(HttpServletRequest request, HttpServletResponse response) {
        LoginUser loginUser = AuthUtil.getUser(request);
        if (AuthUtil.checkLogin(loginUser, response)) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", loginUser.getId());
            jsonObject.put("username", loginUser.getUsername());
            jsonObject.put("name", loginUser.getName());
            jsonObject.put("idCard", loginUser.getIdCard());
            jsonObject.put("tel", loginUser.getTel());
            jsonObject.put("belongLevel", loginUser.getBelongLevel());
            jsonObject.put("createOperatorGroupName", loginUser.getGroupName());
            jsonObject.put("isAdmin", AuthUtil.isAdmin(loginUser) || AuthUtil.isSuperAdmin(loginUser));
            jsonObject.put("laseBelongProCode", loginUser.getLastBelongProCode());
            jsonObject.put("laseBelongCityCode", loginUser.getLastBelongCityCode());
            jsonObject.put("laseBelongCountyCode", loginUser.getLastBelongCountyCode());
            jsonObject.put("features", loginUser.getFeatures());
            jsonObject.put("createOperatorGroupId", loginUser.getGroupId());
            jsonObject.put("longitude", loginUser.getLongitude());
            jsonObject.put("latitude", loginUser.getLatitude());
            return ResponseBuilder.ok(jsonObject);
        }
        return null;
    }

    @RequestMapping(value = "/resetPassword", method = {RequestMethod.POST, RequestMethod.GET})
    @ResponseBody
    public ResponseWrapper resetPassword(@RequestParam("beginId") Long beginId,
                                         @RequestParam("endId") Long endId) {
        ResponseWrapper response = new ResponseWrapper();
        try {
            response.setCode(ResponseCodeEnum.SUCCESS);
            sysUserService.resetPassword(beginId, endId);
        } catch (Exception e) {
            LOGGER.error("重置密码失败！", e);
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }
}
