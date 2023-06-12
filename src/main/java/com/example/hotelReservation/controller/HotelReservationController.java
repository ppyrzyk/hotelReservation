package com.example.hotelReservation.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
public class HotelReservationController {

    private UserService userService;

    private ReservationService reservationService;

    @Autowired
    public HotelReservationController(UserService userService, ReservationService reservationService) {
        this.userService = userService;
        this.reservationService = reservationService;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);
        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @RequestMapping("/")
    public String homePage() {

        return "home-page";
    }

    @GetMapping("/login-form-page")
    public String loginPage(Model model) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (!(auth instanceof AnonymousAuthenticationToken)) {
            return "redirect:/";
        }

        model.addAttribute("newUser", new CurrentUser());

        return "login";
    }

    @PostMapping("/processRegistration")
    public String processRegistrationForm(@Valid @ModelAttribute("newUser") CurrentUser currentUser,
                                          BindingResult theBindingResult, Model model) {

        if (userService.findUserByEmail(currentUser.getEmail()) != null) {
            model.addAttribute("newUser", new CurrentUser());
            model.addAttribute("registrationError", "Email already exists.");

            return "login";
        }

        // create user account
        userService.saveUser(currentUser);
        model.addAttribute("registrationSuccess", "registration Success.");

        return "redirect:/login-form-page";

    }

    @GetMapping("/new-reservation")
    public String newReservation(Model model) {
        // reservation attribute
        model.addAttribute("newRes", new CurrentReservation());

        return "reservation-page";
    }

    @PostMapping("/proceed-reservation")
    public String proceedReservation(@Valid @ModelAttribute("newRes") CurrentReservation currentReservation,
                                     BindingResult theBindingResult, Model model) {

        reservationService.saveOrUpdateReservation(currentReservation);

        return "redirect:/your-reservations";
    }

    @GetMapping("/your-reservations")
    public String reservationsList(Model model) {

        model.addAttribute("resList", reservationService.getReservationsForLoggedUser());

        return "your-reservations";
    }

    @PostMapping("/reservation-update")
    public String updateReservation(@RequestParam("resId") int resId, Model model) {

        model.addAttribute("newRes", reservationService.reservationToCurrentReservationById(resId));

        return "reservation-page";
    }


    @PostMapping("/reservation-delete")
    public String deleteReservation(@RequestParam("resId") int resId) {

        reservationService.deleteReservation(resId);

        return "redirect:/your-reservations";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logoutPage(HttpServletRequest request, HttpServletResponse response) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }

        return "redirect:/login-form-page?logout";
    }

}
