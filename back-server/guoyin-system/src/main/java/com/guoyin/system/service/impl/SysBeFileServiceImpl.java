package com.guoyin.system.service.impl;

import com.guoyin.common.core.text.Convert;
import com.guoyin.common.utils.DateUtils;
import com.guoyin.common.utils.file.FileUtils;
import com.guoyin.system.domain.SysBeFile;
import com.guoyin.system.domain.SysDir;
import com.guoyin.system.mapper.SysBeFileMapper;
import com.guoyin.system.mapper.SysDirMapper;
import com.guoyin.system.service.ISysBeFileService;
import com.guoyin.system.service.ISysDirService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 标注前文件列表Service业务层处理
 *
 * @author czy
 * @date 2021-09-17
 */
@Service
public class SysBeFileServiceImpl implements ISysBeFileService
{
    private static final Logger log = LoggerFactory.getLogger(SysBeFileServiceImpl.class);

    @Resource
    private ISysDirService sysDirService;

    @Resource
    private SysDirMapper deptMapper;

    @Resource
    private SysBeFileMapper sysBeFileMapper;

    /**
     * 查询标注前文件列表
     *
     * @param fileId 标注前文件列表主键
     * @return 标注前文件列表
     */
    @Override
    public SysBeFile selectSysBeFileByFileId(Long fileId)
    {
        return sysBeFileMapper.selectSysBeFileByFileId(fileId);
    }



    /**
     * 根据文件名和目录名确定唯一文件
     *
     */
    @Override
    public SysBeFile selectSysBeFileByFileNameAndFileDirId(String fileName,Long fileDirId)
    {
        return sysBeFileMapper.selectSysBeFileByFileNameAndFileDirId(fileName,fileDirId);
    }

    /**
     * 查询标注前文件列表列表
     *
     * @param sysBeFile 标注前文件列表
     * @return 标注前文件列表
     */
    @Override
    public List<SysBeFile> selectSysBeFileList(SysBeFile sysBeFile)
    {
        return sysBeFileMapper.selectSysBeFileList(sysBeFile);
    }

    /**
     * 新增标注前文件列表 上传文件信息
     *
     * @param sysBeFile 标注前文件列表
     * @return 结果
     */
    @Override
    public int insertSysBeFile(SysBeFile sysBeFile)
    {
        sysBeFile.setCreateTime(DateUtils.getNowDate());
        return sysBeFileMapper.insertSysBeFile(sysBeFile);
    }

    /**
     * 修改标注前文件列表
     *
     * @param sysBeFile 标注前文件列表
     * @return 结果
     */
    @Override
    public int updateSysBeFile(SysBeFile sysBeFile)
    {
//        sysBeFile.setUpdateTime(DateUtils.getNowDate());
        return sysBeFileMapper.updateSysBeFile(sysBeFile);
    }

    @Override
    public int updateStateSysFileByFileDirId(String state, Long fileDirId) {
        return sysBeFileMapper.updateStateSysFileByFileDirId(state,fileDirId);
    }

    /**
     * 批量删除标注前文件列表
     *
     * @param fileIds 需要删除的标注前文件列表主键
     * @return 结果
     */
    @Override
    public int deleteSysBeFileByFileIds(String fileIds)
    {
        List<SysBeFile> sysBeFiles = new ArrayList<SysBeFile>();
        String[] toStrArray = Convert.toStrArray(fileIds);
        for (int i = 0; i < toStrArray.length; i++) {
            SysBeFile file = sysBeFileMapper.selectSysBeFileByFileId(Long.valueOf(toStrArray[i]));
            sysBeFiles.add(file);
        }
        updateFileName(sysBeFiles);
        return sysBeFileMapper.deleteSysBeFileByFileIds(Convert.toStrArray(fileIds));
    }

    /**
     * 文件进行逻辑删除的同时，修改服务器中被删除文件的名称
     * 具体修改为  *（已删除）.*
     */
    private void updateFileName(List<SysBeFile> beFilesList){
        for (SysBeFile file : beFilesList) {
            String path = file.getfilePath();
            String name = file.getfileName();
            String newFileName = name.substring(0,name.lastIndexOf(".")) + "(已删除)";
            FileUtils.FixFileName(path,newFileName);
        }
    }

    /**
     * 删除标注前文件列表信息
     *
     * @param fileId 标注前文件列表主键
     * @return 结果
     */
    @Override
    public int deleteSysBeFileByFileId(Long fileId)
    {
        return sysBeFileMapper.deleteSysBeFileByFileId(fileId);
    }

    /**
     * 查询标注前文件列表列表
     *
     * @param sysBeFile 标注前文件列表
     * @return 标注前文件列表
     */
    @Override
    public List<SysBeFile> selectSysBeFileListByUser(SysBeFile sysBeFile)
    {
        return sysBeFileMapper.selectSysBeFileListByUser(sysBeFile);
    }

    /**
     * 修改标注前文件列表的指派用户
     *
     * @param
     * @return 结果
     */
    @Override
    public int updateSysBeFileToUser(String toUserName, String fileids)
    {
        SysBeFile sysBeFile = new SysBeFile();
        int in = 0;
        String[] strings = fileids.split(",");
        for (int i = 0; i < strings.length; i++) {
            sysBeFile.settoUser(toUserName);
            sysBeFile.setfileId(Long.parseLong(strings[i]));
            in = sysBeFileMapper.updateSysBeFileTouser(sysBeFile);
        }
        return in;
    }

    /**
     * 修改标注前文件列表的指派用户
     *
     * @param
     * @return 结果
     */
    @Override
    public int updateSysBeFileByFileDir(String userId,String toUserName, String fileDirIds)
    {
        String[] strings = fileDirIds.split(",");
        int in = 0;
        for (int i = 0; i < strings.length; i++) {
            Long parentId = Long.parseLong(strings[i]);
            List<SysDir> sysDirs =  sysDirService.selectChildrenAndMyselfDeptById(parentId);
            for (SysDir sysDir:sysDirs) {

                //记录文件根节点目录的 分派人
                sysDir.setLeader(userId);
                int result = deptMapper.updateDept(sysDir);
                log.info("记录文件根节点目录的 分派人 result " + result + " = " + toUserName);

                //记录文件的分派人
                SysBeFile sysBeFile = new SysBeFile();
                sysBeFile.settoUser(toUserName);
                sysBeFile.setfiledirId(sysDir.getDeptId());
                in = sysBeFileMapper.updateToUseByFileDirId(sysBeFile);
            }
        }
        return in;
    }
}
