import cardgameapi.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Blackjack {

    static final Scanner scanner = new Scanner(System.in);

    /*
     * cool thing about java, playables are abstract, but dealers and players both
     * count as playables
     */
    /**
     * Automatically calculates hand value for a playable object - no player choice.
     * It is built for dealers, so only works on a hand value of 2.
     * 
     * @param playable the playable you want to calculate for
     */
    public static int autoHandValue(Playables playable) {
        int acecount = 0;
        for (int i : playable.getHand()) {
            // anonymous object, 1 use
            if (playable.getCardRank(i).equals("Ace")) {
                acecount++;
            }

            // in this, i have set the value of aces to zero so it won't calc them
            return switch (acecount) {
                case 0 -> playable.calcHandValue();
                case 1 -> playable.calcHandValue() + 11;
                case 2 -> 12;
                // i can tell something has gone wrong if this is entered
                default -> 9999999;
            };
        }
        // shoudn't happen since hands are 2 long
        return 99;
    }

    // the function to play the players turn, optional if you want to do it
    // differently
    static boolean playTurn(Player player, Dealer dealer) {
        System.out.println(player.getName() + ", it's your turn");
        System.out.println("Your hand:");

        // I declare this as a variable because it is used too much to justify it not
        // being one.
        String parsedHand = player.parseHand();
        System.out.println(parsedHand);

        if (parsedHand.contains("Ace")) {
            // only need this if it contains ace
            String[] tmpHand = new String[player.getHand().size()];
            for (int i = 0; i < player.getHand().size(); i++) {
                int tmpCard = player.getHand().get(i);
                // I only need ranks to check if a card contains aces
                tmpHand[i] = player.getCardRank(tmpCard);
            }

            boolean acesLeft = true;
            while (acesLeft) {

                int aceValue = 0;
                while (aceValue != 1 && aceValue != 11) {
                    try {
                        System.out.println("What would you like your ace's value to be this turn; 1 or 11?");
                        aceValue = scanner.nextInt();
                        // clears input useless newline
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Please enter \"1\" or \"11\":");
                    }
                }
                player.setHandValue(player.getHandValue() + aceValue);
                // sets the first ace in tmpHand to null since we don't have to worry about it
                for (int i = 0; i < tmpHand.length; i++) {
                    if (tmpHand[i].equals("Ace")) {
                        // can't set to null because I need to loop & compare through the entire list
                        // later
                        tmpHand[i] = "";
                        break;
                    }
                }

                // checks to see if there are any aces left
                for (String i : tmpHand) {
                    if (i.equals("Ace")) {
                        acesLeft = true;
                        break;
                    } else {
                        acesLeft = false;
                    }
                }
            }
        } else {
            player.setHandValue(player.calcHandValue());
        }

        System.out.print("Your hand has a value of ");
        System.out.println(player.getHandValue());

        // blackjack check
        if (parsedHand.contains("Ace")
                && (parsedHand.contains("King") || parsedHand.contains("Queen") || parsedHand.contains("Jack"))) {
            System.out.println("Blackjack!");
            return true;
        }

        String action = "";

        while (!action.toLowerCase().equals("stand")) {
            action = "";

            while (!(action.toLowerCase().equals("hit")) && !(action.toLowerCase().equals("stand"))) {
                System.out.println("What do you want to do, hit or stand? Enter \"hit\" or \"stand\":");
                action = scanner.nextLine();
            }

            if (action.toLowerCase().equals("stand")) {
                System.out.println("You stood.");
            } else {
                // runs while you hit
                System.out.println("You hit");
                // deals a card to the player
                dealer.deal(1, player);

                System.out.println("Your hand is now:\n" + player.parseHand());
                player.setHandValue(player.calcHandValue());

                // if over 21, you bust
                if (player.getHandValue() > 21) {
                    System.out.println("Bust!");
                    return false;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {

        // I use an anonymous class for blackjack specific dealer deal
        Dealer dealer = new Dealer();

        // the name of the winner, starts out as the dealer and if a player wins it
        // changes to that
        String winner = "The Dealer";

        // boolean to determine if it's the dealer's turn
        boolean isDealerTurn = false;

        // sets whos turn it is to zero and that nobody has won
        int turn = 0;
        boolean hasWon = false;

        Table table = new Table();

        /*
         * blackjack aces are unique so I'm custom writing them, i need them to not
         * count towards your hands' value
         */
        table.setAceValue(0);

        // sets royal values to 10
        table.setRoyalValues(10, 10, 10);

        // prompts the players for their number and names and sets up the lists
        table.playerPrompt();
        final Player[] gamePlayers = table.initPlayers().clone();

        // shuffles and deals
        dealer.shuffle();
        dealer.dealAll(2);

        /*
         * The main loop. While nobody has won, it prints the dealer's visible card at
         * the beginning of the loop and the runs the players turns, and then the
         * dealers turn
         */

        while (!hasWon) {

            if (isDealerTurn) {

                // in blackjack, everyone goes once, so the dealer's turn will be the last and
                // determine the winner
                String dealParsedHand = dealer.parseHand();
                if (dealParsedHand.contains("Ace")
                        && (dealParsedHand.contains("King") || dealParsedHand.contains("Queen")
                                || dealParsedHand.contains("Jack"))) {
                    System.out.println("The Dealer got Blackjack!");
                    break;
                }

                System.out.println("The dealer's deck was: ");
                System.out.println(dealParsedHand);

                System.out.print("The dealer's hand had a value of ");
                int dealValue = autoHandValue(dealer);
                System.out.println(dealValue);

                int highestSoFar = 0;
                String winPlayer = "";
                for (Player i : gamePlayers) {
                    if (i.getHandValue() > highestSoFar && i.getHandValue() < 22) {
                        highestSoFar = i.getHandValue();
                        winPlayer = i.getName();
                    }
                }
                if (highestSoFar > dealer.getHandValue()) {
                    winner = winPlayer;
                    System.out.println(highestSoFar);
                } else if (highestSoFar == dealer.getHandValue()) {
                    System.out.println("It was a tie between the dealer and " + winPlayer);
                    System.exit(0);
                }
                break;

            } else {
                System.out.println("Dealer's visible card:");

                // prints the rank and suit of visible card
                System.out.println(dealer.getCardRank(dealer.getHand().get(0)) + " of "
                        + dealer.getCardSuit(dealer.getHand().get(0)));
                hasWon = playTurn(gamePlayers[turn], dealer);
                turn++;
                if (hasWon) {
                    winner = gamePlayers[turn].getName();
                    break;
                }
            }
            if (turn == gamePlayers.length) {
                isDealerTurn = true;
                turn = 0;
            }
        }
        System.out.println(winner + " has won the game!");

    }

}
