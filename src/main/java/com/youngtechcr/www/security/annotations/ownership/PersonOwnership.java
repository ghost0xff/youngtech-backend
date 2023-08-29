package com.youngtechcr.www.security.annotations.ownership;

import org.springframework.security.access.prepost.PostAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
/* TODO: above annotaiton commented so I dont accidentaly
*   break the server
* */
//@PostAuthorize("returnObject.user.id == authentication.name")
public @interface PersonOwnership {
    /*
    * TODO: ^^^ DONT USE THIS, ITS BROKE ^^^^
    *  FIX IT NOWWWWW!!!!!!!!!!!!
    * */
}
