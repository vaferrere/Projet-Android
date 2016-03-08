package apple.iquizz.model;

/**
 * Created by fabiengauthe on 08/03/2016.
 */
public class Theme {

    private int id;
    private String theme;

    public Theme(int id, String theme)
    {
        this.theme = theme;
        this.id = id;
    }

    public int getId() {return id;}

    public String getTheme() {return theme;}
}
