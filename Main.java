// imported libraries

import java.util.Scanner;   // keyboard & file inputs & outputs
import java.io.File;        // connecting to external files
import java.io.IOException; // handle unexpected input/output runtime errors
import java.io.FileWriter;  // writing data to external file

class Main 
{
  // constants

  static final String NAME = "Sven";    // name of program  
  static final int EXIT_CHOICE = 3;     // menu choice to exit program
  static final String ADMIN_PASSWORD = "applesauce"; // Password for Admin Mode

  // external data file containing list of items sold in vending machine   
  static final String INVENTORY_LIST_FILENAME = "ItemsForSale.txt";

  public static void main(String[] args)
  {
    // local variables

    // list of items for sale
    InventoryList itemsForSale = new InventoryList();

    // to obtain human's input  
    Scanner keyboard = new Scanner(System.in);          
  
    String userMenuChoice = "";   // human inputted menu choice
    String newItem = "";          // new item added to inventory
    String password = " ";        // user inputted password

    // customer selection from menu list of items
    int customerSelection = 0;    

    boolean updateSuccess = false;  // flag to indicate if new item added or not

    //////////////////////  read list of items for sale 
    //////////////////////  from external data file
    try
    {
      Scanner externalFile = new Scanner(new File(INVENTORY_LIST_FILENAME));
      
      while (externalFile.hasNext())
      {
        String nextItem = externalFile.nextLine();
        itemsForSale.addItem(nextItem);
      }
      externalFile.close();

    }
    catch (IOException inputOutputError)    // if error occurs trying to read data to file
    {
      System.err.println(inputOutputError); // directing system error to error log file
    }

    ////////////////////// setting up the screen
    clearScreen();                    // hides unneeded info at the top
    System.out.println("\n\n");       // blank lines to space output

    System.out.println("Hello, I am the world's first smart vending machine. My name is " + NAME + "!");

    
    System.out.println("\n\n");       // blank lines to space output
    
    ////////////////////// interact with customer

    // loop until human inputs the choice to exit the menu
    // TODO - find a better way to convert an int (EXIT_CHOICE) to a String
    while (!userMenuChoice.equals(EXIT_CHOICE + ""))
    {
      displayMenu();

      System.out.print("Choice: ");
      // trim leading & trailing spaces
      // TODO - allow user to input lowercase letters
      userMenuChoice = keyboard.nextLine().trim();  

      ////////////////////// Admin Mode
      // TODO - create separate programs for customers and admins
      if (userMenuChoice.equals("1") || userMenuChoice.equals("A") || userMenuChoice.equals("a"))
      {
        System.out.println("Please enter the password: ");
        password = keyboard.nextLine();

        if (password.equals(ADMIN_PASSWORD))
        {

          System.out.println("IN ADMIN MODE.....");
        
          System.out.print("What is the new item to add to the machine's inventory? ");
          newItem = keyboard.nextLine();    // obtain user's input via keyboard
        
          updateSuccess = false;
        
          if (!itemsForSale.addItem(newItem))
          {
            System.out.println("The update failed. Perhaps the item was already in the list.");    
          }
          else
          {
          
            try
            { 
              // add the new item to the end of the file
              // connect to external file, true specifies append mode
              FileWriter externalFile = new FileWriter(INVENTORY_LIST_FILENAME, true);  
              externalFile.write("\n" + newItem); // new line first to preserve format of existing file
              System.out.println(newItem);
              externalFile.flush();                      
              externalFile.close(); 

              updateSuccess = true;   
              System.out.println("Your updated inventory list is: ");
              itemsForSale.displayList();                                 
            }                                                           
            catch (IOException ioe) // if error occurs trying to write data to file
            {
              System.err.println(ioe);
              System.out.println("The update failed due to an error working with an external data file.");
            }

          } // end of else statement
        } // end of password if statement
      }  // end of Admin choice if statement
      
      ////////////////////// Customer Mode
      else if (userMenuChoice.equals("2") || userMenuChoice.equals("C") || userMenuChoice.equals("c"))
      {
        System.out.println("IN CUSTOMER MODE.....");

        // display the list of items as a numbered list
        for (int i = 0; i < itemsForSale.numberOfItems(); i++)
        {
          System.out.println((i + 1) + ". " + itemsForSale.selectItem(i));
        }

        System.out.println("What would you like to purchase? ");
        customerSelection = keyboard.nextInt();

        System.out.println("DEBUGGING: the customer input is #" + customerSelection);
        itemsForSale.removeItem("avocados");


        // TODO save / write the current most upated list of items (one by one) 
        // back to the external file
        // close the file


        try
        { 
          FileWriter externalFile = new FileWriter(INVENTORY_LIST_FILENAME, false);  
          // false specifies write mode (thanks KP)

          for (int i = 0; i < itemsForSale.numberOfItems(); i++)
          {
            externalFile.write(itemsForSale.selectItem(i) + "\n");
          }

          System.out.println("The updated list is: ");
          itemsForSale.displayList();      

          externalFile.flush();                      
          externalFile.close();                                    
        }                                                           
        catch (IOException ioe) // if error occurs trying to write data to file
        {
          System.err.println(ioe);
        }

      } // end of customer choice if statement
    } // end of while loop

    System.out.println("Goodbye");

  } // end of main method

  /////////////////////// static functions

  // display menu
  public static void displayMenu()
  {  
    System.out.println("\u001B[31m 1. (A)dmin Mode");
    System.out.println("\u001B[34m 2. (C)ustomer Mode");
    System.out.println("\u001B[35m " + EXIT_CHOICE + ". Exit");
    System.out.print("\u001B[0m");
  }

  // deletes everything in the output box
  public static void clearScreen() 
   {  
    System.out.print("\033[H\033[2J");  
    System.out.flush();  
   }  

} // end of Main class
