package entity;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ksiazka")
public class Ksiazka {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private  String tytul;


    @ManyToOne(cascade ={CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "autor_id")
    private  Autor autor;



    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "ksiazka_id")
    private List<Recenzja> recenzje = new ArrayList<>();



    //konstruktory

    public Ksiazka(){

    }

    public Ksiazka(String tytul){
        this.tytul=tytul;

    }

    //gettery i settery


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public Autor getAutor() {
        return autor;
    }

    public void setAutor(Autor autor) {
        this.autor = autor;
    }

    public List<Recenzja> getRecenzje() {
        return recenzje;
    }

    public void setRecenzje(List<Recenzja> recenzje) {
        this.recenzje = recenzje;
    }


    //toString()

    @Override
    public String toString() {
        return  "Id: " + id + " Tytuł: " + tytul;
    }

    //metody własne
    public void dodajRecenzje(Recenzja recenzja){
        this.recenzje.add(recenzja);
    }



}
