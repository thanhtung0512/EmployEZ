package com.example.employez.controller;


import com.example.employez.domain.entity_class.Employee;
import com.example.employez.dto.ResumeDto;
import com.example.employez.util.AuthenticationUtil;
import com.example.employez.util.CurrentUserUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

// view: /resume/view
// create: /resume/create
// view by id: /resume/view/{id}


@Controller
@RequestMapping("/resume")
public class ResumeController {


    @Autowired
    private CurrentUserUtil currentUserUtil;

    @Autowired
    private AuthenticationUtil authenticationUtil;

    @Autowired
    private SessionFactory sessionFactory;

    @GetMapping(value = "/view")
    public ResponseEntity<InputStreamResource> getTermsConditions() throws FileNotFoundException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Employee employee = currentUserUtil.employee(currentUserUtil.getCurrentUser().getId());
        String getUserResumePath = "SELECT resumePath FROM resume WHERE employee_id = :e_id ";
        String path = session.createNativeQuery(getUserResumePath, String.class).setParameter("e_id", employee.getId()).getSingleResult();
        String fileName = "";
        for (int i = path.length() - 1; i >= 0; i--) {
            if (path.charAt(i) == '\'') {
                fileName = path.substring(i+1);
                break;
            }
        }
        System.out.println("FILE NAME = " + fileName);
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" + fileName);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }

    @GetMapping("/create")
    public String createResume(Model model) {
        model.addAttribute("resumeDto", new ResumeDto());
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        return "my-resume";
    }

    @RequestMapping("/preview")
    public String preview(@ModelAttribute(name = "resumeDto") ResumeDto resumeDto, Model model) {
        System.out.println("RESUME DTO = " + resumeDto.toString());
        model.addAttribute("resumeDto", resumeDto);
        Authentication auth = authenticationUtil.authentication();
        String mail = null;
        if (auth != null) {
            mail = auth.getName();
        }
        System.out.println("MAIL = " + mail);
        model.addAttribute("auth", auth);
        model.addAttribute("mail", mail);
        model.addAttribute("roles", authenticationUtil.getUserRole(auth));
        return "my-resume";
    }

    @GetMapping(value = "/view/{id}")
    public ResponseEntity<InputStreamResource> viewResumeWithId(@PathVariable(name = "id") Integer resumeId) throws FileNotFoundException {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        // Employee employee = currentUserUtil.employee(currentUserUtil.getCurrentUser().getId());
        String getUserResumePath = "SELECT resumePath FROM resume WHERE id = :resumeId ";
        // System.out.println("RESUME PATH = " + getUserResumePath);
        String path = session.createNativeQuery(getUserResumePath, String.class).setParameter("resumeId", resumeId).getSingleResult();
        System.out.println("RESUME PATH = " + path);
        String fileName = "";
        for (int i = path.length() - 1; i >= 0; i--) {
            if (path.charAt(i) == '\'') {
                fileName = path.substring(i + 1);
                break;
            }
        }
        System.out.println("FILE NAME = " + fileName);
        File file = new File(path);
        HttpHeaders headers = new HttpHeaders();
        headers.add("content-disposition", "inline;filename=" + fileName);

        InputStreamResource resource = new InputStreamResource(new FileInputStream(file));

        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/pdf"))
                .body(resource);
    }
}
