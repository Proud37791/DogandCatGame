package game.logic;

public abstract class Player {
    protected String name;
    protected int health;
    protected int x, y;

    public Player(String name, int x, int y) {
        this.name = name;
        this.health = 100;
        this.x = x;
        this.y = y;
    }

    public abstract void throwItem(ThrowableItem item, Player target);

    public void takeDamage(int damage) {
        health -= damage;
        if (health < 0) health = 0;
    }

    public int getHealth() {
        return health;
    }

    public String getName() {
        return name;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}