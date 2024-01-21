package com.example.Libreria;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.Optional;

@Controller
public class UtenteController {
    @Autowired
    private UtenteService utenteService;

    @GetMapping("/registrazione")
    public String registrationPage() {
        return "registrazione";
    }

    @PostMapping("/registra")
    public String registerUser(@RequestParam String username, @RequestParam String password, Model model) {
        if (username.length() >= 5 && password.length() >= 5) {
            Utente newUser = new Utente();
            newUser.setUsername(username);
            newUser.setPassword(password);
            utenteService.saveUser(newUser);
            String redirectUrl = "/dashboard/" + newUser.getId();

            return "redirect:" + redirectUrl;
        } else {
            return "redirect:/registrazione";
        }
    }

    @GetMapping("/dashboard/{userId}")
    public String dashboardPage(Model model, @PathVariable Long userId) {
        Optional<Utente> user = utenteService.findUserById(userId);
        user.ifPresent(value -> model.addAttribute("libri", value.getLibri()));
        model.addAttribute("username", user.map(Utente::getUsername).orElse("Utente Sconosciuto"));
        return "dashboard";
    }

    @GetMapping("/dashboard")
    public String dashboardPage(Model model, Principal principal) {
        String username = principal.getName();
        Optional<Utente> user = utenteService.findUserByUsername(username);
        user.ifPresent(value -> model.addAttribute("libri", value.getLibri()));
        model.addAttribute("username", username);
        return "dashboard";
    }

    @GetMapping("/login")
    public String loginPage() {
        return "login";
    }
}
