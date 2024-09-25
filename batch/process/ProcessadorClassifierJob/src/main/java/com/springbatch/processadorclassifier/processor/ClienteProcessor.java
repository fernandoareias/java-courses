package com.springbatch.processadorclassifier.processor;

import com.springbatch.processadorclassifier.dominio.Cliente;
import org.springframework.batch.item.ItemProcessor;

public class ClienteProcessor implements ItemProcessor<Cliente, Cliente> {

    @Override
    public Cliente process(Cliente o) throws Exception {

        System.out.println(String.format("Aplicando regras no cliente %s", o.getEmail()));

        return o;
    }
}
