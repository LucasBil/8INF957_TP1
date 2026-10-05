package org.example.singleton;

import org.example.models.*;
import org.example.models.enums.TypeService;
import org.example.models.hebergement.Hebergement;
import org.example.models.recherche.CriteresRecherche;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Annuaire {
    private static Annuaire _instance = new Annuaire();

    public static Annuaire getInstance() {
        return _instance;
    }

    private ArrayList<Reservation> _reservations = new ArrayList<>();
    private ArrayList<Client> _clients = new ArrayList<>();
    private ArrayList<Hebergement> _hebergements = new ArrayList<>();

    private Annuaire() {
    }

    public List<Client> get_clients() {
        return Collections.unmodifiableList(_clients);
    }

    public List<Reservation> get_reservations() {
        return Collections.unmodifiableList(_reservations);
    }

    public List<Hebergement> get_hebergements() {
        return Collections.unmodifiableList(_hebergements);
    }

    public Annuaire addClient(Client client) {
        boolean duplicated = _clients.stream().anyMatch(o -> o.equals(client));
        if (duplicated)
            throw new IllegalArgumentException("Un client existe déjà : " + client);
        _clients.add(client);
        return this;
    }

    public Annuaire addHebergement(Hebergement hebergement) {
        if (hebergement == null)
            throw new IllegalArgumentException("L'hébergement ne doit pas être null");
        boolean duplicated = _hebergements.stream().anyMatch(o -> o.equals(hebergement));
        if (duplicated)
            throw new IllegalArgumentException("Un hébergement existe déjà à cette adresse : " + hebergement);
        _hebergements.add(hebergement);
        return this;
    }

    public Annuaire addReservation(Reservation reservation) {
        if (!estDisponible(reservation.get_chambre(), reservation.get_debut(), reservation.get_fin())) {
            throw new IllegalStateException(
                    "Aucune chambre disponible de ce type pour cette période : " + reservation.get_chambre());
        }
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

    public boolean estDisponible(Chambre chambre, LocalDateTime debut, LocalDateTime fin) {
        long reservationsExistantes = _reservations.stream()
                .filter(r -> r.get_chambre().equals(chambre))
                .filter(r -> periodesSeChevauchent(r.get_debut(), r.get_fin(), debut, fin))
                .count();
        return reservationsExistantes < chambre.get_nombreDisponible();
    }

    private boolean periodesSeChevauchent(LocalDateTime debut1, LocalDateTime fin1,
                                          LocalDateTime debut2, LocalDateTime fin2) {
        return debut1.isBefore(fin2) && debut2.isBefore(fin1);
    }

    public List<Chambre> rechercherChambresDisponibles(CriteresRecherche criteres) {
        return _hebergements.stream()
                .filter(h -> correspondHebergement(h, criteres))
                .flatMap(h -> h.get_chambres().stream())
                .filter(c -> criteres.get_typeChambre() == null || c.get_type() == criteres.get_typeChambre())
                .filter(c -> criteres.get_prixMax() == null || c.get_prix() <= criteres.get_prixMax())
                .filter(c -> estDisponible(c, criteres.get_dateArrivee(), criteres.get_dateDepart()))
                .toList();
    }

    private boolean correspondHebergement(Hebergement h, CriteresRecherche criteres) {
        if (criteres.get_typeHebergement() != null && h.get_typeHebergement() != criteres.get_typeHebergement())
            return false;

        Adresse a = h.get_adresse();
        if (criteres.get_pays() != null && !criteres.get_pays().equalsIgnoreCase(a.get_pays()))
            return false;
        if (criteres.get_province() != null && !criteres.get_province().equalsIgnoreCase(a.get_province()))
            return false;
        if (criteres.get_ville() != null && !criteres.get_ville().equalsIgnoreCase(a.get_ville()))
            return false;
        if (criteres.get_quartier() != null && !criteres.get_quartier().equalsIgnoreCase(a.get_quartier()))
            return false;
        if (criteres.get_rue() != null && !criteres.get_rue().equalsIgnoreCase(a.get_rue()))
            return false;

        if (!criteres.get_servicesRequis().isEmpty()) {
            List<TypeService> servicesOfferts = h.get_services().stream()
                    .map(Service::get_type)
                    .toList();
            if (!servicesOfferts.containsAll(criteres.get_servicesRequis()))
                return false;
        }

        return true;
    }
    void clear() {
        _reservations.clear();
        _clients.clear();
        _hebergements.clear();
    }
}