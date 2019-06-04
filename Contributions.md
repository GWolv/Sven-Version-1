# Sven Version 1 Contributions

For my contributions I added a __clearScreen() method__ and __Inventory Quantities__.

The clearScreen() method deletes everything that is currently in the output box. The code for the method is this:

```public static void clearScreen() 
{  
 System.out.print("\033[H\033[2J");  
 System.out.flush();  
}  
```
The \0 is the escape sequence, while the rest is the command to reset the output box. After the output has been deleted, the flush method gets used. This basically gets rid of all the bytes that are being temporarily stored in the memory. It just causees the program to execute a little faster, since the memory gets emptied.

For my other contribution, I added inventory quantities. For this I had an array called **amount** that stored some hardwired numbers for the current items available.
```
public static int[] amount =
{
 1,
 1,
 0,
 0,
 0
};
```

I use this array in both methods to edit the quantity in the right index positions. The first method, which adds to the quantity, is displayed like this:
```
public static void addQuantity(InventoryList itemsForSale, String userMenuChoice, int quantity)
{
 ArrayList<String> currentInventory = itemsForSale.getItems();

 int indexPosition = currentInventory.indexOf(userMenuChoice);

 amount[indexPosition] = amount[indexPosition] + quantity;
}
```
The three parameters are the available items, the user's item choice, and the quantity that will be added. In the first line of the body, an array list is declared and initialized with the current available items with the use of the **getItems()** method. The second line of the body finds the index position of the requested item in the array list. The third line uses teh index position to edit the **amount** array by adding the chosen quantity to the right number in the array.

The second method removes the chosen amount of an item when the customer buys it:
```
public static void subtractQuantity(int userMenuChoice, int quantity)
{
 int indexPosition = userMenuChoice - 1;
 amount[indexPosition] = amount[indexPosition] - quantity;
}
```
There are only two parameters in this method: the user's item choice and the quantity that will be removed. The user's choice is an integer here because the items will be displayed as a numbered list and the customer chooses an item by selecting the number next to the item. Index positions start at 0 which is why 1 is subtracted from the customer's choice. on the second line in the body, the index position is used to edit the right number in the **amount** array where the chosen quantity is then subtracted.



