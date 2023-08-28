package com.example.shifttimecalculator.dto;

import com.example.shifttimecalculator.constants.BotConstants;
import com.example.shifttimecalculator.entity.Sector;
import lombok.Data;

@Data
public class SectorDTO {
    private final String name;
    public SectorDTO(Sector sector) {
        this.name = sector.getName();
    }

    @Override
    public String toString(){
        return BotConstants.SECTOR + name;
    }
}