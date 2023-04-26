package com.example.employez.util;

import com.example.employez.dao.jobPostingDAO.JobPostDAO;
import com.example.employez.domain.entity_class.CacheData;
import com.example.employez.domain.entity_class.Course;
import com.example.employez.dto.JobPostDto;
import com.example.employez.repository.CacheDataRepository;
import com.example.employez.repository.CourseRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CacheUtil {

    @Autowired
    private CacheDataRepository cacheDataRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private JobPostDAO jobPostDAO;

    public List<Course> getCacheCourseByName(String courseName) throws JsonProcessingException {
        List<Course> courses;
        Optional<CacheData> optionalCacheData = cacheDataRepository.findById(courseName);
        if (optionalCacheData.isPresent()) {
            System.out.println("GET FROM REDIS CACHE " + courseName);
            String courseAsString = optionalCacheData.get().getValue();
            TypeReference<List<Course>> mapType = new TypeReference<>() {
            };
            courses = objectMapper.readValue(courseAsString, mapType);
            return courses;
        }
        courses = courseRepository.findCourseByTitleContaining(courseName);
        String productsAsJsonString = objectMapper.writeValueAsString(courses);
        CacheData cacheData = new CacheData(courseName, productsAsJsonString);
        cacheDataRepository.save(cacheData);

        return courses;
    }

    public List<JobPostDto> gẹtJobFromCacheBySkill(String skillName) throws JsonProcessingException {
        List<JobPostDto> jobPostDtos = new ArrayList<>();
        Optional<CacheData> optionalCacheData = cacheDataRepository.findById(skillName);
        if (optionalCacheData.isPresent()) {
            System.out.println("GET FROM REDIS CACHE");
            String jobAsString = optionalCacheData.get().getValue();
            TypeReference<List<JobPostDto>> mapType = new TypeReference<>() {
            };
            jobPostDtos = objectMapper.readValue(jobAsString, mapType);
            return jobPostDtos;

        }
        ArrayList<JobPostDto> jobPostingsBySkill = (ArrayList<JobPostDto>) jobPostDAO.getBySkill(skillName);
        String jobAsString = objectMapper.writeValueAsString(jobPostingsBySkill);
        CacheData cacheData = new CacheData(skillName, jobAsString);
        cacheDataRepository.save(cacheData);
        return jobPostingsBySkill;


    }
}
