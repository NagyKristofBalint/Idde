package edu.bbte.idde.nkim2061.desktop;

import edu.bbte.idde.nkim2061.server.controller.exception.BadRequestException;
import edu.bbte.idde.nkim2061.server.controller.exception.NotFoundException;
import edu.bbte.idde.nkim2061.server.controller.RealEstateAdController;
import edu.bbte.idde.nkim2061.server.dto.incoming.RealEstateAdCreationDTO;
import edu.bbte.idde.nkim2061.server.dto.incoming.RealEstateAdUpdateDTO;
import edu.bbte.idde.nkim2061.server.model.RealEstateAd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Date;
import java.util.Scanner;

public class DesktopApplication {
    private final Scanner scanner;
    private final RealEstateAdController realEstateAdController;

    private final Logger logger = LoggerFactory.getLogger(DesktopApplication.class);

    public DesktopApplication() {
        scanner = new Scanner(System.in);
        realEstateAdController = new RealEstateAdController();
    }

    private RealEstateAd readRealEstateAd() {
        logger.info("Please enter the date:");
        final String dateInput = scanner.next();
        final Date date = Date.valueOf(dateInput);
        logger.info("Enter the description:");
        final String description = scanner.next();
        logger.info("Enter the address:");
        final String address = scanner.next();
        logger.info("Enter the city:");
        final String city = scanner.next();
        logger.info("Enter the price:");
        final float price = scanner.nextFloat();
        return new RealEstateAd(date, description, address, city, price);
    }

    private RealEstateAdCreationDTO readRealEstateAdCreationDTO() {
        RealEstateAd realEstateAd = readRealEstateAd();
        return new RealEstateAdCreationDTO(realEstateAd.getDate(), realEstateAd.getDescription(),
                realEstateAd.getAddress(), realEstateAd.getCity(), realEstateAd.getPrice());
    }

    private RealEstateAdUpdateDTO readRealEstateAdUpdateDTO() {
        logger.info("Enter the id:");
        Long id = scanner.nextLong();
        RealEstateAd realEstateAd = readRealEstateAd();

        return new RealEstateAdUpdateDTO(id, realEstateAd.getDate(), realEstateAd.getDescription(),
                realEstateAd.getAddress(),realEstateAd.getCity(), realEstateAd.getPrice());
    }

    void run() {
        logger.info("Welcome to the Real Estate Ad Application!");
        logger.info("Please select an option:");
        logger.info("1. List all ads");
        logger.info("2. Add a new ad");
        logger.info("3. Delete an ad");
        logger.info("4. Find an ad by id");
        logger.info("5. Update an ad");
        logger.info("6. Exit");

        int choice;
        do {
            logger.info("Please select an option:");
            choice = scanner.nextInt();
            try {
                switch (choice) {
                    case 1:
                        realEstateAdController.findAll().forEach(System.out::println);
                        break;
                    case 2:
                        realEstateAdController.save(readRealEstateAdCreationDTO());
                        break;
                    case 3:
                        logger.info("Enter the id of the ad to delete:");
                        long id = scanner.nextLong();
                        realEstateAdController.deleteById(id);
                        break;
                    case 4:
                        logger.info("Enter the id of the ad to find:");
                        id = scanner.nextLong();
                        logger.info(realEstateAdController.findById(id).toString());
                        break;
                    case 5:
                        logger.info("Enter the id of the ad to update:");
                        realEstateAdController.findById(scanner.nextLong());
                        realEstateAdController.update(readRealEstateAdUpdateDTO());
                        break;
                    default:
                        logger.info("Invalid choice!");
                        break;
                }
            } catch (NotFoundException | IllegalArgumentException | BadRequestException e) {
                logger.warn(e.getMessage());
            }
        } while (choice != 6);
        scanner.close();
    }

    public static void main(String[] args) {
        DesktopApplication desktopApplication = new DesktopApplication();
        desktopApplication.run();
    }
}
