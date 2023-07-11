package com.guoyin.system.mapper;

import com.guoyin.system.domain.SysMarkfiledir;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 文件目录Mapper接口
 *
 * @author ruoyi
 * @date 2021-12-05
 */
@Repository
public interface SysMarkfiledirMapper
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
     * 删除文件目录
     *
     * @param filedirId 文件目录主键
     * @return 结果
     */
    public int deleteSysMarkfiledirByFiledirId(Long filedirId);

    /**
     * 批量删除文件目录
     *
     * @param filedirIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysMarkfiledirByFiledirIds(String[] filedirIds);

    /**
     * 根据目录ID查询信息
     *
     * @param filedirId 目录ID
     * @return 目录信息
     */
    public SysMarkfiledir selectMarkDirById(Long filedirId);

    /**
     * 根据目录ID查询信息
     *
     * @param rootPath 目录ID
     * @return 目录信息
     */
    public SysMarkfiledir selectDeptByRootPath(@Param("rootPath")String rootPath);

    /**
     * 新增目录信息并返回主键id
     *
     * @param sysMarkfiledir 目录信息
     * @return 结果
     */
    public Long insertDeptAndReturnId(SysMarkfiledir sysMarkfiledir);

    public List<SysMarkfiledir> selectChildrenAndMyselfDeptById(Long deptId);

}
