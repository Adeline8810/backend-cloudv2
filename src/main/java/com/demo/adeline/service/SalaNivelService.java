package com.demo.adeline.service;

import org.springframework.stereotype.Service;

@Service
public class SalaNivelService {

    public int calcularNivel(Long xp) {

        if (xp < 100) return 1;
        if (xp < 300) return 2;
        if (xp < 700) return 3;
        if (xp < 1500) return 4;
        if (xp < 3000) return 5;
        if (xp < 6000) return 6;
        if (xp < 10000) return 7;

        return 8;
    }

    public int calcularMaxAsientos(int nivel) {

        if (nivel == 1) return 8;
        if (nivel == 2) return 10;
        if (nivel == 3) return 12;
        if (nivel == 4) return 15;
        if (nivel == 5) return 20;

        return 25;
    }
}