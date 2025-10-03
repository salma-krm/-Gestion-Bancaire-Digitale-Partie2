package utils;

import Entity.Client;

public class validatedClient {

    public static boolean validatedClient(Client client) {
        if (client == null) {
            System.out.println("Le client ne peut pas être nul.");
            return false;
        }

        if (client.getEmail() == null || client.getEmail().isEmpty()) {
            System.out.println("Email requis.");
            return false;
        }

        if (!client.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            System.out.println("Email invalide.");
            return false;
        }

        if (client.getFirstName() == null || client.getFirstName().length() < 3) {
            System.out.println("Le prénom doit contenir au moins 3 caractères.");
            return false;
        }

        if (client.getLastName() == null || client.getLastName().length() < 3) {
            System.out.println("Le nom doit contenir au moins 3 caractères.");
            return false;
        }

        if (client.getCIN() == null || !client.getCIN().matches("^[A-Za-z]\\d{7}$")) {
            System.out.println("CIN invalide. Elle doit commencer par une lettre suivie de 7 chiffres (ex: A1234567).");
            return false;
        }

        return true;
    }

}
