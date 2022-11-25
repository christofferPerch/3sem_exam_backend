package dtos;

import entities.Address;
import entities.CityInfo;
import entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AddressDTO {

    private Integer id;
    private String streetAddress;
    private CityInfoDTO cityInfo;
    private List<User> users = new ArrayList<>();

    public AddressDTO(Integer id, String streetAddress, CityInfoDTO cityInfo, List<User> users) {
        this.id = id;
        this.streetAddress = streetAddress;
        this.cityInfo = cityInfo;
        this.users = users;
    }

    public AddressDTO(Address address){
        if(address.getId() != null && address.getId() != 0){
            this.id = address.getId();
        }
        this.streetAddress = address.getStreetAddress();
        this.cityInfo = new CityInfoDTO(address.getZipcode());
        this.users = address.getUsers(); //Might have to be changed
    }

    public static List<AddressDTO> getAddressDTOs(List<Address> addresses){
        List<AddressDTO> addressDTOs = new ArrayList<>();
        addresses.forEach(address->addressDTOs.add(new AddressDTO(address)));
        return addressDTOs;
    }

    public Address getEntity(){
        Address address = new Address();
        if(this.id != 0){
            address.setId(id);
        }
        address.setStreetAddress(streetAddress);
        address.setZipcode(this.cityInfo.getEntity());
        //Maybe return users as well
        return address;
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

    public CityInfoDTO getCityInfo() {
        return cityInfo;
    }

    public void setCityInfo(CityInfoDTO cityInfo) {
        this.cityInfo = cityInfo;
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
        if (!(o instanceof AddressDTO)) return false;
        AddressDTO that = (AddressDTO) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AddressDTO{" +
                "id=" + id +
                ", streetAddress='" + streetAddress + '\'' +
                ", cityInfo=" + cityInfo +
                ", users=" + users +
                '}';
    }
}
