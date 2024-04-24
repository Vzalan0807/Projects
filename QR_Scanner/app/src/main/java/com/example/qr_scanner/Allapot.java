package com.example.qr_scanner;

public enum Allapot {
    KIADVA{
        public String toString(){
            return "Kiadva";
        }

        @Override
        public boolean isKiadva() {
            return true;
        }
    },
    ELERHETO {
        public String toString() {
            return "Elerheto";
        }

        @Override
        public boolean isKiadva() {
            return false;
        }
    };

    public abstract String toString();
    public abstract boolean isKiadva();

}
