package org.suxuanhua.ssm.tools;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.web.multipart.MultipartFile;
import org.suxuanhua.ssm.exception.CustomException;
import org.suxuanhua.ssm.po.post.Post;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author XuanhuaSu
 * @version 2018/4/23
 */
public class TAES4Utils {

    private static Logger logger = LogManager.getLogger (LogManager.ROOT_LOGGER_NAME);


    /**
     * 获取客户端真实的IP地址
     * 用request.getRemoteAddr() 方法获取的IP地址是：127.0.0.1　或　192.168.1.110 ，而并不是客户端的真实ＩＰ
     *
     * @return String
     */
    public static String getRemoteHost(HttpServletRequest request) {
        String ip = request.getHeader ("x-forwarded-for");
        if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip)) {
            ip = request.getHeader ("Proxy-Client-IP");
        }
        if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip)) {
            ip = request.getHeader ("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length () == 0 || "unknown".equalsIgnoreCase (ip)) {
            ip = request.getRemoteAddr ();
        }
        return "0:0:0:0:0:0:0:1".equals (ip) ? "127.0.0.1" : ip;
    }


    /**
     * 生成ID
     *
     * @param size 生成的长度
     * @return 可以通过mysql uuid 生成随机ID，或者主键ID："select uuid();"
     */
    public static Integer createID(int size) throws Exception {
        Random r = new Random ();
        StringBuffer stringBuffer = new StringBuffer ();

        //每次随机生成一个数字，存到StringBuffer 中
        for (int in = 0; in < size; in++) {
            //nextInt ：指定生成的数的最大值（0 到 指定的值-1）。
            stringBuffer.append (r.nextInt (10));
        }
        //如果List 中第一个是0 重新生成一个替换
//        int i=1;//用于检测生成次数
        while (stringBuffer.indexOf ("0") == 0) {
//            System.out.print(stringBuffer.toString ()+"<---出现零头，重新生成后"+i+"次后：");
            stringBuffer.replace (0, 1, String.valueOf (r.nextInt (10)));
//            i++;
        }
        return Integer.parseInt (stringBuffer.toString ());
    }


    /**
     * 随机生成32位16进制的随机数，字母全部大写。
     *
     * @return String
     * 可以通过mysql uuid 生成随机ID，或者主键ID："select uuid();"
     */
    public static String randomUUID() {
        //使用UUID.randomUUID() 随机生成32位16进制的随机数，存到uuid变量中。
        UUID uuid = UUID.randomUUID ();
        String string = uuid.toString ();
        //public String replace(char oldChar,char newChar)//返回一个新的字符串，它是通过用 newChar 中的字符串 替换 oldChar 中的字符串。
        return string.replace ("-", "").toUpperCase ();//使用空字符替换“-”，全部字母大写
    }

    /**
     * 获取request 中的Referer。
     *
     * @param req HttpServletRequest
     * @return Referer 不为空，返回该Referer。否则返回 not is Bolomi Page 字符串
     */
    public static String getReferer(HttpServletRequest req) {

        String referer = null;//设置referer 默认值，默认不包含contains 要判断的内容即可。

        //避免 contains 异常：通过/BolomiServlet?method=newPost访问产生异常
        if (req.getHeader ("Referer") != null) {
            referer = req.getHeader ("Referer");
        }
        return referer;
    }

    /**
     * 通知，在session 通过notice 获取JS弹窗通知，通过message 获取页面通知
     *
     * @param session HttpSession对象
     * @param notice  通知信息
     */
    public static void setNotice(HttpSession session, String notice) {

        session.setAttribute ("notice", notice);
        session.setAttribute ("message", notice);
    }

    /**
     * 用户头像上传
     *
     * @param fileRootPath    图片存放的起始目录，比如："D:\\TAES4UT-Pictures"
     * @param pictureTypePath 图片存放的分类目录，比如："\\uHeader_default\\"
     * @param uid             用户ID，用于分类不同用户的图片
     * @param pictureFile     图片源
     * @return String 图片相对路径：pictureTypePath+uid+ UUID.randomUUID ()+文件后缀名
     */
    public static String uploadHeaderImage(String fileRootPath, String pictureTypePath, String uid, MultipartFile pictureFile) throws Exception {
        String pictureName;
        String suffix;
        String newPictureName = null;
        File pic;
        boolean cropSituation;
        try {
            //获得图片的后缀名（获取图片的原始名称，然后使用substring 通过“.” 切割，或者使用判断，使用string 的endswith()方法判断文件后缀名）
            pictureName = pictureFile.getOriginalFilename ();
            //使用substring 通过“.” 切割 获得文件名
            suffix = pictureName.substring (pictureName.lastIndexOf (".") + 1);
            //判断后缀名是否支持使用
            if ("jpg".equals (suffix) || "jpeg".equals (suffix) || "png".equals (suffix)) {
                //如果路径不存在，则创建
                if (!new File (fileRootPath + pictureTypePath + uid).exists ())
                    new File (fileRootPath + pictureTypePath + uid).mkdirs ();

                //新的图片名称
                newPictureName = pictureTypePath + uid + "/" + uid + "-" + UUID.randomUUID ();
                //从新赋值后缀名
                suffix = "." + suffix;
                String absolutelyPictureName = fileRootPath + newPictureName + suffix;
                pic = new File (absolutelyPictureName);

                //将内存中的文件写入磁盘
                pictureFile.transferTo (pic);

                //切割图片
                cropSituation = ImageUtils.cropImageSquare (absolutelyPictureName, absolutelyPictureName);

                if (cropSituation) {
                    newPictureName = newPictureName + suffix;
                } else {
                    newPictureName = "/uHeader_default/uHeader_default.jpg";
                    pic.delete ();//切割失败就删除
                    logger.warn (absolutelyPictureName + "：图片切割失败");
                    System.out.println (absolutelyPictureName + "：图片切割失败");
                }
            } else {
                newPictureName = "/uHeader_default/uHeader_default.jpg";
            }
        } catch (IOException e) {
            throw new CustomException ("图片更新发生异常", "/ExceptionPage.jsp");
        } finally {
            return newPictureName;
        }
    }

    /**
     * 删除指定路径的文件/空目录。
     *
     * @param filePath 删除文件的绝对路径
     * @return Boolean
     */
    public static Boolean deleteAlreadyExistingFile(String filePath) {
        File file;
        try {
            file = new File (filePath);
            if (file.exists ()) {
                if (file.delete ())
                    return true;
                else
                    return false;
            } else
                return false;

        } catch (Exception e) {
            e.printStackTrace ();
            return false;
        }
    }

    /**
     * 获取变量的类型
     *
     * @param o 变量名
     * @return String
     */
    public static String getType(Object o) { //获取变量类型方法
        return o.getClass ().toString (); //使用int类型的getClass()方法
    }


    /**
     * 获取BOLOMI 项目中的目录
     *
     * @param filePath "" 表示获取当前运行类所在目录，"/" 当前项目根目录。也可以是该项目内其他的路径
     * @return PathString
     */
    public static String getProjectPath(String filePath) {
        String path = new Object () {
            public String getPath() {
                //加"/"表示当前项目根目录，不加表示当前运行类的目录
                return this.getClass ().getResource (filePath).getPath ();
            }
        }.getPath ();
        //}.getPath().substring(1);

        return path;
    }

    /**
     * 获取指定路径下的 Properties 配置文件中指定的值
     *
     * @param PropertiesfilePath "xxx" 表示获取当前运行类所在目录，"/xx" 当前项目根目录。也可以是该项目内其他的路径
     * @param findKey            要查询Properties 的键。
     * @return 通过 findKey 获得 Properties 中的值，找不到返回null
     */
    public static String getPropertiesValue(String PropertiesfilePath, String findKey) {

        InputStream in = null;
        String value = null;
        File file;
        Properties pr;
        HashMap<String, String> listMap;
        try {
            //获取工程classes 下的路径，这个方法可以在任意jsp，servlet，java文件中使用，
            // 因为不管是jsp，servlet其实都是java程序，都是一个 class。所以它应该是一个通用的方法。
            //与 this.getClass().getClassLoader().getResource("").getPath()+"\\BOLOMI.properties" 相同，一个是通过当前类对象获得，一个是通过.class Class类对象获得
            //静态类内如何获取？
            //类名.class.getClass().getResource("/").getPath().substring(1)；会报空指针异常；
            //类名.class.getClassLoader().getResource("/").getPath().substring(1)；在本地测试可以，项目部署后就不行了。解决办法是，可以使用匿名内部类的方式
            //解决办法是，可以使用匿名内部类的方式：
            String path = new Object () {
                public String getPath() {
                    //加"/"表示当前项目根目录，不加表示当前运行类的目录
                    return this.getClass ().getResource (PropertiesfilePath).getPath ();

                }
            }.getPath ();
            //加substring(1)的原因，因为win下会在地址前加一个“/”，/E:/mmm/xx，
            //在linux 会把Linux 的 "/" 啃掉。。。。。
            //}.getPath().substring(1);

//            Stitic new Object 获得的地址：opt/apache-tomcat-7.0.82/webapps/ROOT/WEB-INF/classes/
//                    来自 FileUtils.getPathInProperties() 的提示:地址中的文件不存在。

            //显示获得的地址。
//            System.out.println ("FileUtils.class getPathInProperties 方法获得的地址："+path);

            file = new File (path);
            boolean b = file.exists ();
//            System.out.println("exists 返回值是："+b);
            if (b) {

                in = new FileInputStream (file);
                pr = new Properties ();
                listMap = new HashMap<> ();

                pr.load (in);
                Enumeration<?> prList = pr.propertyNames ();
                while (prList.hasMoreElements ()) {
                    String elementKey = (String) prList.nextElement ();

                    //System.out.println ("属于 getPathInProperties()"+elementKey+"="+pr.getProperty (elementKey));

                    listMap.put (elementKey, pr.getProperty (elementKey));
                }
                //value =  listMap.get (findKey);//如果没找到返回null
                if ((value = listMap.get (findKey)) == null) {
                    logger.warn ("获取配置文件时找不到指定键的值（或者该键值对不存在此文件中）：" + PropertiesfilePath);
                    System.out.println ("找不到指定键的值（或者该键值对不存在此文件中）。");
                }

            } else {
                logger.warn (PropertiesfilePath + ":地址中的文件不存在。");
                System.out.println (PropertiesfilePath + ":地址中的文件不存在。");
            }

        } catch (FileNotFoundException e) {
            logger.warn (PropertiesfilePath + ":FileInputStream 找不到文件！\n" + e);
            System.out.println ("FileInputStream 找不到文件！");
            e.printStackTrace ();
        } catch (SecurityException e) {
            logger.warn (PropertiesfilePath + ":FileInputStream 没有权限读取该文件！\n" + e);
            System.out.println ("FileInputStream 没有权限读取该文件！");
            e.printStackTrace ();
        } catch (IOException e) {
            logger.warn (PropertiesfilePath + ":Properties Load FileInputStream Exception!");
            System.out.println ("Properties Load FileInputStream Exception!\n" + e);
            e.printStackTrace ();
        } finally {
            try {
                if (in != null)
                    in.close ();
            } catch (Exception e) {
                logger.warn (e);
                e.printStackTrace ();
            } finally {
                return value;
            }
        }
    }

    public static List<String> getPropertiesValueList(String PropertiesfilePath) {

        InputStream ins = null;
        File file;
        Properties pr;
        List<String> valueList = new ArrayList<> ();
        try {

            String path = new Object () {
                public String getPath() {
                    //加"/"表示当前项目根目录，不加表示当前运行类的目录
                    return this.getClass ().getResource (PropertiesfilePath).getPath ();

                }
            }.getPath ();

            file = new File (path);
            boolean b = file.exists ();
//            System.out.println("exists 返回值是："+b);
            if (b) {

                ins = new FileInputStream (file);
                pr = new Properties ();

                pr.load (ins);
                Enumeration<?> prList = pr.propertyNames ();
                while (prList.hasMoreElements ()) {
                    String elementKey = (String) prList.nextElement ();
                    valueList.add (pr.getProperty (elementKey));
                }

            } else {
                logger.warn (PropertiesfilePath + ":地址中的文件不存在。");
                System.out.println (PropertiesfilePath + ":地址中的文件不存在。");
            }

        } catch (FileNotFoundException e) {
            logger.warn (PropertiesfilePath + ":FileInputStream 找不到文件！\n" + e);
            System.out.println ("FileInputStream 找不到文件！");
            e.printStackTrace ();
        } catch (SecurityException e) {
            logger.warn (PropertiesfilePath + ":FileInputStream 没有权限读取该文件！\n" + e);
            System.out.println ("FileInputStream 没有权限读取该文件！");
            e.printStackTrace ();
        } catch (IOException e) {
            logger.warn (PropertiesfilePath + ":Properties Load FileInputStream Exception!");
            System.out.println ("Properties Load FileInputStream Exception!\n" + e);
            e.printStackTrace ();
        } finally {
            try {
                if (ins != null)
                    ins.close ();
            } catch (Exception ex) {
                logger.warn (ex);
                ex.printStackTrace ();
            } finally {
                return valueList;
            }
        }
    }


    /**
     * 判断自定字符串是否包含数字
     *
     * @param
     * @return
     */
    public static boolean isContainNumber(String sourceString) {

        Pattern p = Pattern.compile ("[0-9]");
        Matcher m = p.matcher (sourceString);
        if (m.find ()) {
            return true;
        }
        return false;
    }

    /**
     * 创建一个编码为UTF-8 文件
     *
     * @param fileName    文件名
     * @param filePath    创建文件的路径
     * @param fileFormat  创建文件的格式
     * @param fileContent 写入文件的内容
     * @return 创建成功返回true
     */
    public static boolean newFile(String fileName, String filePath, String fileFormat, String fileContent) {
        File f;
        OutputStream os = null;
        try {
            //osw = new OutputStreamWriter (new FileOutputStream (filePath+fileName+fileFormat),"UTF-8");
            f = new File (filePath);
            f.mkdirs ();//创建路径
            f = new File (filePath + "/" + fileName + "." + fileFormat);
            f.createNewFile ();//创建文件
            os = new FileOutputStream (f);
            if (f.canWrite ()) {
                os.write (fileContent.getBytes ("UTF-8"));
                return true;
            } else return false;
        } catch (Exception e) {
            e.printStackTrace ();
            return false;
        } finally {
            try {
                if (os != null)
                    os.close ();//关流
            } catch (IOException e) {
                logger.warn (e);
                e.printStackTrace ();
            }
        }
    }


    /**
     * 加载postList 中的信息封装成 Post 列表对象。
     *
     * @param xmlFilePath XML 文件路径
     * @return Post List 对象。
     */
    public static List<Post> loadXMLCreatePostList(String xmlFilePath) {
        List<Post> postList = null;
        SAXReader reader;
        try {
            reader = new SAXReader ();
            postList = new LinkedList<> ();
            Document doc = reader.read (xmlFilePath);
            Element root = doc.getRootElement ();
            List<Element> postElementList = root.elements ("post");
            ListIterator<Element> listiterator = postElementList.listIterator ();
            while (listiterator.hasNext ()) {
                Element post = listiterator.next ();
                postList.add (new Post (
                        post.element ("postID").getText (),
                        post.element ("uName").getText (),
                        post.element ("uID").getText (),
                        post.element ("uSex").getText (),
                        post.element ("uHeader_default").getText (),
                        post.element ("postTitle").getText (),
                        post.element ("postContentAddr").getText (),
                        post.element ("postDate").getText (),
                        Long.valueOf (post.element ("postVisits").getText ()),
                        Long.valueOf (post.element ("postReplies").getText ())));
            }
        } catch (Exception e) {
            logger.warn ("加载POST_LIST_XML发生了异常：" + e);
            e.printStackTrace ();
        } finally {
            return postList;
        }
    }

    /**
     * 通过PostList 创建.xml 文件。
     *
     * @param postList 帖子列表
     * @param filePath 文件创建url
     * @param fileName 文件名
     * @return 创建成功返回true
     */
    public static boolean createPostsXML(List<Post> postList, String filePath, String fileName) {

        File postsXMLFile;
        Writer w = null;
        XMLWriter xmlWriter = null;
        Document doc;
        boolean isCreate = false;

        try {
            postsXMLFile = new File (filePath);
            postsXMLFile.setWritable (true, false);
            postsXMLFile.mkdirs ();
            postsXMLFile = new File (filePath + "/" + fileName + ".xml");
            postsXMLFile.createNewFile ();

            //通过DocumentHelper类，创建一个xml文档
            doc = DocumentHelper.createDocument ();
            //向xml文件中添加时间注释
            SimpleDateFormat serviceTime = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");//获取服务器的时间
            doc.addComment ("updateTime:" + serviceTime.format (new Date ()));

            //添加根元素，设置默认命名空间xmlns 的属性
            Element root = doc.addElement ("postlist", "http://www.bolomi.org/postlist");
            //设置带有别名的，命名空间xmlns的属性
            root.addNamespace ("mm", "http://www.w3.org/2001/XMLSchema-instance");
            //设置属性
            //注意：Schema 约束文件.xsd 要放在 .xml 所在的目录下，否则约束不生效。
//            root.addAttribute ("mm:schemaLocation", "http://www.bolomi.org/postlist BOLOMI_PostList.xsd");

            for (Post ps : postList) {
                Element post = root.addElement ("post");

                Element postID = post.addElement ("postID");
                postID.setText (ps.getPostID ());

                Element uName = post.addElement ("uName");
                uName.setText (ps.getuName ());

                Element uID = post.addElement ("uID");
                uID.setText (ps.getuID ());

                Element uSex = post.addElement ("uSex");
                uSex.setText (ps.getuSex ());

                Element uHeader_default = post.addElement ("uHeader_default");
                uHeader_default.setText (ps.getuHeader_default ());

                Element postTitle = post.addElement ("postTitle");
                postTitle.setText (ps.getPostTitle ());

                Element postContentAddr = post.addElement ("postContentAddr");
                postContentAddr.setText (ps.getPostContentAddr ());

                Element postDate = post.addElement ("postDate");
                postDate.setText (ps.getPostDate ());

                Element postVisits = post.addElement ("postVisits");
                postVisits.setText (String.valueOf (ps.getPostVisits ()));

                Element postReplies = post.addElement ("postReplies");
                postReplies.setText (String.valueOf (ps.getPostReplies ()));
            }

            //xml 格式化样式
            OutputFormat outputFormat = OutputFormat.createPrettyPrint ();
            //设置字符编码
            outputFormat.setEncoding ("utf-8");

            //创建指定编码格式的文件，而不是使用平台默认的字符编码格式。
            //或者在mysql连接字符串url中加入?characterEncoding=utf8
            w = new OutputStreamWriter (new FileOutputStream (postsXMLFile), "UTF-8");
            xmlWriter = new XMLWriter (w, outputFormat);

            //写入文档对象
            xmlWriter.write (doc);
            isCreate = true;

        } catch (IOException io) {
            logger.warn (io);
            io.printStackTrace ();

        } finally {
            try {
                if (xmlWriter != null && w != null)
                    xmlWriter.close ();//自动关闭fw
            } catch (IOException ie) {
                logger.warn ("xmlWriter 流关闭失败：" + ie);
                System.out.println ("xmlWriter 流关闭失败");
                ie.printStackTrace ();
            } finally {
                return isCreate;
            }
        }
    }
}
