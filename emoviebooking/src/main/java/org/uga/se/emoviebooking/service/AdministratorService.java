package org.uga.se.emoviebooking.service;

import org.uga.se.emoviebooking.entity.Employee;
import org.uga.se.emoviebooking.entity.Ticket;

public interface AdministratorService extends User,EmployeeService {
    public void grantAccess(Employee employee);
    public void createEmployee(Employee employee);
    public void deleteEmployee(Employee employee);
    public void modify(Employee employee);
    public void suspend(Employee employee);
    public void salesReport(String startDate,String endDate);
    public void categoryPopularityReport(String startDate,String endDate);
    public void leastPopularMovieReport(String startDate,String endDate);
    public void hiredEmployeeReport(String startDate,String endDate);
    public void firedEmployeeReport(String startDate,String endDate);
    public void productivityReport(String startDate,String endDate);
    public void bookingReport(String startDate,String endDate);
    public void promotionReport(String startDate,String endDate);
    public void updateEmployee(String startDate,String endDate);
    public void reduceTicketPrice(Ticket ticket);



}
