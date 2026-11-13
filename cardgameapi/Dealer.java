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
     * This deal just needs a count and returns a list of strings (the cards,
     * returns this format to save memory)
     * 
     * @param count the amount of cards and the size of the list it returns
     */
    protected String[] deal(int count) {
        return hands.deal(count);
    }

    /**
     * Deals to all players. Meant to be run by the dealer, since the final step
     * adds to the runner's hand.
     * 
     * @param count the number of cards to deal
     */
    public void dealAll(int count) {
        for (Player i : getPlayers()) {
            deal(count, i);
        }

        // enhanced for loop
        // I manually did this because I didn't want to import collections for one thing
        for (String i : hands.deal(count)) {
            this.hand.add(i);
        }
    }

    // custom automatic get card value for the dealer since it shouldn't play
    @Override
    public int getCardValue(String theCard, boolean checkForAce) throws IllegalArgumentException {
        String binString;
        int value = Integer.parseInt(theCard);

        // It's a universal parser now, since every card is forced to 7 bits value
        binString = Integer.toBinaryString(value).substring(3);

        // parses string with radix 2 to convert binary to normal number
        int number = Integer.parseInt(binString, 2);

        if (number == 1) {
            // creates temporary hand without aces
            ArrayList<String> tmpList = new ArrayList<>(hand);
            for (String i : tmpList) {
                int x = Integer.parseInt(i);
                // all of the values of aces
                if (x == 65 || x == 81 || x == 97 || x == 113) {
                    tmpList.remove(i);
                }
            }
            int tmpValue = 0;
            for (String i : tmpList) {
                tmpValue = tmpValue + getCardValue(i, true);
            }

            // protects it from going over
            if (tmpValue > 10) {
                return 1;
            } else {
                return 11;
            }

        } else if (number < 11) {
            return number;
        } else {
            return switch (number) {
                case 11 -> 10;
                case 12 -> 10;
                case 13 -> 10;
                default -> throw new IllegalArgumentException(
                        "The card was not correctly parsed in getCardValue; it had a value other than the allowed ones. Number is "
                                + number);
            };
        }
    }

    // just goes through the hand and gets the value of every card
    @Override
    public int getHandValue() {
        int value = 0;
        for (int i = 0; i < hand.size(); i++) {
            value = value + getCardValue(hand.get(i), true);
        }
        return value;
    }

}
