package managers;

import dtos.PlayerDTO;
import dtos.RoomDTO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CertificatePrinter {

    public void printCertificate(PlayerDTO player, RoomDTO room) {
        LocalDateTime completionTime = LocalDateTime.now();
        String certificate = generateCertificateText(player, room, completionTime);

        System.out.println(certificate);

        saveCertificateToFile(player, room, certificate);
    }

    private String generateCertificateText(PlayerDTO player, RoomDTO room, LocalDateTime completionTime) {
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
                player.name(),
                room.name(),
                completionTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
        );
    }

    private void saveCertificateToFile(PlayerDTO player, RoomDTO room, String content) {
        String filename = "certificate_" + player.name().replaceAll(" ", "_") + "_" + room.name().replaceAll(" ", "_") + ".txt";
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