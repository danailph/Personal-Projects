/* ***************************************
AUTHOR: Danail Todorov
Date: 07.10.2019

 This program simulates alien pets that
 the player must look after.
****************************************/

import java.util.Scanner; // Needed to make Scanner available
import java.util.Random; // Needed to generate a random integer
import java.io.*; // Needed for file I/O








class miniProject
{
  public static void main(String[] param) throws IOException, InterruptedException
  {
    clearConsol();
    printInstructions();

    Alien[] aliens;
    int round;

    int input = inputInt("Please select an option:\n1. Start a new game\n2. Load last game");
    input = validateInput(input, 1, 2, "Please select an option:\n1. Start a new game\n2. Load last game");
    if (input == 1)
      {aliens = newGame(); round = 1;}
    else
      {aliens = loadGame(); round = loadRound();}


    sort(aliens);

    clearConsol();
    while (stillAlive(aliens))
    {


      playRound(aliens, round);
      reduceHealth(aliens);
      round++;
      checkForDeaths(aliens, round);
    }

    printExitMessage(aliens, round);
    System.exit(0);
  } // END main











  public static void printExitMessage(Alien[] aliens, int round) throws IOException
  {
    save(aliens, round);
    clearConsol();
    print("All your aliens have died.");
    print("You survived " + round + " days.");
  }

