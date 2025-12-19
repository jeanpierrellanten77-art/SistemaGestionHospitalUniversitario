package utp.ac.pa.sistema;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import javax.swing.JOptionPane;
import utp.ac.pa.sistema.domain.*;

public class App {

    enum RolApp { ADMIN, DOCTOR, FARMACIA }

    static class AuditEntry {
        final LocalDateTime ts;
        final String usuario;
        final RolApp rol;
        final String accion;

        AuditEntry(String usuario, RolApp rol, String accion) {
            this.ts = LocalDateTime.now();
            this.usuario = usuario;
            this.rol = rol;
            this.accion = accion;
        }
    }

    static class HistEntry {
        final LocalDateTime ts;
        final String tipo;       // CITA, TRATAMIENTO, RECETA, FARMACIA
        final String referencia; // id o nota corta
        final String detalle;

        HistEntry(LocalDateTime ts, String tipo, String referencia, String detalle) {
            this.ts = ts;
            this.tipo = tipo;
            this.referencia = referencia;
            this.detalle = detalle;
        }
    }

    static class RecetaApp {
        final String id;
        final String pacienteNombre;
        final String medicoNombre;
        final LocalDateTime ts;
        final List<String> medicamentos;
        boolean dispensada;

        RecetaApp(String id, String pacienteNombre, String medicoNombre, List<String> meds) {
            this.id = id;
            this.pacienteNombre = pacienteNombre;
            this.medicoNombre = medicoNombre;
            this.ts = LocalDateTime.now();
            this.medicamentos = meds;
            this.dispensada = false;
        }
    }

