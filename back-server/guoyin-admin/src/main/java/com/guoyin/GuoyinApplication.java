package com.guoyin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * 启动程序
 *
 * @author guoyin
 */
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class GuoyinApplication
{
    public static void main(String[] args)
    {
        // System.setProperty("spring.devtools.restart.enabled", "false");
        SpringApplication.run(GuoyinApplication.class, args);
        System.out.println("(♥◠‿◠)ﾉﾞ  医学图像标注系统启动成功   ლ(´ڡ`ლ)ﾞ  \n");
    }
}
