package be.foreseegroup.micro.resourceservice.customersidecomposite.model;

/**
 * Created by Kaj on 29/09/15.
 */
public class AssignmentAggregated extends Assignment {
    private String consultantName;
    private String customerName;

    public AssignmentAggregated(Assignment assignment) {
        super(assignment);
    }

    public String getConsultantName() {
        return consultantName;
    }

    public void setConsultantName(String consultantName) {
        this.consultantName = consultantName;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
}
