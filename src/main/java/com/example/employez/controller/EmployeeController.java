package com.example.employez.controller;

import com.example.employez.dao.CourseDao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.Employee;
import com.example.employez.domain.entity_class.JobPosting;
import com.example.employez.domain.entity_class.Role;
import com.example.employez.domain.entity_class.User;
import com.example.employez.domain.enumPackage.ApplyingJobState;
import com.example.employez.dto.JobPostDto;
import com.example.employez.dto.UserDto;
import com.example.employez.repository.EmployeeRepository;
import com.example.employez.util.AuthenticationUtil;
import com.example.employez.util.CurrentUserUtil;
import com.example.employez.util.DayUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private CurrentUserUtil currentUserUtil;


    @Autowired
    private AuthenticationUtil authenticationUtil;
    @Autowired
    private JobPostDAO jobPostDAO;


    @Autowired
    private EmployeeRepository employeeRepository;

    @GetMapping("/apply/{id}")
    @Transactional
    public String apply(@PathVariable(name = "id") Long jobId, Model model) {
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        JobPostDto jobPostDto = jobPostDAO.getById(jobId);
        model.addAttribute("jobId", jobId);
        model.addAttribute("jobPost", jobPostDto);

        User user = currentUserUtil.getCurrentUser();
        Employee employee = currentUserUtil.employee(user.getId());
        model.addAttribute("employee",employee);
        model.addAttribute("user",user);
        return "apply-form";
    }

    @PostMapping("/tracking_job/{id}")
    @Transactional
    public String tracking(@PathVariable(name = "id") Long jobId, Model model) {
        User user = currentUserUtil.getCurrentUser();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        // select employee id from user id

        String selectEmployeeId = "SELECT id FROM employee e WHERE e.user_id = " + user.getId();
        Integer employeeId = session.createNativeQuery(selectEmployeeId, Integer.class).getSingleResult();

        String insertEmployeeApplyJob = "INSERT INTO apply (fk_employee,fk_jobpost,status) " +
                "value (:employeeId,:jobPostId,:initialState)";
        session.createNativeQuery(insertEmployeeApplyJob)
                .setParameter("employeeId", employeeId)
                .setParameter("jobPostId", jobId)
                .setParameter("initialState", ApplyingJobState.APPLICATION_RECEIVED.toString())
                .executeUpdate();
        String selectJobIdAppliedByUser = "SELECT fk_jobpost FROM apply WHERE fk_employee = " + user.getId();
        List<Integer> jobIds = session.createNativeQuery(selectJobIdAppliedByUser).getResultList();
        List<JobPostDto> jobPostDtos = new ArrayList<>();
        String getApplyStateSql = "SELECT status FROM apply WHERE fk_employee = :emp_id AND fk_jobpost = :jobId ";

        for (Integer id : jobIds) {
            JobPostDto each = jobPostDAO.getById(Long.valueOf(id));
            String status = session.createNativeQuery(getApplyStateSql, String.class)
                    .setParameter("emp_id", employeeId)
                    .setParameter("jobId", id).getSingleResult();
            each.setApplyingJobState(ApplyingJobState.valueOf(status));
            jobPostDtos.add(each);
        }
        model.addAttribute("jobList", jobPostDtos);

        session.getTransaction().commit();
        session.close();
        return "redirect:/employee/tracking_job";
    }

    @GetMapping("/tracking_job")
    @Transactional
    public String trackAllJob(Model model) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = currentUserUtil.getCurrentUser();
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        String selectEmployeeId = "SELECT id FROM employee e WHERE e.user_id = " + user.getId();
        Integer employeeId = session.createNativeQuery(selectEmployeeId, Integer.class).getSingleResult();

        String selectJobIdAppliedByUser = "SELECT fk_jobpost FROM apply WHERE fk_employee = " + employeeId;
        List<Integer> jobIds = session.createNativeQuery(selectJobIdAppliedByUser).getResultList();
        List<JobPostDto> jobPostDtos = new ArrayList<>();
        String getApplyStateSql = "SELECT status FROM apply WHERE fk_employee = :emp_id AND fk_jobpost = :jobId ";

        for (Integer id : jobIds) {
            JobPostDto each = jobPostDAO.getById(new Long(id));
            String status = session.createNativeQuery(getApplyStateSql, String.class)
                    .setParameter("emp_id", employeeId)
                    .setParameter("jobId", id).getSingleResult();
            each.setApplyingJobState(ApplyingJobState.valueOf(status));
            jobPostDtos.add(each);
        }
        model.addAttribute("jobList", jobPostDtos);
        session.getTransaction().commit();
        session.close();
        return "tracking-job";
    }

    @GetMapping("/profile/{id}")
    public String employeeProfile(@PathVariable(name = "id") Long id, Model model) {

        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        Employee employee = employeeRepository.getEmployeeById(id);
        System.out.println(employee.toString());
        Set<Role> roleSet = employee.getUser().getRoles();
        UserDto userDto = new UserDto();
        Set<JobPosting> jobPostings = null;
        userDto.setFirstName(employee.getFirstName());
        userDto.setLastName(employee.getLastName());
        userDto.setFullName(employee.getFirstName() + " " + employee.getLastName());
        userDto.setEmail(employee.getUser().getEmail());
        userDto.setJobTitle(employee.getJobTitle());
        userDto.setAddress(employee.getCity() + "," + employee.getCountry());
        userDto.setPhone(employee.getPhone());
        userDto.setUniversity(employee.getUniversity());
        userDto.setCountry(employee.getCountry());
        userDto.setState(employee.getCity());
        userDto.setSkillSet(employee.getSkills());
        jobPostings = employee.getFavoriteJob();
        model.addAttribute("user", userDto);
        model.addAttribute("roles", authenticationUtil.getUserRole(authenticationUtil.authentication()));
        model.addAttribute("jobList", jobPostings);
        return "employee_profile";
    }

    @GetMapping("/interview/{jobId}")
    public String watchInterviewAppointment(@PathVariable(name = "jobId") int jobId
            , Model model) {
        String getDate = "SELECT  interview_date FROM apply WHERE fk_employee = :empId AND fk_jobpost = :jobId";
        String getLocation = "SELECT  interview_location FROM apply WHERE fk_employee = :empId AND fk_jobpost = :jobId";
        String getFeedback = "SELECT  feedback FROM apply WHERE fk_employee = :empId AND fk_jobpost = :jobId";

        Long empId = currentUserUtil.employee(currentUserUtil.getCurrentUser().getId()).getId();

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Date interviewDate = (session.createNativeQuery(getDate, Date.class)
                .setParameter("empId", empId)
                .setParameter("jobId", jobId)
                .getSingleResult());

        String location = (session.createNativeQuery(getLocation, String.class)
                .setParameter("empId", empId)
                .setParameter("jobId", jobId)
                .getSingleResult());

        String feeback = (session.createNativeQuery(getFeedback, String.class)
                .setParameter("empId", empId)
                .setParameter("jobId", jobId)
                .getSingleResult());

        String interviewDateString = DayUtil.dateToString(interviewDate);

        model.addAttribute("location", location);
        model.addAttribute("date", interviewDateString);
        model.addAttribute("feedback", feeback);
        model.addAttribute("empEmail", currentUserUtil.getCurrentUser().getEmail());
        return "appointment_watch";
    }

    @PostMapping("/interview/{jobId}")
    public String acceptAppointment(@PathVariable(name = "jobId") Long jobId) {
        Long empId = currentUserUtil.employee(currentUserUtil.getCurrentUser().getId()).getId();
        String updateJobApplicationStatus = "UPDATE apply SET status = :interviewStatus WHERE  fk_employee = :empId AND fk_jobpost = :jobId";
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.createNativeQuery(updateJobApplicationStatus).setParameter("interviewStatus", ApplyingJobState.WAITING_INTERVIEW_RESULT.toString())
                .setParameter("empId", empId)
                .setParameter("jobId", jobId)
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return "redirect:/employee/tracking_job";
    }

    @GetMapping("/offered/{jobId}")
    public String getOffer(@PathVariable(name = "jobId") Integer jobId, Model model) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        User user = currentUserUtil.getCurrentUser();
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        JobPostDto jobPostDto = jobPostDAO.getById(Long.valueOf(jobId));

        // get Date and put to model

        String getStartDateSql = "SELECT start_date FROM apply WHERE fk_employee = " + currentUserUtil.employee(user.getId()).getId() + " AND fk_jobpost = " + jobId;
        Date startDate = session.createNativeQuery(getStartDateSql, Date.class).getSingleResult();
        String startDateString = DayUtil.dateToString(startDate);
        model.addAttribute("jobPostDto", jobPostDto);
        model.addAttribute("startDate", startDateString);

        // get salary and put into model
        String getSalary = "SELECT salary_and_benefit FROM apply WHERE fk_employee = " + currentUserUtil.employee(user.getId()).getId() + " AND fk_jobpost = " + jobId;
        String salary = session.createNativeQuery(getSalary, String.class).getSingleResult();

        String getStatus = "SELECT status FROM apply WHERE fk_jobpost = " + jobId + " AND fk_employee = " + currentUserUtil.employee(user.getId()).getId();
        String status = session.createNativeQuery(getStatus, String.class).getSingleResult();

        model.addAttribute("salary", salary);
        model.addAttribute("status", status);
        session.getTransaction().commit();
        session.close();
        return "get_offer";
    }


    @GetMapping("/offer/accept_offer/{id}")
    public String acceptThisOffer(@PathVariable(name = "id") Integer jobId) {
        Long empId = currentUserUtil.employee(currentUserUtil.getCurrentUser().getId()).getId();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String deleteThisApplication = "UPDATE apply SET status = :newStatus WHERE fk_employee = " + empId + " AND fk_jobpost = " + jobId;
        session.createNativeQuery(deleteThisApplication)
                .setParameter("newStatus", ApplyingJobState.ACCEPT_OFFER.toString())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return "redirect:/employee/tracking_job";
    }

    @GetMapping("/offer/decline/{id}")
    public String declineThisOffer(@PathVariable(name = "id") Integer jobId) {
        Long empId = currentUserUtil.employee(currentUserUtil.getCurrentUser().getId()).getId();
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        String deleteThisApplication = "UPDATE apply SET status = :newStatus WHERE fk_employee = " + empId + " AND fk_jobpost = " + jobId;
        session.createNativeQuery(deleteThisApplication)
                .setParameter("newStatus", ApplyingJobState.DECLINE_OFFER.toString())
                .executeUpdate();
        session.getTransaction().commit();
        session.close();
        return "redirect:/employee/tracking_job";
    }

}
