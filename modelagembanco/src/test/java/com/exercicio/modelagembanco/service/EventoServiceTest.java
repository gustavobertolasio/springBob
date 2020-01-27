package com.exercicio.modelagembanco.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.exercicio.modelagembanco.domain.entity.CategoriaEvento;
import com.exercicio.modelagembanco.domain.entity.Evento;
import com.exercicio.modelagembanco.domain.entity.StatusEvento;
import com.exercicio.modelagembanco.exception.DataNotFoundException;
import com.exercicio.modelagembanco.exception.DateEndException;
import com.exercicio.modelagembanco.exception.EventoDayException;
import com.exercicio.modelagembanco.exception.EventoNotExcludedException;
import com.exercicio.modelagembanco.exception.EventoNotUpdatedException;
import com.exercicio.modelagembanco.repository.CategoriaEventoRepository;
import com.exercicio.modelagembanco.repository.EventoRepository;
import com.exercicio.modelagembanco.repository.StatusEventoRepository;

import org.junit.Before;
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
public class EventoServiceTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();
    @Mock
    private EventoRepository repositoryMock;
    @Mock
    private StatusEventoRepository statusRepositoryMock;
    @Mock
    private CategoriaEventoRepository categoriaEventoRepositoryMock;

    @InjectMocks
    private EventoService service;
    @Mock
    private CategoriaEventoService cateService;
    @Mock
    private StatusEventoService statusService;
    @Mock
    private ParticipacaoService participacaoService;

    StatusEvento status = new StatusEvento(2, "Some string");
    CategoriaEvento cateEvento = new CategoriaEvento(1, "Some string");
    Date dataHoje = new Date();

    @Before
    public void reseta() {
        evento = new Evento(1, status, cateEvento, "Oi", dataHoje, dataHoje, "oi", "oi", 10);
    }

    static Evento evento;

    @Test
    public void should_getEventoList() {
        List<Evento> listEvento = new ArrayList<>();
        listEvento.add(evento);
        // given
        when(repositoryMock.findAll()).thenReturn(listEvento);

        // when
        List<Evento> list = service.getList();

        // then
        verify(repositoryMock, times(1)).findAll();
        assertNotNull(list);
        assertEquals("Array deve ser de tamanho 1", 1, list.size());
    }

    @Test
    public void should_getEventoList_WhenInDateRange() {

    //     List<Evento> listEvento = new ArrayList<>();
    //     listEvento.add(evento);
    //     Calendar cal = Calendar.getInstance();
    //     cal.setTime(dataHoje);

    //     String mes = cal.get(Calendar.MONTH) < 9 ? "0" + (cal.get(Calendar.MONTH) + 1)
    //             : "" + (cal.get(Calendar.MONTH) + 1);
    //     String data = "" + cal.get(Calendar.DAY_OF_MONTH) + "" + mes +"" + cal.get(Calendar.YEAR);
    //     // given
    //     when(repositoryMock.findByDate(dataHoje)).thenReturn(listEvento);

    //     // when
    //     List<Evento> list = new ArrayList<>();
    //     try {
    //         list = service.findByDate(data);
    //     } catch (ParseException e) {
    //         // TODO Auto-generated catch block
    //         e.printStackTrace();
    //     }

    //     // then
    //  //   verify(repositoryMock, times(1)).findByDate(dataHoje);
    //     assertNotNull(list);
    //     assertEquals("Array deve ser de tamanho 1", 1, list.size());
    }

    @Test
    public void should_BeValid_WhenGetEventoById() {
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(evento));

        Evento evento = service.findEvento(anyInt());

        verify(repositoryMock, times(1)).findById(anyInt());
        assertNotNull(evento);
    }

    @Test
    public void should_ThrowDataNotFoundException_WhenGetEventoById() {

        expected.expect(DataNotFoundException.class);
        expected.expectMessage("Evento not found");

        service.findEvento(6);
    }

    @Test
    public void should_ThrowDateEndException_WhenSaveEventoWithEndDateBeforeBeginDate() {

        expected.expect(DateEndException.class);
        expected.expectMessage("Data fim menor que data início");

        Calendar cale = Calendar.getInstance();
        cale.setTime(new Date());
        cale.set(cale.get(Calendar.YEAR), cale.get(Calendar.MONTH), cale.get(Calendar.DAY_OF_MONTH) - 1);
        evento.setDataHoraFim(cale.getTime());
        service.saveEvento(evento, evento.getCategoriaEvento().getIdCategoriaEvento());
    }

    @Test
    public void should_ThrowDateEndException_WhenSaveEventoWithEndDayNotTheSameAsBeginDay() {
        expected.expect(DateEndException.class);
        expected.expectMessage("O evento precisa terminar no mesmo dia");

        Calendar cale = Calendar.getInstance();
        cale.setTime(new Date());
        cale.set(cale.get(Calendar.YEAR), cale.get(Calendar.MONTH), cale.get(Calendar.DAY_OF_MONTH) + 1);
        evento.setDataHoraFim(cale.getTime());
        service.saveEvento(evento, evento.getCategoriaEvento().getIdCategoriaEvento());
    }

    @Test
    public void should_SaveEvento_WhenSaveEvento() {
        when(repositoryMock.save(evento)).thenReturn(evento);

        Evento event = service.saveEvento(evento, evento.getCategoriaEvento().getIdCategoriaEvento());
        verify(repositoryMock, times(1)).save((evento));
        assertNotNull(event);
    }

    @Test
    public void should_updateEvento_WhenUpdateEvento() {
        when(repositoryMock.save(evento)).thenReturn(evento);

        Evento event = service.saveEvento(evento, 2);
        verify(repositoryMock, times(1)).save(evento);
        assertNotNull(event);
    }

    @Test
    public void should_ThrowEventoDayException_WhenBeginEventoBeforeBeginDate() {
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(evento));

        expected.expect(EventoDayException.class);
        expected.expectMessage("Não é possível iniciar o evento fora do dia de início");

        Calendar cale = Calendar.getInstance();
        cale.setTime(new Date());
        cale.set(cale.get(Calendar.YEAR), cale.get(Calendar.MONTH), cale.get(Calendar.DAY_OF_MONTH) + 1);
        evento.setDataHoraInicio(cale.getTime());
        evento.setStatusEvento(status);

        service.updateEvento(evento, 1, 2);
    }

    @Test
    public void should_BeginEvento_WhenEventoInDateRange() {
        evento.setStatusEvento(new StatusEvento(2, "Ihi"));
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(evento));
        when(repositoryMock.save(evento)).thenReturn(evento);
        Evento aux = service.updateEvento(evento, 1, 2);

        verify(repositoryMock, times(1)).save(evento);
        assertNotNull(aux);
    }

    @Test
    public void should_ThrowEventoDayException_WhenCancellingEventoInBeginDate() {
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(evento));

        expected.expect(EventoDayException.class);
        expected.expectMessage("Não é possível cancelar o evento no dia de início");

        service.updateEvento(evento, 1, 4);
    }

    @Test
    public void should_CancelEvento_WhenActualDayNotInEventoDate() {
        evento.setStatusEvento(new StatusEvento(4, "Ihi"));
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(evento));
        when(repositoryMock.save(evento)).thenReturn(evento);

        Calendar cale = Calendar.getInstance();
        cale.setTime(new Date());
        cale.set(cale.get(Calendar.YEAR), cale.get(Calendar.MONTH), cale.get(Calendar.DAY_OF_MONTH) + 1);
        evento.setDataHoraInicio(cale.getTime());

        Evento aux = service.updateEvento(evento, 1, 4);

        verify(repositoryMock, times(1)).save(evento);
        assertNotNull(aux);
    }

    @Test
    public void should_ThrowEventoNotUpdatedException_WhenCancellingEventoInBeginDate() {
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(evento));

        expected.expect(EventoNotUpdatedException.class);
        expected.expectMessage("Só é possível concluir o evento quando ele está em andamento");

        evento.setStatusEvento(new StatusEvento(1, "Aberto"));

        service.updateEvento(evento, 1, 3);
    }

    @Test
    public void should_FinishEvento_WhenEventoBegin() {
        evento.setStatusEvento(new StatusEvento(2, "Ihi"));
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(evento));
        when(repositoryMock.save(evento)).thenReturn(evento);

        Evento aux = service.updateEvento(evento, 1, 3);

        verify(repositoryMock, times(1)).save(evento);
        assertNotNull(aux);
    }

    @Test
    public void should_deleteEvento_WhenDeletingEvento() {
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(evento));

        service.deleteEvento(anyInt());
    }

    @Test
    public void should_ThrowEventoNotExcluded_WhenDeletingEventoWithParticipacao() {
        when(repositoryMock.findById(anyInt())).thenReturn(Optional.of(evento));
        when(repositoryMock.countParticipacoesInEvento(anyInt())).thenReturn(5);

        expected.expect(EventoNotExcludedException.class);
        expected.expectMessage("Evento já com participação");

        service.deleteEvento(anyInt());
    }

    @Test
    public void should_GetEventoListByCategoria() {
        /************************************* */
        List<Evento> eventoList = new ArrayList<>();
        eventoList.add((evento));

        when(cateService.findCategoriaEvento(anyInt())).thenReturn(cateEvento);
        when(repositoryMock.findBycategoriaEvento(cateEvento)).thenReturn(eventoList);

        List<Evento> evList = service.findEventosByCategoria(anyInt());

        assertNotNull(evList);
    }
}