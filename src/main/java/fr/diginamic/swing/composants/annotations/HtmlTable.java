package fr.diginamic.swing.composants.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/** A positionner sur une classe à partir de laquelle on souhaite pouvoir générer une table HTML
 * @author RichardBONNAMY
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface HtmlTable {

	String cssClass() default "table";
	String headerClass() default "";
	
	String style() default "";
	int border() default 0;
	String id();
	HtmlAction[] actions() default {};
}
