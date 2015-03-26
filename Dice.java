// Program Name:	Dice.java
// Developer:		Gary Simpson 
// Date:       		September 16, 2013  
// Purpose:      		To create and use jFrame and other container objects to 
//			create a functional elimination game based around dies 
//			being rolled and numbers 1-12 being eliminated until the 
//			user is unable to eliminate the total of the dies rolled or 
//			the two individual numbers.


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Random; // used for random number generator.

public class Dice
{
   private int die = 0; // variable for die number after it has been rolled
   Random randomNumber = new  Random();
// constructor accepts no arguments.
   Dice()
   {
      rollDice();
   }

   public void rollDice()
   {
      die =  1 + randomNumber.nextInt(6); // pick random number from 1 - LIMIT
   }

   public int getRolledDie()
   {
      return die;
   }
   public void setRolledDie(int die1)
   {
      die = die1;
   }

}
