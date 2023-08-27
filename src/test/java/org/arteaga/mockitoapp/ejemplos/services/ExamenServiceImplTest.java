package org.arteaga.mockitoapp.ejemplos.services;

import org.arteaga.mockitoapp.ejemplos.models.Examen;
import org.arteaga.mockitoapp.ejemplos.repositories.ExamenRepository;
import org.arteaga.mockitoapp.ejemplos.repositories.PreguntaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceImplTest {
  @Mock
  ExamenRepository examenRepository;
  @Mock
  PreguntaRepository preguntaRepository;
  @InjectMocks
  ExamenServiceImpl service;

  @Captor
  ArgumentCaptor<Long> captor;

  @BeforeEach
  void setUp(){
    //MockitoAnnotations.openMocks(this);
    //examenRepository = mock(ExamenRepository.class);
    //preguntaRepository = mock(PreguntaRepository.class);
    //service = new ExamenServiceImpl(examenRepository, preguntaRepository);
  }

  @Test
  void findExamenPorNombre(){
    when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
    Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");

    assertTrue(examen.isPresent());
    assertEquals(5L, examen.orElseThrow().getId());
    assertEquals("Matemáticas", examen.get().getNombre());
  }

  @Test
  void findExamenPorNombreListaVacia(){
    List<Examen> datos = Collections.emptyList();

    when(examenRepository.findAll()).thenReturn(datos);
    Optional<Examen> examen = service.findExamenPorNombre("Matemáticas");

    assertFalse(examen.isPresent());
  }

  @Test
  void testPreguntasExamen(){
    when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
    when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

    Examen examen = service.findExamenPorNombreConPreguntas("Historia");
    assertEquals(5, examen.getPreguntas().size());
    assertTrue(examen.getPreguntas().contains("integrales"));
  }

  @Test
  void testPreguntasExamenVerify(){
    when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
    when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);

    Examen examen = service.findExamenPorNombreConPreguntas("Historia");
    assertEquals(5, examen.getPreguntas().size());
    assertTrue(examen.getPreguntas().contains("integrales"));
    verify(examenRepository).findAll();
    verify(preguntaRepository).findPreguntasPorExamenId(7L);
  }
  @Test
  void testNoExisteExamenVerify(){
    //given
    when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
    //when
    Examen examen = service.findExamenPorNombreConPreguntas("Historiaa");
    //then
    assertNull(examen);
    verify(examenRepository).findAll();
  }

  @Test
  void testGuardarExamen(){
    //Given
    Examen newExamen = Datos.EXAMEN;
    newExamen.setPreguntas(Datos.PREGUNTAS);
    when(examenRepository.guardar(any(Examen.class))).then(new Answer<Examen>(){
      Long secuencia = 8L;

      @Override
      public Examen answer(InvocationOnMock invocation) throws Throwable {
        Examen examen = invocation.getArgument(0);
        examen.setId(secuencia++);
        return examen;
      }
    });

    //When
    Examen examen = service.guardar(newExamen);
    //then
    assertNotNull(examen.getId());
    assertEquals(8L, examen.getId());
    assertEquals("Física", examen.getNombre());

    verify(examenRepository).guardar(any(Examen.class));
    verify(preguntaRepository).guardarVarias(anyList());
  }

  @Test
  void testManejoException(){
    when(examenRepository.findAll()).thenReturn(Datos.EXAMENES_ID_NULL);
    when(preguntaRepository.findPreguntasPorExamenId(null)).thenThrow(IllegalArgumentException.class);
    Exception exception = assertThrows(IllegalArgumentException.class, () -> {
      service.findExamenPorNombreConPreguntas("Matemáticas");
    });
    assertEquals(IllegalArgumentException.class, exception.getClass());
    verify(examenRepository).findAll();
    verify(preguntaRepository).findPreguntasPorExamenId(null);

  }

  @Test
  void testArgumentMatchers(){
    when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
    when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
    service.findExamenPorNombreConPreguntas("Matemáticas");
    verify(examenRepository).findAll();
    //verify(preguntaRepository).findPreguntasPorExamenId(argThat(arg -> arg!=null && arg.equals(5L)));
    verify(preguntaRepository).findPreguntasPorExamenId(eq(5L));

  }

  @Test
  void testArgumentMatchers2(){
    when(examenRepository.findAll()).thenReturn(Datos.EXAMENES_ID_NEGATIVOS);
    when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
    service.findExamenPorNombreConPreguntas("Matemáticas");
    verify(examenRepository).findAll();
    verify(preguntaRepository).findPreguntasPorExamenId(argThat(new MiArgsMatchers()));
  }

  public static class MiArgsMatchers implements ArgumentMatcher<Long>{

    private Long argument;
    @Override
    public boolean matches(Long argument) {
      this.argument = argument;
      return argument != null && argument >0;
    }

    @Override
    public String toString(){
      return "es para un mensaje personalizado de error que imprime mockito en caso de que falle el test " +
        argument + " debe ser un entero " +
        "positivo";
    }
  }

  @Test
  void testArgumentCaptor(){
    when(examenRepository.findAll()).thenReturn(Datos.EXAMENES);
    when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(Datos.PREGUNTAS);
    service.findExamenPorNombreConPreguntas("Matemáticas");
    //ArgumentCaptor<Long> captor = ArgumentCaptor.forClass(Long.class);

    verify(preguntaRepository).findPreguntasPorExamenId(captor.capture());
    assertEquals(5L, captor.getValue());
  }

  @Test
  void testDoThrow(){
    Examen examen = Datos.EXAMEN;
    examen.setPreguntas(Datos.PREGUNTAS);
    doThrow(IllegalArgumentException.class).when(preguntaRepository).guardarVarias(anyList());

    assertThrows(IllegalArgumentException.class, () -> {
      service.guardar(examen);
      
    });


  }


}