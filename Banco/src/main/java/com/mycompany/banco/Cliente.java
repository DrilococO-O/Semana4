/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.banco;

import javax.swing.*;

class Cliente {
    private String nombre;
    private String apellido;
    private String cedula;
    private int edad;
    private String tipoCuenta;
    
    public Cliente(String nombre, String apellido, int edad, String cedula, String tipoCuenta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.cedula = cedula;
        this.tipoCuenta = tipoCuenta;
    }
    
    public String getNombre() { return nombre; }
    public String getApellido() { return apellido; }
    public int getEdad() { return edad; }
    public String getCedula() { return cedula; }
    public String getTipoCuenta() { return tipoCuenta; }
}

abstract class CuentaBancaria {
    protected Cliente cliente;
    protected double saldo;
    
    public CuentaBancaria(Cliente cliente, double saldo) {
        this.cliente = cliente;
        this.saldo = saldo;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public abstract void aplicarComisiones();
}

class CuentaCorriente extends CuentaBancaria {

    public CuentaCorriente(Cliente cliente, double saldo) {
        super(cliente, saldo);
    }

    @Override
    public void aplicarComisiones() {
        // Aplicar comisiones de mantenimiento
        saldo -= saldo * 0.015;  // 1.5% mensual por mantenimiento
    }
    
    public void deposito(double monto) {
        if (monto < 500000) {
            saldo -= 7000;
        } else if (monto >= 500000 && monto < 2000000) {
            saldo -= 5000 + (monto * 0.02);
        } else if (monto >= 2000000 && monto <= 10000000) {
            saldo -= 4000 + (monto * 0.02);
        } else if (monto > 10000000) {
            saldo -= monto * 0.033;
        }
        saldo += monto;  // El depósito al saldo
    }
}

class CuentaAhorro extends CuentaBancaria {

    public CuentaAhorro(Cliente cliente, double saldo) {
        super(cliente, saldo);
    }

    @Override
    public void aplicarComisiones() {
        // Aplicar comisiones de rendimiento
        saldo += saldo * 0.022;  // 2.2% de rendimiento anual
    }

    public void deposito(double monto) {
        if (monto >= 500000 && monto < 2000000) {
            saldo -= 3000 + (monto * 0.01);
        } else if (monto >= 2000000 && monto <= 10000000) {
            saldo -= 2000 + (monto * 0.005);
        } else if (monto > 10000000 && monto < 100000000) {
            saldo -= monto * 0.018;
        } else if (monto >= 100000000) {
            saldo -= monto * 0.02;
        }
        saldo += monto;  // El depósito al saldo
    }
}

public class Banco {
    public static void main(String[] args) {
        while (true) {
            String menu = "Seleccione una opción:\n" +
                          "1. Apertura de Cuentas (Ahorro/Corriente)\n" +
                          "2. Transferencias\n" +
                          "3. Cajero Automático\n" +
                          "4. Cierre de Mes (Estado de Cuenta)\n" +
                          "5. Salir";

            int opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));

            switch (opcion) {
                case 1:
                    // Apertura de cuenta
                    String nombre = JOptionPane.showInputDialog("Introduce el nombre del cliente: ");
                    String apellido = JOptionPane.showInputDialog("Introduce el apellido del cliente: ");
                    String cedula = JOptionPane.showInputDialog("Introduce la cédula del cliente: ");
                    int edad = Integer.parseInt(JOptionPane.showInputDialog("Introduce la edad del cliente: "));
                    String tipoCuenta = JOptionPane.showInputDialog("¿Qué tipo de cuenta desea abrir? (Ahorro/Corriente)");

                    // Crear un cliente
                    Cliente cliente = new Cliente(nombre, apellido, edad, cedula, tipoCuenta);

                    // Verificar el monto mínimo de apertura
                    double montoApertura = 200000; // Monto mínimo para cuenta corriente
                    if (tipoCuenta.equalsIgnoreCase("Corriente") && montoApertura < 200000) {
                        JOptionPane.showMessageDialog(null, "El monto mínimo para apertura en cuenta corriente es 200,000.");
                        break;
                    }

                    // Crear la cuenta según el tipo seleccionado
                    CuentaBancaria cuenta = null;
                    if (tipoCuenta.equalsIgnoreCase("Corriente")) {
                        cuenta = new CuentaCorriente(cliente, montoApertura); 
                    } else if (tipoCuenta.equalsIgnoreCase("Ahorro")) {
                        cuenta = new CuentaAhorro(cliente, montoApertura);
                    }

                    // Confirmar apertura de cuenta
                    JOptionPane.showMessageDialog(null, "Cuenta abierta con éxito.");
                    break;

                case 2:
                    // Transferencias (Funcionalidad pendiente)
                    JOptionPane.showMessageDialog(null, "Funcionalidad de Transferencias en desarrollo.");
                    break;

                case 3:
                    // Cajero automático (Funcionalidad pendiente)
                    JOptionPane.showMessageDialog(null, "Funcionalidad de Cajero Automático en desarrollo.");
                    break;

                case 4:
                    // Mostrar estado de cuenta (Funcionalidad pendiente)
                    JOptionPane.showMessageDialog(null, "Funcionalidad de Cierre de Mes en desarrollo.");
                    break;

                case 5:
                    // Salir
                    JOptionPane.showMessageDialog(null, "Gracias por usar nuestro servicio.");
                    System.exit(0);
                    break;

                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida. Intente nuevamente.");
            }
        }
    }
}
