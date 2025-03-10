package models;

public class Address
{
    private Integer id;
    private String address;

    public Address()
    {

    }

    public Address(String address)
    {
        this.address = address;
    }

    public Address(Integer id, String address)
    {
        this.id = id;
        this.address = address;
    }

    public Integer getId()
    {
        return id;
    }

    public void setId(Integer id)
    {
        this.id = id;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    @Override
    public String toString()
    {
        return address;
    }
}
