// Program Name:	Elimination.java
// Developer:				Gary Simpson 
// Date:       					September 16, 2013  
// Purpose:      			To create and use jFrame and other container objects to 
//										create a functional elimination game based around dies 
//										being rolled and numbers 1-12 being eliminated until the 
//										user is unable to eliminate the total of the dies rolled or 
//										the two individual numbers.


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.util.Random; // used for random number generator.


public class Elimination extends JFrame implements ActionListener
{
//Declare variables for  container.
   static int NUMBER_OF_DICE = 2; // number used for die array
   private int[] rolledDie  = new int[NUMBER_OF_DICE]; // used for rolled die number
   private int rolledDie1 = 0; 
   private int rolledDie2 =  0;
   final int LIMIT = 6;  
   int currentScore = 78,  lowScore = 78;  // beginning i#'s for both scores
   int dieClickTotal = 0; // used to tally dice clicked to prevent user from getting extra points.
   int dieBtnClick = 0; // has 3 states: 0=unused, 1=one of two dies clicked, 2 = total of dies clicked.
   private boolean firstRoll = true;  // determines if user has just started game.
// Declare all jradio and jbutton buttons, and button group
   private static JButton btnRoll = new JButton("Roll");
   private JButton btnReset = new JButton("Reset");
   private JButton btnRules = new JButton("Rules");
   private JRadioButton radWhite = new JRadioButton("White", false);
   private JRadioButton radRed = new JRadioButton("Red", false);
   private JRadioButton radBlue = new JRadioButton("Blue", false);
   private ButtonGroup frameColorGroup = new ButtonGroup(); 
   private JButton[] btnDie; //array of buttons for 12 dice.
   private static final String[] dieNames = {"1", "2",
      "3", "4", "5","6", "7", "8", "9", "10", "11", "12"};  // String[] used for button 1-12 names
// Heading and label for Rolled Dice, Current and Lowest scores
   private JLabel lblRolledDiceHeading = new JLabel("Rolled Dice:  "); 
   private JLabel lblRolledDice = new JLabel("0 , 0") ;
   private JLabel lblCurrentScoreHeading = new JLabel("Current Score:"); 
   private JLabel lblCurrentScore = new JLabel(Integer.toString(currentScore)); 
   private JLabel lblLowScoreHeading = new JLabel("Lowest Score:"); 
   private JLabel lblLowScore = new JLabel(Integer.toString(lowScore)); 
// Declare JPanels   and layout
   private BorderLayout layout; // border layout set layout
   private JPanel northJPanel; 
   private JPanel westJPanel;
   private JPanel centerJPanel;
   private JPanel eastJPanel;
   private JPanel southJPanel;
   private JPanel southRadioJPanel;
   private JPanel southButtonsJPanel;

// No argument constructor
   public Elimination()
   {
      super("Elimination");
      btnRoll.requestFocusInWindow();
      layout = new BorderLayout(7, 7); // 5 pixel gap
      setLayout(layout); // set frame layout
      setResizable(false); // disable user resizing of frame
      btnDie = new JButton[dieNames.length]; // set size of array
   
   // register listeners
      btnReset.addActionListener( this );
      btnRoll.addActionListener( this );
      btnRules.addActionListener( this );
      radWhite.addActionListener( this );
      radRed.addActionListener( this );
      radBlue.addActionListener( this );
   
   // set fonts for interface items
      lblLowScore.setFont(new Font("Arial", Font.BOLD, 22));
      lblCurrentScore.setFont(new Font("Arial", Font.BOLD, 22));
      lblRolledDice.setFont(new Font("Arial", Font.BOLD, 15));
      lblRolledDiceHeading.setFont(new Font("Courier", Font.ITALIC, 18));
   //  Set tool tips for objects
      btnReset.setToolTipText( "Click to reset game." );
      btnRoll.setToolTipText( "Click to roll die and continue game." );
      btnRules.setToolTipText( "Click to display game rules." );
      lblLowScore.setToolTipText( "Lowest Score so far. How low can you go :-)" );
      lblCurrentScore.setToolTipText( "Current score so far." );
      lblRolledDice.setToolTipText( "Current numbers of dice shows here. click 'Roll' to begin" );
      radWhite.setToolTipText( "Change to the white theme." );
      radRed.setToolTipText( "Change to the Red theme." );
      radBlue.setToolTipText( "Change to the Blue theme." );
          // add all elements to different panels.
      northJPanel = new JPanel(); // set up panel
      northJPanel.setLayout(new FlowLayout());
      northJPanel.add(lblRolledDiceHeading);
      northJPanel.add( lblRolledDice);
      westJPanel = new JPanel(); 
      westJPanel.setLayout(new GridLayout(2,1));
      westJPanel.add(lblLowScoreHeading);
      westJPanel.add(lblLowScore);
   
      eastJPanel = new JPanel(); 
      eastJPanel.setLayout(new GridLayout(2,1));
      eastJPanel.add(lblCurrentScoreHeading);
      eastJPanel.add(lblCurrentScore);
   
      southJPanel = new JPanel(); 
      southJPanel.setLayout(new GridLayout(2,1));
      southRadioJPanel = new JPanel();
      southRadioJPanel.setLayout(new FlowLayout());
      southRadioJPanel.add(radWhite);
      southRadioJPanel.add(radRed);
      southRadioJPanel.add(radBlue);
   //Titled border for radio buttons panel
      southRadioJPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Change Background Color:"));
   
      southJPanel.add(southRadioJPanel);
      southButtonsJPanel = new JPanel();
      southButtonsJPanel.setLayout(new FlowLayout());
      southButtonsJPanel.add(btnRules);
      southButtonsJPanel.add(btnRoll);
      southButtonsJPanel.add(btnReset);
      southJPanel.add(southButtonsJPanel);
      centerJPanel = new JPanel(); 
      centerJPanel.setLayout(new GridLayout(2,6));
      add(northJPanel , BorderLayout.NORTH );
      add( westJPanel , BorderLayout.WEST );
      add(centerJPanel , BorderLayout.CENTER );
      add( eastJPanel , BorderLayout.EAST );
      add( southJPanel , BorderLayout.SOUTH );
   // create die button and  and register listeners for them
      for(int count = 0; count < dieNames.length; count++ )  {
         btnDie[count] = new JButton(dieNames[count] ); // create all dice
         btnDie[count].addActionListener( this ); // add all dic listeners
         centerJPanel.add(btnDie[count]);
         btnDie[count].setEnabled(false);  
         btnDie[count].setToolTipText( 
            "Click here once the game begins to select " + dieNames[count] + "." );}
   // set mnemonic  see site for help...   http://docs.oracle.com/javase/tutorial/uiswing/misc/keybinding.html
      radBlue.setMnemonic('B');
      radRed.setMnemonic('R');
      radWhite.setMnemonic('W');
      btnReset.setMnemonic('E');
      btnRoll.setMnemonic('O');
      btnRules.setMnemonic('U');
   // Add radButtons to radio group.
      frameColorGroup.add(radWhite);
      frameColorGroup.add(radBlue);
      frameColorGroup.add(radRed);
      btnReset.setEnabled(false);
      invalidate();
      validate();
   }

// main used for frame instantiation, default close, window size etc...
   public static void main(String[] args)
   {
      final int FRAME_WIDTH = 510;
      final int FRAME_HEIGHT =250;
      Elimination frame = new Elimination();
      frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
      frame.setSize(FRAME_WIDTH, FRAME_HEIGHT); //set frame size
      frame.pack();
      btnRoll.requestFocusInWindow();
      frame.setVisible(true); // display frame
      frame.setLocationRelativeTo(null); // set frame center of screen
   }

