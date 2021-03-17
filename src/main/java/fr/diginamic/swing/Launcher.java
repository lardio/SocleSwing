package fr.diginamic.swing;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.swing.SwingUtilities;

import fr.diginamic.agence.EntityManagerGestion;
import fr.diginamic.agence.entity.agence.Entreprise;
import fr.diginamic.agence.service.EntrepriseService;
import fr.diginamic.swing.exemples.ApplicationExemple;

/**
 * Point d'entrée de l'application: launcher
 *
 * @author RichardBONNAMY
 *
 */
public class Launcher {

    /**
     * Point d'entrée
     *
     * @param args non utilisés ici
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                // Création de la fenêtre principale
                final ApplicationExemple wnd = new ApplicationExemple("Application exemple");
                wnd.buildInterfaceGraphique();
                // On passe les données à ce composant pour traitement ultérieur

                // On affiche la fenêtre au démarrage
                wnd.setVisible(true);
                EntityManager manager = EntityManagerGestion.recuperationEntityManager();
                EntrepriseService entrepriseService = new EntrepriseService();
                Entreprise entreprise = new Entreprise();
                entreprise.setNom("Garage de l'apero");
                entreprise.setCompta(0d);
                entrepriseService.create(entreprise);
            }
        });
    }
}