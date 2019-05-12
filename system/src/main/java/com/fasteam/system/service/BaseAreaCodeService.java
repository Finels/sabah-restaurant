package com.fasteam.system.service;


import com.fasteam.common.orm.service.BaseService;
import com.fasteam.common.query.BaseQuery;
import com.fasteam.system.domain.BaseAreaCode;
import com.fasteam.system.dto.AreaTreeNode;

import java.util.List;

/**
 * Created by aaron on 2016/12/26.
 */
public interface BaseAreaCodeService extends BaseService<BaseAreaCode, BaseQuery> {
    List<AreaTreeNode> createProAreaTree();

    List<AreaTreeNode> createCityTree(String pCode);
}
