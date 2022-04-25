import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.util.Random;
import java.util.concurrent.locks.Condition;

public class OregonTrailGUI {

    private JPanel MainPanel;
    private JPanel IMGPanel;
    private JLabel ImageLabel;
    private JPanel BottomPanel;
    private JTextField StoryTextField;
    private JPanel GeneralPanel;
    private JPanel BenPanel;
    private JPanel AugustaPanel;
    private JPanel CharlesPanel;
    private JPanel HattiePanel;
    private JTextArea storyTextArea;
    private JPanel JakePanel;
    private JTextField userInput;
    private JLabel mainInputLabel;
    private JPanel InventoryPanel;
    private JLabel InventoryImagePanel;
    private final Scene scene = new Scene();
    private final DebugGUI debug = new DebugGUI();
    private Random rand = new Random();

    //Our players
    private Character hattie = new Character("Hattie Campbell", 100, 0);
    private Character charles = new Character("Charles",100,0);
    private Character augusta = new Character("Augusta",100,0);
    private Character ben = new Character("Ben",100,0);
    private Character jake = new Character("Jake",100,0);

    //game variables
    private int food = 0, ammunition = 0, medicine = 0, clothes = 0, wagonTools = 0, splints = 0, oxen = 0;
    private boolean isGameWon = false, isGameLost = false;
    private int happiness;
    private Weather weather = new Weather();
    private Wagon wagon = new Wagon();

    private static OregonTrailGUI game = new OregonTrailGUI();

    public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setContentPane(game.MainPanel);
        frame.setTitle("The Oregon Trail -- Remake");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        JMenuBar menuBar = new JMenuBar();
        menuBar.setVisible(true);
        frame.setJMenuBar(menuBar);

        JMenu menuMain = new JMenu("Main");
        menuBar.add(menuMain);
        JMenu menuAbout= new JMenu("About");
        menuBar.add(menuAbout);

        //Give the application the System's theme.
        //Delete this line if the app won't start
        setTheme();

//TODO: Make menu buttons do things
        JMenuItem mainMenu = new JMenuItem("Main Menu");//Prompts are you sure window if game condition is not win/lose
        JMenuItem exitApp = new JMenuItem("Exit");     //Prompts are you sure window if game condition is not win/lose
        menuMain.add(mainMenu);                                      //returns to main menu, resets game
        menuMain.add(exitApp);                                      //exits app
        JMenuItem projectDescription = new JMenuItem("Project Description");
        JMenuItem aboutProject = new JMenuItem("About the Project");
        JMenuItem aboutHattie = new JMenuItem("About Hattie Campbell");
        JMenuItem imageCredits = new JMenuItem("Image Credits");
        menuAbout.add(projectDescription);
        menuAbout.add(aboutProject);
        menuAbout.add(aboutHattie);
        menuAbout.add(imageCredits);
        frame.setExtendedState(frame.getExtendedState() | JFrame.MAXIMIZED_BOTH);   //makes fullscreen
        frame.setVisible(true);


        //Action Listeners for menu:

        //Main:

        mainMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("Close your eyes and imagine going back to the main menu here...");
            }
        });
        exitApp.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exitGame();
            }
        });

        //About:

        projectDescription.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout("The Oregon Trail is an educational game to teach students about the 1800s trip from Missouri to Oregon. This project aims to recreate this experience using the Java programming language. With incredible high-resolution graphics, the experience is more immersive than ever before","Project Description");
            }
        });

        aboutProject.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout("This project was completed by Team Hollenberg Station, consisting of Aleece Al-Olimat, Ken Zhu, and PJ Oschmann","About This Project");
            }
        });

        aboutHattie.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout("(Replace me with actual text) Hattie is a young lass who is setting out for a new life in Oregon. Her twin sister died. What a shame.","About Hattie");
            }
        });
        imageCredits.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showAbout("Images created by Aleece Al-Olimat.\nImage of Oregon by Kendra of Clker.com, in the Public Domain.","Image Credits");
            }
        });



    }

    /**
     * Sets the application's UI to follow the System's look and feel,
     * instead of using the default Metal theme.
     * On Windows, native Windows (win32) elements are used.
     * On Linux, the GTK2 theme is used.
     * On MacOS, the MacOS theme is used. Probably.
     */
    public static void setTheme() {
        try {
            // Set System L&F
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception e) {
            //Don't set it
            //(If setting the theme fails, the code simply is not run.)
        }
        for (Window window : Window.getWindows()) {
            SwingUtilities.updateComponentTreeUI(window);
        }
    }

    /**
     * Shows dialog asking the user if they want to exit. If the user selects "yes," the game
     * exits gracefully (returns 0).
     */
    public static void exitGame() {
        if (JOptionPane.showConfirmDialog(null,"Are you sure you want to quit?","Exit?",JOptionPane.YES_NO_OPTION)==JOptionPane.YES_OPTION) {
            System.exit(0);
        }

    }

    /**
     * Displays a dialog box the screen. Asks as a preset to maintain a common style across all "About" messages.
     * A cute picture of Oregon is shown for some visual flair, and a new line is created every 15 words.
     * @param message - The message to display to the user
     * @param title - The title of the message box.
     */

    //Oregon image from http://www.clker.com/clipart-oregon-3.html
    public static void showAbout(String message, String title) {
        int breakTextAt = 15; //Where to break the text
        StringBuilder newMessage = new StringBuilder(message);
        int wordCounter = 0;
        ImageIcon oregonIcon = new ImageIcon("assets/oregonState.png");

        for (int i=0;i<message.length();i++) {
            if (message.charAt(i)==' ') {
                wordCounter++;
                if (wordCounter==breakTextAt) {newMessage.setCharAt(i,'\n'); wordCounter=0;}
            }

        }

        JOptionPane.showMessageDialog(null,"<html><h1>"+title+"</h1><br>"+newMessage,title,JOptionPane.PLAIN_MESSAGE,oregonIcon);
    }




    /**
     * Constructor for OregonTrailGUI
     */
    //Create application
    public OregonTrailGUI() {

        ImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/TestImage1.png"));
        userInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//TODO: CREATE USER INPUT FIELD CODE
                if (userInput.getText().equalsIgnoreCase("I")) {
                    Inventory inv = new Inventory(food, ammunition, medicine, clothes, wagonTools, splints, oxen);
                    inv.pack();
                    inv.setVisible(true);
                }
                else if (userInput.getText().equalsIgnoreCase("H")) {
                    storyTextArea.setText(
                            """
                            INPUT DIALOGUE BOX HELP MENU
                            
                            OPTIONS AVAILABLE FOR INPUT DIALOGUE BOX:
                            H: HELP
                            I: INVENTORY
                            P: PARTY
                            
                            IF YOU ARE INSIDE OF A TOWN OR FORT:
                            W: GO TO THE LOCAL WAGON REPAIR MECHANIC
                            S: VISIT THE LOCAL SHOP
                            R: REST AT THE LOCAL INN
                            """
                    );
                }
                else if (userInput.getText().equalsIgnoreCase("P")) {
//TODO: IMPLEMENT PARTY MENU AND DETAILS DIALOGUE CLASS
                }
                userInput.setText("");
            }
        });
        userInput.addFocusListener(new FocusAdapter() { //Grey text for input box when not focused on
            @Override
            public void focusGained(FocusEvent e) {
                if (userInput.getText().trim().equals("Enter 'H' to display input options")) {
                    userInput.setText("");
                    userInput.setForeground(Color.BLACK);
                }
            }

            @Override
            public void focusLost(FocusEvent e) {
                if (userInput.getText().trim().equals("")) {
                    userInput.setText("Enter 'H' to display input options");
                    userInput.setForeground(new Color(147, 147,147));
                }
            }
        });
    }


    /**
     * Calculate happiness to add or subtract. This method ensures that
     * happiness won't exceed 100 or become less than 0.
     * @param amount - The amount of happienss to add or subtract. (Positive = add; negative = subtract)
     * @return the amount of happiness to add or subtract.
     */
    public int calculateHappiness(int amount) {
        int newHappiness = 0;
        if (amount>0) {
            if (happiness+amount <=100) {newHappiness = amount;}
            else {newHappiness = 100-happiness;}
        }
        else {
            if (happiness + amount >= 0) {newHappiness = amount;}
            else {newHappiness = -happiness;}
        }
        return happiness+newHappiness;
    }

    /**
     *Impact happiness based on factors.
     * Good weather increases happiness by 5; bad weather decreases it by 5.
     */
    //Figured "setHappiness" wasn't appropriate. Is there a better name we can use?
    //Should each factor have its own method?
    public void impactHappiness() {
        //Weather
        if (weather.getWeatherCondition().equals("Good")) {happiness = calculateHappiness(5);}
        else if (weather.getWeatherCondition().equals("Bad")) {happiness = calculateHappiness(-5);}

        //Player is ill
            //Code goes here lmao
    }

    /**
     * If a given character is not wearing protective clothing, they will be harmed by
     * cold weather. Their health will decrease by 25 HP each day, and they have a 1/4
     * chance of getting ill.
     * @param character - The character that will be affected.
     */
    public void weatherAffectPlayer(Character character) {
        if (!character.getHasClothing()) {
            character.setHealth(character.getHealth() - 25);
            if (rand.nextInt(4) == 0) {
                character.setSick(true);
            }
        }

    }

    /**
     * Check if the game is lost. The game is lost if:
     * - there are no healthy oxen
     * - the wagon breaks
     * - the party's happiness equals 0
     * - everyone dies
     * - no one has food available for 3 consecutive days.
     * @return false if the game is not lost, true if the game is lost
     */

    //TODO: Implement checks for no healthy oxen and lack of food for 3 days
    public boolean checkIfLost() {
        boolean isLost = false;
        String message = "";
        //If the wagon breaks
        if (wagon.getState() == 2) {
            message= "Your wagon broke. You can't continue";
            isLost = true;
        }

        //If party happiness == 0
        else if (happiness <= 0) { //Using "less than" in case it somehow goes below 0.
            message = "Everyone is beyond sad. Happiness is but a memory. You can't continue.";
            isLost = true;
        }

        //If everyone is dead
        else if (hattie.getHealth()==0 && charles.getHealth() == 0 && augusta.getHealth() == 0 && ben.getHealth() == 0 && jake.getHealth() == 0) {
            message = "Everyone literally died. Ghosts don't go to Oregon. You can't continue.";
            isLost = true;
        }

        JOptionPane.showMessageDialog(null,message,"Game Over",JOptionPane.PLAIN_MESSAGE);
        return isLost;

    }
}

