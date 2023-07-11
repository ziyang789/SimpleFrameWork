package com.guoyin.system.service;

import com.guoyin.common.core.domain.Ztree;
import com.guoyin.system.domain.SysFileDir;

import java.util.List;

/**
 * 文件目录表Service接口
 *
 * @author czy
 * @date 2021-09-17
 */
public interface ISysFileDirService
{
    /**
     * 查询文件目录表
     *
     * @param filedirId 文件目录表主键
     * @return 文件目录表
     */
    public SysFileDir selectSysFileDirByfiledirId(Long filedirId);

    public SysFileDir selectSysFileDirByFileDirName(String filedirName);

    /**
     * 查询文件目录表列表
     *
     * @param sysFileDir 文件目录表
     * @return 文件目录表集合
     */
    public List<SysFileDir> selectSysFileDirList(SysFileDir sysFileDir);

    /**
     * 新增文件目录表
     *
     * @param sysFileDir 文件目录表
     * @return 结果
     */
    public int insertSysFileDir(SysFileDir sysFileDir);

    /**
     * 修改文件目录表
     *
     * @param sysFileDir 文件目录表
     * @return 结果
     */
    public int updateSysFileDir(SysFileDir sysFileDir);

    /**
     * 批量删除文件目录表
     *
     * @param filedirIds 需要删除的文件目录表主键集合
     * @return 结果
     */
    public int deleteSysFileDirByfiledirIds(String filedirIds);

    /**
     * 删除文件目录表信息
     *
     * @param filedirId 文件目录表主键
     * @return 结果
     */
    public int deleteSysFileDirByfiledirId(Long filedirId);

    //--------------------------------------------------------
    /**
     * 查询部门管理树
     *
     * @param sysFileDir 部门信息
     * @return 所有部门信息
     */
    public List<Ztree> selectDeptTree(SysFileDir sysFileDir);
}
