package org.example.models;

import java.util.Objects;

public class Adresse {
    private String _pays;
    private String _province;
    private String _ville;
    private String _quartier;
    private String _rue;

    public Adresse(String pays, String province, String ville, String quartier, String rue) {
        this._pays = pays;
        this._province = province;
        this._ville = ville;
        this._quartier = quartier;
        this._rue = rue;
    }

    public Adresse(String pays, String province, String ville) {
        this(pays, province, ville,null, null);
    }

    public String get_rue() {
        return _rue;
    }

    public void set_rue(String _rue) {
        this._rue = _rue;
    }

    public String get_quartier() {
        return _quartier;
    }

    public void set_quartier(String _quartier) {
        this._quartier = _quartier;
    }

    public String get_ville() {
        return _ville;
    }

    public void set_ville(String _ville) {
        if (_ville == null || _ville.trim().isEmpty())
            throw new IllegalArgumentException("La ville ne doit pas être null ou vide");
        this._ville = _ville;
    }

    public String get_province() {
        return _province;
    }

    public void set_province(String _province) {
        if (_province == null || _province.trim().isEmpty())
            throw new IllegalArgumentException("La province ne doit pas être null ou vide");
        this._province = _province;
    }

    public String get_pays() {
        return _pays;
    }

    public void set_pays(String _pays) {
        if (_pays == null || _pays.trim().isEmpty())
            throw new IllegalArgumentException("Le pays ne doit pas être null ou vide");
        this._pays = _pays;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Adresse autre = (Adresse) obj;
        return Objects.equals(this._rue, autre._rue)
                && Objects.equals(this._quartier, autre._quartier)
                && Objects.equals(this._ville, autre._ville)
                && Objects.equals(this._province, autre._province)
                && Objects.equals(this._pays, autre._pays);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._rue, this._quartier, this._ville, this._province, this._pays);
    }

    @Override
    public String toString() {
        return String.format("Adresse=[%s %s %s, %s, %s]",
                Objects.requireNonNullElse(this._rue, ""),
                Objects.requireNonNullElse(this._quartier, ""),
                this._ville, this._province, this._pays)
        .trim();
    }
}
