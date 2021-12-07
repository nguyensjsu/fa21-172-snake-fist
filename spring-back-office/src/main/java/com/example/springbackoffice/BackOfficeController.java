package com.example.springbackoffice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.stereotype.Controller;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Controller
@RequestMapping("/")
class ExampleRestController {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private RefundRepository refundRepository;

    @GetMapping
    public String helloUser() {
        //model.addAttribute("name", user.getFullName());
        return "home";
    }

    @GetMapping(path="/contact")
    //public String submitOrder(@Valid @ModelAttribute("command") StarbucksOrder command, 
    // @RequestParam(value = "action", required = true) String action, Errors errors, Model model,
    // HttpServletRequest request) {
    public String getContact(@Valid @ModelAttribute("inquiry") Contact inquiry, Errors errors, 
                             Model model, HttpServletRequest request)
    {
        return "contact";
    }

    @RequestMapping(path="/contact")
    @PostMapping
    public String submitContact(@Valid @ModelAttribute("inquiry") Contact inquiry, 
                                @RequestParam(value = "action", required = true) String action, Errors errors, 
                                Model model, HttpServletRequest request)
    {
        // Contact contactB = new Contact();
        // contactB.setEmail("email@email.com");
        // contactB.setDescription("seee this is a descrription");

        contactRepository.save(inquiry);
        String confirmed = "Thank you for your inquiry, we will get back to you shortly!";
        model.addAttribute("confirmed", confirmed);
        return "contact";
    }

    @GetMapping(path="/schedule")
    public String getSchedule(@Valid @ModelAttribute("time") Appointment time, Errors errors, 
                             Model model, HttpServletRequest request)
    {
        return "schedule";
    }

    @RequestMapping(path="/schedule")
    @PostMapping
    public String submitSchedule(@Valid @ModelAttribute("time") Appointment time, 
                                @RequestParam(value = "action", required = true) String action, Errors errors, 
                                Model model, HttpServletRequest request)
    {
        appointmentRepository.save(time);
        String confirmed = "Your appointment is set, we will see you soon!";
        model.addAttribute("confirmed", confirmed);
        return "schedule";
    }

    @GetMapping(path="/refund")
    public String getRefund(@Valid @ModelAttribute("info") Refund info, Errors errors, 
                             Model model, HttpServletRequest request)
    {
        return "refund";
    }

    @RequestMapping(path="/refund")
    @PostMapping
    public String submitRefund(@Valid @ModelAttribute("info") Refund info, 
                                @RequestParam(value = "action", required = true) String action, Errors errors, 
                                Model model, HttpServletRequest request)
    {
        refundRepository.save(info);
        String confirmed = "We have received your refund request.";
        model.addAttribute("confirmed", confirmed);
        return "refund";
    }

    @GetMapping(path="/admin/messages") 
    public @ResponseBody Iterable<Contact> getContacts()
    {
        return contactRepository.findAll();
    }

    @GetMapping(path="/admin/appointments") 
    public @ResponseBody Iterable<Appointment> getAppointments()
    {
        return appointmentRepository.findAll();
    }

    @GetMapping(path="/admin/refunds") 
    public @ResponseBody Iterable<Refund> getRefunds()
    {
        return refundRepository.findAll();
    }
}