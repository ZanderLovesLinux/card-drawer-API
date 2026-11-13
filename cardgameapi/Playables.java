package cardgameapi;

import java.util.ArrayList;
import java.util.InputMismatchException;

abstract class Playables {

    // the hand every playable will have
    ArrayList<String> hand = new ArrayList<>();

    // getters and setters
    /** Returns the hand of the object it was called on **/
    public ArrayList<String> getHand() {
        return this.hand;
    }

    /*
     * An integer you can store the value of a player's hand in. Useful for card
     * games where hands are compared; you can make different card combos worth
     * different amounts
     */

    int handValue;

    //getters and setters
    /**returns the hand value of the object it was called on **/
    public int getHandValue() {
        return this.handValue;
    }

    /**sets the hand value of the object it was called on 
     * @param value what to set the handValue to **/
    public void setHandValue(int value) {
        this.handValue = value;
    }


    /**
     * A method to get the raw value of a hand, just uses getCardValue in a loop on
     * the objects hand
     * 
     * @param checkAces a boolean whether or not to check for aces
     **/
    public int calcHandValue(boolean checkAces) {
        int value = 0;
        for (int i = 0; i < this.hand.size(); i++) {
            value = value + getCardValue(hand.get(i), checkAces);
        }
        return value;
    }

    /**
     * It's getCardRank but it returns an integer
     * 
     * @param theCard     The card to check
     * @param checkForAce Whether to ask user for ace value
     **/
    public int getCardValue(String theCard, boolean checkForAce) throws IllegalArgumentException {
        // binary string
        String binString;
        int value = Integer.parseInt(theCard);

        // It's a universal parser now, since every card is forced to 7 bits value
        binString = Integer.toBinaryString(value).substring(3);

        // parses string with radix 2 to convert binary to normal number
        int number = Integer.parseInt(binString, 2);

        if (number == 1) {
            if (checkForAce) {
                int aceValue = 0;
                while (aceValue != 1 && aceValue != 11) {
                    try {
                        System.out.println("What would you like your ace's value to be this turn; 1 or 11?");
                        aceValue = Cards.scanner.nextInt();
                        // clears input useless newline
                        Cards.scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter \"1\" or \"11\":");
                    }
                }
                return aceValue;
            } else {
                // replace getAceValue in cardGame for the thing.
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

    /**
     * It returns the cards' rank as a string
     * 
     * @param theCard The card to check
     **/
    public String getCardRank(String theCard) throws IllegalArgumentException {
        // binary string
        String binString;
        int value = Integer.parseInt(theCard);

        // It's a universal parser now, since every card is forced to 7 bits value
        binString = Integer.toBinaryString(value).substring(3);

        // parses string with radix 2 to convert binary to normal number
        int number = Integer.parseInt(binString, 2);

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
    public String getCardSuit(String theCard) {

        // subtract 64 to offset the original add
        int value = Integer.parseInt(theCard);
        // I use an if else bc i need to compare
        if (value >= 112) {
            return "Hearts";
        } else if (value >= 96) {
            return "Diamonds";
        } else if (value >= 80) {
            return "Spades";
        } else {
            return "Clubs";
        }
    }

    /**
     * It takes a hand to parse and returns the parsed hand as a string
     * 
     * @param toParse the hand to parse, send as the raw arraylist
     **/
    public String parseHand(ArrayList<String> toParse) {
        String suit;
        String rank;
        String currCard;
        String cardString = "";
        for (int i = 0; i < toParse.size(); i++) {
            currCard = toParse.get(i);
            suit = getCardSuit(currCard);
            rank = getCardRank(currCard);
            cardString = cardString + rank + " of " + suit + ", ";

        }
        // removes the last ", "
        return cardString.substring(0, cardString.length() - 2);
    }

}