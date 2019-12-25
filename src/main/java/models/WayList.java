package models;

public class WayList
{
    private String position;
    private Box box;
    private Integer count;

    public WayList()
    {
    }

    public WayList(String position, Box box, Integer count)
    {
        this.position = position;
        this.box = box;
        this.count = count;
    }

    public String getPosition()
    {
        return position;
    }

    public void setPosition(String position)
    {
        this.position = position;
    }

    public Box getBox()
    {
        return box;
    }

    public void setBox(Box box)
    {
        this.box = box;
    }

    public Integer getCount()
    {
        return count;
    }

    public void setCount(Integer count)
    {
        this.count = count;
    }
}
