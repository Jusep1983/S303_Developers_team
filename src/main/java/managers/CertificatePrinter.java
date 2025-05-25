package managers;

import entities.Player;
import entities.Room;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CertificatePrinter {

    public static void printCertificate(Player player, Room room) {
        LocalDateTime completionTime = LocalDateTime.now();
        String certificate = generateCertificateText(player, room, completionTime);

        System.out.println(certificate);

        // Optionally: Save to file
        saveCertificateToFile(player, room, certificate);
    }

    private static String generateCertificateText(Player player, Room room, LocalDateTime completionTime) {
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
                room.getName(),
                completionTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }

    private static void saveCertificateToFile(Player player, Room room, String content) {
        String filename = "certificate_" + player.getName().replaceAll(" ", "_") + "_" + room.getName().replaceAll(" ", "_") + ".txt";
        try {
            Files.writeString(Path.of("certificates/" + filename), content);
            System.out.println("Certificate saved as " + filename);
        } catch (IOException e) {
            System.err.println("Failed to save certificate: " + e.getMessage());
        }
    }
}