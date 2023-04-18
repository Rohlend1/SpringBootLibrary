package rohlend.spring.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.Date;
import java.util.concurrent.TimeUnit;


@Entity
@Table(name = "book")
public class Book {
    @Id
    @Column(name = "book_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @NotNull(message = "Name should not be null")
    @NotEmpty(message = "Name should not be empty")
    @Column(name = "name")
    private String name;

    @NotNull(message = "Author's name should not be null")
    @NotEmpty(message = "Author's name should not be empty")
    @Column(name = "author")
    private String author;

    @Max(value = 2023,message = "Year can't be more than now")
    @NotNull(message = "Author's name should not be null")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "owner_id",referencedColumnName = "person_id")
    private Person owner;

    @Column(name = "date_of_taking")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfTaking;
    @Transient
    private boolean isExpired;
    @Transient
    private String ownerName;

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public void setExpired(boolean expired) {
        isExpired = expired;
    }

    public Book(String name, String author, int year, Date dateOfTaking) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.dateOfTaking = dateOfTaking;
    }

    public void checkTime(){
        Date currentDate = new Date();
        long diffInMillies = Math.abs(currentDate.getTime() - dateOfTaking.getTime());
        long diff = TimeUnit.DAYS.convert(diffInMillies, TimeUnit.MILLISECONDS);
        isExpired = diff>10;
    }

    public Date getDateOfTaking() {
        return dateOfTaking;
    }

    public void setDateOfTaking(Date dateOfTaking) {
        this.dateOfTaking = dateOfTaking;
    }

    public Person getOwner() {
        return owner;
    }

    public void setOwner(Person owner) {
        this.owner = owner;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Book() {
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                ", owner=" + owner +
                '}';
    }
}
