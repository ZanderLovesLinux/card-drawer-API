package cardgameapi;

import java.util.InputMismatchException;

public class Table extends Cards {

    // MARK: Configs
    static int aceValue = 14;

    /**
     * This lets you set a custom value for aces.
     * 
     * @param valueToSet what number aces should be valued at
     */
    public void setAceValue(int valueToSet) {
        aceValue = valueToSet;
    }

    public int getAceValue() {
        return aceValue;
    }

    static int[] royalValues = { 11, 12, 13 };

    /**
     * this function sets the values of royal cards, which are stored in a list.
     * They're stored in a list of ints 3 long and their value corresponds to a
     * position, 0 for jack, 1 for queen, 2 for king
     * 
     * @param jackValue  the value jacks will be set to
     * @param queenValue the value queens will be set to
     * @param kingValue  the value kings will be set to
     */
    public void setRoyalValues(int jackValue, int queenValue, int kingValue) {
        royalValues[0] = jackValue;
        royalValues[1] = queenValue;
        royalValues[2] = kingValue;
    }

    public static int[] getRoyalValues() {
        return royalValues;
    }

    public static int playerCount = 0;

    public void playerPrompt() {
        while (true) {
            try {
                playerCount = 0;
                System.out.println("How many players?");
                playerCount = scanner.nextInt();
                // consumes leftover newline
                scanner.nextLine();
                if (playerCount > 0) {
                    break;
                }

                System.out.println("Please enter a positive number");
            } catch (InputMismatchException e) {
                System.out.println("Invalid input, must be a number");
                scanner.next();
            }
        }

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

    public Player[] initPlayers() {
        Player[] gamePlayers = new Player[getPlayers().size()];
        int i = 0;
        for (Player player : getPlayers()) {
            gamePlayers[i] = player;
            i++;
        }
        return gamePlayers;
    }
}