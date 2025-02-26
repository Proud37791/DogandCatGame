package game.component;

import game.logic.ThrowableItem;
import game.logic.Player;

public class Fish extends ThrowableItem {
    public Fish() {
        super(10, 5, "normal");
    }

    @Override
    public void applyEffect(Player target) {
        target.takeDamage(damage);
        System.out.println("Fish hits and deals " + damage + " damage.");
    }
}