package io.serverchecker.springboot.service;

import io.serverchecker.springboot.model.Server;
import io.serverchecker.springboot.repo.ServerRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.querydsl.QPageRequest;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.net.InetAddress;
import java.util.Collection;

import static io.serverchecker.springboot.enumeration.Status.SERVER_UP;
import static io.serverchecker.springboot.enumeration.Status.SERVER_DOWN;


@RequiredArgsConstructor
@Service
@Transactional
@Slf4j
public class ServerServiceImplementation implements ServerService {
    private final ServerRepo serverRepo;
    @Override
    public Server create(Server server) {
        log.info("Saving new server: {}", server.getName());
        server.setImageUrl(setServerImageUrl());
        return serverRepo.save(server);
    }

    @Override
    public Server ping(Server ipAddress) throws IOException {
        log.info("Ping server IP: {}", ipAddress);
        Server server  = serverRepo.findByIpAddress(ipAddress);
        InetAddress address = InetAddress.getByName(String.valueOf(ipAddress));
        server.setStatus(address.isReachable(10000)? SERVER_UP: SERVER_DOWN);
        serverRepo.save(server);
        return server;
    }

    @Override
    public Collection<Server> list(int limit) {
        log.info("Getting all servers");
        return serverRepo.findAll(PageRequest.of(0, limit)).toList();
    }

    @Override
    public Server get(Long id) {
        log.info("Getting server by id: {}", id);
        return serverRepo.findById(id).get();
    }

    @Override
    public Server update(Server server) {
        log.info("Updating server: {}", server.getName());
        return serverRepo.save(server);
    }

    @Override
    public Boolean delete(Long id) {
        log.info("Deleting server: {}", id);
        serverRepo.deleteById(id);
        return true;
    }

    private String setServerImageUrl() {
        String[] imageNames = { "server1.png", }
        return null;
    }
}
