package model1;

import model1.Enum.EStatus;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Entity
@Table(name = "membership")
public class Membership {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "membership_id", nullable = false, unique = true)
    private UUID membershipId;
    @Column(name = "membership_code", nullable = false, unique = true)
    private String membershipCode;
    @Column(name = "expiring_time")
    private LocalDate expiringTime;

    @Column(name = "price_per_day", nullable = false)
    private int pricePerDay;
    @OneToMany(mappedBy = "membership", cascade = CascadeType.ALL)
    private List<Membership_type> membershipTypes;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private EStatus status;
    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User user;
    @Column(name = "registration_date", nullable = false)
    private LocalDate registrationDate;

    public Membership(UUID membershipId, String membershipCode, LocalDate expiringTime, int pricePerDay, List<Membership_type> membershipTypes, EStatus status, UUID user, LocalDate now) {
    }

    public Membership() {
    }

    public Membership(UUID membershipId, String membershipCode, LocalDate expiringTime, int pricePerDay, List<Membership_type> membershipTypes, EStatus status, User user, LocalDate registrationDate) {
        this.membershipId = membershipId;
        this.membershipCode = membershipCode;
        this.expiringTime = expiringTime;
        this.pricePerDay = pricePerDay;
        this.membershipTypes = membershipTypes;
        this.status = status;
        this.user = user;
        this.registrationDate = registrationDate;
    }



    public UUID getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(UUID membershipId) {
        this.membershipId = membershipId;
    }

    public String getMembershipCode() {
        return membershipCode;
    }

    public void setMembershipCode(String membershipCode) {
        this.membershipCode = membershipCode;
    }

    public LocalDate getExpiringTime() {
        return expiringTime;
    }

    public void setExpiringTime(LocalDate expiringTime) {
        this.expiringTime = expiringTime;
    }

    public int getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(int pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public List<Membership_type> getMembershipTypes() {
        return membershipTypes;
    }

    public void setMembershipTypes(List<Membership_type> membershipTypes) {
        this.membershipTypes = membershipTypes;
    }

    public EStatus getStatus() {
        return status;
    }

    public void setStatus(EStatus status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public LocalDate getRegistrationDate() {
        return registrationDate;
    }

    public void setRegistrationDate(LocalDate registrationDate) {
        this.registrationDate = registrationDate;
    }
}
