import cardgameapi.*;

public class Blackjack extends CardGame {

    // the function to play the players turn, optional if you want to do it
    // differently
    boolean playTurn(Player player, Dealer dealer) {
        System.out.println(player.getName() + ", it's your turn");
        System.out.println("Your hand:");

        // I declare this as a variable because it is used too much to justify it not
        // being one.
        String parsedHand = player.parseHand(player.getHand());
        System.out.println(parsedHand);

        player.setHandValue(player.calcHandValue(true));
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

                System.out.println("Your hand is now:\n" + player.parseHand(player.getHand()));
                int handValue = player.getHandValue();

                // if over 21, you bust
                if (handValue > 21) {
                    System.out.println("Bust!");
                    return false;
                }
            }
        }

        return false;
    }

    public static void main(String[] args) {

        // the name of the winner
        String winner = "Placeholder, should never show";

        // boolean to determine if it's the dealer's turn
        boolean isDealerTurn = false;

        // sets whos turn it is to zero and that nobody has won
        int turn = 0;
        boolean hasWon = false;

        // creates dealer and table objects
        Dealer dealer = new Dealer();
        Blackjack table = new Blackjack();

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
                String dealParsedHand = dealer.parseHand(dealer.getHand());
                if (dealParsedHand.contains("Ace")
                        && (dealParsedHand.contains("King") || dealParsedHand.contains("Queen")
                                || dealParsedHand.contains("Jack"))) {
                    System.out.println("The Dealer got Blackjack!");
                    break;
                }

                System.out.println("The dealer's deck was: ");
                System.out.println(dealParsedHand);

                System.out.print("The dealer's hand had a value of ");
                int dealValue = dealer.getHandValue();
                System.out.println(dealValue);

                int highestSoFar = 0;
                String winPlayer = "";
                for (Player i : gamePlayers) {
                    if (i.getHandValue() > highestSoFar || i.getHandValue() < 22) {
                        highestSoFar = i.getHandValue();
                        winPlayer = i.getName();
                    }
                }
                if (highestSoFar > dealer.getHandValue()) {
                    winner = winPlayer;
                } else if (highestSoFar == dealer.getHandValue()) {
                    System.out.println("It was a tie between the dealer and " + winPlayer);
                    System.exit(0);
                } else {
                    winner = "The dealer";
                }
                break;

            } else {
                System.out.println("Dealer's visible card:");

                // prints the rank and suit of visible card
                System.out.println(dealer.getCardRank(dealer.getHand().get(0)) + " of " + dealer.getCardSuit(dealer.getHand().get(0)));
                hasWon = table.playTurn(gamePlayers[turn], dealer);
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