   public void actionPerformed(ActionEvent event)
   {
      Object source = event.getSource();
      for(int count = 0; count < dieNames.length; count++ )  {   // Beginning for loop for button array objects
         if(source == btnDie[count])  {//Test for  die button pressed  if so begin processing 
            if((dieClickTotal < rolledDie1 +rolledDie2) && (dieClickTotal + count < rolledDie1 + rolledDie2))  {
               if((rolledDie1 == rolledDie2) && (count +1 != rolledDie1 * 2))  { // test for doubles / error prompt
                  JOptionPane.showMessageDialog(null, 
                     "You rolled doubles. You must select the sum of both die.\n Then click 'Roll to continue.");  }  // end test for doubles/ error prompt 
               else if(count + 1 != rolledDie1 + rolledDie2)  {  // else if  wrong number selection - error
                  if((count + 1 == rolledDie1) || (count +1 == rolledDie2))  {
                     dieBtnClick = dieBtnClick +1; 
                     firstRoll = false;
                     dieClickTotal = dieClickTotal + (count + 1);
                     btnDie[count].setEnabled(false); 
                     btnReset.setEnabled(true);
                     currentScore =  currentScore - (count +1); 
                     lblCurrentScore.setText(Integer.toString(currentScore));
                     if(dieBtnClick ==2)
                     {  btnRoll.setEnabled(true);  }
                     if(lowScore  > currentScore){  // if used for current and lowest score processing
                        lowScore = currentScore;
                        lblLowScore.setText(Integer.toString(lowScore));  }  }
                  else  {
                     JOptionPane.showMessageDialog(null, 
                        "You must select a single die amount or total amount rolled!");    }  }
               // else if statement used for total/doubles amount click & total already selected - error
               else if(count +1 == rolledDie1 +rolledDie2){
                  dieClickTotal = dieClickTotal + (count + 1);
                  dieBtnClick = 2;
                  btnRoll.setEnabled(true);
                  firstRoll = false;
                  btnReset.setEnabled(true);
                  btnDie[count].setEnabled(false); 
                  currentScore =  currentScore - (count +1); 
                  lblCurrentScore.setText(Integer.toString(currentScore));  
                  if(lowScore  > currentScore){  // if used for current and lowest score processing
                     lowScore = currentScore;
                     lblLowScore.setText(Integer.toString(lowScore));  }  } // end else if for total/double processing
            }
            else // else used for  total already selected - error
            {JOptionPane.showMessageDialog(null, 
                     "You an incorrect dice total select the correct amount then."+
                           " \nclick 'Roll' to continue when you have no more moves the game is over." +
                                             "\n  Click reset to play agin.");} // end else for total already selected - error
         } //end die button pressed processing
         if(source == btnReset)  { // if_ when reset button pressed. 
            currentScore = 78; 
            dieBtnClick = 0;
            dieClickTotal = 0;
            lblRolledDice.setText("0 , 0") ;
            lblCurrentScore.setText("78") ;
            btnReset.setEnabled(false); 
            btnRoll.setEnabled(true);
            btnDie[count].setEnabled(false);
            btnDie[count].setToolTipText( 
               "Click here once the game begins to select " + dieNames[count] + "." );
            btnRoll.requestFocusInWindow();
            firstRoll = true;  
         } 
                          // set first roll to false
         if(dieBtnClick ==2)  {  
            btnRoll.setEnabled(true);  } 
         if(source == btnRoll)  {  // check for first roll and a roll button click
         // array declare and creation (declare[] = creation[]).
            Dice[] Die = new Dice[NUMBER_OF_DICE];
            for(int counter = 0; counter <Die.length ; ++counter)
            {
               dieBtnClick = 0;
               dieClickTotal = 0;
               Die[counter] = new Dice();
            }
            rolledDie1 = Die[0].getRolledDie();
            rolledDie2 = Die[1].getRolledDie(); 
         //rolledDie2 = 5;
            btnRoll.setEnabled(false);
            lblRolledDice.setText(Integer.toString(rolledDie1) +
               " , " + Integer.toString(rolledDie2)); // change the text on the label for rolled dice
         
            if(firstRoll)
            {  btnDie[count].setEnabled(true);
               btnDie[count].setToolTipText( 
                  "Click here to select " + dieNames[count] + "." );
               lblRolledDice.setToolTipText( "Current numbers on dice shows here."); } }
      } // END FOR LOOP FOR DIE PRESS DETECTION
      if(source == btnRules)  {  // if_ when rules button pressed.
         JOptionPane.showMessageDialog(null,
               "The goal of this game is to gain a low score." + 
               "\nYou will 'roll' two dice and use the numbers on the dice or" +
               "\nthe sum of the dice to eliminate a list of numbers from one" +
               "\n to twelve. You decrease your score by the specific numbers " +
               "\nyou eliminated. You always start with a score of 78 which is" + 
               " \nthe sum of the numbers from one to twelve." +
                "\nThe game is over when you cannot eliminate both die numbers.");  }
   
      invalidate();
      validate();
      if(source == radWhite)
      {  colorSchemeWhite();  }
      else if(source == radRed)
      {  colorSchemeRed();  }
      else if(source == radBlue)
      {  colorSchemeBlue();  }
   }  // end actionperformed method

// public void rollDice()
//    {
//       rolledDie1 = 1 + randomNumber.nextInt(LIMIT); // pick random number from 1 - LIMIT
//       rolledDie2 =  1 + randomNumber.nextInt(LIMIT); // pick random number from 1 - LIMIT
//       lblRolledDice.setText(Integer.toString(rolledDie1) +
//                 " , " + Integer.toString(rolledDie2)); // change the text on the label for rolled dice
//       invalidate();
//       validate();
//    }

