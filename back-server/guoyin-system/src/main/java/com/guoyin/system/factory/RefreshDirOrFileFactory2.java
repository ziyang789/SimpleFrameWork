package com.guoyin.system.factory;

import com.guoyin.common.config.RuoYiConfig;
import com.guoyin.common.utils.DateUtils;
import com.guoyin.system.domain.SysMarkFile;
import com.guoyin.system.domain.SysMarkfiledir;
import com.guoyin.system.mapper.SysDirMapper;
import com.guoyin.system.mapper.SysMarkfiledirMapper;
import com.guoyin.system.service.ISysBeFileService;
import com.guoyin.system.service.ISysDirService;
import com.guoyin.system.service.ISysMarkFileService;
import com.guoyin.system.service.ISysMarkfiledirService;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.File;
import java.util.List;

/**
 * @program: ruoyi
 * @description:  刷新某目录ID下的子目录和文件
 * @author: ashinZhang
 * @create: 2021-12-02 20:20
 */
@Component
public class RefreshDirOrFileFactory2 {

    @Resource
    ISysDirService sysDirService;

    @Resource
    ISysMarkfiledirService sysMarkfiledirService;

    @Resource
    SysDirMapper sysDirMapper;

    @Resource
    SysMarkfiledirMapper sysMarkfiledirMapper;

    @Resource
    ISysBeFileService sysBeFileService;

    @Resource
    ISysMarkFileService sysMarkFileService;

    /**
     * @Description: TODO
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
        SysMarkfiledir sysMarkfiledir = sysMarkfiledirService.selectMarkDirById(dirId);
        File f = new File(sysMarkfiledir.getAbsolutPath());
        refreshDb4Local(f,sysMarkfiledir.getFiledirId(),sysMarkfiledir.getAncestors()+","+sysMarkfiledir.getFiledirId());
    }


    /**
     * @Description: TODO
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
        SysMarkfiledir sysDir = sysMarkfiledirService.selectMarkDirById(dirId);
        File f = new File(sysDir.getAbsolutPath());
        refreshDb4Local(f,sysDir.getFiledirId(),sysDir.getAncestors()+","+sysDir.getFiledirId());
        deleteDb4Local(sysDir.getFiledirId());
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
                SysMarkfiledir sysDir = sysMarkfiledirService.getDirByRootPath(filePath);
                if(ObjectUtils.isEmpty(sysDir)){
                    sysDir = new SysMarkfiledir();
                    sysDir.setParentId(parentId);
                    sysDir.setFiledirName(file.getName());
                    sysDir.setAncestors(ancestors);
                    sysDir.setAbsolutPath(filePath);
                    sysDir.setRelativePath(filePath.substring(RuoYiConfig.getProfile().length()));
                    sysDir.setCreateTime(DateUtils.getNowDate());
                    sysMarkfiledirMapper.insertDeptAndReturnId(sysDir);
                }
                refreshDb4Local(file,sysDir.getFiledirId(),sysDir.getAncestors()+","+sysDir.getFiledirId());
            }else {
                //todo
                SysMarkFile sysBeFile =  sysMarkFileService.selectSysMarkFileByFileNameAndFileDirId(file.getName(),parentId);
                if(ObjectUtils.isEmpty(sysBeFile)){
                    sysBeFile = new SysMarkFile();
                    sysBeFile.setfiledirId(parentId);
                    sysBeFile.setfilePath(file.getAbsolutePath());
                    sysBeFile.setfileSize(((file.length() / 1024) + ((file.length() % 1024) > 0 ? 1 : 0))+"KB");
                    sysBeFile.setfileName(file.getName());
                    sysBeFile.setCreateTime(DateUtils.getNowDate());
                    sysBeFile.setfileState("未下载");
                    sysMarkFileService.insertSysMarkFile(sysBeFile);
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
        List<SysMarkfiledir> sysMarkfiledirs = sysMarkfiledirService.selectChildrenAndMyselfDeptById(dirId);
        sysMarkfiledirs.stream().forEach(foe->{
            File file = new File(foe.getAbsolutPath());
            if (!file.exists()){
                sysDirService.deleteChildrenDeptById(foe.getFiledirId());
            }
        });

    }


}
