package org.example;

import org.example.BaseDatos.HibernateUtil;
import org.example.BaseDatos.implementacionesCRUD.IngredientesCRUD;
import org.example.BaseDatos.implementacionesCRUD.PocionesCRUD;
import org.example.BaseDatos.interfacesDAO.IngredientesDAO;
import org.example.objetos.Ingredientes;
import org.example.objetos.Pociones;
import org.example.objetos.Recetas;
import org.example.objetos.RecetasId;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class creacionTablas {
    IngredientesCRUD ingredientesCRUD = new IngredientesCRUD();
    PocionesCRUD pocionesCRUD = new PocionesCRUD();

    List<Pociones> pociones = List.of(
            new Pociones("Elixir de Vida", "Restaura salud al instante", 50.0, Pociones.Escuela.CONJURACION, Pociones.Tamanio.MEDIANO),
            new Pociones("Poción de Invisibilidad", "Te vuelve invisible por un minuto", 120.0, Pociones.Escuela.ILUSION, Pociones.Tamanio.PEQUEÑO),
            new Pociones("Llama Perpetua", "Produce una llama que nunca se apaga", 80.0, Pociones.Escuela.EVOCACION, Pociones.Tamanio.PEQUEÑO),
            new Pociones("Esencia del Alma", "Permite hablar con los muertos", 200.0, Pociones.Escuela.NIGROMANCIA, Pociones.Tamanio.GRANDE),
            new Pociones("Barrera Arcana", "Crea un escudo mágico protector", 100.0, Pociones.Escuela.ABJURACION, Pociones.Tamanio.MEDIANO),
            new Pociones("Elixir de Sabiduría", "Incrementa temporalmente la inteligencia", 150.0, Pociones.Escuela.DIVINACION, Pociones.Tamanio.PEQUEÑO),
            new Pociones("Poción de Encanto", "Aumenta el carisma temporalmente", 75.0, Pociones.Escuela.ENCANTAMIENTO, Pociones.Tamanio.MEDIANO),
            new Pociones("Transformación Menor", "Transforma objetos pequeños", 90.0, Pociones.Escuela.TRANSMUTACION, Pociones.Tamanio.MEDIANO),
            new Pociones("Poción de Resurrección", "Revive a una criatura recientemente fallecida", 500.0, Pociones.Escuela.CONJURACION, Pociones.Tamanio.GRANDE),
            new Pociones("Polvo de Sombras", "Permite moverse sigilosamente", 60.0, Pociones.Escuela.ILUSION, Pociones.Tamanio.PEQUEÑO),
            new Pociones("Fuerza de Titán", "Duplica la fuerza física temporalmente", 110.0, Pociones.Escuela.EVOCACION, Pociones.Tamanio.MEDIANO),
            new Pociones("Vínculo Etéreo", "Permite ver y tocar seres etéreos", 140.0, Pociones.Escuela.NIGROMANCIA, Pociones.Tamanio.GRANDE),
            new Pociones("Capa Protectora", "Reduce el daño recibido", 100.0, Pociones.Escuela.ABJURACION, Pociones.Tamanio.PEQUEÑO),
            new Pociones("Visión Profética", "Otorga visiones del futuro", 180.0, Pociones.Escuela.DIVINACION, Pociones.Tamanio.MEDIANO),
            new Pociones("Aura de Atracción", "Aumenta la persuasión y atracción", 95.0, Pociones.Escuela.ENCANTAMIENTO, Pociones.Tamanio.PEQUEÑO),
            new Pociones("Rayo Cambiante", "Transforma temporalmente la materia", 130.0, Pociones.Escuela.TRANSMUTACION, Pociones.Tamanio.MEDIANO),
            new Pociones("Curación Mayor", "Restaura una gran cantidad de salud", 300.0, Pociones.Escuela.CONJURACION, Pociones.Tamanio.GRANDE),
            new Pociones("Nieblas Ilusorias", "Crea un área cubierta de niebla ilusoria", 70.0, Pociones.Escuela.ILUSION, Pociones.Tamanio.MEDIANO),
            new Pociones("Piedra de Fuego", "Lanza una bola de fuego a un enemigo", 90.0, Pociones.Escuela.EVOCACION, Pociones.Tamanio.PEQUEÑO),
            new Pociones("Esencia Fantasmal", "Convoca un espíritu protector", 250.0, Pociones.Escuela.NIGROMANCIA, Pociones.Tamanio.GRANDE),
            new Pociones("Armadura Arcana", "Proporciona resistencia mágica", 150.0, Pociones.Escuela.ABJURACION, Pociones.Tamanio.MEDIANO),
            new Pociones("Ojo de Águila", "Permite ver a grandes distancias", 130.0, Pociones.Escuela.DIVINACION, Pociones.Tamanio.PEQUEÑO),
            new Pociones("Encanto de la Sirena", "Aumenta la capacidad de manipulación", 110.0, Pociones.Escuela.ENCANTAMIENTO, Pociones.Tamanio.PEQUEÑO),
            new Pociones("Elixir de Metales", "Convierte hierro en oro durante un tiempo limitado", 200.0, Pociones.Escuela.TRANSMUTACION, Pociones.Tamanio.GRANDE),
            new Pociones("Aura de Vitalidad", "Restaura salud lentamente con el tiempo", 180.0, Pociones.Escuela.CONJURACION, Pociones.Tamanio.MEDIANO),
            new Pociones("Espejo Ilusorio", "Crea un doble ilusorio", 85.0, Pociones.Escuela.ILUSION, Pociones.Tamanio.PEQUEÑO),
            new Pociones("Explosión Controlada", "Libera una onda de energía mágica", 120.0, Pociones.Escuela.EVOCACION, Pociones.Tamanio.MEDIANO),
            new Pociones("Toque de la Muerte", "Permite drenar vida de un objetivo", 350.0, Pociones.Escuela.NIGROMANCIA, Pociones.Tamanio.GRANDE),
            new Pociones("Barrera de Luz", "Bloquea ataques oscuros", 160.0, Pociones.Escuela.ABJURACION, Pociones.Tamanio.MEDIANO),
            new Pociones("Clarividencia Suprema", "Otorga visión del pasado y el futuro", 300.0, Pociones.Escuela.DIVINACION, Pociones.Tamanio.GRANDE),
            new Pociones("Elixir de TragoNublo","Te vuelve un esqueleto chiquitico",399.9, Pociones.Escuela.TRANSMUTACION, Pociones.Tamanio.PEQUEÑO)
    );

    public void crearPociones(){
        List<Ingredientes> ingredientesList = ingredientesCRUD.listaTodos();
        Map<Integer,Ingredientes> ingredientesMap = new HashMap<>();

        for(Ingredientes ingrediente : ingredientesList){
            ingredientesMap.put(ingrediente.getIdIngrediente(),ingrediente);
        }

        List<Map<Ingredientes, Integer>> recetasPorPocion = new ArrayList<>();

        // 1. Elixir de Vida
        Map<Ingredientes, Integer> ingredientesPocion1 = new HashMap<>();
        ingredientesPocion1.put(ingredientesMap.get(1), 2);  // Hoja de Mandragora
        ingredientesPocion1.put(ingredientesMap.get(10), 1); // Fruto de Serbal
        ingredientesPocion1.put(ingredientesMap.get(20), 1); // Fragmento de Corazón de Dragón
        recetasPorPocion.add(ingredientesPocion1);

        // 2. Poción de Invisibilidad
        Map<Ingredientes, Integer> ingredientesPocion2 = new HashMap<>();
        ingredientesPocion2.put(ingredientesMap.get(2), 1);  // Raiz de Belladona
        ingredientesPocion2.put(ingredientesMap.get(12), 2); // Fragmento de Obsidiana
        ingredientesPocion2.put(ingredientesMap.get(22), 1); // Plumas de Cuervo Nocturno
        recetasPorPocion.add(ingredientesPocion2);

        // 3. Llama Perpetua
        Map<Ingredientes, Integer> ingredientesPocion3 = new HashMap<>();
        ingredientesPocion3.put(ingredientesMap.get(3), 1);  // Flor de Luna
        ingredientesPocion3.put(ingredientesMap.get(18), 1); // Polvo de Hadas
        ingredientesPocion3.put(ingredientesMap.get(14), 1); // Esquirla de Jade Encantado
        recetasPorPocion.add(ingredientesPocion3);

        // 4. Esencia del Alma
        Map<Ingredientes, Integer> ingredientesPocion4 = new HashMap<>();
        ingredientesPocion4.put(ingredientesMap.get(4), 2);  // Hierba de San Juan
        ingredientesPocion4.put(ingredientesMap.get(9), 1);  // Espinas de Zarza Negra
        ingredientesPocion4.put(ingredientesMap.get(13), 1); // Piedra Filosofal (Imperfecta)
        recetasPorPocion.add(ingredientesPocion4);

        // 5. Barrera Arcana
        Map<Ingredientes, Integer> ingredientesPocion5 = new HashMap<>();
        ingredientesPocion5.put(ingredientesMap.get(5), 1);  // Alga Espectral
        ingredientesPocion5.put(ingredientesMap.get(11), 2); // Polvo de Estrella
        ingredientesPocion5.put(ingredientesMap.get(8), 1);  // Semillas de Heliotropo
        recetasPorPocion.add(ingredientesPocion5);

        // 6. Elixir de Sabiduría
        Map<Ingredientes, Integer> ingredientesPocion6 = new HashMap<>();
        ingredientesPocion6.put(ingredientesMap.get(6), 1);  // Musgo Brillante
        ingredientesPocion6.put(ingredientesMap.get(15), 1); // Minerales de Hierro Enriquecido
        ingredientesPocion6.put(ingredientesMap.get(24), 2); // Elixir de Metales
        recetasPorPocion.add(ingredientesPocion6);

        // 7. Poción de Encanto
        Map<Ingredientes, Integer> ingredientesPocion7 = new HashMap<>();
        ingredientesPocion7.put(ingredientesMap.get(7), 1);  // Corteza de Roble Ancestral
        ingredientesPocion7.put(ingredientesMap.get(13), 1); // Piedra Filosofal (Imperfecta)
        ingredientesPocion7.put(ingredientesMap.get(17), 2); // Polvo de Pirita
        recetasPorPocion.add(ingredientesPocion7);

        // 8. Transformación Menor
        Map<Ingredientes, Integer> ingredientesPocion8 = new HashMap<>();
        ingredientesPocion8.put(ingredientesMap.get(8), 1);  // Semillas de Heliotropo
        ingredientesPocion8.put(ingredientesMap.get(19), 1); // Arena Dorada de las Dunas Eternas
        ingredientesPocion8.put(ingredientesMap.get(10), 1); // Fruto de Serbal
        recetasPorPocion.add(ingredientesPocion8);

        // 9. Poción de Resurrección
        Map<Ingredientes, Integer> ingredientesPocion9 = new HashMap<>();
        ingredientesPocion9.put(ingredientesMap.get(1), 3);  // Hoja de Mandragora
        ingredientesPocion9.put(ingredientesMap.get(12), 1); // Fragmento de Obsidiana
        ingredientesPocion9.put(ingredientesMap.get(22), 1); // Plumas de Cuervo Nocturno
        recetasPorPocion.add(ingredientesPocion9);

        // 10. Polvo de Sombras
        Map<Ingredientes, Integer> ingredientesPocion10 = new HashMap<>();
        ingredientesPocion10.put(ingredientesMap.get(5), 1);  // Alga Espectral
        ingredientesPocion10.put(ingredientesMap.get(15), 2); // Minerales de Hierro Enriquecido
        ingredientesPocion10.put(ingredientesMap.get(23), 1); // Plumas de Ave Fenix Menor
        recetasPorPocion.add(ingredientesPocion10);

        // 11. Fuerza de Titán
        Map<Ingredientes, Integer> ingredientesPocion11 = new HashMap<>();
        ingredientesPocion11.put(ingredientesMap.get(3), 1);  // Flor de Luna
        ingredientesPocion11.put(ingredientesMap.get(6), 2);  // Musgo Brillante
        ingredientesPocion11.put(ingredientesMap.get(17), 1); // Polvo de Pirita
        recetasPorPocion.add(ingredientesPocion11);

        // 12. Vínculo Etéreo
        Map<Ingredientes, Integer> ingredientesPocion12 = new HashMap<>();
        ingredientesPocion12.put(ingredientesMap.get(4), 1);  // Hierba de San Juan
        ingredientesPocion12.put(ingredientesMap.get(7), 2);  // Corteza de Roble Ancestral
        ingredientesPocion12.put(ingredientesMap.get(20), 1); // Fragmento de Corazón de Dragón
        recetasPorPocion.add(ingredientesPocion12);

        // 13. Capa Protectora
        Map<Ingredientes, Integer> ingredientesPocion13 = new HashMap<>();
        ingredientesPocion13.put(ingredientesMap.get(5), 1);  // Alga Espectral
        ingredientesPocion13.put(ingredientesMap.get(8), 2);  // Semillas de Heliotropo
        ingredientesPocion13.put(ingredientesMap.get(21), 1); // Sombra de Árbol Eterno
        recetasPorPocion.add(ingredientesPocion13);

        // 14. Visión Profética
        Map<Ingredientes, Integer> ingredientesPocion14 = new HashMap<>();
        ingredientesPocion14.put(ingredientesMap.get(6), 1);  // Musgo Brillante
        ingredientesPocion14.put(ingredientesMap.get(19), 2); // Arena Dorada de las Dunas Eternas
        ingredientesPocion14.put(ingredientesMap.get(23), 1); // Plumas de Ave Fenix Menor
        recetasPorPocion.add(ingredientesPocion14);

        // 15. Aura de Atracción
        Map<Ingredientes, Integer> ingredientesPocion15 = new HashMap<>();
        ingredientesPocion15.put(ingredientesMap.get(7), 1);  // Corteza de Roble Ancestral
        ingredientesPocion15.put(ingredientesMap.get(17), 2); // Polvo de Pirita
        ingredientesPocion15.put(ingredientesMap.get(9), 1);  // Espinas de Zarza Negra
        recetasPorPocion.add(ingredientesPocion15);

        // 16. Rayo Cambiante
        Map<Ingredientes, Integer> ingredientesPocion16 = new HashMap<>();
        ingredientesPocion16.put(ingredientesMap.get(8), 1);  // Semillas de Heliotropo
        ingredientesPocion16.put(ingredientesMap.get(17), 2); // Polvo de Pirita
        ingredientesPocion16.put(ingredientesMap.get(14), 1); // Esquirla de Jade Encantado
        recetasPorPocion.add(ingredientesPocion16);

        // 17. Curación Mayor
        Map<Ingredientes, Integer> ingredientesPocion17 = new HashMap<>();
        ingredientesPocion17.put(ingredientesMap.get(1), 3);  // Hoja de Mandragora
        ingredientesPocion17.put(ingredientesMap.get(3), 1);  // Flor de Luna
        ingredientesPocion17.put(ingredientesMap.get(11), 2); // Polvo de Estrella
        recetasPorPocion.add(ingredientesPocion17);

        // 18. Nieblas Ilusorias
        Map<Ingredientes, Integer> ingredientesPocion18 = new HashMap<>();
        ingredientesPocion18.put(ingredientesMap.get(5), 1);  // Alga Espectral
        ingredientesPocion18.put(ingredientesMap.get(12), 1); // Fragmento de Obsidiana
        ingredientesPocion18.put(ingredientesMap.get(24), 1); // Elixir de Metales
        recetasPorPocion.add(ingredientesPocion18);

        // 19. Piedra de Fuego
        Map<Ingredientes, Integer> ingredientesPocion19 = new HashMap<>();
        ingredientesPocion19.put(ingredientesMap.get(3), 2);  // Flor de Luna
        ingredientesPocion19.put(ingredientesMap.get(10), 1); // Fruto de Serbal
        ingredientesPocion19.put(ingredientesMap.get(18), 1); // Polvo de Hadas
        recetasPorPocion.add(ingredientesPocion19);

        // 20. Esencia Fantasmal
        Map<Ingredientes, Integer> ingredientesPocion20 = new HashMap<>();
        ingredientesPocion20.put(ingredientesMap.get(4), 2);  // Hierba de San Juan
        ingredientesPocion20.put(ingredientesMap.get(15), 1); // Minerales de Hierro Enriquecido
        ingredientesPocion20.put(ingredientesMap.get(23), 1); // Plumas de Ave Fenix Menor
        recetasPorPocion.add(ingredientesPocion20);

        // 21. Armadura Arcana
        Map<Ingredientes, Integer> ingredientesPocion21 = new HashMap<>();
        ingredientesPocion21.put(ingredientesMap.get(5), 1);  // Alga Espectral
        ingredientesPocion21.put(ingredientesMap.get(8), 2);  // Semillas de Heliotropo
        ingredientesPocion21.put(ingredientesMap.get(17), 1); // Polvo de Pirita
        recetasPorPocion.add(ingredientesPocion21);

        // 22. Ojo de Águila
        Map<Ingredientes, Integer> ingredientesPocion22 = new HashMap<>();
        ingredientesPocion22.put(ingredientesMap.get(6), 1); // Musgo Brillante
        ingredientesPocion22.put(ingredientesMap.get(7), 1); // Corteza de Roble Ancestral
        ingredientesPocion22.put(ingredientesMap.get(22), 1); // Plumas de Cuervo Nocturno
        recetasPorPocion.add(ingredientesPocion22);

        // 23. Encanto de la Sirena
        Map<Ingredientes, Integer> ingredientesPocion23 = new HashMap<>();
        ingredientesPocion23.put(ingredientesMap.get(7), 2);  // Corteza de Roble Ancestral
        ingredientesPocion23.put(ingredientesMap.get(20), 1); // Fragmento de Corazón de Dragón
        ingredientesPocion23.put(ingredientesMap.get(17), 1); // Polvo de Pirita
        recetasPorPocion.add(ingredientesPocion23);

        // 24. Elixir de Metales
        Map<Ingredientes, Integer> ingredientesPocion24 = new HashMap<>();
        ingredientesPocion24.put(ingredientesMap.get(6), 2);  // Musgo Brillante
        ingredientesPocion24.put(ingredientesMap.get(19), 1); // Arena Dorada de las Dunas Eternas
        ingredientesPocion24.put(ingredientesMap.get(23), 1); // Plumas de Ave Fenix Menor
        recetasPorPocion.add(ingredientesPocion24);

        // 25. Aura de Vitalidad
        Map<Ingredientes, Integer> ingredientesPocion25 = new HashMap<>();
        ingredientesPocion25.put(ingredientesMap.get(1), 1);  // Hoja de Mandragora
        ingredientesPocion25.put(ingredientesMap.get(4), 2);  // Hierba de San Juan
        ingredientesPocion25.put(ingredientesMap.get(10), 1); // Fruto de Serbal
        recetasPorPocion.add(ingredientesPocion25);

        // 26. Espejo Ilusorio
        Map<Ingredientes, Integer> ingredientesPocion26 = new HashMap<>();
        ingredientesPocion26.put(ingredientesMap.get(8), 1);  // Cristales de Espejo
        ingredientesPocion26.put(ingredientesMap.get(15), 1); // Polvo de Estrella Fugaz
        ingredientesPocion26.put(ingredientesMap.get(11), 1); // Hoja de Luna Plateada
        recetasPorPocion.add(ingredientesPocion26);

        // 27. Explosión Controlada
        Map<Ingredientes, Integer> ingredientesPocion27 = new HashMap<>();
        ingredientesPocion27.put(ingredientesMap.get(2), 1);  // Sal del Infierno
        ingredientesPocion27.put(ingredientesMap.get(13), 2); // Raíz de Mandrágora
        ingredientesPocion27.put(ingredientesMap.get(14), 1); // Esencia de Fuego Infernal
        recetasPorPocion.add(ingredientesPocion27);

        // 28. Toque de la Muerte
        Map<Ingredientes, Integer> ingredientesPocion28 = new HashMap<>();
        ingredientesPocion28.put(ingredientesMap.get(9), 1);  // Pétalos de Rosa Negra
        ingredientesPocion28.put(ingredientesMap.get(12), 1); // Sombras de la Noche Eterna
        ingredientesPocion28.put(ingredientesMap.get(3), 2);  // Escamas de Dragón Oscuro
        recetasPorPocion.add(ingredientesPocion28);

        // 29. Barrera de Luz
        Map<Ingredientes, Integer> ingredientesPocion29 = new HashMap<>();
        ingredientesPocion29.put(ingredientesMap.get(14), 1); // Esencia de Fuego Infernal
        ingredientesPocion29.put(ingredientesMap.get(5), 2);  // Flor de Sol
        ingredientesPocion29.put(ingredientesMap.get(20), 1); // Fragmento de Corazón de Dragón
        recetasPorPocion.add(ingredientesPocion29);

        // 30. Clarividencia Suprema
        Map<Ingredientes, Integer> ingredientesPocion30 = new HashMap<>();
        ingredientesPocion30.put(ingredientesMap.get(1), 2);  // Hoja de Mandragora
        ingredientesPocion30.put(ingredientesMap.get(6), 1);  // Musgo Brillante
        ingredientesPocion30.put(ingredientesMap.get(16), 1); // Pétalos de Loto Celestial
        recetasPorPocion.add(ingredientesPocion30);

        // 31. Elixir de TragoNublo
        Map<Ingredientes, Integer> ingredientesPocion31 = new HashMap<>();
        ingredientesPocion31.put(ingredientesMap.get(17), 1); // Polvo de Pirita
        ingredientesPocion31.put(ingredientesMap.get(10), 2); // Fruto de Serbal
        ingredientesPocion31.put(ingredientesMap.get(23), 1); // Plumas de Ave Fenix Menor
        recetasPorPocion.add(ingredientesPocion31);

        for (int i = 0; i < 31; i++) {
            Pociones pocion = pociones.get(i); // Obtén la poción
            Map<Ingredientes, Integer> recetaIngredientes = recetasPorPocion.get(i); // Obtén los ingredientes de la receta

            // Inserta la poción junto con sus ingredientes
            pocionesCRUD.insertar(pocion, recetaIngredientes);
        }
    }


    public void insertarIngredientes(){
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();
        String query = "INSERT INTO Ingredientes (nombreIngrediente, tipoIngrediente) VALUES\n" +
                "('Hoja de Mandragora', 'VEGETAL'),\n" +
                "('Raiz de Belladona', 'VEGETAL'),\n" +
                "('Flor de Luna', 'VEGETAL'),\n" +
                "('Hierba de San Juan', 'VEGETAL'),\n" +
                "('Alga Espectral', 'VEGETAL'),\n" +
                "('Musgo Brillante', 'VEGETAL'),\n" +
                "('Corteza de Roble Ancestral', 'VEGETAL'),\n" +
                "('Semillas de Heliotropo', 'VEGETAL'),\n" +
                "('Espinas de Zarza Negra', 'VEGETAL'),\n" +
                "('Fruto de Serbal', 'VEGETAL'),\n" +
                "\n" +
                "('Polvo de Estrella', 'MINERAL'),\n" +
                "('Cristal de Cuarzo Puro', 'MINERAL'),\n" +
                "('Fragmento de Obsidiana', 'MINERAL'),\n" +
                "('Piedra Filosofal (Imperfecta)', 'MINERAL'),\n" +
                "('Esquirla de Jade Encantado', 'MINERAL'),\n" +
                "('Minerales de Hierro Enriquecido', 'MINERAL'),\n" +
                "('Polvo de Pirita', 'MINERAL'),\n" +
                "('Carbon Vivo', 'MINERAL'),\n" +
                "('Rocas de Basalto Infernal', 'MINERAL'),\n" +
                "('Sal de las Profundidades', 'MINERAL'),\n" +
                "\n" +
                "('Sangre de Dragon (Sustituto)', 'ORGANICO'),\n" +
                "('Huesos de Serpiente Voladora', 'ORGANICO'),\n" +
                "('Piel de Salamandra Escarlata', 'ORGANICO'),\n" +
                "('Escamas de Triton', 'ORGANICO'),\n" +
                "('Diente de Ogro', 'ORGANICO'),\n" +
                "('Plumas de Cuervo Nocturno', 'ORGANICO'),\n" +
                "('Corazon de Rana Gigante', 'ORGANICO'),\n" +
                "('Glandula de Veneno de Escorpion', 'ORGANICO'),\n" +
                "('Colmillo de Lobo Fantasma', 'ORGANICO'),\n" +
                "('Musculo de Golem de Barro', 'ORGANICO'),\n" +
                "\n" +
                "('Polvo de Hadas', 'MAGICO'),\n" +
                "('Lagrimas de Unicornio', 'MAGICO'),\n" +
                "('Fragmento de Runa Antigua', 'MAGICO'),\n" +
                "('Esencia de Elemental de Agua', 'MAGICO'),\n" +
                "('Cenizas de Fenix', 'MAGICO'),\n" +
                "('Energia Residual de un Portal', 'MAGICO'),\n" +
                "('Fragmento de Cristal Onirico', 'MAGICO'),\n" +
                "('Nectar de Arbol del Mundo', 'MAGICO'),\n" +
                "('Resplandor de Llama Eterna', 'MAGICO'),\n" +
                "('Fragmento de Corazon de Dragon', 'MAGICO'),\n" +
                "\n" +
                "('Aceite de Serpiente de Oro', 'ORGANICO'),\n" +
                "('Salvia Negra', 'VEGETAL'),\n" +
                "('Polvo de Carbon Encantado', 'MINERAL'),\n" +
                "('Polvo de Mariposa de Ebano', 'ORGANICO'),\n" +
                "('Resina de Arbol de Fuego', 'VEGETAL'),\n" +
                "('Perla de Agua Clara', 'MINERAL'),\n" +
                "('Arena Dorada de las Dunas Eternas', 'MINERAL'),\n" +
                "('Esencia de Niebla Arcana', 'MAGICO'),\n" +
                "('Plumas de Ave Fenix Menor', 'ORGANICO'),\n" +
                "('Sangre de Basilisco', 'ORGANICO');\n";
        session.createNativeQuery(query).executeUpdate();
        session.getTransaction().commit();
        session.close();
    }
}
