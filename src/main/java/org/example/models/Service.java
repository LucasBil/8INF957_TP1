package org.example.models;

import org.example.models.enums.TypeService;
import org.example.models.hebergement.Hebergement;

import java.util.Objects;

public class Service {
    private TypeService _type;
    private Double _prix;
    private Hebergement _hebergement;

    public Service(TypeService type, Double prix) {          // CORRECTION : retrait de Hebergement
        this.set_type(type);
        this.set_prix(prix);
    }

    public TypeService get_type() {
        return _type;
    }

    public void set_type(TypeService _type) {
        if (_type == null)
            throw new IllegalArgumentException("Le type ne doit pas être null");
        this._type = _type;
    }

    public Double get_prix() {
        return _prix;
    }

    public void set_prix(Double _prix) {
        if (_prix == null || _prix <= 0)                      // CORRECTION : ajout du check null
            throw new IllegalArgumentException("Le prix doit être supérieur à 0");
        this._prix = _prix;
    }

    public Hebergement get_hebergement() {
        return _hebergement;
    }

    public void set_hebergement(Hebergement _hebergement) {   // inchangé : appelé par addService()
        if (_hebergement == null)
            throw new IllegalArgumentException("L'hébergement ne doit pas être null");
        this._hebergement = _hebergement;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Service autre = (Service) obj;
        return Objects.equals(this._type, autre._type)
                && Objects.equals(this._prix, autre._prix)
                && Objects.equals(this._hebergement, autre._hebergement);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._type, this._prix, this._hebergement);
    }

    @Override
    public String toString() {
        return String.format("Service=[%s, %s, %s]",
                this._type,
                this._hebergement,
                this._prix
        ).trim();
    }
}