    public static void main(String[] args) {

        Hospital hospital = new Hospital("H-UTP-001", "Hospital Universitario UTP", "San Miguelito");

        Medico m1 = new Medico("MED001", "Dr. Ricardo Mendoza");
        Medico m2 = new Medico("MED002", "Dra. Valeria González");
        Medico m3 = new Medico("MED003", "Dr. Samuel Ortega");
        Medico m4 = new Medico("MED004", "Dra. Laura Castillo");
        Medico m5 = new Medico("MED005", "Dr. Carlos Mendoza");

        hospital.agregarMedico(m1);
        hospital.agregarMedico(m2);
        hospital.agregarMedico(m3);
        hospital.agregarMedico(m4);
        hospital.agregarMedico(m5);

        Map<Medico, String> especialidades = new LinkedHashMap<>();
        especialidades.put(m1, "Cardiología");
        especialidades.put(m2, "Pediatría");
        especialidades.put(m3, "Medicina Interna");
        especialidades.put(m4, "Dermatología");
        especialidades.put(m5, "Dermatología");

        GestorPacientes gestorPacientes = new GestorPacientes();
        Paciente pIan = new Paciente("PAC001", "Ian Ramos", LocalDate.of(2007, 4, 21));
        Paciente pCris = new Paciente("PAC002", "Cristhofer Villarreal", LocalDate.of(2006, 5, 30));
        gestorPacientes.registrarPaciente(pIan);
        gestorPacientes.registrarPaciente(pCris);

        List<Cita> citas = new ArrayList<>();

        Map<String, List<HistEntry>> historial = new HashMap<>();
        historial.put(pIan.getNombre(), new ArrayList<>());
        historial.put(pCris.getNombre(), new ArrayList<>());

        Map<String, List<String>> tratamientos = new HashMap<>();
        tratamientos.put(pIan.getNombre(), new ArrayList<>());
        tratamientos.put(pCris.getNombre(), new ArrayList<>());

        Map<String, List<RecetaApp>> recetasPorPaciente = new HashMap<>();
        recetasPorPaciente.put(pIan.getNombre(), new ArrayList<>());
        recetasPorPaciente.put(pCris.getNombre(), new ArrayList<>());

        Map<String, Integer> inventario = new LinkedHashMap<>();
        inventario.put("Amoxicilina 500mg", 100);
        inventario.put("Ibuprofeno 400mg", 80);
        inventario.put("Loratadina 10mg", 50);

        List<AuditEntry> auditoria = new ArrayList<>();

        Map<String, String> cred = new HashMap<>();
        cred.put("admin", "1234");
        cred.put("doctor", "1234");
        cred.put("farmacia", "1234");

        Map<String, RolApp> roles = new HashMap<>();
        roles.put("admin", RolApp.ADMIN);
        roles.put("doctor", RolApp.DOCTOR);
        roles.put("farmacia", RolApp.FARMACIA);

        DateTimeFormatter dtFmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

        boolean cerrarSistema = false;

        while (!cerrarSistema) {

            String usuario = login(cred, roles);
            if (usuario == null) break;

            RolApp rolActual = roles.get(usuario);
            if (rolActual == null) break;

            auditoria.add(new AuditEntry(usuario, rolActual, "Inicio de sesión"));
            JOptionPane.showMessageDialog(null,
                    "Bienvenido/a: " + usuario + " (" + rolActual + ")\nHospital: " + hospital.getNombre(),
                    "Acceso", JOptionPane.INFORMATION_MESSAGE);

            boolean salirMenu = false;

            while (!salirMenu) {

                String opStr = JOptionPane.showInputDialog(
                        "SISTEMA HOSPITAL UNIVERSITARIO - UTP\n\n" +
                        "Rol: " + rolActual + " | Usuario: " + usuario + "\n\n" +
                        "1. Registrar paciente (ADMIN)\n" +
                        "2. Ver médicos por especialidad (TODOS)\n" +
                        "3. Agendar cita (ADMIN/DOCTOR)\n" +
                        "4. Registrar tratamiento y receta (DOCTOR)\n" +
                        "5. Farmacia: inventario / dispensar receta (FARMACIA)\n" +
                        "6. Ver historial clínico por paciente (ADMIN/DOCTOR)\n" +
                        "7. Ver auditoría (ADMIN)\n" +
                        "8. Salir (Resumen)\n" +
                        "9. Volver al inicio de sesión\n\n" +
                        "Seleccione una opción:"
                );

                if (opStr == null) {
                    // si cancelan el menú, lo tomamos como "volver al login"
                    auditoria.add(new AuditEntry(usuario, rolActual, "Canceló menú y volvió al login"));
                    salirMenu = true;
                    continue;
                }

                int op;
                try {
                    op = Integer.parseInt(opStr.trim());
                } catch (NumberFormatException e) {
                    JOptionPane.showMessageDialog(null, "Opción inválida (debe ser un número).");
                    continue;
                }

                switch (op) {

                    case 1 -> {
                        if (rolActual != RolApp.ADMIN) {
                            JOptionPane.showMessageDialog(null, "Acceso denegado. Solo ADMIN.");
                            break;
                        }

                        String nombreInput = JOptionPane.showInputDialog("Nombre del paciente:");
                        if (nombreInput == null) break;

                        String nombre = nombreInput.trim();

                        if (nombre.isBlank() || nombre.length() < 3 ||
                                !nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
                            JOptionPane.showMessageDialog(null,
                                    "Nombre inválido.\n- Mínimo 3 letras\n- Solo letras y espacios");
                            break;
                        }

                        boolean duplicado = false;
                        for (Paciente p : gestorPacientes.getPacientesRegistrados()) {
                            if (p.getNombre().equalsIgnoreCase(nombre)) {
                                duplicado = true;
                                break;
                            }
                        }
                        if (duplicado) {
                            JOptionPane.showMessageDialog(null, "Ese paciente ya está registrado.");
                            break;
                        }

                        String fechaStr = JOptionPane.showInputDialog("Fecha de nacimiento (YYYY-MM-DD):");
                        if (fechaStr == null) break;

                        try {
                            LocalDate nac = LocalDate.parse(fechaStr.trim());
                            if (nac.isAfter(LocalDate.now())) {
                                JOptionPane.showMessageDialog(null, "La fecha no puede ser futura.");
                                break;
                            }

                            int edad = Period.between(nac, LocalDate.now()).getYears();

                            String id = "PAC" + String.format("%03d", gestorPacientes.getPacientesRegistrados().size() + 1);
                            Paciente nuevo = new Paciente(id, nombre, nac);
                            gestorPacientes.registrarPaciente(nuevo);

                            historial.putIfAbsent(nombre, new ArrayList<>());
                            tratamientos.putIfAbsent(nombre, new ArrayList<>());
                            recetasPorPaciente.putIfAbsent(nombre, new ArrayList<>());

                            auditoria.add(new AuditEntry(usuario, rolActual, "Registró paciente: " + nombre));
                            JOptionPane.showMessageDialog(null, "Paciente registrado: " + nombre + "\nEdad: " + edad);

                        } catch (DateTimeParseException ex) {
                            JOptionPane.showMessageDialog(null, "Fecha inválida. Use YYYY-MM-DD.");
                        }
                    }

                    case 2 -> {
                        StringBuilder sb = new StringBuilder("MÉDICOS POR ESPECIALIDAD\n\n");
                        for (Map.Entry<Medico, String> e : especialidades.entrySet()) {
                            sb.append(e.getValue()).append(": ").append(e.getKey().getNombre()).append("\n");
                        }
                        auditoria.add(new AuditEntry(usuario, rolActual, "Consultó médicos por especialidad"));
                        JOptionPane.showMessageDialog(null, sb.toString(), "Médicos", JOptionPane.INFORMATION_MESSAGE);
                    }

                    case 3 -> {
 
 
                        if (!(rolActual == RolApp.ADMIN || rolActual == RolApp.DOCTOR)) {
                            JOptionPane.showMessageDialog(null, "Acceso denegado. Solo ADMIN/DOCTOR.");
                            break;
                        }

                        if (gestorPacientes.getPacientesRegistrados().isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No hay pacientes registrados.");
                            break;
                        }

                        Paciente paciente = seleccionarPaciente(gestorPacientes);
                        if (paciente == null) break;

                        Medico medico = seleccionarMedico(hospital);
                        if (medico == null) break;

                        LocalDateTime fechaHora = pedirFechaHora(dtFmt);
                        if (fechaHora == null) break;

                        if (fechaHora.isBefore(LocalDateTime.now())) {
                            JOptionPane.showMessageDialog(null, "La cita no puede ser en el pasado.");
                            break;
                        }

                        boolean pacienteDup = false;
                        boolean medicoOcupado = false;

                        for (Cita c : citas) {
                            if (c.getFechaHora().equals(fechaHora)) {
                                if (c.getPaciente().getNombre().equals(paciente.getNombre())) pacienteDup = true;
                                if (c.getMedico().getNombre().equals(medico.getNombre())) medicoOcupado = true;
                            }
                        }

                        if (pacienteDup) {
                            JOptionPane.showMessageDialog(null, "Duplicidad: el paciente ya tiene una cita en esa fecha/hora.");
                            break;
                        }
                        if (medicoOcupado) {
                            JOptionPane.showMessageDialog(null, "No disponible: el médico ya tiene una cita en esa fecha/hora.");
                            break;
                        }

                        Cita nueva = new Cita(paciente, medico, fechaHora);
                        citas.add(nueva);

                        historial.putIfAbsent(paciente.getNombre(), new ArrayList<>());
                        historial.get(paciente.getNombre()).add(
                                new HistEntry(fechaHora, "CITA", "C-" + (citas.size()), "Médico: " + medico.getNombre())
                        );

                        auditoria.add(new AuditEntry(usuario, rolActual,
                                "Agendó cita: " + paciente.getNombre() + " con " + medico.getNombre() + " (" + fechaHora.format(dtFmt) + ")"));

                        JOptionPane.showMessageDialog(null,
                                "Cita agendada:\nPaciente: " + paciente.getNombre() +
                                        "\nMédico: " + medico.getNombre() +
                                        "\nFecha: " + fechaHora.format(dtFmt));
                    }

                    case 4 -> {
                        if (rolActual != RolApp.DOCTOR) {
                            JOptionPane.showMessageDialog(null, "Acceso denegado. Solo DOCTOR.");
                            break;
                        }

                        Paciente paciente = seleccionarPaciente(gestorPacientes);
                        if (paciente == null) break;

                        Medico medico = seleccionarMedico(hospital);
                        if (medico == null) break;

                        String tratInput = JOptionPane.showInputDialog("Descripción del tratamiento:");
                        if (tratInput == null) break;
                        String trat = tratInput.trim();

                        if (trat.isBlank() || trat.length() < 5) {
                            JOptionPane.showMessageDialog(null, "Tratamiento inválido (muy corto).");
                            break;
                        }

                        tratamientos.putIfAbsent(paciente.getNombre(), new ArrayList<>());
                        tratamientos.get(paciente.getNombre()).add(trat);

                        historial.putIfAbsent(paciente.getNombre(), new ArrayList<>());
                        historial.get(paciente.getNombre()).add(
                                new HistEntry(LocalDateTime.now(), "TRATAMIENTO", "T-" + tratamientos.get(paciente.getNombre()).size(),
                                        "Médico: " + medico.getNombre() + " | " + trat)
                        );

                        String medsStr = JOptionPane.showInputDialog("Receta (medicamentos separados por coma):\nEj: Ibuprofeno 400mg, Loratadina 10mg");
                        if (medsStr == null) break;

                        List<String> meds = new ArrayList<>();
                        for (String s : medsStr.split(",")) {
                            String x = s.trim();
                            if (!x.isBlank()) meds.add(x);
                        }
                        if (meds.isEmpty()) {
                            JOptionPane.showMessageDialog(null, "No se agregaron medicamentos a la receta.");
                            break;
                        }

                        String idRec = "R-" + paciente.getNombre().replace(" ", "").toUpperCase() + "-" +
                                String.format("%03d", recetasPorPaciente.getOrDefault(paciente.getNombre(), new ArrayList<>()).size() + 1);

                        RecetaApp receta = new RecetaApp(idRec, paciente.getNombre(), medico.getNombre(), meds);

                        recetasPorPaciente.putIfAbsent(paciente.getNombre(), new ArrayList<>());
                        recetasPorPaciente.get(paciente.getNombre()).add(receta);

                        historial.get(paciente.getNombre()).add(
                                new HistEntry(LocalDateTime.now(), "RECETA", idRec, "Medicamentos: " + String.join(", ", meds))
                        );

                        auditoria.add(new AuditEntry(usuario, rolActual,
                                "Registró tratamiento/receta: " + paciente.getNombre() + " (" + idRec + ")"));

                        JOptionPane.showMessageDialog(null,
                                "Tratamiento y receta registrados.\nReceta: " + idRec +
                                        "\nMedicamentos: " + String.join(", ", meds));
                    }

                    case 5 -> {
                        if (rolActual != RolApp.FARMACIA) {
                            JOptionPane.showMessageDialog(null, "Acceso denegado. Solo FARMACIA.");
                            break;
                        }

                        String sub = JOptionPane.showInputDialog(
                                "FARMACIA\n\n" +
                                "1. Ver inventario\n" +
                                "2. Agregar/Actualizar stock\n" +
                                "3. Dispensar receta (descontar stock)\n\n" +
                                "Seleccione:"
                        );
                        if (sub == null) break;

                        int sop;
                        try { sop = Integer.parseInt(sub.trim()); }
                        catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Opción inválida."); break; }

                        switch (sop) {
                            case 1 -> {
                                StringBuilder sb = new StringBuilder("INVENTARIO\n\n");
                                for (Map.Entry<String, Integer> e : inventario.entrySet()) {
                                    sb.append("- ").append(e.getKey()).append(": ").append(e.getValue()).append("\n");
                                }
                                auditoria.add(new AuditEntry(usuario, rolActual, "Consultó inventario"));
                                JOptionPane.showMessageDialog(null, sb.toString());
                            }

                            case 2 -> {
                                String medInput = JOptionPane.showInputDialog("Medicamento (nombre exacto):");
                                if (medInput == null) break;
                                String med = medInput.trim();
                                if (med.isBlank()) { JOptionPane.showMessageDialog(null, "Nombre inválido."); break; }

                                String cantStr = JOptionPane.showInputDialog("Cantidad a sumar (entero, puede ser negativo):");
                                if (cantStr == null) break;

                                int cant;
                                try { cant = Integer.parseInt(cantStr.trim()); }
                                catch (NumberFormatException e) { JOptionPane.showMessageDialog(null, "Cantidad inválida."); break; }

                                int actual = inventario.getOrDefault(med, 0);
                                int nuevo = actual + cant;
                                if (nuevo < 0) {
                                    JOptionPane.showMessageDialog(null, "No puede quedar stock negativo.");
                                    break;
                                }
                                inventario.put(med, nuevo);

                                auditoria.add(new AuditEntry(usuario, rolActual, "Actualizó stock: " + med + " (" + actual + " -> " + nuevo + ")"));
                                JOptionPane.showMessageDialog(null, "Stock actualizado:\n" + med + " = " + nuevo);
                            }

                            case 3 -> {
                                Paciente paciente = seleccionarPaciente(gestorPacientes);
                                if (paciente == null) break;

                                List<RecetaApp> rlist = recetasPorPaciente.getOrDefault(paciente.getNombre(), new ArrayList<>());
                                if (rlist.isEmpty()) {
                                    JOptionPane.showMessageDialog(null, "Este paciente no tiene recetas registradas.");
                                    break;
                                }

                                RecetaApp receta = seleccionarReceta(rlist);
                                if (receta == null) break;

                                if (receta.dispensada) {
                                    JOptionPane.showMessageDialog(null, "Esa receta ya fue dispensada.");
                                    break;
                                }

                                for (String med : receta.medicamentos) {
                                    int stock = inventario.getOrDefault(med, 0);
                                    if (stock <= 0) {
                                        JOptionPane.showMessageDialog(null, "Sin stock suficiente para: " + med);
                                        receta = null;
                                        break;
                                    }
                                }
                                if (receta == null) break;

                                for (String med : receta.medicamentos) {
                                    inventario.put(med, inventario.getOrDefault(med, 0) - 1);
                                }
                                receta.dispensada = true;

                                historial.putIfAbsent(paciente.getNombre(), new ArrayList<>());
                                historial.get(paciente.getNombre()).add(
                                        new HistEntry(LocalDateTime.now(), "FARMACIA", receta.id,
                                                "Dispensada. Medicamentos: " + String.join(", ", receta.medicamentos))
                                );

                                auditoria.add(new AuditEntry(usuario, rolActual, "Dispensó receta: " + receta.id + " a " + paciente.getNombre()));
                                JOptionPane.showMessageDialog(null, "Receta dispensada correctamente: " + receta.id);
                            }

                            default -> JOptionPane.showMessageDialog(null, "Opción inválida.");
                        }
                    }

                    case 6 -> {
                        if (!(rolActual == RolApp.ADMIN || rolActual == RolApp.DOCTOR)) {
                            JOptionPane.showMessageDialog(null, "Acceso denegado. Solo ADMIN/DOCTOR.");
                            break;
                        }

                        Paciente paciente = seleccionarPaciente(gestorPacientes);
                        if (paciente == null) break;

                        List<HistEntry> h = historial.getOrDefault(paciente.getNombre(), new ArrayList<>());
                        h.sort(Comparator.comparing(x -> x.ts));

                        StringBuilder sb = new StringBuilder("HISTORIAL CLÍNICO\nPaciente: " + paciente.getNombre() + "\n\n");

                        if (h.isEmpty()) sb.append("Sin registros.\n");
                        else {
                            for (HistEntry e : h) {
                                sb.append("- [").append(e.ts.format(dtFmt)).append("] ")
                                        .append(e.tipo).append(" | ")
                                        .append(e.referencia).append("\n  ")
                                        .append(e.detalle).append("\n\n");
                            }
                        }

                        auditoria.add(new AuditEntry(usuario, rolActual, "Consultó historial: " + paciente.getNombre()));
                        JOptionPane.showMessageDialog(null, sb.toString());
                    }

                    case 7 -> {
                        if (rolActual != RolApp.ADMIN) {
                            JOptionPane.showMessageDialog(null, "Acceso denegado. Solo ADMIN.");
                            break;
                        }

                        StringBuilder sb = new StringBuilder("AUDITORÍA (últimas acciones)\n\n");
                        int start = Math.max(0, auditoria.size() - 20);
                        for (int i = start; i < auditoria.size(); i++) {
                            AuditEntry a = auditoria.get(i);
                            sb.append("- ").append(a.ts.format(dtFmt))
                                    .append(" | ").append(a.usuario)
                                    .append(" (").append(a.rol).append(")")
                                    .append(" -> ").append(a.accion).append("\n");
                        }
                        JOptionPane.showMessageDialog(null, sb.toString());
                    }

                    case 8 -> {
                        int conf = JOptionPane.showConfirmDialog(null, "¿Desea salir?", "Confirmación", JOptionPane.YES_NO_OPTION);
                        if (conf != JOptionPane.YES_OPTION) break;

                        auditoria.add(new AuditEntry(usuario, rolActual, "Cerró sesión y salió del sistema"));

                        StringBuilder sb = new StringBuilder("RESUMEN FINAL\n\n");
                        sb.append("Hospital: ").append(hospital.getNombre()).append("\n");
                        sb.append("Pacientes registrados: ").append(gestorPacientes.getPacientesRegistrados().size()).append("\n");
                        sb.append("Médicos registrados: ").append(hospital.getMedicos().size()).append("\n");
                        sb.append("Citas registradas: ").append(citas.size()).append("\n");
                        sb.append("Acciones en auditoría: ").append(auditoria.size()).append("\n\n");

                        sb.append("CITAS (últimas):\n");
                        int s = Math.max(0, citas.size() - 10);
                        for (int i = s; i < citas.size(); i++) {
                            Cita c = citas.get(i);
                            sb.append("- ").append(c.getFechaHora().format(dtFmt))
                                    .append(" | ").append(c.getPaciente().getNombre())
                                    .append(" con ").append(c.getMedico().getNombre())
                                    .append("\n");
                        }

                        JOptionPane.showMessageDialog(null, sb.toString(), "Resumen", JOptionPane.INFORMATION_MESSAGE);

                        salirMenu = true;
                        cerrarSistema = true;
                    }

                    case 9 -> {
                        auditoria.add(new AuditEntry(usuario, rolActual, "Cerró sesión y volvió al inicio de sesión"));
                        salirMenu = true; // vuelve al login
                    }

                    default -> JOptionPane.showMessageDialog(null, "Opción fuera de rango.");
                }
            }
        }
    }

