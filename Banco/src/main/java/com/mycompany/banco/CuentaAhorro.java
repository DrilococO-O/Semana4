/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco;

public class CuentaAhorro extends CuentaBancaria {
    private static final double TASA_RENDIMIENTO = 0.022; // 2.2%

    public CuentaAhorro(Cliente cliente, double saldoInicial) {
        super(cliente, saldoInicial);
    }

    @Override
    public void abrirCuenta() {
        System.out.println("Cuenta de ahorro abierta para " + cliente.getNombre() + " " + cliente.getApellido());
    }

    @Override
    public void realizarDeposito(double monto) {
        double comision = 3000;
        if (monto >= 500000 && monto < 2000000) {
            comision = 3000 + monto * 0.01;
        } else if (monto >= 2000000 && monto <= 10000000) {
            comision = 2000 + monto * 0.005;
        } else if (monto > 10000000) {
            comision = monto * 0.02;
        }
        saldo += monto - comision;
        System.out.println("Depósito realizado. Saldo actual: " + saldo);
    }

    @Override
    public void retirarDinero(double monto) {
        if (saldo >= monto) {
            saldo -= monto;
            System.out.println("Retiro realizado. Saldo actual: " + saldo);
        } else {
            System.out.println("No tiene suficiente saldo para realizar este retiro.");
        }
    }

    @Override
    public void aplicarComisiones() {
        double rendimiento = saldo * TASA_RENDIMIENTO;
        saldo += rendimiento;
        System.out.println("Rendimiento aplicado. Saldo actual: " + saldo);
    }

    @Override
    public void generarRecibo() {
        System.out.println("Recibo: ");
        System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
        System.out.println("Tipo de cuenta: Cuenta Ahorro");
        System.out.println("Saldo final: " + saldo);
    }
}
