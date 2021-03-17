package fr.diginamic.agence.entity.reservation;

public enum StatutFacture {

    ATTENTE ("NON PAYE"),
    PAYE ("PAYE");

    String statut;

    private StatutFacture (String statut) {
        this.statut = statut;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
