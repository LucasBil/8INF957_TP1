package org.example.models.hebergement;

import org.example.models.Adresse;
import org.example.models.Chambre;
import org.example.models.Service;
import org.example.models.enums.TypeHebergement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public abstract class Hebergement {
    private Adresse _adresse;
    private ArrayList<Chambre> _chambres = new ArrayList<>();
    private ArrayList<Service> _services = new ArrayList<>();

    public Hebergement(Adresse adresse) {
        this.set_adresse(adresse);
    }

    public abstract String getDescription();

    public abstract TypeHebergement get_typeHebergement();

    public Adresse get_adresse() {
        return _adresse;
    }

    public void set_adresse(Adresse _adresse) {
        if (_adresse == null)
            throw new IllegalArgumentException("L'adresse ne doit pas être null");
        this._adresse = _adresse;
    }

    public List<Chambre> get_chambres() {
        return Collections.unmodifiableList(_chambres);
    }

    public Hebergement addChambre(Chambre chambre) {
        if (chambre == null)
            throw new IllegalArgumentException("La chambre ne doit pas être null");
        _chambres.add(chambre);
        chambre.set_hebergement(this);
        return this;
    }

    public List<Service> get_services() {
        return Collections.unmodifiableList(_services);
    }

    public Hebergement addService(Service service) {
        if (service == null)
            throw new IllegalArgumentException("Le service ne doit pas être null");
        _services.add(service);
        service.set_hebergement(this);
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Hebergement autre = (Hebergement) obj;
        return Objects.equals(this._adresse, autre._adresse);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._adresse);
    }

    @Override
    public String toString() {
        return String.format("%s=[%s]",
                this.getDescription(),
                this._adresse
        ).trim();
    }
}