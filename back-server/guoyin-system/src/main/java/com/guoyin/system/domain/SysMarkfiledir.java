package com.guoyin.system.domain;

import com.guoyin.common.annotation.Excel;
import com.guoyin.common.core.domain.TreeEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 文件目录对象 sys_markfiledir
 *
 * @author ruoyi
 * @date 2021-12-05
 */
public class SysMarkfiledir extends TreeEntity
{
    private static final long serialVersionUID = 1L;

    /** 目录id */
    private Long filedirId;

    /** 目录名称 */
    @Excel(name = "目录名称")
    private String filedirName;

    /** 绝对路径 */
    @Excel(name = "绝对路径")
    private String absolutPath;

    /** 相对路径 */
    @Excel(name = "相对路径")
    private String relativePath;

    /** 负责人 */
    @Excel(name = "负责人")
    private String leader;

    /** 联系电话 */
    @Excel(name = "联系电话")
    private String phone;

    /** 邮箱 */
    @Excel(name = "邮箱")
    private String email;

    /** 目录状态（0正常 1停用） */
    @Excel(name = "目录状态", readConverterExp = "0=正常,1=停用")
    private String status;

    /** 删除标志（0代表存在 2代表删除） */
    private String delFlag;

    public void setFiledirId(Long filedirId)
    {
        this.filedirId = filedirId;
    }

    public Long getFiledirId()
    {
        return filedirId;
    }
    public void setFiledirName(String filedirName)
    {
        this.filedirName = filedirName;
    }

    public String getFiledirName()
    {
        return filedirName;
    }
    public void setAbsolutPath(String absolutPath)
    {
        this.absolutPath = absolutPath;
    }

    public String getAbsolutPath()
    {
        return absolutPath;
    }
    public void setRelativePath(String relativePath)
    {
        this.relativePath = relativePath;
    }

    public String getRelativePath()
    {
        return relativePath;
    }
    public void setLeader(String leader)
    {
        this.leader = leader;
    }

    public String getLeader()
    {
        return leader;
    }
    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public String getPhone()
    {
        return phone;
    }
    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getEmail()
    {
        return email;
    }
    public void setStatus(String status)
    {
        this.status = status;
    }

    public String getStatus()
    {
        return status;
    }
    public void setDelFlag(String delFlag)
    {
        this.delFlag = delFlag;
    }

    public String getDelFlag()
    {
        return delFlag;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("filedirId", getFiledirId())
            .append("parentId", getParentId())
            .append("ancestors", getAncestors())
            .append("filedirName", getFiledirName())
            .append("absolutPath", getAbsolutPath())
            .append("relativePath", getRelativePath())
            .append("orderNum", getOrderNum())
            .append("leader", getLeader())
            .append("phone", getPhone())
            .append("email", getEmail())
            .append("status", getStatus())
            .append("delFlag", getDelFlag())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}
