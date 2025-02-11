/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco;

public class CuentaCorriente extends CuentaBancaria {
    private static final double TASA_MANTENIMIENTO = 0.015; // 1.5%

    public CuentaCorriente(Cliente cliente, double saldoInicial) {
        super(cliente, saldoInicial);
    }

    @Override
    public void abrirCuenta() {
        if (saldo < 200000) {
            System.out.println("El saldo inicial debe ser mayor a 200,000 pesos.");
        } else {
            System.out.println("Cuenta corriente abierta para " + cliente.getNombre() + " " + cliente.getApellido());
        }
    }

    @Override
    public void realizarDeposito(double monto) {
        double comision = 7000;
        if (monto >= 500000 && monto < 2000000) {
            comision = 5000 + monto * 0.02;
        } else if (monto >= 2000000 && monto <= 10000000) {
            comision = 4000 + monto * 0.02;
        } else if (monto > 10000000) {
            comision = monto * 0.033;
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
        saldo -= saldo * TASA_MANTENIMIENTO;
        System.out.println("Comisión mensual aplicada. Saldo actual: " + saldo);
    }

    @Override
    public void generarRecibo() {
        System.out.println("Recibo: ");
        System.out.println("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
        System.out.println("Tipo de cuenta: Cuenta Corriente");
        System.out.println("Saldo final: " + saldo);
    }
}
