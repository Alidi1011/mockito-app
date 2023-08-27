package org.arteaga.mockitoapp.ejemplos.repositories;

import org.arteaga.mockitoapp.ejemplos.models.Examen;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class ExamenRepositoryOtro implements  ExamenRepository{
  @Override
  public Examen guardar(Examen examen) {
    return null;
  }

  @Override
  public List<Examen> findAll() {
    try {
      TimeUnit.SECONDS.sleep(5);
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
    return null;
  }
}
