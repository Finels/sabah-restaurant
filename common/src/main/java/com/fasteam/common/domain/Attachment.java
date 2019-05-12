package com.fasteam.common.domain;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 附件实体类
 * Created by Administrator on 2018/3/21.
 */
public class Attachment extends BaseDomain implements Cloneable {
    private static final Logger LOGGER = LoggerFactory.getLogger(Attachment.class);
    private Long uid;       //唯一id
    private Long relationUid;   //关联表uid
    private Long templateUid;   //关联模板uid
    private String name;    //名称
    private Float size;     //文件大小(单位M)
    private String module;  //模块(对于每个模块：如task:任务流转)
    private String feature; //功能类型(对于每个模块下的功能：如mainTask:任务流转模块下的主任务)
    private String path;    //附件目录路径(命名规则：/taskUid/文件名称)
    private Integer status; //状态  是否解析等  0:上传成功，单不需要解析、1：上传成功、2：解析中、3：解析成功
    private String statusName; //状态中文描述
    private Integer type;   //类别  账单、话单等
    private String typeName; //类别中文描述
    private String info;    //附件信息(存放json字符串{})
    private String fileType;    //文件类型
    private String visitUrl;    //访问路径
    private Integer dataSource; //数据来源 1：源文件，2：分析结果集
    private String sheetName;
    private String sourceFileName;
    private String analyzeType;
    private Long fatherUid;
    private Integer titleRow;
    private String backup;      //备注
    private List relationList;  //关联文件（结果集）
    private String createOperatorName;
    private Long resourceUid;   //关联文件夹UID
    private Long topicUid;
    private Long subjectUid;
    private Long analyzeStatus;
    private Long extendOne;
    private Long lineCount;

    ///////////////////
    private String parseMessage;

    @Override
    public Attachment clone() {
        Attachment attachment = null;
        try {
            attachment = (Attachment) super.clone();
        } catch (CloneNotSupportedException e) {
            LOGGER.error("Schema clone error", e);
        }
        return attachment;
    }

    public Integer getTitleRow() {
        return titleRow;
    }

    public void setTitleRow(Integer titleRow) {
        this.titleRow = titleRow;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public Long getTemplateUid() {
        return templateUid;
    }

    public void setTemplateUid(Long templateUid) {
        this.templateUid = templateUid;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getRelationUid() {
        return relationUid;
    }

    public void setRelationUid(Long relationUid) {
        this.relationUid = relationUid;
    }

    public Long getUid() {
        return uid;
    }

    public void setUid(Long uid) {
        this.uid = uid;
    }

    public String getFeature() {
        return feature;
    }

    public void setFeature(String feature) {
        this.feature = feature;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public Float getSize() {
        return size;
    }

    public void setSize(Float size) {
        this.size = size;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getFileType() {
        return fileType;
    }

    public void setFileType(String fileType) {
        this.fileType = fileType;
    }

    public String getVisitUrl() {
        return visitUrl;
    }

    public void setVisitUrl(String visitUrl) {
        this.visitUrl = visitUrl;
    }

    public Integer getDataSource() {
        return dataSource;
    }

    public void setDataSource(Integer dataSource) {
        this.dataSource = dataSource;
    }

    public String getAnalyzeType() {
        return analyzeType;
    }

    public void setAnalyzeType(String analyzeType) {
        this.analyzeType = analyzeType;
    }

    public Long getFatherUid() {
        return fatherUid;
    }

    public void setFatherUid(Long fatherUid) {
        this.fatherUid = fatherUid;
    }

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    public String getSourceFileName() {
        return sourceFileName;
    }

    public void setSourceFileName(String sourceFileName) {
        this.sourceFileName = sourceFileName;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getBackup() {
        return backup;
    }

    public void setBackup(String backup) {
        this.backup = backup;
    }

    public List getRelationList() {
        return relationList;
    }

    public void setRelationList(List relationList) {
        this.relationList = relationList;
    }

    public String getCreateOperatorName() {
        return createOperatorName;
    }

    public void setCreateOperatorName(String createOperatorName) {
        this.createOperatorName = createOperatorName;
    }

    public Long getResourceUid() {
        return resourceUid;
    }

    public void setResourceUid(Long resourceUid) {
        this.resourceUid = resourceUid;
    }

    public Long getSubjectUid() {
        return subjectUid;
    }

    public void setSubjectUid(Long subjectUid) {
        this.subjectUid = subjectUid;
    }

    public Long getTopicUid() {
        return topicUid;
    }

    public void setTopicUid(Long topicUid) {
        this.topicUid = topicUid;
    }

    public String getParseMessage() {
        return parseMessage;
    }

    public void setParseMessage(String parseMessage) {
        this.parseMessage = parseMessage;
    }

    public Long getAnalyzeStatus() {
        return analyzeStatus;
    }

    public void setAnalyzeStatus(Long analyzeStatus) {
        this.analyzeStatus = analyzeStatus;
    }

    public Long getExtendOne() {
        return extendOne;
    }

    public void setExtendOne(Long extendOne) {
        this.extendOne = extendOne;
    }

    public Long getLineCount() {
        return lineCount;
    }

    public void setLineCount(Long lineCount) {
        this.lineCount = lineCount;
    }
}
