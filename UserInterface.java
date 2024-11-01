 public static void createBook(Scanner scanner, BookDao bookDao, ShelfDao shelfDao) {
        try {
            System.out.print("Enter book title: ");
            String title = scanner.nextLine();

            System.out.print("Enter ISBN code: ");
            String isbnCode = scanner.nextLine();

            System.out.print("Enter edition: ");
            int edition = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter publication year (YYYY-MM-DD): ");
            LocalDate publicationYear = LocalDate.parse(scanner.nextLine());

            System.out.print("Enter publisher name: ");
            String publisherName = scanner.nextLine();

            System.out.print("Enter status (e.g., AVAILABLE, CHECKED_OUT): ");
            EBook_status status = EBook_status.valueOf(scanner.nextLine().toUpperCase());

            System.out.print("Enter shelf ID: ");
            String shelfIdInput = scanner.nextLine().trim();

            // Validate and convert shelf ID to UUID
            UUID shelfId = UUID.fromString(shelfIdInput);

            // Retrieve the shelf from the database
            Shelf shelf = shelfDao.findShelfById(shelfId);
            if (shelf == null) {
                System.out.println("No shelf found with the given ID.");
                return;
            }

            // Create a new Book instance
            Book newBook = new Book(
                    UUID.randomUUID(), // Generate a new UUID for the book
                    status,
                    edition,
                    isbnCode,
                    publicationYear,
                    publisherName,
                    shelf,
                    title
            );

            // Save the new book using the DAO
            bookDao.saveBook(newBook); // Pass the entire Book object

            // Confirm the book was created
            System.out.println("Book created successfully: " + newBook.getTitle());
        } catch (Exception e) {
            System.out.println("Error during book creation: " + e.getMessage());
            // Optionally log the error for further analysis
        }


    }


    public static void createShelf(Scanner scanner, ShelfDao shelfDao, RoomDao roomDao) {
        try {
            System.out.print("Enter book category: ");
            String bookCategory = scanner.nextLine();

            System.out.print("Enter initial stock: ");
            int initialStock = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter available stock: ");
            int availableStock = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter borrowed number: ");
            int borrowedNumber = Integer.parseInt(scanner.nextLine());

            System.out.print("Enter room ID: ");
            String roomIdInput = scanner.nextLine().trim();

            UUID roomId = UUID.fromString(roomIdInput);
            Room room = roomDao.findRoomById(roomId);
            if (room == null) {
                System.out.println("No room found with the given ID.");
                return;
            }

            Shelf newShelf = new Shelf();
            newShelf.setShelfId(UUID.randomUUID());
            newShelf.setBookCategory(bookCategory);
            newShelf.setInitialStock(initialStock);
            newShelf.setAvailableStock(availableStock);
            newShelf.setBorrowedNumber(borrowedNumber);
            newShelf.setRoom(room);

            String result = shelfDao.saveShelf(newShelf);
            System.out.println(result);
        } catch (Exception e) {
            System.out.println("Error during shelf creation: " + e.getMessage());
        }

    }
