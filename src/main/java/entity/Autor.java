package entity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autor")
public class Autor {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String imie;

    @Column
    private String nazwisko;


    @OneToMany(mappedBy = "autor", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Ksiazka> ksiazki = new ArrayList<>();


    //konstruktory

    public Autor(){

    }

    public Autor(String imie, String nazwisko){
        this.imie=imie;
        this.nazwisko=nazwisko;
    }


    //gettery i settery


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }


    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public List<Ksiazka> getKsiazki() {
        return ksiazki;
    }

    public void setKsiazki(List<Ksiazka> ksiazki) {
        this.ksiazki = ksiazki;
    }
    //toString()

    @Override
    public String toString() {
        return  imie + " " + nazwisko;
    }


    //metody własne

    public void dodajKsiazke(Ksiazka ksiazka){
        this.ksiazki.add(ksiazka);
    }




}
