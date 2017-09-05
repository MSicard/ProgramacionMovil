package com.iteso.sesion5.Beans;

/**
 * Created by Maritza on 01/09/2017.
 */

public class Student {
    private String name;
    private String phone;
    private String scholarship;
    private String gender;
    private String book;
    private String sport;

    public Student() {
        this.name = "";
        this.phone = "";
        this.scholarship = "Bachillerato";
        this.gender = "Femenino";
        this.sport = "No";
    }

    @Override
    public String toString() {
        if(book.isEmpty()){
            return "Nombre: " + name + "\n" +
                    "Teléfono: " + phone + "\n" +
                    "Escolaridad: " + scholarship + "\n" +
                    "Género: " + gender + "\n" +
                    "Practica Deporte: " + sport;
        }
         else return  "Nombre: " + name + "\n" +
                "Teléfono: " + phone + "\n" +
                "Escolaridad: " + scholarship + "\n" +
                "Género: " + gender + "\n" +
                "Libro favorito: " + book + "\n" +
                "Practica Deporte: " + sport;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getScholarship() {
        return scholarship;
    }

    public void setScholarship(String scholarship) {
        this.scholarship = scholarship;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String isSport() {
        return sport;
    }

    public void setSport(String sport) {
        this.sport = sport;
    }
}
