# Documentation File

## V2.1 (complete overhaul of the backend from 2.0)
### Changelog at the bottom


## What to know to make a game (overview)

* There are 3 objects that i'll be referencing a lot, 1 or more players created from the Player class, a dealer created from the Dealer class, and a table created from the Table class

* I declared a system.in scanner everyone uses in Cards. I was very careful to clean up after myself but if you see a scanner bug it could be that

* You don't actually *need* a dealer. For a game like blackjack, you need a dealer method, because the dealer needs to actually play while the table sets everything up, but both extend Cards

    * if your game is simple enough (for example War), the table inherits the default deal method from Cards, and since you're not dealing to a dealer, there's not much extra you need. You can just call table.deal() or table.dealAll()

* This will be useful to keep in mind for deciphering my methods; your hand is stored as a bunch of 7 bit binary numbers converted into integers (6 bits of data and a padding bit at the beginning), so a bunch of the parsing methods are related to that

## Playables and non-playables
* Playable objects can have hands and take a role in the game, and nonplayable objects cannot.
* Players and Dealers are both playable objects, whereas tables are unplayable
* Unplayable objects like tables can still do things, like behind the scenes or dealing, but don't possess a hand and are made for setup and management rather than playing an active role in the game
* Playable objects *can* be used to set things up, mainly dealer because it has access to some of the deck, but they lack the methods which abstract to do things automatically that come from Table among other places, and it cleans things up to have an object which just handles all the setting up.
* Playable objects have the variables:
    * hand, which serves as a hand, as an arraylist of integers
    * handValue as an optional way to track the value of a hand
* So far, the only playable objects are Players and Dealers
### Admins and nonadmins
* You can also sort objects into another 2 groups - admins and nonadmins
* The dealer and the table are both admins - they can deal cards, shuffle the deck, and look at the player list.
* The player can't do any of those
* Note - the dealer can't by default actually do any of these things, but dealer has a private class which has access to Cards, and a bunch of duplicate methods, so effectively it can.


### Playable object methods:

Raw cards are the cards straight out of the hand, no previous parsing. Hands are full of Integers, but for the most part, they're parsed as ints.

* getHand - returns the objects' hand
* getHandValue - returns the objects' handValue variable
* setHandValue - accepts an int to set the hands' value to
* calcHandvalue - returns the value of the entire hand combined as an int using getCardValue and a loop

* parseHand - takes a hand (should be stored in yourPlayer.hand) and returns a parsed string of the hand. It just shows something readable, and if you're comparing stuff about the cards, you should use getCardRank and getCardSuit, which i'm about to go over

* getCardValue - accepts the int value of the card to get the value of  and returns the cards' value as an integer

* getCardRank - accepts the int value of the card to check and returns the cards' rank as a string. This differs from cardValue because it returns as a string, but also, how the face cards are interpreted. Sending in a queen to getCardValue will return 10, but to getCardRank, will return "Queen". Useful for parsing hands into readable formats and deciding things specific on the card not the value.

* getCardSuit - accepts the int value of the card and returns the suit of the card as a string

* parseHand - returns the parsed hand variable of the object it was called on.

### Admin methods

* deal - deal is overloaded to either:
    * take an integer (number of cards to deal) and a player (target of the deal) and deals to them
    * just take an integer and return a int[] list of all of the cards it got
        * both of these delete the cards they got from the deck, so don't call it and not use it
* shuffle - shuffles the deck, resetting it. Make sure to clear all of the hands first
* dealAll - only the dealer has this. It deals to all players and the dealer. I'll probably add this to table in a future version.
* manDeal - (Manual Deal) accepts an int and a player, manually adds that int to the players hand, no checks or parses. This accepts any ints.

## Tables
* You should create the table from the class of the game you're making which should extend Table
* Tables can handle a lot of the setup and behind the scenes work
* Tables are not playable but can deal cards

### Table methods (setup and config)
Setup:
* playerPrompt - asks how many users will be playing, what their names are, creates their objects, and adds them to the list of players
* initPlayers - returns a permanent Player[] list of all of the players that were added with playerPrompt - you should use this instead of an arrayList for efficiency

Config:
* setAceValue - sets the value that aces are accepted at - some games are one and some games are max so I have a variable for that
* getAceValue - returns the int ace value
* setRoyalValues - lets you set the values of royals to whatever you want. For some reason, I can't have default values for a parameter in java, so you need to do all 3 every time.
* getRoyalValues - like it *exists*, but can't envision any reason someone would want to see what they are right now instead of changing them to what they want, so it's static for efficiency.

### Table methods (in place of dealer)
* Tables have the deal method and the shuffle method

### Players
* Unlike dealers, for Players, you have to pass a name variable into the construtor. This is handled in playerPrompt from Table for you if you want to use that.
* They're stored in a list in Cards, and is accessible by inherited classes
* Adding to this list is handled for you in playerPrompt


### Stuff about the way this works
* Cards.java serves as the deck of the game
* The shuffle method in board completely resets the deck, bringing back all 52 cards
* The deck starts empty and needs to be shuffled.
* The collection of all cards are stored in a native list as ints which have a calculated binary representation plus 64 as a padding bit.
    * For instance, 89 has a binary representation of 1011001. The first 1 is a padding bit so the parsing is mroe efficient, so the real number is 011001. The first 2 are the suit; Hearts are 11, Diamonds are 10, Spades are 01, Clubs are 00. The next 4 are a binary representation of the value, from 1-13. 1001 is 9, so 89 is the 9 of Diamonds. This makes checking for suits simpler, since all of one suit is between 2 numbers.
* Dealer has a private class, DealerHands, which extends Cards. The dealer is just an ordinary player, but it's hands can access the deck, like a real dealer.


## Changelog
### V2.1:
* Added a bunch of config methods for custom stuff
* Separated blackjack logic from bleeding into the classes
* Overhauled the entire backend to use integers instead of strings