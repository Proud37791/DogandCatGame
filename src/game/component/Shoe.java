package game.component;

import game.logic.ThrowableItem;
import game.logic.Player;

public class Shoe extends ThrowableItem {
    public Shoe() {
        super(5, 3, "stun");
    }

    @Override
    public void applyEffect(Player target) {
        target.takeDamage(damage);
        // Add stun logic here
        System.out.println("Shoe hits and stuns for 2 seconds.");
    }
}