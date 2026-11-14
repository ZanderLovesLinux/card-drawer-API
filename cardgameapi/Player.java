package cardgameapi;

public class Player extends Playables {

    /*
     * A player is basically a flavorless playable; There's almost no extra features
     * they get. So the class is almost empty; near everything is defined in
     * Playables
     */

    // player name
    String name;

    // constructor for the name
    Player(String name) {
        this.name = name;
    }

    // getter and setter for the name
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name of the player
     * 
     * @param toSet what to set the name to
     **/
    public void setName(String toSet) {
        this.name = toSet;
    }

}
