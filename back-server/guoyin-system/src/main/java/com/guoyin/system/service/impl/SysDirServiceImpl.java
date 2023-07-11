package com.guoyin.system.service.impl;

import cn.hutool.core.io.FileUtil;
import com.guoyin.common.annotation.DataScope;
import com.guoyin.common.config.RuoYiConfig;
import com.guoyin.common.constant.UserConstants;
import com.guoyin.common.core.domain.Ztree;
import com.guoyin.common.core.domain.entity.SysRole;
import com.guoyin.common.core.domain.entity.SysUser;
import com.guoyin.common.core.text.Convert;
import com.guoyin.common.exception.ServiceException;
import com.guoyin.common.utils.SecurityUtils;
import com.guoyin.common.utils.StringUtils;
import com.guoyin.common.utils.file.FileUtils;
import com.guoyin.common.utils.spring.SpringUtils;
import com.guoyin.system.domain.SysDir;
import com.guoyin.system.mapper.SysDirMapper;
import com.guoyin.system.mapper.SysFileDirMapper;
import com.guoyin.system.service.ISysDirService;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 部门管理 服务实现
 *
 * @author ruoyi
 */
@Service
public class SysDirServiceImpl implements ISysDirService {

    @Resource
    private SysDirMapper deptMapper;

    @Resource
    private SysFileDirMapper sysFileDirMapper;


    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<SysDir> selectDeptList(SysDir dept) {
        return deptMapper.selectDeptList(dept);
    }

    /**
     * 查询部门管理树
     *
     * @param dept 部门信息
     * @return 所有部门信息
     */
    @Override
//    @DataScope(deptAlias = "d")
    public List<Ztree> selectDeptTree(SysDir dept) {

        List<SysDir> deptList = deptMapper.selectDeptList(dept);
        Long l  = 100L;
// TODO: 2021/12/3   待做： 判断是否是学生true
        //判断当前用户是否存在管理员权限,若不存在查询当前用户所拥有的目录。
//        if (!ShiroUtils.getSysUser().isAdmin()) {
        List<SysRole> roles = SecurityUtils.getLoginUser().getUser().getRoles();
        if (String.valueOf(roles.get(0).getRoleId()).equals("2")) {
            deptList = deptList.stream().filter(fit ->
                    String.valueOf(SecurityUtils.getUserId()).equals(fit.getLeader()) || l.equals(fit.getDeptId())
            ).collect(Collectors.toList());
        }

        List<Ztree> ztrees = initZtree(deptList);
        return ztrees;
    }

    /**
     * 查询部门管理树（排除下级）
     *
     * @param dept 部门ID
     * @return 所有部门信息
     */
    @Override
    @DataScope(deptAlias = "d")
    public List<Ztree> selectDeptTreeExcludeChild(SysDir dept) {
        Long deptId = dept.getDeptId();
        List<SysDir> deptList = deptMapper.selectDeptList(dept);
        Iterator<SysDir> it = deptList.iterator();
        while (it.hasNext()) {
            SysDir d = (SysDir) it.next();
            if (d.getDeptId().intValue() == deptId
                    || ArrayUtils.contains(StringUtils.split(d.getAncestors(), ","), deptId + "")) {
                it.remove();
            }
        }
        List<Ztree> ztrees = initZtree(deptList);
        return ztrees;
    }

    /**
     * 根据角色ID查询部门（数据权限）
     *
     * @param role 角色对象
     * @return 部门列表（数据权限）
     */
    @Override
    public List<Ztree> roleDeptTreeData(SysRole role) {
        Long roleId = role.getRoleId();
        List<Ztree> ztrees = new ArrayList<Ztree>();
        List<SysDir> deptList = selectDeptList(new SysDir());
        if (StringUtils.isNotNull(roleId)) {
            List<String> roleDeptList = deptMapper.selectRoleDeptTree(roleId);
            ztrees = initZtree(deptList, roleDeptList);
        } else {
            ztrees = initZtree(deptList);
        }
        return ztrees;
    }

