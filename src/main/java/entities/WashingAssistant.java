package entities;

import javax.persistence.*;

@Table(name = "washing_assistant")
@Entity
@NamedQuery(name = "washing_assistant.deleteAllRows", query = "DELETE from WashingAssistant ")
public class WashingAssistant
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wa_id", nullable = false)
    private int wa_id;
    private String name;
    private String primaryLanguage;
    private int yearsOfXP;
    private int priceHour;

    public WashingAssistant()
    {
    }

    public WashingAssistant(String name, String primaryLanguage, int yearsOfXP, int priceHour)
    {
        this.name = name;
        this.primaryLanguage = primaryLanguage;
        this.yearsOfXP = yearsOfXP;
        this.priceHour = priceHour;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPrimaryLanguage()
    {
        return primaryLanguage;
    }

    public void setPrimaryLanguage(String primaryLanguage)
    {
        this.primaryLanguage = primaryLanguage;
    }

    public int getYearsOfXP()
    {
        return yearsOfXP;
    }

    public void setYearsOfXP(int yearsOfXP)
    {
        this.yearsOfXP = yearsOfXP;
    }

    public int getPriceHour()
    {
        return priceHour;
    }

    public void setPriceHour(int priceHour)
    {
        this.priceHour = priceHour;
    }

    public int getWa_id()
    {
        return wa_id;
    }

    public void setWa_id(int wa_id)
    {
        this.wa_id = wa_id;
    }
}