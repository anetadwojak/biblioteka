package com.example.demo;


import entity.Autor;
import entity.Ksiazka;
import entity.Recenzja;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;


import java.util.List;
import java.util.Scanner;

public class DemoApplication {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

       String wybor, tytul, imieAutora, nazwiskoAutora, opinia;
       int idKsiazki, idAutora;


        while(true){
            SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(Ksiazka.class)
                    .addAnnotatedClass(Autor.class)
                    .addAnnotatedClass(Recenzja.class)
                    .buildSessionFactory();

            boolean a=true;

            System.out.println("---------- Witaj w Bibliotece ----------");
            System.out.println("Wybierz opcję: ");
            System.out.println("1 - Dodaj książkę, 2 - Wyświetl książki dostępne w bazie, 3 - Wyszukaj, 4 - Dodaj recenzję, 5 - Usuń, 6 - Koniec");

            wybor=sc.nextLine();

            if(wybor.equals("1")){
                System.out.println("Podaj tytuł książki: ");
                tytul=sc.nextLine().toUpperCase();
                System.out.println("Podaj imie autora: ");
                imieAutora=sc.nextLine().toUpperCase();
                System.out.println("Podaj nazwisko autora: ");
                nazwiskoAutora=sc.nextLine().toUpperCase();

                Session session = factory.getCurrentSession();

                session.beginTransaction();

               List<Autor> autorzy =  session.createQuery("from Autor").getResultList();


                    for (Autor x : autorzy) {
                        if (imieAutora.equals(x.getImie()) && nazwiskoAutora.equals(x.getNazwisko())) {
                            idAutora = x.getId();
                            Autor autor = session.get(Autor.class, idAutora);
                            Ksiazka ksiazka = new Ksiazka(tytul);
                            ksiazka.setAutor(autor);
                            session.save(ksiazka);
                            a = false;
                            break;
                        }
                    }

                if(a==true){
                    Autor autor = new Autor(imieAutora, nazwiskoAutora);
                    session.save(autor);
                    Ksiazka ksiazka = new Ksiazka(tytul);
                    ksiazka.setAutor(autor);
                    session.save(ksiazka);
                }

                session.getTransaction().commit();

                session.close();


            }else if(wybor.equals("2")){
                System.out.println("Lista książek dostępnych w naszej bazie: ");

                Session session = factory.getCurrentSession();
                session.beginTransaction();


                List<Ksiazka> ksiazki =  session.createQuery("from Ksiazka").getResultList();

                for(Ksiazka x : ksiazki){
                   System.out.println("ID KSIĄŻKI: "+ x.getId()+ " Tytuł: "+ x.getTytul() + " Autor: " + x.getAutor());
                }

                session.getTransaction().commit();
                session.close();


            }else if(wybor.equals("3")){
                System.out.println("Wybierz wyszukiwanie: ");
                System.out.println("A - wyszukiwanie książek danego autora, B - wyszukiwanie książki na podstawie jej tytułu ");
                wybor=sc.nextLine().toUpperCase();

                if(wybor.equals("A")) {

                    Session session = factory.getCurrentSession();
                    session.beginTransaction();


                    System.out.println("Podaj dane autora, którego listę książek chcesz wyświetlić.");
                    System.out.println("Podaj imie autora: ");
                    imieAutora = sc.nextLine().toUpperCase();
                    System.out.println("Podaj nazwisko autora: ");
                    nazwiskoAutora = sc.nextLine().toUpperCase();

                    Query query = session.createQuery("from Autor a where a.imie = :im and a.nazwisko = :naz");
                    query.setString("im", imieAutora);
                    query.setString("naz", nazwiskoAutora);
                    Autor result = (Autor) query.uniqueResult();
                    List <Ksiazka> ksiazki = result.getKsiazki();
                    for(Ksiazka x: ksiazki){
                        System.out.println(x);
                    }

                    session.getTransaction().commit();
                    session.close();

                }else if(wybor.equals("B")) {
                    Session session = factory.getCurrentSession();
                    session.beginTransaction();

                    System.out.println("Podaj tytuł książki, której szukasz: ");
                    tytul = sc.nextLine().toUpperCase();
                    Query query = session.createQuery("from Ksiazka k where k.tytul = :tytul");
                    query.setString("tytul", tytul);
                    Ksiazka ksiazka = (Ksiazka) query.uniqueResult();
                    System.out.println("ID: " + ksiazka.getId() + " Tytuł: " + ksiazka.getTytul() + " Autor: " + ksiazka.getAutor());
                    List<Recenzja> recenzje = ksiazka.getRecenzje();
                    if (recenzje.size() == 0) {
                        System.out.println("Brak opinii o książce!");
                    }else{
                        System.out.println("Opinie o książce: ");
                        for (Recenzja x : recenzje) {
                            System.out.println(x);
                        }
                    }
                }

            }else if(wybor.equals("4")){

                Session session = factory.getCurrentSession();
                session.beginTransaction();


                List<Ksiazka> ksiazki =  session.createQuery("from Ksiazka").getResultList();

                for(Ksiazka x : ksiazki){
                    System.out.println("ID KSIĄŻKI: "+ x.getId()+ " Tytuł: "+ x.getTytul() + " Autor: " + x.getAutor());
                }
                System.out.println("Podaj ID książki, którą chcesz ocenić: ");
                idKsiazki=sc.nextInt();
                sc.nextLine();
                System.out.println("Napisz swoją opinię: ");
                opinia=sc.nextLine();
                Recenzja recenzja = new Recenzja(opinia);
                session.save(recenzja);

                Ksiazka ksiazka = session.get(Ksiazka.class, idKsiazki);
                ksiazka.dodajRecenzje(recenzja);

                session.getTransaction().commit();
                session.close();

            }else if(wybor.equals("5")){
                System.out.println("Wybierz opcję: ");
                System.out.println("A - usuwanie autora (usunięte zostaną, także jego książki, B - usuwanie wybranej książki)");
                wybor=sc.nextLine().toUpperCase();
                if(wybor.equals("A")){
                    
                    System.out.println("Podaj id autora, którego chcesz usunąć: ");
                    idAutora=sc.nextInt();

                   Session session = factory.getCurrentSession();
                    session.beginTransaction();
                    Autor autor = session.get(Autor.class, idAutora);
                    session.delete(autor);

                    session.getTransaction().commit();
                    session.close();
                }else if(wybor.equals("B")){
                    System.out.println("Podaj tytuł książki, którą chcesz usunąć: ");
                    tytul=sc.nextLine().toUpperCase();
                    Session session = factory.getCurrentSession();
                    session.beginTransaction();
                    Query q = session.createQuery("from Ksiazka k where k.tytul= :tytul");
                    q.setString("tytul", tytul);
                    Ksiazka ksiazka =(Ksiazka) q.uniqueResult();
                    session.delete(ksiazka);
                    session.getTransaction().commit();
                    session.close();
                }


            }else if(wybor.equals("6")){
                break;
            }

            factory.close();


        }


    }

}
