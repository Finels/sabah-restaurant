package com.fasteam.system.controller;

import com.fasteam.common.constant.ResponseCodeEnum;
import com.fasteam.common.controller.SecureController;
import com.fasteam.common.dto.ResponseWrapper;
import com.fasteam.common.orm.dto.Pagination;
import com.fasteam.common.util.ResponseBuilder;
import com.fasteam.common.util.StringUtil;
import com.fasteam.security.dto.LoginUser;
import com.fasteam.security.util.AuthUtil;
import com.fasteam.system.query.SysLogQuery;
import com.fasteam.system.service.SysLogService;
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 系统日志
 */
@Controller
@RequestMapping("/sysLog")
public class SysLogController extends SecureController {
    private static final Logger LOG = LoggerFactory.getLogger(SysLogController.class);

    @Autowired
    private SysLogService sysLogService;

    @RequestMapping(value = "/main", method = {RequestMethod.GET, RequestMethod.POST})
    public String main(SysLogQuery sysLogQuery,
                       @RequestParam(value = "_secondMenu", required = false) String _secondMenu,
                       @RequestParam(value = "pageNo", defaultValue = Pagination.DEFAULT_PAGE_NO) int pageNo,
                       @RequestParam(value = "pageSize", defaultValue = Pagination.DEFAULT_PAGE_SIZE) int pageSize,
                       HttpServletRequest request, Model model) {
        LoginUser loginUser = AuthUtil.getUser(request);
        if (AuthUtil.isUser(loginUser)) {
            sysLogQuery.setUserIds(String.valueOf(loginUser.getId()));
        } else {
            sysLogQuery.setUserIds(loginUser.getUserIdStr());
        }
        try {
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            if (sysLogQuery.getSysTimeBegin() != null) {
                String beginTime = format.format(sysLogQuery.getSysTimeBegin()) + " 00:00:00";
                sysLogQuery.setTimeBegin(beginTime);
            }
            if (sysLogQuery.getSysTimeEnd() != null) {
                String endTime = format.format(sysLogQuery.getSysTimeEnd()) + " 23:59:59";
                sysLogQuery.setTimeEnd(endTime);
            }
            Pagination page = sysLogService.listByPage(sysLogQuery, pageNo, pageSize);

            model.addAttribute("page", page);
            model.addAttribute("sysLogQuery", sysLogQuery);
            model.addAttribute("systemMenu", _secondMenu);
        } catch (Exception e) {
            LOG.error("查询时间失败！", e);
        }
        return "log/log-main";
    }

    @RequestMapping(value = "/del")
    @ResponseBody
    public ResponseWrapper del(@RequestParam("ids") String ids) {
        ResponseWrapper response = new ResponseWrapper();
        if (StringUtils.isBlank(ids)) {
            response.setCode(ResponseCodeEnum.FAIL);
            response.setMessage("请选择要删除的数据！");
        }
        try {
            sysLogService.batchDel(StringUtil.strToList(ids));
            response.setCode(ResponseCodeEnum.SUCCESS);
        } catch (Exception e) {
            LOG.error("系统日志删除失败！", e);
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public ResponseWrapper add(@RequestParam("userId") Long userId,
                               @RequestParam("remark") String remark,
                               @RequestParam("type") Integer type,
                               @RequestParam("module") String module,
                               @RequestParam("feature") String feature,
                               @RequestParam("action") String action,
                               @RequestParam("ip") String ip,
                               @RequestParam("requestURL") String requestURL) {
        ResponseWrapper response = new ResponseWrapper();
        try {
            sysLogService.add(userId, remark, type, module, feature, action, ip, requestURL);
        } catch (Exception e) {
            LOG.error("系统日志保存失败！", e);
            response.setCode(ResponseCodeEnum.FAIL);
        }
        return response;
    }

    /**
     * 日志持久化到mysql库
     *
     * @return
     */
    @RequestMapping(value = "/handler")
    @ResponseBody
    public ResponseWrapper handler() {
        ResponseWrapper response;
        try {
            response = sysLogService.handlerSyslog();
        } catch (Exception e) {
            LOG.error("系统日志持久化到mysql库失败！", e);
            response = ResponseBuilder.error("系统日志持久化到mysql库失败！");
        }
        return response;
    }

}
