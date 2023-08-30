package org.arteaga.mockitoapp.ejemplos.services;

import org.arteaga.mockitoapp.ejemplos.Datos;
import org.arteaga.mockitoapp.ejemplos.models.Examen;
import org.arteaga.mockitoapp.ejemplos.repositories.ExamenRepository;
import org.arteaga.mockitoapp.ejemplos.repositories.ExamenRepositoryImpl;
import org.arteaga.mockitoapp.ejemplos.repositories.PreguntaRepository;
import org.arteaga.mockitoapp.ejemplos.repositories.PreguntaRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatcher;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ExamenServiceSpyImplTest {
  @Spy
  ExamenRepositoryImpl examenRepository;
  @Spy
  PreguntaRepositoryImpl preguntaRepository;
  @InjectMocks
  ExamenServiceImpl service;
  @Test
  void testSpy(){
    List<String> preguntas = Arrays.asList("aritmética");
    //doReturn(preguntas).when(preguntaRepository).findPreguntasPorExamenId(anyLong());
    when(preguntaRepository.findPreguntasPorExamenId(anyLong())).thenReturn(preguntas);

    Examen examen = service.findExamenPorNombreConPreguntas("Matemáticas");

    assertEquals(5, examen.getId());
    assertEquals("Matemáticas", examen.getNombre());
    assertEquals(1, examen.getPreguntas().size());
    assertTrue(examen.getPreguntas().contains("aritmética"));

    verify(examenRepository).findAll();
    verify(preguntaRepository).findPreguntasPorExamenId(anyLong());
  }
}