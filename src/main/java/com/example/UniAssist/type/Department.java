package com.example.UniAssist.type;

public enum Department {
    EEF("Инженерно-экономический факультет"),
    ATC("Факультет автоматической электросвязи"),
    CSE("Факультет информатики и вычислительной техники"),
    MTS("Факультет мультисервисных телекоммуникационных систем"),
    DL("Заочное обучение"),
    MRM("Факультет мобильной радиосвязи и мультимедиа"),
    HF("Гуманитарный факультет");

    public final String name;

    private Department(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }
}