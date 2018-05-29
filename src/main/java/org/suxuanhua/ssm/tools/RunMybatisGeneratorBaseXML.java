package org.suxuanhua.ssm.tools;

import org.mybatis.generator.api.MyBatisGenerator;
import org.mybatis.generator.config.Configuration;
import org.mybatis.generator.config.xml.ConfigurationParser;
import org.mybatis.generator.internal.DefaultShellCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * mybatis 逆向工程工具类，通过数据表，自动生成po、mapper、mapper.xml
 * @author XuanhuaSu
 * @version 2018/4/9
 */
public class RunMybatisGeneratorBaseXML {
    public static void main(String [] arr) {
        RunMybatisGeneratorBaseXML run = new RunMybatisGeneratorBaseXML ();
        try {
            run.generator ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    public static void generator() throws Exception {
        List<String> warnings = new ArrayList<String> ();
        boolean overwrite = true;
        //指定XML 配置文件
        File configFile = new File("src/main/resources/generatorConfig.xml");
        ConfigurationParser cp = new ConfigurationParser(warnings);
        Configuration config = cp.parseConfiguration(configFile);
        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config, callback, warnings);
        myBatisGenerator.generate(null);
    }
}
