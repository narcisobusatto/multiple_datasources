package br.com.spring_data_jpa.multiple_datasources.Util;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Utils {

	public static Boolean verifyFieldsNotNull(Object o)
			throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(o.getClass());
		PropertyDescriptor[] pdList = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor pd : pdList) {
			Method readMethod = null;
			try {
				readMethod = pd.getReadMethod();
			} catch (Exception e) {
			}

			if (readMethod == null) {
				continue;
			}

			Object val = readMethod.invoke(o);
			if (val == null)
				return false;
		}
		return true;
	}

	public static void copyProperties(Object origem, Object destino)
			throws IntrospectionException, IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		BeanInfo beanInfo = Introspector.getBeanInfo(origem.getClass());
		PropertyDescriptor[] pdList = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor pd : pdList) {
			if (!pd.getName().contains("id")) {
				Method writeMethod = null;
				Method readMethod = null;
				try {
					writeMethod = pd.getWriteMethod();
					readMethod = pd.getReadMethod();
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (readMethod == null || writeMethod == null) {
					continue;
				}

				Object val = readMethod.invoke(origem);
				if (val == null)
					val = 0;
				writeMethod.invoke(destino, val);
			}
		}
	}

}
