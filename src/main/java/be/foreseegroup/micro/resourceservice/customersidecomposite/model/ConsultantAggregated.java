package be.foreseegroup.micro.resourceservice.customersidecomposite.model;

/**
 * Created by Kaj on 29/09/15.
 */
public class ConsultantAggregated extends Consultant {
    private Iterable<AssignmentAggregated> assignments;

    public ConsultantAggregated(Consultant consultant) {
        super(consultant);
    }

    public Iterable<AssignmentAggregated> getAssignments() {
        return assignments;
    }

    public void setAssignments(Iterable<AssignmentAggregated> assignments) {
        this.assignments = assignments;
    }
}
