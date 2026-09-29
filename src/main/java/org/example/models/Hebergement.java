package org.example.models;

import org.example.models.enums.TypeHebergement;

import java.util.Objects;

public class Hebergement {
    private TypeHebergement _type;
    private Adresse _adresse;

    public Hebergement(TypeHebergement type, Adresse adresse) {
        this._type = type;
        this._adresse = adresse;
    }

    public Adresse get_adresse() {
        return _adresse;
    }

    public void set_adresse(Adresse _adresse) {
        if (_adresse == null)
            throw new IllegalArgumentException("L'adresse ne doit pas être null");
        this._adresse = _adresse;
    }

    public TypeHebergement get_type() {
        return _type;
    }

    public void set_type(TypeHebergement _type) {
        if (_type == null)
            throw new IllegalArgumentException("Le type ne doit pas être null");
        this._type = _type;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Hebergement autre = (Hebergement) obj;
        return Objects.equals(this._type, autre._type)
                && Objects.equals(this._adresse, autre._adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._type, this._adresse);
    }

    @Override
    public String toString() {
        return String.format("Hebergement=[%s, %s]",
                this._type,
                this._adresse
        ).trim();
    }
}
