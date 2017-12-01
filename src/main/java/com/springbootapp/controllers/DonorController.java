package com.springbootapp.controllers;

import com.springbootapp.SpringbootappApplication;
import com.springbootapp.models.Donor;
import com.springbootapp.repositories.DonorRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

@Controller
public class DonorController {
    private static final String donor_add_FORM = "donors/DonorForm";
    private static final String donor_find_FORM = "donors/findOwnersForm";
    private static final String error = "system/error";
    private final DonorRepository donors;
    private static final Logger log = LoggerFactory.getLogger(SpringbootappApplication.class);


    @Autowired
    public DonorController(DonorRepository donors) {
        this.donors = donors;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");               //prevent unwanted modifications
    }

    @RequestMapping(value = "/donors/new", method = RequestMethod.GET)
    public String initCreationForm(Map<String, Object> model) {
        log.info("Creation Form initialized");
        model.put("donor", new Donor());
        return donor_add_FORM;
    }

    @RequestMapping(value = "/donors/new", method = RequestMethod.POST)
    public String processCreationForm(@ModelAttribute @Valid Donor donor, BindingResult result) {
        if (result.hasErrors()) {
            log.info("Error occurs in processing creation form");
            log.info(result.toString());
            return error;
        } else {
            log.info("Processing creation form... saving...");
            this.donors.save(donor);
            log.info("Added donor with adress: " + donor.getAddress());
            log.info("Birthdate: " + donor.getBirthDate().toString());
            return "redirect:/donors/" + donor.getId();
        }
    }

    @RequestMapping(value = "/donors/find", method = RequestMethod.GET)
    public String initFindForm(Map<String, Object> model) {
        model.put("donor", new Donor());
        log.info("Initialized find form");
        return donor_find_FORM;
    }

    @RequestMapping(value = "/donors", method = RequestMethod.GET)
    public String processFindForm(Donor donor, BindingResult result, Map<String, Object> model) {
        if (result.hasErrors()) {
            log.info("Error occurs in processign find form");
            return error;
        }
        List<Donor> donorList = this.donors.findByLastName(donor.getLastName());
        if (donorList.isEmpty()) {
            result.rejectValue("lastName", "notFound", "notFound");
            log.info("Processing find form - donor not found");
            return donor_find_FORM;
        } else if (donorList.size() == 1) {
            log.info("Found one donor");
            return "redirect:/donors/" + donorList.get(0).getId();
        } else {
            log.info("Found multiple donorList");
            model.put("donorList", donorList);
            return "donors/donorsList";
        }
    }

    @RequestMapping(value = "/donors/{donorId}/edit", method = RequestMethod.GET)
    public String initUpdateDonorForm(@PathVariable("donorId") Long donorId, Model model) {
        model.addAttribute(this.donors.findById(donorId));
        log.info("Initialized update donor form");
        return donor_add_FORM;
    }

    @RequestMapping(value = "/donors/{donorId}/edit", method = RequestMethod.POST)
    public String processUpdateDonorForm(@Valid Donor donor, BindingResult result, @PathVariable("donorId") Long donorId) {
        if (result.hasErrors()) {
            log.info("Error occurs in processing update donor form");
            return error;
        } else {
            log.info("Processing update donor form... updating....");
            donor.setId(donorId);
            this.donors.save(donor);
            return "redirect:/donors/{donorId}";
        }
    }

    //Custom handler - modelAndView
    @RequestMapping("/donors/{donorId}")
    public ModelAndView displayDonors(@PathVariable("donorId") Long donorId) {
        ModelAndView view = new ModelAndView("donors/donorDetails");
        view.addObject(this.donors.findById(donorId));
        log.info("Displaying donor...with id: " + donorId);
        return view;
    }
}
