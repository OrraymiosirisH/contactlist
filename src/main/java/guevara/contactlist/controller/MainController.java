package guevara.contactlist.controller;


import guevara.contactlist.model.Contact;
import guevara.contactlist.repository.NewcontactRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller

public class MainController {


    @Autowired
    NewcontactRepo newcontactRepo;


   @RequestMapping("/")
   public String sayHello(){

       return "welcome01";
   }



    @RequestMapping("/welcome")
    public String index(Model model) {
        String sendToIndex = "WELCOME to Contact List App!!!(from controler)";
        model.addAttribute("message", sendToIndex);
        return "welcome";
    }



    @GetMapping("/addcontact")
    public String loadContact(Model model) {
        model.addAttribute("contact", new Contact());

        return "addcontact";


    }

    @PostMapping("/addcontact")
    public String processContact(@Valid @ModelAttribute("contact") Contact contact, BindingResult bindingResult) {

        System.out.println(bindingResult.toString());

        if (bindingResult.hasErrors()) {
            return "addcontact";
        }


        newcontactRepo.save(contact);
        return "redirect:/result";


    }


    @RequestMapping("/result")

    public String displayall(Model model) {
        model.addAttribute ("allcontact", newcontactRepo.findAll());
        return "result";


    }

    @RequestMapping("/update/{id}")
    public String updateContact(@PathVariable("id") long id, Model model) {
        model.addAttribute("contact", newcontactRepo.findOne(id));
        return "addcontact";
    }

    @RequestMapping("/delete/{id}")
    public String delContact(@PathVariable("id") long id) {
        newcontactRepo.delete(id);
        return "redirect:/result";

    }

    @GetMapping("/listall")
    public String repoFinal(Model model){

        Iterable<Contact> listofcontact = newcontactRepo.findAll();

        model.addAttribute("listofcontact",listofcontact);

        return "listall";
    }



}