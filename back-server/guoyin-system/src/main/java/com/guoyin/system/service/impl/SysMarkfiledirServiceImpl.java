package com.guoyin.system.service.impl;

import com.guoyin.common.core.domain.Ztree;
import com.guoyin.common.core.text.Convert;
import com.guoyin.common.utils.DateUtils;
import com.guoyin.system.domain.SysMarkfiledir;
import com.guoyin.system.mapper.SysMarkfiledirMapper;
import com.guoyin.system.service.ISysMarkfiledirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件目录Service业务层处理
 *
 * @author ruoyi
 * @date 2021-12-05
 */
@Service
public class SysMarkfiledirServiceImpl implements ISysMarkfiledirService
{
    @Resource
    private SysMarkfiledirMapper sysMarkfiledirMapper;

    /**
     * 查询文件目录
     *
     * @param filedirId 文件目录主键
     * @return 文件目录
     */
    @Override
    public SysMarkfiledir selectSysMarkfiledirByFiledirId(Long filedirId)
    {
        return sysMarkfiledirMapper.selectSysMarkfiledirByFiledirId(filedirId);
    }

    /**
     * 查询文件目录列表
     *
     * @param sysMarkfiledir 文件目录
     * @return 文件目录
     */
    @Override
    public List<SysMarkfiledir> selectSysMarkfiledirList(SysMarkfiledir sysMarkfiledir)
    {
        return sysMarkfiledirMapper.selectSysMarkfiledirList(sysMarkfiledir);
    }

    /**
     * 新增文件目录
     *
     * @param sysMarkfiledir 文件目录
     * @return 结果
     */
    @Override
    public int insertSysMarkfiledir(SysMarkfiledir sysMarkfiledir)
    {
        sysMarkfiledir.setCreateTime(DateUtils.getNowDate());
        return sysMarkfiledirMapper.insertSysMarkfiledir(sysMarkfiledir);
    }

    /**
     * 修改文件目录
     *
     * @param sysMarkfiledir 文件目录
     * @return 结果
     */
    @Override
    public int updateSysMarkfiledir(SysMarkfiledir sysMarkfiledir)
    {
        sysMarkfiledir.setUpdateTime(DateUtils.getNowDate());
        return sysMarkfiledirMapper.updateSysMarkfiledir(sysMarkfiledir);
    }

    /**
     * 批量删除文件目录
     *
     * @param filedirIds 需要删除的文件目录主键
     * @return 结果
     */
    @Override
    public int deleteSysMarkfiledirByFiledirIds(String filedirIds)
    {
        return sysMarkfiledirMapper.deleteSysMarkfiledirByFiledirIds(Convert.toStrArray(filedirIds));
    }

    /**
     * 删除文件目录信息
     *
     * @param filedirId 文件目录主键
     * @return 结果
     */
    @Override
    public int deleteSysMarkfiledirByFiledirId(Long filedirId)
    {
        return sysMarkfiledirMapper.deleteSysMarkfiledirByFiledirId(filedirId);
    }

    /**
     * 查询文件目录树列表
     *
     * @return 所有文件目录信息
     */
    @Override
    public List<Ztree> selectSysMarkfiledirTree()
    {
        List<SysMarkfiledir> sysMarkfiledirList = sysMarkfiledirMapper.selectSysMarkfiledirList(new SysMarkfiledir());
        List<Ztree> ztrees = new ArrayList<Ztree>();
        for (SysMarkfiledir sysMarkfiledir : sysMarkfiledirList)
        {
            Ztree ztree = new Ztree();
            ztree.setId(sysMarkfiledir.getFiledirId());
            ztree.setpId(sysMarkfiledir.getParentId());
            ztree.setName(sysMarkfiledir.getFiledirName());
            ztree.setTitle(sysMarkfiledir.getFiledirName());
            ztrees.add(ztree);
        }
        return ztrees;
    }

    /**
     * 根据目录ID查询信息
     *
     * @param filedirId 目录ID
     * @return 目录信息
     */
    @Override
    public SysMarkfiledir selectMarkDirById(Long filedirId) {
        return sysMarkfiledirMapper.selectMarkDirById(filedirId);
    }

    @Override
    public SysMarkfiledir getDirByRootPath(String rootPath) {
        return sysMarkfiledirMapper.selectDeptByRootPath(rootPath);
    }

    @Override
    public String getFileDirRootPathByMarkFileDirId(Long fileDirId) {
        String pathTmp = "";
        return "E:\\标注后图像" + getFileDirPath(fileDirId, pathTmp);
    }
    private String getFileDirPath(Long dirId, String path) {

        SysMarkfiledir sysDir = sysMarkfiledirMapper.selectMarkDirById(dirId);
        path = File.separator + sysDir.getFiledirName() + path;
        dirId = sysDir.getParentId();
        if (!dirId.equals(100L)) {
            path = getFileDirPath(dirId, path);

        }
        return path;
    }

    @Override
    public List<SysMarkfiledir> selectChildrenAndMyselfDeptById(Long parentId) {
        return sysMarkfiledirMapper.selectChildrenAndMyselfDeptById(parentId);
    }
}
