package org.suxuanhua.ssm.tools;

import javax.imageio.ImageIO;
import javax.imageio.ImageReadParam;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author XuanhuaSu
 * @version 2018/4/18
 */
public class ImageUtils {

    /**
     * 获取图片的宽度和高度
     *
     * @param pictureFile 图片的File对象
     * @return Map 通过imgWidth 获取源图宽度，imgHeight 获取源图高度
     */
    public static Map<String, Integer> getImageSize(File pictureFile) throws IOException {
        FileInputStream fis = null;
        BufferedImage sourceImg;
        Map<String, Integer> imgSize = null;
        try {
            fis = new FileInputStream (pictureFile);
            sourceImg = ImageIO.read (fis);
            imgSize = new HashMap<> ();
            imgSize.put ("imgWidth", sourceImg.getWidth ());// 源图宽度
            imgSize.put ("imgHeight", sourceImg.getHeight ());// 源图高度

        } catch (Exception e) {
            System.out.println ("org.suxuanhua.ssm.tools.ImageUtils.getImageSize(...) --> 发生异常");
            e.printStackTrace ();
        } finally {
            if (fis != null)
                fis.close ();//关闭InputStream 流，否则会有进程占用，导致文件删除失败
            return imgSize;
        }
    }

    /**
     * byte数组转换成16进制字符串
     *
     * @param src
     * @return
     */
    private static String bytesToHexString(byte[] src) {
        StringBuilder stringBuilder = new StringBuilder ();
        if (src == null || src.length <= 0) {
            return null;
        }
        for (int i = 0; i < src.length; i++) {
            int v = src[i] & 0xFF;
            String hv = Integer.toHexString (v);
            if (hv.length () < 2) {
                stringBuilder.append (0);
            }
            stringBuilder.append (hv);
        }
        return stringBuilder.toString ();
    }

    /**
     * 获取图像真实类型
     *
     * @param path 图像路径
     * @return 图像类型
     * @throws Exception
     */
    public static String getTypeByStream(String path) throws Exception {
        FileInputStream is = new FileInputStream (path);
        String type = "";
        byte[] b = new byte[4];
        try {
            is.read (b, 0, b.length);
        } catch (Exception e) {
            e.printStackTrace ();
        } finally {
            if (null != is) {
                is.close ();
            }
        }
        type = bytesToHexString (b).toUpperCase ();
        if (type.contains ("FFD8FF")) {
            // 使用这种方式 return ".jpg";Iterator<ImageReader> it; 会报 java.util.NoSuchElementException
            return "jpg";
        } else if (type.contains ("89504E47")) {
            return "png";
        } else if (type.contains ("47494638")) {
            return "gif";
        } else if (type.contains ("49492A00")) {
            return "tif";
        } else if (type.contains ("424D")) {
            return "bmp";
        }
        return type;
    }

    /**
     * 功能描述：对图片裁剪，并把裁剪完新图片保存 。 <br>
     *
     * @param suffix          图片后缀，后缀名不能带“.” 否则Iterator<ImageReader> it，会报 java.util.NoSuchElementException
     * @param picturePath     图片地址
     * @param x               开始剪切的x坐标
     * @param y               开始剪切的y坐标
     * @param width           需要剪切的宽
     * @param height          需啊剪切的高
     * @param savePicturePath 保存图片地址
     * @throws IOException <br>
     */
    public static boolean cropImage(String picturePath, String savePicturePath, String suffix, int x, int y, int width,
                                    int height) throws IOException {

        FileInputStream fis = null;
        ImageInputStream iis = null;
        Iterator<ImageReader> it;
        ImageReader reader = null;
        ImageReadParam param = null;
        Rectangle rect = null;
        BufferedImage bi = null;
        boolean cropSituation = true;
        try {
            // 读取图片文件
            fis = new FileInputStream (picturePath);
            it = ImageIO.getImageReadersByFormatName (suffix);
            reader = it.next ();//后缀名不能带“.” 否则Iterator<ImageReader> it，会报 java.util.NoSuchElementException
            // 获取图片流
            iis = ImageIO.createImageInputStream (fis);
            reader.setInput (iis, true);
            param = reader.getDefaultReadParam ();
            rect = new Rectangle (x, y, width, height);
            // 提供一个 BufferedImage，将其用作解码像素数据的目标。
            param.setSourceRegion (rect);
            bi = reader.read (0, param);

            // 保存新图片
            cropSituation = ImageIO.write (bi, suffix, new File (savePicturePath));

        } catch (Exception e) {
            cropSituation = false;
            System.out.println ("org.suxuanhua.ssm.tools.ImageUtils.cropImage(...) --> 切割图片发生异常");
            e.printStackTrace ();
        } finally {
            //关闭InputStream 流，否则会有进程占用，导致文件删除失败
            if (fis != null)
                fis.close ();
            if (iis != null)
                iis.close ();
            return cropSituation;
        }

    }


    /**
     * 切割正方形图片
     *
     * @param imagePath       图片路径
     * @param saveImagePath   图片保存路径
     * @return boolean 切割成功返回true
     */
    public static boolean cropImageSquare(String imagePath, String saveImagePath) throws Exception {
        File pic = null;
        //切割图片
        Map<String, Integer> imageSizeMap = null;
        //获取图片的宽和高
        Integer imgWidth = null;
        Integer imgHeight = null;
        boolean cropSituation = true;
        String saveImageSuffix = "";
        try {
            pic = new File (imagePath);
            imageSizeMap = getImageSize (pic);
            saveImageSuffix = getTypeByStream (imagePath);//通过文件获取真实的后缀名,后缀名不能带“.”，否则Iterator 会报错。
            imgWidth = imageSizeMap.get ("imgWidth");
            //System.out.println (imgWidth);
            imgHeight = imageSizeMap.get ("imgHeight");
            //System.out.println (imgHeight);

            //如果宽大于长
            if (imgWidth - imgHeight > 0) {
                for (int up = 4; up <= 10; up++) {
                    //如果宽-宽的up分之一 大于 高，就以高作为代为进行切割
                    if (imgWidth - imgWidth / up > imgHeight) {
                        cropSituation = cropImage (imagePath, saveImagePath, saveImageSuffix,
                                imgWidth / up, 0, imgHeight, imgHeight);
                        break;
                    }
                }
            } else if (imgWidth - imgHeight < 0) {
                for (int up = 4; up <= 10; up++) {
                    if (imgHeight - imgHeight / up > imgWidth) {
                        cropSituation = cropImage (imagePath, saveImagePath, saveImageSuffix,
                                0, imgHeight / up, imgWidth, imgWidth);
                        break;
                    }
                }

            } else {
                if (imgWidth.equals (imgHeight))//Integer 类型要用equals 代表比较对象的值，== 比较内存地址值
                    System.out.println ("该图片是正方形");
                else {
                    if (imgWidth > imgHeight) {
                        cropSituation = cropImage (imagePath, saveImagePath, saveImageSuffix,
                                0, 0, imgHeight, imgHeight);
                    } else {//(imgHeight > imgWidth)
                        cropSituation = cropImage (imagePath, saveImagePath, saveImageSuffix,
                                0, 0, imgWidth, imgWidth);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println ("org.suxuanhua.ssm.tools.ImageUtils.cropImageSquare(...) --> 切割正方形图片发生异常");
            cropSituation = false;
            e.printStackTrace ();
        } finally {
            return cropSituation;
        }
    }
}
