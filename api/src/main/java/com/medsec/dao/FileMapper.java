package com.medsec.dao;

import com.medsec.entity.File;

public interface FileMapper {
    void insertFile(File file);
    File selectFileById(String id);
    String getLink(String pid);
}
