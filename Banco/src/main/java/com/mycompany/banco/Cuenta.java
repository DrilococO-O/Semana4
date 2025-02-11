/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco;

public abstract class Cuenta {
    protected double saldo;
    protected Cliente cliente;

    public Cuenta(Cliente cliente, double saldoInicial) {
        this.cliente = cliente;
        this.saldo = saldoInicial;
    }

    public abstract void abrirCuenta();
    public abstract void realizarDeposito(double monto);
    public abstract void retirarDinero(double monto);
    public abstract void aplicarComisiones();
    public abstract void generarRecibo();
}
