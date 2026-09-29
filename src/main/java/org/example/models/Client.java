package org.example.models;

import java.util.Objects;

public class Client {
    private String _nom;
    private Adresse _adresse;
    private String _email;
    private String _numtel;

    public Client(String nom, Adresse adresse, String email, String numtel) {
        this.set_adresse(adresse);
        this.set_email(email);
        this.set_nom(nom);
        this.set_numtel(numtel);
    }

    public String get_nom() {
        return _nom;
    }

    public void set_nom(String _nom) {
        this._nom = _nom;
    }

    public Adresse get_adresse() {
        return _adresse;
    }

    public void set_adresse(Adresse _adresse) {
        this._adresse = _adresse;
    }

    public String get_email() {
        return _email;
    }

    public void set_email(String _email) {
        this._email = _email;
    }

    public String get_numtel() {
        return _numtel;
    }

    public void set_numtel(String _numtel) {
        this._numtel = _numtel;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Client autre = (Client) obj;
        return Objects.equals(this._nom, autre._nom)
                && Objects.equals(this._email, autre._email)
                && Objects.equals(this._adresse, autre._adresse)
                && Objects.equals(this._numtel, autre._numtel);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._nom, this._email, this._numtel, this._adresse);
    }

    @Override
    public String toString() {
        return String.format("Client=[%s, %s, %s, %s]",
                this._nom, this._email, this._numtel, this._adresse);
    }
}
