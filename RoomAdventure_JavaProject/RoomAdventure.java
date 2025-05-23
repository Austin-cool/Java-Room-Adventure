import java.util.Scanner; // Import Scanner for reading a user import


public class RoomAdventure{ // Main class containing game logic
    
    // class Varibles
    private static Room currentRoom; // Current room for player
    private static String[] inventory = {null, null, null, null, null, null, null, null, null}; // Player inventory
    private static String status; // Message to display after each action
    private static Room room4;
    
    // Constants
    final private static String DEFUALT_STATUS =
        "Sorry, I do not understand. Try {verb} {noun}. Valid verbs include 'go', 'look', and 'take'. Type 'end game' to end game. "; //Defualt error message

    // END GAME feature 
    private static void endGame() {
        System.out.println("END GAME!");
        System.exit(0); // Terminates the program immediately
    }

    //-Tony Handle the use command-STOPPING POINT
    private static void handleUse(String noun) {
    for (int i = 0; i < inventory.length; i++) {
        if (inventory[i] != null && inventory[i].equals(noun)) { // Check inventory
            for (Item roomItem : currentRoom.getItems()) { // Loop through items in the room
                if (roomItem.getItemName().equals("chest") && noun.equals("key")) {
                    // If the user uses a key on a chest, add a potion to their inventory
                    inventory[i] = "potion"; // Remove key and add potion
                    for (int j = 0; j < inventory.length; j++){
                        if (inventory[j] == null) {
                            inventory[j] = "mushroom"; // added mushroom to inventory
                            break;
                        }
                    }
                    currentRoom.removeItem(roomItem); // remove chest from room
                    
                    status = "You unlocked the chest, found a potion and mushroom!";
                    return;
                } else if (roomItem.getItemName().equals("fireplace") && noun.equals("coal")) {
                    // If the user uses coal in a fireplace, pass for now
                    status = "You used the coal in the fireplace.";
                    inventory[i] = null;
                    System.out.println("You fed the meager flame. YOU FOOL! You gave the flame exactly what it wanted. The flame grows faster than your sense of terror and envelopes the entire room. The entire building is engulfed, and you perish.");
                    endGame();
                    return;
                } else if (roomItem.getItemName().equals("table") && noun.equals("button")) {
                    // If the user presses the button
                    status = "You pressed the button";
                    inventory[i] = null;
                    System.out.println("You escaped.");
                    endGame();
                    return;
                }
            }
            status = "You can't use that here.";
            return;
        }
    }
    
    status = "You don't have that item.";
}

    //Tony- Handles the eat command    
    private static void handleEat(String noun) {  // Handles eatable item within grabbables
        // Checks if item in inventory is edible
        for (int i = 0; i < inventory.length; i++) { // loop through the inventory
            if (inventory[i] != null && inventory[i].equals(noun)) { //Check if in inventory
                Item itemToEat = null;
                for (Item roomItem: currentRoom.getItems()) {
                    if (roomItem.getItemName().equals(noun)) {
                        itemToEat = roomItem;
                        break; // Break out of the loop
                    }
                }

                if (itemToEat != null && itemToEat.getIsEatable()) {
                    status = "You ate the " + noun + "."; // Update status
                    inventory[i] = null; // Remove item from inventory
                    currentRoom.removeItem(itemToEat); // Remove item from room
                } else {
                    status = "You can't eat that."; //Update Status
                }
            break;
            }
        }
    }

    //Tony- Handles the drink command
    private static void handleDrink(String noun) {  // Handles eatable item within grabbables
        // Checks if item in inventory is drinkable
        for (int i = 0; i < inventory.length; i++) { // loop through the inventory
            if (inventory[i] != null && inventory[i].equals(noun)) { //Check if in inventory
                Item itemToDrink = null;
                for (Item roomItem: currentRoom.getItems()) {
                    if (roomItem.getItemName().equals(noun)) {
                        itemToDrink = roomItem;
                        break; // Break out of the loop
                    }
                }

                if (itemToDrink != null && itemToDrink.getIsDrinkable()) {
                    status = "You ate the " + noun + "."; // Update status
                    inventory[i] = null; // Remove item from inventory
                    currentRoom.removeItem(itemToDrink); // Remove item from room
                } else {
                    status = "You can't eat that."; //Update Status
                }
            break;
            }
        }
    }        
      
        


