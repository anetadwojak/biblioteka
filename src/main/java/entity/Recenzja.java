package entity;


import javax.persistence.*;

@Entity
@Table(name = "recenzja")
public class Recenzja {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String opinia;

    //konstruktory

    public Recenzja(){

    }

    public Recenzja(String opinia){
        this.opinia=opinia;
    }

    //gettery i settery


    public String getOpinia() {
        return opinia;
    }

    public void setOpinia(String opinia) {
        this.opinia = opinia;
    }

    @Override
    public String toString() {
        return "Recenzja{" +
                "opinia='" + opinia + '\'' +
                '}';
    }
}
