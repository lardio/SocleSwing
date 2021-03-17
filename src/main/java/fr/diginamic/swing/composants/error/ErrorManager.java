package fr.diginamic.swing.composants.error;

import javax.swing.JOptionPane;

/** Error manager
 * @author RichardBONNAMY
 *
 */
public class ErrorManager {

	/** Gère une exception
	 * @param msg message d'erreur
	 * @param e e
	 */
	public static void manage(String msg, Throwable e) {
		JOptionPane.showMessageDialog(null, msg);
		throw new RuntimeException(msg, e);
	}
	
	/** Gère une exception
	 * @param msg message d'erreur
	 */
	public static void manage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
		throw new RuntimeException(msg);
	}
}
