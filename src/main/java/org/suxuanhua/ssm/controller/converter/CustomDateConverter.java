package org.suxuanhua.ssm.controller.converter;

import org.springframework.core.convert.converter.Converter;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 自定义请求参数 日期类型转换器Converter
 *
 * @author XuanhuaSu
 * @version 2018/4/15
 */
//Converter<String,Date> 泛型类，<原始类型,转换的类型>，可以根据需求更改，比如：<String,Integer>
//同样，内部实现的方法public Date convert(String source)  的返回值也需要更改，如果转换的类型是Integer，返回值类型就要改成Integer
public class CustomDateConverter implements Converter<String, Date> {
    @Override
    public Date convert(String source) {
        Date date = null;
        try {
            //进行日期转换
            if (source != null && !source.equals ("")) {
                SimpleDateFormat sm = new SimpleDateFormat ("yyyy-MM-dd HH:mm:ss");//2018-03-12 19:33:36
                date = sm.parse (source);
            } else return null;
        } catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        return date;
    }
}
