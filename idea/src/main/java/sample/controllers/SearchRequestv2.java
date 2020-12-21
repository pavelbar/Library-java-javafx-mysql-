package sample.controllers;

public class SearchRequestv2 {
    private String id;
    private String userid;
    private String bookid;
    private String startdate;
    private String enddate;
    private String office;

    public SearchRequestv2(String id, String userid, String bookid, String startdate, String enddate, String office) {
        System.out.println("SearchRequestv2 startdate:" + startdate);
        this.id = id;
        this.userid = userid;
        this.bookid = bookid;
        this.startdate = startdate;
        this.enddate = enddate;
        this.office = office;
    }

    public String getEnddate() {
        return enddate;
    }

    public void setEnddate(String enddate) {
        this.enddate = enddate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getBookid() {
        return bookid;
    }

    public void setBookid(String bookid) {
        this.bookid = bookid;
    }

    public String getStartdate() {
        return startdate;
    }

    public void setStartdate(String startdate) {
        this.startdate = startdate;
    }

    public String getOffice() {
        return office;
    }

    public void setOffice(String office) {
        this.office = office;
    }
}
