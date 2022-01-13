package dtos;

public class CarDTO
{
    private int dto_registrationNumber;
    private String dto_brand;
    private String dto_make;
    private int dto_year;
    private String dto_user;


    public CarDTO(int dto_registrationNumber, String dto_brand, String dto_make, int dto_year, String dto_user)
    {
        this.dto_registrationNumber = dto_registrationNumber;
        this.dto_brand = dto_brand;
        this.dto_make = dto_make;
        this.dto_year = dto_year;
        this.dto_user = dto_user;
    }

    public CarDTO(String dto_brand, String dto_make, int dto_year, String dto_user)
    {
        this.dto_brand = dto_brand;
        this.dto_make = dto_make;
        this.dto_year = dto_year;
        this.dto_user = dto_user;
    }

    public int getDto_registrationNumber()
    {
        return dto_registrationNumber;
    }

    public void setDto_registrationNumber(int dto_registrationNumber)
    {
        this.dto_registrationNumber = dto_registrationNumber;
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

    public int getDto_year()
    {
        return dto_year;
    }

    public void setDto_year(int dto_year)
    {
        this.dto_year = dto_year;
    }

    public String getDto_user()
    {
        return dto_user;
    }

    public void setDto_user(String dto_user)
    {
        this.dto_user = dto_user;
    }
}
