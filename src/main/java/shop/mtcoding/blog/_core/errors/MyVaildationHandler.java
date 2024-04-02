package shop.mtcoding.blog._core.errors;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import shop.mtcoding.blog._core.errors.exception.Exception400;

@Aspect // AOP 등록
@Component // IoC 등록
public class MyVaildationHandler {

    // Advice (부가 로직 hello 메서드)
    // Advice가 수행될 위치 === PointCut
    // 유효성검사는 GetMapping시에는 필요 없음 PostMapping에만 필요함
    @Before("@annotation(org.springframework.web.bind.annotation.PostMapping) || @annotation(org.springframework.web.bind.annotation.PutMapping)") // PostMapping, PutMapping 시에만 걸어라 (POINTCUT)
    public void hello(JoinPoint jp){
        Object[] args  = jp.getArgs(); // 파라메터(매개변수)
        System.out.println("크기 : " + args.length );

        for (Object arg : args){

            if (arg instanceof Errors){
                Errors errors = (Errors) arg;

                if (errors.hasErrors()){
                    for (FieldError error : errors.getFieldErrors()){
                        System.out.println(error.getField());
                        System.out.println(error.getDefaultMessage());

                        throw new Exception400(error.getDefaultMessage()+ " : " + error.getField());
                    }
                }
            }
        }

        System.out.println("MyValidationHandler : Hello............");
    }
}
