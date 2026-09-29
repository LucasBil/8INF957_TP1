package org.example.models;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;

public class Reservation {
    private Chambre _chambre;
    private ArrayList<Service> _services;
    private Client _client;
    private LocalDateTime _debut;
    private LocalDateTime _fin;

    public Reservation(Chambre chambre, ArrayList<Service> services, Client client, LocalDateTime debut, LocalDateTime fin) {
        this.set_chambre(chambre);
        this.set_client(client);
        this.set_services(services);
        this.set_debut(debut);
        this.set_fin(fin);
    }

    public Chambre get_chambre() {
        return _chambre;
    }

    public void set_chambre(Chambre _chambre) {
        this._chambre = _chambre;
    }

    public ArrayList<Service> get_services() {
        return _services;
    }

    public void set_services(ArrayList<Service> _services) {
        this._services = _services;
    }

    public Client get_client() {
        return _client;
    }

    public void set_client(Client _client) {
        this._client = _client;
    }

    public LocalDateTime get_debut() {
        return _debut;
    }

    public void set_debut(LocalDateTime _debut) {
        if (this._fin != null && _debut.isAfter(this._fin))
            throw new IllegalArgumentException("La date de fin doit etre apres la date de debut");
        this._debut = _debut;
    }

    public LocalDateTime get_fin() {
        return _fin;
    }

    public void set_fin(LocalDateTime _fin) {
        if (this._debut != null && this._debut.isAfter(_fin))
            throw new IllegalArgumentException("La date de fin doit etre apres la date de debut");
        this._fin = _fin;
    }

    private long getNombreJours() {
        System.out.println(this._debut);
        System.out.println(this._fin);
        Duration duree = Duration.between(this._debut, this._fin);
        long heures = duree.toHours();
        System.out.println(heures);
        return (heures + 23) / 24;
    }

    public Double getPrix() {
        long nbJours = getNombreJours();
        Double prixJournalier = this._chambre.get_prix() + this._services.stream()
                .mapToDouble(Service::get_prix)
                .sum();
        return prixJournalier * nbJours;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Reservation autre = (Reservation) obj;
        return Objects.equals(this._chambre, autre._chambre)
                && Objects.equals(this._services, autre._services)
                && Objects.equals(this._client, autre._client)
                && Objects.equals(this._debut, autre._debut)
                && Objects.equals(this._fin, autre._fin);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this._chambre, this._services, this._client, this._debut, this._fin);
    }

    @Override
    public String toString() {
        return String.format("Reservation=[%s, %s, %s, %s, %s]",
                this._client,
                this._debut,
                this._fin,
                this._chambre,
                "{" + this._services.stream()
                        .map(Service::toString)
                        .collect(Collectors.joining(", ")) + "}"
        ).trim();
    }
}
