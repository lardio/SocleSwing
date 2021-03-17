package fr.diginamic.swing.composants.annotations;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import fr.diginamic.swing.composants.ui.Selectable;
import org.apache.commons.lang3.StringUtils;

import fr.diginamic.swing.composants.error.ErrorManager;

public class TableGenerator {

	public static String toHtml(List<? extends Selectable<?>> selectables, Class<?> clazz) {

		StringBuilder builder = new StringBuilder();
		builder.append("<table");

		HtmlTable htmlTable = (HtmlTable) clazz.getAnnotation(HtmlTable.class);
		if (htmlTable == null) {
			ErrorManager.manage("L'annotation HtmlTable est obligatoire sur la classe " + clazz.getName());
		}

		String id = htmlTable.id();
		String css = htmlTable.cssClass();
		String style = htmlTable.style();
		String headerClasse = htmlTable.headerClass();
		HtmlAction[] actions = htmlTable.actions();

		builder.append(" id='").append(id).append("'");
		builder.append(" class='").append(css).append("'");
		builder.append(" style='").append(style).append("'>");

		Field[] fields = clazz.getDeclaredFields();
		builder.append("<tr class='"+headerClasse+"'>");
		for (HtmlAction action : actions) {
			builder.append("<td>&nbsp;</td>");
		}
		for (Field f : fields) {
			f.setAccessible(true);

			if (f.isAnnotationPresent(HtmlEntete.class)) {
				HtmlEntete entete = (HtmlEntete) f.getAnnotation(HtmlEntete.class);
				String columnName = entete.nom();
				builder.append("<td>").append(columnName).append("</td>");
			}
		}
		builder.append("</tr>");

		if (selectables!=null && selectables.size()>0) {
			for (Selectable o : selectables) {
				builder.append("<tr>");
				for (HtmlAction action : actions) {
					builder.append("<td><a style='text-decoration-line:none;' href='").append(action.methodName()).append("(").append(o.getId())
							.append(")'><img width='25' src='").append(action.image()).append("'></a></td>");
				}
				for (Field f : fields) {
					if (f.isAnnotationPresent(HtmlEntete.class)) {
						builder.append("<td>");
						try {
							Object value = f.get(o);
							if (value != null) {
								if (f.isAnnotationPresent(HtmlFormat.class)) {
									HtmlFormat format = (HtmlFormat) f.getAnnotation(HtmlFormat.class);
									Class<?> formatteurClass = format.formatteur();
									Constructor c = formatteurClass.getConstructor();
									Object formatteur = c.newInstance();
									Method method = formatteurClass.getDeclaredMethod("toHtml", f.getType());
									String formattedValue = (String) method.invoke(formatteur, value);
									formattedValue = formattedValue.replaceAll("#id", "" + o.getId());
									builder.append(formattedValue);
								} 
								else if (f.isAnnotationPresent(HtmlAction.class)){
									HtmlAction action = (HtmlAction)f.getAnnotation(HtmlAction.class);
									String image = action.image();
									String methodName = action.methodName();
									if (StringUtils.isNotEmpty(image) && StringUtils.isNotEmpty(methodName)) {
										builder.append("<a href='").append(methodName).append("(").append(o.getId()).append(")'><img width='25' src='").append(image).append("'></a>");
									}
									else if (StringUtils.isNotEmpty(methodName)) {
										builder.append("<a href='").append(methodName).append("(").append(o.getId()).append(")'>").append(o).append("</a>");
									}
									else if (StringUtils.isNotEmpty(image)) {
										builder.append("<img width='25' src='").append(image).append("'>");
									}
									else {
										ErrorManager.manage("L'annotation HtmlAction n'a pas les renseignements obligatoires sur le champ "+f.getName()+" de la classe "+clazz.getSimpleName(), null);
									}
								}
								else {
									builder.append(value.toString());
								}
							} else {
								builder.append("&nbsp;");
							}
							builder.append("</td>");
						} catch (IllegalArgumentException | ReflectiveOperationException e) {
							ErrorManager.manage(e.getMessage(), e);
						}
					}
				}
				builder.append("</tr>");
			}
		}
		else {
			builder.append("<tr>");
			for (HtmlAction action : actions) {
				builder.append("<td></td>");
			}
			for (Field f : fields) {
				if (f.isAnnotationPresent(HtmlEntete.class)) {
					builder.append("<td></td>");
				}
			}
			builder.append("</tr>");
		}
		builder.append("</table>");

		return builder.toString();
	}
}
