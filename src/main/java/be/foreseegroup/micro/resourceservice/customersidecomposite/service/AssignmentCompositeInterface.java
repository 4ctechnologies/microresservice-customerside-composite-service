package be.foreseegroup.micro.resourceservice.customersidecomposite.service;

import be.foreseegroup.micro.resourceservice.customersidecomposite.model.Assignment;
import be.foreseegroup.micro.resourceservice.customersidecomposite.model.AssignmentAggregated;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;

/**
 * Created by Kaj on 29/09/15.
 */
public interface AssignmentCompositeInterface {
    ResponseEntity<Iterable<AssignmentAggregated>> getAllAggregatedAssignments();
    ResponseEntity<Iterable<Assignment>> getAllAssignments();
    ResponseEntity<AssignmentAggregated> getAggregatedAssignmentById(String assignmentId);

    ResponseEntity<Iterable<AssignmentAggregated>> getAggregatedAssignmentsByConsultantId(String consultantId);
    ResponseEntity<Iterable<Assignment>> getAssignmentsByConsultantId(String consultantId);
    ResponseEntity<Iterable<AssignmentAggregated>> getAggregatedAssignmentsByCustomerId(String customerId);
    ResponseEntity<Iterable<Assignment>> getAssignmentsByCustomerId(String customerId);

    ResponseEntity<Assignment> getAssignmentById(String assignmentId);
    ResponseEntity<Assignment> createAssignment(Assignment assignment);
    ResponseEntity<Assignment> updateAssignment(String assignmentId, Assignment assignment, HttpHeaders headers);
    ResponseEntity<Assignment> deleteAssignment(String assignmentId, HttpHeaders headers);
}
