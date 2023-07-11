package com.guoyin.system.factory;

import com.guoyin.common.config.RuoYiConfig;
import com.guoyin.common.utils.DateUtils;
import com.guoyin.system.domain.SysBeFile;
import com.guoyin.system.domain.SysDir;
import com.guoyin.system.mapper.SysDirMapper;
import com.guoyin.system.service.ISysBeFileService;
import com.guoyin.system.service.ISysDirService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.util.List;

/**
 * @program: ruoyi
 * @description:  刷新某目录ID下的子目录和文件
 * @author: ashinZhang
 * @create: 2021-12-02 20:20
 */
@Component
public class RefreshDirOrFileFactory1 {

    @Autowired
    ISysDirService sysDirService;

    @Autowired
    SysDirMapper sysDirMapper;

    @Autowired
    ISysBeFileService sysBeFileService;


    /**
     * @Date: 2021/12/2 14:47
     * @Author: ashinZhang
     * @Param: [filePath]  要刷新的服务器文件目录。 若该目录为空， 刷新根文件路径。
     * @return: void
     **/
    public void refreshDir(Long dirId) {

        if(ObjectUtils.isEmpty(dirId)){
            dirId = 100L;
        }
        //获取服务器中该文件的子目录
        SysDir sysDir = sysDirService.selectDeptById(dirId);
        File f = new File(sysDir.getAbsolutPath());
        refreshDb4Local(f,sysDir.getDeptId(),sysDir.getAncestors()+","+sysDir.getDeptId());
    }


    /**
     * @Date: 2021/12/2 14:47
     * @Author: ashinZhang
     * @Param: [filePath]  要刷新的服务器文件目录。 若该目录为空， 刷新根文件路径。
     * @return: void
     **/
    public void refreshDir2Controller(Long dirId) {

        if(ObjectUtils.isEmpty(dirId)){
            dirId = 100L;
        }
        //获取服务器中该文件的子目录
        SysDir sysDir = sysDirService.selectDeptById(dirId);
        File f = new File(sysDir.getAbsolutPath());
        refreshDb4Local(f,sysDir.getDeptId(),sysDir.getAncestors()+","+sysDir.getDeptId());
        deleteDb4Local(sysDir.getDeptId());
    }


    @Transactional(rollbackFor = Exception.class)
    public void refreshDb4Local(File f,Long parentId,String ancestors){
        File[] fs = f.listFiles();
        if (ObjectUtils.isEmpty(fs)){
            return;
        }

        for(File file:fs){
            if (file.isDirectory()){
                String filePath = file.getAbsolutePath();
                //根据绝对路径查询是否存在。
                SysDir sysDir = sysDirService.getDirByRootPath(filePath);
                if(ObjectUtils.isEmpty(sysDir)){
                    sysDir = new SysDir();
                    sysDir.setParentId(parentId);
                    sysDir.setDeptName(file.getName());
                    sysDir.setAncestors(ancestors);
                    sysDir.setAbsolutPath(filePath);
                    sysDir.setRelativePath(filePath.substring(RuoYiConfig.getProfile().length()));
                    sysDir.setCreateTime(DateUtils.getNowDate());
                    sysDirMapper.insertDeptAndReturnId(sysDir);
                }
                refreshDb4Local(file,sysDir.getDeptId(),sysDir.getAncestors()+","+sysDir.getDeptId());
            }else {
                SysBeFile sysBeFile =  sysBeFileService.selectSysBeFileByFileNameAndFileDirId(file.getName(),parentId);
                if(ObjectUtils.isEmpty(sysBeFile)){
                    sysBeFile = new SysBeFile();
                    sysBeFile.setfiledirId(parentId);
                    sysBeFile.setfilePath(file.getAbsolutePath());
                    sysBeFile.setfileSize(((file.length() / 1024) + ((file.length() % 1024) > 0 ? 1 : 0))+"KB");
                    sysBeFile.setfileName(file.getName());
                    sysBeFile.setCreateTime(DateUtils.getNowDate());
                    sysBeFile.setfileState("未下载");
                    sysBeFileService.insertSysBeFile(sysBeFile);
                }
            }
        }
    }



    /**
     * @Description: TODO 根据服务器目录删除数据库中无用目录。
     * @Date:  2021/12/4 15:45
     * @Author: ashinZhang
     * @Param: [dirId]
     * @return: void
     **/
    @Transactional(rollbackFor = Exception.class)
    public void deleteDb4Local(Long dirId){

        if (ObjectUtils.isEmpty(dirId)){
            dirId = 100L;
        }
        List<SysDir> sysDirs = sysDirService.selectChildrenAndMyselfDeptById(dirId);
        sysDirs.stream().forEach(foe->{
            File file = new File(foe.getAbsolutPath());
            if (!file.exists()){
                sysDirService.deleteChildrenDeptById(foe.getDeptId());
            }
        });

    }


}
