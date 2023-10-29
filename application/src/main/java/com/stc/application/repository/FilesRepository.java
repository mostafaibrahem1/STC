package com.stc.application.repository;


import com.stc.application.model.domain.File;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilesRepository extends JpaRepository<File, String> {


}
