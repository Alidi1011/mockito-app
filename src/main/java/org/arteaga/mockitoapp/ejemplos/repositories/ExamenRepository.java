package org.arteaga.mockitoapp.ejemplos.repositories;

import org.arteaga.mockitoapp.ejemplos.models.Examen;

import java.util.List;

public interface ExamenRepository {
  Examen guardar(Examen examen);

  List<Examen> findAll();
}
