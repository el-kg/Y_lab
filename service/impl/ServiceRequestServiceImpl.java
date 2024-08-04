package service.impl;

import model.ServiceRequest;
import model.User;
import model.enums.ServiceRequestStatus;
import repository.ServiceRequestRepository;
import service.ServiceRequestService;

import java.util.List;
import java.util.Optional;

public class ServiceRequestServiceImpl implements ServiceRequestService {

        private ServiceRequestRepository serviceRequestRepository;

        public ServiceRequestServiceImpl(ServiceRequestRepository serviceRequestRepository) {
            this.serviceRequestRepository = serviceRequestRepository;
        }

        public void createServiceRequest(ServiceRequest request) {
            serviceRequestRepository.addServiceRequest(request);
        }

        public List<ServiceRequest> findServiceRequestsByUser(User user) {
            return serviceRequestRepository.findServiceRequestsByUser(user);
        }

        public List<ServiceRequest> getAllServiceRequests() {
            return serviceRequestRepository.getAllServiceRequests();
        }

        public Optional<ServiceRequest> findServiceRequestById(int requestId) {
            return serviceRequestRepository.findServiceRequestById(requestId);
        }

        public void updateServiceRequest(ServiceRequest request) {
            serviceRequestRepository.updateServiceRequest(request);
        }

        public void completeServiceRequest(ServiceRequest request) {
            request.setStatus(ServiceRequestStatus.COMPLETED);
            serviceRequestRepository.updateServiceRequest(request);
        }
    }

