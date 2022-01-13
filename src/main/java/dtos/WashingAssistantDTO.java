package dtos;

import entities.WashingAssistant;

public class WashingAssistantDTO
{
    private int dto_wa_id;
    private String dto_name;
    private String dto_primaryLanguage;
    private int dto_yearsOfXP;
    private int dto_priceHour;


    public WashingAssistantDTO(int dto_wa_id, String dto_name, String dto_primaryLanguage, int dto_yearsOfXP, int dto_priceHour)
    {
        this.dto_wa_id = dto_wa_id;
        this.dto_name = dto_name;
        this.dto_primaryLanguage = dto_primaryLanguage;
        this.dto_yearsOfXP = dto_yearsOfXP;
        this.dto_priceHour = dto_priceHour;
    }

    public WashingAssistantDTO(String dto_name, String dto_primaryLanguage, int dto_yearsOfXP, int dto_priceHour)
    {
        this.dto_name = dto_name;
        this.dto_primaryLanguage = dto_primaryLanguage;
        this.dto_yearsOfXP = dto_yearsOfXP;
        this.dto_priceHour = dto_priceHour;
    }

    public WashingAssistantDTO(WashingAssistant wa)
    {
        this.dto_wa_id = wa.getWa_id();
        this.dto_name = wa.getName();
        this.dto_primaryLanguage = wa.getPrimaryLanguage();
        this.dto_yearsOfXP = wa.getYearsOfXP();
        this.dto_priceHour = wa.getPriceHour();
    }

    public int getDto_wa_id()
    {
        return dto_wa_id;
    }

    public void setDto_wa_id(int dto_wa_id)
    {
        this.dto_wa_id = dto_wa_id;
    }

    public String getDto_name()
    {
        return dto_name;
    }

    public void setDto_name(String dto_name)
    {
        this.dto_name = dto_name;
    }

    public String getDto_primaryLanguage()
    {
        return dto_primaryLanguage;
    }

    public void setDto_primaryLanguage(String dto_primaryLanguage)
    {
        this.dto_primaryLanguage = dto_primaryLanguage;
    }

    public int getDto_yearsOfXP()
    {
        return dto_yearsOfXP;
    }

    public void setDto_yearsOfXP(int dto_yearsOfXP)
    {
        this.dto_yearsOfXP = dto_yearsOfXP;
    }

    public int getDto_priceHour()
    {
        return dto_priceHour;
    }

    public void setDto_priceHour(int dto_priceHour)
    {
        this.dto_priceHour = dto_priceHour;
    }
}


