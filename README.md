# SEG3502 — Laboratoire 7 — Book Store Authors

## Authors

- Andrianina Rakotobe(300428674)
- Darcy-Joel Kabura(300432705)

## What was done

The Book Store application was extended with a complete Authors lookup feature:

- An **Authors** link was added to the navigation menu and an `authors` route to the router.
- An author ID form was built with validation (a required, positive integer; the **Search** button
  stays disabled while the form is invalid).
- Submitting the form calls `GET /books-api/authors/{id}` on the REST API through `BooksService`.
- When the author exists, the page displays their ID, first name, last name and the titles of their
  books. When the ID is unknown (the API answers `404`), the page displays a not-found message
  instead.

## Installation

1. Clone the repository and move into the project folder:

   ```bash
   git clone https://github.com/ThunderX500/LAB7
   cd LAB7
   ```

2. Install the client dependencies:

   ```bash
   cd client
   npm install
   ```

3. The server needs a MySQL database named `booksDb` listening on `localhost:6033` with the user
   `root` and the password `root` (see `server/src/main/resources/application.properties`), and a
   Java 21 toolchain.

## Usage

- Start the REST API server (from the `server` folder):

  ```bash
  ./gradlew bootRun
  ```

  The API is served at `http://localhost:8080/books-api` and its documentation at
  `http://localhost:8080/swagger-ui.html`.

- Start the development server (from the `client` folder):

  ```bash
  npm start
  ```

  The application is served at `http://localhost:4200`; the Authors page is at
  `http://localhost:4200/authors`.

- Run the unit tests (Karma + Jasmine):

  ```bash
  npm test
  ```
