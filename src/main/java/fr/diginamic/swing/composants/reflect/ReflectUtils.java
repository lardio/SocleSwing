package fr.diginamic.swing.composants.reflect;

import java.awt.Desktop;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;

import fr.diginamic.swing.composants.AbstractApplication;
import org.apache.commons.lang3.StringUtils;

import com.google.common.reflect.ClassPath;

import fr.diginamic.swing.composants.error.ErrorManager;

/**
 * Fournit des méthodes utiles pour instantier une classe et appeler
 * dynamiquement une méthode
 * 
 * @author RichardBONNAMY
 *
 */
public class ReflectUtils {

	/**
	 * Recherche une classe dans le projet à partir du package fr.diginamic (scan)
	 * et à partir de son nom.
	 * 
	 * @param className nom de la classe
	 * @return Class
	 */
	public static Class<?> getClass(String className) {
		try {
			final ClassLoader loader = Thread.currentThread().getContextClassLoader();

			for (final ClassPath.ClassInfo info : ClassPath.from(loader).getTopLevelClasses()) {
				if (info.getName().startsWith("fr.diginamic.")) {
					final Class<?> clazz = info.load();
					if (clazz.getSimpleName().equals(className)) {
						return clazz;
					}
				}
			}
			return null;
		} catch (IOException e) {
			String msg = "La classe " + className + " n'existe pas.";
			ErrorManager.manage(msg, e);
			return null;
		}
	}

	/**
	 * Invoque une méthode pour une classe donnée avec un paramètre Long. La chaine
	 * d'invocation doit être du type: maClass.maMethode(Long.class)
	 * 
	 * @param chaineInvocation
	 */
	public static void invoke(String chaineInvocation) {

		if (chaineInvocation.contains(".") && chaineInvocation.contains("(") && chaineInvocation.contains(")")) {
			String[] tokens = chaineInvocation.split("\\.");

			String className = tokens[0].substring(0, 1).toUpperCase() + tokens[0].substring(1);
			String methodWithParameter = tokens[1];
			String methodName = methodWithParameter.substring(0, methodWithParameter.indexOf("("));
			String parameter = methodWithParameter.substring(methodWithParameter.indexOf("(") + 1,
					methodWithParameter.indexOf(")"));
			
			Class<?> classe = getClass(className);
			if (classe==null) {
				ErrorManager.manage("La classe " + className + " n'existe pas.");
			}
			instantiateClassAndInvokeMethod(classe, methodName, parameter);
		}
		else if (chaineInvocation.contains("(") && chaineInvocation.contains(")")){
			
			String methodName = chaineInvocation.substring(0, chaineInvocation.indexOf("("));
			String parameter = chaineInvocation.substring(chaineInvocation.indexOf("(") + 1,
					chaineInvocation.indexOf(")"));
			
			Class<?> classe = AbstractApplication.currentMenuService.getClass();
			instantiateClassAndInvokeMethod(classe, methodName, parameter);
		}
		else {
			try {
				Desktop.getDesktop().browse(new java.net.URI(chaineInvocation));
			} catch (IOException | URISyntaxException e) {
				ErrorManager.manage("Impossible d'accéder à l'URL "+chaineInvocation, e);
			}
		}
	}

	/** Appel de la méthode methodName de la classe classe avec l'identifiant id
	 * @param classe classe
	 * @param methodName nom de la méthode
	 * @param id identifiant
	 */
	private static void instantiateClassAndInvokeMethod(Class<?> classe, String methodName, String parameter) {
		Object obj = null;
		try {
			if (classe != null) {
				Constructor<?> construct = classe.getConstructor();
				construct.setAccessible(true);
				if (construct != null) {
					obj = construct.newInstance();
				} else {
					ErrorManager.manage("Le constructeur sans paramètre n'existe pas dans la classe " + classe.getName());
				}
			} 
		} catch (ReflectiveOperationException e) {
			String msg = "Le constructeur sans paramètre est obligatoire dans la classe " + classe.getName();
			ErrorManager.manage(msg, e);
		}
		try {
			invokeMethod(classe, methodName, parameter, obj);
		} catch (ReflectiveOperationException e) {
			e.printStackTrace();
			ErrorManager.manage(e.getMessage(), e);
		}
	}

	private static void invokeMethod(Class<?> classe, String methodName, String parameter, Object obj)
			throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Method method = null;
		if (StringUtils.isNotEmpty(parameter)) {
			method = findMethodByName(classe, methodName, true);
			if (method!=null) {
				method.setAccessible(true);
				Class<?> parameterType = method.getParameterTypes()[0];
				if (parameterType.equals(Long.class) || parameterType.equals(long.class)) {
					method.invoke(obj, Long.parseLong(parameter));
				}
				else if (parameterType.equals(Integer.class) || parameterType.equals(Integer.class)) {
					method.invoke(obj, Integer.parseInt(parameter));
				}
				else {
					method.invoke(obj, parameter);
				}
			}
			else {
				ErrorManager.manage("La méthode "+methodName+" n'existe pas dans la classe "+classe.getSimpleName());
			}
		}
		else {
			method = findMethodByName(classe, methodName, false);
			method.setAccessible(true);
			method.invoke(obj);
		}
	}
	
	public static Method findMethodByName(Class<?> origin, String methodName, boolean hasParameter) {
		if (origin.getName().equals("java.lang.Object")) {
			return null;
		}
		if (hasParameter) {
			List<Class<?>> possibleTypes = Arrays.asList(Long.class, Integer.class, long.class, int.class, String.class);
			for (Class<?> type: possibleTypes) {
				try {
					Method m = origin.getDeclaredMethod(methodName, type);
					return m;
				} catch (NoSuchMethodException | SecurityException e) {
				}
			}
		}
		else {
			try {
				Method m = origin.getDeclaredMethod(methodName);
				return m;
			} catch (NoSuchMethodException | SecurityException e) {
			}
		}
		return findMethodByName(origin.getSuperclass(), methodName, hasParameter);
	}
}
