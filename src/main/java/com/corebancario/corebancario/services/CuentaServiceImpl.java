package com.corebancario.corebancario.services;

import com.corebancario.corebancario.entities.Cuenta;
import com.corebancario.corebancario.entities.Cliente;
import com.corebancario.corebancario.repositories.ClienteRepository;
import com.corebancario.corebancario.repositories.CuentaRepository;
import com.corebancario.corebancario.repositories.MovimientoRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CuentaServiceImpl implements CuentaService {

    @Autowired
    private CuentaRepository cuentaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private MovimientoRepository movimientoRepository;

      @Transactional
    @Override
    public Cuenta guardar(Cuenta cuenta) {
        try {
            String identificacion = cuenta.getCliente().getPersona().getIdentificacion();
            Optional<Cliente> cliente = clienteRepository.findByPersonaIdentificacion(identificacion);
            if (cliente.isEmpty())
                return null;

            if (cuentaRepository.existsByNumeroCuenta(cuenta.getNumeroCuenta()))
                return null;

            cuenta.setCliente(cliente.get());
            return cuentaRepository.save(cuenta);
        } catch (Exception e) {
            return null;
        }
    }

      @Transactional
    @Override
    public Cuenta actualizar(Cuenta cuenta) {
        try {
            Optional<Cuenta> cuentaExistente = cuentaRepository.findByNumeroCuenta(cuenta.getNumeroCuenta());
            if (cuentaExistente.isEmpty())
                return null;

            Cuenta cuentaDB = cuentaExistente.get();
            cuentaDB.setTipoCuenta(cuenta.getTipoCuenta());
            cuentaDB.setSaldoInicial(cuenta.getSaldoInicial());
            cuentaDB.setEstado(cuenta.getEstado());

            return cuentaRepository.save(cuentaDB);
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public Optional<Cuenta> buscarPorNumero(Long numeroCuenta) {
        try {
            return cuentaRepository.findByNumeroCuenta(numeroCuenta);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Cuenta> listar() {
        try {
            return cuentaRepository.findAll();
        } catch (Exception e) {
            return List.of();
        }
    }

    @Transactional
    @Override
    public Cuenta eliminar(Long numeroCuenta) {
        Optional<Cuenta> cuentaOpt = cuentaRepository.findById(numeroCuenta);

        if (cuentaOpt.isPresent()) {
            Cuenta cuenta = cuentaOpt.get();

            // Primero eliminar movimientos relacionados
            movimientoRepository.deleteByCuentaNumeroCuenta(numeroCuenta);

            // Luego eliminar la cuenta
            cuentaRepository.delete(cuenta);
            return cuenta;
        }

        return null;
    }


    @Override
    public List<Cuenta> listarPorCliente(Long clienteId) {
        try {
            return cuentaRepository.findByClienteId(clienteId);
        } catch (Exception e) {
            return List.of();
        }
    }
}
