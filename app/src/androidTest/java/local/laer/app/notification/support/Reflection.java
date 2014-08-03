package local.laer.app.notification.support;

import com.google.common.collect.Iterables;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * Created by lars on 2014-07-01.
 */
public class Reflection {
	private final static Reflection impl = new Reflection();

	private Reflection() {

	}

	private <T> List<Field> fieldsByType(Class<T> type, Class<?> beanType) {
		if(beanType == null) {
			return null;
		}

		List<Field> fieldList = new ArrayList<Field>();

		for(Field field : beanType.getDeclaredFields()) {
			if(type.isAssignableFrom(field.getType())) {
				fieldList.add(field);
			}
		}

		return fieldList;
	}

	public static <T> void set(T newInstance, Class<? extends T> type, Object bean) {
		Field f= Iterables.getOnlyElement(impl.fieldsByType(type, bean.getClass()));

		if(!f.isAccessible()) {
			f.setAccessible(true);
		}

		try {
			f.set(bean, newInstance);
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		}
	}

	public static <T> T get(Class<T> type, Object bean) {
		try {
			Field f = Iterables.getOnlyElement(impl.fieldsByType(type, bean.getClass()));

			if(!f.isAccessible()) {
				f.setAccessible(true);
			}

			return type.cast(f.get(bean));
		} catch (IllegalAccessException e) {
			throw new RuntimeException(e);
		} catch(NoSuchElementException e) {
			throw new RuntimeException("Could not find field of type: "+type+":"+bean.getClass().getName());
		}
	}
}
