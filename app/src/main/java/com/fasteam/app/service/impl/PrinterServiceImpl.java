package com.fasteam.app.service.impl;

import com.fasteam.app.dao.PrinterDao;
import com.fasteam.app.domain.Printer;
import com.fasteam.app.query.PrinterQuery;
import com.fasteam.app.service.PrinterService;
import com.fasteam.app.util.PrinterUtil;
import com.fasteam.common.orm.dto.Pagination;
import com.fasteam.common.orm.service.impl.BaseServiceImpl;
import com.fasteam.security.util.AuthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Description:  com.fasteam.app.service.impl
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/4/13
 */
@Service
public class PrinterServiceImpl extends BaseServiceImpl<Printer, PrinterQuery> implements PrinterService {
    @Autowired
    private PrinterDao printerDao;

    @Override
    public void addPrinter(Printer printer) throws Exception {
        //先保存到云平台上
        StringBuilder addPrinterStr = new StringBuilder(); //sn1#key1#remark1#carnum1
        addPrinterStr.append(printer.getSn());
        addPrinterStr.append("#");
        addPrinterStr.append(printer.getKey());
        addPrinterStr.append("#");
        addPrinterStr.append(printer.getName() + "-" + printer.getBackup());
        String result = PrinterUtil.addprinter(addPrinterStr.toString());
        if (PrinterUtil.getResultStatus(result, printer.getSn())) {
            //再保存到数据库中
            printer.setCreateOperator(AuthUtil.getId());
            printerDao.insert(printer);
        } else {
            //保存失败，抛出异常
            throw new Exception();
        }
    }

    @Override
    public void delPrinter(Printer printer) throws Exception {
        //先删除数据库
        printerDao.delById(printer.getId());
        //再删除云平台上的数据
        PrinterUtil.delPrinter(printer.getSn());
    }

    @Override
    public Pagination<Printer> listByPage(PrinterQuery query, int pageNo, int pageSize) {
        Pagination<Printer> page = new Pagination<>();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);

        List<Printer> list = printerDao.listByPage(query, page);
        long total = 0;
        if (!list.isEmpty()) {
            total = printerDao.count(query);
        }
        //查询打印机在云平台中的状态
        for (Printer printer : list) {
            printer.setStatus(PrinterUtil.queryPrinterStatus(printer.getSn()));
        }
        page.setContents(list);
        page.setTotalCount(total);
        return page;
    }

}
