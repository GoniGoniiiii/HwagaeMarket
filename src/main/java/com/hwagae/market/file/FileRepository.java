package com.hwagae.market.file;

import org.springframework.data.jpa.repository.JpaRepository;
import com.hwagae.market.file.FileEntity;
public interface FileRepository extends JpaRepository<FileEntity,Integer> {
}