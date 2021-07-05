package ru.levelp.hospital.model;

import javax.persistence.*;
import java.sql.Blob;

@Entity
public class FileUploadItem {
    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column
    private long size;

    @Lob
    private Blob fileContent;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public Blob getFileContent() {
        return fileContent;
    }

    public void setFileContent(Blob fileContent) {
        this.fileContent = fileContent;
    }
}

