package org.example.models.hebergement;

import org.example.models.Adresse;
import org.example.models.enums.TypeHebergement;

public class Couette extends Hebergement {
    public Couette(Adresse adresse) {
        super(adresse);
    }

    @Override
    public String getDescription() {
        return "Couette";
    }

    @Override
    public TypeHebergement get_typeHebergement() {
        return TypeHebergement.COUETTE;
    }
}

