package fr.diginamic.swing.serviceAgence.Paiement.Validator;

import fr.diginamic.agence.entity.reservation.ModePaiement;
import fr.diginamic.agence.helpers.SystemOutPrintHelper;
import fr.diginamic.swing.composants.Console;
import fr.diginamic.swing.serviceAgence.AgenceFormValidator;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PaiementFormValidator extends AgenceFormValidator {

    @Override
    public boolean checkValueFromForm(String field, String value, Long id, Console console) {
        if( field.equals("mode")) {
            SystemOutPrintHelper.messageJaune(value);
            List<String> enumValues = Stream.of(ModePaiement.values()).map(Enum::name).collect(Collectors.toList());
            if(enumValues.contains(value)) {
                SystemOutPrintHelper.messageJaune("MODE DE PAIEMENT OK");
            } else {
                SystemOutPrintHelper.messageBleu("MODE DE PAIEMENT pas bon.");
                console.alert("Le mode de paiement selectioné n'est pas correct.");
                return false;
            }

        }
        return true;
    }

}