    private static void handleGo(String noun) { // Handles moving between rooms
        String[] exitDirections = currentRoom.getExitDirections(); // Get the available directions
        Room[] exitDestinations = currentRoom.getExitDestinations(); // Get rooms in those directions
        status = "I don't see that room."; // Defuealt if that room is not found
        for (int i = 0; i < exitDirections.length; i++) { // Loop through the directions
            if (noun.equals("up") && (!room4.hasItem("chest"))) {
                if (noun.equals(exitDirections[i])) { // Check if the direction is valid
                    currentRoom = exitDestinations[i]; // move to the new room
                    status = "Changed Room "; // Update Status
                }
            }
            else if (!noun.equals("up") && noun.equals(exitDirections[i])) { // Check if the direction is valid
                currentRoom = exitDestinations[i]; // Move to the new room
                status = "Changed Room "; // Update Status
            }
            

        }

    }

    private static void handlelook(String noun) { // Handles looking at items
        Item[] items = currentRoom.getItems(); // Get the items in the room
        status = "I don't see that item."; // Default if the item is not found
        for (Item item : items) { // Loop through the items
            if (noun.equals(item.getItemName())) { // Check if the item is valid
                status = item.getItemDescription();// Update status with description
                break; 
            }
        }
    }

    private static void handleTake(String noun) { // Handles taking items
        Item[] items = currentRoom.getItems(); // Items that can be taken
        for (Item item : items){ // Loop through the grabbables
            if (noun.equals(item.getItemName()) && item.getIsGrabbable()) { // Check if the item is valid
                for (int j = 0; j < inventory.length; j++) { // Loop through the inventory
                    if (inventory[j] == null) { // Check for empty slot
                        inventory[j] = noun; // Add item to inventory
                        status = "Added it to your inventory."; // Update status
                        // Remove item from the room
                        currentRoom.removeItem(item); // Remove item from room
                        break; // Break out of the loop
                    }
                }
            }else {
                status = "I cant take that item."; // Default if the item is not grabbable
            }
        }
    }

