public interface SpaceShip {
    boolean launch();
    boolean land();
    boolean canCarry(Item itm);
    void carry(Item itm);
}
