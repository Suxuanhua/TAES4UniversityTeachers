package org.suxuanhua.ssm.po.initconfig;

/**
 * 系统配置类
 * 通过spring 到该类中，然后通过注入的方式注入到类中
 * 用于加快系统运行速度，不用每次都要加载配置，只有在spring 容器启动时，加载配置并注入到类中
 * @author XuanhuaSu
 * @version 2018/5/21
 */
public class Config {

    //系统图片存放路径
    private String TAES4UT_IMG_PATH;
    //论文帖子存放路径
    private String TAES4UT_POST_PATH;
    //XML Post 列表位置
    private String TAES4UT_XML_POSTLIST_PATH;
    //tomcat 图片虚拟目录名称
    private String TAES4UT_IMG_VIRTUAL_PATH;

    public String getTAES4UT_IMG_PATH() {
        return TAES4UT_IMG_PATH;
    }

    public void setTAES4UT_IMG_PATH(String TAES4UT_IMG_PATH) {
        this.TAES4UT_IMG_PATH = TAES4UT_IMG_PATH;
    }

    public String getTAES4UT_POST_PATH() {
        return TAES4UT_POST_PATH;
    }

    public void setTAES4UT_POST_PATH(String TAES4UT_POST_PATH) {
        this.TAES4UT_POST_PATH = TAES4UT_POST_PATH;
    }

    public String getTAES4UT_XML_POSTLIST_PATH() {
        return TAES4UT_XML_POSTLIST_PATH;
    }

    public void setTAES4UT_XML_POSTLIST_PATH(String TAES4UT_XML_POSTLIST_PATH) {
        this.TAES4UT_XML_POSTLIST_PATH = TAES4UT_XML_POSTLIST_PATH;
    }

    public String getTAES4UT_IMG_VIRTUAL_PATH() {
        return TAES4UT_IMG_VIRTUAL_PATH;
    }

    public void setTAES4UT_IMG_VIRTUAL_PATH(String TAES4UT_IMG_VIRTUAL_PATH) {
        this.TAES4UT_IMG_VIRTUAL_PATH = TAES4UT_IMG_VIRTUAL_PATH;
    }
}
