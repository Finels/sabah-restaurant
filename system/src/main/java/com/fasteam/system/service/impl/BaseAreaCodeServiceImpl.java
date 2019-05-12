package com.fasteam.system.service.impl;

import com.fasteam.common.orm.dao.BaseDao;
import com.fasteam.common.orm.service.impl.BaseServiceImpl;
import com.fasteam.common.query.BaseQuery;
import com.fasteam.common.tree.NodeTypeEnum;
import com.fasteam.system.dao.BaseAreaCodeDao;
import com.fasteam.system.domain.BaseAreaCode;
import com.fasteam.system.dto.AreaTreeNode;
import com.fasteam.system.service.BaseAreaCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/26.
 */
@Service
public class BaseAreaCodeServiceImpl extends BaseServiceImpl<BaseAreaCode, BaseQuery> implements BaseAreaCodeService {
    private static final Logger LOG = LoggerFactory.getLogger(BaseAreaCodeServiceImpl.class);
    private static final Integer SHOW_LENGTH_ON_TREE = 10; //生成的tree节点名称展示的字符串长度
    @Autowired
    private BaseAreaCodeDao baseAreaCodeDao;

    @Override
    public BaseDao<BaseAreaCode, BaseQuery> getDao() {
        return baseAreaCodeDao;
    }

    /**
     * 构造省级结构
     *
     * @return
     */
    @Override
    public List<AreaTreeNode> createProAreaTree() {
        List<AreaTreeNode> rootList = new ArrayList<>();
        try {
            List<BaseAreaCode> proAreas = baseAreaCodeDao.listCityByLevel(1);
            if (!proAreas.isEmpty()) {
                for (BaseAreaCode baseAreaCode : proAreas) {
                    AreaTreeNode root = new AreaTreeNode();
                    root.setIsParent(true);
                    root.setOpen(true);
                    root.setId(baseAreaCode.getId());
                    root.setAreaCode(baseAreaCode.getCode());
                    root.setpCode(baseAreaCode.getPcode());
                    root.setNodeType(NodeTypeEnum.AREA);
                    String name = baseAreaCode.getName();
                    root.setTitle(name);
                    if (name.length() > SHOW_LENGTH_ON_TREE) {
                        name = name.substring(0, SHOW_LENGTH_ON_TREE - 1) + "...";
                    }
                    root.setName(name);
                    rootList.add(root);
                }
            }
        } catch (Exception e) {
            LOG.error("构造区域树失败：", e);
        }
        return rootList;
    }

    /**
     * 根据父节点构造子节点
     *
     * @param pCode
     * @return
     */
    @Override
    public List<AreaTreeNode> createCityTree(String pCode) {
        List<AreaTreeNode> rootList = new ArrayList<>();
        try {
            List<BaseAreaCode> childCities = baseAreaCodeDao.listCityByPCode(pCode);
            if (!childCities.isEmpty()) {
                for (BaseAreaCode childCity : childCities) {
                    AreaTreeNode root = new AreaTreeNode();
                    if (childCity.getLevel() >= 3) {
                        root.setIsParent(false);
                    } else {
                        root.setIsParent(true);
                    }
                    root.setOpen(true);
                    root.setId(childCity.getId());
                    root.setAreaCode(childCity.getCode());
                    root.setpCode(childCity.getPcode());
                    root.setNodeType(NodeTypeEnum.CITY);
                    String name = childCity.getName();
                    root.setTitle(name);
                    if (name.length() > SHOW_LENGTH_ON_TREE) {
                        name = name.substring(0, SHOW_LENGTH_ON_TREE - 1) + "...";
                    }
                    root.setName(name);
                    rootList.add(root);
                }
            }
        } catch (Exception e) {
            LOG.error("构造城市树失败：", e);
        }
        return rootList;
    }
}
