package com.example.employez.controller;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.CacheData;
import com.example.employez.dto.JobPostDto;
import com.example.employez.repository.CacheDataRepository;
import com.example.employez.util.AuthenticationUtil;
import com.example.employez.util.CacheUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class SearchController {

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private CacheDataRepository cacheDataRepository;

    @Autowired
    private ObjectMapper objectMapper;


    private Authentication getAuth() {
        return SecurityContextHolder.getContext().getAuthentication();
    }

    @Autowired
    private SessionFactory sessionFactory;
    @Autowired
    private JobPostDAO jobPostDAO;

    @Autowired
    private CacheUtil cacheUtil;

    @GetMapping("/search")
    /*@PreAuthorize("hasRole('ADMIN')")*/
    public String index(@RequestParam(name = "jobTitle", required = false, defaultValue = "") String jobTitle
            , @RequestParam(name = "location", required = false, defaultValue = "") String location
            , Model model) throws Exception {

        ArrayList<String> subField = new ArrayList<>();
        subField.add("Machine Learning");
        subField.add("DevOps");
        subField.add("Data Engineer");
        subField.add("Developer");
        subField.add("Software Engineer");
        subField.add("NLP Engineer");

        List<JobPostDto> jobPostDtosBySalaryRange =  jobPostDAO.getBySalaryRange();
        List<JobPostDto> jobPostings = jobPostDAO.jobPostingListByTwoFields(jobTitle,location);

        Authentication auth = getAuth();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth",auth);
        model.addAttribute("mail", mail);
        List<JobPostDto> topFiveJobPost = new ArrayList<>();
        for (int i = 0; i < Math.min(5,jobPostDtosBySalaryRange.size()); i++) {
            topFiveJobPost.add(jobPostDtosBySalaryRange.get(i));
        }
        model.addAttribute("jobList", jobPostings);
        model.addAttribute("subField", subField);
        model.addAttribute("topFive", topFiveJobPost);
        model.addAttribute("jobTitleSearch", jobTitle);
        return "search";
    }


    @GetMapping("/search/byskill/{skillName}")
    public String searchBySkill(@PathVariable(name = "skillName") String skillName, Model model) throws JsonProcessingException {
        Optional<CacheData> optionalCacheData = cacheDataRepository.findById(skillName);
        if (optionalCacheData.isPresent()) {
            System.out.println("GET FROM REDIS CACHE");
            String jobAsString = optionalCacheData.get().getValue();
            TypeReference<List<JobPostDto>> mapType = new TypeReference<>() {
            };
            List<JobPostDto> jobPostDtos = objectMapper.readValue(jobAsString, mapType);
            model.addAttribute("jobList",jobPostDtos);

        }
        else {
            List<JobPostDto> jobPostingsBySkill =  jobPostDAO.getBySkill(skillName);
            String jobAsString = objectMapper.writeValueAsString(jobPostingsBySkill);
            CacheData cacheData = new CacheData(skillName, jobAsString);
            cacheDataRepository.save(cacheData);
            model.addAttribute("jobList",jobPostingsBySkill);
        }


        model.addAttribute("skillName", skillName);
        Authentication auth = authenticationUtil.authentication();

        String mail = null;

        if (auth != null) {
            mail = auth.getName();
        }

        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        return "search";
    }
}
