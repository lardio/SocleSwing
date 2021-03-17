package fr.diginamic.swing.composants.html;

/** Représente un attribut CSS
 * @author RichardBONNAMY
 *
 */
public class AttributCss {

	/** name */
	private String name;
	/** value */
	private String value;
	
	/** Constructeur
	 * @param name name
	 * @param value value
	 */
	public AttributCss(String name, String value) {
		super();
		this.name = name;
		this.value = value;
	}
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/** Setter
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
}
