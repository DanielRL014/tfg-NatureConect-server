package com.natureconect.natureConect.service;

import com.natureconect.natureConect.models.*;
import com.natureconect.natureConect.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.*;
import java.util.stream.Collectors;

/**
 * servicio para las publicaciones
 */
@Service
public class PublicacionService implements PublicacionServiceInterface {
    /**
     * ruta en la que se guardan las imagnes
     */
    private static final String UPLOAD_DIR = "uploads/publicaciones";
    /**
     * repositorio de las publicaciones
     */
    @Autowired
    private PublicacionRepository purp;
    /**
     * repositorio de los usuarios
     */
    @Autowired
    private UsuarioRepository usrp;
    /**
     * repositorio de las aves publicacion
     */
    @Autowired
    private AvePublicacionRepository aprp;
    /**
     * repositorio de las etiquetas publicacion
     */
    @Autowired
    private EtiquetaPublicacionRepository eprp;
    /**
     * repositorio de las aves
     */
    @Autowired
    private AveRepository avrp;
    /**
     * repositorio de las etiquetas
     */
    @Autowired
    private EtiquetaRepository etrp;
    /**
     * repositorio de los likes
     */
    @Autowired
    private LikeRepository lirp;

    /**
     * metodo para guardar la imagen
     * @param imagen imgen a guaradar
     * @return
     * @throws IOException
     */
    @Override
    public boolean subir(MultipartFile imagen) throws IOException {
        File dir = new File(UPLOAD_DIR);
        if (!dir.exists()) {
            dir.mkdirs();
        }


        String[] imageFiles = dir.list((d, name) -> name.matches(".*\\.(jpg|jpeg|png|gif)$"));
        int idFoto = (imageFiles != null ? imageFiles.length : 0) + 1;


        String extension = Optional.ofNullable(imagen.getOriginalFilename())
                .filter(f -> f.contains("."))
                .map(f -> f.substring(f.lastIndexOf(".") + 1))
                .orElse("jpg");

        String fileName = idFoto + "." + extension;
        Path imagePath = Paths.get(UPLOAD_DIR, fileName);
        Files.write(imagePath, imagen.getBytes());

        return true;
    }

    /**
     * metodo para crear la publicacion
     * @param idUsuario identificador del usuario
     * @param latitud latitud de la publicacion
     * @param longitud longitud de la publicacion
     * @return
     * @throws IOException
     */
    @Override
    public Publicacion crearPublicacion(Integer idUsuario, String latitud, String longitud) throws IOException {
        File dir = new File(UPLOAD_DIR);


        String[] imageFiles = dir.list((d, name) -> name.matches(".*\\.(jpg|jpeg|png|gif)$"));
        int idFoto = (imageFiles != null ? imageFiles.length : 0) ;



        Publicacion publicacion = new Publicacion();
        publicacion.setIdPublicacion(String.valueOf(idFoto));
        publicacion.setIdUsuario( usrp.findById(idUsuario).get());
        publicacion.setLatitud(latitud);
        publicacion.setLongitud(longitud);
        publicacion.setFecha(LocalDate.now());
        publicacion.setMeGustas(0);
        publicacion.setIdFoto(String.valueOf(idFoto));

        return purp.save(publicacion);
    }

    /**
     * metodo para obtener las publicaciones
     * @return
     * @throws IOException
     */
    @Override
    public List<Publicacion> obtenerPublicacionesU() throws IOException {
        return purp.findPublicacionesSinAvesProtegidas() ;
    }

    /**
     * metodo para obtener una publicacion
     * @param idPublicacion identificador de la publicacion
     * @return
     * @throws IOException
     */
    @Override
    public Optional<Publicacion> obtenerPublicacion(String idPublicacion) throws IOException {
        return  purp.findById(idPublicacion);
    }