    /**
     * 对象转部门树
     *
     * @param deptList 部门列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysDir> deptList) {
        return initZtree(deptList, null);
    }

    /**
     * 对象转部门树
     *
     * @param deptList     部门列表
     * @param roleDeptList 角色已存在菜单列表
     * @return 树结构列表
     */
    public List<Ztree> initZtree(List<SysDir> deptList, List<String> roleDeptList) {

        List<Ztree> ztrees = new ArrayList<Ztree>();
        boolean isCheck = StringUtils.isNotNull(roleDeptList);
        for (SysDir dept : deptList) {
            if (UserConstants.DEPT_NORMAL.equals(dept.getStatus())) {
                Ztree ztree = new Ztree();
                ztree.setId(dept.getDeptId());
                ztree.setpId(dept.getParentId());
                ztree.setName(dept.getDeptName());
                ztree.setTitle(dept.getDeptName());
                if (isCheck) {
                    ztree.setChecked(roleDeptList.contains(dept.getDeptId() + dept.getDeptName()));
                }
                ztrees.add(ztree);
            }
        }
        return ztrees;
    }

    /**
     * 查询部门人数
     *
     * @param parentId 部门ID
     * @return 结果
     */
    @Override
    public int selectDeptCount(Long parentId) {
        SysDir dept = new SysDir();
        dept.setParentId(parentId);
        return deptMapper.selectDeptCount(dept);
    }

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    @Override
    public boolean checkDeptExistUser(Long deptId) {
        int result = deptMapper.checkDeptExistUser(deptId);
        return result > 0 ? true : false;
    }

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    @Override
    public int deleteDeptById(Long deptId) {
        String fileDirRootPath = getFileDirRootPathByFileDirId(deptId);
        try {
            FileUtil.del(FileUtil.file(fileDirRootPath));
        } catch (Exception e) {
            System.out.println("删除文件夹及其子文件失败！");
        }
        return deptMapper.deleteDeptById(deptId);
    }

    @Override
    public int deleteChildrenDeptById(Long deptId) {
        return deptMapper.deleteChildrenDeptById(deptId);
    }

    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public int insertDept(SysDir dept) {
        SysDir info = deptMapper.selectDeptById(dept.getParentId());
        // 如果父节点不为"正常"状态,则不允许新增子节点
        if (!UserConstants.DEPT_NORMAL.equals(info.getStatus())) {
            throw new ServiceException("部门停用，不允许新增");
        }
        dept.setAncestors(info.getAncestors() + "," + dept.getParentId());
        String path = "";
        if (dept.getParentId() != 100l){
            String fileDirRootPath = getFileDirRootPathByFileDirId(dept.getParentId());
            path = fileDirRootPath + File.separator + dept.getDeptName();
        }else {
            path = RuoYiConfig.getProfile() + File.separator + dept.getDeptName();
        }
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return deptMapper.insertDept(dept);
    }

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    @Transactional
    public int updateDept(SysDir dept) {
        SysDir newParentDept = deptMapper.selectDeptById(dept.getParentId());
        SysDir oldDept = selectDeptById(dept.getDeptId());
        //获取原来的文件夹名称
        String oldFileDirRootPath = getFileDirRootPathByFileDirId(dept.getDeptId());
        //更改文件目录名
        File file = new File(oldFileDirRootPath);
        String substring = oldFileDirRootPath.substring(0, oldFileDirRootPath.lastIndexOf("\\"));
        file.renameTo(new File(substring + File.separator + dept.getDeptName()));

        if (StringUtils.isNotNull(newParentDept) && StringUtils.isNotNull(oldDept)) {
            String newAncestors = newParentDept.getAncestors() + "," + newParentDept.getDeptId();
            String oldAncestors = oldDept.getAncestors();
            dept.setAncestors(newAncestors);
            updateDeptChildren(dept.getDeptId(), newAncestors, oldAncestors);
        }
        int result = deptMapper.updateDept(dept);
        if (UserConstants.DEPT_NORMAL.equals(dept.getStatus()) && StringUtils.isNotEmpty(dept.getAncestors())
                && !StringUtils.equals("0", dept.getAncestors())) {
            // 如果该部门是启用状态，则启用该部门的所有上级部门
            updateParentDeptStatusNormal(dept);
        }
        return result;
    }

