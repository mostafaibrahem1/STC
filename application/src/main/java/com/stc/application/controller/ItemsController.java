package com.stc.application.controller;


import com.stc.application.exception.InsufficientPrivilege;
import com.stc.application.exception.NotFoundException;
import com.stc.application.manager.IItemManager;
import com.stc.application.model.view.ItemsDTO;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.management.BadAttributeValueExpException;

@RestController
@RequestMapping(value = "/item")
@Slf4j
public class ItemsController {
    private final IItemManager itemManager;

    public ItemsController(final IItemManager itemManager) {
        this.itemManager = itemManager;
    }

    @PostMapping(value = "/space")
    public ResponseEntity createItemsSpace(@RequestBody ItemsDTO itemsDTO) throws NotFoundException, BadAttributeValueExpException {
        log.info("start receiving create space request");
        return new ResponseEntity<>(itemManager.createSpace(itemsDTO), HttpStatus.CREATED);
    }


    @PostMapping(value = "/{space}/folder")
    public ResponseEntity createItemsFolder(@PathVariable @NotNull String space, @RequestBody ItemsDTO itemsDTO) throws Exception {
        return new ResponseEntity<>(itemManager.createFolder(space, itemsDTO), HttpStatus.CREATED);
    }


    @PostMapping(value = "/{space}/{folder}/file", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity createItemsFile(@PathVariable String space, @PathVariable String folder, @RequestParam("file") MultipartFile file, @RequestParam("userEmail") String userEmail) throws Exception {


        return new ResponseEntity<>(itemManager.createFile(space, folder, file, userEmail), HttpStatus.CREATED);
    }

    @GetMapping("/file-download/{id}")
    public ResponseEntity getFileMetaData(@PathVariable String id, @RequestParam("userEmail") @NotNull String userEmail) throws NotFoundException, InsufficientPrivilege {
        return ResponseEntity.ok().body(itemManager.getItemFileBinary(id, userEmail));
    }


}

