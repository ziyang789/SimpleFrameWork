package com.guoyin.system.mapper;

import com.guoyin.system.domain.SysDir;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 部门管理 数据层
 *
 * @author ruoyi
 */
@Repository
public interface SysDirMapper
{
    /**
     * 查询部门人数
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int selectDeptCount(SysDir dept);

    /**
     * 查询部门是否存在用户
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int checkDeptExistUser(Long deptId);

    /**
     * 查询部门管理数据
     *
     * @param dept 部门信息
     * @return 部门信息集合
     */
    public List<SysDir> selectDeptList(SysDir dept);

    /**
     * 删除部门管理信息
     *
     * @param deptId 部门ID
     * @return 结果
     */
    public int deleteDeptById(Long deptId);

    public int deleteChildrenDeptById(Long deptId);

    /**
     * 新增部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int insertDept(SysDir dept);

    /**
     * 新增部门信息并返回主键id
     *
     * @param dept 部门信息
     * @return 结果
     */
    public Long insertDeptAndReturnId(SysDir dept);


    /**
     * 修改部门信息
     *
     * @param dept 部门信息
     * @return 结果
     */
    public int updateDept(SysDir dept);

    /**
     * 修改子元素关系
     *
     * @param depts 子元素
     * @return 结果
     */
    public int updateDeptChildren(@Param("depts") List<SysDir> depts);

    /**
     * 根据部门ID查询信息
     *
     * @param deptId 部门ID
     * @return 部门信息
     */
    public SysDir selectDeptById(Long deptId);

    /**
     * 根据部门ID查询信息
     *
     * @param rootPath 部门ID
     * @return 部门信息
     */
    public SysDir selectDeptByRootPath(@Param("rootPath")String rootPath);


    /**
     * 校验部门名称是否唯一
     *
     * @param deptName 部门名称
     * @param parentId 父部门ID
     * @return 结果
     */
    public SysDir checkDeptNameUnique(@Param("filedirName") String deptName, @Param("parentId") Long parentId);

    /**
     * 根据角色ID查询部门
     *
     * @param roleId 角色ID
     * @return 部门列表
     */
    public List<String> selectRoleDeptTree(Long roleId);

    /**
     * 修改所在部门正常状态
     *
     * @param deptIds 部门ID组
     */
    public void updateDeptStatusNormal(Long[] deptIds);

    /**
     * 根据ID查询所有子部门
     *
     * @param deptId 部门ID
     * @return 部门列表
     */
    public List<SysDir> selectChildrenDeptById(Long deptId);

    public List<SysDir> selectChildrenAndMyselfDeptById(Long deptId);

    /**
     * 根据ID查询所有子部门（正常状态）
     *
     * @param deptId 部门ID
     * @return 子部门数
     */
    public int selectNormalChildrenDeptById(Long deptId);

}
