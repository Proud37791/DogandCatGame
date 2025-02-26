package game.component;

import game.logic.Player;

public class PowerUp {
    private String type;
    private int effectDuration;

    public PowerUp(String type, int effectDuration) {
        this.type = type;
        this.effectDuration = effectDuration;
    }

    public void applyPowerUp(Player player) {
        // Add power-up logic here
        System.out.println("Power-up applied: " + type);
    }
}