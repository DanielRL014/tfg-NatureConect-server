package com.natureconect.natureConect.controllers;

import com.natureconect.natureConect.models.Publicacion;
import com.natureconect.natureConect.models.publicacionConLike;
import com.natureconect.natureConect.service.PublicacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * controlador para las publicaciones
 */
@RestController
@RequestMapping("/api/publicacion")
public class PublicacionController {
    /**
     * servicio para las notificaiones
     */
    @Autowired
    private PublicacionService publicacionService;

    /**
     * metodo para subir una imagne
     * @param imagen imagen a guardar
     * @return
     */
    @PostMapping("/Subir")
    public ResponseEntity<?> subir(
            @RequestPart("imagen") MultipartFile imagen
    ) {
        try {
            boolean subidad = publicacionService.subir( imagen);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "subida exitosamente",
                    "id_publicacion", subidad
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }

    }

    /**
     * metodo para crear la publicacion
     * @param idUsuario identificador del usuario
     * @param latitud latitud de la publicacion
     * @param longitud longitud de la publicacion
     * @return
     */
    @PostMapping("/crearP")
    public ResponseEntity<?> crearPublicacion(
            @RequestParam("id_usuario") Integer idUsuario,
            @RequestParam("latitud") String latitud,
            @RequestParam("longitud") String longitud
    ) {
        try {
            Publicacion publicacion = publicacionService.crearPublicacion(idUsuario, latitud,longitud);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Publicación creada exitosamente",
                    "data", publicacion
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    /**
     * metodo para devolver las publicaciones
     * @return
     */
    @GetMapping("/listar")
    public ResponseEntity<?> obtenerPublicacionesU() {
        try {
            List<Publicacion> publicaciones = publicacionService.obtenerPublicacionesU();

            if (publicaciones.isEmpty()) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "success", false,
                        "message", "no hay publicaciones"
                ));
            }

            return ResponseEntity.ok( Map.of(
                    "success", true,
                    "message", "Publicaciones enviadas",
                    "data", publicaciones
                        ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    /**
     * metodo para ver una publicacion
     * @param idPublicacion identificador de la publicacion
     * @param idUsuario identificador del usuario
     * @return
     */
    @PostMapping("/ver")
    public ResponseEntity<?> obtenerPublicacion(
            @RequestParam("id_publicacion") String idPublicacion,
            @RequestParam("id_usuario") Integer idUsuario
    ) {
        try {
            publicacionConLike publicacion = publicacionService.obtenerPublicacionLike(idPublicacion, idUsuario);


            if(publicacion!=null) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Publicacion enviad",
                        "data", publicacion
                ));
            }else{
                return ResponseEntity.ok(Map.of(
                        "success", false,
                        "message", "Publicacion no encontrada"
                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    /**
     * metodo para añadir una ave a una publicacion
     * @param idPublicacion identificador de la publicacion
     * @param idAve identificador de la ave
     * @return
     */
    @PostMapping("/nave")
    public ResponseEntity<?> navePublicacion( @RequestParam("id_publicacion") String idPublicacion, @RequestParam("id_ave") Integer idAve){
        try {
            if ( publicacionService.anadirAvePublicacion(idPublicacion,idAve)) {
                return ResponseEntity.ok("ave incluida");
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "success", false,
                        "message","error al añadir el ave" ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()));
        }
    }

    /**
     * metodo para añadir etiquetas a una publicacion
     * @param idPublicacion identificador de la publicacion
     * @param idEtiqueta identificador de la etiqueta
     * @return
     */
    @PostMapping("/nEtiqueta")
    public ResponseEntity<?> nEtiquetaPublicacion( @RequestParam("id_publicacion") String idPublicacion, @RequestParam("id_etiqueta") String idEtiqueta){
        try {
            if (publicacionService.anadirEtiquetaPublicacion(idPublicacion,Integer.valueOf(idEtiqueta))){
                return ResponseEntity.ok("etiqueta incluida");
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "success", false,
                        "message", "error al añadir la etiqueta"));
            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()));
        }
    }

    /**
     * metodo para obtener las publicaciones de un usuario
     * @param id identificador del usuario
     * @return
     */
    @PostMapping("/misPublicaciones")
    public ResponseEntity<?> misPublicaciones(@RequestParam("id_usuario") String id ){
        try {
            List<Publicacion> publicaciones = publicacionService.obtenerPublicacionesUP(Integer.valueOf(id));



            return ResponseEntity.ok( Map.of(
                    "success", true,
                    "message", "Publicaciones enviadas",
                    "data", publicaciones
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()));
        }
    }

    /**
     * metodo para ver una publicacion para los invitados
     * @param idPublicacion identificador de la publicacion
     * @return
     */
    @PostMapping("/verInvitado")
    public ResponseEntity<?> obtenerPublicacionIn(
            @RequestParam("id_publicacion") String idPublicacion
    ) {
        try {
            Optional<Publicacion> publicacion = publicacionService.obtenerPublicacion(idPublicacion);


            if(publicacion.isPresent()) {
                return ResponseEntity.ok(Map.of(
                        "success", true,
                        "message", "Publicacion enviad",
                        "data", publicacion.get()
                ));
            }else{
                return ResponseEntity.ok(Map.of(
                        "success", false,
                        "message", "Publicacion no encontrada"

                ));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()
            ));
        }
    }

