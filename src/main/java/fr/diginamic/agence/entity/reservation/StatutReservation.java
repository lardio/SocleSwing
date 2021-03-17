package fr.diginamic.agence.entity.reservation;

public enum StatutReservation {

    A_VENIR("La reservation n'a pas encore commencée"),
    EN_COURS("La reservation a commencée mais n'est pas terminée"),
    TERMINEE("La reservation est terminée mais pas encore payée"),
    PAYEE("La reservation est terminée et réglée") ;

    private String statut ;

    private StatutReservation(String statut) {
        this.statut = statut ;
    }

    public String StatutReservation() {
        return  this.statut ;
    }

}
