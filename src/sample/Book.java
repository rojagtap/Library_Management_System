package sample;

import java.time.LocalDate;

public class Book implements Comparable<Book>{

    private String uid,name,author,issueDate,returnDate,availability,issuer;

    public Book() {
        this.uid=this.name=this.author=this.issueDate=this.returnDate=this.issuer="N/A";
        this.availability="yes";
    }

    Book(String uid, String name, String author, String issueDate,
         String returnDate, String availability, String issuer){

        this.uid=uid;
        this.name=name;
        this.author=author;
        this.issueDate=issueDate;
        this.returnDate=returnDate;
        this.availability=availability;
        this.issuer=issuer;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public String getIssueDate() {
        return issueDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public String getAvailability() {
        return availability;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public void setAvailability(String availability) {
        this.availability = availability;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public int compareTo(Book b) {
        if (b.returnDate.equals("N/A")) {
            if (this.returnDate.equals("N/A")){
                return 0;
            }
            return -1;
        } else if (this.returnDate.equals("N/A")) {
            return 1;
        } else {
            return LocalDate.parse(this.returnDate, AppController.FORMATTER)
                    .compareTo(LocalDate.parse(b.returnDate, AppController.FORMATTER));
        }
    }
}
