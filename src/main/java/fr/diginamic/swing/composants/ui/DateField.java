package fr.diginamic.swing.composants.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import javax.swing.JComponent;

import fr.diginamic.swing.composants.error.ErrorManager;
import org.jdesktop.swingx.JXDatePicker;

/** Champ de saisie d'une date
 * @author RichardBONNAMY
 *
 */
public class DateField extends Input {
	
	/** Valeur saisie */
	private String value;
	/** format */
	private String format;
	/** Composant Swing associé */
	private JXDatePicker picker;

	/** Constructeur
	 * @param label libellé
	 * @param name nom
	 */
	public DateField(String label, String name) {
		super(label, name);
		this.format="dd/MM/yyyy";
		setWidth(150);
	}
	
	/** Constructeur
	 * @param label libellé
	 * @param name nom
	 */
	public DateField(String label, String name, String value) {
		super(label, name);
		this.value=value;
		this.format="dd/MM/yyyy";
		setWidth(150);
	}
	
	@Override
	public JComponent convert() {
		picker = new JXDatePicker(Locale.FRANCE);
		if (value!=null) {
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			try {
				picker.setDate(formatter.parse(value));
			} catch (ParseException e) {
				ErrorManager.manage("La date "+value+" n'est pas correcte. Vous ne pouvez pas vous en servir pour initialiser un DateField", e);
			}
		}
		picker.setEditable(isEditable());
		picker.setPreferredSize(new Dimension(getWidth(), 30));
		if (!isEditable()) {
			picker.setBackground(new Color(218, 243, 245));
		}
		picker.setFormats(new SimpleDateFormat(format));
		return picker;
	}
	
	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	
	@Override
	public void setValue(JComponent component) {
		String texte = picker.getEditor().getText();
		if (!texte.isEmpty()) {
			Date date = (Date)picker.getEditor().getValue();
			SimpleDateFormat formatter = new SimpleDateFormat(format);
			this.value = formatter.format(date);
		}
	}
	
	@Override
	public InputType getType() {
		return InputType.DATEFIELD;
	}

	/** Getter
	 * @return the format
	 */
	public String getFormat() {
		return format;
	}

	/** Setter
	 * @param format the format to set
	 */
	public void setFormat(String format) {
		this.format = format;
	}

	
}