    /**
     * metodo para obtener una publicacion y si tiene like de un usuario
     * @param idPublicacion identificador de la publicacion
     * @param idUsuario identificador del usuario
     * @return
     * @throws IOException
     */
    @Override
    public publicacionConLike obtenerPublicacionLike(String idPublicacion,Integer idUsuario) throws IOException {
        Optional<Usuario> u=usrp.findById(idUsuario);
        Optional<Publicacion> p=purp.findById(idPublicacion);
        if (u.isPresent() && p.isPresent()) {
           Optional<Like> i= lirp.findByIdUsuarioAndIdPublicacion(u.get(),p.get());
           if (i.isPresent()) {
               publicacionConLike pl=new publicacionConLike();
               pl.setIdPublicacion(p.get().getIdPublicacion());
               pl.setIdUsuario(p.get().getIdUsuario());
               pl.setIdFoto(p.get().getIdFoto());
               pl.setMeGustas(p.get().getMeGustas());
               pl.setLatitud(p.get().getLatitud());
               pl.setLongitud(p.get().getLongitud());
               pl.setFecha(p.get().getFecha());
               pl.setAvesPublicacions(p.get().getAvesPublicacions());
               pl.setEtiquetaPublicacions(p.get().getEtiquetaPublicacions());
               pl.setLikes(p.get().getLikes());
               pl.setHasLiked(true);
               return pl;
           }else{
               publicacionConLike pl=new publicacionConLike();
               pl.setIdPublicacion(p.get().getIdPublicacion());
               pl.setIdUsuario(p.get().getIdUsuario());
               pl.setIdFoto(p.get().getIdFoto());
               pl.setMeGustas(p.get().getMeGustas());
               pl.setLatitud(p.get().getLatitud());
               pl.setLongitud(p.get().getLongitud());
               pl.setFecha(p.get().getFecha());
               pl.setAvesPublicacions(p.get().getAvesPublicacions());
               pl.setEtiquetaPublicacions(p.get().getEtiquetaPublicacions());
               pl.setLikes(p.get().getLikes());
               pl.setHasLiked(false);
               return pl;
           }
        }

        return null;
    }

    /**
     * metodo para añaidr un ave a una publicacion
     * @param idPublicacion identificador de la publicacio
     * @param idAve identificador de la ave
     * @return
     * @throws IOException
     */
    @Override
    public Boolean anadirAvePublicacion(String idPublicacion, Integer idAve) throws IOException {
        Optional<Publicacion> publicacion =purp.findById(idPublicacion);
        Optional<Ave> ave =  avrp.findById(idAve);
        if(ave.isPresent() && publicacion.isPresent()) {
            AvesPublicacion ap = new AvesPublicacion();
            ap.setIdPublicacion(publicacion.get());
            ap.setIdAve(ave.get());
            aprp.save(ap);
            return true;
        }else{
            return false;
            }
    }

    /**
     * metodo para añadir una etiqueta una publicacion
     * @param idPublicacion identificador de la publicacio
     * @param idEtiqueta identificador de la etiqueta
     * @return
     * @throws IOException
     */
    @Override
    public Boolean anadirEtiquetaPublicacion(String idPublicacion, Integer idEtiqueta) throws IOException {
        Optional<Publicacion> publicacion =purp.findById(idPublicacion);
        Optional<Etiqueta> etiqueta = etrp.findById(idEtiqueta);
        if(etiqueta.isPresent() && publicacion.isPresent()) {
            EtiquetaPublicacion ep = new EtiquetaPublicacion();
            ep.setIdPublicacion(publicacion.get());
            ep.setIdEtiqueta(etiqueta.get());
            eprp.save(ep);
            return true;
        }else{
            return false;
        }
    }

    /**
     * metodo para obtener las publicaciones de un usuario
     * @param idUsuario identificador del usuario
     * @return
     * @throws IOException
     */
    @Override
    public List<Publicacion> obtenerPublicacionesUP(Integer idUsuario) throws IOException {
        Optional<Usuario> u=usrp.findById(idUsuario);
        if(u.isPresent()) {
            return purp.findByIdUsuario(u.get());
        }
        return null;
    }

    /**
     * metod para dar like a una publicacion
     * @param idPublicacion identificador de la publicacio
     * @param idUsuario identificador del usuario
     * @return
     * @throws IOException
     */
    @Override
    public Boolean darLike(String idPublicacion, Integer idUsuario) throws IOException {
        Optional<Publicacion> publicacion = purp.findById(idPublicacion);
        Optional<Usuario> usu = usrp.findById(idUsuario);
        if(usu.isPresent() && publicacion.isPresent()) {
            Optional<Like> like = lirp.findByIdUsuarioAndIdPublicacion(usu.get(),publicacion.get());
            if(like.isPresent()){
                return false;
            }else{
                publicacion.get().setMeGustas(publicacion.get().getMeGustas()+1);
                Like like1 = new Like();
                like1.setIdPublicacion(publicacion.get());
                like1.setIdUsuario(usu.get());
                like1.setFecha(LocalDate.now());
                lirp.save(like1);
                purp.save(publicacion.get());
                return true;
            }

        }else{
            return false;
        }
    }

