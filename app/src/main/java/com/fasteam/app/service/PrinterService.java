package com.fasteam.app.service;

import com.fasteam.app.domain.Printer;
import com.fasteam.app.query.PrinterQuery;
import com.fasteam.common.orm.service.BaseService;
import com.fasteam.common.query.BaseQuery;


/**
 * Description:  com.fasteam.app.service.impl
 * Copyright: © 2017 FanLei. All rights reserved.
 * Company: NULL
 *
 * @author FL
 * @version 1.0
 * @timestamp 2019/4/13
 */
public interface PrinterService extends BaseService<Printer, PrinterQuery> {
    /**
     * 添加打印机到云平台和本地数据库中
     *
     * @param printer
     * @throws Exception
     */
    void addPrinter(Printer printer) throws Exception;

    void delPrinter(Printer printer) throws Exception;

}
