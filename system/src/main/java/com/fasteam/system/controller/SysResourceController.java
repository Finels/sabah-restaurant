package com.fasteam.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.fasteam.common.constant.ResponseCodeEnum;
import com.fasteam.common.controller.SecureController;
import com.fasteam.common.dto.ResponseWrapper;
import com.fasteam.common.orm.dto.Pagination;
import com.fasteam.common.util.ResponseBuilder;
import com.fasteam.common.util.StringUtil;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.system.constant.SysConsts;
import com.fasteam.system.domain.SysResource;
import com.fasteam.system.dto.SysResourceNode;
import com.fasteam.system.query.SysResourceQuery;
import com.fasteam.system.service.SysResourceService;
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

import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 系统资源
 */
@RequestMapping("/sysResource")
@Controller
public class SysResourceController extends SecureController {
    private static final Logger LOG = LoggerFactory.getLogger(SysResourceController.class);

    @Autowired
    private SysResourceService sysResourceService;

    @RequestMapping(value = "/main", method = {RequestMethod.GET, RequestMethod.POST})
    public String main(SysResourceQuery sysResourceQuery,
                       @RequestParam(value = "_secondMenu", required = false) String _secondMenu,
                       @RequestParam(value = "pageNo", defaultValue = Pagination.DEFAULT_PAGE_NO) int pageNo,
                       @RequestParam(value = "pageSize", defaultValue = Pagination.DEFAULT_PAGE_SIZE) int pageSize,
                       Model model) {
        Pagination page = sysResourceService.listByPage(sysResourceQuery, pageNo, pageSize);

        model.addAttribute("page", page);
        model.addAttribute("sysResourceRequest", sysResourceQuery);
        model.addAttribute("systemMenu", _secondMenu);
        return "resource/resource-main";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper save(SysResource sysResource) {
        ResponseWrapper response = new ResponseWrapper();
        try {
            sysResourceService.save(sysResource);
            response.setCode(ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            LOG.error("资源保存失败：", e);
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }

    /**
     * 新增或者修改
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/preEdit", method = RequestMethod.GET)
    public String preEdit(@RequestParam(value = "id", required = false) Long id, Model model) {
        if (id != null) {
            SysResource sysResource = sysResourceService.getById(id);
            model.addAttribute("sysResource", sysResource);
        }
        return "resource/resource-edit";
    }

    /**
     * check uniqueCode 是否已经存在
     *
     * @param id
     * @param uniqueCode
     * @return
     */
    @RequestMapping(value = "/checkUniqueCode")
    @ResponseBody
    public Boolean checkUniqueCode(@RequestParam(value = "id", required = false) Long id,
                                   @RequestParam(value = "uniqueCode") String uniqueCode) {
        return !sysResourceService.checkUniqueCode(id, uniqueCode);
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public ResponseWrapper del(@RequestParam("ids") String ids) {
        ResponseWrapper response = new ResponseWrapper();
        if (StringUtils.isNotBlank(ids)) {
            try {
                sysResourceService.batchDel(StringUtil.strToList(ids));
                response.setCode(ResponseCodeEnum.SUCCESS);
            } catch (Exception e) {
                LOG.error("资源删除失败！", e);
                response.setCode(ResponseCodeEnum.FAIL);
            }
        }
        return response;
    }

    @RequestMapping(value = ("/getResourceNode"), method = RequestMethod.POST)
    @ResponseBody
    public List<SysResourceNode> getResourceNode(@RequestParam(value = "id", required = false,
            defaultValue = SysConsts.RESOURCE_DEFAULT_FATHER_ID) Long id) {
        return sysResourceService.createResourceTree(id, false, null, null);
    }

    @RequestMapping(value = "/getSecondMenu", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseWrapper getSecondMenu(String uniqueCode, HttpServletResponse httpServletResponse) {
        ResponseWrapper response;
        try {
            response = ResponseBuilder.ok();
            LoginUser loginUser = AuthUtil.getUser();
            if (AuthUtil.checkLogin(loginUser, httpServletResponse)) {
                Map<String, List<JSONObject>> secondMenuMap = loginUser.getSecondResourceMap();
                List<JSONObject> list = secondMenuMap.get(uniqueCode);
                response.setResult(list);
            }
        } catch (Exception e) {
            response = ResponseBuilder.error("查询二级菜单有误！");
            LOG.error("查询二级菜单有误！", e);
        }
        return response;
    }

    @RequestMapping(value = "/getModule", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public ResponseWrapper getModule(String uniqueCode) {
        ResponseWrapper response;
        try {
            response = ResponseBuilder.ok();
            List<SysResource> list = sysResourceService.listResourceByUniqueCode(uniqueCode);
            if (list != null && list.size() > 0) {
                response.setResult(list.get(0));
            }
        } catch (Exception e) {
            response = ResponseBuilder.error("查询二级菜单有误！");
            LOG.error("查询二级菜单有误！", e);
        }
        return response;
    }
}
