package controller;

import model.Car;
import model.ServiceRequest;
import model.User;
import model.enums.ServiceRequestStatus;
import service.impl.CarServiceImpl;
import service.impl.ServiceRequestServiceImpl;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class ServiceRequestController {
    private ServiceRequestServiceImpl serviceRequestService;
    private CarServiceImpl carService;
    private Scanner scanner;

    public ServiceRequestController(ServiceRequestServiceImpl serviceRequestService, CarServiceImpl carService, Scanner scanner) {
        this.serviceRequestService = serviceRequestService;
        this.carService = carService;
        this.scanner = scanner;
    }

    public void createServiceRequest(User client) {
        System.out.println("Enter car make:");
        String make = scanner.nextLine();
        System.out.println("Enter car model:");
        String model = scanner.nextLine();

        Optional<Car> car = carService.findByMakeAndModel(make, model);
        if (car.isPresent()) {
            ServiceRequest request = new ServiceRequest(client, car.get());
            serviceRequestService.createServiceRequest(request);
            System.out.println("Service request created successfully!");
        } else {
            System.out.println("Car not found!");
        }
    }

    public void viewServiceRequests(User client) {
        List<ServiceRequest> requests = serviceRequestService.findServiceRequestsByUser(client);
        for (ServiceRequest request : requests) {
            System.out.println(request);
        }
    }

    public void processServiceRequests() {
        List<ServiceRequest> requests = serviceRequestService.getAllServiceRequests();
        requests.forEach(System.out::println);

        System.out.println("Enter service request ID to manage:");
        int requestId = scanner.nextInt();
        scanner.nextLine(); // consume newline

        Optional<ServiceRequest> requestOpt = serviceRequestService.findServiceRequestById(requestId);
        if (requestOpt.isPresent()) {
            ServiceRequest request = requestOpt.get();
            System.out.println("1. Change status");
            System.out.println("2. Complete request");

            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    System.out.println("Enter new status (PENDING, IN_PROGRESS, COMPLETED, CANCELLED):");
                    ServiceRequestStatus status = ServiceRequestStatus.valueOf(scanner.nextLine().toUpperCase());
                    request.setStatus(status);
                    serviceRequestService.updateServiceRequest(request);
                    System.out.println("Service request status updated successfully!");
                    break;
                case 2:
                    serviceRequestService.completeServiceRequest(request);
                    System.out.println("Service request completed successfully!");
                    break;
            }
        } else {
            System.out.println("Service request not found!");
        }
    }
}

