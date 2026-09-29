package org.example.models.hebergement;

import org.example.models.Adresse;
import org.example.models.enums.TypeHebergement;

public class Hotel extends Hebergement {
    public Hotel(Adresse adresse) {
        super(adresse);
    }

    @Override
    public String getDescription() {
        return "Hotel";
    }

    @Override
    public TypeHebergement get_typeHebergement() {
        return TypeHebergement.HOTEL;
    }
}