    /**
     * 修改该部门的父级部门状态
     *
     * @param dept 当前部门
     */
    private void updateParentDeptStatusNormal(SysDir dept) {
        String ancestors = dept.getAncestors();
        Long[] deptIds = Convert.toLongArray(ancestors);
        deptMapper.updateDeptStatusNormal(deptIds);
    }

    /**
     * 修改子元素关系
     *
     * @param deptId       被修改的部门ID
     * @param newAncestors 新的父ID集合
     * @param oldAncestors 旧的父ID集合
     */
    public void updateDeptChildren(Long deptId, String newAncestors, String oldAncestors) {
        List<SysDir> children = deptMapper.selectChildrenDeptById(deptId);
        for (SysDir child : children) {
            child.setAncestors(child.getAncestors().replaceFirst(oldAncestors, newAncestors));
        }
        if (children.size() > 0) {
            deptMapper.updateDeptChildren(children);
        }
    }

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    @Override
    public SysDir selectDeptById(Long deptId) {
        return deptMapper.selectDeptById(deptId);
    }

    @Override
    public List<SysDir> selectDeptByParentId(Long parentId) {
        return deptMapper.selectChildrenDeptById(parentId);
    }

    @Override
    public List<SysDir> selectChildrenAndMyselfDeptById(Long parentId) {
        return deptMapper.selectChildrenAndMyselfDeptById(parentId);
    }


    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    @Override
    public int selectNormalChildrenDeptById(Long deptId) {
        return deptMapper.selectNormalChildrenDeptById(deptId);
    }

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    @Override
    public String checkDeptNameUnique(SysDir dept) {
        Long deptId = StringUtils.isNull(dept.getDeptId()) ? -1L : dept.getDeptId();
        SysDir info = deptMapper.checkDeptNameUnique(dept.getDeptName(), dept.getParentId());
        if (StringUtils.isNotNull(info) && info.getDeptId().longValue() != deptId.longValue()) {
            return UserConstants.DEPT_DISABLE;
        }
        return UserConstants.DEPT_NORMAL;
    }

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门id
     */
    @Override
    public void checkDeptDataScope(Long deptId) {
        if (!SysUser.isAdmin(SecurityUtils.getUserId())) {
            SysDir dept = new SysDir();
            dept.setDeptId(deptId);
            List<SysDir> depts = SpringUtils.getAopProxy(this).selectDeptList(dept);
            if (StringUtils.isEmpty(depts)) {
                throw new ServiceException("没有权限访问部门数据！");
            }
        }
    }

    @Override
    public String getFileDirPathByFileDirId(Long fileDirId) {
        String pathTmp = "";
        return getFileDirPath(fileDirId, pathTmp);

    }

    @Override
    public String getFileDirRootPathByFileDirId(Long fileDirId) {
        String pathTmp = "";
        return RuoYiConfig.getUploadPath() + getFileDirPath(fileDirId, pathTmp);
    }

    @Override
    public String getFileDirRootPathByMarkFileDirId(Long fileDirId) {
        String pathTmp = "";
        return RuoYiConfig.getUploadPath() + "_Mark" + getFileDirPath(fileDirId, pathTmp);
    }

    private String getFileDirPath(Long dirId, String path) {

        SysDir sysDir = deptMapper.selectDeptById(dirId);
        path = File.separator + sysDir.getDeptName() + path;
        dirId = sysDir.getParentId();
        if (!dirId.equals(100L)) {
            path = getFileDirPath(dirId, path);

        }
        return path;
    }


    @Override
    public SysDir getDirByRootPath(String rootPath) {
        return deptMapper.selectDeptByRootPath(rootPath);
    }
}
