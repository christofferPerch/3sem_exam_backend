package entities;

import javax.persistence.*;
import java.util.*;

@Entity
@NamedQuery(name = "CityInfo.deleteAllRows", query = "DELETE from CityInfo")
@Table(name = "cityinfo")
public class CityInfo {
    @Id
    @Column(name = "zipcode", nullable = false)
    private Integer zipCode;

    @Column(name = "city_name", nullable = false, length = 45)
    private String cityName;

    @OneToMany(mappedBy = "zipcode")
    private List<Address> addresses = new ArrayList<>();

    public CityInfo() {
    }

    public CityInfo(Integer zipCode, String cityName) {
        this.zipCode = zipCode;
        this.cityName = cityName;
    }


    public Integer getZipCode() {
        return zipCode;
    }

    public void setZipCode(Integer zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityInfo)) return false;
        CityInfo cityInfo = (CityInfo) o;
        return getZipCode().equals(cityInfo.getZipCode());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getZipCode());
    }

    @Override
    public String toString() {
        return "CityInfo{" +
                "zipCode=" + zipCode +
                ", cityName='" + cityName + '\'' +
                ", addresses=" + addresses +
                '}';
    }
}