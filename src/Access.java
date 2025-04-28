class Book {
    private String title;  // private variable

    public void setTitle(String t) {
        title = t;
    }

    public String getTitle() {
        return title;
    }
}

public class Access {
    public static void main(String[] args) {
        Book myBook = new Book();
        myBook.setTitle("The Alchemist");
        System.out.println(myBook.getTitle());
    }
}
