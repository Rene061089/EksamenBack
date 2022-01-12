package dtos;

//import entities.BoatOwner;
import entities.Owner;

public class OwnerDTO
{
    private int dto_owner_id;
    private String dto_name;
    private String dto_address;
    private int dto_phone;


    public OwnerDTO(Owner o)
    {
     this.dto_owner_id = o.getOwner_id();
     this.dto_name = o.getName();
     this.dto_address =o.getAddress();
     this.dto_phone = o.getPhone();

    }



    public int getDto_owner_id()
    {
        return dto_owner_id;
    }

    public void setDto_owner_id(int dto_owner_id)
    {
        this.dto_owner_id = dto_owner_id;
    }

    public String getDto_name()
    {
        return dto_name;
    }

    public void setDto_name(String dto_name)
    {
        this.dto_name = dto_name;
    }

    public String getDto_address()
    {
        return dto_address;
    }

    public void setDto_address(String dto_address)
    {
        this.dto_address = dto_address;
    }

    public int getDto_phone()
    {
        return dto_phone;
    }

    public void setDto_phone(int dto_phone)
    {
        this.dto_phone = dto_phone;
    }
}
