package be.foreseegroup.micro.resourceservice.customersidecomposite.service;

import be.foreseegroup.micro.resourceservice.customersidecomposite.model.Assignment;
import be.foreseegroup.micro.resourceservice.customersidecomposite.model.AssignmentAggregated;
import be.foreseegroup.micro.resourceservice.customersidecomposite.model.Consultant;
import be.foreseegroup.micro.resourceservice.customersidecomposite.model.Customer;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;

/**
 * Created by Kaj on 29/09/15.
 */
@Component
public class AssignmentCompositeIntegration {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerCompositeIntegration.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ServiceUtils util;

    @Autowired
    private ConsultantCompositeIntegration consultantIntegration;

    @Autowired
    private CustomerCompositeIntegration customerIntegration;


    /**
     * @todo:
     * Hystrix is also using the fallbackMethod when the person resource returns (intended) 4xx errors, e.g. 404 not found
     */

    public ResponseEntity<Iterable<AssignmentAggregated>> getAllAggregatedAssignments() {
        ResponseEntity<Iterable<Assignment>> response = getAllAssignments();
        Iterable<Assignment> assignments = response.getBody();
        Iterable<AssignmentAggregated> aggregatedAssignments = aggregateAssignments(assignments);
        return new ResponseEntity<Iterable<AssignmentAggregated>>(aggregatedAssignments, response.getHeaders(), response.getStatusCode());
    }

    @HystrixCommand(fallbackMethod = "assignmentsFallback")
    public ResponseEntity<Iterable<Assignment>> getAllAssignments() {
        LOG.debug("Will call getAllAssignments with Hystrix protection");

        URI uri = util.getServiceUrl("assignment");
        String url = uri.toString() + "/assignments";
        LOG.debug("getAllAssignments from URL: {}", url);

        ParameterizedTypeReference<Iterable<Assignment>> responseType = new ParameterizedTypeReference<Iterable<Assignment>>() {};
        ResponseEntity<Iterable<Assignment>> assignments = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return assignments;
    }

    public ResponseEntity<AssignmentAggregated> getAggregatedAssignmentById(String assignmentId) {
        ResponseEntity<Assignment> response = getAssignmentById(assignmentId);
        Assignment assignment = response.getBody();
        AssignmentAggregated aggregateAssignment = aggregateAssignment(assignment);
        return new ResponseEntity<AssignmentAggregated>(aggregateAssignment, response.getHeaders(), response.getStatusCode());
    }

    @HystrixCommand(fallbackMethod = "assignmentFallback")
    public ResponseEntity<Assignment> getAssignmentById(String assignmentId) {
        LOG.debug("Will call getAssignmentById with Hystrix protection");

        URI uri = util.getServiceUrl("assignment");
        String url = uri.toString() + "/assignments/"+assignmentId;
        LOG.debug("getAssignmentByid from URL: {}", url);

        ResponseEntity<Assignment> assignment = restTemplate.getForEntity(url, Assignment.class);
        return assignment;
    }

    @HystrixCommand(fallbackMethod = "assignmentFallback")
    public ResponseEntity<Assignment> createAssignment(Assignment assignment) {
        LOG.debug("Will call createAssignment with Hystrix protection");

        URI uri = util.getServiceUrl("assignment");
        String url = uri.toString() + "/assignments";
        LOG.debug("createAssignment from URL: {}", url);

        ResponseEntity<Assignment> resultAssignment = restTemplate.postForEntity(url, assignment, Assignment.class);
        return resultAssignment;
    }

