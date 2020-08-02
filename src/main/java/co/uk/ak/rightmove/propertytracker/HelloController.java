package co.uk.ak.rightmove.propertytracker;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController
{
   @GetMapping(value = "/")
   public ResponseEntity<String> message()
   {
      return ResponseEntity.status(HttpStatus.OK).body("This app is doing some important stuff");
   }
}
