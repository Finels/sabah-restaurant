package com.fasteam.system.controller;

import com.fasteam.common.constant.ResponseCodeEnum;
import com.fasteam.common.controller.SecureController;
import com.fasteam.common.dto.ResponseWrapper;
import com.fasteam.common.orm.dto.Pagination;
import com.fasteam.common.util.StringUtil;
import com.fasteam.security.constant.SecurityLevelConst;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.system.constant.SysConsts;
import com.fasteam.system.domain.SysRole;
import com.fasteam.system.domain.SysUser;
import com.fasteam.system.dto.SysResourceNode;
import com.fasteam.system.query.SysRoleQuery;
import com.fasteam.system.service.SysResourceService;
import com.fasteam.system.service.SysRoleService;
import com.fasteam.system.service.SysUserService;
import com.google.gson.Gson;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 系统角色
 */
@RequestMapping("sysRole")
@Controller
public class SysRoleController extends SecureController {
    private static final Logger LOG = LoggerFactory.getLogger(SysRoleController.class);

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysRoleService sysRoleService;
    @Autowired
    private SysResourceService sysResourceService;

    @RequestMapping(value = "/main", method = {RequestMethod.GET, RequestMethod.POST})
    public String main(@RequestParam(value = "name", required = false) String name,
                       @RequestParam(value = "_secondMenu", required = false) String _secondMenu,
                       @RequestParam(value = "pageNo", defaultValue = Pagination.DEFAULT_PAGE_NO) int pageNo,
                       @RequestParam(value = "pageSize", defaultValue = Pagination.DEFAULT_PAGE_SIZE) int pageSize,
                       HttpServletRequest request, Model model) {
        LoginUser loginUser = AuthUtil.getUser(request);
        name = StringUtil.trim(name);

        SysRoleQuery sysRoleQuery = new SysRoleQuery();
        sysRoleQuery.setName(name);
//        if (loginUser.getDataArea().contains(DataArea.P.getCode())) {
//            sysRoleRequest.setUserId(loginUser.getId());
//        } else {
        sysRoleQuery.setGroupCode(loginUser.getGroupCode());
//            if (loginUser.getDataArea().contains(DataArea.L.getCode())) {
//                sysRoleRequest.setDataArea(DataArea.L.getCode());
//            }
//        }

        Pagination page = sysRoleService.listByPage(sysRoleQuery, pageNo, pageSize);

        model.addAttribute("page", page);
        model.addAttribute("name", name);
        model.addAttribute("systemMenu", _secondMenu);
        return "role/role-main";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper save(SysRole sysRole) {
        ResponseWrapper response = new ResponseWrapper();
        try {
            sysRoleService.save(sysRole);
            response.setCode(ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            LOG.error("角色保存失败：", e);
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }

    /**
     * 新增或者修改角色
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/preEdit", method = RequestMethod.GET)
    public String preEdit(@RequestParam(value = "id", required = false) Long id,
                          HttpServletRequest request, Model model) {
        if (id != null) {
            SysRole sysRole = sysRoleService.getById(id);
            model.addAttribute("sysRole", sysRole);
        }

        if (AuthUtil.isUser(request)) {
            model.addAttribute("roleMap", SecurityLevelConst.userRoleMap);
        } else {
            model.addAttribute("roleMap", SecurityLevelConst.adminRoleMap);
        }
        return "role/role-edit";
    }

    @RequestMapping(value = "/del", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper del(@RequestParam("ids") String ids) {
        ResponseWrapper response = new ResponseWrapper();
        if (StringUtils.isNotBlank(ids)) {
            try {
                sysRoleService.batchDel(StringUtil.strToList(ids));
                response.setCode(ResponseCodeEnum.SUCCESS);
            } catch (Exception e) {
                LOG.error("删除角色失败！", e);
                response.setCode(ResponseCodeEnum.FAIL);
            }
        }
        return response;
    }

    @RequestMapping(value = "/preAuthorization", method = RequestMethod.GET)
    public String preAuthorization(@RequestParam("id") Long id, Model model) {
        SysRole sysRole = sysRoleService.getById(id);
        List<String> selectResourceList = new ArrayList<>();
        String resources = sysRole.getResources();
        if (resources != null) {
            Collections.addAll(selectResourceList, resources.split(","));
        }
        List<SysResourceNode> resourceNodes = sysResourceService.createResourceTree(Long.parseLong(SysConsts.RESOURCE_DEFAULT_FATHER_ID),
                true, selectResourceList, sysRole.getCode());
        Gson gson = new Gson();
        model.addAttribute("sysRole", sysRole);
        model.addAttribute("resourceNodes", gson.toJson(resourceNodes));
        return "role/role-authorization";
    }

    @RequestMapping(value = "/authorization", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper authorization(SysRole sysRole) {
        ResponseWrapper response = new ResponseWrapper();
        try {
            sysRoleService.saveAuthorization(sysRole);
            response.setCode(ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            LOG.error("角色授权失败：", e);
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }

    @RequestMapping(value = "/checkRoleName")
    @ResponseBody
    public Boolean checkRoleName(@RequestParam(value = "id", required = false) Long id,
                                 @RequestParam(value = "name") String name) {
        return !sysRoleService.checkRoleName(id, name);
    }

    /**
     * 获取角色节点
     *
     * @return
     */
    @RequestMapping(value = "/getRoles")
    @ResponseBody
    public ResponseWrapper getRoles(@RequestParam(value = "userId", required = false) String userId,
                                    HttpServletRequest request) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        String roles = "";
        try {
            LoginUser loginUser = AuthUtil.getUser(request);
            SysUser selectUser;
            String roleNames = "";
            if (StringUtils.isNotBlank(userId)) {
                selectUser = sysUserService.getById(Long.valueOf(userId));
                roleNames = selectUser.getRoleNames();
            }

            List<SysRole> roleList = sysRoleService.listByGroupCode(loginUser.getGroupCode());
            for (SysRole sysRole : roleList) {
                String roleName = sysRole.getName();
                roles += "<option title='" + roleName + "' value='" + sysRole.getId() + "' label='" + sysRole.getCode() + "'";
                if (StringUtils.isNotBlank(roleNames)) {
                    if (("," + roleNames + ",").indexOf("," + roleName + ",") != -1) {
                        roles += " selected = 'selected'";
                    }
                }
                roles += ">";
                if (roleName.length() > 15) {
                    roleName = roleName.substring(0, 15) + "...";
                }
                roles += roleName + "</option>";
            }
            if (roles.endsWith(",")) {
                roles = roles.substring(0, roles.length() - 1);
            }
        } catch (Exception e) {
            LOG.error("异步获取角色节点失败", e);
        }
        responseWrapper.setResult(roles);
        return responseWrapper;
    }

    /**
     * 获取线索数据范围
     *
     * @return
     */
    @RequestMapping(value = "/getFindDatas")
    @ResponseBody
    public ResponseWrapper getFindDatas(@RequestParam(value = "roleId", required = false) String roleId,
                                        HttpServletRequest request) {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        String findDataScopes = "";
        try {
            SysRole selectRole;
            String categorys = "";
            if (StringUtils.isNotBlank(roleId)) {
                selectRole = sysRoleService.getById(Long.valueOf(roleId));
                categorys = selectRole.getFindDataScopeName();
            }
            List<Map<String, Object>> listFindData = sysRoleService.listFindData();
            for (int i = 0; i < listFindData.size(); i++) {
                Map<String, Object> map = listFindData.get(i);
                String category = (String) map.get("category");
                findDataScopes += "<option title='" + category + "' value='" + map.get("categoryCode") + "' label='" + map.get("categoryCode") + "'";
                if (StringUtils.isNotBlank(categorys)) {
                    if (("," + categorys + ",").indexOf("," + category + ",") != -1) {
                        findDataScopes += " selected = 'selected'";
                    }
                }
                findDataScopes += ">";
                if (category.length() > 15) {
                    category = category.substring(0, 15) + "...";
                }
                findDataScopes += category + "</option>";
            }
            if (findDataScopes.endsWith(",")) {
                findDataScopes = findDataScopes.substring(0, findDataScopes.length() - 1);
            }
        } catch (Exception e) {
            LOG.error("异步获取线索数据范围失败", e);
        }
        responseWrapper.setResult(findDataScopes);
        return responseWrapper;
    }
}
