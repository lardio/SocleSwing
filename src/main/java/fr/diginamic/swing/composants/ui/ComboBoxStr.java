package fr.diginamic.swing.composants.ui;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComboBox;
import javax.swing.JComponent;

/** Liste de sélection
 * @author RichardBONNAMY
 *
 */
public class ComboBoxStr extends Input {

	/** Liste des options de la liste */
	private List<String> selectables = new ArrayList<>();
	
	/** Item sélectionné par défaut */
	private String selectedItem;
	
	/** Constructeur
	 * @param label libellé
	 * @param name nom
	 * @param selectables liste des options de la liste
	 */
	public ComboBoxStr(String label, String name, List<String> selectables) {
		super(label, name);
		this.selectables = selectables;
		setEditable(true);
		setWidth(200);
	}
	
	/** Constructeur
	 * @param label libellé
	 * @param name nom
	 * @param selectables liste des options de la liste
	 * @param selectedItem item sélectionné par défaut dans la liste
	 */
	public ComboBoxStr(String label, String name, List<String> selectables, String selectedItem) {
		super(label, name);
		this.selectedItem = selectedItem;
		this.selectables = selectables;
		setEditable(true);
		setWidth(200);
	}
	
	@Override
	public JComponent convert() {
		JComboBox<String> combobox = new JComboBox<>();
		for (String selectable: selectables) {
			combobox.addItem(selectable);
		}
		if (selectedItem!=null) {
			combobox.setSelectedItem(selectedItem);
		}
		else {
			if (selectables.size()>0) {
				combobox.setSelectedIndex(0);
			}
		}
		combobox.setVisible(true);
		combobox.setEditable(isEditable());
		if (!isEditable()) {
			combobox.setBackground(new Color(218, 243, 245));
		}
		return combobox;
	}

	@Override
	public String getValue() {
		return this.selectedItem;
	}
	
	@Override
	public void setValue(JComponent component) {
		this.selectedItem=(String)((JComboBox<String>)component).getSelectedItem();
	}

	@Override
	public InputType getType() {
		return InputType.SELECT;
	}

	/** Getter
	 * @return the selectables
	 */
	public List<String> getSelectables() {
		return selectables;
	}

	/** Setter
	 * @param selectables the selectables to set
	 */
	public void setSelectables(List<String> selectables) {
		this.selectables = selectables;
	}

	/**
	 * @return the selectedItem
	 */
	public String getSelectedItem() {
		return selectedItem;
	}

	/**
	 * @param selectedItem the selectedItem to set
	 */
	public void setSelectedItem(String selectedItem) {
		this.selectedItem = selectedItem;
	}
}
