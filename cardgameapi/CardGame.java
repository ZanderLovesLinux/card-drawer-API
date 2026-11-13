package cardgameapi;

import java.util.InputMismatchException;

public abstract class CardGame extends Cards {
    protected static int playerCount = 0;

    protected void playerPrompt() {
        while (true) {
            try {
                System.out.println("How many players?");
                playerCount = scanner.nextInt();
                if (playerCount > 0) {
                    break;
                }

                System.out.println("Please enter a positive number");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, must be a number");
                scanner.next();
            }
        }

        // consumes the leftover \n from nextInt. Why is user input like this?
        scanner.nextLine();
        for (int i = 0; i < playerCount; i++) {
            String playerName = "";

            while (playerName.length() < 1) {
                System.out.println("What would you like the name to be for player " + Integer.toString(i + 1));
                playerName = scanner.nextLine();
            }

            Player player = new Player(playerName);
            addPlayer(player);
        }
    }

    protected Player[] initPlayers() {
        Player[] gamePlayers = new Player[getPlayers().size()];
        int i = 0;
        for (Player player : getPlayers()) {
            gamePlayers[i] = player;
            i++;
        }
        return gamePlayers;
    }
}