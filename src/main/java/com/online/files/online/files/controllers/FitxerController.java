package com.online.files.online.files.controllers;

import java.io.IOException;
import java.text.ParseException;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.online.files.online.files.DTO.FileFolderDTO;
import com.online.files.online.files.DTO.FitxerDTO;
import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.models.User;
import com.online.files.online.files.models.fitxers.Fitxer;
import com.online.files.online.files.models.fitxers.FitxerBD;
import com.online.files.online.files.repositories.FitxerRepository;
import com.online.files.online.files.repositories.UserRepository;
import com.online.files.online.files.services.FitxerBDService;
import com.online.files.online.files.services.FitxerService;
import com.online.files.online.files.services.FitxerUsuariService;
import com.online.files.online.files.services.UserService;

@RequestMapping(path = "/fitxers")
@RestController
public class FitxerController
{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private FitxerBDService fitxerBDService;

    @Autowired
    private FitxerService fitxerService;

    @Autowired
    private FitxerUsuariService fitxerUsuariService;

    @Autowired
    private UserService userService;

    @GetMapping()
    public Collection<Fitxer> getFitxers(){ return fitxerService.getFitxers();}

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @path on volem penjar el fitxer
     * @param file amb el nou fitxer
     * @return L'usuari amb l'estructura actualitzada amb el fitxer penjat
     */
    @PostMapping(path = "/{username}/upload")
    public ResponseEntity <User> pujarFitxer(@PathVariable("username") String username,
            @RequestParam("path") String path,
            @RequestParam("file") MultipartFile file, Model model) throws IOException {

        String id = fitxerBDService.createFitxerBD(file);
        Fitxer f = fitxerService.createFitxer(file.getOriginalFilename(),file.getContentType().split("/")[0],fitxerBDService.getUploadDate(id),id);
        fitxerUsuariService.createFitxerUsuari(f.getId(),username,true);
        FileFolderDTO ff = new FileFolderDTO();
        ff.setPath(path);
        ff.setFitxerID(f.getId());
        userService.addFile(username, ff);
        return new ResponseEntity<> (userService.getUserByUserName(username), HttpStatus.OK);
    }

    /**
     * @param id del fitxer que volem descarregar
     * @return El fitxer descarregat de la base de dades
     */
    @GetMapping("/get/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable("id") String id) throws IOException {
        FitxerBD fitxerBD = fitxerService.getFitxerBD(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fitxerBD.getType() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fitxerBD.getTitle() + "\"")
                .body(new ByteArrayResource(IOUtils.toByteArray(fitxerBD.getStream())));
    }

    /**
     * @param id del fitxer que volem esborrar
     * @param file amb el path del fitxer
     * @return L'usuari amb l'estructura actualitzada amb el fitxer esborrat
     */
    @PostMapping("/delete/{id}")
    public ResponseEntity<User> deleteFitxer(@PathVariable("id") String id, @RequestBody FitxerDTO file){
        Collection<FitxerUsuari> fus = fitxerUsuariService.getListFitxerUsuariByFitxer(id);
        User user = fitxerUsuariService.deleteFitxerUsuariOfUser(fus);
        FileFolderDTO ff = new FileFolderDTO();
        ff.setPath(file.getPath());
        ff.setFitxerID(id);
        userService.removeFile(user.getUsername(), ff);
        fitxerService.deleteFitxer(id);
        return new ResponseEntity<>(userRepository.findByUsername(user.getUsername()), HttpStatus.OK);
    }

    /**
     * @param id del fitxer que volem renombrar
     * @param file amb el nou nom del fitxer
     * @return L'usuari amb l'estructura actualitzada amb el fitxer renombrat
     */
    @PostMapping("/rename/{id}")
    public ResponseEntity<User> renameFitxer(@PathVariable("id") String id, @RequestBody FitxerDTO file) throws IOException {
        FileFolderDTO ff = new FileFolderDTO();
        ff.setPath(file.getPath());
        ff.setFitxerID(id);
        Collection<FitxerUsuari> fus = fitxerUsuariService.getListFitxerUsuariByFitxer(id);
        User user = new User();
        Iterator<FitxerUsuari> it = fus.iterator();
        while (it.hasNext() && user != null){
            FitxerUsuari fu = (FitxerUsuari)it.next();
            if (fu.getEsPropietari())
                user = userService.getUser(fu.getUserId());
        }
        userService.renameFile(user.getUsername(),ff,file.getNouNom());
        fitxerService.renameFitxer(id,file.getNouNom());

        return new ResponseEntity<>(userRepository.findByUsername(user.getUsername()),HttpStatus.OK);
    }

