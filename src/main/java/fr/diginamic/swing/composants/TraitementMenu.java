package fr.diginamic.swing.composants;

import java.util.concurrent.Callable;

import fr.diginamic.swing.composants.error.ErrorManager;

/** Traitement asynchrone permettant d'exécuter un service
 * @author RichardBONNAMY
 *
 */
public class TraitementMenu implements Callable<Void> {
	
	/** menuService */
	private MenuService menuService;
	
	/** Constructeur
	 * @param menuService service à exécuter en asynchrone
	 */
	public TraitementMenu(MenuService menuService) {
		this.menuService=menuService;
	}

	@Override
	public Void call() {
		try {
			menuService.traitement();
		} catch (Exception e) {
			System.err.println(e.getMessage());
			e.printStackTrace();
			ErrorManager.manage(e.getMessage(), e);
		}
		return null;
	}

}