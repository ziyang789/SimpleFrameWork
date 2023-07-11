package com.guoyin.system.service.impl;

import com.guoyin.common.core.text.Convert;
import com.guoyin.common.utils.DateUtils;
import com.guoyin.common.utils.file.FileUtils;
import com.guoyin.system.domain.SysMarkFile;
import com.guoyin.system.mapper.SysMarkFileMapper;
import com.guoyin.system.service.ISysMarkFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 标注后文件列表Service业务层处理
 *
 * @author czy
 * @date 2021-09-17
 */
@Service
public class SysMarkFileServiceImpl implements ISysMarkFileService
{
    @Resource
    private SysMarkFileMapper sysMarkFileMapper;

    /**
     * 查询标注后文件列表
     *
     * @param fileId 标注后文件列表主键
     * @return 标注后文件列表
     */
    @Override
    public SysMarkFile selectSysMarkFileByFileId(Long fileId)
    {
        return sysMarkFileMapper.selectSysMarkFileByFileId(fileId);
    }

    /**
     * 查询标注后文件列表列表
     *
     * @param sysMarkFile 标注后文件列表
     * @return 标注后文件列表
     */
    @Override
    public List<SysMarkFile> selectSysMarkFileList(SysMarkFile sysMarkFile)
    {
        return sysMarkFileMapper.selectSysMarkFileList(sysMarkFile);
    }

    /**
     * 新增标注后文件列表 上传文件信息
     *
     * @param sysMarkFile 标注后文件列表
     * @return 结果
     */
    @Override
    public int insertSysMarkFile(SysMarkFile sysMarkFile)
    {
        sysMarkFile.setCreateTime(DateUtils.getNowDate());
        return sysMarkFileMapper.insertSysMarkFile(sysMarkFile);
    }

    /**
     * 修改标注后文件列表
     *
     * @param sysMarkFile 标注后文件列表
     * @return 结果
     */
    @Override
    public int updateSysMarkFile(SysMarkFile sysMarkFile)
    {
//        sysMarkFile.setUpdateTime(DateUtils.getNowDate());
        return sysMarkFileMapper.updateSysMarkFile(sysMarkFile);
    }

    @Override
    public int updateStateSysMarkFileByFileDirId(String state, Long fileDirId) {
        return sysMarkFileMapper.updateStateSysMarkFileByFileDirId(state,fileDirId);
    }

    /**
     * 批量删除标注后文件列表
     *
     * @param fileIds 需要删除的标注后文件列表主键
     * @return 结果
     */
    @Override
    public int deleteSysMarkFileByFileIds(String fileIds)
    {
        List<SysMarkFile> sysMarkFile = new ArrayList<SysMarkFile>();
        String[] toStrArray = Convert.toStrArray(fileIds);
        for (int i = 0; i < toStrArray.length; i++) {
            SysMarkFile file = sysMarkFileMapper.selectSysMarkFileByFileId(Long.valueOf(toStrArray[i]));
            sysMarkFile.add(file);
        }
        updateFileName(sysMarkFile);
        return sysMarkFileMapper.deleteSysMarkFileByFileIds(Convert.toStrArray(fileIds));
    }

    /**
     * 文件进行逻辑删除的同时，修改服务器中被删除文件的名称
     * 具体修改为  *（已删除）.*
     */
    private void updateFileName(List<SysMarkFile> sysMarkFile){
        for (SysMarkFile file : sysMarkFile) {
            String path = file.getfilePath();
            String name = file.getfileName();
            String newFileName = name.substring(0,name.indexOf(".")) + "(已删除)";
            FileUtils.FixFileName(path,newFileName);
        }
    }

    /**
     * 删除标注后文件列表信息
     *
     * @param fileId 标注后文件列表主键
     * @return 结果
     */
    @Override
    public int deleteSysMarkFileByFileId(Long fileId)
    {
        return sysMarkFileMapper.deleteSysMarkFileByFileId(fileId);
    }

    /**
     * 查询标注后文件列表列表
     *
     * @param sysMarkFile 标注后文件列表
     * @return 标注后文件列表
     */
    @Override
    public List<SysMarkFile> selectSysMarkFileListByUser(SysMarkFile sysMarkFile)
    {
        return sysMarkFileMapper.selectSysMarkFileListByUser(sysMarkFile);
    }

    @Override
    public SysMarkFile selectSysMarkFileByFileNameAndFileDirId(String fileName, Long fileDirId) {
        return sysMarkFileMapper.selectSysMarkFileByFileNameAndFileDirId(fileName,fileDirId);
    }
}
