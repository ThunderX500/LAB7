package seg3x02.booksrestapi.config

import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import seg3x02.booksrestapi.entities.Author
import seg3x02.booksrestapi.entities.Bio
import seg3x02.booksrestapi.entities.Book
import seg3x02.booksrestapi.entities.Order
import seg3x02.booksrestapi.repository.AuthorRepository
import seg3x02.booksrestapi.repository.BookRepository

/**
 * Populates the database with sample authors, books, bios and orders at
 * startup. The in-memory H2 database is empty on every run, so this is what
 * gives the API something to return.
 *
 * Seeding happens here rather than in a data.sql script because the script
 * initializer runs before Hibernate creates the schema unless
 * spring.jpa.defer-datasource-initialization is set; going through the
 * repositories avoids that ordering problem and lets Hibernate generate the ids.
 */
@Component
class DataSeeder(
    private val authorRepository: AuthorRepository,
    private val bookRepository: BookRepository
) : ApplicationRunner {
    private val log = LoggerFactory.getLogger(DataSeeder::class.java)

    @Transactional
    override fun run(args: ApplicationArguments) {
        if (authorRepository.count() > 0) {
            log.info("Database already contains authors - skipping seed data")
            return
        }

        val nineteenEightyFour = book(
            "1984", "Fiction", "978-0451524935", 12.99, 1949,
            "Roman dystopique sur une societe sous surveillance totale.",
            orders = listOf(3)
        )
        val animalFarm = book(
            "Animal Farm", "Fiction", "978-0451526342", 9.99, 1945,
            "Fable animaliere sur la trahison des revolutions."
        )
        val prideAndPrejudice = book(
            "Pride and Prejudice", "Romance", "978-0141439518", 11.50, 1813,
            "Comedie de moeurs sur Elizabeth Bennet et M. Darcy.",
            orders = listOf(1)
        )
        val kafkaOnTheShore = book(
            "Kafka on the Shore", "Fiction", "978-1400079278", 15.75, 2002,
            "Recit onirique melant deux destins paralleles."
        )
        val norwegianWood = book(
            "Norwegian Wood", "Fiction", "978-0375704024", 14.25, 1987,
            "Roman d'apprentissage sur la memoire et la perte."
        )

        author(
            "George", "Orwell",
            "Ecrivain et journaliste britannique, connu pour ses romans politiques et dystopiques.",
            listOf(nineteenEightyFour, animalFarm)
        )
        author(
            "Jane", "Austen",
            "Romanciere anglaise dont les oeuvres depeignent la societe de la petite noblesse rurale.",
            listOf(prideAndPrejudice)
        )
        author(
            "Haruki", "Murakami",
            "Ecrivain japonais contemporain, traduit dans une cinquantaine de langues.",
            listOf(kafkaOnTheShore, norwegianWood)
        )

        authorRepository.findAll().forEach {
            log.info("Seeded author {} - {} {} ({} book(s))", it.id, it.firstName, it.lastName, it.books.size)
        }
    }

    /** Books are saved first: Author.books owns the join table but does not cascade. */
    private fun book(
        title: String,
        category: String,
        isbn: String,
        cost: Double,
        year: Int,
        description: String,
        orders: List<Int> = emptyList()
    ): Book {
        val book = Book()
        book.title = title
        book.category = category
        book.isbn = isbn
        book.cost = cost
        book.year = year
        book.description = description
        orders.forEach { quantity ->
            val order = Order()
            order.quantity = quantity
            book.orders.add(order)
        }
        return bookRepository.save(book)
    }

    /** Author.bio cascades ALL, so the Bio is persisted along with the author. */
    private fun author(firstName: String, lastName: String, biodata: String, books: List<Book>): Author {
        val author = Author()
        author.firstName = firstName
        author.lastName = lastName
        author.bio = Bio().apply { this.biodata = biodata }
        author.books.addAll(books)
        val saved = authorRepository.save(author)
        books.forEach { it.authors.add(saved) }
        return saved
    }
}
