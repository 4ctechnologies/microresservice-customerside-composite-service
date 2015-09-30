package be.foreseegroup.micro.resourceservice.customersidecomposite.service;

import be.foreseegroup.micro.resourceservice.customersidecomposite.model.AssignmentAggregated;
import be.foreseegroup.micro.resourceservice.customersidecomposite.model.Consultant;
import be.foreseegroup.micro.resourceservice.customersidecomposite.model.ConsultantAggregated;
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
public class ConsultantCompositeIntegration {
    private static final Logger LOG = LoggerFactory.getLogger(CustomerCompositeIntegration.class);

    private RestTemplate restTemplate = new RestTemplate();

    @Autowired
    private ServiceUtils util;

    @Autowired
    private AssignmentCompositeIntegration assignmentCompositeIntegration;

    /**
     * @todo:
     * Hystrix is also using the fallbackMethod when the person resource returns (intended) 4xx errors, e.g. 404 not found
     */

    public ResponseEntity<Iterable<ConsultantAggregated>> getAllAggregatedConsultants() {
        ResponseEntity<Iterable<Consultant>> response = getAllConsultants();
        Iterable<Consultant> consultants = response.getBody();
        Iterable<ConsultantAggregated> aggregatedConsultants = aggregateConsultants(consultants);
        return new ResponseEntity<Iterable<ConsultantAggregated>>(aggregatedConsultants, response.getHeaders(), response.getStatusCode());
    }

    @HystrixCommand(fallbackMethod = "consultantsFallback")
    public ResponseEntity<Iterable<Consultant>> getAllConsultants() {
        LOG.debug("Will call getAllConsultants with Hystrix protection");

        URI uri = util.getServiceUrl("consultant");
        String url = uri.toString() + "/consultants";
        LOG.debug("getAllConsultants from URL: {}", url);

        ParameterizedTypeReference<Iterable<Consultant>> responseType = new ParameterizedTypeReference<Iterable<Consultant>>() {};
        ResponseEntity<Iterable<Consultant>> response = restTemplate.exchange(url, HttpMethod.GET, null, responseType);
        return response;
    }

    public ResponseEntity<ConsultantAggregated> getAggregatedConsultantById(String consultantId) {
        ResponseEntity<Consultant> response = getConsultantById(consultantId);
        Consultant consultant = response.getBody();
        ConsultantAggregated aggregatedConsultant = aggregateConsultant(consultant);
        return new ResponseEntity<ConsultantAggregated>(aggregatedConsultant, response.getHeaders(), response.getStatusCode());
    }

    @HystrixCommand(fallbackMethod = "consultantFallback")
    public ResponseEntity<Consultant> getConsultantById(String consultantId) {
        LOG.debug("Will call getConsultantById with Hystrix protection");

        URI uri = util.getServiceUrl("consultant");
        String url = uri.toString() + "/consultants/"+consultantId;
        LOG.debug("getConsultantById from URL: {}", url);

        ResponseEntity<Consultant> consultant = restTemplate.getForEntity(url, Consultant.class);
        return consultant;
    }

    @HystrixCommand(fallbackMethod = "consultantFallback")
    public ResponseEntity<Consultant> createConsultant(Consultant consultant) {
        LOG.debug("Will call createConsultant with Hystrix protection");

        URI uri = util.getServiceUrl("consultant");
        String url = uri.toString() + "/consultants";
        LOG.debug("createConsultant from URL: {}", url);

        ResponseEntity<Consultant> resultConsultant = restTemplate.postForEntity(url, consultant, Consultant.class);
        return resultConsultant;
    }

    @HystrixCommand(fallbackMethod = "consultantFallback")
    public ResponseEntity<Consultant> updateConsultant(String consultantId, Consultant consultant, HttpHeaders headers) {
        LOG.debug("Will call updateConsultant with Hystrix protection");

        URI uri = util.getServiceUrl("consultant");
        String url = uri.toString() + "/consultants/"+consultantId;
        LOG.debug("updateConsultant from URL: {}", url);

        HttpEntity<Consultant> requestEntity = new HttpEntity<>(consultant, headers);
        ResponseEntity<Consultant> resultConsultant = restTemplate.exchange(url, HttpMethod.PUT, requestEntity, Consultant.class);
        return resultConsultant;
    }

    @HystrixCommand(fallbackMethod = "consultantFallback")
    public ResponseEntity<Consultant> deleteConsultant(String consultantId, HttpHeaders headers) {
        LOG.debug("Will call deleteConsultant with Hystrix protection");

        URI uri = util.getServiceUrl("consultant");
        String url = uri.toString() + "/consultants/"+consultantId;
        LOG.debug("deleteConsultants from URL: {}", url);

        HttpEntity<Consultant> requestEntity = new HttpEntity<>(headers);
        ResponseEntity<Consultant> resultConsultant = restTemplate.exchange(url, HttpMethod.DELETE, requestEntity, Consultant.class);
        return resultConsultant;
    }


    ResponseEntity<Consultant> consultantFallback(String identifier) {
        return consultantFallback();
    }
    ResponseEntity<Consultant> consultantFallback(Consultant consultant) {
        return consultantFallback();
    }
    ResponseEntity<Consultant> consultantFallback(String identifier, Consultant consultant, HttpHeaders headers) {
        return consultantFallback();
    }
    ResponseEntity<Consultant> consultantFallback(String identifier, HttpHeaders headers) {
        return consultantFallback();
    }

    ResponseEntity<Consultant> consultantFallback() {
        LOG.warn("Using fallback method for consultant-service");
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }

    ResponseEntity<Iterable<Consultant>> consultantsFallback() {
        LOG.warn("Using fallback method for consultant-service");
        return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
    }


    private Iterable<ConsultantAggregated> aggregateConsultants(Iterable<Consultant> consultants) {
        ArrayList<ConsultantAggregated> aggregatedConsultants = new ArrayList<ConsultantAggregated>();

        for (Consultant consultant : consultants) {
            aggregatedConsultants.add(aggregateConsultant(consultant));
        }

        return aggregatedConsultants;
    }

    private ConsultantAggregated aggregateConsultant(Consultant consultant) {
        ConsultantAggregated aggregatedConsultant = new ConsultantAggregated(consultant);


        String consultantId = consultant.getId();

        ResponseEntity<Iterable<AssignmentAggregated>> assignmentResponse = assignmentCompositeIntegration.getAggregatedAssignmentsByConsultantId(consultantId);
        if (assignmentResponse.getStatusCode() == HttpStatus.OK) {
            Iterable<AssignmentAggregated> assignments = assignmentResponse.getBody();
            aggregatedConsultant.setAssignments(assignments);
        }

        return aggregatedConsultant;
    }
}
