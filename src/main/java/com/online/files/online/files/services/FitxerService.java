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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Optional;

@Service
public class FitxerService
{
    @Autowired
    private FitxerRepository fitxerRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private FitxerBDService fitxerBDService;

    public Collection<Fitxer> getFitxers(){ return fitxerRepository.findAll();}

    public Fitxer createFitxer(String nom, String tipus, Date data, String idDB){
        Fitxer f = new Fitxer(nom, tipus, data, idDB);
        fitxerRepository.save(f);
        return f;
    }

    public void addFitxerUser(Fitxer f, FitxerUsuari fu){
        f.addFitxerUsuari(fu);
        fitxerRepository.save(f);
    }

    public Fitxer getFitxer(String fitxerID){
        Optional<Fitxer> f = fitxerRepository.findById(fitxerID);
        if (f.isEmpty())
            throw new RuntimeException("No existeix un fitxer amb aquesta id");
        return f.get();
    }

    public FitxerBD getFitxerBD(FitxerDTO fitxer) throws IOException {
        Optional<Fitxer> f = fitxerRepository.findById(fitxer.getId());
        if (f.isEmpty())
            throw new RuntimeException("No existeix un fitxer amb aquesta id");
        return fitxerBDService.getFitxerBD(f.get().getFitxerDBId());
    }

    public FitxerBD getFitxerBD(String fitxerId) throws IOException {
        Optional<Fitxer> f = fitxerRepository.findById(fitxerId);
        if (f.isEmpty())
            throw new RuntimeException("No existeix un fitxer amb aquesta id");
        return fitxerBDService.getFitxerBD(f.get().getFitxerDBId());
    }

    public Fitxer getFitxerByNom(String nom){
        return fitxerRepository.findByNom(nom);
    }

    public void renameFitxer(String id, String nouNom) throws IOException {
        Fitxer fitxer = this.getFitxer(id);
        fitxer.setNom(nouNom);
        String nouIdBD = fitxerBDService.renameFitxerBD(fitxer.getFitxerDBId(),nouNom);
        fitxer.setDataPujada(fitxerBDService.getUploadDate(nouIdBD));
        fitxer.setFitxerBDId(nouIdBD);
        fitxerRepository.save(fitxer);
    }

    public Fitxer copyFitxer( MultipartFile file) throws IOException {
        //Fitxer fitxer = this.getFitxer(id);
        String idBDNou = fitxerBDService.createFitxerBD(file);
        Fitxer fitxer = new Fitxer(file.getOriginalFilename(),file.getContentType().split("/")[0],fitxerBDService.getUploadDate(idBDNou),idBDNou);
        return fitxer;
    }

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

    public void deleteFitxer(String fitxerID){
        Fitxer f = getFitxer(fitxerID);
        fitxerBDService.deleteFitxerBD(f.getFitxerDBId());
        fitxerRepository.delete(f);
    }

    public Collection<FitxerBD> getFitxerByTipus(String tipus) throws IOException {
        Collection<FitxerBD> fitxerBDS = new ArrayList<>();
        Collection<Fitxer> ftxs = fitxerRepository.findByTipus(tipus);
        for(Fitxer f : ftxs){
            fitxerBDS.add(fitxerBDService.getFitxerBD(f.getFitxerDBId()));
        }
        return fitxerBDS;
    }

    public Collection<Fitxer> getFitxerByDataPujada(Date data){
        return fitxerRepository.findByDataPujada(data);
    }

    public Collection<Fitxer> getFitxerByDataPujadaBetween(Date data1, Date data2){
        return fitxerRepository.findByDataPujadaBetween(data1, data2);
    }

    public Collection<Fitxer> getFitxerByNomStartsWith(String nom){
        return fitxerRepository.findByNomStartingWith(nom);
    }

    public Collection<Fitxer> getFitxerByNomEndsWith(String nom){
        return fitxerRepository.findByNomEndingWith(nom);
    }
}
