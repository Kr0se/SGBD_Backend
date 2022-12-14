package com.online.files.online.files.services;

import com.online.files.online.files.DTO.FitxerDTO;
import com.online.files.online.files.models.fitxers.Fitxer;
import com.online.files.online.files.models.FitxerUsuari;
import com.online.files.online.files.models.fitxers.FitxerBD;
import com.online.files.online.files.repositories.FitxerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class FitxerService
{
    @Autowired
    private FitxerRepository fitxerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FitxerBDService fitxerBDService;

    /**
     *
     * @return Llista de totes els fitxers existents en base de dades
     */
    public Collection<Fitxer> getFitxers(){ return fitxerRepository.findAll();}

    public Fitxer createFitxer(String nom, String tipus, Date data, String idDB){
        Fitxer f = new Fitxer(nom, tipus, data, idDB);
        fitxerRepository.save(f);
        return f;
    }

    /**
     *
     * @param f fitxer
     * @param fu relacio que afegirem a la llista de relacions del fitxer
     */
    public void addFitxerUser(Fitxer f, FitxerUsuari fu){
        f.addFitxerUsuari(fu);
        fitxerRepository.save(f);
    }

    /**
     *
     * @param fitxerID del fitxer
     * @return fitxer de base de dades
     */
    public Fitxer getFitxer(String fitxerID){
        Optional<Fitxer> f = fitxerRepository.findById(fitxerID);
        if (f.isEmpty())
            throw new RuntimeException("No existeix un fitxer amb aquesta id");
        return f.get();
    }

    /**
     *
     * @param fitxer amb el fitxerId del fitxer
     * @return fitxerBD de base de dades
     * @throws IOException
     */
    public FitxerBD getFitxerBD(FitxerDTO fitxer) throws IOException {
        Optional<Fitxer> f = fitxerRepository.findById(fitxer.getId());
        if (f.isEmpty())
            throw new RuntimeException("No existeix un fitxer amb aquesta id");
        return fitxerBDService.getFitxerBD(f.get().getFitxerDBId());
    }

    /**
     *
     * @param fitxerId del fitxer
     * @return fitxerBD de base de dades
     * @throws IOException
     */
    public FitxerBD getFitxerBD(String fitxerId) throws IOException {
        Optional<Fitxer> f = fitxerRepository.findById(fitxerId);
        if (f.isEmpty())
            throw new RuntimeException("No existeix un fitxer amb aquesta id");
        return fitxerBDService.getFitxerBD(f.get().getFitxerDBId());
    }

    /**
     *
     * @param nom del fitxer
     * @return fitxer de base de dades
     */
    public Fitxer getFitxerByNom(String nom){
        return fitxerRepository.findByNom(nom);
    }

    /**
     *
     * @param id del fitxer
     * @param nouNom amb el qual volem renombrar el fitxer
     * @return fitxer actualitzat
     * @throws IOException
     */
    public void renameFitxer(String id, String nouNom) throws IOException {
        Fitxer fitxer = this.getFitxer(id);
        fitxer.setNom(nouNom);
        String nouIdBD = fitxerBDService.renameFitxerBD(fitxer.getFitxerDBId(),nouNom);
        fitxer.setDataPujada(fitxerBDService.getUploadDate(nouIdBD));
        fitxer.setFitxerBDId(nouIdBD);
        fitxerRepository.save(fitxer);
    }

    /**
     *
     * @param file nou fitxer
     * @return nou fitxer creat
     * @throws IOException
     */
    public Fitxer copyFitxer( MultipartFile file) throws IOException {
        //Fitxer fitxer = this.getFitxer(id);
        String idBDNou = fitxerBDService.createFitxerBD(file);
        Fitxer fitxer = new Fitxer(file.getOriginalFilename(),file.getContentType().split("/")[0],fitxerBDService.getUploadDate(idBDNou),idBDNou);
        return fitxer;
    }

    /**
     *
     * @param id del fitxer que volem actualitzar
     * @param copy nova versio del fitxer
     * @return fitxer actualitzat
     */
    public Fitxer updateFitxer(String id, Fitxer copy){
        Fitxer fitxer = this.getFitxer(id);
        fitxerBDService.deleteFitxerBD(fitxer.getFitxerDBId());
        fitxer.setNom(copy.getNom());
        fitxer.setTipus(copy.getTipus());
        fitxer.setDataPujada(copy.getDataPujada());
        fitxer.setFitxerBDId(copy.getFitxerDBId());
        fitxerRepository.save(fitxer);
        return fitxer;
    }

    /**
     *
     * @param fitxerID del fitxer que volem borrar
     */
    public void deleteFitxer(String fitxerID){
        Fitxer f = getFitxer(fitxerID);
        fitxerBDService.deleteFitxerBD(f.getFitxerDBId());
        fitxerRepository.delete(f);
    }

    /**
     *
     * @param fus llista de relacions
     * @param tipus de fitxer
     * @return llista de fitxers del tipus buscat
     */
    public Collection<Fitxer> getFitxerByTipus(Collection<FitxerUsuari> fus, String tipus) {
        Collection<Fitxer> fitxers = new ArrayList<>();
        for(FitxerUsuari fu : fus){
            Fitxer f = this.getFitxer(fu.getFitxerId());
            if(f.getTipus().equals(tipus))
                fitxers.add(f);
        }
        return fitxers;
    }

    /**
     *
     * @param dataS en format string
     * @return en format data
     * @throws ParseException
     */
    private Date parser(String dataS) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        return format.parse(dataS);
    }

    /**
     *
     * @param fus llista de relacions
     * @param data
     * @return llista de fitxers en la data buscada
     */
    public Collection<Fitxer> getFitxerByDataPujada(Collection<FitxerUsuari> fus, String data) throws ParseException {
        Collection<Fitxer> fitxers = new ArrayList<>();
        for(FitxerUsuari fu : fus){
            Fitxer f = this.getFitxer(fu.getFitxerId());
            if(f.getDataPujada().equals(this.parser(data)))
                fitxers.add(f);
        }
        return fitxers;
    }

    /**
     *
     * @param fus llista de relacions
     * @param data1
     * @param data2
     * @return llista de fitxers entre les dates buscades
     */
    public Collection<Fitxer> getFitxerByDataPujadaBetween(Collection<FitxerUsuari> fus, String data1, String data2) throws ParseException {
        Collection<Fitxer> fitxers = new ArrayList<>();
        for(FitxerUsuari fu : fus){
            Fitxer f = this.getFitxer(fu.getFitxerId());
            if(f.getDataPujada().after(this.parser(data1)) && f.getDataPujada().before(this.parser(data2)))
                fitxers.add(f);
        }
        return fitxers;
    }

    /**
     *
     * @param fus llista de relacions
     * @param data
     * @return llista de fitxers despers de la data buscada
     */
    public Collection<Fitxer> getFitxerByDataPujadaAfter(Collection<FitxerUsuari> fus, String data) throws ParseException {
        Collection<Fitxer> fitxers = new ArrayList<>();
        for(FitxerUsuari fu : fus){
            Fitxer f = this.getFitxer(fu.getFitxerId());
            if(f.getDataPujada().after(this.parser(data)))
                fitxers.add(f);
        }
        return fitxers;
    }

    /**
     *
     * @param fus llista de relacions
     * @param data
     * @return llista de fitxers abans de la data buscada
     */
    public Collection<Fitxer> getFitxerByDataPujadaBefore(Collection<FitxerUsuari> fus, String data) throws ParseException {
        Collection<Fitxer> fitxers = new ArrayList<>();
        for(FitxerUsuari fu : fus){
            Fitxer f = this.getFitxer(fu.getFitxerId());
            if(f.getDataPujada().before(this.parser(data)))
                fitxers.add(f);
        }
        return fitxers;
    }

    /**
     *
     * @param fus llista de relacions
     * @param nom
     * @return llista de fitxers pel nom inicial
     */
    public Collection<Fitxer> getFitxerByNomStartsWith(Collection<FitxerUsuari> fus, String nom) {
        List<Fitxer> fitxers = new ArrayList<Fitxer>();
        if(!nom.isEmpty()){
            for(FitxerUsuari fu : fus){
                Fitxer f = this.getFitxer(fu.getFitxerId());
                if(f.getNom().startsWith(nom))
                    fitxers.add(f);
            }
        }
        Collections.sort(fitxers, Comparator.comparing(Fitxer::getNom));
        return fitxers;
    }

    /**
     *
     * @param fus llista de relacions
     * @param nom
     * @return llista de fitxers pel nom final
     */
    public Collection<Fitxer> getFitxerByNomEndsWith(Collection<FitxerUsuari> fus, String nom) {
        List<Fitxer> fitxers = new ArrayList<>();
        if(!nom.isEmpty()){
            for (FitxerUsuari fu : fus) {
                Fitxer f = this.getFitxer(fu.getFitxerId());
                if (f.getNom().endsWith(nom))
                    fitxers.add(f);
            }
        }
        Collections.sort(fitxers, Comparator.comparing(Fitxer::getNom));
        return fitxers;
    }
}
