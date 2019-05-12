package com.fasteam.system.controller;

import com.fasteam.common.constant.ResponseCodeEnum;
import com.fasteam.common.controller.SecureController;
import com.fasteam.common.dto.ResponseWrapper;
import com.fasteam.common.util.ResponseBuilder;
import com.fasteam.common.util.StringUtil;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.system.configuration.AppProperties;
import com.fasteam.system.domain.SysGroup;
import com.fasteam.system.dto.AreaTreeNode;
import com.fasteam.system.dto.SysGroupNode;
import com.fasteam.system.dto.SysObjectFatherNode;
import com.fasteam.system.dto.SysObjectNode;
import com.fasteam.system.service.BaseAreaCodeService;
import com.fasteam.system.service.SysGroupService;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 系统用户组
 */
@RequestMapping("/sysGroup")
@Controller
public class SysGroupController extends SecureController {
    private static final Logger LOG = LoggerFactory.getLogger(SysGroupController.class);

    @Autowired
    private AppProperties appProperties;

    @Autowired
    private SysGroupService sysGroupService;
    @Autowired
    private BaseAreaCodeService baseAreaCodeService;

    @RequestMapping(value = "/main", method = {RequestMethod.GET, RequestMethod.POST})
    public String main(@RequestParam(value = "groupName", required = false) String groupName,
                       @RequestParam(value = "_secondMenu", required = false) String _secondMenu,
                       Model model) {
        Gson gson = new Gson();
        List<SysGroupNode> sysGroupNodes;
        groupName = StringUtil.trim(groupName);
        if (StringUtils.isNotEmpty(groupName)) {
            sysGroupNodes = sysGroupService.createTree(groupName);
        } else {
            sysGroupNodes = sysGroupService.createTree();
        }

        if (!sysGroupNodes.isEmpty()) {
            model.addAttribute("groupCode", sysGroupNodes.get(0).getCode());
        } else {
            model.addAttribute("groupCode", "-1");  //表示没有对应的记录
        }

        model.addAttribute("sysGroupNodes", gson.toJson(sysGroupNodes));
        model.addAttribute("groupName", groupName);
        model.addAttribute("systemMenu", _secondMenu);
        return "group/group-main";
    }

