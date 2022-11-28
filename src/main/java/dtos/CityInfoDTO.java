package dtos;

import entities.CityInfo;
import entities.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CityInfoDTO {

    private int zipCode;
    private String cityName;

    public CityInfoDTO(int zipCode, String cityName) {
        this.zipCode = zipCode;
        this.cityName = cityName;
    }

    public CityInfoDTO(CityInfo cityInfo){
        this.zipCode = cityInfo.getZipCode();
        this.cityName = cityInfo.getCityName();
    }

    public static List<CityInfoDTO> getCityInfoDTOs(List<CityInfo> cityInfos){
        List<CityInfoDTO> cityInfoDTOs = new ArrayList<>();
        cityInfos.forEach(cityInfo->cityInfoDTOs.add(new CityInfoDTO(cityInfo)));
        return cityInfoDTOs;
    }

    public CityInfo getEntity(){
        return new CityInfo(this.zipCode,this.cityName);
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CityInfoDTO)) return false;
        CityInfoDTO that = (CityInfoDTO) o;
        return getZipCode() == that.getZipCode();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getZipCode());
    }

    @Override
    public String toString() {
        return "CityInfoDTO{" +
                "zipCode=" + zipCode +
                ", cityName='" + cityName + '\'' +
                '}';
    }
}
