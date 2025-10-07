package Service.util;

import Entity.Credit;

public class MensualiteRunnable implements Runnable {

    private final Credit credit;

    public MensualiteRunnable(Credit credit) {
        this.credit = credit;
    }

    @Override
    public void run() {
        double montantMensuel = (credit.getAmount() + (credit.getAmount() * credit.getTaux() / 100)) / credit.getDuree();

        for (int i = 1; i <= credit.getDuree(); i++) {
            System.out.printf("Mensualité %d : %.2f MAD planifiée pour le mois %d\n", i, montantMensuel, i);

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
