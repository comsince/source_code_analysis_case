package org.tio.utils.json;

import java.util.Date;

import org.tio.utils.hutool.StrUtil;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.ObjectSerializer;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializeFilter;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

/**
 *
 * @author tanyaowu
 * 2017年4月16日 上午11:36:53
 */
public class Json {

	private static SerializeConfig mapping = new SerializeConfig();

	static {
		mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		mapping.put(java.sql.Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		mapping.put(java.sql.Timestamp.class, new SimpleDateFormatSerializer("yyyy-MM-dd HH:mm:ss"));
		mapping.put(java.sql.Time.class, new SimpleDateFormatSerializer("HH:mm:ss"));
	}

	public static SerializeConfig put(Class<?> clazz, SerializeFilter filter) {
		mapping.addFilter(clazz, filter);
		return mapping;
	}

	public static SerializeConfig put(Class<?> clazz, ObjectSerializer serializer) {
		mapping.put(clazz, serializer);
		return mapping;
	}

	public static <T> T toBean(String jsonString, Class<T> tt) {
		if (StrUtil.isBlank(jsonString)) {
			return null;
		}
		T t = JSON.parseObject(jsonString, tt);
		return t;
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @author tanyaowu
	 */
	public static String toFormatedJson(Object bean) {
		return JSON.toJSONString(bean, mapping, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.PrettyFormat);
	}

	/**
	 * 
	 * @param bean
	 * @return
	 * @author tanyaowu
	 */
	public static String toJson(Object bean) {
		return JSON.toJSONString(bean, mapping, SerializerFeature.DisableCircularReferenceDetect);
	}

	/**
	 * 可以返回null的key值
	 * @param bean
	 * @return
	 * @author tanyaowu
	 */
	public static String toJsonAboutNull(Object bean) {
		return JSON.toJSONString(bean, mapping, SerializerFeature.DisableCircularReferenceDetect, SerializerFeature.WriteNullStringAsEmpty);
	}

	/**
	 * 
	 * @param bean
	 * @param serializeFilter
	 * @return
	 * @author tanyaowu
	 */
	public static String toJson(Object bean, SerializeFilter serializeFilter) {
		if (serializeFilter != null) {
			return JSON.toJSONString(bean, mapping, serializeFilter, SerializerFeature.DisableCircularReferenceDetect);
		} else {
			return JSON.toJSONString(bean, mapping, SerializerFeature.DisableCircularReferenceDetect);
		}
	}
}
