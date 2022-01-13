package entities;

import javax.persistence.*;

@Table(name = "car")
@Entity
@NamedQuery(name = "car.deleteAllRows", query = "DELETE from Car ")
public class Car
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "registration_number", nullable = false)
    private int registrationNumber;
    private String brand;
    private String make;
    private int year;
    private String user;

    public Car()
    {
    }


    public Car(int registrationNumber, String brand, String make, int year, String user)
    {
        this.registrationNumber = registrationNumber;
        this.brand = brand;
        this.make = make;
        this.year = year;
        this.user = user;
    }

    public String getBrand()
    {
        return brand;
    }

    public void setBrand(String brand)
    {
        this.brand = brand;
    }

    public String getMake()
    {
        return make;
    }

    public void setMake(String make)
    {
        this.make = make;
    }

    public int getYear()
    {
        return year;
    }

    public void setYear(int year)
    {
        this.year = year;
    }

    public int getRegistrationNumber()
    {
        return registrationNumber;
    }

    public void setRegistrationNumber(int registrationNumber)
    {
        this.registrationNumber = registrationNumber;
    }

    public String getUser()
    {
        return user;
    }

    public void setUser(String user)
    {
        this.user = user;
    }
}