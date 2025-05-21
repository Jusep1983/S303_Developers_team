package utils;

public class Menus {
    // Menu provisional
    public static int mainMenuOptions() {
        return ValidateInputs.validateIntegerBetweenOnRange(""" 
                
                MAIN ESCAPE ROOM MENÚ
                ---Editor options-------
                1.  Show total inventory
                2.  Show total inventory value €
                3.  Add room
                4.  Delete room
                5.  Edit room (add/del clue and decoration)
                ---Business options-----
                6.  Generate tickets (elegir sala, ++€ totalSales)
                7.  Show total sales
                ---Users options--------
                8.  Notifications
                9.  Unsubscribe players
                10. Generate certificate
                0.  Exit
                """, 0, 10);
    }

}
