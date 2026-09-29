package org.example.models.hebergement;

import org.example.models.Adresse;
import org.example.models.enums.TypeHebergement;

public class Motel extends Hebergement {
    public Motel(Adresse adresse) {
        super(adresse);
    }

    @Override
    public String getDescription() {
        return "Motel";
    }

    @Override
    public TypeHebergement get_typeHebergement() {
        return TypeHebergement.MOTEL;
    }
}

