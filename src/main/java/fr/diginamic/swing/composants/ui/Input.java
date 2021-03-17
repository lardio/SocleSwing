package fr.diginamic.swing.composants.ui;

import javax.swing.JComponent;

import org.apache.commons.lang3.builder.EqualsBuilder;

/** Représente un champ du formulaire
 * @author RichardBONNAMY
 *
 */
public abstract class Input {
	
	/** Libellé associé au champ de saisie */
	private String label;
	
	/** Nom du champ de saisie: doit être unique */
	private String name;
	
	/** Largeur en pixels du champ de saisie */
	private int width;
	
	/** editable */
	private boolean editable;

	/** Constructeur
	 * @param label libellé du champ de saisie
	 * @param name nom du champ de saisie
	 */
	public Input(String label, String name) {
		super();
		this.label = label;
		this.name = name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Input)) {
			return false;
		}
		Input autre = (Input)obj;
		return new EqualsBuilder().append(name, autre.getName()).isEquals();
	}
	
	/** Convertir le champ de saisie en composant Swing
	 * @return {@link JComponent}
	 */
	public abstract JComponent convert();

	/** Retourne la valeur saisie par l'utilisateur
	 * @return String
	 */
	public abstract <T> T getValue();
	
	/**
	 * @param value
	 */
	public abstract void setValue(JComponent value);
	
	/** Retourne le type du champ de saisie
	 * @return {@link InputType}
	 */
	public abstract InputType getType();

	/** Getter
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/** Setter
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/** Getter
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/** Setter
	 * @param label the label to set
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/** Getter
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/** Setter
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/** Getter
	 * @return the editable
	 */
	boolean isEditable() {
		return editable;
	}

	/** Setter
	 * @param editable the editable to set
	 */
	void setEditable(boolean editable) {
		this.editable = editable;
	}
}