    /**
     * @param id del fitxer que volem actualitzar
     * @param path on es troba el fitxer
     * @param file actualitzat del fitxer
     * @return L'usuari amb l'estructura actualitzada amb el fitxer actualitzat
     */
    @PostMapping("/update/{id}")
    public ResponseEntity<User> updateFitxer(@PathVariable("id") String id,
                                             @RequestParam("path") String path, @RequestParam("file") MultipartFile file, Model model ) throws IOException {
        FileFolderDTO ff = new FileFolderDTO();
        ff.setPath(path);
        ff.setFitxerID(id);
        Collection<FitxerUsuari> fus = fitxerUsuariService.getListFitxerUsuariByFitxer(id);
        User user = new User();
        Iterator<FitxerUsuari> it = fus.iterator();
        while (it.hasNext() && user != null){
            FitxerUsuari fu = (FitxerUsuari)it.next();
            if (fu.getEsPropietari())
                user = userService.getUser(fu.getUserId());
        }
        Fitxer fitxer = fitxerService.copyFitxer(file); // retorna el fitxer que volem canviar actualitzar
        userService.updateFile(user.getUsername(),ff,fitxer); //-> fer metode com el rename q actualitzi el fitxer sencer
        fitxerService.updateFitxer(id,fitxer);
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @param tipus de fitxer pel qual volem obtenir els fitxers de l'usuari
     * @return Llista de fitxers pel tipus de fitxer entrat
     */
    @GetMapping("/{username}/tipus")
    public ResponseEntity<Collection<Fitxer>> getFitxerByTipus(@PathVariable("username") String username, @RequestParam("tipus") String tipus) {
        Collection<FitxerUsuari> fus = fitxerUsuariService.getListFitxerUsuariByUsuari(username);
        return new ResponseEntity<>(fitxerService.getFitxerByTipus(fus,tipus),HttpStatus.OK);
    }

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @param data data en format (dd/MM/yyyy) de la qual volem obtenir els fitxers de l'usuari
     * @return Llista de fitxers en la data entrada
     */
    @GetMapping("/{username}/data")
    public ResponseEntity<Collection<Fitxer>> getFitxerByData(@PathVariable("username") String username,@RequestParam("data") String data) throws ParseException {
        Collection<FitxerUsuari> fus = fitxerUsuariService.getListFitxerUsuariByUsuari(username);
        return new ResponseEntity<>(fitxerService.getFitxerByDataPujada(fus,data),HttpStatus.OK);
    }

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @param data1 data posterior en format (dd/MM/yyyy) de la qual volem obtenir els fitxers de l'usuari
     * @param data2 data anterior en format (dd/MM/yyyy) de la qual volem obtenir els fitxers de l'usuari
     * @return Llista de fitxers entre les dates entrades
     */
    @GetMapping("/{username}/databetween")
    public ResponseEntity<Collection<Fitxer>> getFitxerBetweenData(@PathVariable("username") String username,
                                                                   @RequestParam("data1") String data1,
                                                                   @RequestParam("data2") String data2) throws ParseException {
        Collection<FitxerUsuari> fus = fitxerUsuariService.getListFitxerUsuariByUsuari(username);
        return new ResponseEntity<>(fitxerService.getFitxerByDataPujadaBetween(fus,data1,data2),HttpStatus.OK);
    }

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @param data data anterior en format (dd/MM/yyyy) de la qual volem obtenir els fitxers de l'usuari
     * @return Llista de fitxers entre les dates entrades
     */
    @GetMapping("/{username}/afterdata")
    public ResponseEntity<Collection<Fitxer>> getFitxerByDataAfter(@PathVariable("username") String username,@RequestParam("data") String data) throws ParseException {
        Collection<FitxerUsuari> fus = fitxerUsuariService.getListFitxerUsuariByUsuari(username);
        return new ResponseEntity<>(fitxerService.getFitxerByDataPujadaAfter(fus,data),HttpStatus.OK);
    }

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @param data data posterior en format (dd/MM/yyyy) de la qual volem obtenir els fitxers de l'usuari
     * @return Llista de fitxers entre les dates entrades
     */
    @GetMapping("/{username}/beforedata")
    public ResponseEntity<Collection<Fitxer>> getFitxerByDataBefore(@PathVariable("username") String username,@RequestParam("data") String data) throws ParseException {
        Collection<FitxerUsuari> fus = fitxerUsuariService.getListFitxerUsuariByUsuari(username);
        return new ResponseEntity<>(fitxerService.getFitxerByDataPujadaBefore(fus,data),HttpStatus.OK);
    }

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @param nom d'inici pel qual volem obtenir els fitxers de l'usuari
     * @return Llista de fitxers pel nom inicial entrat
     */
    @GetMapping("/{username}/nomstarts")
    public ResponseEntity<Collection<Fitxer>> getFitxerByNomStarts(@PathVariable("username") String username,@RequestParam("nom") String nom) {
        Collection<FitxerUsuari> fus = fitxerUsuariService.getListFitxerUsuariByUsuari(username);
        return new ResponseEntity<>(fitxerService.getFitxerByNomStartsWith(fus,nom),HttpStatus.OK);
    }

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @param nom final pel qual volem obtenir els fitxers de l'usuari
     * @return Llista de fitxers pel nom final entrat
     */
    @GetMapping("/{username}/nomends")
    public ResponseEntity<Collection<Fitxer>> getFitxerByNomEnds(@PathVariable("username") String username,@RequestParam("nom") String nom) {
        Collection<FitxerUsuari> fus = fitxerUsuariService.getListFitxerUsuariByUsuari(username);
        return new ResponseEntity<>(fitxerService.getFitxerByNomEndsWith(fus,nom),HttpStatus.OK);
    }

}