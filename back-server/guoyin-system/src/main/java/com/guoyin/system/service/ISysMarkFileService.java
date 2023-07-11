package com.guoyin.system.service;

import com.guoyin.system.domain.SysMarkFile;

import java.util.List;

/**
 * 标注后文件列表Service接口
 *
 * @author czy
 * @date 2021-09-17
 */
public interface ISysMarkFileService
{
    /**
     * 查询标注后文件列表
     *
     * @param fileId 标注后文件列表主键
     * @return 标注后文件列表
     */
    public SysMarkFile selectSysMarkFileByFileId(Long fileId);

    /**
     * 查询标注后文件列表列表
     *
     * @param sysMarkFile 标注后文件列表
     * @return 标注后文件列表集合
     */
    public List<SysMarkFile> selectSysMarkFileList(SysMarkFile sysMarkFile);

    /**
     * 新增标注后文件列表
     *
     * @param sysMarkFile 标注后文件列表
     * @return 结果
     */
    public int insertSysMarkFile(SysMarkFile sysMarkFile);

    /**
     * 修改标注后文件列表
     *
     * @param sysMarkFile 标注后文件列表
     * @return 结果
     */
    public int updateSysMarkFile(SysMarkFile sysMarkFile);


    public int updateStateSysMarkFileByFileDirId(String state ,Long fileDirId );


    /**
     * 批量删除标注后文件列表
     *
     * @param fileIds 需要删除的标注后文件列表主键集合
     * @return 结果
     */
    public int deleteSysMarkFileByFileIds(String fileIds);

    /**
     * 删除标注后文件列表信息
     *
     * @param fileId 标注后文件列表主键
     * @return 结果
     */
    public int deleteSysMarkFileByFileId(Long fileId);

    /**
     * 查询标注后文件列表列表
     *
     * @param sysMarkFile 标注后文件列表
     * @return 标注后文件列表集合
     */
    public List<SysMarkFile> selectSysMarkFileListByUser(SysMarkFile sysMarkFile);

    /**
     *
     * 根据文件名和目录名确定唯一文件
     */
    public SysMarkFile selectSysMarkFileByFileNameAndFileDirId(String fileName, Long fileDirId);

}
