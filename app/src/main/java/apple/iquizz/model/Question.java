package apple.iquizz.model;

/**
 * Created by fabiengauthe on 08/03/2016.
 */
public class Question {

    private int id;
    private String question;
    private int idReponseVrai;
    private int idTheme;

    public Question(int id, String question, int idReponseVrai, int idTheme) {
        this.id = id;
        this.question = question;
        this.idReponseVrai = idReponseVrai;
        this.idTheme = idTheme;
    }

    public int getId(){return id;}

    public String getQuestion() { return question;}

    public int getIdReponseVrai() { return idReponseVrai;}

    public int getIdTheme() { return idTheme;}
}
