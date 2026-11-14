package cardgameapi;

import java.util.ArrayList;

public class Dealer extends Playables {

    /**
     * The hands of the dealer, which do the shuffling and everything. This extends
     * cards
     **/
    private class hands extends Cards {
    }

    hands hands = new hands();

    // a dealer's hand value, similar to the players.

    // copies of all of the cards methods

    /**
     * Adds a player to the master list of players
     * 
     * @param player the player to add to the list
     */
    public void addPlayer(Player player) {
        Cards.addPlayer(player);
    }

    /**
     * returns the list of players
     */
    public ArrayList<Player> getPlayers() {
        return hands.getPlayers();
    }

    /** shuffles the deck */
    public void shuffle() {
        hands.shuffle();
    }

    /**
     * deals cards to players
     * 
     * @param count     how many cards to deal
     * @param thePlayer which player to deal to
     */
    public void deal(int count, Player thePlayer) {
        hands.deal(count, thePlayer);
    }

    /**
     * This function (Manual deal) lets you manually add a cards' value to a
     * players' hand.
     * 
     * @param card   the card as an int which will be added to the players' hand
     * @param player the player whos' hand to add the card to
     */
    public void manDeal(int card, Player player) {
        hands.manDeal(card, player);
    }

    /**
     * This deal just needs a count and returns a list of strings (the cards,
     * returns this format to save memory)
     * 
     * @param count the amount of cards and the size of the list it returns
     */
    public int[] deal(int count) {
        return hands.deal(count);
    }

    /**
     * Dealer's method to to all players. Meant to be run by the dealer, since the
     * final step adds to the runner's hand.
     * 
     * @param count the number of cards to deal
     */
    public void dealAll(int count) {
        for (Player i : getPlayers()) {
            deal(count, i);
        }

        // enhanced for loop
        // I manually did this because I didn't want to import collections for one thing
        for (int i : hands.deal(count)) {
            this.hand.add(i);
        }
    }
}
