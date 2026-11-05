package seg3x02.booksrestapi.config

import org.springframework.boot.autoconfigure.orm.jpa.HibernatePropertiesCustomizer
import org.springframework.context.annotation.Configuration

/**
 * Book.year maps to a column named "year", which H2 2.x treats as a reserved
 * word; without quoting, the "create table book" DDL fails with a syntax error
 * (Hibernate only logs it as a warning, so the application starts with the
 * table missing). Quoting every reserved keyword keeps the entities and the
 * datasource configuration untouched, and works the same way on MySQL.
 */
@Configuration
class HibernateKeywordConfig : HibernatePropertiesCustomizer {
    override fun customize(hibernateProperties: MutableMap<String, Any>) {
        hibernateProperties["hibernate.auto_quote_keyword"] = true
    }
}
