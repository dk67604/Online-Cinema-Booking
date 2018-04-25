package org.uga.se.emoviebooking;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uga.se.emoviebooking.entity.Administrator;
import org.uga.se.emoviebooking.entity.RegisteredUser;
import org.uga.se.emoviebooking.helper.HelperMethods;
import org.uga.se.emoviebooking.helper.ResponseTemplate;
import org.uga.se.emoviebooking.persistence.UserDAO;

import javax.ws.rs.client.Entity;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/api")
@RestController
public class RegistrationController {
    @Autowired
    UserDAO userDAO;

    @RequestMapping(path = "/status", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseEntity<String>  index() {
        JSONObject object=new JSONObject();
        System.out.println("IN service");
        object.put("success","Ok");
        return ResponseEntity.ok(object.toString());
    }

    @RequestMapping(path = "/register", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseTemplate register(@RequestBody  String stringToParse) throws IOException {
        JsonParser jsonParser=JsonParserFactory.getJsonParser();
        JsonNode jsonNode = new ObjectMapper().readTree(stringToParse);
        ResponseTemplate responseTemplate=new ResponseTemplate();
        Map<String,Object> jsonMap= jsonParser.parseMap(stringToParse);
        RegisteredUser registeredUser=new RegisteredUser();
        String result=userDAO.checkUserExist((String)jsonMap.get("emailAddress"));
        responseTemplate.setStatus("200");
        if(result!=null&&result.equalsIgnoreCase((String)jsonMap.get("emailAddress"))){
            System.out.println("User exists"+result);
            responseTemplate.setMessageCode("1000");
            responseTemplate.setErrorMessage("User already exist");
        }else{
            registeredUser.setEmail( jsonNode.get("emailAddress").textValue());
            registeredUser.setFirstName( jsonNode.get("firstName").textValue());
            registeredUser.setLastName(jsonNode.get("lastName").textValue());
            String password=HelperMethods.hashPassword(jsonNode.get("password").textValue());

            registeredUser.getPassword().setPassword(password);
            registeredUser.setUserType((String)jsonMap.get("type"));
            if(jsonNode.has("creditCard")){
                if(jsonNode.get("creditCard").has("cardNumber")){
                    registeredUser.getCreditCard().setCardId(jsonNode.get("creditCard").get("cardNumber").textValue());
                }
                if(jsonNode.get("creditCard").has("expiryDate")){
                    registeredUser.getCreditCard().setCardExpirationDate(jsonNode.get("creditCard").get("expiryDate").textValue());

                }
                if(jsonNode.get("creditCard").has("securityCode")){
                    registeredUser.getCreditCard().setSecurityCode(jsonNode.get("creditCard").get("securityCode").textValue());

                }
            }else{
                registeredUser.setCreditCard(null);
            }

            if(jsonNode.has("address")){
             if(jsonNode.get("address").has("streetAddress")){
                 registeredUser.getAddress().
                         setStreetAddress1(jsonNode.get("address").get("streetAddress").textValue());
             }
             if(jsonNode.get("address").has("state")){
                 registeredUser.getAddress().setState(jsonNode.get("address").get("state").textValue());
             }
                if(jsonNode.get("address").has("zipCode")){
                    registeredUser.getAddress().setZipcode(jsonNode.get("address").get("zipCode").textValue());
                }

            }else{
                registeredUser.setAddress(null);
            }
            registeredUser.setUserType(jsonNode.get("type").textValue());
            int status= userDAO.register(registeredUser);
            if(status>0) {
                responseTemplate.setMessageCode("1002");
                responseTemplate.setSuccessMessage("User successfully added");
            }else{
                responseTemplate.setMessageCode("1001");
                responseTemplate.setSuccessMessage("Fail to add,try later");
            }
        }


        return responseTemplate;
    }


    @RequestMapping(path = "/login", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseTemplate login(@RequestBody  String stringToParse) throws IOException {
        System.out.println("In movies");
        JsonNode jsonNode = new ObjectMapper().readTree(stringToParse);
        ResponseTemplate responseTemplate=new ResponseTemplate();
        String result=userDAO.checkUserExist(jsonNode.get("userName").textValue());
        if(result!=null){
            Map<String,Object> map=new HashMap<>();
            String passwordStatus=null;
            if(jsonNode.get("type").asBoolean()){
                Administrator administrator=new Administrator();
                administrator.setUserType("Admin");
                administrator.getPassword().setPassword(jsonNode.get("password").textValue());
                administrator.setEmail(jsonNode.get("userName").textValue());
                passwordStatus=userDAO.verifyPassword(administrator);
                map.put("type","Admin");

            }else{
                RegisteredUser registeredUser=new RegisteredUser();
                registeredUser.setUserType("Customer");
                registeredUser.setEmail(jsonNode.get("userName").textValue());
                registeredUser.getPassword().setPassword(jsonNode.get("password").textValue());
                passwordStatus=userDAO.verifyPassword(registeredUser);
                map=new HashMap<>();
                map.put("type","Customer");

            }

            if(passwordStatus!=null && passwordStatus.equalsIgnoreCase("SUCCESS")){
                responseTemplate.setSuccessMessage("User verified");
                responseTemplate.setMessageCode("2000");
                responseTemplate.setResponse(map);
            }else {
                responseTemplate.setSuccessMessage("User Password Incorrect");
                responseTemplate.setMessageCode("2003");
                responseTemplate.setResponse(map);
            }

        }else{
            responseTemplate.setMessageCode("2001");
            responseTemplate.setSuccessMessage("Fail to add,try later");
        }

        return responseTemplate;
    }


    @RequestMapping(path = "/forgotPassword", method = RequestMethod.POST, produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    @ResponseBody
    public ResponseTemplate forgotPassword(@RequestBody  String stringToParse) throws IOException {
        ResponseTemplate responseTemplate=new ResponseTemplate();
        System.out.println(stringToParse);
        JsonNode jsonNode = new ObjectMapper().readTree(stringToParse);
        int status=userDAO.resetPassword(jsonNode.get("emailAddress").textValue());
        if(status>0){
            responseTemplate.setSuccessMessage("Password Changed successfully");
            responseTemplate.setMessageCode("4000");

        }
        else {
            responseTemplate.setSuccessMessage("Password not changed");
            responseTemplate.setMessageCode("4001");
        }
        return responseTemplate;
    }


}
