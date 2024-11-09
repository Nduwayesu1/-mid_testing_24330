package model1;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @Column(name = "room_id", nullable = false, unique = true)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID roomId;

    @Column(name = "room_code", nullable = false, unique = true)
    private String roomCode;

    // One-to-Many relationship with Shelf
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Shelf> shelfList;

    // Getters and Setters
    public UUID getRoomId() {
        return roomId;
    }

    public void setRoomId(UUID roomId) {
        this.roomId = roomId;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public List<Shelf> getShelfList() {
        return shelfList;
    }

    public void setShelfList(List<Shelf> shelfList) {
        this.shelfList = shelfList;
    }


    // Method to count books in the room
    public int countBooksInRoom() {
        int bookCount = 0;

        if (shelfList != null) {
            for (Shelf shelf : shelfList) {
                if (shelf.getBookList() != null) {
                    bookCount += shelf.getBookList().size(); // Count books on each shelf
                }
            }
        }

        return bookCount;
    }
}
