package apple.iquizz.model;

/**
 * Created by fabiengauthe on 08/03/2016.
 */
public class Reponse {

    private int id;
    private String reponse;
    private int idQuestion;

    public Reponse(int id,String reponse, int idQuestion)
    {
        this.id = id;
        this.reponse = reponse;
        this.idQuestion = idQuestion;
    }

    public int getId() {return id;}

    public String getReponse() {return reponse;}

    public int getIdQuestion() {return idQuestion;}
}
