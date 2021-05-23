package ua.kpi.comsys.IO8323;

public class Book {
    private String title;
    private String subtitle;
    private String isbn13;
    private String price;
    private String image;
    private String authors;
    private String publisher;
    private int pages;
    private int year;
    private int rating;
    private String desc;
    public Book(String title, String subtitle, String isbn13, String price, String image){
        this.title = title;
        this.subtitle = subtitle;
        this.isbn13 = isbn13;
        this.price = price;
        this.image = image;
    }
    public Book(String title, String subtitle, String authors, String publisher, String isbn13,
                int pages, int year, int rating, String desc, String price, String image){
        this.title = title;
        this.subtitle = subtitle;
        this.authors = authors;
        this.publisher = publisher;
        this.isbn13 = isbn13;
        this.pages = pages;
        this.year = year;
        if (rating > 5){
            this.rating = 5;
        } else if (rating < 1){
            this.rating = 1;
        } else {
            this.rating = rating;
        }
        this.desc = desc;
        this.price = price;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getIsbn13() {
        return isbn13;
    }

    public void setIsbn13(String isbn13) {
        this.isbn13 = isbn13;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAuthors() {
        return authors;
    }

    public void setAuthors(String authors) {
        this.authors = authors;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
