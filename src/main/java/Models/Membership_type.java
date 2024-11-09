package model1;

import javax.persistence.*;
import java.util.UUID;
@Entity
public class Membership_type {
    @Id
    @Column(name = "membership_type_id", nullable = false, unique = true)
    private UUID membershipTypeId;
    @Column(name = "membership_name", nullable = false)
    private String membershipName;
    @Column(name = "max_books", nullable = false)
    private Integer maxBooks;
    @Column(name = "price", nullable = false)
    private Integer price;
    @ManyToOne
    @JoinColumn(name = "membership_id", referencedColumnName = "membership_id", nullable = false)
    private Membership membership;

    public Membership_type() {
    }

    public Membership_type(UUID membershipTypeId, String membershipName, Integer maxBooks, Integer price, Membership membership) {
        this.membershipTypeId = membershipTypeId;
        this.membershipName = membershipName;
        this.maxBooks = maxBooks;
        this.price = price;
        this.membership = membership;
    }

    public UUID getMembershipTypeId() {
        return membershipTypeId;
    }

    public void setMembershipTypeId(UUID membershipTypeId) {
        this.membershipTypeId = membershipTypeId;
    }

    public String getMembershipName() {
        return membershipName;
    }

    public void setMembershipName(String membershipName) {
        this.membershipName = membershipName;
    }

    public Integer getMaxBooks() {
        return maxBooks;
    }

    public void setMaxBooks(Integer maxBooks) {
        this.maxBooks = maxBooks;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Membership getMembership() {
        return membership;
    }

    public void setMembership(Membership membership) {
        this.membership = membership;
    }
}
