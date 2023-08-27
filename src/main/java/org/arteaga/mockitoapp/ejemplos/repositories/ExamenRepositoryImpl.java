package org.arteaga.mockitoapp.ejemplos.repositories;

import org.arteaga.mockitoapp.ejemplos.models.Examen;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ExamenRepositoryImpl implements ExamenRepository{

  @Override
  public Examen guardar(Examen examen) {
    return null;
  }

  @Override
  public List<Examen> findAll() {
    return Collections.emptyList();
    /*return Arrays.asList(new Examen(5L, "Matemáticas"), new Examen(6L, "Lenguaje"),
      new Examen(7L, "Historia"));*/
  }
}
