package entities;

import dtos.BoatDTO;

import javax.persistence.*;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Table(name = "boat")
@Entity
@NamedQuery(name = "boat.deleteAllRows", query = "DELETE from Boat")
public class Boat
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boat_id", nullable = false)
    private int boat_id;
    private String brand;
    private String make;
    private String name;
    private byte[] image;


    public Boat()
    {
    }

//    @OneToMany(mappedBy = "boat", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    private List<BoatOwner> owners = new ArrayList<>();

    @JoinColumn(name = "Harbour_id")
    @ManyToOne
    private Harbour harbour;

    public Boat(String brand, String make, String name)
    {
        this.brand = brand;
        this.make = make;
        this.name = name;
    }


    public Boat(int boat_id, String brand, String make, String name, byte[] image)
    {
        this.boat_id = boat_id;
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
    }

    public Boat(BoatDTO boatDTO){
        this.boat_id = boatDTO.getDto_boatId();
        this.brand = boatDTO.getDto_brand();
        this.make = boatDTO.getDto_make();
        this.name = boatDTO.getDto_name();
    }


@ManyToMany(mappedBy = "boatList")
private List<Owner> ownerList;

    public List<Owner> getOwnerList()
    {
        return ownerList;
    }

    public void setOwnerList(List<Owner> ownerList)
    {
        this.ownerList = ownerList;
    }

    //    public List<BoatOwner> getOwners()
//    {
//        return owners;
//    }
//
//    public void setOwners(List<BoatOwner> owners)
//    {
//        this.owners = owners;
//    }


    public byte[] getImage()
    {
        return image;
    }

    public void setImage(byte[] image)
    {
        this.image = image;
    }

    public Harbour getHarbour()
    {
        return harbour;
    }

    public void setHarbour(Harbour harbour)
    {
        this.harbour = harbour;
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

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public int getBoat_id()
    {
        return boat_id;
    }

    public void setBoat_id(int boat_id)
    {
        this.boat_id = boat_id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Boat boat = (Boat) o;
        return boat_id == boat.boat_id && brand.equals(boat.brand) && make.equals(boat.make) && name.equals(boat.name) && Arrays.equals(image, boat.image) && Objects.equals(harbour, boat.harbour) && ownerList.equals(boat.ownerList);
    }

    @Override
    public int hashCode()
    {
        int result = Objects.hash(boat_id, brand, make, name, harbour, ownerList);
        result = 31 * result + Arrays.hashCode(image);
        return result;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "(" +
                "boat_id = " + boat_id + ", " +
                "brand = " + brand + ", " +
                "make = " + make + ", " +
                "name = " + name + ", " +
                "image = " + image + ", " +
                "harbour = " + harbour + ")";
    }
}