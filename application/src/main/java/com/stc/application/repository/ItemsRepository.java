package com.stc.application.repository;


import com.stc.application.model.domain.Item;
import com.stc.application.model.enums.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ItemsRepository extends JpaRepository<Item, String> {
    Item findByNameAndType(String name, ItemType type);


}
