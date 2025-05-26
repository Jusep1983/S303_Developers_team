package managers;

import dtos.RoomDTO;
import entities.Player;
import entities.enums.Difficulty;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TicketPrinter {

    public static void printTicket(Player player, RoomDTO room) {

        Difficulty difficulty = room.getDifficulty();
        int durationMinutes = difficulty.getDurationMinutes();
        int price = difficulty.getPriceByDifficulty();
        LocalDate date = LocalDate.now();
        LocalTime startTime = LocalTime.now();

        String border = "+--------------------------------------------------+";
        String title = "|                🗝 ESCAPE ROOM TICKET 🗝           |";
        String lineFormat = "| %-15s: %-32s |";

        System.out.println(border);
        System.out.println(title);
        System.out.println(border);
        System.out.printf(lineFormat + "%n", "Player Name", player.getName());
        System.out.printf(lineFormat + "%n", "Room Name", room.getName());
        System.out.printf(lineFormat + "%n", "Date", date);
        System.out.printf(lineFormat + "%n", "Start Time", startTime.format(DateTimeFormatter.ofPattern("HH:mm")));
        System.out.printf(lineFormat + "%n", "Duration", durationMinutes + " minutes");
        System.out.println(border);
        System.out.printf(lineFormat + "%n", "Total", price + "€");
        System.out.println(border);
        System.out.println("|       GOOD LUCK AND HAVE FUN ESCAPING! 🎉         |");
        System.out.println(border);
    }
}