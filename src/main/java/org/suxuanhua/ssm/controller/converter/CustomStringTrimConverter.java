package org.suxuanhua.ssm.controller.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * 自定义请求参数 字符串类型转换器Converter
 * 去掉字符串两边的空格
 * @author XuanhuaSu
 * @version 2018/4/15
 */
public class CustomStringTrimConverter implements Converter<String, String> {
    @Override
    public String convert(String source) {
        try {
            //去掉字符串两边的空格
            if (source != null && !source.isEmpty ()) {
                source = source.trim ();
                if (source.equals (""))
                    return null;
            }
        } catch (Exception e) {
            e.printStackTrace ();
            return null;
        }
        return source;
    }
}