    private static void setupGame() { //init game world
        Room room1 = new Room("Room 1"); // Create room 1
        Room room2 = new Room("Room 2"); // Create room 2
        Room room3 = new Room("Room 3"); // Create room 3
        room4 = new Room("Room 4"); // Create room 4
        Room room5 = new Room("Room 5"); // Create room 5
        // Room for hidden items

        // Items not seen yet
        // Must unlock chest to obtain potion
        Item potion = new Item(
            "potion", 
            "This is a magic potion", 
            false, 
            true, 
            true, 
            false,
            false
        );

        // Must look at desk to see mushroom
        Item mushroom = new Item(
            "mushroom", 
            "This is a mushroom", 
            true, 
            false, 
            true, 
            false,
            false
        );

        // Room 1 Items
        Item apple = new Item(
            "apple",
            "this is an apple",
            true, 
            false, 
            true,
            false,
            false
        );
        Item chair = new Item(
            "chair", 
            "This is a wooden chair. It looks comfortable.", 
            false, 
            false, 
            false, 
            false,
            false
        );
        Item desk = new Item(
            "desk",
            "This is a hardwood desk. It looks like lots of paperwork was done on it.", 
            false, 
            false, 
            false, 
            false,
            false
        );
        Item key = new Item(
            "key", 
            "The key looks complex and is made from copper. I wonder what this could possibly open.", 
            false, 
            false, 
            true, 
            true,
            false
        );
        Item plant = new Item(
            "plant",
            "There is a small cactus on the desk. You're allergic to the prick of a cactus needle, so you might not want tograb this. Your call, though.",
            false,
            false,
            true,
            false,
            false

        );
        Item painting = new Item(
            "painting", 
            "This is an old one. Probably from the Renaissance. You feel that you're not rich enough to even go near it.", 
             false, 
             false,
             false,
             false,
             false
        );
        // Room 2 Items
        Item fireplace = new Item(
            "fireplace",
            "Its on fire. The meager flame hungers.", 
            false, 
            false, 
            false, 
            false,
            false
        );
        Item rug = new Item(
            "rug", 
            "theres a lump of coal on the rug", 
            false, 
            false, 
            false, 
            false,
            false
        );
        Item coal = new Item(
            "coal", 
            "You hear the faint call of the meager flame beckoning you.", 
            false, 
            false, 
            true, 
            true,
            false
        );
        Item shovel = new Item(
            "shovel", 
            "You have no use for this.", 
            false, 
            false,
            false,
            false,
            false
        );
        Item couch = new Item(
            "couch", 
            "It looks comfy.", 
            false, 
            false,
            false,
            false,
            false 
            );
        // Room 3 Items

        Item bookshelves = new Item(
            "bookshelves", 
            "There is nothing on it. Go figure.", 
            false, 
            false, 
            true, 
            false,
            false
        );
        Item statue = new Item(
            "statue",
            "The statue is one of a knight. It brandishes its sword as if prepared to fight.", 
            false, 
            false, 
            false, 
            false,
            false
        );
        Item table = new Item(
            "table", 
            "It's made of oak. There is a book on it", 
            false, 
            false,
            false,
            false,
            false      
        );
        Item book = new Item(
            "book", 
            "You read the title. It says 'If you are reading this, you are a nerd'.", 
            false, 
            false, 
            true, 
            false,
            false
        );
        Item iron_maiden = new Item(
            "iron_maiden", 
            "Don't even try", 
            false, 
            false,
            false,
            false,
            false 
            );
        // Room 4 Items
        Item brew_rig = new Item(
            "brew rig",
            "Gourd is brewing some sort of oatmeal stout on the brewrig. A 6-pack is resting beside it.", 
            false, 
            false, 
            false, 
            false,
            false
            );
        Item chest = new Item(
            "chest",
            "It is made of a dark oak. It is locked. Maybe USE a key to open it.",
            false,
            false,
            false,
            false,
            true
        );
        Item six_pack = new Item(
            "6_pack",
            "It's resting next to a brew rig. A little drinking never hurt anyone.",
            false,
            true,
            true,
            false,
            false
        );
        Item empty_cans = new Item(
            "empty_cans", 
            "It looks like some lazybones was having a good time with another 6-pack.", 
            false, 
            false,
            false,
            false,
            false 
            );
        Item broom = new Item(
            "broom", 
            "This is a broom. This isn't your house, so you don't need to clean.", 
            false, 
            false,
            true,
            false,
            false
            );
        // Room 5 Items
        Item stairs = new Item(
            "stairs", 
            "The stairs lead to a door, but you have a feeling that you can't just freely go in there", 
            false, 
            false,
            false,
            false,
            false
        );
        Item button = new Item(
            "button", 
            "There is a button laying on a table in the center of the room. You can take it.", 
            false, 
            false,
            true,
            true,
            false
        );
        Item table2 = new Item(
            "table", 
            "It has a button on it. I wonder what it does", 
            false, 
            false,
            false,
            false,
            false  
        );
        // ################################# Room 1 #################################

        String[] room1ExitDirections = {"east", "south", "up"}; // Exit directions for room 1
        Room[] room1ExitDestinations = {room2, room4, room5}; // Exit destinations for room 1
        room1.setExitDirections(room1ExitDirections); // Set exit directions for room 1
        room1.setExitDestinations(room1ExitDestinations); // Set exit destinations for room 1
        room1.setItems(new Item[]{chair, desk, key, apple, plant, painting, stairs}); // Set items for room 1

        // ################################# Room 2 #################################

        String[] room2ExitDirections = {"west", "south", "up"}; // Exit directions for room 2
        Room[] room2ExitDestinations = {room1, room3, room5}; // Exit destinations for room 2
        room2.setExitDirections(room2ExitDirections); // Set exit directions for room 2
        room2.setExitDestinations(room2ExitDestinations); // Set exit destinations for room 2
        room2.setItems(new Item[]{fireplace, rug, coal, stairs}); // Set items for room 2

        // ################################# Room 3 #################################

        String[] room3ExitDirections = {"north", "west", "up"}; // Exit directions for room 3
        Room[] room3ExitDestinations = {room2, room4, room5}; // Exit destinations for room 3
        room3.setExitDirections(room3ExitDirections); // Set exit directions for room 3
        room3.setExitDestinations(room3ExitDestinations); // Set exit destinations for room 3
        room3.setItems(new Item[]{bookshelves, statue, table, book, shovel, couch, iron_maiden, stairs}); // Set items for room 3

         // ################################# Room 4 #################################

        String[] room4ExitDirections = {"east", "north", "up"}; // Exit directions for room 4
        Room[] room4ExitDestinations = {room3, room1, room5}; // Exit destinations for room 4
        room4.setExitDirections(room4ExitDirections); // Set exit directions for room 4
        room4.setExitDestinations(room4ExitDestinations); // Set exit destinations for room 4
        room4.setItems(new Item[]{brew_rig, chest, six_pack, empty_cans, broom, stairs}); // Set items for room 4

        // ################################# Room 5 #################################

        String[] room5ExitDirections = {"room1", "room2", "room3", "room4"}; // Exit directions for room 5
        Room[] room5ExitDestinations = {room1, room2, room3, room4}; // Exit destinations for room 5
        room5.setExitDirections(room5ExitDirections); // Set exit directions for room 5
        room5.setExitDestinations(room5ExitDestinations); // Set exit destinations for room 5
        room5.setItems(new Item[]{table2, button}); // Set items for room 5

        // Set the current room to room 1
        currentRoom = room1; // Set the current room to room 1
    }


