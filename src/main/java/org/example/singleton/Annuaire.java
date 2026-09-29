package org.example.singleton;

import org.example.models.*;

import java.util.ArrayList;

public class Annuaire {
    private static Annuaire _instance = new Annuaire();

    public static Annuaire getInstance() {
        return _instance;
    }

    /// Property of instance
    private ArrayList<Reservation> _reservations = new ArrayList<>();
    private ArrayList<Client> _clients = new ArrayList<>();
    private ArrayList<Hebergement> _hebergements = new ArrayList<>();
    private ArrayList<Chambre> _chambres = new ArrayList<>();
    private ArrayList<Service> _services = new ArrayList<>();

    private Annuaire(){}

    public ArrayList<Client> get_clients() {
        return _clients;
    }

    public ArrayList<Reservation> get_reservations() {
        return _reservations;
    }

    public Annuaire addClient(Client client) {
        boolean duplicated = _clients.stream().anyMatch(o -> o.equals(client));
        if (duplicated)
            throw new IllegalArgumentException("Un client existe déjà : " + client);
        _clients.add(client);
        return this;
    }

    public Annuaire addReservation(Reservation reservation) {
        boolean duplicated = _reservations.stream().anyMatch(o -> o.equals(reservation));
        if (duplicated)
            throw new IllegalArgumentException("La reservation existe déjà : " + reservation);
        _reservations.add(reservation);
        return this;
    }

    public Annuaire removeReservation(Reservation reservation) {
        _reservations.remove(reservation);
        return this;
    }
}