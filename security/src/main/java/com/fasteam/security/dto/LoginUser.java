package com.fasteam.security.dto;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.*;

/**
 * 登录用户信息
 */
public class LoginUser implements Serializable, UserDetails {
    private static final long serialVersionUID = 5649575400950585684L;
    private Long id;
    private String username;            //用户名、账号
    private String password;            //密码
    private String name;                //用户名称
    private String idCard;              // 身份证(联系人)
    private String tel;                 // 联系方式
    private Set<Long> resourceIds = new HashSet<>();
    private List<JSONObject> systemResourceList = new ArrayList<>();    //其它系统链接
    private List<JSONObject> topResourceList = new ArrayList<>();       //一级菜单
    private String features;            //功能菜单
    private Map<String, List<JSONObject>> secondResourceMap = new HashMap<>();  //上级uniqueCode, 子级resourceList(二级菜单)
    private Map<String, List<JSONObject>> thirdResourceMap = new HashMap<>();   //上级uniqueCode, 子级resourceList(三级菜单)
    private String topLogo;             //头部logo
    private Set<String> dataArea;       //数据权限范围
    private Integer publishShare;       // 是否有权限发布共享 1:有， 其它：否
    private Set<String> baseDataScope;
    private Set<String> findDataScope;
    private Set<Long> userIdSet = new HashSet<>();           // 可见用户ID序列
    private String userIdStr;
    private Set<Long> groupUserIdSet = new HashSet<>();      // 本机构用户ID序列
    private String groupUserIdStr;
    private Set<Long> lowGroupUserIdSet = new HashSet<>();   // 下级机构用户ID序列
    private String lowGroupUserIdStr;
    private Set<Long> superGroupUserIdSet = new HashSet<>(); // 上级机构用户ID序列
    private String superGroupUserIdStr;
    private String lastBelongPCode;     //所属地区上级code
    private String lastBelongCode;      //所属地区code
    private String lastBelongProCode;   //所属地区省编码
    private String lastBelongCityCode;  //所属地区市编码
    private String lastBelongCountyCode;    //所属地区县编码
    private Integer belongLevel;
    private Long groupId;           //所属组织架构ID
    private String groupName;       //组织机构名称
    private String groupCode;       //组织编码
    private String roleIds;         // 用户角色IDs(一个用户可以有多个角色)
    private String roleNames;
    private String roleCodes;
    private Long fatherGroupId;  //上级用户组id
    private Double latitude;    //纬度
    private Double longitude;   //经度

    public Set<String> getBaseDataScope() {
        return baseDataScope;
    }

    public void setBaseDataScope(Set<String> baseDataScope) {
        this.baseDataScope = baseDataScope;
    }

    public Set<String> getFindDataScope() {
        return findDataScope;
    }

    public void setFindDataScope(Set<String> findDataScope) {
        this.findDataScope = findDataScope;
    }

