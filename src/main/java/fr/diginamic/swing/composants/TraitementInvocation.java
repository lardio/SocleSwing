package fr.diginamic.swing.composants;

import java.util.concurrent.Callable;

import fr.diginamic.swing.composants.error.ErrorManager;
import fr.diginamic.swing.composants.reflect.ReflectUtils;

/** Traitement asynchrone permettant d'exécuter une chaine d'invocation du type: maClasse.maMethode(Long)
 * @author RichardBONNAMY
 *
 */
public class TraitementInvocation implements Callable<Void> {
	
	/** chaine d'invocation */
	private String invocation;
	
	/** Constructeur
	 * @param invocation chaine d'invocation à exécuter
	 */
	public TraitementInvocation(String invocation) {
		this.invocation=invocation;
	}

	@Override
	public Void call() {
		try {
			ReflectUtils.invoke(invocation);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			ErrorManager.manage(e.getMessage(), e);
		}
		return null;
	}

}