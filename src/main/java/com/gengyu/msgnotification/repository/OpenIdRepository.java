package com.gengyu.msgnotification.repository;

import com.gengyu.msgnotification.entity.OpenIdEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author Siegfried GENG
 * @date 2019/8/18 - 12:24
 */
public interface OpenIdRepository extends JpaRepository<OpenIdEntity, Integer>{


}
