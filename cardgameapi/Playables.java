package cardgameapi;

import java.util.ArrayList;

public abstract class Playables {

    // the hand every playable will have
    ArrayList<Integer> hand = new ArrayList<>();

    // getters and setters
    /** Returns the hand of the object it was called on **/
    public ArrayList<Integer> getHand() {
        return this.hand;
    }

    /*
     * An integer you can store the value of a player's hand in. Useful for card
     * games where hands are compared; you can make different card combos worth
     * different amounts, starts at zero for easier usage
     */

    int handValue = 0;

    // getters and setters
    /** returns the hand value of the object it was called on **/
    public int getHandValue() {
        return this.handValue;
    }

    /**
     * sets the hand value of the object it was called on
     * 
     * @param value what to set the handValue to
     **/
    public void setHandValue(int value) {
        this.handValue = value;
    }

    /**
     * A method to get the raw value of a hand as an integer, just uses getCardValue
     * in a loop on the objects hand
     * 
     **/
    public int calcHandValue() {
        int value = 0;
        for (int i : this.hand) {
            value = value + getCardValue(i);
        }
        return value;
    }

    /**
     * It's getCardRank but it returns an integer
     * 
     * @param theCard The card to check
     **/
    public int getCardValue(int theCard) throws IllegalArgumentException {

        /*
         * Converts the card to a binary number, cuts off the first 3 bits, then back
         * again
         */
        int number = Integer.parseInt(Integer.toBinaryString(theCard).substring(3), 2);

        if (number == 1) {
            // returns the ace value defined in dealer with a temporary object
            return new Table().getAceValue();
        } else if (number < 11) {
            return number;
        } else {
            int royalValues[] = Table.getRoyalValues();
            return switch (number) {
                case 11 -> royalValues[0];
                case 12 -> royalValues[1];
                case 13 -> royalValues[2];
                default -> throw new IllegalArgumentException(
                        "The card was not correctly parsed in getCardValue; it had a value other than the allowed ones. Number is "
                                + number);
            };
        }
    }

    /**
     * It returns the cards' rank as a string
     * 
     * @param theCard The card to check
     **/
    public String getCardRank(int theCard) throws IllegalArgumentException {

        // It's a universal parser now, since every card is forced to 7 bits value
        // parses string with radix 2 to convert binary to normal number
        int number = Integer.parseInt(Integer.toBinaryString(theCard).substring(3), 2);

        if (number < 11 && number > 1) {
            // just the number part as a string
            return Integer.toString(number);
        } else if (number > 10) {

            // learned this thing called a rule switch, pretty useful.
            return switch (number) {
                case 11 -> "Jack";
                case 12 -> "Queen";
                case 13 -> "King";
                // should NOT happen
                default -> throw new IllegalArgumentException(
                        "The card was not correctly parsed in getCardRank, it had a value other than the allowed ones, number is "
                                + number);

            };
        } else {
            // only occurs when it's a one, which I'm interpreting as an ace
            return "Ace";
        }

    }

    /**
     * It returns the cards' suit as a string
     * 
     * @param theCard The card to check
     **/
    public String getCardSuit(int theCard) {

        // I use an if else instead of a rule switch bc i need to compare
        if (theCard >= 112) {
            return "Hearts";
        } else if (theCard >= 96) {
            return "Diamonds";
        } else if (theCard >= 80) {
            return "Spades";
        } else {
            return "Clubs";
        }
    }

    /**
     * It takes a hand to parse and returns the parsed hand as a string. It uses the
     * objects' hand variable
     * 
     **/
    public String parseHand() {
        String suit;
        String rank;
        int currCard;
        String cardString = "";
        for (int i = 0; i < this.hand.size(); i++) {
            currCard = this.hand.get(i);
            suit = getCardSuit(currCard);
            rank = getCardRank(currCard);
            cardString = cardString + rank + " of " + suit + ", ";

        }
        // removes the last ", "
        return cardString.substring(0, cardString.length() - 2);
    }

}