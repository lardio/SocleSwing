package fr.diginamic.swing.composants.ui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import fr.diginamic.swing.composants.error.ErrorManager;

/** Formulaire
 * @author RichardBONNAMY
 *
 */
public class Form implements Iterable<Input> {

	/** Liste des champs de saisie */
	private List<Input> inputs = new ArrayList<>();
	
	/** validated */
	private boolean validated;
	
	/** Retourne un champ de saisie à partir de son nom
	 * @param name nom du champ
	 * @return {@link Input}
	 */
	public Input getInput(String name) {
		Optional<Input> optional = inputs.stream().filter(i -> i.getName().equals(name)).findFirst();
		if (optional.isPresent()) {
			return optional.get();
		}
		return null;
	}
	
	/** Ajoute un champ de saisie
	 * @param input champ de saisie
	 */
	public void addInput(Input input) {
		if (input==null) {
			ErrorManager.manage("Il est interdit d'ajouter un champ null dans le formulaire.");
		}
		else if (inputs.contains(input)) {
			ErrorManager.manage("Il existe déjà un champ de saisie avec le nom "+input.getName()+" dans le formulaire.");
		}
		inputs.add(input);
	}

	/** Retourne la valeur saisie pour un champ de saisie donné
	 * @param name nom du champ de saisie
	 * @return String
	 */
	public <T> T getValue(String name) {
		if (!isValidated()) {
			ErrorManager.manage("Vous devez d'abord afficher et valider le formulaire (console.input) avant de récupérer les résultats.");
		}
		return getInput(name).getValue();
	}

	/** Retourne le nb de champs de saisie
	 * @return int
	 */
	public int size() {
		return inputs.size();
	}

	@Override
	public Iterator<Input> iterator() {
		return inputs.iterator();
	}

	/** Getter
	 * @return the inputs
	 */
	public List<Input> getInputs() {
		return inputs;
	}

	/** Setter
	 * @param inputs the inputs to set
	 */
	public void setInputs(List<Input> inputs) {
		this.inputs = inputs;
	}

	/** Getter
	 * @return the validated
	 */
	public boolean isValidated() {
		return validated;
	}

	/** Setter
	 * @param validated the validated to set
	 */
	public void setValidated(boolean validated) {
		this.validated = validated;
	}
}