    @HystrixCommand(fallbackMethod = "assignmentFallback")
    public ResponseEntity<Assignment> updateAssignment(String assignmentId ,Assignment assignment, HttpHeaders headers) {
        LOG.debug("Will call updateAssignment with Hystrix protection");

        URI uri = util.getServiceUrl("assignment");
        String url = uri.toString() + "/assignments/"+assignmentId;
        LOG.debug("updateAssignment from URL: {}", url);

        HttpEntity<Assignment> requestEntity = new HttpEntity<>(assignment, headers);
        ResponseEntity<Assignment> resultAssignment = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Assignment.class);
        return resultAssignment;
    }

    @HystrixCommand(fallbackMethod = "assignmentFallback")
    public ResponseEntity<Assignment> deleteAssignment(String assignmentId, HttpHeaders headers) {
        LOG.debug("Will call deleteAssignment with Hystrix protection");

        URI uri = util.getServiceUrl("assignment");
        String url = uri.toString() + "/assignments/"+assignmentId;
        LOG.debug("deleteAssignment from URL: {}", url);

        HttpEntity<Assignment> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Assignment> resultAssignment = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Assignment.class);
        return resultAssignment;
    }

    public ResponseEntity<Iterable<AssignmentAggregated>> getAggregatedAssignmentsByConsultantId(String consultantId) {
        ResponseEntity<Iterable<Assignment>> response = getAssignmentsByConsultantId(consultantId);
        Iterable<Assignment> assignments = response.getBody();
        Iterable<AssignmentAggregated> aggregatedAssignments = aggregateAssignments(assignments);
        return new ResponseEntity<Iterable<AssignmentAggregated>>(aggregatedAssignments, response.getHeaders(), response.getStatusCode());
    }

    @HystrixCommand(fallbackMethod = "assignmentsFallback")
    public ResponseEntity<Iterable<Assignment>> getAssignmentsByConsultantId(String consultantId) {
        LOG.debug("Will call getAssignmentsByConsultantId with Hystrix protection");

        URI uri = util.getServiceUrl("assignment");
        String url = uri.toString() + "/assignmentsbycid/"+consultantId;
        LOG.debug("getAssignmentsByConsultantId from URL: {}", url);

        ParameterizedTypeReference<Iterable<Assignment>> responseType = new ParameterizedTypeReference<Iterable<Assignment>>() {};
        ResponseEntity<Iterable<Assignment>> assignments = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return assignments;
    }

    public ResponseEntity<Iterable<AssignmentAggregated>> getAggregatedAssignmentsByCustomerId(String customerId) {
        ResponseEntity<Iterable<Assignment>> response = getAssignmentsByCustomerId(customerId);
        Iterable<Assignment> assignments = response.getBody();
        Iterable<AssignmentAggregated> aggregatedAssignments = aggregateAssignments(assignments);
        return new ResponseEntity<Iterable<AssignmentAggregated>>(aggregatedAssignments, response.getHeaders(), response.getStatusCode());
    }

    @HystrixCommand(fallbackMethod = "assignmentsFallback")
    public ResponseEntity<Iterable<Assignment>> getAssignmentsByCustomerId(String customerId) {
        LOG.debug("Will call getAssignmentsByCustomerId with Hystrix protection");

        URI uri = util.getServiceUrl("assignment");
        String url = uri.toString() + "/assignmentsbycuid/"+customerId;
        LOG.debug("getAssignmentsByCustomerId from URL: {}", url);

        ParameterizedTypeReference<Iterable<Assignment>> responseType = new ParameterizedTypeReference<Iterable<Assignment>>() {};
        ResponseEntity<Iterable<Assignment>> assignments = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return assignments;
    }


    ResponseEntity<Assignment> assignmentFallback(String identifier) {
        return assignmentFallback();
    }
    ResponseEntity<Assignment> assignmentFallback(Assignment assignment) {
        return assignmentFallback();
    }
    ResponseEntity<Assignment> assignmentFallback(String identifier, Assignment assignment, HttpHeaders headers) {
        return assignmentFallback();
    }
    ResponseEntity<Assignment> assignmentFallback(String identifier, HttpHeaders headers) {
        return assignmentFallback();
    }

    ResponseEntity<Assignment> assignmentFallback() {
        LOG.warn("Using fallback method for assignment-service");
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    ResponseEntity<Iterable<Assignment>> assignmentsFallback(String identifier) {
        return assignmentsFallback();
    }
    ResponseEntity<Iterable<Assignment>> assignmentsFallback() {
        LOG.warn("Using fallback method for assignment-service");
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }



    private Iterable<AssignmentAggregated> aggregateAssignments(Iterable<Assignment> assignments) {
        ArrayList<AssignmentAggregated> aggregatedAssignments = new ArrayList<AssignmentAggregated>();

        for (Assignment assignment : assignments) {
            aggregatedAssignments.add(aggregateAssignment(assignment));
        }

        return aggregatedAssignments;
    }


    private AssignmentAggregated aggregateAssignment(Assignment assignment) {
        AssignmentAggregated aggregatedAssignment = new AssignmentAggregated(assignment);

        String customerId = assignment.getCustomerId();
        String consultantId = assignment.getConsultantId();

        ResponseEntity<Customer> customerResponse = customerIntegration.getCustomerById(customerId);
        if (customerResponse.getStatusCode() == HttpStatus.OK) {
            Customer customer = customerResponse.getBody();
            aggregatedAssignment.setCustomerName(customer.getName());
        }

        ResponseEntity<Consultant> consultantResponse = consultantIntegration.getConsultantById(consultantId);
        if (consultantResponse.getStatusCode() == HttpStatus.OK) {
            Consultant consultant = consultantResponse.getBody();
            String consultantFirstname = consultant.getFirstname();
            String consultantLastname = consultant.getLastname();
            String consultantName = consultantFirstname + " " + consultantLastname;
            aggregatedAssignment.setConsultantName(consultantName);
        }

        return aggregatedAssignment;
    }

}
