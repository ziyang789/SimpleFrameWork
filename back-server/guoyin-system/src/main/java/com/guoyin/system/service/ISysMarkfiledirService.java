package com.guoyin.system.service;

import com.guoyin.common.core.domain.Ztree;
import com.guoyin.system.domain.SysMarkfiledir;

import java.util.List;

/**
 * 文件目录Service接口
 *
 * @author ruoyi
 * @date 2021-12-05
 */
public interface ISysMarkfiledirService
{
    /**
     * 查询文件目录
     *
     * @param filedirId 文件目录主键
     * @return 文件目录
     */
    public SysMarkfiledir selectSysMarkfiledirByFiledirId(Long filedirId);

    /**
     * 查询文件目录列表
     *
     * @param sysMarkfiledir 文件目录
     * @return 文件目录集合
     */
    public List<SysMarkfiledir> selectSysMarkfiledirList(SysMarkfiledir sysMarkfiledir);

    /**
     * 新增文件目录
     *
     * @param sysMarkfiledir 文件目录
     * @return 结果
     */
    public int insertSysMarkfiledir(SysMarkfiledir sysMarkfiledir);

    /**
     * 修改文件目录
     *
     * @param sysMarkfiledir 文件目录
     * @return 结果
     */
    public int updateSysMarkfiledir(SysMarkfiledir sysMarkfiledir);

    /**
     * 批量删除文件目录
     *
     * @param filedirIds 需要删除的文件目录主键集合
     * @return 结果
     */
    public int deleteSysMarkfiledirByFiledirIds(String filedirIds);

    /**
     * 删除文件目录信息
     *
     * @param filedirId 文件目录主键
     * @return 结果
     */
    public int deleteSysMarkfiledirByFiledirId(Long filedirId);

    /**
     * 查询文件目录树列表
     *
     * @return 所有文件目录信息
     */
    public List<Ztree> selectSysMarkfiledirTree();

    /**
     * 根据部门ID查询信息
     *
     * @param filedirId 部门ID
     * @return 部门信息
     */
    public SysMarkfiledir selectMarkDirById(Long filedirId);


    /**
     * 根据绝对路径查询目录表
     */
    public SysMarkfiledir getDirByRootPath(String rootPath);

    /**
     * 根据目录id,获取项目绝对路径
     *
     * @param fileDirId 目录id
     * @return 例： e:/医学图像/病人A/2021年12月2日
     */
    public String getFileDirRootPathByMarkFileDirId(Long fileDirId);

    public List<SysMarkfiledir> selectChildrenAndMyselfDeptById(Long parentId);

}
