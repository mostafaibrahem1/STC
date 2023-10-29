package com.stc.application.manager;

import com.stc.application.exception.InsufficientPrivilege;
import com.stc.application.exception.NotFoundException;
import com.stc.application.model.view.ItemsDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.management.BadAttributeValueExpException;

public interface IItemManager {


    String createSpace(ItemsDTO itemsDTO) throws NotFoundException, BadAttributeValueExpException;

    String createFolder(String space, ItemsDTO itemsDTO) throws Exception;

    byte[] getItemFileBinary(String id, String userEmail) throws NotFoundException, InsufficientPrivilege;

    String createFile(String space, String folder, MultipartFile file, String userEmail) throws Exception;
}
