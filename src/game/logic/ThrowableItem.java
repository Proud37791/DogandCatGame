package game.logic;

public abstract class ThrowableItem {
    protected int damage;
    protected int speed;
    protected String effect;

    public ThrowableItem(int damage, int speed, String effect) {
        this.damage = damage;
        this.speed = speed;
        this.effect = effect;
    }

    public abstract void applyEffect(Player target);
}