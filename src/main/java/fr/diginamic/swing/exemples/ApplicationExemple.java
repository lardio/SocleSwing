package fr.diginamic.swing.exemples;

import fr.diginamic.swing.composants.AbstractApplication;
import fr.diginamic.swing.serviceAgence.Client.AddClientServiceSwing;
import fr.diginamic.swing.serviceAgence.Client.ListePermisServiceSwing;
import fr.diginamic.swing.serviceAgence.Dashboard;
import fr.diginamic.swing.serviceAgence.Maintenance.AllMaintenanceEndedServiceSwing;
import fr.diginamic.swing.serviceAgence.Paiement.AddPaiementServiceSwing;
import fr.diginamic.swing.serviceAgence.Paiement.ListAllPaiementEnded;
import fr.diginamic.swing.serviceAgence.Reservation.ListeReservationServiceSwing;
import fr.diginamic.swing.serviceAgence.Reservation.EndReservationServiceSwing;
import fr.diginamic.swing.serviceAgence.Vehicule.AddVehiculeServiceSwing;
import fr.diginamic.swing.serviceAgence.Maintenance.EndMaintenanceServiceSwing;
import fr.diginamic.swing.serviceAgence.Maintenance.MaintenanceServiceSwing;
import fr.diginamic.swing.serviceAgence.Vehicule.ListeVehiculeServiceSwing;
import fr.diginamic.swing.serviceAgence.Vehicule.MarqueServiceSwing;
import fr.diginamic.swing.serviceAgence.Vehicule.TypeVehiculeServiceSwing;

/**
 * Fenêtre principale qui porte les principaux composants graphiques de
 * l'application:<br>
 * - les boutons du menu,<br>
 * - le panneau d'affichage des résultats<br>
 * 
 * @author RichardBONNAMY
 *
 */
public class ApplicationExemple extends AbstractApplication {

	/** serialVersionUID */
	private static final long serialVersionUID = 6755835482616236832L;
	
	/** emf */
	//public static EntityManagerFactory emf = Persistence.createEntityManagerFactory("h2-mem");
	
	/** Constructeur
	 * @param title titre
	 */
	public ApplicationExemple(String title) {
		super(title);
	}

	/**
	 * Code principal
	 * 
	 */
	public void main() {
		addMenu(1, "Dashboard");
		addMenu(2, "Vehicules");
		addMenu(4, "Maintenances");
		addMenu(5, "Clients");
		addMenu(6, "Reservations");
		addMenu(7, "Paiements");

		addMenuOption(1, "Informations sur l'entreprise", new Dashboard());
		
		addMenuOption(2, "Liste des vehicules", new ListeVehiculeServiceSwing());
		addMenuOption(2, "Ajouter un vehicule", new AddVehiculeServiceSwing());
		addMenuOption(2, "Liste des marques", new MarqueServiceSwing());
		addMenuOption(2, "Liste des types de vehicule", new TypeVehiculeServiceSwing());

		addMenuOption(4, "Ajouter une maintenance", new MaintenanceServiceSwing());
		addMenuOption(4, "Terminer une maintenance", new EndMaintenanceServiceSwing());
		addMenuOption(4, "Toutes les maintenances terminées", new AllMaintenanceEndedServiceSwing());

		addMenuOption(5, "Tous les clients / ajouter", new AddClientServiceSwing());
		addMenuOption(5, "Types de permis / ajouter", new ListePermisServiceSwing());

		addMenuOption(6, "Liste des reservations / ajouter", new ListeReservationServiceSwing());
		addMenuOption(6, "Reservation en cours", new EndReservationServiceSwing());

		addMenuOption(7, "Paiements en attente", new AddPaiementServiceSwing());
		addMenuOption(7, "Paiements terminés", new ListAllPaiementEnded());

		//addMenuOption(3, "Exemple 4 - Table avec liens vers méthodes", new EndMaintenanceServiceSwing());
		//addMenuOption(3, "Exemple 5 - Table dynamique", new Exemple5Service());ListAllPaiementEnded
		//addMenuOption(3, "Exemple 6 - Formulaire", new Exemple6Service());
		//addMenuOption(3, "Exemple 7 - Champ non modifiable", new Exemple7Service());
	}
}