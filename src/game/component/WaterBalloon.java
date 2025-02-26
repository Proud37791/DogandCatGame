package game.component;

import game.logic.ThrowableItem;
import game.logic.Player;

public class WaterBalloon extends ThrowableItem {
    public WaterBalloon() {
        super(6, 4, "slow");
    }

    @Override
    public void applyEffect(Player target) {
        target.takeDamage(damage);
        // Add slow logic here
        System.out.println("Water Balloon hits and slows movement.");
    }
}