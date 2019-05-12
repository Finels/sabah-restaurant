package com.fasteam.system.domain;

import com.fasteam.security.dto.LoginUser;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 系统用户
 */
public class SysUser extends LoginUser {
    private String uKeySerialNo;
    private Integer maxTask;        //最多只能执行的任务数量
    private String seriesNo;        // 用户唯一标识序列号(uuid)
    private String userType;        // 用户类型(Pn,Pe等)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validTimeBegin;    //用户有效期
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date validTimeEnd;      //用户有效期
    private String isRegister;      // 是否注册取证云
    private Integer logicDel;       // 逻辑删标识字段：1：已删除用户，其它值均为正常用户
    private String baseAuthority;   // 基础库权限字段{"mail":{"exp":"2014-1-1","valid":1},"base":{"exp":"","valid":0},"qq":{"exp":"","valid":1}}
    private String company;         //单位名称
    private String job;         // 职务
    private String sex;
    private String telephone;       //手机号码
    private String qq;
    private String email;
    private String address;         //
    private String keyTypeIds;      // 线索类别序列 用户线索列表排序
    private Integer isUploadTz;     // 新增专项是否显示上传nf-5000 1:显示, 0：不显示
    private Integer userStatus;     // 用户状态，1正式 2试用
    private Integer userFrom;       // 用户类型，1平台 2工具箱
    private Integer auditing;       // 审核状态 3表示审核通过 1表示待审核 0表示未注册 -1表示审核不通过
    private Integer score;          //用户积分
    private Date createTime;        // 创建时间
    private Long createOperator;    //创建人
    private Date updateTime;
    private Long updateOperator;
    private String lastPid;             //上级城市编码
    private String lastCid;             //城市编码
    ///////////////////////////////////////
    //不用持久化的字段
    private String baseData;  // 基础库标识
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date baseExp;     // 基础库有效时间
    private Integer preAuditing;

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getuKeySerialNo() {
        return uKeySerialNo;
    }

    public void setuKeySerialNo(String uKeySerialNo) {
        this.uKeySerialNo = uKeySerialNo;
    }

    public Integer getMaxTask() {
        return maxTask;
    }

    public void setMaxTask(Integer maxTask) {
        this.maxTask = maxTask;
    }

    public String getSeriesNo() {
        return seriesNo;
    }

    public void setSeriesNo(String seriesNo) {
        this.seriesNo = seriesNo;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getIsRegister() {
        return isRegister;
    }

    public void setIsRegister(String isRegister) {
        this.isRegister = isRegister;
    }

    public Integer getLogicDel() {
        return logicDel;
    }

    public void setLogicDel(Integer logicDel) {
        this.logicDel = logicDel;
    }

    public String getBaseAuthority() {
        return baseAuthority;
    }

    public void setBaseAuthority(String baseAuthority) {
        this.baseAuthority = baseAuthority;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public String getEmail() {
        return email;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getKeyTypeIds() {
        return keyTypeIds;
    }

    public void setKeyTypeIds(String keyTypeIds) {
        this.keyTypeIds = keyTypeIds;
    }

    public Integer getIsUploadTz() {
        return isUploadTz;
    }

    public void setIsUploadTz(Integer isUploadTz) {
        this.isUploadTz = isUploadTz;
    }

    public Integer getAuditing() {
        return auditing;
    }

    public void setAuditing(Integer auditing) {
        this.auditing = auditing;
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Integer getUserFrom() {
        return userFrom;
    }

    public void setUserFrom(Integer userFrom) {
        this.userFrom = userFrom;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getCreateOperator() {
        return createOperator;
    }

    public void setCreateOperator(Long createOperator) {
        this.createOperator = createOperator;
    }

    public Long getUpdateOperator() {
        return updateOperator;
    }

    public void setUpdateOperator(Long updateOperator) {
        this.updateOperator = updateOperator;
    }

    public Date getValidTimeBegin() {
        return validTimeBegin;
    }

    public void setValidTimeBegin(Date validTimeBegin) {
        this.validTimeBegin = validTimeBegin;
    }

    public Date getValidTimeEnd() {
        return validTimeEnd;
    }

    public void setValidTimeEnd(Date validTimeEnd) {
        this.validTimeEnd = validTimeEnd;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBaseData() {
        return baseData;
    }

    public void setBaseData(String baseData) {
        this.baseData = baseData;
    }

    public Date getBaseExp() {
        return baseExp;
    }

    public void setBaseExp(Date baseExp) {
        this.baseExp = baseExp;
    }

    public String getLastPid() {
        return lastPid;
    }

    public void setLastPid(String lastPid) {
        this.lastPid = lastPid;
    }

    public String getLastCid() {
        return lastCid;
    }

    public void setLastCid(String lastCid) {
        this.lastCid = lastCid;
    }

    public Integer getPreAuditing() {
        return preAuditing;
    }

    public void setPreAuditing(Integer preAuditing) {
        this.preAuditing = preAuditing;
    }
}

