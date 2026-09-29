package org.example;

import org.example.models.*;
import org.example.models.enums.TypeChambre;
import org.example.models.enums.TypeHebergement;
import org.example.models.enums.TypeService;
import org.example.singleton.Annuaire;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static void main() {
        /// Initialisation de l'annuaire
        Annuaire annuaire = Annuaire.getInstance();

        Adresse adresse = new Adresse("Canada", "Saguenay", "Chicoutimi", null, null);
        Client client_1 = new Client("BILLY Lucas", adresse, "lucas.billy@etu.univ-savoie.fr", "+33750253256");
        Client client_2 = new Client("AMBERT Maxence", adresse, "lucas.billy@etu.univ-savoie.fr", "+33750253256");
        annuaire.addClient(client_1)
                .addClient(client_2);

        for (Client c : annuaire.get_clients()) {
            System.out.println(c);
        }

        /*
        Hebergement hebergement = new Hebergement(TypeHebergement.HOTEL, adresse);
        Chambre chambre = new Chambre(TypeChambre.SIMPLE, hebergement, 50.0);
        Service service = new Service(TypeService.ACCES_HANDICAPE, hebergement, 10.0);

        Reservation reservation = new Reservation(
                chambre,
                new ArrayList<>(List.of(service)),
                client,
                LocalDateTime.now(),
                LocalDateTime.of(2026,9,26, 0, 0, 0)
        );
        System.out.println(reservation);
        System.out.println(reservation.getPrix());
         */
    }
}
