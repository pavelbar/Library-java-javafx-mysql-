package sample;

public class SearchRequest {
    private String book_id;
    private String book_name;
    private String book_janre;
    private String book_author_id;
    private String book_publisher;
    private String book_pages;
    private String book_ISBN;
    private String book_balance;

    public SearchRequest(String book_id, String book_name, String book_janre, String book_author_id, String book_publisher, String book_pages, String book_ISBN, String book_balance) {
        this.book_id = book_id;
        this.book_name = book_name;
        System.out.println("search2 EB:" + book_name);
        this.book_janre = book_janre;
        this.book_author_id = book_author_id;
        this.book_publisher = book_publisher;
        this.book_pages = book_pages;
        this.book_ISBN = book_ISBN;
        this.book_balance = book_balance;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_janre() {
        return book_janre;
    }

    public void setBook_janre(String book_janre) {
        this.book_janre = book_janre;
    }

    public String getBook_author_id() {
        return book_author_id;
    }

    public void setBook_author_id(String book_author_id) {
        this.book_author_id = book_author_id;
    }

    public String getBook_publisher() {
        return book_publisher;
    }

    public void setBook_publisher(String book_publisher) {
        this.book_publisher = book_publisher;
    }

    public String getBook_pages() {
        return book_pages;
    }

    public void setBook_pages(String book_pages) {
        this.book_pages = book_pages;
    }

    public String getBook_ISBN() {
        return book_ISBN;
    }

    public void setBook_ISBN(String book_ISBN) {
        this.book_ISBN = book_ISBN;
    }

    public String getBook_balance() {
        return book_balance;
    }

    public void setBook_balance(String book_balance) {
        this.book_balance = book_balance;
    }
}
