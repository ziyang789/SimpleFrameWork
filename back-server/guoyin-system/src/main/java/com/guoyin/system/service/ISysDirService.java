package com.guoyin.system.service;

import com.guoyin.common.core.domain.Ztree;
import com.guoyin.common.core.domain.entity.SysRole;
import com.guoyin.system.domain.SysDir;

import java.util.List;

/**
 * 部门管理 服务层
 *
 * @author ruoyi
 */
public interface ISysDirService {
    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDir> selectDeptList(SysDir dept);

    /**
     * 查询部门管理树
     *
     * @param dept 部门信息
     * @return 所有部门信息
     */
    public List<Ztree> selectDeptTree(SysDir dept);

    /**
     * 查询部门管理树（排除下级）
     *
     * @param dept 部门信息
     * @return 所有部门信息
     */
    public List<Ztree> selectDeptTreeExcludeChild(SysDir dept);

    /**
     * 根据角色ID查询菜单
     *
     * @param role 角色对象
     * @return 菜单列表
     */
    public List<Ztree> roleDeptTreeData(SysRole role);

    /**
     * 查询部门人数
     *
     * @param parentId 父部门ID
     * @return 结果
     */
    public int selectDeptCount(Long parentId);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果 true 存在 false 不存在
     */
    public boolean checkDeptExistUser(Long deptId);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);


    /**
     * 删除子部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteChildrenDeptById(Long deptId);




    /**
     * 新增保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(SysDir dept);

    /**
     * 修改保存部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(SysDir dept);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDir selectDeptById(Long deptId);

    /**
     * 根据部门父ID查询信息
     *
     * @return 部门信息
     */
    public List<SysDir> selectDeptByParentId(Long parentId);


    public List<SysDir> selectChildrenAndMyselfDeptById(Long parentId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenDeptById(Long deptId);

    /**
     * 校验部门名称是否唯一
     *
     * @param dept 部门信息
     * @return 结果
     */
    public String checkDeptNameUnique(SysDir dept);

    /**
     * 校验部门是否有数据权限
     *
     * @param deptId 部门id
     */
    public void checkDeptDataScope(Long deptId);


    /**
     * 根据目录id,获取项目相对路径
     *
     * @param fileDirId 目录id
     * @return 例： /病人A/2021年12月2日
     */
    public String getFileDirPathByFileDirId(Long fileDirId);


    /**
     * 根据目录id,获取项目绝对路径
     *
     * @param fileDirId 目录id
     * @return 例： e:/医学图像/病人A/2021年12月2日
     */
    public String getFileDirRootPathByFileDirId(Long fileDirId);

    /**
     * 根据目录id,获取项目绝对路径
     *
     * @param fileDirId 目录id
     * @return 例： e:/医学图像/病人A/2021年12月2日
     */
    public String getFileDirRootPathByMarkFileDirId(Long fileDirId);

    /**
     * 根据绝对路径查询目录表
     */
    public SysDir getDirByRootPath(String rootPath);


}