    // @SuppressWarnings("java:52189") // Suppress warning for Scanner
    public static void main(String[] args) { //Entry point of the game
        setupGame(); // Setup the game

        boolean RUNNING = true;
        
        while (RUNNING) { // Game loop, runs until program is terminated
            System.out.print(currentRoom.toString()); // Print the current room
            System.out.print("inventory: "); // Print the inventory

            for (int i = 0; i < inventory.length; i++) { // Loop through the inventory slots
              System.out.print(inventory[i] + " "); // Print each item in the inventory

            }
            
            System.out.println("\nWhat would you like to do?"); // Prompt for user input

            Scanner s = new Scanner(System.in); // Create a new Scanner object
            String input = s.nextLine(); // Read user input
            String[] words = input.split(" "); // Split the input into words

            if (words.length != 2) { // Check for proper two-word command
                status = DEFUALT_STATUS; // Set status to default error message
                continue; // Skip to the next iteration of the loop    
            }
            
            String verb = words[0]; // Get the verb from the input
            String noun = words[1]; // Get the noun from the input

            switch (verb) { // Decide which aciton to take
                case "go": // if verb is go
                    handleGo(noun); // go to another room
                    break;
                case "walk": // if verb is walk
                    handleGo(noun); // go to another room
                    break;
                case "run": // if verb is run
                    handleGo(noun); // go to another room
                    break;
                case "look": // if verb is look
                    handlelook(noun); // look at an item
                    break;
                case "stare": // if verb is look
                    handlelook(noun); // look at an stare
                    break;
                case "analyze": // if verb is analyze
                    handlelook(noun); // look at an item
                    break;
                case "take": // if verb is take
                    handleTake(noun); // take an item
                    break;
                case "steal": // if verb is steal
                    handleTake(noun); // take an item
                    break;
                case "grab": // if verb is steal
                    handleTake(noun); // take an item
                    break;
                case "end":  // If verb is end
                    status = "";
                    System.out.println("Closing game...");
                    s.close();
                    RUNNING = false;
                    break;
                case "leave":  // If verb is leave
                    status = "";
                    s.close();
                    System.out.println("Closing game...");
                    RUNNING = false;
                    break;
                case "close":  // If verb is close
                    status = "";
                    s.close();
                    System.out.println("Closing game...");
                    RUNNING = false;
                    break;
                case "eat": 
                    handleEat(noun); // eat an item
                    break;
                case "drink":
                    handleDrink(noun); // drink an item
                    break;
                case "use": // if verb is use
                    handleUse(noun); // use an item
                    break;
                default: // if verb is not recognized
                    status = DEFUALT_STATUS; // Set status to default error message
                
            }

            System.out.println(status); // Print the status message
    
        }



    }
}

