package com.guoyin.system.mapper;

import com.guoyin.system.domain.SysFileDir;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件目录表Mapper接口
 *
 * @author czy
 * @date 2021-09-17
 */
@Repository
public interface SysFileDirMapper
{
    /**
     * 查询文件目录表
     *
     * @param filedirId 文件目录表主键
     * @return 文件目录表
     */
    public SysFileDir selectSysFileDirByfiledirId(Long filedirId);

    /**
     * 查询文件目录表
     *
     * @param parentId 文件目录表主键
     * @return 文件目录表
     */
    public SysFileDir selectSysFileDirByParentId(Long parentId);


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
     * 删除文件目录表
     *
     * @param filedirId 文件目录表主键
     * @return 结果
     */
    public int deleteSysFileDirByfiledirId(Long filedirId);

    /**
     * 批量删除文件目录表
     *
     * @param filedirIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysFileDirByfiledirIds(String[] filedirIds);

    /**
     * 查询部门管理数据
     *
     * @param sysFileDir 部门信息
     * @return 部门信息集合
     */
    public List<SysFileDir> selectDeptList(SysFileDir sysFileDir);

    /**
     * 查询目录名
     *
     * @param sysFileDirName
     * @return 目录id
     */
    public SysFileDir selectFileDirName(String sysFileDirName);
}
