package game.component;

import game.logic.ThrowableItem;
import game.logic.Player;

public class Ball extends ThrowableItem {
    public Ball() {
        super(8, 7, "bounce");
    }

    @Override
    public void applyEffect(Player target) {
        target.takeDamage(damage);
        target.takeDamage(damage);
        System.out.println("Ball hits twice and deals " + (damage * 2) + " damage.");
    }
}