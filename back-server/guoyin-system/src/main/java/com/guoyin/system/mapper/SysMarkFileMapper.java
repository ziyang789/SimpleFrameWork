package com.guoyin.system.mapper;

import com.guoyin.system.domain.SysMarkFile;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标注后文件列表Mapper接口
 *
 * @author czy
 * @date 2021-09-17
 */
@Repository
public interface SysMarkFileMapper
{
    /**
     * 查询文件列表
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



    public int updateStateSysMarkFileByFileDirId(@Param("file_state") String state, @Param("filedirId") Long filedirId);




    /**
     * 删除标注后文件列表
     *
     * @param fileId 标注后文件列表主键
     * @return 结果
     */
    public int deleteSysMarkFileByFileId(Long fileId);

    /**
     * 批量删除标注后文件列表
     *
     * @param fileIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteSysMarkFileByFileIds(String[] fileIds);

    /**
     * 查询标注后文件列表列表
     *
     * @param sysMarkFile 标注后文件列表
     * @return 标注后文件列表集合
     */
    public List<SysMarkFile> selectSysMarkFileListByUser(SysMarkFile sysMarkFile);


    public SysMarkFile selectSysMarkFileByFileNameAndFileDirId(@Param("fileName") String fileName, @Param("fileDirId") Long fileDirId);

}
