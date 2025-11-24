package utp.ac.pa.sistema;

import utp.ac.pa.sistema.domain.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class App {
    public static void main(String[] args) {

        System.out.println("======= SISTEMA DE GESTIÓN DE HOSPITAL UNIVERSITARIO =======\n");

        Hospital hospital = new Hospital("H-SMA-001", "Hospital de la Universidad Tecnologica de Panamá", "Tumba Muerto");
        System.out.println("Hospital creado: " + hospital.getNombre() + " | Ciudad: " + hospital.getDireccion());

        Especialidad cardiologia = new Especialidad("ESP01", "Cardiología");
        Especialidad pediatria = new Especialidad("ESP02", "Pediatría");
        Especialidad medInterna = new Especialidad("ESP03", "Medicina Interna");
        Especialidad dermatologia = new Especialidad("ESP04", "Dermatología");

        Medico m1 = new Medico("MED001", "Dr. Ricardo Mendoza");
        Medico m2 = new Medico("MED002", "Dra. Valeria González");
        Medico m3 = new Medico("MED003", "Dr. Samuel Ortega");
        Medico m4 = new Medico("MED004", "Dra. Laura Castillo");

        m1.addEspecialidad(cardiologia);
        m2.addEspecialidad(pediatria);
        m3.addEspecialidad(medInterna);
        m4.addEspecialidad(dermatologia);

        cardiologia.asignarMedico(m1);
        pediatria.asignarMedico(m2);
        medInterna.asignarMedico(m3);
        dermatologia.asignarMedico(m4);

        hospital.agregarMedico(m1);
        hospital.agregarMedico(m2);
        hospital.agregarMedico(m3);
        hospital.agregarMedico(m4);

        System.out.println("\nMédicos registrados en el hospital:");
        for (Medico m : hospital.getMedicos()) {
            System.out.println(" - " + m.getNombre() + " | Especialidades: " + m.getEspecialidades().size());
        }

        Paciente p1 = new Paciente("PAC001", "Ian Ramos", LocalDate.of(2007, 4, 21));
        Paciente p2 = new Paciente("PAC002", "Cristhofer Villarreal", LocalDate.of(2006, 5, 30));
        p1.registrar();
        p2.registrar();

        GestorPacientes gestorPacientes = new GestorPacientes();
        gestorPacientes.registrarPaciente(p1);
        gestorPacientes.registrarPaciente(p2);

        gestorPacientes.registrarMedico(m1);
        gestorPacientes.registrarMedico(m2);
        gestorPacientes.registrarMedico(m3);
        gestorPacientes.registrarMedico(m4);

        System.out.println("\nPacientes registrados:");
        for (Paciente p : gestorPacientes.getPacientesRegistrados()) {
            System.out.println(" - " + p.getNombre() + " | Edad: " + p.getEdad());
        }

        HistorialClinico historial1 = gestorPacientes.crearHistorial(p1);
        historial1.agregarEntrada(new EntradaHistorial(LocalDateTime.now(), "Consulta General", "REF001"));
        historial1.agregarEntrada(new EntradaHistorial(LocalDateTime.now(), "Chequeo Cardiología", "REF002"));

        System.out.println("\nHistorial clínico de " + p1.getNombre() + ":");
        for (EntradaHistorial e : historial1.obtenerCronologico()) {
            System.out.println(" - " + e);
        }

        GestorCitas gestorCitas = new GestorCitas();
        boolean citaOK = gestorCitas.agendarCita(p1, m1, LocalDateTime.now().plusDays(1));
        System.out.println("\nCita creada correctamente: " + citaOK);

        System.out.println("\nCitas registradas:");
        for (Cita c : gestorCitas.getCitasRegistradas()) {
            System.out.println(" - " + c);
        }

        Tratamiento tratamientoIan = new Tratamiento("Tratamiento antibiótico por infección respiratoria", m3);
        tratamientoIan.agregarObservacion("Tomar 1 cápsula cada 8 horas");
        tratamientoIan.agregarObservacion("Reposo de 48 horas");

        System.out.println("\nTratamiento para " + p1.getNombre() + ":");
        System.out.println(" - Descripción: " + tratamientoIan.getDescripcion());
        System.out.println(" - Observaciones: " + tratamientoIan.getObservaciones());

        Receta receta1 = new Receta(tratamientoIan);
        Medicamento amoxicilina = new Medicamento("MED01", "Amoxicilina 500mg", 5.25);
        Medicamento ibuprofeno = new Medicamento("MED02", "Ibuprofeno 400mg", 3.75);
        receta1.agregarItem(amoxicilina, 10);
        receta1.agregarItem(ibuprofeno, 5);

        System.out.println("\nReceta generada:");
        System.out.println(" - ID: " + receta1.getIdReceta());
        System.out.println(" - Items: " + receta1.getItems().size());

        Farmacia farmacia = new Farmacia("FARM001");
        farmacia.getInventario().registrarEntrada(amoxicilina, 100);
        farmacia.getInventario().registrarEntrada(ibuprofeno, 70);

        System.out.println("\nInventario inicial:");
        for (var entry : farmacia.getInventario().getRegistros().entrySet()) {
            System.out.println(" - " + entry.getKey().getNombre() + ": " + entry.getValue());
        }

        boolean dispensado = farmacia.dispensarReceta(receta1);
        System.out.println("\nReceta dispensada: " + dispensado);

        System.out.println("\nInventario después de dispensar:");
        for (var entry : farmacia.getInventario().getRegistros().entrySet()) {
            System.out.println(" - " + entry.getKey().getNombre() + ": " + entry.getValue());
        }

        Rol rolAdmin = new Rol("R001", "Administrador");
        rolAdmin.addPermiso("GESTIONAR_USUARIOS");
        Usuario admin = new Usuario("U001", "admin", "1234", rolAdmin);

        Auditoria audit = Auditoria.registrarAccion("Inicio de sesión del administrador", admin);
        GeneradorReportes gen = new GeneradorReportes();
        gen.addAuditoria(audit);
        Report reporte = gen.generarReporteEventos(null);

        System.out.println("\nReporte del sistema:");
        System.out.println(reporte.getTitle());
        System.out.println(reporte.getContent());
    }
}