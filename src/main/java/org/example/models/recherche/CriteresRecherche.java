package org.example.models.recherche;

import org.example.models.enums.TypeChambre;
import org.example.models.enums.TypeHebergement;
import org.example.models.enums.TypeService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CriteresRecherche {
    private final TypeHebergement _typeHebergement;
    private final String _pays;
    private final String _province;
    private final String _ville;
    private final String _quartier;
    private final String _rue;
    private final TypeChambre _typeChambre;
    private final List<TypeService> _servicesRequis;
    private final Double _prixMax;

    // Obligatoires
    private final LocalDateTime _dateArrivee;
    private final LocalDateTime _dateDepart;

    private CriteresRecherche(Builder builder) {
        this._typeHebergement = builder.typeHebergement;
        this._pays = builder.pays;
        this._province = builder.province;
        this._ville = builder.ville;
        this._quartier = builder.quartier;
        this._rue = builder.rue;
        this._typeChambre = builder.typeChambre;
        this._servicesRequis = builder.servicesRequis;
        this._prixMax = builder.prixMax;
        this._dateArrivee = builder.dateArrivee;
        this._dateDepart = builder.dateDepart;
    }

    public TypeHebergement get_typeHebergement() { return _typeHebergement; }
    public String get_pays() { return _pays; }
    public String get_province() { return _province; }
    public String get_ville() { return _ville; }
    public String get_quartier() { return _quartier; }
    public String get_rue() { return _rue; }
    public TypeChambre get_typeChambre() { return _typeChambre; }
    public List<TypeService> get_servicesRequis() { return Collections.unmodifiableList(_servicesRequis); }
    public Double get_prixMax() { return _prixMax; }
    public LocalDateTime get_dateArrivee() { return _dateArrivee; }
    public LocalDateTime get_dateDepart() { return _dateDepart; }

    @Override
    public String toString() {
        return String.format("CriteresRecherche=[type=%s, region=%s/%s/%s/%s/%s, chambre=%s, services=%s, prixMax=%s, du %s au %s]",
                _typeHebergement, _pays, _province, _ville, _quartier, _rue,
                _typeChambre, _servicesRequis, _prixMax, _dateArrivee, _dateDepart);
    }

    public static class Builder {
        private TypeHebergement typeHebergement;
        private String pays;
        private String province;
        private String ville;
        private String quartier;
        private String rue;
        private TypeChambre typeChambre;
        private List<TypeService> servicesRequis = new ArrayList<>();
        private Double prixMax;
        private LocalDateTime dateArrivee;
        private LocalDateTime dateDepart;

        public Builder(LocalDateTime dateArrivee, LocalDateTime dateDepart) {
            if (dateArrivee == null || dateDepart == null)
                throw new IllegalArgumentException("Les dates d'arrivée et de départ sont obligatoires");
            if (!dateArrivee.isBefore(dateDepart))
                throw new IllegalArgumentException("La date d'arrivée doit être avant la date de départ");
            this.dateArrivee = dateArrivee;
            this.dateDepart = dateDepart;
        }

        public Builder typeHebergement(TypeHebergement type) {
            this.typeHebergement = type;
            return this;
        }

        public Builder region(String pays, String province, String ville, String quartier, String rue) {
            this.pays = pays;
            this.province = province;
            this.ville = ville;
            this.quartier = quartier;
            this.rue = rue;
            return this;
        }

        public Builder typeChambre(TypeChambre type) {
            this.typeChambre = type;
            return this;
        }

        public Builder servicesRequis(List<TypeService> services) {
            if (services != null)
                this.servicesRequis = new ArrayList<>(services);
            return this;
        }

        public Builder prixMax(Double prixMax) {
            if (prixMax != null && prixMax <= 0)
                throw new IllegalArgumentException("Le prix maximum doit être supérieur à 0");
            this.prixMax = prixMax;
            return this;
        }

        public CriteresRecherche build() {
            return new CriteresRecherche(this);
        }
    }
}