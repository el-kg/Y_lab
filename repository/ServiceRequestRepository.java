package repository;

import model.ServiceRequest;
import model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServiceRequestRepository {
    private List<ServiceRequest> serviceRequests = new ArrayList<>();
    private int nextId = 1;

    public void addServiceRequest(ServiceRequest request) {
        request.setId(nextId++);
        serviceRequests.add(request);
    }

    public List<ServiceRequest> findServiceRequestsByUser(User user) {
        return serviceRequests.stream()
                .filter(request -> request.getUser().equals(user))
                .collect(Collectors.toList());
    }

    public List<ServiceRequest> getAllServiceRequests() {
        return new ArrayList<>(serviceRequests);
    }

    public Optional<ServiceRequest> findServiceRequestById(int requestId) {
        return serviceRequests.stream()
                .filter(request -> request.getId() == requestId)
                .findFirst();
    }

    public void updateServiceRequest(ServiceRequest updatedRequest) {
        for (int i = 0; i < serviceRequests.size(); i++) {
            if (serviceRequests.get(i).getId() == updatedRequest.getId()) {
                serviceRequests.set(i, updatedRequest);
                return;
            }
        }
    }
}