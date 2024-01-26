package com.spring.view.common;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice("com.spring.view")
public class CommonExceptionHandler {

   @ExceptionHandler(Exception.class)
   public ModelAndView handleException(Exception e) {
      ModelAndView mav = new ModelAndView();
      mav.addObject("exception",e);
      mav.setViewName("../common/error");
      return mav;
   }

}