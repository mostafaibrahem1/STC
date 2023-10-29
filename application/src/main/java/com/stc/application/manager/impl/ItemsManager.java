package com.stc.application.manager.impl;


import com.stc.application.exception.InsufficientPrivilege;
import com.stc.application.exception.NotFoundException;
import com.stc.application.manager.IItemManager;
import com.stc.application.model.domain.File;
import com.stc.application.model.domain.Item;
import com.stc.application.model.domain.Permission;
import com.stc.application.model.domain.PermissionGroup;
import com.stc.application.model.enums.ItemType;
import com.stc.application.model.view.ItemsDTO;
import com.stc.application.repository.FilesRepository;
import com.stc.application.repository.ItemsRepository;
import com.stc.application.repository.PermissionGroupsRepository;
import com.stc.application.repository.PermissionRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.management.BadAttributeValueExpException;
import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@Component
@Slf4j
public class ItemsManager implements IItemManager {

    private final ItemsRepository itemsRepository;
    private final PermissionGroupsRepository permissionGroupsRepository;

    private final PermissionRepository permissionsRepository;
    private final FilesRepository filesRepository;


    public ItemsManager(ItemsRepository itemsRepository, PermissionGroupsRepository permissionGroupsRepository, PermissionRepository permissionsRepository, FilesRepository filesRepository) {
        this.itemsRepository = itemsRepository;
        this.permissionGroupsRepository = permissionGroupsRepository;
        this.permissionsRepository = permissionsRepository;
        this.filesRepository = filesRepository;
    }


    @Override
    public String createSpace(ItemsDTO itemsDTO) throws NotFoundException, BadAttributeValueExpException {
        log.info("creating Space");
        Item item = new Item();
        item.setType(ItemType.SPACE);


        // validate space not created before
        validateItemIsNotExist(itemsDTO.getName(), item.getType());


        log.info("end createSpace method");
        return createItem(itemsDTO, item).getId();
    }

    private void validateItemIsNotExist(String name, ItemType type) throws BadAttributeValueExpException {

        Item item = itemsRepository.findByNameAndType(name.trim(), type);
        if (Objects.nonNull(item)) {
            throw new BadAttributeValueExpException(type.toString() + " with name " + name + " is already created");
        }

    }

    @Override
    public String createFolder(String space, ItemsDTO itemsDTO) throws Exception {


        // validate user permissions
        validateUserPermission(itemsDTO.getUserEmail());

        log.info("creating folder");
        Item item = new Item();
        item.setType(ItemType.FOLDER);

        // validate is exist by name and type
        validateItemExistByNameAndType(space, ItemType.SPACE);
        // validate item not created before
        validateItemIsNotExist(itemsDTO.getName(), ItemType.FOLDER);

        return createItem(itemsDTO, item).getId();
    }

    private void validateItemExistByNameAndType(String name, ItemType type) throws NotFoundException {
        Item spaceItem = itemsRepository.findByNameAndType(name, type);
        if (Objects.isNull(spaceItem)) {
            throw new RuntimeException(type + " with name " + name + " Not Found");
        }
    }

    @Override
    public byte[] getItemFileBinary(String id, String userEmail) throws NotFoundException, InsufficientPrivilege {


        Optional<Item> item = itemsRepository.findById(id);

        if (item.isEmpty()) {
            throw new NotFoundException("file with id " + id + " Not Found");

        }


        // validate user in same permission group
        if (!item.get().getPermissionGroup().getId().equals(permissionsRepository.findByUserEmail(userEmail).getPermissionGroup().getId())) {
            throw new InsufficientPrivilege("InsufficientPrivilege contact your administrator");
        }

        return item.get().getFile().getBinaryData();
    }

    @Override
    public String createFile(String space, String folder, MultipartFile file, String userEmail) throws Exception {

        // validate user permissions
        validateUserPermission(userEmail);

        Item item = new Item();
        item.setType(ItemType.FILE);

        // validate space is exist
        validateItemExistByNameAndType(space, ItemType.SPACE);
        // validate folder is exist
        validateItemExistByNameAndType(folder, ItemType.FOLDER);

        // validate file is not created before
        validateItemIsNotExist(Objects.requireNonNull(file.getOriginalFilename()), ItemType.FILE);


        Permission permission = permissionsRepository.findByUserEmail(userEmail);

        ItemsDTO itemsDTO = ItemsDTO.builder()
                .file(file)
                .name(file.getName())
                .userEmail(userEmail)
                .permissionGroupId(permission.getPermissionGroup().getId())
                .build();
        Item returnedItemAfterSaving = createItem(itemsDTO, item);


        uploadFile(file, returnedItemAfterSaving);
        return returnedItemAfterSaving.getId();
    }

    private void validateUserPermission(String userEmail) throws NotFoundException, InsufficientPrivilege {
        Permission permission = permissionsRepository.findByUserEmail(userEmail);

        if (Objects.isNull(permission)) {
            throw new NotFoundException("user with email " + userEmail + " Not Found");
        }
        if (!permission.getType().equals(Permission.PermissionType.EDIT)) {
            throw new InsufficientPrivilege("InsufficientPrivilege contact your administrator");
        }
    }


    private Item createItem(ItemsDTO itemsDTO, Item item) throws NotFoundException {
        log.info("start createItem method");
        mapToEntity(itemsDTO, item);
        log.info("end createItem method");
        return itemsRepository.save(item);
    }

    private void mapToEntity(ItemsDTO itemsDTO, Item item) throws NotFoundException {

        item.setName(itemsDTO.getName().trim());
        PermissionGroup permissionGroup = itemsDTO.getPermissionGroupId() == null ? null : permissionGroupsRepository.findById(itemsDTO.getPermissionGroupId()).orElseThrow(() -> new NotFoundException("permissionGroup not found"));
        item.setPermissionGroup(permissionGroup);

    }

    private void uploadFile(MultipartFile fileRequest, Item item) throws IOException {
        File file = new File();
        file.setItem(item);
        file.setBinaryData(fileRequest.getBytes());
        filesRepository.save(file);

    }


}


