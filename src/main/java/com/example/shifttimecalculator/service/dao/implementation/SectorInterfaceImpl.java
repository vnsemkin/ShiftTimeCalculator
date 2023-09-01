package com.example.shifttimecalculator.service.dao.implementation;

import com.example.shifttimecalculator.entity.Sector;
import com.example.shifttimecalculator.repository.SectorRepo;
import com.example.shifttimecalculator.service.dao.SectorInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SectorInterfaceImpl implements SectorInterface {
    private final SectorRepo sectorRepo;

    public SectorInterfaceImpl(SectorRepo sectorRepo) {
        this.sectorRepo = sectorRepo;
    }

    @Override
    public List<Sector> findAll(){
        return sectorRepo.findAll();
    }

    @Override
    public Sector findByName(String name) {
        return sectorRepo.findByName(name);
    }
}
