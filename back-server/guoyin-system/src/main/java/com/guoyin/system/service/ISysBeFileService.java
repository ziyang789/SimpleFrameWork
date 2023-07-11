package com.guoyin.system.service;

import com.guoyin.system.domain.SysBeFile;

import java.util.List;

/**
 * 标注前文件列表Service接口
 *
 * @author czy
 * @date 2021-09-17
 */
public interface ISysBeFileService
{
    /**
     * 查询标注前文件列表
     *
     * @param fileId 标注前文件列表主键
     * @return 标注前文件列表
     */
    public SysBeFile selectSysBeFileByFileId(Long fileId);



    /**
     * 查询标注前文件列表列表
     *
     * @param sysBeFile 标注前文件列表
     * @return 标注前文件列表集合
     */
    public List<SysBeFile> selectSysBeFileList(SysBeFile sysBeFile);


    /**
     *
     * 根据文件名和目录名确定唯一文件
     */
    public SysBeFile selectSysBeFileByFileNameAndFileDirId(String fileName,Long fileDirId);

    /**
     * 新增标注前文件列表
     *
     * @param sysBeFile 标注前文件列表
     * @return 结果
     */
    public int insertSysBeFile(SysBeFile sysBeFile);

    /**
     * 修改标注前文件列表
     *
     * @param sysBeFile 标注前文件列表
     * @return 结果
     */
    public int updateSysBeFile(SysBeFile sysBeFile);

    public int updateStateSysFileByFileDirId(String state ,Long fileDirId );



    /**
     * 批量删除标注前文件列表
     *
     * @param fileIds 需要删除的标注前文件列表主键集合
     * @return 结果
     */
    public int deleteSysBeFileByFileIds(String fileIds);

    /**
     * 删除标注前文件列表信息
     *
     * @param fileId 标注前文件列表主键
     * @return 结果
     */
    public int deleteSysBeFileByFileId(Long fileId);

    /**
     * 查询标注前文件列表列表
     *
     * @param sysBeFile 标注前文件列表
     * @return 标注前文件列表集合
     */
    public List<SysBeFile> selectSysBeFileListByUser(SysBeFile sysBeFile);

    /**
     * 修改标注前文件列表
     *
     * @param
     * @return 结果
     */
    public int updateSysBeFileToUser(String toUserName, String fileids);

    /**
     * 修改标注前文件列表的指派用户
     *
     * @param
     * @return 结果
     */
    public int updateSysBeFileByFileDir(String userId,String toUserName, String fileDirIds);
}
