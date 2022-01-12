package dtos;

import entities.Harbour;

public class HarbourDTO
{

    private int dto_harbour_id;
    private String dto_name;
    private String dto_address;
    private int dto_capacity;



    public HarbourDTO(Harbour h)
    {
        this.dto_harbour_id = h.getHarbour_id();
        this.dto_name = h.getName();
        this.dto_address = h.getAddress();
        this.dto_capacity = h.getCapacity();

    }


    public int getDto_harbour_id()
    {
        return dto_harbour_id;
    }

    public void setDto_harbour_id(int dto_harbour_id)
    {
        this.dto_harbour_id = dto_harbour_id;
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

    public int getDto_capacity()
    {
        return dto_capacity;
    }

    public void setDto_capacity(int dto_capacity)
    {
        this.dto_capacity = dto_capacity;
    }


}