class Room{ // Represents a game room
    public String name; // Room name
    private String[] exitDirections; // Directions you can go to exit  
    public Room[] exitDestinations; // Rooms reached by each direciton 
    private Item[] items; // Items visible in each room 



    public Room(String name) { // Constructor for room
        this.name = name; // Set the rooms name
    }


    public void setExitDestinations(Room[] exitDestinations){ // Setter for exits
        this.exitDestinations = exitDestinations; // Set the exit destinations
    }

    public Room[] getExitDestinations(){ // Getter for exits
        return exitDestinations; // Return the exit destinations
    }

    public void setExitDirections(String[] exitDestinations){ //Setter for exits
        this.exitDirections = exitDestinations; // Set the exit directions
    }

    public String[] getExitDirections(){ // Getter for exit directions
        return exitDirections; // Return the exit directions
    }

    public void setItems(Item[] items){ // Setter for items
        this.items = items; // Set the items
    }

    public Item[] getItems(){ // Getter for items
        return items; // Return the items
    }

    // check for item in a room
    public boolean hasItem(String itemName) {
        for (Item item : items) {
            if (item.getItemName().equalsIgnoreCase(itemName)) {
                return true;
            }
        }
        return false;
    }

    // Create remove item method from the current room
    public String removeItem(Item item) { // Remove item from the room 
        Item[] items = getItems(); // Get the items in the room
        Item[] newItems = new Item[items.length - 1]; //Create a new array for the item in the room
        int index = 0; // Index for new items
        boolean removed = false; // Flag for item removed
        for (Item i : items) { // Loop through the items
            if (i != item) { // Check if the item is not the one to be removed
                newItems[index] = i; // Add the item to the new array
                index++; // Increment the index unril all items are added
            } else {
                removed = true;
            }
        }
        this.items = newItems; // Set the items to the new array
        if (removed) {
            return item.getItemName() + " removed from the room."; 
        } else {
            return "Item not found in the room."; 
        }
    }


     public String toString(){ // Custom print for the room
        String result = "\nLocation: " + name; //Show room name
        result += "\nYou See: "; // Show what you see
        for (Item item : items){ //Loop items
            result += item.getItemName() + " "; // apend each item
            }
        result += "\nExits: "; // List exits
        for (String direction : exitDirections) { // Loop exits
            result += direction + " "; // Append each direction
        }
        return result + "\n"; //Return the full result
    }
}



class Item {
    private String name;
    private String description;
    private boolean isEatable;
    private boolean isDrinkable;
    private boolean isGrabbable;
    private boolean isUsable;
    private boolean requiresKey;

    // constructor
    public Item(String name, String description, boolean isEatable, boolean isDrinkable, boolean isGrabbable, boolean isUsable, boolean requiresKey) {
        this.name = name;
        this.description = description;
        this.isEatable = isEatable;
        this.isDrinkable = isDrinkable;
        this.isGrabbable = isGrabbable;
        this.isUsable = false; // Default to false
        this.requiresKey = false; // Default to false
        
    }


    // getters and setters
     public String getItemName(){ 
        return name; 
    }

    public void setItemName(String name){ 
        this.name = name; 
    }

    public String getItemDescription(){ 
        return description;
    }

    public void setItemDescriptions(String description){ 
        this.description = description; 
    }

    public boolean getIsEatable(){
        return isEatable;
    }

    public void setIsEatable(boolean isEatable){
        this.isEatable = isEatable;
    }

    public boolean getIsDrinkable(){
        return isDrinkable;
    }

    public void setIsDrinkable(boolean isDrinkable){
        this.isDrinkable = isDrinkable;
    }

    public boolean getIsGrabbable(){ 
        return isGrabbable; 
    }

    public void setIsGrabbable(boolean isGrabbable){ 
        this.isGrabbable = isGrabbable; 
    }

    public boolean getIsUsable(){ 
        return isUsable; 
    }
    public void setIsUsable(boolean isUsable){ 
        this.isUsable = isUsable; 
    }
    public boolean getRequiresKey(){ 
        return requiresKey; 
    }
    public void setRequiresKey(boolean requiresKey){ 
        this.requiresKey = requiresKey; 
    }

}