    /**
     * metod para quitar like a una publicacion
     * @param idPublicacion identificador de la publicacio
     * @param idUsuario identificador del usuario
     * @return
     * @throws IOException
     */
    @Override
    public Boolean quitarLike(String idPublicacion, Integer idUsuario) throws IOException {
        Optional<Publicacion> publicacion =  purp.findById(idPublicacion);
        Optional<Usuario> usu =  usrp.findById(idUsuario);
        if(usu.isPresent()  && publicacion.isPresent()) {
            Optional<Like> like = lirp.findByIdUsuarioAndIdPublicacion(usu.get(),publicacion.get());
            if(like.isPresent()){
                publicacion.get().setMeGustas(publicacion.get().getMeGustas()-1);
                lirp.delete(like.get());
                purp.save(publicacion.get());
                return true;
            }else{

                return false;
            }

        }else{
            return false;
        }
    }

    /**
     * metodo para buscar publicaciones con un ave
      * @param texto nombre del ave
     * @return
     * @throws IOException
     */
    @Override
    public List<Publicacion> buscarPorAve(String texto) throws IOException {
        List<Publicacion> publicaciones;

        if (texto != null && !texto.trim().isEmpty()) {
            publicaciones = purp.findDistinctByNombreComunAveContaining(texto);
        } else {
            publicaciones = purp.findPublicacionesSinAvesProtegidas();
        }

        return publicaciones;
    }

    /**
     * metodo para buscar publicaciones con un ave de una familia
     * @param texto nombre de la familia
     * @return
     * @throws IOException
     */
    @Override
    public List<Publicacion> filtarFamilia(String texto) throws IOException {
        List<Publicacion> publicaciones;

        if (texto != null && !texto.trim().isEmpty()) {
            publicaciones = purp.findDistinctByFamiliaAveContaining(texto);
        } else {
            publicaciones = purp.findPublicacionesSinAvesProtegidas();
        }

        return publicaciones;
    }

    /**
     * metodo para obtener los datos de observaciones de una especie
     * @param especieId identificador de la especie
     * @param desde fecha de inicio
     * @param hasta fecha de fin
     * @return
     */
    public Map<String, Object> obtenerDatosVisualizacion(Long especieId, LocalDate desde, LocalDate hasta) {
        if (desde == null) desde = LocalDate.of(2000, 1, 1);
        if (hasta == null) hasta = LocalDate.now();

        List<Publicacion> publicaciones = purp.findByAveAndFechaBetween(especieId, desde, hasta);

        List<Map<String, Object>> mapaCalor = publicaciones.stream()
                .map(pub -> {
                    Map<String, Object> mapa = new HashMap<>();
                    mapa.put("lat", pub.getLatitud());
                    mapa.put("lng", pub.getLongitud());
                    mapa.put("fecha", pub.getFecha().toString());
                    return mapa;
                })
                .collect(Collectors.toList());

        Map<String, Integer> barrasMensuales = new TreeMap<>();
        Map<Integer, Integer> lineaAnual = new TreeMap<>();

        for (Publicacion pub : publicaciones) {
            LocalDate fecha = pub.getFecha();
            String mes = fecha.getMonth().getDisplayName(TextStyle.FULL, Locale.getDefault());
            int anio = fecha.getYear();

            if (anio == hasta.getYear()) {
                barrasMensuales.put(mes, barrasMensuales.getOrDefault(mes, 0) + 1);
            }

            lineaAnual.put(anio, lineaAnual.getOrDefault(anio, 0) + 1);
        }

        return Map.of(
                "mapaCalor", mapaCalor,
                "barrasMensuales", Map.of(String.valueOf(hasta.getYear()), barrasMensuales),
                "lineaAnual", lineaAnual.entrySet().stream()
                        .map(e -> Map.of("anio", e.getKey(), "total", e.getValue()))
                        .collect(Collectors.toList())
        );
    }

}
