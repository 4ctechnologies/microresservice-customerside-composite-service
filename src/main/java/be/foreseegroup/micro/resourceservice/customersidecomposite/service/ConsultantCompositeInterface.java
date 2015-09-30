package be.foreseegroup.micro.resourceservice.customersidecomposite.service;

import be.foreseegroup.micro.resourceservice.customersidecomposite.model.Consultant;
import be.foreseegroup.micro.resourceservice.customersidecomposite.model.ConsultantAggregated;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * Created by Kaj on 29/09/15.
 */
public interface ConsultantCompositeInterface {
    ResponseEntity<Iterable<ConsultantAggregated>> getAllAggregatedConsultants();
    ResponseEntity<Iterable<Consultant>> getAllConsultants();
    ResponseEntity<ConsultantAggregated> getAggregatedConsultantById(String consultantId);
    ResponseEntity<Consultant> getConsultantById(String consultantId);
    ResponseEntity<Consultant> createConsultant(Consultant consultant);
    ResponseEntity<Consultant> updateConsultant(String consultantId, Consultant consultant, HttpHeaders headers);
    ResponseEntity<Consultant> deleteConsultant(String consultantId, HttpHeaders headers);
}