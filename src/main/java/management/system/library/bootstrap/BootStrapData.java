//package management.system.library.bootstrap;
//
//import management.system.library.model.Author;
//import management.system.library.model.Book;
//
//import management.system.library.repositories.AuthorRepository;
//import management.system.library.repositories.BookRepository;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//
//// Dependency injection to author repository and book repository
//@Component
//public class BootStrapData implements CommandLineRunner {
//
//    private final AuthorRepository authorRepository;
//    private final BookRepository bookRepository;
//
//    public BootStrapData(AuthorRepository authorRepository, BookRepository bookRepository) {
//        this.authorRepository = authorRepository;
//        this.bookRepository = bookRepository;
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//
//        Author joanneRowling = new Author(){
//
//        }
//        Book harryPotter1 = new Book("Harry Potter and the Philosopher's Stone", "9788869183157");
//
//        joanneRowling.getBooks().add(harryPotter1);
//        harryPotter1.getAuthors().add(joanneRowling);
//
//        authorRepository.save(joanneRowling);
//        bookRepository.save(harryPotter1);
//
//        Author johnKatzenbach = new Author("John", "Katzenbach");
//        Book analyst = new Book("The Analyst", "9780345455482");
//        johnKatzenbach.getBooks().add(analyst);
//        analyst.getAuthors().add(johnKatzenbach);
//
//        authorRepository.save(johnKatzenbach);
//        bookRepository.save(analyst);
//
//        System.out.println("Number of Books in the Library: " + bookRepository.count());
//        System.out.println("Number of Authors in the Library: " + authorRepository.count());
//
//    }
//}
