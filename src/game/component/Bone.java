package game.component;

import game.logic.ThrowableItem;
import game.logic.Player;

public class Bone extends ThrowableItem {
    public Bone() {
        super(10, 5, "normal");
    }

    @Override
    public void applyEffect(Player target) {
        target.takeDamage(damage);
        System.out.println("Bone hits and deals " + damage + " damage.");
    }
}