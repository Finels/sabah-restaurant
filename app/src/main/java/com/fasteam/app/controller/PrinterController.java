package com.fasteam.app.controller;

import com.fasteam.app.domain.Printer;
import com.fasteam.app.query.PrinterQuery;
import com.fasteam.app.service.PrinterService;
import com.fasteam.common.dto.ResponseWrapper;
import com.fasteam.common.orm.dto.Pagination;
import com.fasteam.common.util.ResponseBuilder;
import com.fasteam.security.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Description:  com.fasteam.app.controller
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/4/13
 */
@RestController
@RequestMapping("/printer")
public class PrinterController {
    @Autowired
    private PrinterService printerService;

    /**
     * 添加打印机，单台添加
     *
     * @param printer
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ResponseWrapper add(Printer printer) {
        try {
            printerService.addPrinter(printer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error("保存失败请重试");
        }
        return ResponseBuilder.ok();
    }

    /**
     * 查询打印机，传入分页参数，暂无其他查询条件
     *
     * @param pageNo   从第几条开始，默认1
     * @param pageSize 每页查询几条，默认10
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ResponseWrapper list(@RequestParam(value = "pageNo", defaultValue = Pagination.DEFAULT_PAGE_NO) int pageNo,
                                @RequestParam(value = "pageSize", defaultValue = Pagination.DEFAULT_PAGE_SIZE) int pageSize) {
        PrinterQuery query = new PrinterQuery();
        query.setCreateOperator(AuthUtil.getId());
        return ResponseBuilder.ok(printerService.listByPage(query, pageNo, pageSize));

    }

    /**
     * 删除打印机（物理删除），同时也会删除云平台上的记录，单台删除，暂无批量删除
     *
     * @param printer 打印机信息
     * @return
     */
    @RequestMapping(value = "/del", method = RequestMethod.DELETE)
    public ResponseWrapper del(Printer printer) {
        try {
            printerService.delPrinter(printer);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseBuilder.error("删除失败请重试");
        }
        return ResponseBuilder.ok();
    }
}
