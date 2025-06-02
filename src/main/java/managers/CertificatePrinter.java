package managers;

import dtos.RoomDTO;
import entities.EscapeRoom;
import entities.Player;
import entities.Ticket;
import exceptions.PlayerNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

public class CertificatePrinter {

    private boolean playerCompletedRoom(Player player, RoomDTO room) {
        List<Ticket> tickets = player.getTicketsBought();
        for (Ticket ticket : tickets) {
            if (ticket.getRoom().equals(room.name())) {
                return true;
            }
        }
        System.out.println("This player did not play this room.");
        return false;
    }

    public void processCertificate(EscapeRoom escapeRoom) {
        CertificatePrinter printer = new CertificatePrinter();
        try {
            Player player = escapeRoom.playerManager().selectPlayer();
            Optional<RoomDTO> roomOpt = escapeRoom.roomManager().selectRoom("print certification for");
            if (roomOpt.isEmpty()){
                System.out.println("No room selected. Cannot print ticket.");
                return;
            }
            RoomDTO room = roomOpt.get();
            printer.printCertificate(player, room);
        } catch (PlayerNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void printCertificate(Player player, RoomDTO room) {
        if (!playerCompletedRoom(player, room)) {
            return;
        }
        String certificate = generateCertificateText(player, room);
        System.out.println(certificate);
        saveCertificateToFile(player, room, certificate);
    }

    private String generateCertificateText(Player player, RoomDTO room) {
        LocalDateTime completionTime = LocalDateTime.now();
        return """
                ----------------------------------------
                      ESCAPE ROOM COMPLETION CERTIFICATE
                ----------------------------------------
                Congratulations, %s!
                
                You have successfully completed the room:
                "%s"
                
                Completion Time: %s
                
                Thank you for playing!
                
                ----------------------------------------
                """.formatted(
                player.getName(),
                room.name(),
                completionTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }

    private void saveCertificateToFile(Player player, RoomDTO room, String content) {
        String filename = "certificate_" + player.getName().replaceAll(" ", "_") + "_" + room.name().replaceAll(" ", "_") + ".txt";
        Path directory = Path.of("certificates");
        Path filePath = directory.resolve(filename);
        try {
            Files.createDirectories(directory);
            Files.writeString(filePath, content);
            System.out.println("Certificate saved as " + filename);
        } catch (IOException e) {
            System.err.println("Failed to save certificate: " + e.getMessage());
        }
    }
}