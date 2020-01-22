package com.exercicio.modelagembanco.domain.validator;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class DateFutureValidator implements ConstraintValidator<DateFuture, Date> {

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {
        
        Calendar dataAtual = Calendar.getInstance();
        long dataHojeMilisec= dataAtual.getTimeInMillis() ;

        Calendar dataRecebida = Calendar.getInstance();
        dataRecebida.setTime(value);
        long dataRecebidaMilisec = dataRecebida.getTimeInMillis();
        return value != null && (dataHojeMilisec <= dataRecebidaMilisec);
    }
}