package entities;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Table(name = "harbour")
@Entity
@NamedQuery(name = "harbour.deleteAllRows", query = "DELETE from Harbour ")
public class Harbour
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "harbour_id", nullable = false)
    private int harbour_id;
    private String name;
    private String address;
    private int capacity;
    @OneToMany(mappedBy = "harbour", cascade = CascadeType.PERSIST)
    private List<Boat> boats;

    public Harbour()
    {
    }


    public Harbour(String name, String address, int capacity)
    {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public List<Boat> getBoats()
    {
        return boats;
    }

    public void setBoats(List<Boat> boats)
    {
        this.boats = boats;
    }

    public void addBoat(Boat boat)
    {
       this.boats.add(boat);
       if (boat != null)
       {
           boat.setHarbour(this);
       }
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public int getCapacity()
    {
        return capacity;
    }

    public void setCapacity(int capacity)
    {
        this.capacity = capacity;
    }

    public int getHarbour_id()
    {
        return harbour_id;
    }

    public void setHarbour_id(int harbour_id)
    {
        this.harbour_id = harbour_id;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Harbour harbour = (Harbour) o;
        return Objects.equals(harbour_id, harbour.harbour_id);
    }

    @Override
    public int hashCode()
    {
        return 0;
    }

    @Override
    public String toString()
    {
        return getClass().getSimpleName() + "(" +
                "harbour_id = " + harbour_id + ", " +
                "name = " + name + ", " +
                "address = " + address + ", " +
                "capacity = " + capacity + ")";
    }
}