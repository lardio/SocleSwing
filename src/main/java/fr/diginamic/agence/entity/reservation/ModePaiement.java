package fr.diginamic.agence.entity.reservation;

public enum ModePaiement {

    CB ("carte banquaire"),
    LIQUIDE ("liquide"),
    CHEQUE ("cheque");

    String modePaiement;

    private ModePaiement(String modePaiement) {
        this.modePaiement = modePaiement;
    }

    public String getStatut() {
        return modePaiement;
    }

    public void setStatut(String modePaiement) {
        this.modePaiement = modePaiement;
    }

}
