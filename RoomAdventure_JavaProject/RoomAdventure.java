import java.util.Scanner; // Import Scanner for reading a user import


public class RoomAdventure{ // Main class containing game logic
    
    // class Varibles
    private static Room currentRoom; // Current room for player
    private static String[] inventory = {null, null, null, null, null}; // Player inventory
    private static String status; // Message to display after each action
    
    
    // Constants
    final private static String DEFUALT_STATUS =
        "Sorry, I do not understand. Try {verb} {noun}. Valid verbs include 'go', 'look', and 'take'. Type 'end game' to end game. "; //Defualt error message

    private static void handleGo(String noun) { // Handles moving between rooms
        String[] exitDirections = currentRoom.getExitDirections(); // Get the available directions
        Room[] exitDestinations = currentRoom.getExitDestinations(); // Get rooms in those directions
        status = "I don't see that room."; // Defuealt if that room is not found
        for (int i = 0; i < exitDirections.length; i++) { // Loop through the directions
            if (noun.equals(exitDirections[i])) { // Check if the direction is valid
                currentRoom = exitDestinations[i]; // Move to the new room
                status = "Changed Room "; // Update Status
            }

        }

    }

    private static void handlelook(String noun) { // Handles looking at items
        String[] items = currentRoom.getItems(); // Get the items in the room
        String[] itemDescriptions = currentRoom.getItemDescriptions(); // Get the item descriptions
        status = "I don't see that item."; // Default if the item is not found
        for (int i = 0; i < items.length; i++) { // Loop through the items
            if (noun.equals(items[i])) { // Check if the item is valid
                status = itemDescriptions[i]; // Update status with description
            }
        }
    }

    private static void handleTake(String noun) { // Handles taking items
        String[] grabbables = currentRoom.getGrabbables(); // Items that can be taken
        status = "I cant take that item."; // Default if the item is not grabbable
        for (String item: grabbables){ // Loop through the grabbables
            if (noun.equals(item)) { // Check if the item is valid
                for (int j = 0; j < inventory.length; j++) { // Loop through the inventory
                    if (inventory[j] == null) { // Check for empty slot
                        inventory[j] = noun; // Add item to inventory
                        status = "Added it to your inventory."; // Update status
                        break; // Break out of the loop
                    }
                }
            }
        }
    }


