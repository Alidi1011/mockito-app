package org.arteaga.mockitoapp.ejemplos.repositories;

import org.arteaga.mockitoapp.ejemplos.Datos;
import org.arteaga.mockitoapp.ejemplos.models.Examen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamenRepositoryImpl implements ExamenRepository{

  @Override
  public Examen guardar(Examen examen) {
    System.out.println("ExamenRepositoryImpl.guardar");
    return Datos.EXAMEN;
  }

  @Override
  public List<Examen> findAll() {
    //return Collections.emptyList();
    /*return Arrays.asList(new Examen(5L, "Matemáticas"), new Examen(6L, "Lenguaje"),
      new Examen(7L, "Historia"));*/
    System.out.println("ExamenRepositoryImpl.findAll");
    try {
      System.out.println("ExamenRepositoryOtro");
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return Datos.EXAMENES;
  }
}
