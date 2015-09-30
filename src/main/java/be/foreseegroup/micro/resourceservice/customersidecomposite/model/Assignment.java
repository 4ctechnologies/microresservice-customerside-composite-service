package be.foreseegroup.micro.resourceservice.customersidecomposite.model;

/**
 * Created by Kaj on 29/09/15.
 */
public class Assignment {
    private String id;

    private String consultantId;
    private String customerId;

    private String startDate;
    private String endDate;

    public Assignment() {

    }

    public Assignment(Assignment assignment) {
        this.id = assignment.getId();
        this.consultantId = assignment.getConsultantId();
        this.customerId = assignment.getCustomerId();
        this.startDate = assignment.getStartDate();
        this.endDate = assignment.getEndDate();
    }

    public Assignment(String consultantId, String customerId, String startDate, String endDate) {
        this.consultantId = consultantId;
        this.customerId = customerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConsultantId() {
        return consultantId;
    }

    public void setConsultantId(String consultantId) {
        this.consultantId = consultantId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}