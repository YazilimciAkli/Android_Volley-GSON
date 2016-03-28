package myfirstjson.com.myfirstjson;

public class Kelime {

    private String tr;

    private String en;

    public String getTr ()
    {
        return tr;
    }
    public void setTr (String tr)
    {
        this.tr = tr;
    }

    public String getEn ()
    {
        return en;
    }

    public void setEn (String en)
    {
        this.en = en;
    }

    @Override
    public String toString()
    {
        return tr + "\n" + en;
    }
}