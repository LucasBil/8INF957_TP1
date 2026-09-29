package org.example.models.hebergement;

import org.example.models.Adresse;
import org.example.models.enums.TypeHebergement;

public class Cafe extends Hebergement {
    public Cafe(Adresse adresse) {
        super(adresse);
    }

    @Override
    public String getDescription() {
        return "Cafe";
    }

    @Override
    public TypeHebergement get_typeHebergement() {
        return TypeHebergement.CAFE;
    }
}
