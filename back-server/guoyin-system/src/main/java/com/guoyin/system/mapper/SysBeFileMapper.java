package com.guoyin.system.mapper;

import com.guoyin.system.domain.SysBeFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标注前文件列表Mapper接口
 *
 * @author czy
 * @date 2021-09-17
 */
@Repository
public interface SysBeFileMapper
{
    /**
     * 查询标注前文件列表
     *
     * @param fileId 标注前文件列表主键
     * @return 标注前文件列表
     */
    public SysBeFile selectSysBeFileByFileId(Long fileId);

    public List<SysBeFile> selectSysBeFile(SysBeFile sysBeFile);

    /**
     * 查询标注前文件列表
     *
     * @param sysBeFile 标注前文件列表
     * @return 标注前文件列表集合
     */
    public List<SysBeFile> selectSysBeFileList(SysBeFile sysBeFile);

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

    public int updateToUseByFileDirId(SysBeFile sysBeFile);


    public int updateStateSysFileByFileDirId(@Param("file_state") String state, @Param("filedirId") Long filedirId);


    /**
     * 删除标注前文件列表
     *
     * @param fileId 标注前文件列表主键
     * @return 结果
     */
    public int deleteSysBeFileByFileId(Long fileId);

    /**
     * 批量删除标注前文件列表
     *
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysBeFileByFileIds(String[] fileIds);

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
     * @param sysBeFile 标注前文件列表
     * @return 结果
     */
    public int updateSysBeFileTouser(SysBeFile sysBeFile);

    /**
     * 根据文件名和目录名确定唯一文件
     *
     */
    public SysBeFile selectSysBeFileByFileNameAndFileDirId(@Param("fileName") String fileName, @Param("fileDirId") Long fileDirId);

    /**
     * 根据文件夹ID（fileDirId）修改标注前文件列表
     * @param sysBeFile
     * @return
     */
    public int updateSysBeFileByFileDir(SysBeFile sysBeFile);
}
