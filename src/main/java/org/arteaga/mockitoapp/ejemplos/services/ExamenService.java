package org.arteaga.mockitoapp.ejemplos.services;

import org.arteaga.mockitoapp.ejemplos.models.Examen;

import java.util.Optional;

public interface ExamenService {
  Optional<Examen> findExamenPorNombre(String nombre);

  Examen findExamenPorNombreConPreguntas(String nombre);

  Examen guardar(Examen examen);

}
