package fr.diginamic.agence.entity.vehicule;

public enum StatutVehicule {

    DISPONIBLE ("Disponible"),
    MAINTENANCE ("Maintenance"),
    LOCATION ("Location");

    String statut;

    private StatutVehicule (String statut) {
        this.statut = statut;
    }

    public String getStatut() {
        return statut;
    }

    public void setStatut(String statut) {
        this.statut = statut;
    }
}
