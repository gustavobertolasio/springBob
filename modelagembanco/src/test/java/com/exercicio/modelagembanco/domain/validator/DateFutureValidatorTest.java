package com.exercicio.modelagembanco.domain.validator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintValidatorContext;

import com.exercicio.modelagembanco.domain.validator.DateFutureValidator;
import com.exercicio.modelagembanco.exception.DateIsNullException;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

/**
 * ClientServiceTest
 */
@RunWith(MockitoJUnitRunner.class)
public class DateFutureValidatorTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Mock
    ConstraintValidatorContext constraintValidatorContext;

    DateFutureValidator dateFutureValidator;

    @Before
    public void setUp() {
        dateFutureValidator = new DateFutureValidator();
    }

    @Test
    public void should_BeValid_WhenDataRecebidaIsBiggerThanDataHoje() {
        Calendar calen = Calendar.getInstance();
        calen.setTime(new Date());
        calen.add(Calendar.DAY_OF_MONTH, 1);
        Boolean resp = dateFutureValidator.isValid(calen.getTime(), constraintValidatorContext);
        assertTrue(resp);
    }

    @Test
    public void should_NotBeValid_WhenDataRecebidaIsSmallerThanDataHoje() {
        Calendar calen = Calendar.getInstance();
        calen.setTime(new Date());
        calen.add(Calendar.DAY_OF_MONTH, -1);
        Boolean resp = dateFutureValidator.isValid(calen.getTime(), constraintValidatorContext);
        assertFalse(resp);
    }

    @Test
    public void should_NotBeValid_WhenDataRecebidaIsNull() {
        expected.expect(DateIsNullException.class);
        expected.expectMessage("Data de início está nula");
        dateFutureValidator.isValid(null, constraintValidatorContext);
    }

}