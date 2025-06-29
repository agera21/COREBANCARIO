package com.corebancario.corebancario.services;


import com.corebancario.corebancario.entities.Cuenta;
import com.corebancario.corebancario.entities.Movimiento;
import com.corebancario.corebancario.repositories.CuentaRepository;
import com.corebancario.corebancario.repositories.MovimientoRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MovimientoServiceImpl implements MovimientoService {

    @Autowired
    private MovimientoRepository movimientoRepository;

    @Autowired
    private CuentaRepository cuentaRepository;

      @Transactional
  @Override
    public Movimiento guardar(Movimiento movimiento) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findById(movimiento.getCuenta().getNumeroCuenta());

        if (cuentaOpt.isEmpty()) {
            return null; // se maneja en el controlador
        }

        Cuenta cuenta = cuentaOpt.get();
        double valorMovimiento = movimiento.getValor();

        if (valorMovimiento == 0) {
            return null;
        }

        if (movimiento.getTipoMovimiento().equalsIgnoreCase("Retiro")) {
            if (cuenta.getSaldoInicial() < valorMovimiento) {
                return null; // Saldo insuficiente
            }
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() - valorMovimiento);
        } else if (movimiento.getTipoMovimiento().equalsIgnoreCase("Deposito")) {
            cuenta.setSaldoInicial(cuenta.getSaldoInicial() + valorMovimiento);
        } else {
            return null; // Tipo de movimiento no válido
        }

        cuentaRepository.save(cuenta);

        movimiento.setSaldo(cuenta.getSaldoInicial());
        movimiento.setFecha(LocalDate.now());
        return movimientoRepository.save(movimiento);
    }


    @Override
    public List<Movimiento> listar() {
        return movimientoRepository.findAll();
    }

      @Transactional
    @Override
    public void eliminar(Long id) {
        if (movimientoRepository.existsById(id)) {
            movimientoRepository.deleteById(id);
        }
    }

    @Override
    public List<Movimiento> movimientosPorCuenta(Long numeroCuenta) {
        return movimientoRepository.findByCuentaNumeroCuenta(numeroCuenta);
    }

    @Override
    public List<Movimiento> reportePorClienteYFechas(Long clienteId, LocalDate desde, LocalDate hasta) {
        return movimientoRepository.findByCuentaClienteIdAndFechaBetween(clienteId, desde, hasta);
    }

    @Override
    public List<Movimiento> movimientosPorCuentaYFechas(Long numeroCuenta, LocalDate desde, LocalDate hasta) {
        return movimientoRepository.findByCuentaNumeroCuentaAndFechaBetween(numeroCuenta, desde, hasta);
    }
}

