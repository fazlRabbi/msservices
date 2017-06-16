package no.hib.controllers;

import no.hib.logic.interfaces.LoginService;
import no.hib.models.AdminUser;
import no.hib.models.BankIdUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@Controller
@EnableWebMvc
@RequestMapping("api/login")
public class LoginController {

    @Autowired
    LoginService loginService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity loginBySsn(@RequestBody BankIdUser bankIdUser){
        boolean validUser = loginService.validUser(bankIdUser);
        if(validUser) {
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

    @RequestMapping(method = RequestMethod.POST, value = "/admin")
    public ResponseEntity loginBySsn(@RequestBody AdminUser adminUser){
        boolean validUser = loginService.validUser(adminUser);
        if(validUser) {
            return new ResponseEntity(HttpStatus.OK);
        }else{
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
    }

}
