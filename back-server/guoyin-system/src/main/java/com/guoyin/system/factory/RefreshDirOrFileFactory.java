package com.guoyin.system.factory;

import com.guoyin.system.domain.SysFileDir;
import com.guoyin.system.mapper.SysFileDirMapper;
import com.guoyin.system.service.ISysDirService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.Resource;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: ruoyi
 * @description: 根据服务器文件目录 刷新数据库目录表tree
 * @author: ashinZhang
 * @create: 2021-12-02 14:45
 */
public class RefreshDirOrFileFactory {

    @Resource
    SysFileDirMapper sysFileDirMapper;

    @Resource
    ISysDirService sysDirService;

    /**
     * @Description: TODO
     * @Date: 2021/12/2 14:47
     * @Author: ashinZhang
     * @Param: [filePath]  要刷新的服务器文件目录。 若该目录为空， 刷新根文件路径。
     * @return: void
     **/
    public void refreshDir(Long dirId) {

        //获取数据库中该路径下的子目录。
        SysFileDir sysFileDir = new SysFileDir();
        List<SysFileDir> sysFileDirList = sysFileDirMapper.selectDeptList(sysFileDir);
        List<FileDir> fileDirs4db = tree2(sysFileDirList, 1, dirId);

        //获取服务器中该文件的子目录
        String filePath = sysDirService.getFileDirRootPathByFileDirId(dirId);
        File f = new File(filePath);
        List<FileDir> fileDirs4local = tree(f, 1);

        //将服务器上有的，而数据库中没有的拿出来。
        List<FileDir> fileDirs = betch(fileDirs4local,fileDirs4db);

        for (int i = 0; i < fileDirs.size(); i++) {
            SysFileDir fileDir = new SysFileDir();
            fileDir.setParentId(dirId);
            fileDir.setfiledirName(fileDirs.get(i).getName());
            fileDir.setAncestors("?");
//            if(fileDir.)

        }



    }


    private static List<FileDir> betch(List<FileDir> fileDirs1, List<FileDir> fileDirs2){
        List<FileDir> fileDirList = new ArrayList<>();
        for (FileDir fileDir1:fileDirs1){
            boolean bol = true;
            for (FileDir fileDir2:fileDirs2){

                if(fileDir2.getName().equals(fileDir1.getName())){
                    bol = false;
                    fileDirList.addAll(betch(fileDir1.getFileDirs(),fileDir2.getFileDirs()));
                    break;
                }
            }
            if(bol){
                fileDirList.add(fileDir1);
            }
        }
        return fileDirList;
    }

    private  List<FileDir> tree2(List<SysFileDir> sysFileDirList, int level, Long parentId) {
        List<FileDir> fileDirs = new ArrayList<>();


        for (int i = 0; i < sysFileDirList.size(); i++) {

            if (parentId.equals(sysFileDirList.get(i).getParentId())) {
                FileDir fileDir = new FileDir();
                fileDir.setName(sysFileDirList.get(i).getfiledirName());
                fileDir.setLevel(level);
                fileDir.setRootPath(sysDirService.getFileDirRootPathByFileDirId(sysFileDirList.get(i).getfiledirId()));
                fileDir.setFileDirs(tree2(sysFileDirList, level + 1, sysFileDirList.get(i).getfiledirId()));
                fileDirs.add(fileDir);
            } else {
                System.out.println(sysFileDirList.get(i).getfiledirName());
            }

        }
        return fileDirs;

    }

    private static List<FileDir> tree(File f, int level) {
        List<FileDir> fileDirs = new ArrayList<>();

        File[] childs = f.listFiles();
        for (int i = 0; i < childs.length; i++) {
            if (childs[i].isDirectory()) {
                FileDir fileDir = new FileDir();
                fileDir.setName(childs[i].getName());
                fileDir.setLevel(level);
                fileDir.setRootPath(childs[i].getAbsolutePath());
                fileDir.setFileDirs(tree(childs[i], level + 1));
                fileDirs.add(fileDir);
            } else {
                System.out.println(childs[i].getName());
            }

        }
        return fileDirs;

    }
}

class FileDir {
    private String name;
    private int level;
    private String rootPath;
    List<FileDir> fileDirs;

    public String getName() {

        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public List<FileDir> getFileDirs() {
        return fileDirs;
    }

    public void setFileDirs(List<FileDir> fileDirs) {
        this.fileDirs = fileDirs;
    }

}