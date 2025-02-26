package game.component;

import game.logic.Player;
import game.logic.ThrowableItem;

public class Dog extends Player {
    public Dog(String name, int x, int y) {
        super(name, x, y);
    }

    @Override
    public void throwItem(ThrowableItem item, Player target) {
        System.out.println(name + " throws " + item.getClass().getSimpleName() + " at " + target.getName());
        item.applyEffect(target);
    }
}