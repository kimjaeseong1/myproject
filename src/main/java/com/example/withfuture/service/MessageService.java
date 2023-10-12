package com.example.withfuture.service;

import lombok.RequiredArgsConstructor;
import net.bytebuddy.asm.Advice;
import org.springframework.cglib.core.Local;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class MessageService {

    private final MessageSource messageSource;

   public String getMessage(String code){
       Locale locale = LocaleContextHolder.getLocale();
       return messageSource.getMessage(code,null,locale);
   }

   public String getMessage(String code, String[] args){
       Locale locale = LocaleContextHolder.getLocale();
       return messageSource.getMessage(code,args,locale);
   }
}
