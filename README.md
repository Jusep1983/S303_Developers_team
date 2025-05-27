🧩 Virtual Escape Room - Level 3
Welcome to the Virtual Escape Room Project – Level 3, where we enhance our immersive puzzle-solving experience using MongoDB as the data persistence layer.

📌 Project Overview
This application simulates a Virtual Escape Room, where users can enjoy themed rooms, follow clues, and interact with unique decorative items. Level 3 builds upon previous levels but replaces MySQL with MongoDB for data management.

🛠️ Technologies Used
Java 17+
MongoDB
MongoDB Java Driver
Lombok
JUnit (for testing)

🧱 Core Entities
Escape Room: The main entity containing rooms, clues, and decorations.
Room: Each with a name, difficulty, and collections of clues and decorations.
Clue: Includes a name, price, and theme (e.g., PUZZLE, PASSWORD).
Decoration: Has a name, price, and material type (e.g., WOOD, METAL).
Ticket: Represents a user's entry with a recorded value.
Certificate: Issued to users who complete the rooms successfully.
Player : has an name, an email and isSubscribed (or needs to unsubscribe) and has ticketsBought list.

🧩 Features
✅ Create a new Virtual Escape Room
✅ Add rooms with difficulty levels
✅ Add themed clues to rooms
✅ Add decorative items to enhance room ambiance
✅ View full inventory (rooms, clues, decorations)
✅ Display total inventory value in euros
✅ Remove rooms, clues or decorations
✅ Issue tickets and view total revenue from ticket sales
✅ Generate achievement certificates
✅ Notify registered users about key events


xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
🧪 Testing (from Level 2)
At least two core functionalities are tested using JUnit:

Adding clues to a room
Calculating total inventory value
xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx

💾 MongoDB Integration (Level 3)
✅ Collections:
- escape_rooms: Stores all data about rooms, clues, and decorations. Each room document embeds sub-documents for clues and decorations
- players : stores all about players tickets


✅ DAO Pattern:
The app uses the DAO pattern to separate data access logic. Interfaces are defined for each domain (e.g., ClueDAO, RoomDAO), and implementations like ClueDAOImpl use the MongoDB Java driver.


▶️ Getting Started
Install MongoDB and ensure it's running on localhost:27017.

Clone the project:
git clone https://github.com/your-username/escape-room-mongodb.git
cd escape-room-mongodb
Run the application (Main class).

Interact via console-based menu.

📬 Notifications
/////////////////////////////////////////////////////////////////Users can register to receive notifications when:
//////////////////////////////////////////////////////////New clues or decorations are added  / A room is completed

📈 Future Improvements
GUI interface with JavaFX or Spring Boot REST API
User authentication and tracking
Multiplayer collaboration features

🧠 Authors
Developed as part of a Java full-stack learning path.
Jose : https://github.com/Jusep1983
Luis : https://github.com/Unrotopo
Zohra : https://github.com/zohra-b