    private Boolean personal;       //是否只显示个人信息(页面顶部有个是否个人的checkbox)
    ////////////////////////////////
    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        if (authorities == null) {
            authorities = new HashSet<>();
        }
        return authorities;
    }

    /**
     * 增加用户角色
     *
     * @param role
     */
    public void addAuthority(String role) {
        boolean hasRole = false;
        for (GrantedAuthority auth : getAuthorities()) {
            if (auth.getAuthority().equalsIgnoreCase(role)) {
                hasRole = true;
                break;
            }
        }
        if (!hasRole) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getName() {
        return name;
    }

    public String getFeatures() {
        return features;
    }

    public void setFeatures(String features) {
        this.features = features;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getLastBelongPCode() {
        return lastBelongPCode;
    }

    public void setLastBelongPCode(String lastBelongPCode) {
        this.lastBelongPCode = lastBelongPCode;
    }

    public String getLastBelongCode() {
        return lastBelongCode;
    }

    public void setLastBelongCode(String lastBelongCode) {
        this.lastBelongCode = lastBelongCode;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public String getUserIdStr() {
        return StringUtils.join(userIdSet.toArray(), ',');
    }

    public String getGroupUserIdStr() {
        return StringUtils.join(groupUserIdSet.toArray(), ',');
    }

    public String getLowGroupUserIdStr() {
        return StringUtils.join(lowGroupUserIdSet.toArray(), ',');
    }

    public String getSuperGroupUserIdStr() {
        return StringUtils.join(superGroupUserIdSet.toArray(), ',');
    }

    public String getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(String roleIds) {
        this.roleIds = roleIds;
    }

    public String getRoleNames() {
        return roleNames;
    }

    public Set<Long> getResourceIds() {
        return resourceIds;
    }

    public void setResourceIds(Set<Long> resourceIds) {
        this.resourceIds = resourceIds;
    }

    public void setRoleNames(String roleNames) {
        this.roleNames = roleNames;
    }

    public String getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(String roleCodes) {
        this.roleCodes = roleCodes;
    }

    public String getTopLogo() {
        return topLogo;
    }

    public void setTopLogo(String topLogo) {
        this.topLogo = topLogo;
    }

    public List<JSONObject> getTopResourceList() {
        return topResourceList;
    }

    public void setTopResourceList(List<JSONObject> topResourceList) {
        this.topResourceList = topResourceList;
    }

    public Map<String, List<JSONObject>> getSecondResourceMap() {
        return secondResourceMap;
    }

    public void setSecondResourceMap(Map<String, List<JSONObject>> secondResourceMap) {
        this.secondResourceMap = secondResourceMap;
    }

    public Map<String, List<JSONObject>> getThirdResourceMap() {
        return thirdResourceMap;
    }

    public void setThirdResourceMap(Map<String, List<JSONObject>> thirdResourceMap) {
        this.thirdResourceMap = thirdResourceMap;
    }

    public Set<String> getDataArea() {
        return dataArea;
    }

    public void setDataArea(Set<String> dataArea) {
        this.dataArea = dataArea;
    }

    public Integer getPublishShare() {
        return publishShare;
    }

    public void setPublishShare(Integer publishShare) {
        this.publishShare = publishShare;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getBelongLevel() {
        return belongLevel;
    }

    public void setBelongLevel(Integer belongLevel) {
        this.belongLevel = belongLevel;
    }

    public String getLastBelongProCode() {
        return lastBelongProCode;
    }

    public void setLastBelongProCode(String lastBelongProCode) {
        this.lastBelongProCode = lastBelongProCode;
    }

    public String getLastBelongCityCode() {
        return lastBelongCityCode;
    }

    public void setLastBelongCityCode(String lastBelongCityCode) {
        this.lastBelongCityCode = lastBelongCityCode;
    }

    public String getLastBelongCountyCode() {
        return lastBelongCountyCode;
    }

    public void setLastBelongCountyCode(String lastBelongCountyCode) {
        this.lastBelongCountyCode = lastBelongCountyCode;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<JSONObject> getSystemResourceList() {
        return systemResourceList;
    }

    public void setSystemResourceList(List<JSONObject> systemResourceList) {
        this.systemResourceList = systemResourceList;
    }

    public Set<Long> getUserIdSet() {
        return userIdSet;
    }

    public void setUserIdSet(Set<Long> userIdSet) {
        this.userIdSet = userIdSet;
    }

    public Set<Long> getGroupUserIdSet() {
        return groupUserIdSet;
    }

    public void setGroupUserIdSet(Set<Long> groupUserIdSet) {
        this.groupUserIdSet = groupUserIdSet;
    }

    public Set<Long> getLowGroupUserIdSet() {
        return lowGroupUserIdSet;
    }

    public void setLowGroupUserIdSet(Set<Long> lowGroupUserIdSet) {
        this.lowGroupUserIdSet = lowGroupUserIdSet;
    }

    public Set<Long> getSuperGroupUserIdSet() {
        return superGroupUserIdSet;
    }

    public void setSuperGroupUserIdSet(Set<Long> superGroupUserIdSet) {
        this.superGroupUserIdSet = superGroupUserIdSet;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public Long getFatherGroupId() {
        return fatherGroupId;
    }

    public void setFatherGroupId(Long fatherGroupId) {
        this.fatherGroupId = fatherGroupId;
    }

    public Boolean getPersonal() {
        return personal;
    }

    public void setPersonal(Boolean personal) {
        this.personal = personal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LoginUser loginUser = (LoginUser) o;

        return username.equals(loginUser.username);

    }

    @Override
    public int hashCode() {
        return username.hashCode();
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }
}
