package model1;

import model1.Enum.ELocation_type;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "location")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "location_id", nullable = false, unique = true)
    private UUID locationId;
    @Column(name = "location_code", nullable = false, unique = true)
    private String locationCode;
    @Column(name = "location_name", nullable = false)
    private String locationName;
    @Enumerated(EnumType.STRING)
    private ELocation_type type;

    @ManyToOne(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "parent_id")
    private Location parentLocation;
    @OneToMany(mappedBy = "location")
    private List<User> users;

    public Location() {
    }

    public Location(UUID locationId, String locationCode, String locationName, ELocation_type type, Location parentLocation) {
        this.locationId = locationId;
        this.locationCode = locationCode;
        this.locationName = locationName;
        this.type = type;
        this.parentLocation = parentLocation;
    }

    public Location(UUID locationId, ELocation_type type, Location parentLocation) {
        this.locationId = locationId;
        this.type = type;
        this.parentLocation = parentLocation;
    }

    public Location(UUID locationId, String locationName, ELocation_type type, Location parentLocation) {
        this.locationId = locationId;
        this.locationName = locationName;
        this.type = type;
        this.parentLocation = parentLocation;
    }

    public Location(UUID locationId, String locationCode, String locationName, ELocation_type type, Location parentLocation, List<User> users) {
        this.locationId = locationId;
        this.locationCode = locationCode;
        this.locationName = locationName;
        this.type = type;
        this.parentLocation = parentLocation;
        this.users = users;
    }

    public UUID getLocationId() {
        return locationId;
    }

    public void setLocationId(UUID locationId) {
        this.locationId = locationId;
    }

    public String getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(String locationCode) {
        this.locationCode = locationCode;
    }

    public String getLocationName() {
        return locationName;
    }

    public void setLocationName(String locationName) {
        this.locationName = locationName;
    }

    public ELocation_type getType() {
        return type;
    }

    public void setType(ELocation_type type) {
        this.type = type;
    }

    public Location getParentLocation() {
        return parentLocation;
    }

    public void setParentLocation(Location parentLocation) {
        this.parentLocation = parentLocation;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
