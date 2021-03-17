package fr.diginamic.agence.service;

import fr.diginamic.agence.dao.AdresseDAO;
import fr.diginamic.agence.entity.client.Adresse;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;

public class AdresseService extends SourceService<AdresseDAO, Adresse> {

    public AdresseService() {
        this.entityDAO = new AdresseDAO();
    }

    public boolean CheckInformation (Adresse adresse) {
        //check format tel
        if( adresse.getTel().matches("^0[1-9][0-9]{8}$") ) {
            SystemOutPrintHelper.messageJaune("Format telephone OK");
        } else {
            SystemOutPrintHelper.messageBleu("Le format du numero telephone donné est incorrect.");
            return false;
        }
        //check format mail
        if( adresse.getMail().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$") ) {
            SystemOutPrintHelper.messageJaune("Format mail OK");
        } else {
            SystemOutPrintHelper.messageBleu("Le format de l'adresse mail donnée est incorrecte.");
            return false;
        }
        //check si mail unique
        if(this.getByKey("mail", adresse.getMail()) != null) {
            SystemOutPrintHelper.messageBleu("Cette adresse mail est déjà prise");
        }
        SystemOutPrintHelper.messageJaune("Mail unique OK.");
        return true;
    }

    /*
    public Adresse create(Adresse adresse) {
        if (this.CheckInformation(adresse)) {
            super.create(adresse);
        } else {
            SystemOutPrintHelper.messageBleu("Erreur lors de la verif des informations de l'adresse");
        }
        return adresse;
    }
    */


}