  public static void clearConsol()
  {
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void sort(Alien[] aliens)
  {
    Alien temp;
    for (int i=0; i<aliens.length; i++)
    {
      for (int j=i+1; j<aliens.length; j++)
      {
        if (getName(aliens[j]).compareTo(getName(aliens[i])) < 0)
        {
          temp = aliens[i];
          aliens[i] = aliens[j];
          aliens[j] = temp;
        }
      }
    }
  }

  //
  public static void checkForDeaths(Alien[] aliens, int round)
  {
    for (int i=0; i<aliens.length; i++)
    {
      if ( getHunger(aliens[i]) <= 0 || getThirst(aliens[i]) <= 0)
      { aliens[i] = setAlive(aliens[i], false);
        aliens[i] = setAge(aliens[i], round); }
    }
  }

  // This method check if there are still alive pets to continue the game
  public static boolean stillAlive(Alien[] aliens)
  {
    boolean c = false;
    for (int i=0; i<aliens.length; i++)
    {
      if (getAlive(aliens[i]))
      { c = true; }
    }
    return c;
  } // END stillAlive

  // This method facilitates a whole round
  public static void playRound(Alien[] aliens, int round) throws IOException
  {
    printOptions(aliens, round);
    int input = inputInt("Choose an option:");
    input = validateInput(input, 1, aliens.length+1, "Choose an option:");
    if (input == (aliens.length+1))
    { save(aliens, round); System.exit(0);}
    else
    { printRoundOptions(aliens, input, round); }
  }

  // This method saves all aliens to a file
  public static void save(Alien[] aliens, int round) throws IOException
  {
    PrintWriter outputStream = new PrintWriter(new FileWriter("round.txt"));
    outputStream.println(round);
    outputStream.close();

    outputStream = new PrintWriter(new FileWriter("aliens.txt"));
    outputStream.println(aliens.length);
    for (Alien alien : aliens)
    {
      outputStream.println(getName(alien));
      outputStream.println(getAge(alien));
      outputStream.println(getColor(alien));
      outputStream.println(getHunger(alien));
      outputStream.println(getThirst(alien));
      outputStream.println(getAlive(alien));
    }
    outputStream.close();
  }

  // This method increases the hunger or thirst of an alien and updates its mood
  public static void reduceHealth(Alien[] aliens) throws InterruptedException
  {
    int randomAlien = random(0, (aliens.length));
    while (getAlive(aliens[randomAlien]) == false)
    {randomAlien = random(0, (aliens.length));}
    int foodOrWater = random(1,2);
    if (foodOrWater == 1)
    { aliens[randomAlien] = setHunger(aliens[randomAlien], (getHunger(aliens[randomAlien])-4));
      print("\n" + getName(aliens[randomAlien]) + " got hugry.");
      java.util.concurrent.TimeUnit.SECONDS.sleep(2);}
    else
    { aliens[randomAlien] = setThirst(aliens[randomAlien], (getThirst(aliens[randomAlien])-4));
      print("\n" + getName(aliens[randomAlien]) + " got thirsty.");
      java.util.concurrent.TimeUnit.SECONDS.sleep(2);}
  }

  // This method print the options within a round for an alien
  public static void printRoundOptions(Alien[] aliens, int index, int round) throws IOException
  {
    clearConsol();
    Alien a = aliens[index-1];
    int input = 0;

    if (getAlive(a) == true)
    {
      printAlien(a);
      print("1. Go back\n2. Feed\n3. Give water");
      input = inputInt("Choose an option:");
      input = validateInput(input, 1, 3, "Choose an option:");

    }
    else if (getAlive(a) == false)
    {
      prindDeathAlien(a);
      print("1. Go back");
      input = inputInt("Choose an option:");
      input = validateInput(input, 1, 1, "Choose an option:");
    }

    if (input == 1)
    { playRound(aliens, round); }
    else if (input == 2 )
    { feed(a); }
    else if (input == 3)
    { giveWater(a); }
    aliens[index-1] = a;
  } // END printRoundOptions

  // This method is used to feed the alien
  public static Alien feed(Alien a)
  {
    a = setHunger(a, (getHunger(a)+2));
    return a;
  }

  // This method is used to give water to the alien
  public static Alien giveWater(Alien a)
  {
    a = setThirst(a, (getThirst(a)+2));
    return a;
  }

  // This method prints a description of a single alien
  public static void printAlien(Alien a)
  {
      print(getName(a) + " the alien is " + getColor(a));
      print("Hunger: " + getHunger(a));
      print("Thirst: " + getThirst(a)+"\n");
  } // END printAlien

  public static void prindDeathAlien(Alien a)
  {
    print(getName(a) + " the alien has died." );
    print("It lived for " + getAge(a) + " days.\n");
  }

  // This method prints tha names of alive aliens
  public static void printOptions(Alien[] aliens, int round)
  {
    clearConsol();
    print("This is round " + round + "\n");
    print("Choose an alien");
    for (int i=0; i<aliens.length; i++)
    {
      print((i+1)+". "+getName(aliens[i]));
    }
    print((aliens.length+1)+". Save and Quit ");
  } // END printOptions






  // This method prints the instructions when the game is launched
  public static void printInstructions()
  {
    print("ALIEN PET SIMULATOR\n\nIn this game you need to take care of your numberOfAliens.\n");
    print("Every pet has hunger and thirst levels which change after each round.\n");
    print("You choose one of your pets to feed or give water each round.\n");
  } // END printInstructions

  // This method creates new array of aliens
  public static Alien[] newGame() throws InterruptedException
  {
    clearConsol();
    int numberOfAliens = inputInt("How many aliens do you want to create?\nEnter a number between 1-5");
    numberOfAliens = validateInput(numberOfAliens, 1, 5, "Enter a number between 1-5");
    Alien[] aliens = new Alien[numberOfAliens];

    for (int i=0; i<aliens.length; i++)
    {

      aliens[i] = newAlien();
    }

    return aliens;
  } // END newGame

  // This method creates a single new alien
  public static Alien newAlien() throws InterruptedException
  {
    clearConsol();
    Alien a = new Alien();

    a = setName(a, inputString("What is the name of your alien pet?"));
    print("Happy Birthday " + getName(a) + " the Alien");
    java.util.concurrent.TimeUnit.SECONDS.sleep(1);
    a = setAge(a, 1);

    String[] options = {"blue", "red", "yellow", "green", "white"};
    a = setColor(a, options[random(0,4)]);

    a = setHunger(a, random(1,10));
    a = setThirst(a, random(1,10));

    a = setAlive(a, true);
    return a;
  } // END newAlien

  // This method loads aliens from a file
  public static Alien[] loadGame() throws IOException
  {
    BufferedReader inputStream = new BufferedReader(new FileReader("aliens.txt"));
    int numberOfAliens = Integer.parseInt(inputStream.readLine());
    Alien[] aliens = new Alien[numberOfAliens];

    for (int i=0; i<numberOfAliens; i++)
    {
      Alien a = new Alien();
      a = setName(a, inputStream.readLine());
      a = setAge(a, Integer.parseInt(inputStream.readLine()));
      a = setColor(a, inputStream.readLine());
      a = setHunger(a, Integer.parseInt(inputStream.readLine()));
      a = setThirst(a, Integer.parseInt(inputStream.readLine()));
      a = setAlive(a, Boolean.parseBoolean(inputStream.readLine()));
      aliens[i] = a;
    }

    inputStream.close();

    return aliens;
  } // END loadGame

  // This method loads the last round from a file
  public static int loadRound() throws IOException
  {
    BufferedReader inputStream = new BufferedReader(new FileReader("round.txt"));
    int r = Integer.parseInt(inputStream.readLine());
    inputStream.close();
    return r;
  } // END loadRound





  // This is a general print
  public static void print(String message)
  {
    System.out.println(message);
  } // END print

  // This method inputs a String
  public static String inputString(String message)
  {
    Scanner scanner = new Scanner(System.in);
    if (!message.equals(""))
    {
      print(message);
    } // END if
    String input = scanner.nextLine();
    return input;
  } // END inputString

  // This methods takes an integer and returns it
  public static int inputInt(String message)
  {
    Scanner scanner = new Scanner(System.in);
    if (!message.equals(""))
    {
      print(message);
    } // END if
    int input = Integer.parseInt(scanner.nextLine());
    return input;
  } // END inputInt

  // This method returns a random number
  public static int random(int lowerBound, int uperBound)
  {
    Random random = new Random();
    int number = random.nextInt(uperBound) + (lowerBound);
    return number;
  } // END random


  // This method is used to validate user's inputs
  public static int validateInput(int input, int lowerBound, int upperBound, String message)
  {
    while (!(input>=lowerBound && input<=upperBound))
    {
      print("Invalid input!\n");
      input = inputInt(message);
    }
    return input;
  }




  public static Alien setName(Alien a, String name)
  {
    a.name = name;
    return a;
  } // END setName
  public static String getName(Alien a)
  {
    return a.name;
  } // END getName

  public static Alien setAge(Alien a, int age)
  {
    a.age = age;
    return a;
  } // END setAge
  public static int getAge(Alien a)
  {
    return a.age;
  } // END getAge

  public static Alien setColor(Alien a, String color)
  {
    a.color = color;
    return a;
  } // END setColor
  public static String getColor(Alien a)
  {
    return a.color;
  } // END getColor

  public static Alien setHunger(Alien a, int hunger)
  {
    a.hunger = hunger;
    return a;
  } // END setHunger
  public static int getHunger(Alien a)
  {
    return a.hunger;
  } // END getHunger

  public static Alien setThirst(Alien a, int thirst)
  {
    a.thirst = thirst;
    return a;
  } // END setThirst
  public static int getThirst(Alien a)
  {
    return a.thirst;
  } // END getThirst

  public static Alien setAlive(Alien a, boolean alive)
  {
    a.alive = alive;
    return a;
  } // END setAlive
  public static boolean getAlive(Alien a)
  {
    return a.alive;
  } // END getAlive
} // END miniProject

class Alien
{
  String name;
  int age;
  String color;
  int hunger;
  int thirst;
  boolean alive;
} // END Alien
