# SEG3502 — Laboratoire 7 — Book Store Authors

## Authors

- Andrianina Rakotobe(300428674)
- Darcy-Joel Kabura(300432705)

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

A Java Development Kit (JDK) 20 or newer is required for the server. No database setup is needed:
the server uses an in-memory H2 database that is seeded with sample authors and books at startup.

## Usage

Always start the server first, then the client — the Authors page gets its data from the REST API,
so the client has nothing to display until the API is up.

1. Start the REST API server (from the `server` folder):

   ```bash
   ./gradlew bootRun
   ```

   The API is served at `http://localhost:8080/books-api` and its documentation at
   `http://localhost:8080/swagger-ui.html`. 

2. Then start the development server (from the `client` folder):

   ```bash
   npm start
   ```

   The application is served at `http://localhost:4200`;(port may change if occupied) the Authors page is at
   `http://localhost:4200/authors`. Search for author `1`, `2` or `3` to see the details, and any
   other number to see the not-found message.