    @RequestMapping(value = ("/getChildNode"))
    @ResponseBody
    public List<SysGroupNode> getChildNode(@RequestParam(value = "id", required = false) String id,
                                           HttpServletRequest request) throws IOException {
        if (AuthUtil.isUser(request)) {
            return new ArrayList<>();
        }
        if (StringUtils.isBlank(id)) {
            return sysGroupService.createTree();
        } else {
            return sysGroupService.createTree(Long.valueOf(id));
        }
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper save(SysGroup sysGroup,
                                @RequestParam(value = "topLogoFile", required = false) MultipartFile topLogoFile) {
        ResponseWrapper response = new ResponseWrapper();
        try {
            sysGroupService.save(sysGroup, topLogoFile);
            response.setCode(ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            LOG.error("用户组保存失败：", e);
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }

    @RequestMapping(value = "/preEdit", method = RequestMethod.GET)
    public String preEdit(@RequestParam(value = "id", required = false) Long id,
                          @RequestParam(value = "fatherId", required = false) Integer fatherId,
                          @RequestParam(value = "fatherName", required = false) String fatherName,
                          @RequestParam(value = "fatherCode", required = false) String fatherCode,
                          Model model) {
        SysGroup sysGroup = new SysGroup();
        if (id != null) {
            sysGroup = sysGroupService.getById(id);
        } else {
            sysGroup.setFatherId(fatherId);
            sysGroup.setFatherName(fatherName);
            sysGroup.setFatherCode(fatherCode);
        }

        List<AreaTreeNode> areaTreeNodes = baseAreaCodeService.createProAreaTree();
        Gson gson = new Gson();

        model.addAttribute("sysGroup", sysGroup);
        model.addAttribute("areaTreeNodes", gson.toJson(areaTreeNodes));
        return "group/group-edit";
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public ResponseWrapper del(@RequestParam("code") String code) {
        ResponseWrapper response = new ResponseWrapper();
        try {
            if (StringUtils.isNotBlank(code)) {
                sysGroupService.delByCode(code);
            }
            response.setCode(ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            LOG.error("删除用户组失败！", e);
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }

    @RequestMapping(value = "/checkGroupName")
    @ResponseBody
    public Boolean checkGroupName(@RequestParam(value = "id") Long id,
                                  @RequestParam(value = "name") String name) {
        return !sysGroupService.checkGroupName(id, name);
    }

    @RequestMapping(value = ("/showLogo"), method = RequestMethod.GET)
    public void showImage(@RequestParam(value = "logoName", required = true) String logoName,
                          HttpServletResponse response) throws Exception {
        response.setContentType("text/html; charset=UTF-8");
        response.setContentType("image/jpeg");
        String absolutePath = appProperties.logoDir + logoName;
        FileInputStream fis = new FileInputStream(absolutePath);
        OutputStream os = response.getOutputStream();
        try {
            int count = 0;
            byte[] buffer = new byte[1024 * 1024];
            while ((count = fis.read(buffer)) != -1) {
                os.write(buffer, 0, count);
            }
            os.flush();
        } catch (IOException e) {
            LOG.error("顶部图片获取失败：", e);
        } finally {
            if (os != null) {
                os.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * 返回登录用户同级+下级的用户和用户组
     *
     * @param userIds ,1,2,3,4,
     * @return
     */
    @RequestMapping(value = "/getGroupByLoginUser", method = RequestMethod.GET)
    @ResponseBody
    public ResponseWrapper getGroupByUserId(@RequestParam(value = "userIds", required = false) String userIds) {
        LoginUser loginUser = AuthUtil.getUser();
        Long groupId = loginUser.getGroupId();
        SysObjectFatherNode sysObjectNode = sysGroupService.getByFatherId(groupId, userIds);
        sysObjectNode.setExpand(true);
        List<SysObjectNode> sysObjectNodes = new ArrayList<>();
        sysObjectNodes.add(sysObjectNode);
        return ResponseBuilder.ok(sysObjectNodes);
    }

    /**
     * 返回登录用户直属上级和同居+下级的用户和用户组,省级用户就不需要获取上级用户
     *
     * @param userIds ,1,2,3,4,
     * @return
     */
    @RequestMapping(value = "/getTopAndLowGroup", method = RequestMethod.GET)
    @ResponseBody
    public ResponseWrapper getTopAndLowGroup(@RequestParam(value = "userIds", required = false) String userIds) {
        LoginUser loginUser = AuthUtil.getUser();
        Long groupId = loginUser.getGroupId();
        SysObjectFatherNode sysObjectNode = new SysObjectFatherNode();
        Integer belongLevel = loginUser.getBelongLevel();
        if (belongLevel != null) {
            if (belongLevel == 1) {
                sysObjectNode = sysGroupService.getByFatherId(groupId, userIds);
            } else {
                sysObjectNode = sysGroupService.getTopAndLowByFatherId(groupId, userIds);
            }
            sysObjectNode.setExpand(true);

        }
        List<SysObjectNode> sysObjectNodes = new ArrayList<>();
        sysObjectNodes.add(sysObjectNode);
        return ResponseBuilder.ok(sysObjectNodes);
    }

    /**
     * 返回登录用户同级+下级的用户和用户组
     *
     * @param groupId
     * @param userIds
     * @return
     */
    @RequestMapping(value = "/getGroupByGroupId", method = RequestMethod.GET)
    @ResponseBody
    public ResponseWrapper getGroupByGroupId(@RequestParam(value = "groupId") Long groupId,
                                             @RequestParam(value = "userIds", required = false) String userIds) {
        SysObjectFatherNode sysObjectFatherNode = sysGroupService.getByFatherId(groupId, userIds);
        return ResponseBuilder.ok(sysObjectFatherNode.getChildren());
    }
}
