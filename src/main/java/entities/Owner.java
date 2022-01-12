package entities;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Table(name = "owner")
@Entity
@NamedQuery(name = "owner.deleteAllRows", query = "DELETE from Owner")
public class Owner
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id", nullable = false)

    private int owner_id;
    private String name;
    private String address;
    private int phone;

    public Owner()
    {
    }


//    @OneToMany(mappedBy = "owner", cascade = CascadeType.PERSIST, orphanRemoval = true)
//    private List<BoatOwner> boats = new ArrayList<>();

    @ManyToMany
    private List<Boat> boatList = new ArrayList<>();

    public List<Boat> getBoatList()
    {
        return boatList;
    }

    public void setBoatList(List<Boat> boatList)
    {
        this.boatList = boatList;
    }

    public Owner(String name, String address, int phone)
    {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }


//    public List<BoatOwner> getBoats()
//    {
//        return boats;
//    }
//
//    public void setBoats(List<BoatOwner> boats)
//    {
//        this.boats = boats;
//    }


    public void setAddress(String address)
    {
        this.address = address;
    }

    public void setPhone(int phone)
    {
        this.phone = phone;
    }



    public String getAddress()
    {
        return address;
    }

    public int getPhone()
    {
        return phone;
    }

    public int getOwner_id()
    {
        return owner_id;
    }

    public void setOwner_id(int owner_id)
    {
        this.owner_id = owner_id;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "(" +
                "owner_id = " + owner_id + ", " +
                "name = " + name + ", " +
                "address = " + address + ", " +
                "phone = " + phone + ")";
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return phone == owner.phone && name.equals(owner.name) && address.equals(owner.address);
    }

    @Override
    public int hashCode()
    {
        return 0;
    }
}