   public void colorSchemeWhite()
   {
      getContentPane().setBackground( Color.WHITE );
      lblRolledDice.setForeground( Color.RED );
      lblRolledDiceHeading.setForeground( Color.RED );
      eastJPanel.setBackground(Color.GRAY);
      westJPanel.setBackground(Color.GRAY);
      northJPanel.setBackground( Color.LIGHT_GRAY );
      southRadioJPanel.setBackground( Color.LIGHT_GRAY );
      southButtonsJPanel.setBackground( Color.GRAY );
   
      btnRules.setForeground( Color.RED );
      btnRoll.setForeground( Color.RED );
      btnReset.setForeground( Color.RED );
   
   }
   public void colorSchemeRed()
   {
      getContentPane().setBackground( Color.RED);
      lblRolledDice.setForeground( Color.BLACK);
      lblRolledDiceHeading.setForeground( Color.DARK_GRAY );
      eastJPanel.setBackground(Color.GRAY);
      westJPanel.setBackground(Color.GRAY);
      northJPanel.setBackground( Color.LIGHT_GRAY );
      southRadioJPanel.setBackground( Color.LIGHT_GRAY );
      southButtonsJPanel.setBackground( Color.GRAY );
   
      btnRules.setForeground( Color.DARK_GRAY  );
      btnRoll.setForeground( Color.DARK_GRAY  );
      btnReset.setForeground( Color.DARK_GRAY  );
   }
   public void colorSchemeBlue()
   {
      getContentPane().setBackground( Color.CYAN);
      lblRolledDice.setForeground( Color.BLUE );
      lblRolledDiceHeading.setForeground( Color.BLUE );
      eastJPanel.setBackground(Color.GRAY);
      westJPanel.setBackground(Color.GRAY);
      northJPanel.setBackground( Color.LIGHT_GRAY );
      southRadioJPanel.setBackground( Color.GRAY );
      southButtonsJPanel.setBackground( Color.LIGHT_GRAY );
   
      btnRules.setForeground( Color.BLUE );
      btnRoll.setForeground( Color.BLUE );
      btnReset.setForeground( Color.BLUE );
   }
 
}
