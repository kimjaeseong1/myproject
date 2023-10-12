package com.example.withfuture.exception;

import com.example.withfuture.dto.MemberDTO;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


@Component
public class MemberArgumentResolver implements HandlerMethodArgumentResolver {
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(MemberDTO.memberSignUpDTO.class) ||
                parameter.getParameterType().equals(MemberDTO.loginMember.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter,
                                  ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest,
                                  WebDataBinderFactory binderFactory) throws Exception {
        if(parameter.getParameterType().equals(MemberDTO.memberSignUpDTO.class)){
            return parameter.getParameterType().newInstance();


        }else if (parameter.getParameterType().equals(MemberDTO.loginMember.class)) {
            return parameter.getParameterType().newInstance();
        }

        throw new IllegalArgumentException("Unsupported parameter type: " + parameter.getParameterType());
    }


}