    /**
     * metodo para dar me guasta a una publicacion
     * @param idPublicacion identificador de la publicacion
     * @param idUsuario identificador del usuario
     * @return
     */
    @PostMapping("/dar")
    public ResponseEntity<?> darLike( @RequestParam("id_publicacion") String idPublicacion, @RequestParam("id_usuario") String idUsuario){
        try {
            if (publicacionService.darLike(idPublicacion,Integer.valueOf(idUsuario))){
                publicacionConLike publicacion = publicacionService.obtenerPublicacionLike(idPublicacion, Integer.valueOf(idUsuario));
                if(publicacion!=null) {
                    return ResponseEntity.ok(Map.of(
                            "success", true,
                            "message", "like dado",
                            "data", publicacion
                    ));
                }else{
                    return ResponseEntity.ok(Map.of(
                            "success", false,
                            "message", "Publicacion no encontrada"
                    ));
                }
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "success", false,
                        "message", "error al dar like"));
            }


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()));
        }
    }

    /**
     * metodo para quitar me guasta a una publicacion
     * @param idPublicacion identificador de la publicacion
     * @param idUsuario identificador del usuario
     * @return
     */
    @PostMapping("/quitar")
    public ResponseEntity<?> quitarLike( @RequestParam("id_publicacion") String idPublicacion, @RequestParam("id_usuario") String idUsuario){
        try {
            if (publicacionService.quitarLike(idPublicacion,Integer.valueOf(idUsuario))){
                publicacionConLike publicacion = publicacionService.obtenerPublicacionLike(idPublicacion, Integer.valueOf(idUsuario));
                if(publicacion!=null) {
                    return ResponseEntity.ok(Map.of(
                            "success", true,
                            "message", "like quitado",
                            "data", publicacion
                    ));
                }else{
                    return ResponseEntity.ok(Map.of(
                            "success", false,
                            "message", "Publicacion no encontrada"
                    ));
                }
            }else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                        "success", false,
                        "message", "error al quitar like"));
            }


        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()));
        }
    }

    /**
     * metodo para buscar publicaciones por el nombre de un ave
     * @param texto nombre del ave
     * @return
     */
    @PostMapping("/Buscar")
    public ResponseEntity<?> buscarPorAves(@RequestParam(value = "texto", required = false) String texto) {
        try{
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Publicaciones obtenidas exitosamente",
                    "data", publicacionService.buscarPorAve(texto)
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()));
        }
    }

    /**
     * metodo para obtener publicaciones de una familia
     * @param texto nombre de la familia
     * @return
     */
    @PostMapping("/filtrarFamilia")
    public ResponseEntity<?> FiltrarFamilia(@RequestParam(value = "familia", required = false) String texto) {
        try{
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Publicaciones obtenidas exitosamente",
                    "data", publicacionService.filtarFamilia(texto)
            ));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", e.getMessage()));
        }
    }

    /**
     * metodo para obtener los datos de observaciones de una especie
     * @param id identificador de la especie
     * @param desde fecha de inicio
     * @param hasta fecha de fin
     * @return
     */
    @GetMapping("/especie")
    public ResponseEntity<?> obtenerDatosVisualizacion(
            @RequestParam Long id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate desde,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate hasta
    ) {
        try {
            Map<String, Object> datos = publicacionService.obtenerDatosVisualizacion(id, desde, hasta);
            return ResponseEntity.ok(Map.of(
                    "success", true,
                    "message", "Datos de observaciones obtenidos correctamente",
                    "data", datos
            ));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Map.of(
                    "success", false,
                    "message", "Error al obtener los datos",
                    "error", e.getMessage()
            ));
        }
    }

}
