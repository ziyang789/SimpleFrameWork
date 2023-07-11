package com.guoyin.system.service.impl;

import com.guoyin.common.annotation.DataScope;
import com.guoyin.common.constant.UserConstants;
import com.guoyin.common.core.domain.Ztree;
import com.guoyin.common.core.text.Convert;
import com.guoyin.common.utils.DateUtils;
import com.guoyin.common.utils.StringUtils;
import com.guoyin.system.domain.SysFileDir;
import com.guoyin.system.mapper.SysFileDirMapper;
import com.guoyin.system.service.ISysFileDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件目录表Service业务层处理
 *
 * @author czy
 * @date 2021-09-17
 */
@Service
public class SysFileDirServiceImpl implements ISysFileDirService
{
    @Resource
    private SysFileDirMapper sysFileDirMapper;

    /**
     * 查询文件目录表
     *
     * @param filedirId 文件目录表主键
     * @return 文件目录表
     */
    @Override
    public SysFileDir selectSysFileDirByfiledirId(Long filedirId)
    {
        return sysFileDirMapper.selectSysFileDirByfiledirId(filedirId);
    }

    /**
     * 查询文件目录表
     *
     * @param filedirName 文件目录表主键
     * @return 文件目录表
     */
    @Override
    public SysFileDir selectSysFileDirByFileDirName(String filedirName)
    {
        return sysFileDirMapper.selectFileDirName(filedirName);
    }

    /**
     * 查询文件目录表列表
     *
     * @param sysFileDir 文件目录表
     * @return 文件目录表
     */
    @Override
    public List<SysFileDir> selectSysFileDirList(SysFileDir sysFileDir)
    {
        return sysFileDirMapper.selectSysFileDirList(sysFileDir);
    }

    /**
     * 新增文件目录表
     *
     * @param sysFileDir 文件目录表
     * @return 结果
     */
    @Override
    public int insertSysFileDir(SysFileDir sysFileDir)
    {
        sysFileDir.setCreateTime(DateUtils.getNowDate());
        return sysFileDirMapper.insertSysFileDir(sysFileDir);
    }

    /**
     * 修改文件目录表
     *
     * @param sysFileDir 文件目录表
     * @return 结果
     */
    @Override
    public int updateSysFileDir(SysFileDir sysFileDir)
    {
        sysFileDir.setUpdateTime(DateUtils.getNowDate());
        return sysFileDirMapper.updateSysFileDir(sysFileDir);
    }

    /**
     * 批量删除文件目录表
     *
     * @param filedirIds 需要删除的文件目录表主键
     * @return 结果
     */
    @Override
    public int deleteSysFileDirByfiledirIds(String filedirIds)
    {
        return sysFileDirMapper.deleteSysFileDirByfiledirIds(Convert.toStrArray(filedirIds));
    }

    /**
     * 删除文件目录表信息
     *
     * @param filedirId 文件目录表主键
     * @return 结果
     */
    @Override
    public int deleteSysFileDirByfiledirId(Long filedirId)
    {
        return sysFileDirMapper.deleteSysFileDirByfiledirId(filedirId);
    }

    //-------------------------------------------------------------
    /**
     * 查询部门管理树
     *
     * @param sysFileDir 部门信息
     * @return 所有部门信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<Ztree> selectDeptTree(SysFileDir sysFileDir)
    {
        List<SysFileDir> sysFileDirList = sysFileDirMapper.selectDeptList(sysFileDir);
        List<Ztree> ztrees = initZtree(sysFileDirList);
        return ztrees;
    }

    /**
     * 对象转部门树
     *
     * @param sysFileDirList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysFileDir> sysFileDirList)
    {
        return initZtree(sysFileDirList, null);
    }

    /**
     * 对象转部门树
     *
     * @param sysFileDirList 部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysFileDir> sysFileDirList, List<String> roleDeptList)
    {

        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (SysFileDir sysFileDir : sysFileDirList)
        {
            if (UserConstants.DEPT_NORMAL.equals(sysFileDir.getStatus()))
            {
                Ztree ztree = new Ztree();
                ztree.setId(sysFileDir.getfiledirId());
                ztree.setpId(sysFileDir.getParentId());
                ztree.setName(sysFileDir.getfiledirName());
                ztree.setTitle(sysFileDir.getfiledirName());
                if (isCheck)
                {
                    ztree.setChecked(roleDeptList.contains(sysFileDir.getfiledirId() + sysFileDir.getfiledirName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }
}
