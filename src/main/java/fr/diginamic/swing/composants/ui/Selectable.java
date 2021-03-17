package fr.diginamic.swing.composants.ui;

/** Un item de ComboBox doit implémenter cette interface
 * @author RichardBONNAMY
 *
 */
public interface Selectable<T> {

	/** Retourne l'identifiant de l'objet. 
	 * C'est cet id qui sera transmis lorsque l'utilisateur aura fait sa sélection.
	 * @return Long
	 */
	T getId();
}