    // -------------------- HELPERS --------------------

    static String login(Map<String, String> cred, Map<String, RolApp> roles) {
        for (int intentos = 0; intentos < 3; intentos++) {
            String user = JOptionPane.showInputDialog("LOGIN\nUsuario: (admin / doctor / farmacia)");
            if (user == null) return null;
            user = user.trim().toLowerCase();

            String pass = JOptionPane.showInputDialog("Contraseña:");
            if (pass == null) return null;
            pass = pass.trim();

            if (cred.containsKey(user) && cred.get(user).equals(pass) && roles.containsKey(user)) {
                return user;
            }
            JOptionPane.showMessageDialog(null, "Credenciales inválidas. Intento " + (intentos + 1) + "/3");
        }
        JOptionPane.showMessageDialog(null, "Demasiados intentos. Cerrando.");
        return null;
    }

    static Paciente seleccionarPaciente(GestorPacientes gestor) {
        List<Paciente> lista = gestor.getPacientesRegistrados();
        String[] nombres = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) nombres[i] = lista.get(i).getNombre();

        String sel = (String) JOptionPane.showInputDialog(
                null, "Seleccione paciente:", "Pacientes",
                JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]
        );
        if (sel == null) return null;

        for (Paciente p : lista) if (p.getNombre().equals(sel)) return p;
        return null;
    }

    static Medico seleccionarMedico(Hospital hospital) {
        List<Medico> lista = hospital.getMedicos();
        String[] nombres = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) nombres[i] = lista.get(i).getNombre();

        String sel = (String) JOptionPane.showInputDialog(
                null, "Seleccione médico:", "Médicos",
                JOptionPane.QUESTION_MESSAGE, null, nombres, nombres[0]
        );
        if (sel == null) return null;

        for (Medico m : lista) if (m.getNombre().equals(sel)) return m;
        return null;
    }

    static LocalDateTime pedirFechaHora(DateTimeFormatter dtFmt) {
        String s = JOptionPane.showInputDialog("Fecha y hora de la cita (dd/MM/yyyy HH:mm):");
        if (s == null) return null;
        try {
            return LocalDateTime.parse(s.trim(), dtFmt);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(null, "Formato inválido. Use: dd/MM/yyyy HH:mm");
            return null;
        }
    }

    static RecetaApp seleccionarReceta(List<RecetaApp> recetas) {
        String[] ids = new String[recetas.size()];
        for (int i = 0; i < recetas.size(); i++) {
            RecetaApp r = recetas.get(i);
            ids[i] = r.id + (r.dispensada ? " (DISPENSADA)" : "");
        }

        String sel = (String) JOptionPane.showInputDialog(
                null, "Seleccione receta:", "Recetas",
                JOptionPane.QUESTION_MESSAGE, null, ids, ids[0]
        );
        if (sel == null) return null;

        String id = sel.split(" ")[0].trim();
        for (RecetaApp r : recetas) if (r.id.equals(id)) return r;
        return null;
    }
}