package ru.levelp.hospital.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.levelp.hospital.model.FileUploadItem;

@Repository
public interface FileUploadItemRepository extends JpaRepository<FileUploadItem, Integer> {
}