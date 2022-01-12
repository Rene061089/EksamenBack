package dtos;

import entities.Boat;

import javax.persistence.CascadeType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

public class BoatDTO
{

    private int dto_boatId;
    private String dto_brand;
    private String dto_make;
    private String dto_name;
    //    private String dto_image;


    public BoatDTO(int dto_boatId, String dto_brand, String dto_make, String dto_name)
    {
        this.dto_boatId = dto_boatId;
        this.dto_brand = dto_brand;
        this.dto_make = dto_make;
        this.dto_name = dto_name;
    }

    public BoatDTO(String dto_brand, String dto_make, String dto_name)
    {
        this.dto_brand = dto_brand;
        this.dto_make = dto_make;
        this.dto_name = dto_name;
    }

    public BoatDTO(Boat b)
    {
        this.dto_boatId = b.getBoat_id();
        this.dto_brand = b.getBrand();
        this.dto_make = b.getMake();
        this.dto_name = b.getName();
    }



    public int getDto_boatId()
    {
        return dto_boatId;
    }

    public void setDto_boatId(int dto_boatId)
    {
        this.dto_boatId = dto_boatId;
    }

    public String getDto_brand()
    {
        return dto_brand;
    }

    public void setDto_brand(String dto_brand)
    {
        this.dto_brand = dto_brand;
    }

    public String getDto_make()
    {
        return dto_make;
    }

    public void setDto_make(String dto_make)
    {
        this.dto_make = dto_make;
    }

    public String getDto_name()
    {
        return dto_name;
    }

    public void setDto_name(String dto_name)
    {
        this.dto_name = dto_name;
    }
}
