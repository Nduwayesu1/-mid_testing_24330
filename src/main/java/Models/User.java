package model1;

import model1.Enum.ERole;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User extends Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ERole role;

    @Column(name = "user_name", nullable = false, unique = true)
    private String userName;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "village_id", referencedColumnName = "location_id", nullable = false)
    private Location location;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private List<Membership> membershipList;

    @OneToMany(mappedBy = "reader", cascade = CascadeType.ALL)
    private List<Borrower> borrowerList;

    // Constructors
    public User() {}

    public User(String personId, String firstName, String lastName, String userName, String phoneNumber, String rawPassword, ERole role, Location location  ) {
        super(personId, firstName, lastName, phoneNumber); // Call the parent constructor
        this.userName = userName;
        this.password = hashPassword(rawPassword);
        this.role = role;
        this.location = location;
    }

    public User(UUID userId) {
        this.userId=userId;
    }

    public User(UUID userUUID, String sampleUser) {
        this.userId=userUUID;
        this.userName=sampleUser;
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    private String hashPassword(String rawPassword) {
        return BCrypt.hashpw(rawPassword, BCrypt.gensalt());
    }

//    // Getters and Setters
//    public UUID getUserId() {
//        return userId;
//    }
//
//    public void setUserId(UUID userId) {
//        this.userId = userId;
//    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ERole getRole() {
        return role;
    }

    public void setRole(ERole role) {
        this.role = role;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Membership> getMembershipList() {
        return membershipList;
    }

    public void setMembershipList(List<Membership> membershipList) {
        this.membershipList = membershipList;
    }

    public List<Borrower> getBorrowerList() {
        return borrowerList;
    }

    public void setBorrowerList(List<Borrower> borrowerList) {
        this.borrowerList = borrowerList;
    }
}
