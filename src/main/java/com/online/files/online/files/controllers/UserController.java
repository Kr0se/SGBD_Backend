package com.online.files.online.files.controllers;

import com.online.files.online.files.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.online.files.online.files.DTO.FileFolderDTO;
import com.online.files.online.files.DTO.FolderDTO;
import com.online.files.online.files.DTO.UserAuthDTO;
import com.online.files.online.files.models.User;
import com.online.files.online.files.models.fitxers.Fitxer;

import java.util.Collection;
import java.util.List;

@RequestMapping(path="/users")
@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping()
    public Collection<User> getUser(){
        return userService.getUsers();
    }

    @GetMapping("/get")
    public User getUser(@RequestParam("firstName") String firstName){
        return userService.getUser(firstName);
    }

    @GetMapping("/getString")
    public String getUserString(@RequestParam("id") String firstName){
        return userService.getUser(firstName).toString();
    }

    @GetMapping("/getUserByUsername")
    public User getUserByUsername(@RequestParam("username") String username){
        return userService.getUserByUsername(username);
    }

    @PostMapping("/register")
    public ResponseEntity<Boolean> register(@RequestBody UserAuthDTO user){

        return new ResponseEntity<>(this.userService.register(user),HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<Boolean> login(@RequestBody UserAuthDTO user){

        return new ResponseEntity<>(this.userService.login(user),HttpStatus.OK);
    }

    /**
     * 
     * @param username usuari de la base de dades (clau primaria de user)
     * @param path path on volem inserir la nova carpeta. La carpeta principal
     *             dels usuaris es diu 'main'. Exemples: 'main', 'main/pelis'
     * @param folderName nom de la carpeta nova
     * @return L'usuari amb l'estructura actualitzada
     */
    @PostMapping("/{username}/addFolder")
    public ResponseEntity<User> addFolder(@PathVariable("username") String username, @RequestBody FolderDTO folder){

        return new ResponseEntity<>(this.userService.addFolder(username, folder),HttpStatus.OK);
    }

    /**
     * 
     * @param username usuari de la base de dades (clau primaria de user)
     * @param path path on volem borrar la carpeta. La carpeta principal
     *             dels usuaris es diu 'main'. Exemples: 'main', 'main/pelis'
     * @param folderName nom de la carpeta que volem borrar
     * @return L'usuari amb l'estructura actualitzada
     */
    @PostMapping("/{username}/removeFolder")
    public ResponseEntity<User> removeFolder(@PathVariable("username") String username, @RequestBody FolderDTO folder){

        return new ResponseEntity<>(this.userService.removeFolder(username, folder),HttpStatus.OK);
    }

    /**
     * 
     * @param username usuari de la base de dades (clau primaria de user)
     * @param path path on volem borrar la carpeta. La carpeta principal
     *             dels usuaris es diu 'main'. Exemples: 'main', 'main/pelis'
     * @param folderName nom de la carpeta que volem canviar el nopm
     * @return L'usuari amb l'estructura actualitzada
     */
    @PostMapping("/{username}/renameFolder")
    public ResponseEntity<User> renameFolder(@PathVariable("username") String username, @RequestBody FolderDTO folder){

        return new ResponseEntity<>(this.userService.renameFolder(username, folder),HttpStatus.OK);
    }

    @GetMapping("/{username}/carpeta/getFiles")
    public List<Fitxer> getFiles(@PathVariable("username") String username, @RequestBody FolderDTO folder){
        return userService.getFiles(username, folder);
    }

    /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @param fitxerID id del fitxer
     * @param path path de la carpeta on volem inserir el fitxer. La carpeta principal
     *             dels usuaris es diu 'main'. Exemples: 'main', 'main/pelis'
     * @return True si s'ha afegit el fitxer a la carpeta
     */
    @PostMapping("/{username}/carpeta/addFile")
    public ResponseEntity<Boolean> addFile(
                            @PathVariable("username") String username,
                            @RequestBody FileFolderDTO fileFolder){

        return new ResponseEntity<>(this.userService.addFile(username, fileFolder),HttpStatus.OK);
    }

     /**
     * @param username usuari de la base de dades (clau primaria de user)
     * @param fitxerID id del fitxer
     * @param path path de la carpeta d'on volem borrar el fitxer. La carpeta principal
     *             dels usuaris es diu 'main'. Exemples: 'main', 'main/pelis'
     * @return True si s'ha borrat el fitxer de la carpeta
     */
    @DeleteMapping("/{username}/carpeta/removeFile")
    public ResponseEntity<Boolean> removeFile(
                            @PathVariable("username") String username,
                            @RequestBody FileFolderDTO fileFolder){

        return new ResponseEntity<>(this.userService.removeFile(username, fileFolder),HttpStatus.OK);
    }
}