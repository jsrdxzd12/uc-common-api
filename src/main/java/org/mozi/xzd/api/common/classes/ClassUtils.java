package org.mozi.xzd.api.common.classes;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.PrioritizedParameterNameDiscoverer;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class ClassUtils {
	private static final Logger logger = LoggerFactory.getLogger(ClassUtils.class);

	@SuppressWarnings("unchecked")
	public static <T> T newClass(String className, Class<T> clazz) {
		Object obj = newClass(className);
		return (T) obj;
	}

	@SuppressWarnings("unchecked")
	public static <T> T newClass(Class<?> classValue) {
		try {
			Assert.notNull(classValue, "class 类为空");
			Object object = classValue.newInstance();
			return (T) object;
		} catch (InstantiationException e) {
			logger.info(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.info(e.getMessage(), e);
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T newClass(String packageName) {
		try {
			return (T) getClass(packageName).newInstance();
		} catch (InstantiationException e) {
			logger.info(e.getMessage(), e);
		} catch (IllegalAccessException e) {
			logger.info(e.getMessage(), e);
		}
		return null;
	}

	public static Class<?> getClass(String packageName) {
		Assert.hasLength(packageName, "传入的package url 为空！");
		try {

			return Class.forName(packageName);
		} catch (ClassNotFoundException e) {
			logger.info(e.getMessage(), e);
		}

		return null;
	}

	public static Class<?> getClass(String packageName, String message) {

		try {
			return Class.forName(packageName);
		} catch (Exception e) {
			logger.info(message, e);
		}
		Assert.isTrue(false, message);
		return null;

	}

	public static Class<?>[] getClasses(String packageName) throws ClassNotFoundException, IOException {
		ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
		assert classLoader != null;
		String path = packageName.replace('.', '/');
		Enumeration<URL> resources = classLoader.getResources(path);
		List<File> dirs = new ArrayList<File>();
		ArrayList<Class<?>> classes = new ArrayList<Class<?>>();
		while (resources.hasMoreElements()) {
			URL resource = resources.nextElement();
			String p = "";
			if (resource.getFile().indexOf("!") >= 0) {
				p = resource.getFile().substring(0, resource.getFile().indexOf("!")).replaceAll("%20", "");
			} else {
				p = resource.getFile();
			}
			if (p.startsWith("file:/"))
				p = p.substring(6);
			if (p.toLowerCase().endsWith(".jar")) {
				@SuppressWarnings("resource")
				JarFile jarFile = new JarFile(p);
				Enumeration<JarEntry> enums = jarFile.entries();
				while (enums.hasMoreElements()) {
					JarEntry entry = (JarEntry) enums.nextElement();
					String n = entry.getName();
					if (n.endsWith(".class")) {
						n = n.replaceAll("/", ".").substring(0, n.length() - 6);
						if (n.startsWith(packageName)) {
							classes.add(Class.forName(n));
						}
					}
				}
			} else {
				dirs.add(new File(p));
			}
		}

		for (File directory : dirs) {
			classes.addAll(findClasses(directory, packageName));
		}
		return classes.toArray(new Class[classes.size()]);
	}

	public static List<Class<?>> findClasses(File directory, String packageName) throws ClassNotFoundException {
		List<Class<?>> classes = new ArrayList<Class<?>>();
		if (!directory.exists())
			return classes;
		File[] files = directory.listFiles();
		for (File file : files) {
			if (file.isDirectory()) {
				assert !file.getName().contains(".");
				classes.addAll(findClasses(file, packageName + "." + file.getName()));
			} else if (file.getName().endsWith(".class")) {
				classes.add(
						Class.forName(packageName + '.' + file.getName().substring(0, file.getName().length() - 6)));
			}
		}
		return classes;
	}

	public static Boolean hasClass(Class<?>[] parameterClases, String parameterClassPath) {
		if (ObjectUtils.isEmpty(parameterClassPath))
			return false;
		return findClass(parameterClases, ClassUtils.getClass(parameterClassPath));
	}

	public static Boolean findClass(Class<?>[] parameterClases, Class<?> parameterClass) {
		if (ObjectUtils.isEmpty(parameterClases) && ObjectUtils.isEmpty(parameterClases))
			return false;

		for (Class<?> classItem : parameterClases) {
			if (parameterClass.equals(classItem))
				return true;
		}
		return false;
	}

	public static String convertClassNameToPropertyName(Object value) {
		return convertNameToPropertyName(value.getClass().getSimpleName());
	}

	public static String convertNameToPropertyName(String value) {
		return value.substring(0, 1).toLowerCase() + value.substring(1);
	}
	
	

	public static String[] getParameterNames(Method method) {

        ParameterNameDiscoverer standardReflectionParameterNameDiscoverer= new PrioritizedParameterNameDiscoverer();
    //    StandardReflectionParameterNameDiscoverer
                //new LocalVariableTableParameterNameDiscoverer();

		try {
			return standardReflectionParameterNameDiscoverer.getParameterNames(method);
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}

    public static String[] getMethodParams(Class<?> aClass, String methodName) throws ClassNotFoundException {

        Method[] methods = aClass.getMethods();
        String[] params = null;
        for (Method method : methods) {
            if (method.getName().equals(methodName)) {
                params = getParameterNames(method);
                if (params == null || params.length == 0) {
                    break;
                }
                StringBuilder sb = new StringBuilder();
                sb.append("方法：" + method.getName() + "()  ");
                for (int i = 0; i < params.length; i++) {
                    if (i > 0) {
                        sb.append(" ,");
                    }
                    sb.append(params[i]);
                }
                System.out.println(sb.toString());
                break;
            }
        }
        return params;
    }

	public static void main(String[] args) {
		String t = "SuperControlle";
		System.out.println(t.substring(0, 1).toLowerCase() + t.substring(1));
	}

}
