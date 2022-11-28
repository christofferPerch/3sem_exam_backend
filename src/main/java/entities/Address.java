package entities;

import javax.persistence.*;
import java.util.*;

@Entity
@NamedQuery(name = "Address.deleteAllRows", query = "DELETE from Address")
@Table(name = "address")
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id", nullable = false)
    private Integer id;

    @Column(name = "street_address", nullable = false, length = 45)
    private String streetAddress;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "zipcode", nullable = false)
    private CityInfo zipcode;

    @OneToMany(mappedBy = "address", cascade = CascadeType.ALL)
    private List<User> users = new ArrayList<>();

    public Address() {
    }

    public Address(String streetAddress, CityInfo zipcode) {
        this.streetAddress = streetAddress;
        this.zipcode = zipcode;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreetAddress() {
        return streetAddress;
    }

    public void setStreetAddress(String streetAddress) {
        this.streetAddress = streetAddress;
    }

    public CityInfo getZipcode() {
        return zipcode;
    }

    public void setZipcode(CityInfo zipcode) {
        this.zipcode = zipcode;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Address)) return false;
        Address address = (Address) o;
        return getId().equals(address.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", streetAddress='" + streetAddress + '\'' +
                ", zipcode=" + zipcode +
                ", users=" + users +
                '}';
    }
}