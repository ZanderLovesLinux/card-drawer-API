# Documentation File

## What to know to make a game (overview)

* Your game file should extend CardGame

* There are 3 objects that i'll be referencing a lot, 1 or more players created from the Player class, a dealer created from the Dealer class, and a table created from the class of the game you're making

* I declared a system.in scanner everyone uses in Cards, if you want to use a system.in scanner, use the one called "scanner". PLEASE. If I find a better solution I'll push it.

* You don't actually need a dealer. For a game like blackjack, you need a dealer method, because the dealer needs to actually play while the table sets everything up (the table is an object of the class of the game, but your actual game should inherit a bunch of methods from CardGame), but both extend Cards

    * if your game is simple enough (for example War), the table inherits the default deal method from Cards, and since you're not dealing to a dealer, there's not much extra you need. You can just call table.deal()

* This will be useful to keep in mind for deciphering my methods; your hand is stored as a bunch of 7 bit binary numbers converted into integers (6 bits of data and a padding bit at the beginning), so a bunch of the parsing methods are related to that

## Playables and non-playables
* Playable objects can have hands and take a role in the game, and nonplayable objects cannot.
* Players and Dealers are both playable objects, whereas tables are unplayable
* Unplayable objects like tables can still do things, like behind the scenes or dealing, but don't possess a hand
* Playable objects *can* be used to set things up, mainly dealer because it has access to the deck, but they lack the methods which abstract to do things automatically that come from CardGame among other places, and it cleans things up to have an object which just handles all the setting up.
* So far, the only playable objects are Players and Dealers
### Admins and nonadmins
* You can also sort objects into another 2 groups - admins and nonadmins
* The dealer and the table are both admins - they can deal cards, shuffle the deck, and look at the player list.
* The player can't do any of those

### Playable object methods:

I'll refer to an ace check boolean a lot. An ace check boolean means there's an option to either have 2 options for the value of your ace or not. True will ask for which value you want your ace to have and false assumes a value

Raw cards are the cards straight out of the hand, no previous parsing. They're stored as Strings because ArrayLists don't like ints.

* getHand - returns the objects' hand
* getHandValue - returns the objects' handValue variable
* setHandValue - accepts an int to set the hands' value to
* calcHandvalue - accepts a boolean whether to check for aces and returns the value of the entire hand combined as an int using getCardValue and a loop

* parseHand - takes a hand (should be stored in yourPlayer.hand) and returns a parsed string of the hand. It just shows something readable, and if you're comparing stuff about the cards, you should use getCardRank and getCardSuit, which i'm about to go over

* getCardValue - accepts the string of the card to get the value of and an ace check boolean, and returns the cards' value as an integer

* getCardRank - accepts the string of the raw card to check and returns the cards' rank as a string. This differs from cardValue because it returns as a string, but also, how the face cards are interpreted. Sending in a queen to getCardValue will return 10, but to getCardRank, will return "Queen". Useful for parsing hands into readable formats and deciding things specific on the card not the value.

* getCardSuit - accepts the raw card as a string and returns the suit of the card as a string

* parseHand - accepts an ArrayList of strings (a hand) to parse and returns the completely parsed hand in a readable format as a string

### Admin methods

* deal - deal is overloaded to either:
    * take an integer (number of cards to deal) and a player (target of the deal) and deals to them
    * just take an integer and return a String[] list of all of the cards it got
    * both of these delete the cards they got from the deck, so don't dispose of one
* shuffle - shuffles the deck, resetting it. Make sure to clear all of the hands first
* dealAll - only the dealer has this. It deals to all players and the dealer. I'll probably add this to table in a future version.

### Players
* You have to pass a name variable into the construtor, but this is handled in playerPrompt
### Player variables
* hand - an arraylist of strings, should look like a bunch of numbers
* name - defined in a constructor
* handValue - optional; can be used as the value of your cards

## Tables
* You should create the table from the class of the game you're making which should extend CardGame
* Tables can handle a lot of the setup and behind the scenes work
* Tables are not playable but can deal cards

### Table methods (setup)
* playerPrompt - asks how many users will be playing, what their names are, creates their objects, and adds them to the list of players
* initPlayers - returns a permanent Player[] list of all of the players that were added with playerPrompt - you should use this instead of an arrayList for efficiency

### Table methods (in place of dealer)
* Tables have the deal method and the shuffle method


### Stuff about the way this works
* Cards.java serves as the deck of the game
* The shuffle method in board completely resets the deck, bringing back all 52 cards
* The deck starts empty and needs to be shuffled.
* Cards are stored in a native list as integers of a binary representation plus 64 as an offset
    * For instance, 89 has a binary representation of 1011001. The first 1 is a padding bit so the parsing is mroe efficient, so the real number is 011001. The first 2 are the suit; Hearts are 11, Diamonds are 10, Spades are 01, Clubs are 00. The next 4 are a binary representation of the value, from 1-13. 1001 is 9, so 89 is the 9 of Diamonds. This makes checking for suits simpler, since all of one suit is between 2 numbers.
* Dealer has a private class, DealerHands, which extends Cards. The dealer is just an ordinary player, but it's hands can access the deck, like a real dealer.