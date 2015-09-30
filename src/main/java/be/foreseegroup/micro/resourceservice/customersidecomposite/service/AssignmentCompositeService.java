package be.foreseegroup.micro.resourceservice.customersidecomposite.service;

import be.foreseegroup.micro.resourceservice.customersidecomposite.model.Assignment;
import be.foreseegroup.micro.resourceservice.customersidecomposite.model.AssignmentAggregated;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Kaj on 29/09/15.
 */
@RestController
public class AssignmentCompositeService {
    private static final Logger LOG = LoggerFactory.getLogger(AssignmentCompositeService.class);

    @Autowired
    AssignmentCompositeIntegration assignmentIntegration;

    @RequestMapping(method = RequestMethod.GET, value="/oassignments")
    public ResponseEntity<Iterable<Assignment>> getAllAssignments() {
        return assignmentIntegration.getAllAssignments();
    }

    @RequestMapping(method = RequestMethod.GET, value="/assignments")
    public ResponseEntity<Iterable<AssignmentAggregated>> getAllAggregatedAssignments() {
        return assignmentIntegration.getAllAggregatedAssignments();
    }

    @RequestMapping(method = RequestMethod.GET, value="/oassignments/{assignmentId}")
    public ResponseEntity<Assignment> getAssignmentById(@PathVariable String assignmentId) {
        return assignmentIntegration.getAssignmentById(assignmentId);
    }

    @RequestMapping(method = RequestMethod.GET, value="/assignments/{assignmentId}")
    public ResponseEntity<AssignmentAggregated> getAggregatedAssignmentById(@PathVariable String assignmentId) {
        return assignmentIntegration.getAggregatedAssignmentById(assignmentId);
    }

    @RequestMapping(method = RequestMethod.POST, value="/assignments")
    public ResponseEntity<Assignment> createAssignment(@RequestBody Assignment assignment) {
        return assignmentIntegration.createAssignment(assignment);
    }

    @RequestMapping(method = RequestMethod.PUT, value="/assignments/{assignmentId}")
    public ResponseEntity<Assignment> updateAssignment(@PathVariable String assignmentId, @RequestBody Assignment assignment, @RequestHeader HttpHeaders headers) {
        return assignmentIntegration.updateAssignment(assignmentId, assignment, headers);
    }

    @RequestMapping(method = RequestMethod.DELETE, value="/assignments/{assignmentId}")
    public ResponseEntity<Assignment> deleteAssignment(@PathVariable String assignmentId, @RequestHeader HttpHeaders headers) {
        return assignmentIntegration.deleteAssignment(assignmentId, headers);
    }
}
