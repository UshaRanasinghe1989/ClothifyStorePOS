package edu.icet.pos.controller.form_validation;

import lombok.extern.slf4j.Slf4j;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class FormValidationController {
    public Matcher emailFormatMatcher(String email){
        //Regular Expression
        String regex = "^[A-Za-z0-9+%_.-]+@[a-zA-Z0-9.-]+\\\\\\\\.[a-z]{2,}$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(email);
    }
    public Matcher phoneNoFormatMatcher(String phone){
        //Regular Expression
        String regex = "^(?:0)[0-9]{9,10}$";
        //Compile regular expression to get the pattern
        Pattern pattern = Pattern.compile(regex);
        return pattern.matcher(phone);
    }
}
