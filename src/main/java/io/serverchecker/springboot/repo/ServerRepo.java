package io.serverchecker.springboot.repo;

import io.serverchecker.springboot.model.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepo extends JpaRepository<Server, Long> {
    Server findByIpAddress(Server ipAddress);
}
