import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Inventory extends JDialog {
    private JPanel contentPane;
    private JTextArea invInfo;
    private JLabel InventoryImageLabel;
    private JPanel mainPanel;
    private JPanel textPanel;
    private JComboBox<String> invComboBox;
    private JTextField userInput;
    private JLabel invInputLabel;

    public Inventory(int food, int ammunition, int medicine, int clothes, int wagonTools, int splints, int oxen) {
        //instantiating private vars

        setContentPane(contentPane);
        setModal(true);

        //Image goes here
        InventoryImageLabel.setIcon(new javax.swing.ImageIcon("src/assets/images/Inventory.png"));
        if (invComboBox.getSelectedItem() == "SELECT AN INVENTORY ITEM") {
            displayMenu();
        }

        invComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int input = invComboBox.getSelectedIndex();
                switch (input) {
                    case 0 -> displayMenu();
                    case 1 -> displayFood(food);
                    case 2 -> displayAmmo(ammunition);
                    case 3 -> displayMed(medicine);
                    case 4 -> displayClothes(clothes);
                    case 5 -> displayWT(wagonTools);
                    case 6 -> displaySplints(splints);
                    case 7 -> displayOxen(oxen);
                    default -> invInfo.setText("ERROR IN SWITCH");
                }
        }});

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        //Action listener for userInput
        userInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String input = userInput.getText().toUpperCase();
                switch (input) {
                    case "I" -> { displayMenu(); invComboBox.setSelectedIndex(0); }
                    case "F" -> { displayFood(food); invComboBox.setSelectedIndex(1); }
                    case "A" -> { displayAmmo(ammunition); invComboBox.setSelectedIndex(2); }
                    case "M" -> { displayMed(medicine); invComboBox.setSelectedIndex(3); }
                    case "C" -> { displayClothes(clothes); invComboBox.setSelectedIndex(4); }
                    case "W" -> { displayWT(wagonTools); invComboBox.setSelectedIndex(5); }
                    case "S" -> { displaySplints(splints); invComboBox.setSelectedIndex(6); }
                    case "O" -> { displayOxen(oxen); invComboBox.setSelectedIndex(7); }
                    default -> displayMenu();
                }

                //TODO: Implement party gui and information to be altered in inventory when items are used
                if (userInput.getText().equalsIgnoreCase("U") && invComboBox.getSelectedIndex() == 1) {
                    useFood();
                    //This is what happens when they "use" a food item
                }
                else if (userInput.getText().equalsIgnoreCase("U") && invComboBox.getSelectedIndex() == 3) {
                    //This is what happens when they "use" the medicine item
                    useMedicine();
                }
                else if (userInput.getText().equalsIgnoreCase("E") && invComboBox.getSelectedIndex() == 4) {
                    //This is what happens when they "equip" the clothes item
                    equipClothes();
                }
                else if (userInput.getText().equalsIgnoreCase("U") && invComboBox.getSelectedIndex() == 6) {
                    //This is what happens when they "use" the splints item
                    useSplints();
                }
                else if (userInput.getText().equalsIgnoreCase("C") && invComboBox.getSelectedIndex() == 7) {
                    //This is what happens when they "consume" an OXEN for food
                    consumeOxen();
                }
                userInput.setText("");
            }
        });

        userInput.addFocusListener(inputHelp);
    }

    //TEXT METHODS and CANCEL METHOD
    private void displayMenu() {
        invInfo.setText(
                """
                Please select an INVENTORY item using the dropdown menu
                or enter the letter the letter in the dialogue box
                corresponding with the item you would like to view:
                
                F: FOOD
                A: AMMUNITION
                M: MEDICINE
                C: CLOTHES
                W: WAGON TOOLS
                S: SPLINTS
                O: OXEN
                I: RETURN TO THIS MENU
                
                Press ESC to exit the INVENTORY screen.
                """);
    }

    private void displayFood(int food) {
        invInfo.setText(String.format(
                """
                FOOD is a resource that prevents your party members
                from going HUNGRY. If the party has 0 units of FOOD
                for three days in a row, the game will end.
                
                Each unit of FOOD given to a party member will
                increase their food level by 2.
                
                You have %d units of FOOD.
                
                Type "U" to use this item.
                Type "I" to return to the INVENTORY menu.
                """, food
        ));
    }

    private void displayAmmo(int ammo) {
        invInfo.setText(String.format(
                """
                AMMUNITION is a consumable resource used in
                combination with one daily action to go HUNTING.

                One AMMUNITION box is consumed when your party goes
                hunting. HUNTING yields about double the food for
                its cost relative to buying food (on average).
                
                You have %d boxes of AMMUNITION.
                    
                Type "I" to return to the INVENTORY menu.
                """, ammo
        ));
    }

    private void displayMed(int med) {
        invInfo.setText(String.format(
                """
                MEDICINE is a resource that cures your party members
                of illness. When a party member is ILL, their food level
                consumption is increased by 2 a day on top of the travel
                consumption. That party member will also lose 5 HP a day
                and party happiness is reduced by 2 for each member that is
                sick each day.
                                            
                One unit of MEDICINE can cure a single party member, and
                can only be used on a character who is ILL.
                
                You have %d units of MEDICINE.
                
                Type "U" to use this item.
                Type "I" to return to the INVENTORY menu.
                """, med
        ));
    }

    private void displayClothes(int clothes) {
        invInfo.setText(String.format(
                """
                CLOTHES are a one-time consumable resource that will
                protect your party members from weather for the remainder
                of their journey. If a character is not protected from
                extreme weather, they may fall ILL and lose health as a
                consequence.
                
                This resource can be produced on the trip using a total of
                3 daily actions to produce clothes. This item may only be
                used on characters who do not already have a set of CLOTHES.
                
                You have %d sets of CLOTHES.
                
                Type "E" to equip this item onto a character.
                Type "I" to return to the INVENTORY menu.
                """, clothes
        ));
    }

    private void displayWT(int wagonTools) {
        invInfo.setText(String.format(
                """
                WAGON TOOLS are a consumable resource used to repair the
                wagon when it suffers damage along the journey. If your
                wagon is in a DAMAGED state, your party is forced to
                travel at the slowest pace. If it is left DAMAGED without
                repair for 7 consecutive days, the wagon will become BROKEN
                and the party will lose. There is also a risk that the wagon
                can suffer damage while DAMAGED and become BROKEN, also
                resulting in a game over.
                
                You can use this item when taking the MEND WAGON daily
                action in between your travels. This will consume one set
                of WAGON TOOLS and one daily action.

                You have %d spare WAGON TOOLS.
                
                Type "I" to return to the INVENTORY menu.
                """, wagonTools
        ));
    }

    private void displaySplints(int splints) {
        invInfo.setText(String.format(
                """
                SPLINTS are used to cure a party member of the INJURED
                condition. When someone is injured, it takes one week to
                naturally recover. While they are recovering, your party
                is forced to travel at the lowest speed. They lose 5 HP
                a day and party happiness decreases by 1 per day for each
                injured party member. Additionally, you will only have one
                daily action available until no one is injured.
                                             
                You can use one SPLINT to cure one party member from the
                INJURED status. This item may only be used on characters
                who are INJURED.
                 
                You have %d SPLINTS.
                
                Type "U" to use this item.
                Type "I" to return to the INVENTORY menu.
                """, splints
        ));
    }

    private void displayOxen(int oxen) {
        invInfo.setText(String.format(
                """
                OXEN are used to drive your wagon in your journey. You need
                a minimum of two OXEN to drive your cart at all available
                speeds, and one OXEN to be able to travel at all. There is
                an increased risk of injury to your OXEN if you have less
                than 4 OXEN pulling your wagon. If your OXEN becomes injured,
                you have the choice of harvesting 10 FOOD from it, or
                abandoning the OXEN.
                
                You may choose to kill an OXEN if you are low on food and
                in dire straits.
                                            
                You have %d OXEN.
                
                Type "C" to slaughter and harvest an OXEN for 10 FOOD.
                Type "I" to return to the INVENTORY menu.
                """, oxen
        ));
    }

    private void useFood() {
        invInfo.setText(
                """
                Player has chosen to use one unit of food.
                Proceed to select which character they want to feed.
                Requires the character to not be full or have food level == 10.
                
                Pop up a mini dialogue box saying that the character
                is full and cannot consume food if their food level is at 10.
                
                Enter "I" to return to the inventory menu.
                """);
    }

    private void useMedicine() {
        invInfo.setText(
                """
                Player has chosen to use one medicine item.
                Proceed to select which character they want to treat.
                Requires the character to be ILL to use this item.
                
                Pop up a mini dialogue saying that the character is
                not ILL or has been cured depending on what happens.
                
                Enter "I" to return to the inventory menu.
                """);
    }

    private void equipClothes() {
        invInfo.setText(
                """
                Player has chosen to equip one set of clothes.
                Proceed to select which character they want to equip.
                Requires the character to not have a set of clothes
                to equip a set of clothes..
                
                Pop up a mini dialogue saying that the character already
                has a set of clothes equipped and that they do not need
                another set.
                
                Enter "I" to return to the inventory menu.
                """);
    }

    private void useSplints() {
        invInfo.setText(
                """
                Player has chosen to use one splint.
                Proceed to select which character they want to use the
                splint on. Requires the character to be INJURED to use
                the splints item.
                
                Pop up a mini dialogue saying that the character is not
                INJURED if they try and use the splints on a healthy
                character.
                
                Enter "I" to return to the inventory menu.
                """);
    }

    private void consumeOxen() {
        invInfo.setText(
                """
                Player has chosen to CONSUME one OXEN. Increase total
                party food count by 10 units of food.
                
                Pop up a mini dialogue letting the player know they cannot
                consume an OXEN if it will leave them with less than 4 OXEN.
                
                Enter "I" to return to the inventory menu.
                """);
    }

    private FocusAdapter inputHelp = new FocusAdapter() { //Grey text for input box when not focused on
        @Override
        public void focusGained(FocusEvent e) {
            if (userInput.getText().trim().equals("Input Option Here")) {
                userInput.setText("");
                userInput.setForeground(Color.BLACK);
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (userInput.getText().trim().equals("")) {
                userInput.setText("Input Option Here");
                userInput.setForeground(new Color(147, 147,147));
            }
        }
    };

    private void onCancel() {
        dispose();
    }
}
