package com.example.shifttimecalculator.service.dao.implementation;

import com.example.shifttimecalculator.entity.Shift;
import com.example.shifttimecalculator.repository.ShiftRepo;
import com.example.shifttimecalculator.service.dao.ShiftInterface;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShiftInterfaceImpl implements ShiftInterface {
    private final ShiftRepo shiftRepo;

    public ShiftInterfaceImpl(ShiftRepo shiftRepo) {
        this.shiftRepo = shiftRepo;
    }

    @Override
    public List<Shift> findAll() {
        return shiftRepo.findAll();
    }

    @Override
    public Shift findByName(String name) {
        return shiftRepo.findByName(name);
    }
}
