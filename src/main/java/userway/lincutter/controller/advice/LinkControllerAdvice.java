package userway.lincutter.controller.advice;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import userway.lincutter.controller.LinkController;
import userway.lincutter.exception.LinkNotFoundException;

@ControllerAdvice(basePackageClasses = LinkController.class)
public class LinkControllerAdvice {

    @ExceptionHandler(LinkNotFoundException.class)
    public ResponseEntity<String> handleLinkNotFoundException(LinkNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Link not found: " + ex.getMessage());
    }

}
