package utils;

import Entity.User;

public class validatedd {
    private final User user ;

    public validatedd( User user) {

        this.user = user;
    }

    public static boolean validate(User user) {
        if (user.getEmail() == null || user.getEmail().isEmpty()) {
            System.out.println("Email requis.");
            return false;
        }

        if (!user.getEmail().matches("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
            System.out.println("Email invalide.");
            return false;
        }

        if (user.getPassword() == null || user.getPassword().isEmpty()) {
            System.out.println("Mot de passe requis.");
            return false;
        }

        if (user.getPassword().length() < 8) {
            System.out.println("Le mot de passe doit faire au moins 8 caractères.");
            return false;
        }

        if (user.getPassword().contains(" ")) {
            System.out.println("Le mot de passe ne doit pas contenir d'espaces.");
            return false;
        }

        return true;
    }
}
