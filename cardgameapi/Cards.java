package cardgameapi;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Cards {

    // the deck has all the current cards and is static because it should belong to
    // the board, not a dealer or a player
    static ArrayList<String> deck = new ArrayList<>();

    // global scanner everyone uses
    public static Scanner scanner = new Scanner(System.in);

    // static for optimization
    Random rStatic = new Random();

    // public list of all players along with getter and adder
    static ArrayList<Player> players = new ArrayList<>();

    protected static void addPlayer(Player player) {
        players.add(player);
    }

    protected ArrayList<Player> getPlayers() {
        return players;
    }

    /*
     * This is my binary representation of cards. It looks random, but it's not.
     * You may notice some numbers are missing. My formatting works in a binary
     * format, where the numbers are parsed as binary. My formatting has the first 2
     * bits representing the suit (first zero determines red or blank and second
     * determines which red or black) and the second 4 numbers have a binary
     * representation of the cards value from 1-16. so 111101 would be the king of
     * Diamonds; The first 2 ones signify that the suit is diamond, and the next 4
     * are a binary representation of 13, which I have mapped to King.
     * 
     * After reading this, you are probably noticing that none of these numbers fit
     * this notation. This is because they're shifted by 64. The binary parser had
     * some problems with low cards (spades and clubs) so I added a starting bit at
     * the start of the number. For example, 000001, which would be the ace of
     * clubs, to turn it into 1000001, so that the binary number is converted in a
     * parsing friendly format.
     */

    private static final int[] allCards = { 65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 81, 82, 83, 84, 85, 86,
            87, 88, 89, 90, 91, 92, 93, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 113, 114, 115,
            116, 117, 118, 119, 120, 121, 122, 123, 124, 125 };

    // shuffle isn't static because it needs to be called by dealer objects
    protected void shuffle() {
        ArrayList<String> tempDeck = new ArrayList<>();

        /*
         * We add 64 for the parsing later. It marks the start of the card and can be
         * removed, since it occupies the first binary digit.
         */

        // duplicating tempDeck into allCards with an enhanced for loop

        // i added as strings because arraylists hate ints for some reason.
        for (int i : allCards) {
            tempDeck.add(Integer.toString(i));
        }   

        // didnt use an enhanced for loop because it needs to go in a random order
        String toAdd;
        deck.clear();
        for (int i = 0; i < allCards.length; i++) {
            
            int deckIndex = rStatic.nextInt(0, tempDeck.size());
            toAdd = tempDeck.get(deckIndex);
            tempDeck.remove(deckIndex);
            deck.add(toAdd);

        }
    }

    /*
     * this will be inherited by dealer, it deals count cards by taking cards off
     * the top of the deck. this assumes a shuffled deck. It deals from the top of
     * the deck
     */

    // TODO: Overhaul this deal system to use a list of strings
    protected void deal(int count, Player thePlayer) {
        ArrayList<String> drawnCards = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            drawnCards.add(deck.get(0));
            deck.remove(0);
        }
        thePlayer.hand.addAll(drawnCards);
    }

    /*
     * This method just returns the drawn cards, useful for dealing to the dealer.
     * You might notice that I return a list here instead of adding it. That's
     * because this system is much simpler than dealing to players, and I would need
     * to rework a bunch of stuff for a little bit of savings. I'll probably do that
     * in a future patch
     */
    protected String[] deal(int count) {
        // formatting makes dealing weird if i don't add 1
        String[] drawnCards = new String[count];
        // used a normal list to be more memory efficient

        for (int i = 0; i < count; i++) {
            drawnCards[i] = (deck.get(0));
            deck.remove(0);
        }
        return drawnCards;
    }

}