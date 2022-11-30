package com.online.files.online.files.services;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.GridFSFindIterable;
import com.mongodb.client.gridfs.model.GridFSFile;
import com.online.files.online.files.models.fitxers.FitxerBD;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.function.Consumer;

@Service
public class FitxerBDService {

    @Autowired
    private GridFsTemplate gridFsTemplate;

    @Autowired
    private GridFsOperations operations;

    public String createFitxerBD(String title,String tipus, MultipartFile file) throws IOException {
        DBObject metaData = new BasicDBObject();
        metaData.put("type", tipus);
        metaData.put("title", title);
        ObjectId id = gridFsTemplate.store(
                file.getInputStream(), file.getName(), file.getContentType(), metaData);
        return id.toString();
    }

    public FitxerBD getFitxerBD(String id) throws IllegalStateException, IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        FitxerBD fitxerBD = new FitxerBD();
        fitxerBD.setTitle(file.getMetadata().get("title").toString());
        fitxerBD.setStream(operations.getResource(file).getInputStream());
        return fitxerBD;
    }

    public Collection<FitxerBD> getFitxersBD() throws IllegalStateException, IOException{
        GridFSFindIterable files = gridFsTemplate.find(new Query());
        Collection<FitxerBD> fitxerBDS = new ArrayList<>();
        files.forEach((Consumer<? super GridFSFile>) d -> {
            try {
                fitxerBDS.add(new FitxerBD(d.getMetadata().get("title").toString(),operations.getResource(d).getInputStream()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        return fitxerBDS;
    }

    public Date getUploadDate(String id)throws IllegalStateException, IOException {
        GridFSFile file = gridFsTemplate.findOne(new Query(Criteria.where("_id").is(id)));
        /*DateFormat df = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z");
        df.setTimeZone(TimeZone.getTimeZone("GMT"));
        return df.format(file.getUploadDate());*/
        return file.getUploadDate();
    }

    public void deleteFitxerBD(String id){
        gridFsTemplate.delete(new Query(Criteria.where("_id").is(id)));
    }
}
