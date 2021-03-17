package fr.diginamic.swing.composants.html;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.ObjectArrays;

/**
 * Fournit des méthodes pour créer du HTML à partir d'informations
 * 
 * @author RichardBONNAMY
 *
 */
public class HtmlUtils {

	private static final Insets WEST_INSETS = new Insets(0, 0, 0, 5);
	private static final Insets EAST_INSETS = new Insets(0, 5, 0, 0);

	/**
	 * Encapsule un texte et ses attributs CSS dans un span avec une balise style
	 * 
	 * @param text       texte
	 * @param attributes attributs CSS
	 * @return HTML au format String
	 */
	public static String toSpan(String text, AttributCss... attributes) {
		StringBuilder builder = new StringBuilder();
		builder.append("<span");
		if (attributes.length > 0) {
			builder.append(" style='");
			for (AttributCss a : attributes) {
				builder.append(a.getName()).append(":").append(a.getValue()).append(";");
			}
			builder.append("'>").append(text);
		} else {
			builder.append(">").append(text);
		}
		builder.append("</span>");
		return builder.toString();
	}

	/**
	 * Encapsule un texte, une couleur, et ses attributs CSS dans un span avec une
	 * balise style
	 * 
	 * @param text     texte
	 * @param attrsStr attributs CSS au format "name:value"
	 * @return String
	 */
	public static String toSpan(String text, String... attrsStr) {

		List<AttributCss> attributes = new ArrayList<>();
		for (int i = 0; i < attrsStr.length; i++) {
			if (attrsStr[i].contains(";")) {
				String[] subAttrs = attrsStr[i].split(";");
				for (String subAttr : subAttrs) {
					String[] tokens = subAttr.split(":");
					if (tokens.length == 2) {
						attributes.add(new AttributCss(tokens[0], tokens[1]));
					}
				}
			} else {
				String[] tokens = attrsStr[i].split(":");
				if (tokens.length == 2) {
					attributes.add(new AttributCss(tokens[0], tokens[1]));
				}
			}
		}

		return toSpan(text, attributes.toArray(new AttributCss[0]));
	}

	/**
	 * Encapsule un texte, une couleur, et ses attributs CSS dans un span avec une
	 * balise style
	 * 
	 * @param text     texte
	 * @param c        couleur
	 * @param attrsStr attributs CSS au format "name:value"
	 * @return String
	 */
	public static String toSpan(String text, Color c, String... attrsStr) {

		List<AttributCss> attributes = new ArrayList<>();
		for (int i = 0; i < attrsStr.length; i++) {
			if (attrsStr[i].contains(";")) {
				String[] subAttrs = attrsStr[i].split(";");
				for (String subAttr : subAttrs) {
					String[] tokens = subAttr.split(":");
					if (tokens.length == 2) {
						attributes.add(new AttributCss(tokens[0], tokens[1]));
					}
				}
			} else {
				String[] tokens = attrsStr[i].split(":");
				if (tokens.length == 2) {
					attributes.add(new AttributCss(tokens[0], tokens[1]));
				}
			}
		}

		return toSpan(text, ObjectArrays.concat(toAttribute(c), attributes.toArray(new AttributCss[0])));
	}

	/**
	 * Encapsule un texte, une couleur, et ses attributs CSS dans un span avec une
	 * balise style
	 * 
	 * @param text     texte
	 * @param c        couleur
	 * @param attrsStr attributs CSS au format "name:value"
	 * @return String
	 */
	public static String toSpan(String text, Color c, AttributCss... attributes) {

		return toSpan(text, ObjectArrays.concat(toAttribute(c), attributes));
	}

	/**
	 * Transforme la couleur en AttributCss
	 * 
	 * @param c couleur
	 * @return {@link AttributCss}
	 */
	public static AttributCss toAttribute(Color c) {

		StringBuilder builder = new StringBuilder();
		builder.append("#").append(String.format("%02X", c.getRed())).append(String.format("%02X", c.getGreen()))
				.append(String.format("%02X", c.getBlue()));

		return new AttributCss("color", builder.toString());
	}

	public static GridBagConstraints createGbc(int x, int y) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;

		gbc.anchor = (x == 0) ? GridBagConstraints.WEST : GridBagConstraints.EAST;
		gbc.fill = (x == 0) ? GridBagConstraints.BOTH : GridBagConstraints.HORIZONTAL;

		gbc.insets = (x == 0) ? WEST_INSETS : EAST_INSETS;
		gbc.weightx = (x == 0) ? 0.1 : 1.0;
		gbc.weighty = 1.0;
		return gbc;
	}
	
	public static GridBagConstraints createGbc(int x, int y, int position, int fill) {
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.gridx = x;
		gbc.gridy = y;
		gbc.gridwidth = 1;
		gbc.gridheight = 1;

		gbc.anchor = position;
		gbc.fill = fill;

		gbc.insets = (x == 0) ? WEST_INSETS : EAST_INSETS;
		gbc.weightx = (x == 0) ? 0.2 : 1.0;
		gbc.weighty = 0.9;
		return gbc;
	}
}
