package com.fasteam.system.controller;

import com.fasteam.common.dto.ResponseWrapper;
import com.fasteam.common.util.ResponseBuilder;
import com.fasteam.system.domain.Dictionary;
import com.fasteam.system.query.DictionaryQuery;
import com.fasteam.system.service.DictionaryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 数据字典
 * Created by Administrator on 2017/11/2.
 */
@Controller
@RequestMapping("/dictionary")
public class DictionaryController {
    private static final Logger LOG = LoggerFactory.getLogger(DictionaryController.class);

    @Autowired
    private DictionaryService dictionaryService;

    /**
     * 根据类型获取数据
     *
     * @param type
     * @return
     */
    @RequestMapping(value = ("/type/{type}"))
    @ResponseBody
    public ResponseWrapper getByType(@PathVariable(value = "type") String type) {
        LOG.info("根据类型【{}】查询数据字典", type);
        ResponseWrapper response;
        try {
            DictionaryQuery query = new DictionaryQuery();
            query.setType(type.trim());
            List<Dictionary> dictionaries = dictionaryService.listByQuery(query);
            response = ResponseBuilder.ok();
            response.setResult(dictionaries);
            response.setTotal(dictionaries.size());
        } catch (Exception e) {
            LOG.error("根据类型获取字典数据失败！", e);
            response = ResponseBuilder.error("查询失败！");
        }
        return response;
    }
}
