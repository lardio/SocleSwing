package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.TypeVehiculeDAO;
import fr.diginamic.agence.entity.vehicule.TypeVehicule;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;

public class TypeVehiculeService extends SourceService<TypeVehiculeDAO, TypeVehicule> {

    public TypeVehiculeService() {
        this.entityDAO = new TypeVehiculeDAO();
    }

    public boolean checkInformations(TypeVehicule typeVehicule) {
        boolean retour = true;
        if(typeVehicule.getCaution() <= 0) {
            SystemOutPrintHelper.messageBleu("La caution doit etre strictement positive");
            retour = false;
        }
        if(typeVehicule.getTarif() <= 0) {
            SystemOutPrintHelper.messageBleu("La tarif doit etre strictement positive");
            retour = false;
        }
        return  retour;
    }

    public TypeVehicule addType(TypeVehicule typeVehicule) {
        //check caution
        if(this.checkInformations(typeVehicule)) {
            this.create(typeVehicule);
            SystemOutPrintHelper.messageJaune("TypeVehicule créé");
        }
        return typeVehicule;
    }

    public TypeVehicule updateType(TypeVehicule typeVehicule) {
        if(this.checkInformations(typeVehicule)) {
            this.update(typeVehicule);
            SystemOutPrintHelper.messageJaune("TypeVehicule mis a jour");
        }
        return typeVehicule;
    }

}
