package com.guoyin.system.domain;

import com.guoyin.common.annotation.Excel;
import com.guoyin.common.annotation.Excels;
import com.guoyin.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 标注前文件列表对象 sys_BeFile
 *
 * @author czy
 * @date 2021-09-17
 */
public class SysBeFile extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 文件ID */
    private Long fileId;

    /** 目录ID */
    @Excel(name = "当前目录")
    private Long filedirId;

    /** 目录父ID */
    private Long parentId;

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    /** 文件名 */
    @Excel(name = "文件名")
    private String fileName;

    /** 文件大小 */
    @Excel(name = "文件大小")
    private String fileSize;

    /** 文件路径 */
    @Excel(name = "文件路径")
    private String filePath;

    /** 分派用户 */
    @Excel(name = "分派用户")
    private String toUser;

    /** 文件状态 */
    @Excel(name = "文件状态")
    private String fileState;

    /** 文件目录对象 */
    @Excels({
            @Excel(name = "目录名称", targetAttr = "filedirName", type = Excel.Type.EXPORT)
    })
    private SysDir sysDir;

    public SysDir getSysDir()
    {
        if (sysDir == null)
        {
            sysDir = new SysDir();
        }
        return sysDir;
    }

    public void setSysDir(SysDir sysDir) {
        this.sysDir = sysDir;
    }

    public void setfileId(Long fileId)
    {
        this.fileId = fileId;
    }

    public Long getfileId()
    {
        return fileId;
    }
    public void setfiledirId(Long filedirId)
    {
        this.filedirId = filedirId;
    }

    public Long getfiledirId()
    {
        return filedirId;
    }
    public void setfileName(String fileName)
    {
        this.fileName = fileName;
    }

    public String getfileName()
    {
        return fileName;
    }
    public void setfileSize(String fileSize)
    {
        this.fileSize = fileSize;
    }

    public String getfileSize()
    {
        return fileSize;
    }
    public void setfilePath(String filePath)
    {
        this.filePath = filePath;
    }

    public String getfilePath()
    {
        return filePath;
    }
    public void settoUser(String toUser)
    {
        this.toUser = toUser;
    }

    public String gettoUser()
    {
        return toUser;
    }
    public void setfileState(String fileState)
    {
        this.fileState = fileState;
    }

    public String getfileState()
    {
        return fileState;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("fileId", getfileId())
            .append("filedirId", getfiledirId())
            .append("fileName", getfileName())
            .append("fileSize", getfileSize())
            .append("filePath", getfilePath())
            .append("toUser", gettoUser())
            .append("fileState", getfileState())
            .append("createTime", getCreateTime())
            .append("remark", getRemark())
            .append("filedir", getSysDir())
            .toString();
    }
}
