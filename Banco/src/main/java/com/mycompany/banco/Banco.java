/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.mycompany.banco;
import javax.swing.*;
import java.util.*;

public class Banco {
    static ArrayList<Cuenta> cuentas = new ArrayList<>();
    static Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcion = 0;

        do {
            String menu = "Bienvenido al Banco. Seleccione una opción:\n"
                        + "1. Apertura de Cuenta\n"
                        + "2. Transferencias\n"
                        + "3. Cajero Automático\n"
                        + "4. Cierre de Mes\n"
                        + "5. Salir";
            opcion = Integer.parseInt(JOptionPane.showInputDialog(menu));

            switch (opcion) {
                case 1:
                    abrirCuenta();
                    break;
                case 2:
                    realizarTransferencia();
                    break;
                case 3:
                    cajeroAutomatico();
                    break;
                case 4:
                    cierreDeMes();
                    break;
                case 5:
                    JOptionPane.showMessageDialog(null, "Gracias por usar nuestros servicios.");
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Opción no válida.");
            }
        } while (opcion != 5);
    }

    // 1. Apertura de cuenta
    public static void abrirCuenta() {
        String nombre = JOptionPane.showInputDialog("Ingrese su nombre:");
        String apellido = JOptionPane.showInputDialog("Ingrese su apellido:");
        int edad = Integer.parseInt(JOptionPane.showInputDialog("Ingrese su edad:"));
        String tipoCuenta = JOptionPane.showInputDialog("Ingrese el tipo de cuenta (ahorro/corriente):").toLowerCase();

        // Aquí se crea la cuenta
        if (tipoCuenta.equals("corriente")) {
            double saldoInicial = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto de apertura (mínimo 200,000):"));
            if (saldoInicial >= 200000) {
                CuentaCorriente cuenta = new CuentaCorriente(nombre, apellido, edad, saldoInicial);
                cuentas.add(cuenta);
                JOptionPane.showMessageDialog(null, "Cuenta corriente abierta exitosamente.");
            } else {
                JOptionPane.showMessageDialog(null, "Monto de apertura insuficiente.");
            }
        } else if (tipoCuenta.equals("ahorro")) {
            double saldoInicial = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto de apertura:"));
            CuentaAhorro cuenta = new CuentaAhorro(nombre, apellido, edad, saldoInicial);
            cuentas.add(cuenta);
            JOptionPane.showMessageDialog(null, "Cuenta de ahorro abierta exitosamente.");
        } else {
            JOptionPane.showMessageDialog(null, "Tipo de cuenta no válido.");
        }
    }

    // 2. Realizar transferencia
    public static void realizarTransferencia() {
        String numCuentaOrigen = JOptionPane.showInputDialog("Ingrese el número de cuenta de origen:");
        String numCuentaDestino = JOptionPane.showInputDialog("Ingrese el número de cuenta de destino:");
        double monto = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto a transferir:"));

        Cuenta cuentaOrigen = encontrarCuenta(numCuentaOrigen);
        Cuenta cuentaDestino = encontrarCuenta(numCuentaDestino);

        if (cuentaOrigen != null && cuentaDestino != null && cuentaOrigen.getSaldo() >= monto) {
            cuentaOrigen.retirar(monto);
            cuentaDestino.depositar(monto);
            JOptionPane.showMessageDialog(null, "Transferencia exitosa.");
        } else {
            JOptionPane.showMessageDialog(null, "Transferencia no realizada. Verifique los datos.");
        }
    }

    // 3. Cajero Automático
    public static void cajeroAutomatico() {
        String numCuenta = JOptionPane.showInputDialog("Ingrese el número de cuenta:");
        Cuenta cuenta = encontrarCuenta(numCuenta);

        if (cuenta != null) {
            double montoRetiro = Double.parseDouble(JOptionPane.showInputDialog("Ingrese el monto a retirar:"));
            boolean esCajeroBanco = JOptionPane.showConfirmDialog(null, "¿Está usando un cajero del banco?") == JOptionPane.YES_OPTION;

            if (cuenta.getSaldo() >= montoRetiro) {
                cuenta.retirar(montoRetiro);

                if (!esCajeroBanco) {
                    cuenta.retirar(4500); // Comisión por usar un cajero fuera del banco
                    JOptionPane.showMessageDialog(null, "Comisión por usar cajero de otro banco: 4500.");
                }
                JOptionPane.showMessageDialog(null, "Retiro exitoso.");
            } else {
                JOptionPane.showMessageDialog(null, "Saldo insuficiente.");
            }
        } else {
            JOptionPane.showMessageDialog(null, "Cuenta no encontrada.");
        }
    }

    // 4. Cierre de mes
    public static void cierreDeMes() {
        for (Cuenta cuenta : cuentas) {
            cuenta.aplicarComisionesYRendimientos();
            JOptionPane.showMessageDialog(null, cuenta.getEstadoDeCuenta());
        }
    }

    // Método para encontrar cuenta por número
    public static Cuenta encontrarCuenta(String numeroCuenta) {
        for (Cuenta cuenta : cuentas) {
            if (cuenta.getNumeroCuenta().equals(numeroCuenta)) {
                return cuenta;
            }
        }
        return null;
    }
}

// Clase Cuenta (base)
abstract class Cuenta {
    protected String nombre, apellido;
    protected int edad;
    protected double saldo;
    protected String numeroCuenta;

    public Cuenta(String nombre, String apellido, int edad, double saldoInicial) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.saldo = saldoInicial;
        this.numeroCuenta = generarNumeroCuenta();
    }

    public double getSaldo() {
        return saldo;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public abstract void aplicarComisionesYRendimientos();

    public void depositar(double monto) {
        saldo += monto;
    }

    public void retirar(double monto) {
        saldo -= monto;
    }

    public String getEstadoDeCuenta() {
        return "Cuenta: " + numeroCuenta + "\nSaldo: " + saldo;
    }

    private String generarNumeroCuenta() {
        return String.valueOf(UUID.randomUUID().toString());
    }
}

// Cuenta Corriente
class CuentaCorriente extends Cuenta {
    public CuentaCorriente(String nombre, String apellido, int edad, double saldoInicial) {
        super(nombre, apellido, edad, saldoInicial);
    }

    @Override
    public void aplicarComisionesYRendimientos() {
        saldo -= saldo * 0.015; // 1.5% comisión mensual
    }
}

// Cuenta de Ahorro
class CuentaAhorro extends Cuenta {
    public CuentaAhorro(String nombre, String apellido, int edad, double saldoInicial) {
        super(nombre, apellido, edad, saldoInicial);
    }

    @Override
    public void aplicarComisionesYRendimientos() {
        saldo += saldo * 0.022; // 2.2% rendimiento anual
    }
}
