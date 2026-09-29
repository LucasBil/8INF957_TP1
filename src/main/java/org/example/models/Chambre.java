package org.example.models;

import org.example.models.enums.TypeChambre;
import org.example.models.hebergement.Hebergement;

import java.util.Objects;

public class Chambre {
    private TypeChambre _type;
    private Double _prix;
    private Hebergement _hebergement;
    private int _nombreDisponible;

    public Chambre(TypeChambre type, Double prix, int nombreDisponible) {   // CORRECTION : retrait de Hebergement
        this.set_nombreDisponible(nombreDisponible);
        this.set_prix(prix);
        this.set_type(type);
    }

    public TypeChambre get_type() {
        return _type;
    }

    public void set_type(TypeChambre _type) {
        if (_type == null)
            throw new IllegalArgumentException("Le type ne doit pas être null");
        this._type = _type;
    }

    public Double get_prix() {
        return _prix;
    }

    public void set_prix(Double _prix) {
        if (_prix == null || _prix <= 0)                     // CORRECTION : ajout du check null
            throw new IllegalArgumentException("Le prix doit être supérieur à 0");
        this._prix = _prix;
    }

    public Hebergement get_hebergement() {
        return _hebergement;
    }

    public void set_hebergement(Hebergement _hebergement) {  // inchangé : appelé par addChambre()
        if (_hebergement == null)
            throw new IllegalArgumentException("L'hébergement ne doit pas être null");
        this._hebergement = _hebergement;
    }

    public int get_nombreDisponible() {
        return _nombreDisponible;
    }

    public void set_nombreDisponible(int _nombreDisponible) {
        if (_nombreDisponible < 0)
            throw new IllegalArgumentException("Le nombre de chambres disponibles ne peut pas être négatif");
        this._nombreDisponible = _nombreDisponible;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Chambre autre = (Chambre) obj;
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
        return String.format("Chambre=[%s, %s, %s, %d disponible(s)]",   // AJOUT : affichage du stock
                this._type,
                this._hebergement,
                this._prix,
                this._nombreDisponible
        ).trim();
    }
}