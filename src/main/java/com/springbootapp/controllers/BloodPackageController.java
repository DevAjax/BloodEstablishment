package com.springbootapp.controllers;

import com.springbootapp.SpringbootappApplication;
import com.springbootapp.models.BloodPackage;
import com.springbootapp.repositories.BloodPackageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.Map;

@Controller
public class BloodPackageController {
    private BloodPackageRepository bloodPackages;
    private static final Logger log = LoggerFactory.getLogger(SpringbootappApplication.class);

    @Autowired
    public BloodPackageController(BloodPackageRepository bloodPackages) {
        this.bloodPackages = bloodPackages;
    }

    @InitBinder
    public void setAllowedFields(WebDataBinder dataBinder) {
        dataBinder.setDisallowedFields("id");                //prevent unwanted modifications
    }

    @RequestMapping(value = "/bloodPackages/new", method = RequestMethod.GET)
    public String initNewPackage(Map<String, Object> model) {
        log.info("New BloodPackage Initialized");
        model.put("bloodPackage", new BloodPackage());
        return "bloodPackages/new";
    }

    @RequestMapping(value = "bloodPackages/new", method = RequestMethod.POST)
    public String processNewPackage(@ModelAttribute @Valid BloodPackage bloodPackage, BindingResult result) {
        if (result.hasErrors()) {
            log.info("Error occurs in processing new package");
            log.info(result.toString());
            return "error";

        } else {
            log.info("Processing new package... saving...");
            this.bloodPackages.save(bloodPackage);
            return "redirect:/bloodPackages/" + bloodPackage.getId();
        }
    }

    @RequestMapping(value = "/bloodPackages/{bloodPackageId}")
    public ModelAndView displayPackages(@PathVariable("bloodPackageId") Long bloodPackageId) {
        ModelAndView view = new ModelAndView("bloodPackages/packagesDetails");
        log.info("Displaying bloodPackages");
        view.addObject(bloodPackages.findById(bloodPackageId));
        return view;
    }

}