    private static void setupGame() { //init game world
        Room room1 = new Room("Room 1"); // Create room 1
        Room room2 = new Room("Room 2"); // Create room 2
        Room room3 = new Room("Room 3"); // Create room 3
        Room room4 = new Room("Room 4"); // Create room 4

        // Room 1 Items
        Item chair = new Item("chair", "This is a chair", false, false, false);
        Item desk = new Item("desk", "This is a desk", false, false, false);
        Item key = new Item("key", "This is a key", false, false, true);
        // Room 2 Items
        Item fireplace = new Item(
            "fireplace",
            "This is a fireplace", 
            false, 
            false, 
            false);
        Item rug = new Item("rug", "This is a rug", false, false, false);
        Item coal = new Item("coal", "This is coal", false, false, true);
        // Room 3 Items
        Item bookshelves = new Item("bookshelves", "There is a book on one of the shelves", false, false, true);
        Item statue = new Item("statue", "This is a statue", false, false, false);
        Item desk2 = new Item("desk", "This is a desk", false, false, false);
        Item book = new Item(
        // Room 4 Items

        // ################################# Room 1 #################################

        String[] room1ExitDirections = {"east", "south"}; // Exit directions for room 1
        Room[] room1ExitDestinations = {room2, room4}; // Exit destinations for room 1
        String[] room1Items = {chair.getItemName(), desk.getItemName()}; // Items in room 1
        String[] room1ItemDescriptions = {
            chair.getItemDescription(), 
            desk.getItemDescription()
        }; // Item descriptions for room 1

        String[] room1Grabbables = {"key"}; // items in room 1 you can take
        room1.setExitDirections(room1ExitDirections); // Set exit directions for room 1
        room1.setExitDestinations(room1ExitDestinations); // Set exit destinations for room 1
        room1.setItems(room1Items); // Set items for room 1
        room1.setItemDescriptions(room1ItemDescriptions); // Set item descriptions for room 1
        room1.setGrabbables(room1Grabbables); // Set grabbables for room 1

        // ################################# Room 2 #################################

        String[] room2ExitDirections = {"west", "south"}; // Exit directions for room 2
        Room[] room2ExitDestinations = {room1, room3}; // Exit destinations for room 2
        String[] room2Items = {"fireplace", "rug"}; // Items in room 2
        String[] room2ItemDescriptions = {
            "Its on fire",
            "theres a lump of coal on the rug"
        }; // Item descriptions for room 2
        String[] room2Grabbables = {"coal"}; // items in room 2 you can take
        room2.setExitDirections(room2ExitDirections); // Set exit directions for room 2
        room2.setExitDestinations(room2ExitDestinations); // Set exit destinations for room 2
        room2.setItems(room2Items); // Set items for room 2
        room2.setItemDescriptions(room2ItemDescriptions); // Set item descriptions for room 2
        room2.setGrabbables(room2Grabbables); // Set grabbables for room 2

        // ################################# Room 3 #################################

        String[] room3ExitDirections = {"north", "west"}; // Exit directions for room 3
        Room[] room3ExitDestinations = {room2, room4}; // Exit destinations for room 3
        String[] room3Items = {"bookshelves", "statue", "desk"}; // Items in room 3
        String[] room3ItemDescriptions = {
            "They are empty. Go Figure.", 
            "There is nothing special about it.",
            "The statue is resting on it. So is a book."
        }; // Item descriptions for room 3

        String[] room3Grabbables = {"book"}; // items in room 3 you can take
        room3.setExitDirections(room3ExitDirections); // Set exit directions for room 3
        room3.setExitDestinations(room3ExitDestinations); // Set exit destinations for room 3
        room3.setItems(room3Items); // Set items for room 3
        room3.setItemDescriptions(room3ItemDescriptions); // Set item descriptions for room 3
        room3.setGrabbables(room3Grabbables); // Set grabbables for room 3

         // ################################# Room 4 #################################

        String[] room4ExitDirections = {"east", "north"}; // Exit directions for room 4
        Room[] room4ExitDestinations = {room3, room1}; // Exit destinations for room 4
        String[] room4Items = {"brew_rig", "chest"}; // Items in room 4
        String[] room4ItemDescriptions = {
            "Gourd is brewing some sort of oatmeal stout on the brewrig. A 6-pack is resting beside it.", 
            "It is made of a dark oak. It is locked. Maybe USE a key to open it."
        }; // Item descriptions for room 4

        String[] room4Grabbables = {"6-pack"}; // items in room 4 you can take
        room4.setExitDirections(room4ExitDirections); // Set exit directions for room 4
        room4.setExitDestinations(room4ExitDestinations); // Set exit destinations for room 4
        room4.setItems(room4Items); // Set items for room 4
        room4.setItemDescriptions(room4ItemDescriptions); // Set item descriptions for room 4
        room4.setGrabbables(room4Grabbables); // Set grabbables for room 4

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
                case "look": // if verb is look
                    handlelook(noun); // look at an item
                    break;
                case "take": // if verb is take
                    handleTake(noun); // take an item
                    break;
                case "end":  // If verb is end
                    System.out.println("Closing game...");
                    RUNNING = false;
                    break;
                default: // if verb is not recognized
                    status = DEFUALT_STATUS; // Set status to default error message
                
            }

            System.out.println(status); // Print the status message
    
        }



    }
}

class Room{ // Represents a game room
    private String name; // Room name
    private String[] exitDirections; // Directions you can go to exit  
    private Room[] exitDestinations; // Rooms reached by each direciton 
    private String[] items; // Items visible in each room 
    private String[] itemDescriptions; // Descriptions of items
    private String[] grabbables; // Items you can take


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

    public void setItems(String[] items){ // Setter for items
        this.items = items; // Set the items
    }

    public String[] getItems(){ // Getter for items
        return items; // Return the items
    }

    public void setItemDescriptions(String[] itemDescriptions){ // Setter for item descriptions
        this.itemDescriptions = itemDescriptions; // Set the item descriptions
    }

    public String[] getItemDescriptions(){ // Getter for item descriptions
        return itemDescriptions; // Return the item descriptions
    }

    public void setGrabbables(String[] grabbables){ // Setter for grabbables
        this.grabbables = grabbables; // Set the grabbables
    }

    public String[] getGrabbables(){ // Getter for grabbables
        return grabbables; // Return the grabbables
    }

// NOTE TO SELF: public void ...(type, varible){ // Setters
// NOTE TO SELF: public type get...(type, varible){ // Getters

    // @Override 
    public String toString(){ // Custom print for the room
        String result = "\nLocation: " + name; //Show room name
        result += "\nYou See: "; // Show what you see
        for (String item : items){ //Loop items
            result += item + " "; // apend each item
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

    // constructor
    public Item(String name, String description, boolean isEatable, boolean isDrinkable, boolean isGrabbable){
        this.name = name;
        this.description = description;
        this.isEatable = isEatable;
        this.isDrinkable = isDrinkable;
        this.isGrabbable = isGrabbable;
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

}
