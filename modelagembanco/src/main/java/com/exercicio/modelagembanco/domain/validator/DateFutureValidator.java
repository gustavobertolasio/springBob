package com.exercicio.modelagembanco.domain.validator;

import java.util.Calendar;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.exercicio.modelagembanco.exception.DateIsNullException;

public class DateFutureValidator implements ConstraintValidator<DateFuture, Date> {

    @Override
    public boolean isValid(Date value, ConstraintValidatorContext context) {

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.set(dataAtual.get(Calendar.YEAR), dataAtual.get(Calendar.MONTH), dataAtual.get(Calendar.DAY_OF_MONTH),
                00, 00, 00);
        long dataHojeMilisec = dataAtual.getTimeInMillis();

        Calendar dataRecebida = Calendar.getInstance();
        try {
            dataRecebida.setTime(value);
        } catch (Exception e) {
            throw new DateIsNullException("Data de início está nula");
        }
        dataRecebida.set(dataRecebida.get(Calendar.YEAR), dataRecebida.get(Calendar.MONTH),
                dataRecebida.get(Calendar.DAY_OF_MONTH), 00, 00, 00);
        long dataRecebidaMilisec = dataRecebida.getTimeInMillis();
        return (dataHojeMilisec < dataRecebidaMilisec);
    }
}